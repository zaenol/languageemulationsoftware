package chatClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.zion.jbuddy.IBuddy;
import com.zion.jbuddy.IMessage;


import les.controller.Controller_LES;
import modelEditor.model.Model_Message;

import chatClient.model.Model_chatClient;
import chatClient.view.View_chatClient;

public class Controller_chatClient implements ActionListener, ItemListener {

	View_chatClient vcc;
	Model_chatClient mcc;
	Controller_LES parent = null;
	boolean localChat = false;
	

	
	public Controller_chatClient() throws InterruptedException{
		vcc= new View_chatClient(this);
		
		//mcc.startIMConversation();
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
			String buddy = vcc.getSelectedBuddyName();
			
			outgoingMessage(buddy,messageTyped);
		}
	}
	
	private void outgoingMessage(String buddy, String messageTyped){
		Model_Message message = new Model_Message(messageTyped);
		
		if(parent != null)
			message = parent.distortMessage(message);
		
		if(parent != null)
			parent.log_outgoingMessage(message, mcc.getScreenName(), buddy);
		
		
		for(String s_message:message.getMessageToTransmit()){			
			if(!message.isPostOriginalMessage())
				vcc.postOutgoingMessage(mcc.getScreenName(), s_message);
			
			mcc.outgoing_sendIMMessage(buddy, s_message);
		}
		
		if(message.isPostOriginalMessage())
			vcc.postOutgoingMessage(mcc.getScreenName(), message.getOriginalMessage());
	}
	
	public void incomingMessage(IMessage message){
		if(parent != null)
			parent.log_incomingMessage(message);
		vcc.postIncomingMessage(message.getSender(), message.getMessage());		
	}

	public void updateBuddies(IBuddy[] buddies) {
		vcc.updateBuddies(buddies);
		
	}
	public void setVisible(boolean visible){
		vcc.setVisible(visible);
	}


	public void itemStateChanged(ItemEvent e) {
		if(vcc!=null)
			vcc.updateUsability();
		
	}
	

	
	
}
