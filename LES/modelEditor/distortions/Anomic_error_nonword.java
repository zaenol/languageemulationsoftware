package modelEditor.distortions;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_BubbleDown;

public class Anomic_error_nonword extends AC_Distortion_BubbleDown implements ChangeListener{

	model m;
	view v;
	
	public Anomic_error_nonword() {
		super("Non Word", true, false, true, false);
		m = new model();
		v = new view();
		
		
		update();
	}

	public void update() {
		m.update();
		v.update();

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
		Double value = new Double(v.getSlider_localValue());
		
		if(isReleased())
			this.setSeverityValue_local(value);
		
		update();
		
	}
	
	public double getD_errorProbability() {
		return m.getD_errorProbability();
	}
	
	private class model{
		double d_errorProbability = 0;
		
		public model(){
			
		}
		
		public void update(){
			double x = getSeverityValue_local()/100;
			d_errorProbability = 0.0391*Math.pow(x,2) - 0.4101*x + 0.369;
			if(d_errorProbability<0)
				d_errorProbability =0;
			
		}

		public double getD_errorProbability() {
			return d_errorProbability;
		}
		
	}
	
	private class view{
		JLabel l_errorProbability;
		String s_errorProbability = "Probability of Error: ";
		
		JSlider slider_localValue;
		JLabel label_localValue;
		String string_localValue = "Local Correctness: ";
		
		public view(){
			l_errorProbability = new JLabel();
			l_errorProbability.setAlignmentX(Component.LEFT_ALIGNMENT);
			bodyPanel.add(l_errorProbability);
			
			
			label_localValue = new JLabel("", JLabel.CENTER);
			//label_localValue.setAlignmentX(JPanel.CENTER_ALIGNMENT);
			label_localValue.setAlignmentX(Component.LEFT_ALIGNMENT);
			bodyPanel.add(label_localValue);
			
			slider_localValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
			slider_localValue.setMajorTickSpacing(100);
			slider_localValue.setMinorTickSpacing(2);
			slider_localValue.setPaintTicks(true);
			slider_localValue.setPaintLabels(true);
			slider_localValue.addChangeListener(Anomic_error_nonword.this);
			slider_localValue.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			bodyPanel.add(slider_localValue);
		}
		
		public int getSlider_localValue(){
			return slider_localValue.getValue();
		}
		
		public void update(){
			l_errorProbability.setText(s_errorProbability+roundFourDecimals(getD_errorProbability()));

			slider_localValue.setEnabled(isReleased());
			label_localValue.setText(string_localValue+this.slider_localValue.getValue()+"%");
			slider_localValue.setValue(new Double(getSeverityValue_local()).intValue());
			slider_localValue.setVisible(isReleased());
			label_localValue.setVisible(isReleased());
		}
	}
}