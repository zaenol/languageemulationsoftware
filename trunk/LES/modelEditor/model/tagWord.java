package modelEditor.model;

import NLP.BooleanObject;
import NLP.StringObject;

public class tagWord{
	StringObject tokens;
	StringObject tags;
	StringObject Distortions;
	StringObject DistortionTypes;
	BooleanObject IsDistorted;
	BooleanObject NewMessageAfterWord;
	
	public tagWord(StringObject tokens, StringObject tags, StringObject Distortions, StringObject DistortionTypes, BooleanObject IsDistorted, BooleanObject NewMessageAfterWord){
		this.tokens = tokens;
		this.tags = tags;
		this.Distortions = Distortions; 
		this.DistortionTypes = DistortionTypes;
		this.IsDistorted = IsDistorted;
		this.NewMessageAfterWord = NewMessageAfterWord;
		
		//if(!isKnown()){
			System.out.println(tokens+"  -  "+tags);
		//}
	}
	
	public boolean isKnown(){
		
		if(isUnclassified()||isPunctuation()||isContentWord()||isFunctionWord())
			return true;
		
		return false;
	}
	
	public boolean isUnclassified(){
		
		if(tags.equals("EX")) //Existential there
			return true;
		if(tags.equals("FW")) //Foreign Word
			return true;
		if(tags.equals("LS")) //List item marker
			return true;
		if(tags.equals("POS")) //Possessive ending
			return true;
		if(tags.equals("SYM")) //Symbol
			return true;
		if(tags.equals("CD")) // Cardinal Number
			return true;
		return false;
	}
	
	public boolean isPunctuation(){
		if(tags.equals(".")) //Ending punctuation
			return true;
		if(tags.equals(",")) //Comma
			return true;
		return false;
	}
	
	/*
	 * CONTENT WORDS
	 */
	
	public boolean isContentWord(){
		
		if(isVerb()||isNoun()||isAdj()||isAdVerb())
			return true;
		
		return false;
	}
			
	
	public boolean isVerb(){
		if(tags.equals("VB")) //Verb, base form
			return true;
		if(tags.equals("VBD")) //Verb, past tense
			return true;
		if(tags.equals("VBG")) //Verb, gerund or present participle
			return true;
		if(tags.equals("VBN")) //Verb, past participle
			return true;
		if(tags.equals("VBP")) //Verb, non-3rd person singular present
			return true;
		if(tags.equals("VBZ")) //Verb, 3rd person singular present
			return true;
		
		return false;
	}
	public boolean isNoun(){
		if(tags.equals("NN")) //Noun, singular or mass
			return true;
		if(tags.equals("NNS")) //Noun, plural
			return true;
		if(tags.equals("NP")) //Proper noun, singular
			return true;
		if(tags.equals("NPS")) //Proper noun, plural
			return true;
		
		return false;
	}		
	
	public boolean isAdj(){
		if(tags.equals("JJ")) //Adjective
			return true;
		if(tags.equals("JJR")) //Adjective, comparative
			return true;
		if(tags.equals("JJS")) //Adjective, superlative
			return true;
		
		return false;
	}
	public boolean isAdVerb(){
		if(tags.equals("RB")) //Adverb
			return true;
		if(tags.equals("RBR")) //Adverb, comparative
			return true;
		if(tags.equals("RBS")) //Adverb, superlative
			return true;
		return false;
	}

	
	/*
	 * FUNCTION WORDS
	 */
	
	public boolean isFunctionWord(){
		if(isProNoun()||isArticle()||isAdpositions()||isConjunction()||isAuxiliaryVerb()||isInterjection()||isParticles()||isExpletives()||isProSentences())
			return true;
		
		return false;
	}
	
	
	public boolean isArticle(){
		if(tags.equals("DT")) //Determiner
			return true;
		if(tags.equals("PDT")) //Predeterminer
			return true;
		return false;
	}
	public boolean isProNoun(){
		if(tags.equals("PP")) //Personal pronoun
			return true;
		if(tags.equals("PP$")) //Possessive pronoun
			return true;
		if(tags.equals("PRP")) //Personal Prounoun
			return true;
		if(tags.equals("PRP$")) //Possessive Personal Prounoun
			return true;
		return false;
	}
	public boolean isAdpositions(){
		if(tags.equals("TO")) //to
			return true;
		if(tags.equals("IN")) //Preposition or subordinating conjunction
			return true;
		return false;
	}
	public boolean isConjunction(){
		if(tags.equals("CC")) //Coordinating Conjunction
			return true;
		return false;
	}
	public boolean isAuxiliaryVerb(){
		if(tags.equals("MD")) //Modal Verbs
			return true;
		return false;
	}
	public boolean isInterjection(){
		if(tags.equals("UH")) //Interjection
			return true;
		if(tags.equals("WDT")) //Wh-determiner
			return true;
		if(tags.equals("WP")) //Wh-pronoun
			return true;
		if(tags.equals("WP$")) //Possessive wh-pronoun
			return true;
		if(tags.equals("WRB")) //wh-adverb
			return true;
		return false;
	}
	public boolean isParticles(){
		if(tags.equals("RP")) //Particle
			return true;
		return false;
	}
	public boolean isExpletives(){
		return false;
	}
	public boolean isProSentences(){
		return false;
	}
}
