package chatClient.model.jbuddy;

/*
 * (c) 2006 Zion Software, LLC. All Rights Reserved.
 */

import java.io.*;
import java.util.*;

import com.zion.jbuddy.*;
import com.zion.jbuddy.filetransfer.*;
import com.zion.jbuddy.conference.*;


/**
 * This demo shows how to use JBuddy's various features.  It creates and 
 * connects an IClient, accepting various commands from the command line.
 */
public class CmdLineClientDemo {
    
    int protocol;
    String clientName;
    protected IClient client;
    
    boolean useFirstLastNaming; // if 2 arguments are needed for login and buddy names (ie: "firstname lastname")
    public boolean isUserQuit; // prevents reconnecting in the Gateway.connectionLost() method
    
    protected IBuddyList buddyList;
    IBuddyList reverseList;
    IBuddyList denyList;
    IBuddyList permitList;
    
    Map conferences = Collections.synchronizedMap(new HashMap()); // key=String(id), value=IConference
    ConferenceServiceHandler conferenceServiceHandler = new ConferenceServiceHandler(this);
    int nextConferenceID = 1;

    Map fileSessions = Collections.synchronizedMap(new HashMap()); // key=String(id), value=IFileSession
    FileSessionInvitationHandler fileSessionInvitationHandler = new FileSessionInvitationHandler(this);
    int nextFileSessionID = 1;

    
    
    /**
     * Creates the demo using the specified protocol, client name, and password.
     */
    public CmdLineClientDemo(int protocol, String clientName, String password, boolean localChat) {
    	if(!localChat){
			this.protocol = protocol;
			this.clientName = clientName;
		
			// create a IGateway instance to handle callback events
			Gateway gateway = new Gateway(this);
			
			// now instantiate an IClient from the IClientFactory class.
			// pass the constructor:
			//   a reference to an IGateway,
			//   the name of the IM Server, 
			//   the user name, and 
			//   the password
			client = IClientFactory.factory(gateway, protocol, clientName, password);
		
			if (client == null) {
			    System.err.println("Client init error");
			    System.exit(1);
			}
		
			// SAMETIME ids have 2 parts (FirstName LastName)
			useFirstLastNaming = (protocol == IClient.SAMETIME);
		
			// JBuddy automatically handles the Buddy List, Reverse List,
			// Permit list, and Deny List, but references to these lists may be 
			// obtained in the following manner:
			buddyList = client.getBuddyList();
			permitList = client.getPermitList();
			denyList = client.getDenyList();
		        reverseList = null;
		        
		        if (protocol == IClient.JSC)
		            reverseList = ((IJscClient)client).getReverseList();
		        else if (protocol == IClient.MSN)
		            reverseList = ((IMsnClient)client).getReverseList();
		        
			// Optionally, name the lists
			buddyList.setName(clientName + "'s Buddy List");
			permitList.setName(clientName + "'s Permit List");
			denyList.setName(clientName + "'s Deny List");
		
		        if (reverseList != null)
		            reverseList.setName(clientName + "'s Reverse List");
		
		        if (isConferenceSupported()) {
		            // listen for incoming ConferenceService events
		            try {
		        	IConferenceService conferenceService = client.getConferenceService();
		        	conferenceService.addListener(conferenceServiceHandler);
		            }
		            catch (IOException ioe) {
		        	ioe.printStackTrace(); // shouldn't happen here
		            }
		        }
			
		        if (isFileTransferSupported()) {
		            // listen for incoming FileSession invitations
		            client.addFileSessionInvitationListener(fileSessionInvitationHandler);
		        }
    	}
    }


    /**
     * Returns the protocol constant for the specified name.
     */
    public static int getProtocol(String name) {
	name = name.toUpperCase();
	
        if (name.equals("JSC"))
            return IClient.JSC;
        if (name.equals("AIM"))
            return IClient.AIM;
        if (name.equals("ICQ"))
            return IClient.ICQ;
        if (name.equals("MSN"))
            return IClient.MSN;
        if (name.equals("YIM"))
            return IClient.YIM;
        if (name.equals("JABBER"))
            return IClient.JABBER;
        if (name.equals("LCS"))
            return IClient.LCS;
        if (name.equals("SAMETIME"))
            return IClient.SAMETIME;
        
        return -1;
    }

    
    /**
     * Returns the permit mode constant for the specified name.
     */
    public static int getPermitMode(String name) {
	name = name.toUpperCase();
	
	if (name.equals("DENY_ALL"))
	    return IBuddyList.DENY_ALL;
	if (name.equals("DENY_SOME"))
	    return IBuddyList.DENY_SOME;
	if (name.equals("PERMIT_ALL"))
	    return IBuddyList.PERMIT_ALL;
	if (name.equals("PERMIT_SOME"))
	    return IBuddyList.PERMIT_SOME;
	
	return -1;
    }

    /**
     * Returns the name for the specified permit mode constant.
     */
    public static String getPermitModeName(int mode) {
	switch (mode) {
	case IBuddyList.PERMIT_ALL:
	    return "PERMIT_ALL";
	case IBuddyList.PERMIT_SOME:
	    return "PERMIT_SOME";
	case IBuddyList.DENY_SOME:
	    return "DENY_SOME";
	case IBuddyList.DENY_ALL:
	    return "DENY_ALL";
	default:
	    return "UNKNOWN";
	}
    }

    
    /**
     * Returns the status constant for the specified name.
     */
    public static int getStatus(String name) {
	name = name.toUpperCase();
	
	if (name.equals("ATLUNCH"))
	    return IBuddy.ATLUNCH;
	if (name.equals("AWAY"))
	    return IBuddy.AWAY;
	if (name.equals("BRB"))
	    return IBuddy.BRB;
	if (name.equals("BUSY"))
	    return IBuddy.BUSY;
	if (name.equals("CUSTOM_AWAY"))
	    return IBuddy.CUSTOM_AWAY;
	if (name.equals("DND"))
	    return IBuddy.DND;
	if (name.equals("FREE_TO_CHAT"))
	    return IBuddy.FREE_TO_CHAT;
	if (name.equals("IDLE"))
	    return IBuddy.IDLE;
	if (name.equals("INVISIBLE"))
	    return IBuddy.INVISIBLE;
	if (name.equals("NA"))
	    return IBuddy.NA;
	if (name.equals("NOT_AT_DESK"))
	    return IBuddy.NOT_AT_DESK;
	if (name.equals("NOT_AT_HOME"))
	    return IBuddy.NOT_AT_HOME;
	if (name.equals("NOT_IN_OFFICE"))
	    return IBuddy.NOT_IN_OFFICE;
	if (name.equals("OCCUPIED"))
	    return IBuddy.OCCUPIED;
	if (name.equals("OFFLINE"))
	    return IBuddy.OFFLINE;
	if (name.equals("ON_VACATION"))
	    return IBuddy.ON_VACATION;
	if (name.equals("ONLINE"))
	    return IBuddy.ONLINE;
	if (name.equals("ONPHONE"))
	    return IBuddy.ONPHONE;
	if (name.equals("STEPPED_OUT"))
	    return IBuddy.STEPPED_OUT;
	
	return -1;
    }
    
    /**
     * Returns the name for the specified status constant.
     */
    public static String getStatusName(int status) {
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


    /**
     * Returns the text mode constant for the specified name.
     */
    public static int getTextMode(String name) {
	name = name.toUpperCase();
	
	if (name.equals("PLAIN_TEXT"))
	    return IClient.PLAIN_TEXT;
	if (name.equals("RICH_TEXT"))
	    return IClient.RICH_TEXT;
	
	return -1;
    }
    
    /**
     * Returns the name for the specified text mode constant.
     */
    public static String getTextModeName(int mode) {
	switch (mode) {
	case IClient.PLAIN_TEXT:
	    return "PLAIN_TEXT";
	case IClient.RICH_TEXT:
	    return "RICH_TEXT";
	default:
	    return "UNKNOWN";
	}
    }
    
    
    /**
     * Returns true if the protocol supports buddy auth requests. 
     */
    public boolean isBuddyAuthSupported() {
	switch (protocol) {
	case IClient.ICQ:
	case IClient.JABBER:
	case IClient.LCS:
	case IClient.MSN:
	case IClient.YIM:
	    return true;
	default:
	    return false;
	}
    }
    
    /**
     * Returns true if the protocol supports conferences. 
     */
    public boolean isConferenceSupported() {
	switch (protocol) {
	case IClient.AIM:
	case IClient.ICQ:
	case IClient.JABBER:
	case IClient.SAMETIME:
	case IClient.YIM:
	    return true;
	default:
	    return false;
	}
    }
    
    /**
     * Returns true if the protocol supports file transfer. 
     */
    public boolean isFileTransferSupported() {
	return (protocol != IClient.LCS);
    }
    
    /**
     * Returns true if the protocol supports permit lists. 
     */
    public boolean isPermitListSupported() {
	switch (protocol) {
	case IClient.AIM:
	case IClient.JABBER:
	case IClient.JSC:
	case IClient.LCS:
	case IClient.MSN:
	case IClient.SAMETIME:
	    return true;
	default:
	    return false;
	}
    }
    
    /**
     * Returns true if the protocol supports reverse lists. 
     */
    public boolean isReverseListSupported() {
	switch (protocol) {
	case IClient.JSC:
	case IClient.MSN:
	    return true;
	default:
	    return false;
	}
    }
    
    /**
     * Returns true if the protocol supports sending config to server. 
     */
    public boolean isSendConfigSupported() {
	switch (protocol) {
	case IClient.AIM:
	case IClient.ICQ:
	case IClient.JABBER:
	case IClient.LCS:
	    return true;
	default:
	    return false;
	}
    }


    /**
     * We attach an ID to conferences so we can refer to them later.
     */
    public synchronized String getNextConferenceID() {
	return String.valueOf(nextConferenceID++);
    }

    /**
     * We attach an ID to file sessions so we can refer to them later.
     */
    public synchronized String getNextFileSessionID() {
	return String.valueOf(nextFileSessionID++);
    }

    
    /**
     * Connect to the server.
     */
    protected boolean connect() {
	try {
	    System.out.print("Trying to connect to " + client.getProtocolName() + " as " + clientName);
	    return client.connect(); // Call this method to connect to the IM server
	}
	catch (IOException ioe) {
	    System.out.println();
	    System.err.println("An IOException occurred while trying to connect to " + client.getProtocolName() + ": " + ioe.getMessage());
	}
	return false;
    }

    protected void remindSendConfig() {
        if (isSendConfigSupported())
            System.out.println("Use 'sendconfig' to save your changes");
    }

    
    /**
     * Prints a message to the console.
     */
    public void printMessage(IMessage message) {
	String prefix = null;
	
	switch (message.getType()) {
	case IMessage.IM:
	    prefix = "IM";
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

    /**
     * Prints a list of valid commands based on the client that has been loaded.
     */
    public void printMenu() {
	System.out.println("--------------------------------------------------------------------------------");
	System.out.println("Here is a list of valid commands:");
	System.out.println("im <userName> <message>  - sends an Instant Message");
	System.out.println("x <message>  - sends an Instant Message to the last user you IMed");
	System.out.println("broadcast <userName1>...<userNameN> msg: <message>  - sends an Instant Message to multiple users");
	System.out.println("status [[status] [message]]  - shows or sets your status and status message (omit parameters to show)");
        
	if (isBuddyAuthSupported()) {
	    System.out.println("acceptauth <userName>  - accepts an auth request");
	    System.out.println("declineauth <userName>  - declines an auth request");
	}
	
	if (isConferenceSupported()) {
	    System.out.println("acceptconference <id>  - accepts a conference invitation");
	    System.out.println("declineconference <id>  - declines a conference invitation");
	    System.out.println("leaveconference <id>  - leaves a conference");
	}
	
	if (isFileTransferSupported()) {
	    System.out.println("sendfile <userName> <path>  - sends a file invitation");
	    System.out.println("acceptfile <id>  - accepts a file invitation");
	    System.out.println("declinefile  - declines a file invitation");
	}
	
	System.out.println("bl  - shows your Buddy List");
	System.out.println("ab <userName> <nickName> <groupName>  - adds a user to your Buddy List");
	System.out.println("rb <userName> [groupName]  - removes a user from your Buddy List (omit groupName to remove from all groups)");
	
        System.out.println("dl  - shows your Deny List");
        System.out.println("ad <userName>  - adds a user to your Deny List");
        System.out.println("rd <userName>  - removes a user from your Deny List");

        if (isPermitListSupported()) {
            System.out.println("pl  - shows your Permit List");
            System.out.println("ap <userName>  - adds a user to your Permit List");
            System.out.println("rp <userName>  - removes a user from your Permit List");
	}

	if (isReverseListSupported()) {
	    System.out.println("rl  - shows your Reverse List (users who are watching you)");
	}

	System.out.println("permitmode [mode]  - shows or sets your permit mode (omit parameter to show)");
	
	if (isSendConfigSupported()) {
	    System.out.println("sendconfig  - saves permit/Buddy List changes to the server");
	}
	
	System.out.println("textmode [mode]  - shows or sets your text mode (omit parameter to show)");
	System.out.println("quit  - quits CmdLineClientDemo");
	System.out.println("--------------------------------------------------------------------------------");
    }
    
    /**
     * prints full status chart of current buddy list
     */ 
    public void showList(IBuddyList buddyList) {
        if (buddyList.getListType() == IBuddyList.BUDDY_LIST) {
            // iterate through each group and display the buddy status
            Enumeration groupNames = buddyList.getListOfGroups();
            
            if (!groupNames.hasMoreElements()) {
        	System.err.println("The list is empty");
            }
            else {
        	System.out.println("--------------------------------------------------------------------------------");
        	System.out.println(buddyList.getName());
    
        	while (groupNames.hasMoreElements()) {
        	    String groupName = (String)groupNames.nextElement();
    
        	    System.out.println(groupName);
    
        	    Enumeration buddies = buddyList.getBuddiesInGroup(groupName);
        	    while (buddies.hasMoreElements()) {
        		IBuddy buddy = (IBuddy)buddies.nextElement();
        		String buddyName = buddy.getName();
    
        		System.out.print("    " + buddyName + " ");
    
        		// padding
        		for (int i = buddyName.length(); i < 30; i++)
        		    System.out.print(".");
    
        		System.out.print(" " + getStatusName(buddy.getStatus()));
    
        		String statusMessage = buddy.getCustomAwayMessage();
        		if (statusMessage != null && statusMessage.length() != 0)
        		    System.out.print(" (" + statusMessage + ")");
    
        		System.out.println();
        	    }
        	}
        	
        	System.out.println("--------------------------------------------------------------------------------");
            }
        }
        else {
            // other list types (DL, PL, RL) don't care about group names
            IBuddy[] buddies = buddyList.getBuddies();
            
            if (buddies.length == 0) {
        	System.err.println("The list is empty");
            }
            else {
        	System.out.println("--------------------------------------------------------------------------------");
        	System.out.println(buddyList.getName());
    
        	for (int i = 0; i < buddies.length; i++) {
        	    IBuddy buddy = buddies[i];
        	
        	    System.out.println("    " + buddy.getName());
        	}
    
        	System.out.println("--------------------------------------------------------------------------------");
            }
        }
    }


    public void run() throws InterruptedException {
	// Connect to the server
	if (!connect()) {
	    System.err.println("Error connecting to server");
	    System.exit(1);
	}
        
	// at this point, we have a socket connection to the IM service, 
	// however handshaking between the client and server are still underway
        
        // when client.isOnline() returns true, we are truly ready
	while (client.isOnline() == false) {
	    System.out.print(".");
	    Thread.sleep(1000); 
	}

	System.out.println("Connected!");
	
	printMenu();

	// Read from the console
	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	
	String lastIMRecipient = null;
	String cmd = null;
	
	// Loop for as long as the user is online
	while (client.isOnline() && !isUserQuit) {
	    try {
		System.out.print("Enter cmd >");
		String input = bufferedReader.readLine();
		
		if (input == null)
		    continue;
		
		// Parse the user input
		StringTokenizer tokenizer = new StringTokenizer(input.trim());
		
		// no command entered
		if (!tokenizer.hasMoreTokens()) {
		    // print the usage menu and get more input
		    printMenu();
		    continue;
		}

		// the name of the command
		cmd = tokenizer.nextToken().toLowerCase();

		// ********** Process the user command *********
		
		// sends an Instant Message
                if (cmd.equals("im")) {
                    try {
                	String userName = tokenizer.nextToken();
                    
                	if (useFirstLastNaming)
                	    userName += (" " + tokenizer.nextToken());
                	
                	String message = tokenizer.nextToken("").trim();
                    
                	if (client.sendIM(userName, message) == false)
                	    System.err.println("Failed to send message");

                	lastIMRecipient = userName;
                    }
                    catch (NoSuchElementException nsee) {
                	System.err.println("Usage: im <userName> <message>");
                    }
                }
		// sends an Instant Message to the last user you IMed
                else if (cmd.equals("x")) {
		    if (lastIMRecipient == null) {
			System.err.println("Use the 'im' command at least once before using 'x'");
		    }
		    else {
			try {
			    String message = tokenizer.nextToken("").trim();

			    if (client.sendIM(lastIMRecipient, message) == false)
				System.err.println("Failed to send message");
			}
			catch (NoSuchElementException nsee) {
			    System.err.println("Usage: x <message>");
			}
		    }
		}
		// sends an Instant Message to multiple users
	        else if (cmd.equals("broadcast")) {
	            try {
	        	List userNames = new LinkedList();
	        	String message = null;
	            
	        	while (tokenizer.hasMoreTokens()) {
	        	    String token = tokenizer.nextToken();
	        	    
	        	    if (token.equalsIgnoreCase("msg:")) {
	        		// everything after msg: is the message
	        		message = tokenizer.nextToken("").trim();
	        		break;
	        	    }
	        	    else {
	        		String userName = token;
	        	    
	        		if (useFirstLastNaming)
	        		    userName += (" " + tokenizer.nextToken());
	        		
	        		userNames.add(userName);
	        	    }
	        	}
	        	
	        	if (!userNames.isEmpty() && message != null) {
	        	    Iterator userNameIterator = userNames.iterator();
	        	    
	        	    while (userNameIterator.hasNext()) {
	        		String userName = (String)userNameIterator.next();
	        		
	        		if (client.sendIM(userName, message) == false)
	        		    System.err.println("Failed to send message to " + userName);
	        	    }
	        	    
	        	    continue; // success
	        	}
	            }
	            catch (NoSuchElementException nsee) { }
	            
	            System.err.println("Usage: broadcast <userName1>...<userNameN> msg: <message>");
                }
		// shows or sets your status and status message
                // (omit parameters to show)
		else if (cmd.equals("status")) {
		    if (tokenizer.hasMoreTokens()) {
			String statusName = tokenizer.nextToken().toUpperCase();
			int status = getStatus(statusName);
			
			if (status == -1) {
			    System.err.println("Invalid status");
			    System.err.println("Available status values are ATLUNCH, AWAY, BRB, BUSY, CUSTOM_AWAY, DND, FREE_TO_CHAT, IDLE, INVISIBLE, NA, NOT_AT_DESK, NOT_AT_HOME, NOT_IN_OFFICE, OCCUPIED, OFFLINE, ON_VACATION, ONLINE, ONPHONE, STEPPED_OUT");
			}
			else {
			    String statusMessage = null;
			    
			    if (tokenizer.hasMoreTokens())
				statusMessage = tokenizer.nextToken("").trim();
			    
			    if (!client.setStatus(status, statusMessage))
				System.err.println("Not supported");
			}
		    }
		    else {
			int status = client.getStatus();
			String statusMessage = client.getCustomAwayMessage();
			
			System.out.print("Current status: " + getStatusName(status));
			
			if (statusMessage != null && statusMessage.length() != 0)
			    System.out.print(" (" + statusMessage + ")");
			
			System.out.println();
		    }
		}
                // accepts an auth request
                else if (cmd.equals("acceptauth")) {
                    if (!isBuddyAuthSupported()) {
                	System.err.println("Not supported");
                    }
                    else {
                        try {
			    String userName = tokenizer.nextToken();

			    if (useFirstLastNaming)
				userName += (" " + tokenizer.nextToken());

			    // compose the IMessage response
			    IMessage authResponse = IMessageFactory.factory(IMessage.AUTH_ACCEPT, client, userName, "");

			    // send the response
			    if (authResponse != null)
				client.sendMessage(authResponse);
			}
			catch (NoSuchElementException nsee) {
			    System.err.println("Usage: acceptauth <userName>");
			}
                    }
                }
                // declines an auth request
                else if (cmd.equals("declineauth")) {
                    if (!isBuddyAuthSupported()) {
			System.err.println("Not supported");
		    }
		    else {
			try {
			    String userName = tokenizer.nextToken();

			    if (useFirstLastNaming)
				userName += (" " + tokenizer.nextToken());

			    String message = "";

			    if (tokenizer.hasMoreTokens())
				message = tokenizer.nextToken("").trim();

			    // compose the IMessage response
			    IMessage authResponse = IMessageFactory.factory(IMessage.AUTH_DECLINE, client, userName, message);

			    // send the response
			    if (authResponse != null)
				client.sendMessage(authResponse);
			}
			catch (NoSuchElementException nsee) {
			    System.err.println("Usage: declineauth <userName> [message]");
			}
                    }
                }
                // accepts a conference invitation
		else if (cmd.equals("acceptconference")) {
		    try {
			String id = tokenizer.nextToken();
			IConference conference = (IConference)conferences.get(id);
			
			if (conference == null) {
			    System.err.println("Conference does not exist");
			}
			else {
			    conference.addListener(new ConferenceHandler(this));
			    conference.accept();
			}
                    }
		    catch (NoSuchElementException nsee) {
			System.err.println("Usage: acceptconference <id>");
		    }
		}
                // declines a conference invitation
		else if (cmd.equals("declineconference")) {
		    try {
			String id = tokenizer.nextToken();
			IConference conference = (IConference)conferences.remove(id);
			
			if (conference == null) {
			    System.err.println("Conference does not exist");
			}
			else {
			    String message;
			    
			    if (tokenizer.hasMoreTokens())
				message = tokenizer.nextToken("").trim();
			    else 
				message = "CmdLineClientDemo declines your conference invitation.";
			    
			    conference.decline(message);
			}
		    }
		    catch (NoSuchElementException nsee) {
			System.err.println("Usage: declineconference <id> [message]");
		    }
		}
                // leaves a conference
		else if (cmd.equals("leaveconference")) {
		    try {
			String id = tokenizer.nextToken();
			IConference conference = (IConference)conferences.remove(id);
			
			if (conference == null) {
			    System.err.println("Conference does not exist");
			}
			else {
			    conference.disconnect();
			}
		    }
		    catch (NoSuchElementException nsee) {
			System.err.println("Usage: leaveconference <id>");
		    }
		}
		// sends a file invitation
		else if (cmd.equals("sendfile")) {
		    if (!isFileTransferSupported()) {
			System.err.println("Not supported");
		    }
		    else {
			try {
			    String userName = tokenizer.nextToken();

			    if (useFirstLastNaming)
				userName += (" " + tokenizer.nextToken());

			    String path = tokenizer.nextToken("").trim();

			    File file = new File(path);
			    IFileSession fileSession = client.createFileSession(userName, file);

			    // register for events
			    fileSession.addFileSessionListener(new FileSessionHandler(this));

			    // keep track of reference so it doesn't get GC'd
			    String id = getNextFileSessionID();
			    fileSessions.put(id, fileSession);

			    fileSession.setDescription("File sent by CmdLineClientDemo");
			    fileSession.invite(); // send our invitation!
			}
			catch (NoSuchElementException nsee) {
			    System.err.println("Usage: sendfile <recipient> <path>");
			}
		    }
		}
                // accepts a file invitation
		else if (cmd.equals("acceptfile")) {
		    try {
			String id = tokenizer.nextToken();
			IFileSession fileSession = (IFileSession)fileSessions.get(id);
			
			if (fileSession == null) {
			    System.err.println("File session does not exist");
			}
			else {
			    fileSession.addFileSessionListener(new FileSessionHandler(this));
			    fileSession.accept();
			}
		    }
		    catch (NoSuchElementException nsee) {
			System.err.println("Usage: acceptfile <id>");
		    }
		}
                // declines a file invitation
		else if (cmd.equals("declinefile")) {
		    try {
			String id = tokenizer.nextToken();
			IFileSession fileSession = (IFileSession)fileSessions.remove(id);
			
			if (fileSession == null) {
			    System.err.println("File session does not exist");
			}
			else {
			    String message;
			    
			    if (tokenizer.hasMoreTokens())
				message = tokenizer.nextToken("").trim();
			    else
				message = "CmdLineClientDemo declines your file transfer invitation.";
			    
			    fileSession.decline(message);
			}
		    }
		    catch (NoSuchElementException nsee) {
			System.err.println("Usage: declinefile <id> [message]");
		    }
		}
		// shows your Buddy List
		else if (cmd.equals("bl")) {
		    showList(buddyList);
		}
		// adds a user to your Buddy List
		else if (cmd.equals("ab")) {
		    try {
			String userName = tokenizer.nextToken();
		    
			if (useFirstLastNaming)
			    userName += (" " + tokenizer.nextToken());
			
			String nickName = tokenizer.nextToken();
			String groupName = tokenizer.nextToken("").trim();
		    
			client.addBuddy(userName, nickName, groupName);
			remindSendConfig();
		    }
		    catch (NoSuchElementException nsee) {
			System.err.println("Usage: ab <userName> <nickName> <groupName>");
		    }
		}
		// removes a user from your Buddy List
                // (omit groupName to remove from all groups)
		else if (cmd.equals("rb")) {
		    try {
			String userName = tokenizer.nextToken();

			if (useFirstLastNaming)
			    userName += (" " + tokenizer.nextToken());
			
			if (tokenizer.hasMoreTokens()) {
			    String groupName = tokenizer.nextToken("").trim();

			    client.removeBuddy(userName, groupName);
			    remindSendConfig();
			}
			else {
			    int count = 0;
			    
			    Enumeration groupNames = buddyList.getAllGroupsForBuddy(userName);
			    while (groupNames.hasMoreElements()) {
				String groupName = (String)groupNames.nextElement();
				
				count++;
				client.removeBuddy(userName, groupName);
			    }

			    System.out.println("Removed " + userName + " from " + count + " buddy group(s)");
			    
			    if (count != 0)
				remindSendConfig();
			}
		    }
		    catch (NoSuchElementException nsee) {
			System.err.println("Usage: rb <buddyName> [groupName]");
		    }
		}
		// shows your Deny List
		else if (cmd.equals("dl")) {
		    showList(denyList);
		}
		// adds a user to your Deny List
		else if (cmd.equals("ad")) {
		    try {
			String userName = tokenizer.nextToken();

			if (useFirstLastNaming)
			    userName += (" " + tokenizer.nextToken());
			
			// no nickname or group name necessary!
			client.addDeny(userName, null, null);

			remindSendConfig();
		    }
		    catch (Exception e) {
			System.err.println("Usage: ad <buddyName>");
		    }
		}
		// removes a user from your Deny List
		else if (cmd.equals("rd")) {
		    try {
			String userName = tokenizer.nextToken();

			if (useFirstLastNaming)
			    userName += (" " + tokenizer.nextToken());
			
			// no group name necessary!
			client.removeDeny(userName, null);

			remindSendConfig();
		    }
		    catch (Exception e) {
			System.err.println("Usage: rd <buddyName>");
		    }
		}
		// shows your Permit List
		else if (cmd.equals("pl")) {
		    if (!isPermitListSupported()) {
			System.err.println("Not supported");
		    }
		    else {
			showList(permitList);
		    }
		}
		// adds a user to your Permit List
		else if (cmd.equals("ap")) {
		    if (!isPermitListSupported()) {
			System.err.println("Not supported");
		    }
		    else {
			try {
			    String userName = tokenizer.nextToken();

			    if (useFirstLastNaming)
				userName += (" " + tokenizer.nextToken());

			    // no nickname or group name necessary!
			    client.addPermit(userName, null, null);

			    remindSendConfig();
			}
			catch (Exception e) {
			    System.err.println("Usage: ap <buddyName>");
			}
		    }
		}
		// removes a user from your Permit List
		else if (cmd.equals("rp")) {
		    if (!isPermitListSupported()) {
			System.err.println("Not supported");
		    }
		    else {
			try {
			    String userName = tokenizer.nextToken();

			    if (useFirstLastNaming)
				userName += (" " + tokenizer.nextToken());

			    // no group name necessary!
			    client.removePermit(userName, null);

			    remindSendConfig();
			}
			catch (Exception e) {
			    System.err.println("Usage: rp <buddyName>");
			}
		    }
		}
                // shows your Reverse List (users who are watching you)
                else if (cmd.equals("rl")) {
                    if (!isReverseListSupported()) {
                	System.err.println("Not supported");
                    }
                    else {
                	showList(reverseList);
                    }
                }
		// shows or sets your permit mode (omit parameter to show)
		else if (cmd.equals("permitmode")) {
		    if (tokenizer.hasMoreTokens()) {
			String modeName = tokenizer.nextToken().toUpperCase();
			int mode = getPermitMode(modeName);
			
			if (mode == -1) {
			    System.err.println("Invalid permit mode");
			    System.err.println("Available modes are DENY_ALL, DENY_SOME, PERMIT_ALL, PERMIT_SOME");
			}
			else if (client.setPermitMode(mode)) {
			    System.out.println("New permit mode: " + modeName);
			    remindSendConfig();
			}
			else {
			    System.err.println("Not supported");
			}
		    }
		    else {
			int mode = client.getPermitMode();
			System.out.println("Current permit mode: " + getPermitModeName(mode));
		    }
		}
                // saves permit/Buddy List changes to the server
		else if (cmd.equals("sendconfig")) {
		    if (!isSendConfigSupported()) {
			System.err.println("Not supported");
		    }
		    else {
			System.out.println("Sending config to server...");
			client.sendConfigToServer();
			System.out.println("Done.");
		    }
		}
		// shows or sets your text mode (omit parameter to show)
		else if (cmd.equals("textmode")) {
		    if (tokenizer.hasMoreTokens()) {
			String modeName = tokenizer.nextToken().toUpperCase();
			int mode = getTextMode(modeName);
			
			if (mode == -1) {
			    System.err.println("Invalid text mode");
			    System.err.println("Available modes: PLAIN_TEXT, RICH_TEXT");
			}
			else {
			    client.setPlainTextMode(mode);
			    System.out.println("New text mode: " + modeName);
			}
		    }
		    else {
			int mode = client.getPlainTextMode();
			System.out.println("Current text mode: " + getTextModeName(mode));
		    }
		}
		// quits CmdLineClientDemo
                else if (cmd.equals("quit")) {
		    isUserQuit = true;
		    client.disconnect();
		}
		// Command not recognized, redisplay the main menu
		else {
		    System.err.println("Unknown command " + cmd);
		    printMenu();
		}
		
		tokenizer = null;
	    }
	    catch (Exception e) {
		System.err.println("Caught " + e.getClass().getName() + ": " + e.getMessage());
	    }
	} // while client.isOnline

	// cancel any current file sessions
	for (Iterator i = fileSessions.values().iterator(); i.hasNext(); ) {
	    ((IFileSession)i.next()).disconnect();
	}

	// cancel any current conferences
	for (Iterator i = conferences.values().iterator(); i.hasNext(); ) {
	    ((IConference)i.next()).disconnect();
	}

	System.out.println("CmdLineClientDemo has exited");
	System.exit(0);
    } // run()
    
    public static void main(String[] args) {
    	
		try {
		    Iterator argIterator = Arrays.asList(args).iterator();
		
		    String protocolName = (String)argIterator.next();
		    int protocol = getProtocol(protocolName);
		    
		    if (protocol == -1) {
			System.err.println("Invalid protocol");
			System.err.println("Available protocol values are JSC, AIM, ICQ, MSN, YIM, JABBER, LCS, SAMETIME");
			System.exit(1);
		    }
		
		    String clientName = (String)argIterator.next();
		
		    // SAMETIME ids have 2 parts (FirstName LastName)
		    if (protocol == IClient.SAMETIME)
			clientName += (" " + (String)argIterator.next());
		
		    // Unfortunately, in command line mode there is no way to supress 
		    // the echo of user input, so we cannot hide the user's password.
		    String password = (String)argIterator.next();
		
		    CmdLineClientDemo demo = new CmdLineClientDemo(protocol, clientName, password,false);
		    demo.run();
		}
		catch (NoSuchElementException nsee) {
		    System.err.println("Usage: <protocol> <userName> <password>");
		}
		catch (Exception e) {
		    System.out.println("Caught " + e.getClass().getName() + ": " + e.getMessage());
		}
    }

}
