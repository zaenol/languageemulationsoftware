package modelEditor.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import opennlp.maxent.MaxentModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import NLP.BooleanObject;
import NLP.StringObject;
import NLP.openNLP.openNLP;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.util.InvalidFormatException;
/**
 * @author jmh
 *
 */
public class Model_Message extends Model_Message_Parent {
	private String originalMessage="";
	
	/*
	private String[] originalWords = {};
	private String[] distoredWords = {};
	private boolean[]  isDistorted = {};
	private String[] partOfSpeech = {};
	private String[] distortion = {};
	private boolean[] newMessageAfterWord = {};
	*/
	private ArrayList<fullWord> _words;
	private ArrayList<PosWord> _tagWords;
	
	private ArrayList<StringObject> _tokens;
	private ArrayList<StringObject> _tags;
	private ArrayList<StringObject> _distortedWord;
	private ArrayList<BooleanObject> _isDistorted;
	private ArrayList<StringObject> _distortionType;
	private ArrayList<BooleanObject> _newMessageAfterWord;
		
	private boolean postOriginalMessage=false;
	
	
	
	public Model_Message(String message, ArrayList<StringObject> tokens, ArrayList<StringObject> tags){
		super();
		//InputStream in = getClass().getResourceAsStream("/modelEditor/openNLP/tag.bin.gz");
		//Dictionary dict = new Dictionary(in);
		this.init(message, tokens, tags);
		
	}
	public Model_Message(String message){
		super();
		
		System.out.println("Bad Model Message... forced local NLP!");

		openNLP onlp = new openNLP();
		ArrayList<ArrayList<StringObject>> opennlp = onlp.runNLP(message);
		
		ArrayList<StringObject> tokens = opennlp.get(0);
		ArrayList<StringObject> tags = opennlp.get(1);
		
		this.init(message, tokens, tags);
		
	}
	private void init(String message, ArrayList<StringObject> tokens, ArrayList<StringObject> tags){
		originalMessage = message+"";
		
		String[] originalWords = originalMessage.replace("  ", " ").split(" ");
		
		_words = new ArrayList<fullWord>();
		_tagWords = new ArrayList<PosWord>();
		
		_distortedWord = new ArrayList<StringObject>();
		_distortionType = new ArrayList<StringObject>();
		_isDistorted = new ArrayList<BooleanObject>();
		_newMessageAfterWord = new ArrayList<BooleanObject>();
		_tokens = tokens;
		_tags = tags;
		
		int tIndex = 0;
		
		for(int oIndex = 0; oIndex<originalWords.length; oIndex++){
			String oWord = originalWords[oIndex];
	
			
			ArrayList<PosWord> word_posWords = new ArrayList<PosWord>();
			
			if(tIndex>=tokens.size())
				System.out.println("problem");
			
			StringObject tWord = new StringObject(tokens.get(tIndex).getValue());
			word_posWords.add(this.init_addToArrays(tIndex,tokens,tags));
			
			tIndex++;
			
			while(!oWord.equals(tWord.getValue()) && tIndex<tokens.size()){
				
				tWord.append(tokens.get(tIndex));
				word_posWords.add(this.init_addToArrays(tIndex,tokens,tags));				
				tIndex++;
			}
			
			//make a word object
			fullWord word = new fullWord(oWord,word_posWords); 
			
			//add word object to list!
			_words.add(word);
		}
		
		
		init_Dom();
		
	}
	
	private PosWord init_addToArrays(int tIndex,ArrayList<StringObject> tokens, ArrayList<StringObject> tags){
		
		init_createEmptyElements();

		PosWord tagWord = new PosWord(tokens.get(tIndex),tags.get(tIndex),_distortedWord.get(tIndex),_distortionType.get(tIndex),_isDistorted.get(tIndex),_newMessageAfterWord.get(tIndex));
		_tagWords.add(tagWord);
		
		return tagWord;
	}
	
	
	private void init_createEmptyElements(){
		StringObject distortion = new StringObject("");
		StringObject distortionType = new StringObject("");
		BooleanObject isDistorted = new BooleanObject(false);
		BooleanObject newMessageAfterWord = new BooleanObject(false);
		
		_distortedWord.add(distortion);
		_distortionType.add(distortionType);
		_isDistorted.add(isDistorted);
		_newMessageAfterWord.add(newMessageAfterWord);
	}
	

	
	public String getOriginalMessage(){
		return originalMessage;
	}
	
	public String[] getMessageToTransmit(){
		String[] messages = {originalMessage};
		return messages;
	}
	/**
	 * This is used when wanting a consolodated version of the message to send but fits in one String.
	 * @return
	 */
	public String getMessageToTransmit_toString(){
		String s = ""+originalMessage;
		
		return s;
	}

	/**
	 * Should we post our original comment to the current user's screen.
	 * 
	 * @return
	 */
	public boolean isPostOriginalMessage() {
		return postOriginalMessage;
	}

	public void setPostOriginalMessage(boolean postOriginalMessage) {
		this.postOriginalMessage = postOriginalMessage;
	}
	public Element getXML(){
		Element message_element=null;
		
		if(dom==null)
			this.init_Dom();
		
		if(dom!=null){
			message_element = dom.createElement("Outgoing");
			message_element.appendChild(makeElementWithTextBody("OriginalMessage",originalMessage));
			
			for(int i=0; i<_words.size();i++){
				message_element.appendChild(_words.get(i).getXML());
			}
			
		}
		
		return message_element;
	}
	
	

	
	

	
	
	private class fullWord{
		String oWord;
		ArrayList<PosWord> posWords;
		
		public fullWord(String oWord, ArrayList<PosWord> posWords){
			//System.out.println(oWord+":   ("+tokens+")  ("+tags+")");
			this.oWord = oWord;
			this.posWords = posWords;
		}
		
		public String getWord_Original(){
			return oWord;
		}
		
		public boolean isNewMessageAfterFullWord(){
			return posWords.get(posWords.size()-1).isNewMessageAfterWord();
		}
		
		public ArrayList<String> getWord_PostDist(){
			ArrayList<String> finalWord = new ArrayList<String>();
			
			boolean untouched = true;
			for(int i=0; i<posWords.size() && untouched;i++){
				if(posWords.get(i).isDistorted() || posWords.get(i).isNewMessageAfterWord()){
					untouched = false;
					
					//if the last element has a new line, who cares... original word will suffice
					if(i== posWords.size()-1 && !posWords.get(i).isDistorted() && posWords.get(i).isNewMessageAfterWord())
						untouched = true;						
				}
					
			}
			
			
			if(untouched){
				finalWord.add(this.getWord_Original());
			}
			else{	
				
				String currentMessage = "";
				
				PosWord _posWord = null;
				
				for(int i=0; i<posWords.size();i++){
					PosWord posWord = posWords.get(i);
					
					if(_posWord == null){
						if(i==posWords.size()-1){//last element
							if(posWord.isDistorted())
								currentMessage += posWord.getDistortedWord();
							else
								currentMessage += posWord.getTokenAsWord();
						}else{//not last element
							if(posWord.pos_isUnclassified() || !posWord.pos_isKnown()){//punctuation and other
								currentMessage += posWord.getToken()+" ";
								
								if(posWord.isNewMessageAfterWord()){
									finalWord.add(currentMessage);
									currentMessage= "";
								}
								
							}else{//is a real word
								_posWord = posWord;
							}		
						}
						
					}else{//previous element exists!
						
						if(!_posWord.isDistorted() || !_posWord.isNewMessageAfterWord()){//previous word is NOT distorted NOR New line
							
							if(posWord.isDistorted()){//current word is distorted
								currentMessage+=_posWord.getTokenAsWord()+" "+posWord.getDistortedWord();
							}else{//current word is normal
								currentMessage+=_posWord.getToken()+posWord.getToken();
							}
							
						}else{//previous word IS distorted or has a new line
							if(_posWord.isNewMessageAfterWord()){// new line after prior word
								
								if(_posWord.isDistorted())//prior word is distorted
									currentMessage += _posWord.getDistortedWord();
								else//prior word is not distorted
									currentMessage += _posWord.getTokenAsWord();
								
								finalWord.add(currentMessage);
								currentMessage= "";
								
								if(posWord.isDistorted()){//current word is distorted
									currentMessage+=posWord.getDistortedWord();
								}else{//current word is normal
									currentMessage+=posWord.getTokenAsWord();
								}
								
							}else{//no new line after prior word, use a space... also prior is distorted
								
								if(posWord.isDistorted()){//current word is distorted
									currentMessage+=_posWord.getDistortedWord()+" "+posWord.getDistortedWord();
								}else{//current word is normal
									currentMessage+=_posWord.getDistortedWord()+" "+posWord.getTokenAsWord();
								}
								
							}
							
						}
						
						if(posWord.isNewMessageAfterWord()){
							finalWord.add(currentMessage);
							currentMessage= "";
						}
						
						_posWord = null;
					}						
					
				}
				
				if(currentMessage.length()!=0)
					finalWord.add(currentMessage);

			}
			
			return finalWord;
		}
	
		public Element getXML(){
			Element word_element = dom.createElement("FullWord");
			word_element.appendChild(makeElementWithTextBody("OriginalWord",getWord_Original()));
			word_element.appendChild(makeElementWithIndextedChildren("FinalWord",getWord_PostDist(),"Word"));
			
			for(int i=0; i<posWords.size();i++){
				word_element.appendChild(posWords.get(i).getXML());
			}
			
			
			return word_element;
		}
	}
	
}
