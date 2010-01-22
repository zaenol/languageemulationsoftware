package modelEditor.distortions;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_BubbleDown;

public class Anomic_error_formal extends AC_Distortion_BubbleDown implements ChangeListener {

	JLabel l_errorProbability;
	String s_errorProbability = "Probability of Error: ";
	double d_errorProbability = 0;
	
	JSlider slider_localValue;
	JLabel label_localValue;
	String string_localValue = "Local Correctness: ";
	
	public Anomic_error_formal() {
		super("Formal", true, false, true, false);
		
		l_errorProbability = new JLabel();
		l_errorProbability.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.bodyPanel.add(l_errorProbability);
		
		
		label_localValue = new JLabel("", JLabel.CENTER);
		//label_localValue.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		label_localValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.bodyPanel.add(label_localValue);
		
		slider_localValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		slider_localValue.setMajorTickSpacing(100);
		slider_localValue.setMinorTickSpacing(2);
		slider_localValue.setPaintTicks(true);
		slider_localValue.setPaintLabels(true);
		slider_localValue.addChangeListener(this);
		slider_localValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		this.bodyPanel.add(slider_localValue);
		
		update();
	}

	public void update() {
		double x = this.getSeverityValue_local()/100;
		d_errorProbability = -0.0303*Math.pow(x,2) - 0.1986*x + 0.2134;
		if(d_errorProbability<0)
			d_errorProbability =0;
		l_errorProbability.setText(s_errorProbability+this.roundFourDecimals(d_errorProbability));

		slider_localValue.setEnabled(this.isReleased());
		label_localValue.setText(string_localValue+this.slider_localValue.getValue()+"%");
		slider_localValue.setValue(new Double(this.getSeverityValue_local()).intValue());
		slider_localValue.setVisible(this.isReleased());
		label_localValue.setVisible(this.isReleased());

	}

	public Document getXML() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] parseString_local(String[] messages) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValuesFromXML_local(Document dom) {
		// TODO Auto-generated method stub

	}

	public void stateChanged(ChangeEvent e) {
		Double value = new Double(slider_localValue.getValue());
		//this.setSeverityValue_global(value);
		if(isReleased())
			this.setSeverityValue_local(value);
		update();
		
	}
	
	private class view{
		public view(){
			
		}
	}

}
