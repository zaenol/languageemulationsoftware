package modelEditor.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import modelEditor.abstractClasses.AC_Distortion;
import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.abstractClasses.AC_Distortion_BubbleUp;
import modelEditor.abstractClasses.AC_Distortion_Independent;
import modelEditor.abstractClasses.AC_Element;
import modelEditor.eventsListeners.BubbleDown_Event;
import modelEditor.eventsListeners.BubbleDown_Listener;
import modelEditor.eventsListeners.BubbleUp_Event;
import modelEditor.eventsListeners.BubbleUp_Listener;
import modelEditor.interfaces.I_Element;

public class Classification extends AC_Element implements BubbleUp_Listener, ChangeListener{
	
	private Classification_Model model;
	private Classification_View view;

	public Classification(String classificationName) {
		super(classificationName);
		
		model = new Classification_Model();
		view = new Classification_View();
		
		//masterPanel.setSize(300, 300);
	}
	
	public void addDistortion(Constructor distConstructor,AC_Distortion distInstance){
		
		/*
		 * Functionality
		 */
		
		boolean isBubbleDown = matchClass(distInstance.getClass(),AC_Distortion_BubbleDown.class,10);
		
		if(isBubbleDown){
			AC_Distortion_BubbleDown instance = (AC_Distortion_BubbleDown) distInstance;
			model.addBubbleDownDistortion(instance);
			view.hasBubbleDown(true);
			
		}
		
		boolean isBubbleUp = matchClass(distInstance.getClass(),AC_Distortion_BubbleUp.class,10);
		if(isBubbleUp){
			AC_Distortion_BubbleUp instance = (AC_Distortion_BubbleUp) distInstance;
			instance.addBubbleUpListener(this);
			model.addBubbleUpDistortion(instance);
			view.hasBubbleUp(true);
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
    	
    	//System.out.println(source+" vs "+target);
    	
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
	
	private int getCorrectness(){
		return model.getCorrectness();
	}
	private int getRate(){
		return model.getRate();
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		view.sliderMoved(source);
		if(view.sliderIsRate(source)!=-1)
			model.setRate(view.sliderIsRate(source));
		if(view.sliderIsCorrectness(source)!=-1)
			model.setCorrectness(view.sliderIsCorrectness(source));
		
	}
	
	private class Classification_Model{
		int correctness = 0;
		int rate = 0;
		



		private ArrayList<AC_Distortion> allDistortions = new ArrayList<AC_Distortion>();
		
		private ArrayList<AC_Distortion_BubbleDown> bubbleDownDistortions = new ArrayList<AC_Distortion_BubbleDown>();
		private ArrayList<AC_Distortion_BubbleUp> bubbleUpDistortions = new ArrayList<AC_Distortion_BubbleUp>();
		private ArrayList<AC_Distortion_Independent> indeptDistortions = new ArrayList<AC_Distortion_Independent>();
		
		public Classification_Model(){
			
		}
		
		
		public synchronized void addBubbleDownDistortion(AC_Distortion_BubbleDown bubbleDown){
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

		public int getRate() {
			return rate;
		}


		public void setRate(int rate) {
			this.rate = rate;
		}

		public int getCorrectness() {
			return correctness;
		}


		public void setCorrectness(int correctness) {
			this.correctness = correctness;
			fireDoubleEvent(correctness+0d);
		}
		
		protected synchronized void fireDoubleEvent(Double value){
			BubbleDown_Event event = new BubbleDown_Event(Classification.this,value);
			
			for(BubbleDown_Listener listen:bubbleDownDistortions)
				listen.bubbleDownEventDetected(event);
		}
		

	}
	
	private class Classification_View{
		
		JPanel distortionErrors;
		JPanel distortionCorrections;
		
		JPanel correctness;
		JLabel correctnessLabel;
		JSlider correctnessSlider;
		String correctnessTitle = "Correctness = ";
		
		JPanel rate;
		JLabel rateLabel;
		JSlider rateSlider;
		String rateTitle = "Correction Rate = ";
		
		boolean isBubbleDown=false;
		boolean isBubbleUp=false;
		
		public Classification_View(){
	
			setBorder(Color.gray);
			
			bodyPanel.setLayout(new GridLayout(1, 2));
			headerPanel.setLayout(new GridLayout(1,2));
			
			correctness = new JPanel(new GridLayout(2,1));
			headerPanel.add(correctness,BorderLayout.CENTER);
			correctness.setVisible(false);
			
			rate = new JPanel(new GridLayout(2,1));
			headerPanel.add(rate,BorderLayout.EAST);
			rate.setVisible(false);

			distortionErrors  = createDistortionGroup("Errors",Color.gray);
			
			distortionCorrections = createDistortionGroup("Corrections",Color.gray);

			
			correctnessLabel = new JLabel(correctnessTitle+" 0%", JLabel.CENTER);
		    correctnessLabel.setAlignmentX(correctness.CENTER_ALIGNMENT);
		    correctness.add(correctnessLabel);
			
			correctnessSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, getCorrectness());
			correctnessSlider.setMajorTickSpacing(10);
			correctnessSlider.setMinorTickSpacing(2);
			correctnessSlider.setPaintTicks(true);
			correctnessSlider.setPaintLabels(true);
			correctnessSlider.addChangeListener(Classification.this);
			correctness.add(correctnessSlider);
			
			rateLabel = new JLabel(rateTitle+" 0%", JLabel.CENTER);
		    rateLabel.setAlignmentX(rate.CENTER_ALIGNMENT);
		    rate.add(rateLabel);
			
			rateSlider = new JSlider(JSlider.HORIZONTAL, 0, 30, getRate());
			rateSlider.setMajorTickSpacing(10);
			rateSlider.setMinorTickSpacing(1);
			rateSlider.setPaintTicks(true);
			rateSlider.setPaintLabels(true);
			rateSlider.addChangeListener(Classification.this);
			rate.add(rateSlider);
			
			
		}
		
		public void hasBubbleDown(boolean isBubbleDown) {
			this.isBubbleDown = isBubbleDown;
			correctness.setVisible(true);
		}
		public void hasBubbleUp(boolean isBubbleUp){
			this.isBubbleUp = isBubbleUp;
			rate.setVisible(true);
		}

		private JPanel createDistortionGroup(String label,Color c){
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createLineBorder(c));
			panel.add(new JLabel(label,JLabel.CENTER));
			bodyPanel.add(panel);
			return panel;
		}

		public void addDistortionError(JPanel panel){
			distortionErrors.add(panel);
			
		}
		public void addDistortionCorrection(JPanel panel){
			distortionCorrections.add(panel);
		}
		
		public int sliderIsRate(Object source){
			if(source.equals(rateSlider))
				return rateSlider.getValue();
			return -1;
		}
		public int sliderIsCorrectness(Object source){
			if(source.equals(correctnessSlider))
				return correctnessSlider.getValue();
			return -1;
		}
		
		public void sliderMoved(Object source){
			if(source.equals(rateSlider))
				rateLabel.setText(rateTitle+rateSlider.getValue()+"%");
			else if(source.equals(correctnessSlider))
				correctnessLabel.setText(correctnessTitle+correctnessSlider.getValue()+"%");
		}
		
	}



	

}
