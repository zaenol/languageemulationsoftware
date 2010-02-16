package modelEditor.model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
/**
 * @author jmh
 *
 */
public class Model_Message {
	private String originalMessage="";
	
	private String[] originalWords = {};
	private String[] finalWords = {};
	private boolean[]  isDistorted = {};
	private String[] partOfSpeech = {};
	private String[] distortion = {};
	private boolean[] newMessageAfterWord = {};
		
	private boolean postOriginalMessage=false;
	
	Document dom = null;
	
	public Model_Message(String message){
		originalMessage = message+"";
		
		originalWords = originalMessage.split(" ");
		finalWords = originalMessage.split(" ");
		
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
	
	public String[] getMessageToSend(){
		String[] messages = {originalMessage};
		return messages;
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
			for(int i=0; i<originalWords.length;i++){
				Element word_element = dom.createElement("Word");
				
				word_element.appendChild(makeWordElement("Original Word",originalWords[i]));
				word_element.appendChild(makeWordElement("Final Word",finalWords[i]));
				word_element.appendChild(makeWordElement("Is Distored",isDistorted[i]+""));
				word_element.appendChild(makeWordElement("Distortion",distortion[i]+""));
				word_element.appendChild(makeWordElement("Part Of Speech",partOfSpeech[i]));
				word_element.appendChild(makeWordElement("New Message After Word",newMessageAfterWord[i]+""));
				
				message_element.appendChild(word_element);
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
}
