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
	private ArrayList<fullWord> words;
		
	private boolean postOriginalMessage=false;
	
	Document dom = null;
	
	public Model_Message(String message, ArrayList<String> tokens, ArrayList<String> tags){
		//InputStream in = getClass().getResourceAsStream("/modelEditor/openNLP/tag.bin.gz");
		//Dictionary dict = new Dictionary(in);
		this.init(message, tokens, tags);
		
	}
	public Model_Message(String message){
		
		System.out.println("Bad Model Message... forced local NLP!");

		openNLP onlp = new openNLP();
		ArrayList<ArrayList<String>> opennlp = onlp.runNLP(message);
		
		ArrayList<String> tokens = opennlp.get(0);
		ArrayList<String> tags = opennlp.get(1);
		
		this.init(message, tokens, tags);
		
	}
	private void init(String message, ArrayList<String> tokens, ArrayList<String> tags){
		originalMessage = message+"";
		
		String[] originalWords = originalMessage.split(" ");
		
		
		int tIndex = 0;
		
		for(int oIndex = 0; oIndex<originalWords.length; oIndex++){
			String oWord = originalWords[oIndex];
			String tWord = tokens.get(tIndex);
			
			ArrayList<String> _tokens = new ArrayList<String>();
			ArrayList<String> _tags = new ArrayList<String>();
			
			_tokens.add(tWord);			
			_tags.add(tags.get(tIndex));
			
			tIndex++;
			
			while(!oWord.equals(tWord)){
				
				tWord+=tokens.get(tIndex);
				_tokens.add(tokens.get(tIndex));
				_tags.add(tags.get(tIndex));
				
				tIndex++;
			}
			//make a word object
			fullWord word = new fullWord(oWord,_tokens,_tags); 
			
			//add word object to list!
			
		}		
		
		words = new ArrayList<fullWord>();
		/*
		originalWords = originalMessage.split(" ");
		distoredWords = originalMessage.split(" ");
		
		partOfSpeech = new String[originalWords.length];
		
		isDistorted = new boolean[originalWords.length];
		for(int i=0; i<isDistorted.length;i++)
			isDistorted[i] = false;
		
		newMessageAfterWord = new boolean[originalWords.length];
		for(int i=0; i<newMessageAfterWord.length;i++)
			newMessageAfterWord[i] = false;
		
		distortion = new String [originalWords.length];
		for(int i=0; i<distortion.length;i++)
			distortion[i] = "";
		*/
		
		this.initDom();
		
	}
	
	private void initDom(){
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
			this.initDom();
		
		if(dom!=null){
			message_element = dom.createElement("Outgoing");
			message_element.appendChild(makeWordElement("OriginalMessage",originalMessage));
			
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
		public fullWord(String oWord, ArrayList<String> tokens, ArrayList<String> tags){
			System.out.println(oWord+":   ("+tokens+")  ("+tags+")");
		}
	
		public Element getXML(){
			Element word_element = dom.createElement("Word");
			
			return word_element;
		}
	}
}
