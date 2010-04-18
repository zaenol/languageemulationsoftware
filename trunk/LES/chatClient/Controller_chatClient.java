package chatClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import NLP.Gen_NLP;
import NLP.StringObject;
import NLP.openNLP.openNLP;

import com.zion.jbuddy.IBuddy;
import com.zion.jbuddy.IMessage;


import les.controller.Controller_LES;
import modelEditor.model.Model_Message;

import chatClient.model.Model_chatClient;
import chatClient.view.View_chatClient;

public class Controller_chatClient implements ActionListener, ItemListener, WindowListener {

	View_chatClient vcc;
	Model_chatClient mcc;
	Controller_LES parent = null;
	boolean localChat = false;
	
	Gen_NLP nlp;
	boolean openNLP = true;
	
	
	public Controller_chatClient() throws InterruptedException{
		vcc= new View_chatClient(this);
		
		if(openNLP)
			nlp = new openNLP();
		
		//mcc.startIMConversation();
	}
	
	public boolean isLoaded(){
		if(nlp != null)
			if(nlp.isLoaded())
				return true;
		
		return false;
	}
	
	public boolean connect(String screenName,String password){
		return connect(screenName, password,false);
	}
	
	public boolean connect(String screenName,String password,boolean localChat){
		
		if(mcc != null){
			mcc.quitConnection();
		}
		
		mcc= new Model_chatClient(this,screenName,password,localChat);
		
		try {
			return mcc.startIMConversation();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
	}
	
	public void setParent(Controller_LES parent) {
		this.parent = parent;
	}



	public void actionPerformed(ActionEvent e) {
		
		if( (mcc != null && mcc.isOnline())){
		
			String messageTyped = vcc.getMessageAndClear();
			if(messageTyped.length()>0 && vcc.selectedBuddyIsReal()){
				String buddy = vcc.getSelectedBuddyName();
			
				outgoingMessage(buddy,messageTyped);
			}
		}
	}
	
	private void outgoingMessage(String buddy, String messageTyped){
		ArrayList<ArrayList<StringObject>> opennlp = nlp.runNLP(messageTyped);
		
		ArrayList<StringObject> tokens = opennlp.get(0);
		ArrayList<StringObject> tags = opennlp.get(1);
		
		Model_Message message = new Model_Message(messageTyped,tokens,tags);
		
		if(parent != null)
			message = parent.distortMessage(message);
		
		if(parent != null)
			parent.log_outgoingMessage(message, mcc.getScreenName(), buddy);
		
		String screenName = mcc.getScreenName();
		if(parent != null){
			screenName = parent.getMyName();
		}
		
		
		for(String s_message:message.getMessageToTransmit()){			
			if(!message.isPostOriginalMessage())
				vcc.postOutgoingMessage(screenName, s_message);
			
			mcc.outgoing_sendIMMessage(buddy, s_message);
		}
		
		if(message.isPostOriginalMessage())
			vcc.postOutgoingMessage(screenName, message.getOriginalMessage());
	}
	
	public void incomingMessage(IMessage message){
		if(parent != null)
			parent.log_incomingMessage(message);
		vcc.postIncomingMessage(message.getSender(), message.getMessage());		
		String senderName = message.getSender();
		if(!mcc.containsBuddy(senderName)){
			mcc.addTempBuddy(senderName);
		}
	}

	public void updateBuddies(IBuddy[] buddies) {
		vcc.updateBuddies(buddies);
		mcc.updateBuddies(buddies);
		
	}
	public void setVisible(boolean visible){
		vcc.setVisible(visible);
	}


	public void itemStateChanged(ItemEvent e) {
		if(vcc!=null)
			vcc.updateUsability();
		
	}


	//@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//@Override
	public void windowClosing(WindowEvent e) {
		mcc.quitConnection();
		if(parent != null){
			parent.chatSessionClosed();
		}
		
	}


	//@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	

	
	
}
