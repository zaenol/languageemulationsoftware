package modelEditor.distortions;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.abstractClasses.AC_Distortion_BubbleUp;
import modelEditor.model.Classification;

public class testDist2 extends AC_Distortion_BubbleUp implements ChangeListener{
	
	JSlider slider;
	JLabel label;
	int oldValue = 0;
	
	public testDist2(){
		super("Test 2");
		this.setCORRECTION(true);
		this.setANOMIC(true);
		
		label = new JLabel("Value of "+" 0%", JLabel.CENTER);
		label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.bodyPanel.add(label);
		
		slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(2);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(this);
		this.bodyPanel.add(slider);
	}

	public void getXML() {
		// TODO Auto-generated method stub
		
	}

	public void initID() {
		// TODO Auto-generated method stub
		
	}

	public String[] parseString(String[] messages) {
		// TODO Auto-generated method stub
		return messages;
	}

	public void setXML() {
		// TODO Auto-generated method stub
		
	}

	public void stateChanged(ChangeEvent e) {
		label.setText("Value of "+slider.getValue()+"%");
		this.fireDoubleEvent(new Double(slider.getValue()-oldValue));
		oldValue = slider.getValue();
		
	}
	

}
