package les.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import les.controller.Controller_LES;


public class View_LES {

	JFrame loginFrame;
	JPanel loginPanel;
	
	JTextField userName;
	JPasswordField password;
	JTextField subjectID;
	JButton connect;
	JButton localChat;
	JButton startModelEditor;
	
	JPanel loadingPanel;
	
	Controller_LES controller;
	
	public View_LES(Controller_LES parent) {
		controller = parent;
		
		loginFrame = new JFrame();
		loginFrame.setSize(400, 300);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(new BoxLayout(loginFrame.getContentPane(), BoxLayout.Y_AXIS));
		loginFrame.setResizable(false);
		
		loginPanel = new JPanel();
		//loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		
		loginPanel.setBorder(
	                BorderFactory.createCompoundBorder(
	                                BorderFactory.createTitledBorder("Language Emulation Software"),
	                                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		loginFrame.add(loginPanel);
		
		
		
		userName = new JTextField();
		userName.addKeyListener(controller);
		JLabel userName_Label = makeLabel(userName,"User Name:");
		//loginPanel.add(userName);
		

		password = new JPasswordField();
		password.addKeyListener(controller);
		JLabel password_Label = makeLabel(password,"Password:");


		
		subjectID = new JTextField();
		subjectID.addKeyListener(controller);
		JLabel subjectID_Label = makeLabel(subjectID,"Subject ID (optional):");
		
		connect = new JButton("Connect");
		connect.addActionListener(controller);
		JLabel connect_Label = makeLabel(connect,"Connect to AIM server:");
		//loginPanel.add(connect);
		connect.setEnabled(false);
		
		localChat = new JButton("Local Chat");
		localChat.addActionListener(controller);
		JLabel localChat_Label = makeLabel(localChat,"Local Chat for Testing");
		//loginPanel.add(localChat);
		
		startModelEditor= new JButton("Model Editor");
		startModelEditor.addActionListener(controller);
		JLabel startModelEditor_Label = makeLabel(startModelEditor,"Model Editor Interface:");
		//loginPanel.add(startModelEditor);
		
		 JLabel[] labels = {userName_Label,password_Label,subjectID_Label,new JLabel(" "),connect_Label,localChat_Label,startModelEditor_Label};
		 JComponent[] textFields = {userName,password,subjectID,new JSeparator(),connect,localChat,startModelEditor};
		 
		 GridBagLayout gridbag = new GridBagLayout();
	        GridBagConstraints c = new GridBagConstraints();
		
	        loginPanel.setLayout(gridbag);
	        
		 addLabelTextRows(labels, textFields, gridbag, loginPanel);
		 
		 c.gridwidth = GridBagConstraints.REMAINDER; //last
	        c.anchor = GridBagConstraints.WEST;
	        c.weightx = 1.0;
		
		
		/*
		 * 
		 * Loading Screen
		 * 
		 */
		
		JLabel imageLabel = new JLabel();
		loginPanel.setVisible(false);
		URL in = getClass().getResource("loading.gif");
		ImageIcon ii = new ImageIcon(in);
		
		imageLabel.setIcon(ii);
		
		loadingPanel = new JPanel();
		//loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.Y_AXIS));
		//imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		//imageLabel.setLocation(-40, 100);
		
		loadingPanel.add(imageLabel);
		loginFrame.add(loadingPanel);
		
		this.setLoading(true);
		
		loginFrame.setVisible(true);
	}
	
	private JLabel makeLabel(JComponent element, String label){
		JLabel myLabel = new JLabel(label);
		myLabel.setLabelFor(element);
		return myLabel;
	}
	
	private void addLabelTextRows(JLabel[] labels,
			JComponent[] textFields,
            GridBagLayout gridbag,
            Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;
		
		for (int i = 0; i < numLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
			c.fill = GridBagConstraints.NONE;      //reset to default
			c.weightx = 0.0;                       //reset to default
			container.add(labels[i], c);
			
			c.gridwidth = GridBagConstraints.REMAINDER;     //end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			container.add(textFields[i], c);
		}
	}
	
	public void setLoading(boolean loading){
		
		if(!loading){
			//loginFrame.removeAll();
			//loginFrame.add(loginPanel);
			loadingPanel.setVisible(false);
			loginPanel.setVisible(true);
			
		}else{
			
			loginPanel.setVisible(false);
			loadingPanel.setVisible(true);
			
			
			
			//loginFrame.removeAll();
			//JLabel load = new JLabel("Loading....");
			
			//loginFrame.add(load);
		}
		
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
		return userName.getText();//.getEnteredText();
	}
	public String getPassword() {
		//return password.getEnteredText();
		String pw="";
		for(char c:password.getPassword())
			pw+=c;
		return pw;
	}
	public String getSubjectID() {
		return subjectID.getText();//.getEnteredText();
	}

	public void updateUI() {
		if(userName.getText().length()>0 && password.getPassword().length>0)
			connect.setEnabled(true);
		else
			connect.setEnabled(false);
		localChat.setEnabled(true);
		
	}
	public void disableUI(){
		connect.setEnabled(false);
		localChat.setEnabled(false);
	}
	

}
