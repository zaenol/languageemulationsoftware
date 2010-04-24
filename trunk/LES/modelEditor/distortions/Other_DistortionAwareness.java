package modelEditor.distortions;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_BubbleUp;
import modelEditor.abstractClasses.AC_Distortion_Independent;
import modelEditor.distortions.Anomic_correction_omissions.model;
import modelEditor.distortions.Anomic_correction_omissions.view;
import modelEditor.model.Model_Message;
import modelEditor.model.Model_Message_posWord.PosWord;

public class Other_DistortionAwareness extends AC_Distortion_Independent implements ItemListener {

	model m;
	view v;
	
	public Other_DistortionAwareness() {
		super("Distortion Awarness", false, false, false, false,true);
		m = new model();
		v = new view();
	}

	public Document getXML() {
		// TODO Auto-generated method stub
		return null;
	}

	public Model_Message parseMessage(Model_Message messages) {
		Model_Message myMessages = messages;
		
		if(m.isSelected())
			myMessages.setPostOriginalMessage(false);
		else
			myMessages.setPostOriginalMessage(true);
		
		return myMessages;
	}
	/*
	public void parseMessageWord(PosWord posWord){
		
	}*/

	public void setValuesFromXML_local(Document dom) {
		// TODO Auto-generated method stub

	}
	
	public class view{
		JCheckBox slider;
		JLabel label;
		
		public view(){
			//label = new JLabel("Value of "+" 0%", JLabel.CENTER);
			//label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
			//bodyPanel.add(label);
			
			slider = new JCheckBox("Subject is Aware of Their Errors",true);
			slider.setSelected(true);
			
			
			slider.addItemListener(Other_DistortionAwareness.this);
			bodyPanel.add(slider);
		}
		public void update(){
			String not = "";
			if(!slider.isSelected())
				not = "not ";
			
			slider.setText("Subject is "+not+"Aware of Their Errors");
		}
		public boolean isSelected(){
			return slider.isSelected();
		}
	}
	public class model{
		boolean selected = true;
		public model(){
			
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		
	}
	/*
	public void stateChanged(ChangeEvent e) {
		v.update();
		//fireDoubleEvent(new Double(v.getSliderValue()-m.getOldValue()));
		m.setOldValue(v.getSliderValue());		
	}*/
	//@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		v.update();
		m.setSelected(e.getStateChange() == ItemEvent.SELECTED);
		
	}

}
