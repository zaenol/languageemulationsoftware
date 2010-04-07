package modelEditor.distortions;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_BubbleUp;
import modelEditor.distortions.Anomic_correction_omissions.model;
import modelEditor.distortions.Anomic_correction_omissions.view;
import modelEditor.model.Model_Message;
import modelEditor.model.Model_Message_posWord.PosWord;

public class Anomic_correction_semanticDescription extends
		AC_Distortion_BubbleUp implements ChangeListener {

			model m;
			view v;
			
	public Anomic_correction_semanticDescription() {
		super("Semantic Description", false, false, false, true);
		m = new model();
		v = new view();
	}

	public Document getXML() {
		// TODO Auto-generated method stub
		return null;
	}


	public void parseMessageWord(PosWord posWord){
		
	}


	public void setValuesFromXML_local(Document dom) {
		// TODO Auto-generated method stub

	}
	
	public class view{
		JSlider slider;
		JLabel label;
		
		public view(){
			label = new JLabel("Value of "+" 0%", JLabel.CENTER);
			label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
			bodyPanel.add(label);
			
			slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
			slider.setMajorTickSpacing(10);
			slider.setMinorTickSpacing(2);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.addChangeListener(Anomic_correction_semanticDescription.this);
			bodyPanel.add(slider);
		}
		public void update(){
			label.setText("Value of "+slider.getValue()+"%");
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
		fireDoubleEvent(new Double(v.getSliderValue()-m.getOldValue()));
		m.setOldValue(v.getSliderValue());		
	}

}
