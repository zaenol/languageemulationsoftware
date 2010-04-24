package modelEditor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;

import NLP.BooleanObject;
import NLP.StringObject;

public class Model_Message_posWord extends Model_Message_GlobalFunctions{
	boolean debug= true;
	
	protected String[] functionWordArray = { "the", "of", "and", "to", "a",
			"in", "that", "is", "was", "he", "for", "it", "with", "as", "his",
			"on", "be", "at", "by", "i", "this", "had", "not", "are", "but",
			"from", "or", "have", "an", "they", "which", "one", "you", "were",
			"her", "all", "she", "there", "would", "their", "we", "him",
			"been", "has", "when", "who", "will", "more", "no", "if", "out",
			"so", "said", "what", "up", "its", "about", "into", "than", "them",
			"can", "only", "other", "new", "some", "could", "time", "these",
			"two", "may", "then", "do", "first", "any", "my", "now", "such",
			"like", "our", "over", "man", "me", "even", "most", "made",
			"after", "also", "did", "many", "before", "must", "through",
			"back", "years", "where", "much", "your", "way", "well", "down",
			"should", "because", "each", "just", "those", "people", "mr",
			"how", "too", "little", "state", "good", "very", "make", "world",
			"still", "own", "see", "men", "work", "long", "get", "here",
			"between", "both", "life", "being", "under", "never", "day",
			"same", "another", "know", "while", "last", "might", "us", "great",
			"old", "year", "off", "come", "since", "against", "go", "came",
			"right", "used", "take", "three" };
	protected ArrayList<String> functionWordList = new ArrayList(Arrays.asList(functionWordArray));

	public PosWord makePosWord(StringObject tokens, StringObject tags){
		final PosWord pw = new PosWord(tokens,tags);
		//String pw = "";
		return pw;
	}
	
	public class PosWord{
		StringObject token;
		StringObject tag;
		StringObject distortedWord;
		StringObject distortionType;
		BooleanObject distorted;
		BooleanObject newMessageAfterWord;
		
		public PosWord(StringObject tokens, StringObject tags){
			this.token = tokens;
			this.tag = tags;
			this.distortedWord = new StringObject(""); 
			this.distortionType = new StringObject("");
			this.distorted = new BooleanObject(false);
			this.newMessageAfterWord = new BooleanObject(false);
			
			if(!pos_isKnown()){
				System.out.println("UNKNOWN:  "+tokens+"  -  "+tags);
			}
		}
		
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
		
		
		public void setDistorted(String dType, String dWord){
			if(debug)
				System.out.println("> "+dType+": "+this.getToken()+"("+this.getTokenAsWord()+") -> "+dWord);
			distorted.setValue(true);
			distortionType.setValue(dType);
			distortedWord.setValue(dWord);
		}
		public void setPause(){
			newMessageAfterWord.setValue(true);
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
		public String getTokenAsWord(){
			//http://en.wiktionary.org/wiki/Category:English_contractions
			//http://www.textfixer.com/resources/english-contractions-list.php
			if(token.equals("'t"))
				return "not";
			if(token.equals("'ll"))
				return "will";
			if(token.equals("wo"))
				return "will";
			if(token.equals("'d"))
				return "had";
			if(token.equals("n't"))
				return "not";
			if(token.equals("ai"))
				return "am";
			if(token.equals("ca"))
				return "can";
			if(token.equals("'m"))
				return "am";
			if(token.equals("'s") && !this.pos_isPossessiveEnding())
				return "is/us";
			if(token.equals("'ve"))
				return "have";
				
			
			return token.getValue().toLowerCase();		
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
	
		public String toString(){
			return "<"+this.getToken()+" - "+this.getTokenAsWord()+">";
		}
	
	
		public Element getXML(){
			
				Element word_element = dom.createElement("POSWord");
				
				word_element.appendChild(makeElementWithTextBody("OriginalWord",token));
				
				Element posElement = dom.createElement("PartOfSpeech");
					setElementAttribute(posElement, "value", tag);
					posElement.appendChild(makeElementWithTextBody("OtherOrPuncturation",pos_isUnclassified()));
					posElement.appendChild(makeElementWithTextBody("FunctionWord",pos_isFunctionWord()));
					posElement.appendChild(makeElementWithTextBody("ContentWord",pos_getContentWordType()));
				word_element.appendChild(posElement);
				
				word_element.appendChild(makeElementWithTextBody("NewMessageAfterWord",newMessageAfterWord));
				
				Element distortedElement = dom.createElement("IsDistored");
				
				if(distorted.getValue()){
					distortedElement.appendChild(makeElementWithTextBody("DistortedWord",distortedWord));
					distortedElement.appendChild(makeElementWithTextBody("DistortionType",distortionType));
				}else{
					distortedElement = makeElementWithTextBody("IsDistored","null");
				}
				
				setElementAttribute(distortedElement, "value", distorted);
				word_element.appendChild(distortedElement);
				
				//message_element.appendChild(word_element);
				return word_element;
			
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
			if(tag.equals("CD")) // Cardinal Number
				return true;
			if(pos_isPunctuation())
				return true;
			return false;
		}
		
		public boolean pos_isPossessiveEnding(){
			if(tag.equals("POS")) //Possessive ending
				return true;
			return false;
		}
		
		public boolean pos_isPunctuation(){
			if(tag.equals(".")) //Ending punctuation
				return true;
			if(tag.equals(",")) //Comma
				return true;
			if(tag.equals("$")) //$
				return true;
			if(tag.equals("-LRB-")) //(
				return true;
			if(tag.equals("-RRB-")) //)
				return true;
			if(tag.equals(":")) //- ; :
				return true;
			if(tag.equals("''")) //
				return true;
			if(tag.equals("SYM")) //Symbol
				return true;
			return false;
		}
		
		/*
		 * CONTENT WORDS
		 */
		
		public boolean pos_isContentWord(){
			
			if(!this.pos_isFunctionWord())
				if(pos_isVerb()||pos_isNoun()||pos_isAdj()||pos_isAdVerb())
					return true;
			
			return false;
		}
		
		public String pos_getContentWordType(){
			
			if(pos_isVerb())
				return "Verb";
			if(pos_isNoun())
				return "Noun";
			if(pos_isAdj())
				return "Adjective";
			if(pos_isAdVerb())
				return "Adverb";
				
			
			return false+"";
		}
				
		
		public boolean pos_isVerb(){
			if(!pos_isFunctionWord()){
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
			}
			
			return false;
		}
		public boolean pos_isNoun(){
			if(!pos_isFunctionWord()){
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
			}
			return false;
		}
		
		public boolean pos_isPluralNoun(){
			
			if(tag.equals("NNS")) //Noun, plural
				return true;
			if(tag.equals("NNPS")) //Proper noun, plural
				return true;
			return false;
		}
		
		public boolean pos_isNounNotProperNoun(){
			if(!pos_isFunctionWord()){
				if(tag.equals("NN")) //Noun, singular or mass
					return true;
				if(tag.equals("NNS")) //Noun, plural
					return true;
			}
			return false;
		}
		public boolean pos_isProperNoun(){
			if(!pos_isFunctionWord()){
				if(tag.equals("NPS")) //Proper noun, plural
					return true;
				if(tag.equals("NNP")) //Proper noun, singular
					return true;
				if(tag.equals("NNPS")) //Proper noun, plural
					return true;
			}
			return false;
		}
		
		public boolean pos_isAdj(){
			if(!pos_isFunctionWord()){
				if(tag.equals("JJ")) //Adjective
					return true;
				if(tag.equals("JJR")) //Adjective, comparative
					return true;
				if(tag.equals("JJS")) //Adjective, superlative
					return true;
			}
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
			if(functionWordList.contains(this.getTokenAsWord()))
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
}
