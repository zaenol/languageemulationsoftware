package modelEditor.model;

import NLP.BooleanObject;
import NLP.StringObject;

public class PosWord{
	StringObject token;
	StringObject tag;
	StringObject distortedWord;
	StringObject distortionType;
	BooleanObject distorted;
	BooleanObject newMessageAfterWord;
	
	public PosWord(StringObject tokens, StringObject tags, StringObject distoredWord, StringObject DistortionTypes, BooleanObject IsDistorted, BooleanObject NewMessageAfterWord){
		this.token = tokens;
		this.tag = tags;
		this.distortedWord = distoredWord; 
		this.distortionType = DistortionTypes;
		this.distorted = IsDistorted;
		this.newMessageAfterWord = NewMessageAfterWord;
		
		if(!pos_isKnown()){
			System.out.println("UNKNOWN:  "+tokens+"  -  "+tags);
		}
	}
	
	
	public boolean isDistorted(){
		return distorted.getValue();
	}
	
	public boolean isNewMessageAfterWord(){
		return newMessageAfterWord.getValue();
	}
	
	public String getToken(){
		return token.getValue();
	}
	public String getTag(){
		return tag.getValue();
	}
	public String getDistortedWord(){
		return distortedWord.getValue();
	}
	
	public void setDistortedWord(String distortedWord) {
		this.distortedWord.setValue(distortedWord);
	}

	public void setDistortionType(String distortionType) {
		this.distortionType.setValue(distortionType);
	}

	public void setDistorted(boolean distorted) {
		this.distorted.setValue(distorted);
	}


	public void setNewMessageAfterWord(boolean newMessageAfterWord) {
		this.newMessageAfterWord.setValue(newMessageAfterWord);
	}





	public boolean pos_isKnown(){
		
		if(pos_isUnclassified()||pos_isPunctuation()||pos_isContentWord()||pos_isFunctionWord())
			return true;
		
		return false;
	}
	
	public boolean pos_isUnclassified(){
		
		if(tag.equals("EX")) //Existential there
			return true;
		if(tag.equals("FW")) //Foreign Word
			return true;
		if(tag.equals("LS")) //List item marker
			return true;
		if(tag.equals("POS")) //Possessive ending
			return true;
		if(tag.equals("SYM")) //Symbol
			return true;
		if(tag.equals("CD")) // Cardinal Number
			return true;
		return false;
	}
	
	public boolean pos_isPunctuation(){
		if(tag.equals(".")) //Ending punctuation
			return true;
		if(tag.equals(",")) //Comma
			return true;
		return false;
	}
	
	/*
	 * CONTENT WORDS
	 */
	
	public boolean pos_isContentWord(){
		
		if(pos_isVerb()||pos_isNoun()||pos_isAdj()||pos_isAdVerb())
			return true;
		
		return false;
	}
			
	
	public boolean pos_isVerb(){
		if(tag.equals("VB")) //Verb, base form
			return true;
		if(tag.equals("VBD")) //Verb, past tense
			return true;
		if(tag.equals("VBG")) //Verb, gerund or present participle
			return true;
		if(tag.equals("VBN")) //Verb, past participle
			return true;
		if(tag.equals("VBP")) //Verb, non-3rd person singular present
			return true;
		if(tag.equals("VBZ")) //Verb, 3rd person singular present
			return true;
		
		return false;
	}
	public boolean pos_isNoun(){
		if(tag.equals("NN")) //Noun, singular or mass
			return true;
		if(tag.equals("NNS")) //Noun, plural
			return true;
		if(tag.equals("NP")) //Proper noun, singular
			return true;
		if(tag.equals("NPS")) //Proper noun, plural
			return true;
		if(tag.equals("NNP")) //Proper noun, singular
			return true;
		if(tag.equals("NNPS")) //Proper noun, plural
			return true;
		return false;
	}		
	
	public boolean pos_isAdj(){
		if(tag.equals("JJ")) //Adjective
			return true;
		if(tag.equals("JJR")) //Adjective, comparative
			return true;
		if(tag.equals("JJS")) //Adjective, superlative
			return true;
		
		return false;
	}
	public boolean pos_isAdVerb(){
		if(tag.equals("RB")) //Adverb
			return true;
		if(tag.equals("RBR")) //Adverb, comparative
			return true;
		if(tag.equals("RBS")) //Adverb, superlative
			return true;
		return false;
	}

	
	/*
	 * FUNCTION WORDS
	 */
	
	public boolean pos_isFunctionWord(){
		if(pos_isProNoun()||pos_isArticle()||pos_isAdpositions()||pos_isConjunction()||pos_isAuxiliaryVerb()||pos_isInterjection()||pos_isParticles()||pos_isExpletives()||pos_isProSentences())
			return true;
		
		return false;
	}
	
	
	public boolean pos_isArticle(){
		if(tag.equals("DT")) //Determiner
			return true;
		if(tag.equals("PDT")) //Predeterminer
			return true;
		return false;
	}
	public boolean pos_isProNoun(){
		if(tag.equals("PP")) //Personal pronoun
			return true;
		if(tag.equals("PP$")) //Possessive pronoun
			return true;
		if(tag.equals("PRP")) //Personal Prounoun
			return true;
		if(tag.equals("PRP$")) //Possessive Personal Prounoun
			return true;
		return false;
	}
	public boolean pos_isAdpositions(){
		if(tag.equals("TO")) //to
			return true;
		if(tag.equals("IN")) //Preposition or subordinating conjunction
			return true;
		return false;
	}
	public boolean pos_isConjunction(){
		if(tag.equals("CC")) //Coordinating Conjunction
			return true;
		return false;
	}
	public boolean pos_isAuxiliaryVerb(){
		if(tag.equals("MD")) //Modal Verbs
			return true;
		return false;
	}
	public boolean pos_isInterjection(){
		if(tag.equals("UH")) //Interjection
			return true;
		if(tag.equals("WDT")) //Wh-determiner
			return true;
		if(tag.equals("WP")) //Wh-pronoun
			return true;
		if(tag.equals("WP$")) //Possessive wh-pronoun
			return true;
		if(tag.equals("WRB")) //wh-adverb
			return true;
		return false;
	}
	public boolean pos_isParticles(){
		if(tag.equals("RP")) //Particle
			return true;
		return false;
	}
	public boolean pos_isExpletives(){
		return false;
	}
	public boolean pos_isProSentences(){
		return false;
	}
}
