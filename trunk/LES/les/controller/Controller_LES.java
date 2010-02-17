package les.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
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

import chatClient.Controller_chatClient;

import com.zion.jbuddy.IMessage;

import les.view.View_LES;
import modelEditor.Controller_modelEditor;
import modelEditor.model.Model_Message;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class Controller_LES implements ActionListener {
	
	Controller_chatClient chatClient;
	Controller_modelEditor modelEditor;
	View_LES loginScreen;
	
	Document dom = null;
	
	String fileName = "";
	
	String myName = "LocalChat";
	
	Element rootEle = null;
	
	public Controller_LES() throws InterruptedException{
				
		loginScreen = new View_LES(this);
		chatClient = new Controller_chatClient();
		modelEditor = new Controller_modelEditor();
		
		this.initDom();
		
		rootEle = dom.createElement("Conversation");
		dom.appendChild(rootEle);
		
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
		return message;
	}
	
	public void log_incomingMessage(IMessage message){
		String[] messageArray = {message.getMessage()};
		Element element = this.generateMessageXML(messageArray, message.getSender(), message.getRecipient(), "Incoming", null);
	}
	public void log_outgoingMessage(Model_Message message,String sender,String recipient){
		//Element element = this.generateMessageXML(sentMessage, sender, recipient, messageDirection, additionalInformation);
		Element element = this.generateMessageXML(message.getMessageToSend(), sender, recipient, "Outgoing", message.getXML());
	}

	private void addAndPrint(Element message){
		rootEle.appendChild(message);
		try
		{
			//print
			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);

			//to generate output to console use this serializer
			//XMLSerializer serializer = new XMLSerializer(System.out, format);

			String home = FileSystemView.getFileSystemView().getHomeDirectory().toString();
			
			String sep = File.separator;
			
			//to generate a file output use fileoutputstream instead of system.out
			XMLSerializer serializer = new XMLSerializer(
			new FileOutputStream(new File(home+sep+"Desktop"+sep+fileName+".xml")), format);

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
			fileName = loginScreen.getSubjectID()+"_"+now.toString();
			if(screenName.length()>0 && password.length()>0){
				boolean started = chatClient.connect(screenName, password);
				
				if(started)
					chatClient.setVisible(true);
				else
					chatClient.setVisible(false);	
			}
		}else if(loginScreen.isModelEditorButton(e)){
			modelEditor.setVisible(true);
		}
			
		
	}

	private Element generateMessageXML(String[] sentMessage,String sender, String recipient,String messageDirection,Element additionalInformation){
		Element message_element=null;
		
		Date now = new Date();
		
		if(dom==null)
			this.initDom();
		
		if(dom!=null){
			message_element = dom.createElement("Message");
			
			String sentMessageString = "[";
			if(sentMessage.length>0)
				sentMessageString+=sentMessage[0];
			for(int i=1; i<sentMessage.length;i++)
				sentMessageString+=sentMessage[i];
			sentMessageString+="]";
			
			message_element.setAttribute("content", sentMessageString);
			message_element.appendChild(this.makeWordElement("Date",DateFormat.getInstance().format(now)));
			message_element.appendChild(this.makeWordElement("Sender", sender));
			message_element.appendChild(this.makeWordElement("Recipient", recipient));
			message_element.appendChild(this.makeWordElement("Direction",messageDirection));
			message_element.appendChild(this.makeWordElement("Content",sentMessageString));
			if(additionalInformation!=null)
				message_element.appendChild(additionalInformation);
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
