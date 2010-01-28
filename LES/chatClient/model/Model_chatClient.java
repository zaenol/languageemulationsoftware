package chatClient.model;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import chatClient.model.jbuddy.CmdLineClientDemo;

import com.zion.jbuddy.*;
import com.zion.jbuddy.filetransfer.*;
import com.zion.jbuddy.conference.*;

public class Model_chatClient extends CmdLineClientDemo{

	private String screenName = "";
	private String password = "";


	public Model_chatClient(String screenName, String password){
		super(getProtocol("AIM"),screenName,password);
		this.setPassword(password);
		this.setScreenName(screenName);
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
	
	public void startIMConversation() throws InterruptedException{
		
		if (!connect()) {
		    System.err.println("Error connecting to server");
		    System.exit(1);
		}
		
		while (client.isOnline() == false) {
		    System.out.print(".");
		    Thread.sleep(1000); 
		}

		System.out.println("Connected!");
		
		Thread thread = new chatListener();
		thread.start();
		
	}
	
	public void sendIMMessage(String userName, String message){
		 try {
             
         	if (client.sendIM(userName, message) == false)
         	    System.err.println("Failed to send message");

         		//lastIMRecipient = userName;
             }
             catch (NoSuchElementException nsee) {
            	 System.err.println("Usage: im <userName> <message>");
             }
             catch(IOException e){
            	 System.err.println("IO Exception; sendIMMessage\n"+e.getMessage()+e.getStackTrace());
             }
	}
	
	
	class chatListener extends Thread {
		
		public chatListener(){
			
		}
		
	    // This method is called when the thread runs
	    public void run() {
	    	while (client.isOnline() && !isUserQuit) {
	    		
	    	}
	    }
	}

}
