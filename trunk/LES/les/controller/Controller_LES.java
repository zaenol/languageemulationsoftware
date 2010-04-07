package les.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.Node;

import chatClient.Controller_chatClient;

import com.zion.jbuddy.IMessage;

import les.view.View_LES;
import modelEditor.Controller_modelEditor;
import modelEditor.model.Model_Message;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import html.*;

public class Controller_LES implements ActionListener, KeyListener {
	
	Controller_chatClient chatClient;
	Controller_modelEditor modelEditor;
	View_LES loginScreen;
	
	Document dom = null;
	
	Tag body = null;
	Tag html_title = null;
	
	String fileName = "";
	
	String myName = "LocalSelf";
	
	Element rootEle = null;
	
	boolean localChat = false;
	
	public Controller_LES() throws InterruptedException{
			
				
		loginScreen = new View_LES(this);
		
		init();	
		
		modelEditor = new Controller_modelEditor();

		
		
		
	}
	
	private void init() throws InterruptedException{
		loginScreen.setLoading(true);
		
		loginScreen.updateUI();
		chatClient = new Controller_chatClient();
		chatClient.setParent(this);
		
		this.initDom();
		this.initHTML();
		
		rootEle = dom.createElement("Conversation");
		dom.appendChild(rootEle);
		
		while(chatClient == null || !chatClient.isLoaded()){
			
		}
		loginScreen.setLoading(false);
	}
	
	private void initHTML(){
		body = new Tag("body");
		Tag head = new Tag("head");
		html_title = new Tag("title");
		html_title.add("Conversation");
		head.add(html_title);
		body.add(head);
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
	
	public Model_Message distortMessage(Model_Message message){	
		return modelEditor.distortMessage(message);
	}
	
	public void log_incomingMessage(IMessage message){
		String[] messageArray = {message.getMessage()};

		this.log_message(message.getMessage(),messageArray, message.getSender(), myName, "Incoming","blue", null);
	}
	public void log_outgoingMessage(Model_Message message,String sender,String recipient){

		
		this.log_message(message.getMessageToTransmit_toString(),message.getMessageToTransmit(), myName, recipient, "Outgoing", "red", message.getXML());
	}
	private void log_message(String message,String[] subMessages, String sender,String recipient,String direction,String color,Element additionalInformation){
		Date now = new Date();
		
		Element xml_element = this.generateMessageXML(message,subMessages, sender, recipient, direction,now, additionalInformation);
		this.addAndPrint_XML(xml_element);
		
		for(String s:subMessages){
			Tag html_element = this.generateMessageHTML(s, sender, now, color);
			this.addAndPrint_HTML(html_element);
		}
		
	}

	private void addAndPrint_HTML(Tag html_message){
		String home = FileSystemView.getFileSystemView().getHomeDirectory().toString();
		
		String sep = File.separator;
		
		/*
		 * HTML PRINTING AND ADDING
		 */
		try{
			body.add(html_message);
			//body.add(new Tag("br", false));
			Tag html = new Tag("html");
			html.add(body);
			File output = new File(home+sep+"Desktop"+sep+fileName+".html");
			FileWriter fstream = new FileWriter(output);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(html.toString());
			out.close();			
		} catch(IOException ie) {
		    ie.printStackTrace();
		}
	}
	
	private void addAndPrint_XML(Element xml_message){
		String home = FileSystemView.getFileSystemView().getHomeDirectory().toString();
		
		String sep = File.separator;
		
		/*
		 * XML PRINTING AND ADDING
		 */
		rootEle.appendChild(xml_message);
		try
		{
			//print
			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);

			//to generate output to console use this serializer
			//XMLSerializer serializer = new XMLSerializer(System.out, format);

			
			
			File output = new File(home+sep+"Desktop"+sep+fileName+".xml");
			
			//to generate a file output use fileoutputstream instead of system.out
			XMLSerializer serializer = new XMLSerializer(
			new FileOutputStream(output), format);

			serializer.serialize(dom);

		} catch(IOException ie) {
		    ie.printStackTrace();
		}
		
		
	}

	public void actionPerformed(ActionEvent e) {
		if(loginScreen.isConnectButton(e) || loginScreen.isLocalChatButton(e)){
			
			String screenName = loginScreen.getScreenName();
			String password = loginScreen.getPassword();
			String subjectID = loginScreen.getSubjectID();
			
			Date now = new Date();
			fileName = subjectID+"_"+now.toString();
			
			if(subjectID.length()==0){
				if(screenName.length()>0)
					myName = screenName;
			}else
				myName = subjectID;
			
			if(subjectID.length()==0)
				fileName = myName+"_"+now.toString();
			
			html_title.set(0, fileName);
			Tag h1 = new Tag("h1");
			h1.add(myName+" Conversation on "+now.toString());
			body.add(h1);
			
			localChat = false;
			if(loginScreen.isLocalChatButton(e))
				localChat = true;
			

			if((screenName.length()>0 && password.length()>0 && !localChat) || localChat ){
				boolean started = chatClient.connect(screenName, password,localChat);
				
				if(started){
					chatClient.setVisible(true);
					loginScreen.disableUI();
				}else
					chatClient.setVisible(false);	
			}
					
		}else if(loginScreen.isModelEditorButton(e)){
			modelEditor.setVisible(true);
		}
			
		
	}
	
	private Tag generateMessageHTML(String sentMessage,String sender,Date now,String color){
		Tag br = new Tag("div");
		
		String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(now);
		
		Tag font = new Tag("font","color=\""+color+"\"");
		font.add(sender+" ("+time+"): ");
		br.add(font);
		br.add(sentMessage);
		return br;
	}

	private Element generateMessageXML(String sentMessage,String[] subMessages,String sender, String recipient,String messageDirection,Date now,Element additionalInformation){
		Element message_element=null;
		
		
		if(dom==null)
			this.initDom();
		
		if(dom!=null){
			message_element = dom.createElement("Message");
			
			String sentMessageString = sentMessage; 
				/*"[";
			if(sentMessage.length>0)
				sentMessageString+=sentMessage[0];
			for(int i=1; i<sentMessage.length;i++)
				sentMessageString+=" "+sentMessage[i];
			sentMessageString+="]";*/
			
			message_element.setAttribute("Direction", messageDirection);
			message_element.setAttribute("TransmittedMessage", sentMessageString);			
			
			message_element.appendChild(this.makeWordElement("Date",DateFormat.getInstance().format(now)));
			message_element.appendChild(this.makeWordElement("Sender", sender));
			message_element.appendChild(this.makeWordElement("Recipient", recipient));
			message_element.appendChild(this.makeWordElement("Direction",messageDirection));
			
			Element transmitted_element = dom.createElement("TransmittedMessage");
			for(int i=0; i<subMessages.length;i++){
				transmitted_element.appendChild(this.makeWordElement("Message"+i,subMessages[i]));
			}			
			message_element.appendChild(transmitted_element);		
			
			
			if(additionalInformation!=null){
				Node tempNode = dom.importNode(additionalInformation,true); //true if you want a deep copy
				//domYouAreAddingTheNodeTo.appendNode(tempNode);
				message_element.appendChild(tempNode);
				//message_element.appendChild(additionalInformation);
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

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		if(loginScreen != null)
			loginScreen.updateUI();
		
	}
	public void chatSessionClosed(){
		try {
			
		
			init();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public String getMyName(){
		return myName+"";
	}

}
