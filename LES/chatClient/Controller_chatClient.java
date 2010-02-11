package chatClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.zion.jbuddy.IBuddy;
import com.zion.jbuddy.IMessage;

import controller.Controller_LES;

import modelEditor.model.Model_Message;

import chatClient.model.Model_chatClient;
import chatClient.view.View_chatClient;

public class Controller_chatClient implements ActionListener {

	View_chatClient vcc;
	Model_chatClient mcc;
	Controller_LES parent = null;
	
	public Controller_chatClient() throws InterruptedException{
		vcc= new View_chatClient(this);
		
		//mcc.startIMConversation();
	}
	
	public boolean connect(String screenName,String password){
		
		if(mcc != null){
			mcc.quitConnection();
		}
		
		mcc= new Model_chatClient(this,screenName,password);
		
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
		
		if(mcc != null && mcc.isOnline()){
		
			String messageTyped = vcc.getMessageAndClear();
			String buddy = vcc.getSelectedBuddyName();
			Model_Message message = new Model_Message(messageTyped);
			
			if(parent != null)
				message = parent.distortMessage(message);
			
			if(parent != null)
				parent.log_outgoingMessage(message);
			
			
			for(String s_message:message.getMessageToSend()){
			
				if(!message.isPostOriginalMessage())
					vcc.postOutgoingMessage(mcc.getScreenName(), s_message);
				mcc.outgoing_sendIMMessage(buddy, s_message);
			}
			
			if(message.isPostOriginalMessage())
				vcc.postOutgoingMessage(mcc.getScreenName(), message.getOriginalMessage());

		}
		
	}
	
	public void incomingMessage(IMessage message){
		vcc.postIncomingMessage(message.getSender(), message.getMessage());
	}



	public void updateBuddies(IBuddy[] buddies) {
		vcc.updateBuddies(buddies);
		
	}
	public void setVisible(boolean visible){
		vcc.setVisible(visible);
	}
	
}
