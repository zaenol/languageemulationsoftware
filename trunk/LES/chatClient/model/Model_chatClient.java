package chatClient.model;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import chatClient.Controller_chatClient;
import chatClient.model.jbuddy.CmdLineClientDemo;

import com.zion.jbuddy.*;
import com.zion.jbuddy.filetransfer.*;
import com.zion.jbuddy.conference.*;

public class Model_chatClient extends CmdLineClientDemo{

	private String screenName = "";
	private String password = "";

	Controller_chatClient controller;
	
	boolean localChat = false;

	public Model_chatClient(Controller_chatClient parent,String screenName, String password, boolean localChat){
		super(getProtocol("AIM"),screenName,password,localChat);
		this.localChat = localChat;
		controller = parent;
		this.setPassword(password);
		this.setScreenName(screenName);
		if(localChat && screenName.length()==0)
			this.setScreenName("ME");
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isOnline(){
		if(localChat)
			return true;
		
		return client.isOnline();
	}
	
	public boolean startIMConversation() throws InterruptedException{
		if(localChat)
			return this.startIMConversation_local();
		else
			return this.startIMConversation_online();
		
		
	}
	
	private boolean startIMConversation_local(){
		IBuddy localBuddy = new myBuddy();
		IBuddy[] localBuddies = {localBuddy};
		controller.updateBuddies(localBuddies);
		
		return true;
	}
	private boolean startIMConversation_online() throws InterruptedException{
		if (!connect()) {
		    System.err.println("Error connecting to server");
		    //System.exit(1);
		    return false;
		}
		
		while (client.isOnline() == false) {
		    System.out.print(".");
		    Thread.sleep(1000); 
		}

		System.out.println("Connected!");
		
		IBuddyList list = client.getBuddyList();
		IBuddy[] buddies = list.getBuddies();
		controller.updateBuddies(buddies);
		
		Thread thread = new chatListener();
		thread.start();
		
		return true;
	}
	
	
	public void quitConnection(){
		if(!localChat){
			isUserQuit = true;
	    	client.disconnect();
		}
	}
	
	public void outgoing_sendIMMessage(String userName, String message) {
		if (!localChat)
			try {

				String finalMessage = message;

				if (client.sendIM(userName, finalMessage) == false)
					System.err.println("Failed to send message");

				// lastIMRecipient = userName;
			} catch (NoSuchElementException nsee) {
				System.err.println("Usage: im <userName> <message>");
			} catch (IOException e) {
				System.err.println("IO Exception; sendIMMessage\n"
						+ e.getMessage() + e.getStackTrace());
			}
	}
	
	public void outgoing_broadcastIM(List<String> userNames,String message){
		if (!localChat)
			try {

				if (!userNames.isEmpty() && message != null) {
					Iterator userNameIterator = userNames.iterator();

					while (userNameIterator.hasNext()) {
						String userName = (String) userNameIterator.next();

						if (client.sendIM(userName, message) == false)
							System.err.println("Failed to send message to "
									+ userName);
					}

					// continue; // success
				}
			} catch (NoSuchElementException nsee) {
			} catch (IOException e) {
				System.err.println("IO Exception; broadcastIM\n"
						+ e.getMessage() + e.getStackTrace());
			}

		// System.err.println("Usage: broadcast <userName1>...<userNameN> msg: <message>");
	}
	
	public void incoming_IM(IMessage message){
		controller.incomingMessage(message);
	}
	
	class chatListener extends Thread {
		
		long lastTime = 0;
		
		public chatListener(){
			
		}
		
	    // This method is called when the thread runs
	    public void run() {
	    	lastTime = System.currentTimeMillis();
	    	while (client.isOnline() && !isUserQuit) {
	    		long currentTime = System.currentTimeMillis();
	    		if(currentTime-lastTime > 60000*5){
	    			
	    			IBuddyList list = client.getBuddyList();
	    			IBuddy[] buddies = list.getBuddies();
	    			controller.updateBuddies(buddies);
	    			
	    			lastTime = System.currentTimeMillis();
	    		}
	    		
	    	}
	    }
	}
	
	class myBuddy implements IBuddy{


		public String getCustomAwayMessage() {
			// TODO Auto-generated method stub
			return "Custom Away Message";
		}


		public String getGroup() {
			// TODO Auto-generated method stub
			return "Custom Group";
		}


		public int getIdleTime() {
			// TODO Auto-generated method stub
			return 0;
		}


		public String getName() {
			// TODO Auto-generated method stub
			return "Local Buddy";
		}


		public String getNickName() {
			// TODO Auto-generated method stub
			return "Local Buddy";
		}


		public String getOwner() {
			// TODO Auto-generated method stub
			return "Local Buddy";
		}


		public Properties getProperties() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getProperty(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}


		public int getProtocol() {
			// TODO Auto-generated method stub
			return 0;
		}

		public String getProtocolName() {
			// TODO Auto-generated method stub
			return "Local Buddy";
		}


		public Date getSignOnTimeStamp() {
			// TODO Auto-generated method stub
			return null;
		}


		public int getStatus() {
			// TODO Auto-generated method stub
			return 0;
		}


		public String getStatusAsString() {
			// TODO Auto-generated method stub
			return null;
		}


		public Date getStatusTimeStamp() {
			// TODO Auto-generated method stub
			return null;
		}


		public int getWarningPercent() {
			// TODO Auto-generated method stub
			return 0;
		}


		public boolean isMobile() {
			// TODO Auto-generated method stub
			return false;
		}
		
		public String toString(){
			return "Local Buddy";
		}
		
	}
	
	public void printMessage(IMessage message) {
		String prefix = null;
		
		switch (message.getType()) {
		case IMessage.IM:
		    prefix = "IM";
		    //outgoing_sendIMMessage(message.getSender(),"Message Recieved");
		    incoming_IM(message);
		    break;
		case IMessage.IM_OFFLINE:
		    prefix = "Offline Message";
		    break;
		case IMessage.AUTO_RESPONSE:
		    prefix = "Auto Response";
		    break;
		case IMessage.TYPING:
		    prefix = "Typing Indicator";
		    break;
		case IMessage.TYPING_OFF:
		    prefix = "Typing Indicator OFF";
		    break;
		case IMessage.AUTH_REQUEST:
		    prefix = "Authorization Request";
		    break;
		case IMessage.AUTH_ACCEPT:
		    prefix = "Authorization Accepted";
		    break;
		case IMessage.AUTH_DECLINE:
		    prefix = "Authorization Declined";
		    break;
		case IMessage.ERROR:
		    prefix = "Error Message";
		    break;
		case IMessage.WARNING:
		    prefix = "Warning Message";
		    break;
		default:
		    prefix = "Unknown Message";
		}
		
		System.out.print("< ");
		
		if (prefix != null)
		    System.out.print(prefix + " ");
		    
		System.out.print("from " + message.getSender());
		
		String body = message.getMessage();
		
		if (body != null && body.length() != 0)
		    System.out.print(": " + body);
		
		System.out.println();
	}

}
