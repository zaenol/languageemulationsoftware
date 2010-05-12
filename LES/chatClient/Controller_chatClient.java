package chatClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

public class Controller_chatClient implements ActionListener, ItemListener, WindowListener, DocumentListener, KeyListener {

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
		String _messageTyped = messageTyped.replace("\r", "").replace("\n", "");
		ArrayList<ArrayList<StringObject>> opennlp = nlp.runNLP(_messageTyped);
		
		ArrayList<StringObject> tokens = opennlp.get(0);
		ArrayList<StringObject> tags = opennlp.get(1);
		
		Model_Message message = new Model_Message(_messageTyped,tokens,tags);
		
		if(parent != null)
			message = parent.distortMessage(message);
		
		if(parent != null)
			parent.log_outgoingMessage(message, mcc.getScreenName(), buddy);
		
		String screenName = mcc.getScreenName();
		if(parent != null){
			screenName = parent.getMyName();
		}
		
		if(message.isPostOriginalMessage())
			vcc.postOutgoingMessage(screenName, message.getOriginalMessage());
		
		
		String[] messages = message.getMessageToTransmit();
		int messageCount = 0;
		
		for(String s_message:messages){
			System.out.println(message.isPostOriginalMessage());
			if(!message.isPostOriginalMessage())
				vcc.postOutgoingMessage(screenName, s_message);
			
			mcc.outgoing_sendIMMessage(buddy, s_message);
			
			
		}
		

	}
	
	private static void wait (int n){
        long t0,t1;
        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<(n*1000));
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


	//
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//
	public void windowClosing(WindowEvent e) {
		mcc.quitConnection();
		if(parent != null){
			parent.chatSessionClosed();
		}
		
	}


	//
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("change");
		
	}

	
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println("insert");
	}

	
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("remove");
	}

	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getKeyCode());
		//System.out.println(KeyEvent.VK_ENTER);
		
		
	}

	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if( (mcc != null && mcc.isOnline())){
				
				String messageTyped = vcc.getMessageAndClear();
				messageTyped = messageTyped.substring(0,messageTyped.length()-1);
				if(messageTyped.length()>0 && vcc.selectedBuddyIsReal() && containsNonSpaces(messageTyped)){
					String buddy = vcc.getSelectedBuddyName();
				
					outgoingMessage(buddy,messageTyped);
				}
			}
		}
	}

	private boolean containsNonSpaces(String msg){
		
		for(int i=0; i<msg.length();i++){
			char c = msg.charAt(i);
			String cs = c+"";
			if(!cs.equals(" "))
				return true;
		}
		
		return false;
		
	}
	
	public void keyTyped(KeyEvent e) {
		
		
		
	}
	

	
	
}
