package modelEditor.distortions;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.model.Model_Message;
import modelEditor.model.Model_Message_posWord.PosWord;

/**
 * @author jmh
 * unrelated	word	errors	(e.g.,	pig -> ‘‘brain’’)
 * two completely unrelated words.
 *
 */
public class Anomic_error_unrelated extends AC_Distortion_BubbleDown implements ChangeListener {

	model m;
	view v;
	
	Random random;
	
	String fullPath = "/modelEditor/distortions/conceptNet/";
	String[] fileNames = {"_adjList.csv","_adVerbList.csv","_nounList.csv","_verbList.csv"};
	ArrayList[] fileValues = {new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>()};
	
	public Anomic_error_unrelated() {
		super("UnRelated", true, false, false, false);
		m = new model();
		v = new view();
		
		random = new Random();
		
		try{
			for(int i=0; i<fileNames.length;i++){
				InputStream in = getClass().getResourceAsStream(fullPath+fileNames[i]);
			
				BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));
				String line = "";
				while ((line = buffRead.readLine()) != null){
					fileValues[i].add(line);
				}
			
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//header line
		
		
		update();
	}

	public void update() {
		m.update();
		fireDoubleEvent(new Double(m.getChnage()*100));
		v.update();

	}

	public Document getXML() {
		// TODO Auto-generated method stub
		return null;
	}

	public void parseMessageWord(PosWord posWord){
		if(posWord.pos_isContentWord()){
			
			if(posWord.pos_isVerb()){
				findNewWord(3,posWord);
				
			}else if(posWord.pos_isNoun()){
				findNewWord(2,posWord);
			}else if(posWord.pos_isAdj()){
				findNewWord(0,posWord);
			}else if(posWord.pos_isAdVerb()){
				findNewWord(1,posWord);
			}
			
		}
		
	}
	
	private void findNewWord(int pullFrom,PosWord posWord){
		boolean notFound = true;
		while(notFound){
			String relation = (String) fileValues[pullFrom].get(random.nextInt(fileValues[pullFrom].size()));
			if(!relation.equalsIgnoreCase(posWord.getTokenAsWord())){
				notFound = false;
				posWord.setDistorted("UNRELATED", relation); 
			}
		}
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
	
	public double getProbability() {
		// TODO Auto-generated method stub
		return getD_errorProbability();
	}
	
	public double getD_errorProbability() {
		return m.getD_errorProbability();
	}
	
	private class model{
		double d_errorProbability = 0;
		
		double oldValue = 0;
		public model(){
			
		}
		public double getOldValue() {
			return oldValue;
		}
		public void setOldValue(double oldValue) {
			this.oldValue = oldValue;
		}
		
		public double getChnage(){
			return getD_errorProbability()-getOldValue();
		}
		
		public void update(){
			setOldValue(d_errorProbability);
			double x = getSeverityValue_local()/100;
			d_errorProbability = 0.4119*Math.pow(x,2) - 0.7368*x + 0.3356;
			if(d_errorProbability<0|| getSeverityValue_local()==100)
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
		String string_localValue = "Locally Simulated Correctness: ";
		
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
			slider_localValue.addChangeListener(Anomic_error_unrelated.this);
			slider_localValue.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			bodyPanel.add(slider_localValue);
		}
		
		public int getSlider_localValue(){
			return slider_localValue.getValue();
		}
		
		public void update(){
			l_errorProbability.setText(s_errorProbability+round4Decimals(getD_errorProbability()));

			slider_localValue.setEnabled(isReleased());
			label_localValue.setText(string_localValue+this.slider_localValue.getValue()+"%");
			slider_localValue.setValue(new Double(getSeverityValue_local()).intValue());
			slider_localValue.setVisible(isReleased());
			label_localValue.setVisible(isReleased());
		}
	}

}