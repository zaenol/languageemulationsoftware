package modelEditor.model;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelEditor.abstractClasses.AC_Distortion;
import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.abstractClasses.AC_Distortion_BubbleUp;
import modelEditor.abstractClasses.AC_Distortion_Independent;
import modelEditor.abstractClasses.AC_Element;
import modelEditor.eventsListeners.BubbleDown_Listener;
import modelEditor.eventsListeners.BubbleUp_Event;
import modelEditor.eventsListeners.BubbleUp_Listener;
import modelEditor.interfaces.I_Element;

public class Classification extends AC_Element implements BubbleUp_Listener{
	
	Classification_Model model;
	Classification_View view;

	public Classification(String classificationName) {
		super(classificationName);
		
		model = new Classification_Model();
		view = new Classification_View();
		
		panel.setSize(300, 300);
	}
	
	public void addDistortion(Constructor distConstructor,AC_Distortion distInstance){
		
		/*
		 * Functionality
		 */
		
		boolean isBubbleDown = matchClass(distInstance.getClass(),AC_Distortion_BubbleDown.class,10);
		
		if(isBubbleDown){
			AC_Distortion_BubbleDown instance = (AC_Distortion_BubbleDown) distInstance;
			model.addBubbleDownDistortion(instance);
			
		}
		
		boolean isBubbleUp = matchClass(distInstance.getClass(),AC_Distortion_BubbleUp.class,10);
		if(isBubbleUp){
			AC_Distortion_BubbleUp instance = (AC_Distortion_BubbleUp) distInstance;
			instance.addBubbleUpListener(this);
			model.addBubbleUpDistortion(instance);
		}
		
		boolean isIndependent = matchClass(distInstance.getClass(),AC_Distortion_Independent.class,10);
		if(isIndependent){
			AC_Distortion_Independent instance = (AC_Distortion_Independent) distInstance;
			model.addIndeptDistortion(instance);
		}
		
		boolean isDistortionError = distInstance.isDISTORTION_ERROR();
		if(isDistortionError)
			view.addDistortionError(distInstance.getGUI());
		
		boolean isDistortionCorrection = distInstance.isDISTORTION_CORRECTION();
		if(isDistortionCorrection)
			view.addDistortionCorrection(distInstance.getGUI());
		
	}
	
	private boolean matchClass(Class source,Class target,int depth){
    	
    	System.out.println(source+" vs "+target);
    	
    		if(source.equals(target))
    			return true;   	
    	
    	if ( source.getSuperclass() != null )
    		return matchClass(source.getSuperclass(),target,depth+1);
    	return false;
    }
	public String[] parseString(String[] messages){
				return model.parseString(messages);
	}
	
	public void bubbleUpEventDetected(BubbleUp_Event event) {
		// TODO Auto-generated method stub
		
	}

	public void getXML() {
		// TODO Auto-generated method stub
		
	}

	public void setXML() {
		// TODO Auto-generated method stub
		
	}
	
	
	private class Classification_Model{
		private ArrayList<AC_Distortion> allDistortions = new ArrayList<AC_Distortion>();
		
		private ArrayList<AC_Distortion_BubbleDown> bubbleDownDistortions = new ArrayList<AC_Distortion_BubbleDown>();
		private ArrayList<AC_Distortion_BubbleUp> bubbleUpDistortions = new ArrayList<AC_Distortion_BubbleUp>();
		private ArrayList<AC_Distortion_Independent> indeptDistortions = new ArrayList<AC_Distortion_Independent>();
		
		public Classification_Model(){
			
		}
		
		
		public void addBubbleDownDistortion(AC_Distortion_BubbleDown bubbleDown){
			bubbleDownDistortions.add(bubbleDown);
			allDistortions.add(bubbleDown);
		}
		public void addBubbleUpDistortion(AC_Distortion_BubbleUp bubbleUp){
			bubbleUpDistortions.add(bubbleUp);
			allDistortions.add(bubbleUp);
		}
		public void addIndeptDistortion(AC_Distortion_Independent indept){
			indeptDistortions.add(indept);
			allDistortions.add(indept);
		}

		public String[] parseString(String[] messages){
			String[] lastEdit = messages.clone();
			
			for(AC_Distortion distortion:allDistortions){
				lastEdit = distortion.parseString(messages);
			}			
			return lastEdit;
		}
		public void setXML(){

			for(AC_Distortion distortion:allDistortions){
				distortion.setXML();
			}			
			
		}

	}
	
	private class Classification_View{
		JPanel distortionErrors;
		JPanel distortionCorrections;
		
		public Classification_View(){
			panel.setBorder(BorderFactory.createLineBorder(Color.blue));
			
			distortionErrors = new JPanel();
			distortionErrors.setBorder(BorderFactory.createLineBorder(Color.red));
			distortionErrors.add(new JLabel("Errors"));
			panel.add(distortionErrors);
			
			distortionCorrections = new JPanel();
			distortionCorrections.setBorder(BorderFactory.createLineBorder(Color.green));
			distortionCorrections.add(new JLabel("Corrections"));
			panel.add(distortionCorrections);
		}
		public JPanel getGUI() {
			
			return panel;
		}
		public void addDistortionError(JPanel panel){
			distortionErrors.add(panel);
			
		}
		public void addDistortionCorrection(JPanel panel){
			distortionCorrections.add(panel);
		}
		
	}





}
