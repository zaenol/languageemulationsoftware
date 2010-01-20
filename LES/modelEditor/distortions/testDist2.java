package modelEditor.distortions;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.abstractClasses.AC_Distortion_BubbleUp;
import modelEditor.model.Classification;

public class testDist2 extends AC_Distortion_BubbleUp implements ChangeListener{
	
	JSlider slider;
	JLabel label;
	int oldValue = 0;
	
	public testDist2(){
		super("Test 2",false,false,false,false);
		
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

	public Document getXML() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setValuesFromXML_local(Document dom) {
		// TODO Auto-generated method stub
		
	}

	public String[] parseString_local(String[] messages) {
		// TODO Auto-generated method stub
		return null;
	}

	public void stateChanged(ChangeEvent e) {
		label.setText("Value of "+slider.getValue()+"%");
		this.fireDoubleEvent(new Double(slider.getValue()-oldValue));
		oldValue = slider.getValue();
		
	}




	

}
