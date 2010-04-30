package modelEditor.distortions;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.distortions.conceptNet.ConceptuallyRelatedIndex;
import modelEditor.distortions.stemmingSnowball.ext.englishStemmer;
import modelEditor.model.Model_Message;
import modelEditor.model.Model_Message_posWord.PosWord;


/**
 * @author jmh
 * elbow -> ��knee��;	orange -> ��apple��
 *
 */
public class Anomic_error_semantic extends AC_Distortion_BubbleDown implements ChangeListener{

	model m;
	view v;
	ConceptuallyRelatedIndex cri;
	
	englishStemmer stemmer;
	
	public Anomic_error_semantic() {
		super("Semantic", true, false, false, false);
		m = new model();
		v = new view();
		
		cri = new ConceptuallyRelatedIndex();
		
		stemmer = new englishStemmer();
		
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
		if(posWord.pos_isContentWord() && !posWord.isDistorted()){
			String theWord = posWord.getTokenAsWord();
			
			if(posWord.pos_isPluralNoun()){
				String twoEnding = theWord.substring(theWord.length()-2);
				String oneEnding = theWord.substring(theWord.length()-1);
				// if(theWord.substring(theWord.length()-2))
				
				stemmer.setCurrent(posWord.getTokenAsWord());
				stemmer.stem();
				theWord = stemmer.getCurrent();
				
				if(!cri.containsWord(theWord)){
					theWord = posWord.getTokenAsWord();
				}
				
			}else{
				
				
				if(!cri.containsWord(theWord)){
					
					stemmer.setCurrent(posWord.getTokenAsWord());
					stemmer.stem();
					theWord = stemmer.getCurrent();
					if(!cri.containsWord(theWord)){
						theWord = posWord.getTokenAsWord();
					}
				}
				
			}
			posWord.setDistorted("SEMANTIC ERROR", cri.getConceptuallyRelatedTo(theWord));
			
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
			d_errorProbability = -0.2653*Math.pow(x,2) + 0.2134*x + 0.0577;
			if(d_errorProbability<0 || getSeverityValue_local()==100)
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
			slider_localValue.addChangeListener(Anomic_error_semantic.this);
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