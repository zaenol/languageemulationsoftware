package modelEditor.distortions;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.distortions.spelling.JaSpellWidget;
import modelEditor.model.Model_Message;
import modelEditor.model.Model_Message_posWord.PosWord;

/**
 * @author jmh
 * nonwords	(e.g.,	castle -> ‘‘kaksel’’)
 *
 */
public class Anomic_error_nonword extends AC_Distortion_BubbleDown implements ChangeListener{

	String[] _consonants = {"b", "c", "d", "f", "g", "h", "i", "j", "k", "l", "m", "n", "p", 
			"q", "r", "s", "t", "v", "w","","sh","ch","br","gr"};
	String[] _vowels = {"a","e","i","o","u","y"};
	
	ArrayList<String> consonants;
	ArrayList<String> vowels;
	
	JaSpellWidget spell;
	
	Random random;
	
	model m;
	view v;
	
	public Anomic_error_nonword() {
		super("Non Word", true, false, false, false);
		m = new model();
		v = new view();
		
		spell = new JaSpellWidget();
		
		random = new Random();
		consonants =  new ArrayList(Arrays.asList(_consonants));

		vowels =new ArrayList(Arrays.asList(_vowels));
		
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
		
		if(!posWord.isDistorted() && posWord.pos_isContentWord()){
			boolean createdNonWord = false;
			String finalWord = "";
			String rawWord = posWord.getTokenAsWord();
			
			int attempts = 0;
			
			while(createdNonWord && attempts<10){
				finalWord = createNonWord(rawWord);	
				if(spell.isRealWord(finalWord))
					createdNonWord = true;
				attempts++;
			}
			posWord.setDistorted("NONWORD_ERROR", finalWord);
		}		
	}
	
	private String createNonWord(String rawWord){
		
		
		int changes = random.nextInt(rawWord.length()-1)+1;
		ArrayList<Integer> changed = new ArrayList<Integer>();
		
		char[] charArr = rawWord.toCharArray();
		String[] strArr = new String[charArr.length];
		for(int i=0; i<charArr.length; i++)
			strArr[i] = charArr[i]+"";
		
		while(changed.size()<changes){
			int toChange = random.nextInt(rawWord.length());
			Integer iToChange = new Integer(toChange);
			if(!changed.contains(iToChange)){
				String c = strArr[toChange];
				ArrayList<String> choiceArray;
				if(vowels.contains(c))
					choiceArray = vowels;
				else
					choiceArray = consonants;
				int toReplace = random.nextInt(choiceArray.size());
				if(!c.equalsIgnoreCase(choiceArray.get(toReplace))){
					//check to see if it is a real word!
					changed.add(iToChange);
					strArr[toChange]= choiceArray.get(toReplace);
				}					
			}				
		}
		String finalWord = "";
		for(String s:strArr)
			finalWord+=s;
		return finalWord;
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
			slider_localValue.addChangeListener(Anomic_error_nonword.this);
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