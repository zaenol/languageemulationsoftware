package chatClient.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Date;

import java.text.DateFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.zion.jbuddy.IBuddy;

import chatClient.Controller_chatClient;

public class View_chatClient {

	JFrame chatWindow;
	
	JComboBox buddyList;
	
	JPanel fullPanel;
	
	JTextArea conversation;
	JScrollPane conversation_ScrollPane;
	
	JPanel inputPanel;
	JButton sendButton;
	JTextField inputMessage;
	
	private int clientWidth = 500;
	private int clientHeight = 350;
	private int clientBorder = 20;
	
	private int clientMesageTextAreaHeight = clientHeight*4/5;
	private int clientMesageTextAreaWidth = clientWidth-(2*clientBorder);
	
	private final static String newline = "\n";
	private final static String tab = "\t";
	
	Controller_chatClient controller;
	
	//IBuddy[] myBuddies;
	
	public View_chatClient(Controller_chatClient parent) {

		controller = parent;
		
		chatWindow = new JFrame("Chat Window");

		chatWindow.setSize(clientWidth, clientHeight);
		chatWindow.setResizable(false);
		
		fullPanel = new JPanel();
		fullPanel.setLayout(new BoxLayout(fullPanel, BoxLayout.Y_AXIS));
		fullPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
		
		
		buddyList = new JComboBox();
		buddyList.setMaximumSize(new Dimension(clientMesageTextAreaWidth,70));
		resetBuddyList();
		
		/* FOR MORE INFO ABOUT JTEXTPANE AND CREATING STYLIZED TEXT PLEASE SEE
		 * 
		 * http://java.sun.com/docs/books/tutorial/uiswing/examples/components/TextSamplerDemoProject/src/components/TextSamplerDemo.java
		 * http://java.sun.com/docs/books/tutorial/uiswing/components/editorpane.html#editorpane
		 * 
		 */
		conversation =  new JTextArea();
		conversation.setWrapStyleWord(true);
		conversation.setLineWrap(true);
		conversation.setEditable(false);
		conversation_ScrollPane = new JScrollPane(conversation, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        conversation_ScrollPane.setMaximumSize(new Dimension(clientMesageTextAreaWidth, clientMesageTextAreaHeight));
        
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
        
        inputMessage = new JTextField(20);
        inputMessage.setMaximumSize(new Dimension(clientMesageTextAreaWidth-40, clientBorder));
        //inputMessage.addKeyListener(this.controller);
        inputMessage.addActionListener(controller);
        
        sendButton = new JButton("Send");
        sendButton.setMnemonic(KeyEvent.VK_ENTER);
        sendButton.addActionListener(this.controller);
        
        inputPanel.add(inputMessage);
        inputPanel.add(Box.createRigidArea(new Dimension(5,0)));
        inputPanel.add(sendButton);
		
        
        fullPanel.add(buddyList);
        fullPanel.add(conversation_ScrollPane);        
        fullPanel.add(inputPanel);
        
        chatWindow.add(fullPanel, BorderLayout.CENTER);

	}
	
	public void setVisible(boolean visible){
		chatWindow.setVisible(visible);
	}
	
	private void resetBuddyList(){
		buddyList.removeAllItems();
		buddyList.insertItemAt("Select Buddy to IM with",0);
		buddyList.setSelectedIndex(0);
	}
	
	
	public String getMessageAndClear(){
		String message = inputMessage.getText();
		inputMessage.setText("");
		return message;
	}
	public String getSelectedBuddyName(){
		if(buddyList.getSelectedIndex() == 0)
			return "Select Buddy to IM with";
		
		String currentBuddy = ((IBuddy) buddyList.getSelectedItem()).getName();
		return currentBuddy;
	}
	
	public void postIncomingMessage(String from, String message){

    	this.postMessage(from, message,true,false);
	}
	
	public void postOutgoingMessage(String from,String message){
		this.postMessage(from, message,false,true);    	
	}
	private void postMessage(String from,String message,boolean incoming, boolean outgoing){
		Date now = new Date();
		String timestamp = DateFormat.getTimeInstance(DateFormat.SHORT).format(now);
		conversation.append(from+" ("+timestamp+"):"+tab+message + newline);
	}

	public void updateBuddies(IBuddy[] buddies) {
		String currentBuddy = getSelectedBuddyName();
		
		resetBuddyList();
		
		//myBuddies = buddies;
		
		for(IBuddy buddy:buddies){
			buddyList.addItem(buddy);
			if(buddy.getName().equals(currentBuddy))
				buddyList.setSelectedItem(buddy);
		}
		
	}
}
