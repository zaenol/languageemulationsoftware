package jbuddy.demo.TrivialSendApp;

/*
 * (c) 2006 Zion Software, LLC. All Rights Reserved.
 */

import java.util.*;
import com.zion.jbuddy.*;


/**
 * This demo shows how to use JBuddy.  It simply connects to the specified 
 * protocol and sends a message to a user.
 */
public class TrivialSendApp {

    public static void main(String[] args) {
    	String[] myArgs = {"AIM","cmurockstar","c00lkid","austarv","Hello josh"};
    	
        try {
            Iterator argIterator = Arrays.asList(myArgs).iterator();

	    String protocolName = (String)argIterator.next();
	    int protocol = getProtocol(protocolName);

	    if (protocol == -1) {
		System.err.println("Invalid protocol");
		System.err.println("Available values are JSC, AIM, ICQ, MSN, YIM, JABBER, LCS, SAMETIME");
		System.exit(1);
	    }

	    String userName = (String)argIterator.next();

	    // SAMETIME ids have 2 parts
	    if (protocol == IClient.SAMETIME)
		userName += (" " + (String)argIterator.next());

	    String password = (String)argIterator.next();
	    String recipient = (String)argIterator.next();

	    // SAMETIME ids have 2 parts
	    if (protocol == IClient.SAMETIME)
		recipient += (" " + (String)argIterator.next());

	    String message = (String)argIterator.next();

	    while (argIterator.hasNext())
		message += (" " + (String)argIterator.next());

	    // create a IGateway instance to handle callback events
	    Gateway gateway = new Gateway();

	    // create a client
	    IClient client = IClientFactory.factory(gateway, protocol, userName, password);

	    if (client == null) {
		System.err.println("Client init error");
		System.exit(1);
	    }

	    System.out.print("Trying to connect to " + client.getProtocolName() + " as " + userName);
	    
	    // sign on to IM service
            client.connect();

            // make sure we are connected and online
            while (!client.isOnline()) {
        	System.out.print(".");
                Thread.sleep(1000);
            }

            System.out.println("Connected!");
            
            // send the message
            System.out.println("Sending message...");
            boolean wasSent = client.sendIM(recipient, message);
            System.out.println("Sent message?: " + wasSent);

            // pause to let the message go through before disconnecting
            Thread.sleep(3000);
            
            System.out.println("Disconnecting...");
            client.disconnect();

            System.out.println("Done.");
        }
        catch (NoSuchElementException nsee) {
            System.err.println("Usage: <protocol> <userName> <password> <recipient> <message>");
        }
        catch (Exception e) {
            System.err.println("Caught " + e.getClass().getName() + ": " + e.getMessage());
        }

        System.exit(0);
    }
    
    
    /**
     * Returns the protocol constant for the specified protocol name.
     */
    static int getProtocol(String name) {
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
    
}
