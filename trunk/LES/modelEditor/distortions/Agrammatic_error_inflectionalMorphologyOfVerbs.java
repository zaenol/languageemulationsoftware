package modelEditor.distortions;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_Independent;
import modelEditor.distortions.Agrammatic_error_functionWords.model;
import modelEditor.distortions.Agrammatic_error_functionWords.view;
import modelEditor.distortions.stemmingSnowball.ext.englishStemmer;
import modelEditor.model.Model_Message;
import modelEditor.model.Model_Message_posWord.PosWord;

public class Agrammatic_error_inflectionalMorphologyOfVerbs extends AC_Distortion_Independent  implements ChangeListener {

	englishStemmer stemmer;
	model m;
	view v;
	
	public Agrammatic_error_inflectionalMorphologyOfVerbs() {
		super("Inflectional Morphology of Verbs", false, true, false, false);
		m = new model();
		v = new view();
		stemmer = new englishStemmer();
		
		// TODO Auto-generated constructor stub
	}

	public Document getXML() {
		// TODO Auto-generated method stub
		return null;
	}

	public Model_Message parseMessage(Model_Message messages) {
		Random randomGen = new Random();
		Model_Message myMessages = messages;
		
		ArrayList<PosWord>words = myMessages.get_tagWords();
		
		for(PosWord word:words){
		
			
				double random = randomGen.nextDouble();
				
				if(random<Math.abs(getProbability()/100d))
							parseMessageWord(word);
			
		}					
			
		
		return myMessages;
	}
	

	
	public void parseMessageWord(PosWord posWord){
		if(posWord.pos_isVerb() && !posWord.isDistorted()){
			stemmer.setCurrent(posWord.getTokenAsWord());
			stemmer.stem();
		
			//System.out.println("STEM: "+posWord.getTokenAsWord()+" -> "+stemmer.getCurrent());
			posWord.setDistorted("INFLECTION_MORPHOLOGY_OF_VERBS", stemmer.getCurrent());
		}
		
		
	}


	public void setValuesFromXML_local(Document dom) {
		// TODO Auto-generated method stub

	}
	
	public double getProbability() {
		// TODO Auto-generated method stub
		return m.getOldValue();
	}
	
	public class view{
		JSlider slider;
		JLabel label;
		String labelS = "Probability of Inflection Morphology of Verbs ";
		
		public view(){
			label = new JLabel(labelS+"0%", JLabel.CENTER);
			label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
			bodyPanel.add(label);
			
			slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
			slider.setMajorTickSpacing(10);
			slider.setMinorTickSpacing(2);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.addChangeListener(Agrammatic_error_inflectionalMorphologyOfVerbs.this);
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
