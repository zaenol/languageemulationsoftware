package chatClient.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Date;

import java.text.DateFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.zion.jbuddy.IBuddy;

import chatClient.Controller_chatClient;

public class View_chatClient {

	JFrame chatWindow;
	
	JComboBox buddyList;
	
	JPanel fullPanel;
	
	JTextPane conversation;
	JScrollPane conversation_ScrollPane;
	StyledDocument doc;
	
	
	JPanel inputPanel;
	JButton sendButton;
	//JTextField inputMessage;
	JTextArea inputMessage;
	JScrollPane inputMessage_ScrollPane;
	
	private int clientWidth = 500;
	private int clientHeight = 450;
	private int clientBorder = 20;
	
	private int clientMesageTextAreaHeight = clientHeight*4/5;
	private int clientMesageTextAreaWidth = clientWidth-(2*clientBorder);
	
	private final static String newline = "\n";
	private final static String tab = "\t";
	
	Controller_chatClient controller;
	
	String noBuddyText = "Select Buddy to IM with";
	
	//IBuddy[] myBuddies;
	
	public View_chatClient(Controller_chatClient parent) {

		controller = parent;
		
		chatWindow = new JFrame("Chat Window");

		chatWindow.setSize(clientWidth, clientHeight);
		chatWindow.setResizable(false);
		chatWindow.addWindowListener(controller);
		
		fullPanel = new JPanel();
		fullPanel.setLayout(new BoxLayout(fullPanel, BoxLayout.Y_AXIS));
		fullPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
		
		
		buddyList = new JComboBox();
		buddyList.setMaximumSize(new Dimension(clientMesageTextAreaWidth,70));
		buddyList.addItemListener(controller);
		resetBuddyList();
		
		/* FOR MORE INFO ABOUT JTEXTPANE AND CREATING STYLIZED TEXT PLEASE SEE
		 * 
		 * http://java.sun.com/docs/books/tutorial/uiswing/examples/components/TextSamplerDemoProject/src/components/TextSamplerDemo.java
		 * http://java.sun.com/docs/books/tutorial/uiswing/components/editorpane.html#editorpane
		 * 
		 */
		conversation =  new JTextPane();
		doc = conversation.getStyledDocument();
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		Style regular = doc.addStyle("regular", def);
		
		 Style r = doc.addStyle("red", regular);
	     StyleConstants.setForeground(r, Color.RED);
	     
	     Style b = doc.addStyle("blue", regular);
	     StyleConstants.setForeground(b, Color.BLUE);
		
		//conversation.setWrapStyleWord(true);
		//conversation.setLineWrap(true);
		
		conversation.setEditable(false);
		conversation_ScrollPane = new JScrollPane(conversation, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        conversation_ScrollPane.setMaximumSize(new Dimension(clientMesageTextAreaWidth, clientMesageTextAreaHeight));
        
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
        
        inputMessage = new JTextArea(2,20);
        inputMessage.setMaximumSize(new Dimension(clientMesageTextAreaWidth-40, clientBorder*3));
        inputMessage.setWrapStyleWord(true);
        inputMessage.setLineWrap(true);
        inputMessage.addKeyListener(this.controller);
        //inputMessage.addActionListener(controller);
        //inputMessage.getDocument().addDocumentListener(controller);
        //inputMessage.addKeyListener(l)
        
        inputMessage_ScrollPane = new JScrollPane(inputMessage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inputMessage_ScrollPane.setMaximumSize(new Dimension(clientMesageTextAreaWidth-40, clientBorder*3));
        
        
        sendButton = new JButton("Send");
        sendButton.setMnemonic(KeyEvent.VK_ENTER);
        sendButton.addActionListener(this.controller);
        
        inputPanel.add(inputMessage_ScrollPane);
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
			return noBuddyText;
		
		String currentBuddy = ((BuddyWrapper) buddyList.getSelectedItem()).getName();
		return currentBuddy;
	}
	public boolean selectedBuddyIsReal(){
		if(this.getSelectedBuddyName().equals(noBuddyText))
			return false;
		return true;
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
		/*if(outgoing)
			conversation.setForeground(Color.RED);
		else
			conversation.setForeground(Color.BLUE);
		conversation.append(from+" ("+timestamp+"):");
		conversation.setForeground(Color.BLACK);
		conversation.append(""+tab+message + newline);
		conversation_ScrollPane.revalidate();
		*/
		String color = "red";
		if(!outgoing)
			color = "blue";
		
		String[] initString = {from+" ("+timestamp+"):",tab+message + newline};
		String[] initStyles = {color,"regular"};
		
		for (int i=0; i < initString.length; i++) {
			try {
				doc.insertString(doc.getLength(), initString[i],
						 doc.getStyle(initStyles[i]));
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		//conversation.revalidate();
		conversation.select(doc.getLength(),doc.getLength());
		//conversation_ScrollPane.revalidate();
		//conversation.inse.setText(conversation.getText()+"<div><font color=\""+color+"\">"+from+" ("+timestamp+"):\t</font>"+message+"</div><br>");
		
		//JLabel theLabel = new JLabel("<html><div><font color=\""+color+"\">"+from+" ("+timestamp+"):\t</font>"+message+"</div><br>");
		//conversation.add(theLabel);
		
	}

	public void updateUsability(){
		if(buddyList.getSelectedIndex()>0){
			inputPanel.setEnabled(true);
			sendButton.setEnabled(true);
			inputMessage.setEditable(true);
		}else{
			inputPanel.setEnabled(false);
			sendButton.setEnabled(false);
			inputMessage.setEditable(false);
		}
			
		
	}
	
	public void updateBuddies(IBuddy[] buddies) {
		System.out.println("Update Buddies");
		
		String currentBuddy = getSelectedBuddyName();
		
		resetBuddyList();
		
		//myBuddies = buddies;
		
		for(IBuddy buddy:buddies){
			BuddyWrapper myBuddy = new BuddyWrapper(buddy);
			
			//if(myBuddy.getStatus() != IBuddy.OFFLINE){
			
				buddyList.addItem(myBuddy);
				if(buddy.getName().equals(currentBuddy))
					buddyList.setSelectedItem(myBuddy);
			
			//}
		}
		
	}
	private class BuddyWrapper{
		private IBuddy _buddy;
		public BuddyWrapper(IBuddy buddy){
			_buddy = buddy;
		}
		
		public String getName(){
			return _buddy.getName();
		}
		public IBuddy getBuddy(){
			return _buddy;
		}
		
		public String toString(){
			String s = _buddy.getName();
			
			s +=" ("+this.getStatusName(getStatus())+")";
			
			return s;			
		}
		public int getStatus(){
			return _buddy.getStatus();
		}
	    public String getStatusName(int status) {
	    	switch (status) {
	    	case IBuddy.ATLUNCH:
	    	    return "ATLUNCH";
	    	case IBuddy.AWAY:
	    	    return "AWAY";
	    	case IBuddy.BRB:
	    	    return "BRB";
	    	case IBuddy.BUSY:
	    	    return "BUSY";
	    	case IBuddy.CUSTOM_AWAY:
	    	    return "CUSTOM_AWAY";
	    	case IBuddy.DND:
	    	    return "DND";
	    	case IBuddy.FREE_TO_CHAT:
	    	    return "FREE_TO_CHAT";
	    	case IBuddy.IDLE:
	    	    return "IDLE";
	    	case IBuddy.INVISIBLE:
	    	    return "INVISIBLE";
	    	case IBuddy.NA:
	    	    return "NA";
	    	case IBuddy.NOT_AT_DESK:
	    	    return "NOT_AT_DESK";
	    	case IBuddy.NOT_AT_HOME:
	    	    return "NOT_AT_HOME";
	    	case IBuddy.NOT_IN_OFFICE:
	    	    return "NOT_IN_OFFICE";
	    	case IBuddy.OCCUPIED:
	    	    return "OCCUPIED";
	    	case IBuddy.OFFLINE:
	    	    return "OFFLINE";
	    	case IBuddy.ON_VACATION:
	    	    return "ON_VACATION";
	    	case IBuddy.ONLINE:
	    	    return "ONLINE";
	    	case IBuddy.ONPHONE:
	    	    return "ONPHONE";
	    	case IBuddy.STEPPED_OUT:
	    	    return "STEPPED_OUT";
	    	default:
	    	    return "UNKNOWN";
	    	}   
	        }
	}
}
