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
public class Model_Message {
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
	
	Document dom = null;
	
	public Model_Message(String message, ArrayList<StringObject> tokens, ArrayList<StringObject> tags){
		//InputStream in = getClass().getResourceAsStream("/modelEditor/openNLP/tag.bin.gz");
		//Dictionary dict = new Dictionary(in);
		this.init(message, tokens, tags);
		
	}
	public Model_Message(String message){
		
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
		
		
		this.init_Dom();
		
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
	
	private void init_Dom(){
		//get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();
	
			//create an instance of DOM
			dom = db.newDocument();
			
		}catch(ParserConfigurationException pce) {
			//dump it
			System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
			System.exit(1);
		}
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
			message_element.appendChild(makeWordElement("OriginalMessage",originalMessage));
			
			for(int i=0; i<_words.size();i++){
				message_element.appendChild(_words.get(i).getXML());
			}
			
		}
		
		return message_element;
	}
	private Element makeWordElement(String elementName, String elementContent){
		Element word_element = dom.createElement(elementName);
		Text word_text = dom.createTextNode(elementContent);
		word_element.appendChild(word_text);		
		return word_element;
	}
	
	

	
	
	private class fullWord{
		String oWord;
		ArrayList<PosWord> posWords;
		
		public fullWord(String oWord, ArrayList<PosWord> posWords){
			//System.out.println(oWord+":   ("+tokens+")  ("+tags+")");
			this.oWord = oWord;
			this.posWords = posWords;
		}
	
		public Element getXML(){
			Element word_element = dom.createElement("Word");
			
			/*
			for(int i=0; i<originalWords.length;i++){
				Element word_element = dom.createElement("Word");
				
				word_element.appendChild(makeWordElement("OriginalWord",originalWords[i]));
				word_element.appendChild(makeWordElement("DistortedWord",distoredWords[i]));
				word_element.appendChild(makeWordElement("IsDistored",isDistorted[i]+""));
				word_element.appendChild(makeWordElement("Distortion",distortion[i]+""));
				word_element.appendChild(makeWordElement("PartOfSpeech",partOfSpeech[i]));
				word_element.appendChild(makeWordElement("NewMessageAfterWord",newMessageAfterWord[i]+""));
				
				message_element.appendChild(word_element);
			}
			*/
			
			return word_element;
		}
	}
	
}
