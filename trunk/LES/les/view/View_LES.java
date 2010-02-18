package les.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import les.controller.Controller_LES;


public class View_LES {

	JFrame loginFrame;
	JPanel loginPanel;
	
	TextEntry userName;
	TextEntry password;
	TextEntry subjectID;
	JButton connect;
	JButton localChat;
	JButton startModelEditor;;
	
	Controller_LES controller;
	
	public View_LES(Controller_LES parent) {
		controller = parent;
		
		loginFrame = new JFrame();
		loginFrame.setSize(400, 300);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		
		loginFrame.add(loginPanel);
		
		userName = new TextEntry("User Name:");
		loginPanel.add(userName);
		
		
		password = new TextEntry("Password:");
		loginPanel.add(password);
		
		subjectID = new TextEntry("Subject ID (optional):");
		loginPanel.add(subjectID);
		
		connect = new JButton("Connect");
		connect.addActionListener(controller);
		loginPanel.add(connect);
		connect.setEnabled(false);
		
		localChat = new JButton("Local Chat");
		localChat.addActionListener(controller);
		loginPanel.add(localChat);
		
		startModelEditor= new JButton("Model Editor");
		startModelEditor.addActionListener(controller);
		loginPanel.add(startModelEditor);
		
		loginFrame.setVisible(true);
	}
	
	
	private class TextEntry extends JPanel{
		JLabel label = new JLabel();
		JTextField entry = new JTextField(20);
		
		public TextEntry(String entryLabel){
			super();
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			label.setText(entryLabel);
			
			this.entry.addKeyListener(controller);
			
			this.add(label);
			this.add(entry);
			
			
		}
		public String getEnteredText(){
			return entry.getText();
		}
		public boolean isEmpty(){
			if(entry.getText().length()==0)
				return true;
			return false;
		}
	}
	
	public boolean isLocalChatButton(ActionEvent e){
		if(e.getSource() == localChat)
			return true;
		return false;
	}
	
	public boolean isConnectButton(ActionEvent e){
		if(e.getSource() == connect)
			return true;
		return false;
	}
	public boolean isModelEditorButton(ActionEvent e){
		if(e.getSource() == startModelEditor)
			return true;
		
		return false;
	}
	public String getScreenName() {
		return userName.getEnteredText();
	}
	public String getPassword() {
		return password.getEnteredText();
	}
	public String getSubjectID() {
		return subjectID.getEnteredText();
	}

	public void updateUI() {
		if(!userName.isEmpty() && !password.isEmpty())
			connect.setEnabled(true);
		else
			connect.setEnabled(false);
		
	}
	

}
