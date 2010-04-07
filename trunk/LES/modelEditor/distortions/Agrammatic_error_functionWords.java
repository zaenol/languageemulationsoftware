package modelEditor.distortions;

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

public class Agrammatic_error_functionWords  extends AC_Distortion_Independent implements ChangeListener  {

	model m;
	view v;
	
	
	public Agrammatic_error_functionWords() {
		super("Dropping Function Words", false, false, true, false);
		m = new model();
		v = new view();
	}

	public Document getXML() {
		// TODO Auto-generated method stub
		return null;
	}

	public Model_Message parseMessage(Model_Message messages) {
		// TODO Auto-generated method stub
		return messages;
	}

	public void setValuesFromXML_local(Document dom) {
		// TODO Auto-generated method stub

	}

	public class view{
		JSlider slider;
		JLabel label;
		String labelS = "Probability of Dropping a Function Word ";
		
		public view(){
			label = new JLabel(labelS+"0%", JLabel.CENTER);
			label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
			bodyPanel.add(label);
			
			slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
			slider.setMajorTickSpacing(10);
			slider.setMinorTickSpacing(2);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.addChangeListener(Agrammatic_error_functionWords.this);
			bodyPanel.add(slider);
		}
		public void update(){
			label.setText(labelS+slider.getValue()+"%");
		}
		public int getSliderValue(){
			return slider.getValue();
		}
	}
	public class model{
		int oldValue = 0;
		public model(){
			
		}
		public int getOldValue() {
			return oldValue;
		}
		public void setOldValue(int oldValue) {
			this.oldValue = oldValue;
		}
		
	}
	public void stateChanged(ChangeEvent e) {
		v.update();
		//fireDoubleEvent(new Double(v.getSliderValue()-m.getOldValue()));
		m.setOldValue(v.getSliderValue());		
	}

}
