package controller;

import chatClient.Controller_chatClient;

import com.zion.jbuddy.IMessage;

import modelEditor.Controller_modelEditor;
import modelEditor.model.Model_Message;

public class Controller_LES {
	
	Controller_chatClient chatClient;
	Controller_modelEditor modelEditor;
	
	public Controller_LES() throws InterruptedException{
		chatClient = new Controller_chatClient();
		modelEditor = new Controller_modelEditor();
	}
	
	public Model_Message distortMessage(Model_Message message){	
		return message;
	}
	
	public void log_incomingMessage(IMessage message){
		
	}
	public void log_outgoingMessage(Model_Message message){
		
	}

}
