package chatClient.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
	
	public void startIMConversation(){
		
		if (!connect()) {
		    System.err.println("Error connecting to server");
		    System.exit(1);
		}
		
	}

}
