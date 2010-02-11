package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View_LES;
import chatClient.Controller_chatClient;

import com.zion.jbuddy.IMessage;

import modelEditor.Controller_modelEditor;
import modelEditor.model.Model_Message;

public class Controller_LES implements ActionListener {
	
	Controller_chatClient chatClient;
	Controller_modelEditor modelEditor;
	View_LES loginScreen;
	
	public Controller_LES() throws InterruptedException{
		loginScreen = new View_LES(this);
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


	public void actionPerformed(ActionEvent e) {
		if(loginScreen.isConnectButton(e)){
			String screenName = loginScreen.getScreenName();
			String password = loginScreen.getPassword();
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

}
