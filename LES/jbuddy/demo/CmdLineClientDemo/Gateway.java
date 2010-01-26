package jbuddy.demo.CmdLineClientDemo;

/*
 * (c) 2006 Zion Software, LLC. All Rights Reserved.
 */

import com.zion.jbuddy.*;


/**
 * Handles IGateway events.
 */
public class Gateway implements IGateway {
    
    private CmdLineClientDemo demo;
    
    
    public Gateway(CmdLineClientDemo demo) {
	this.demo = demo;
    }
    

    /**
     * All incoming messages from the IM server can be processed via the 
     * incomingMessage() callback method.
     */
    public void incomingMessage(IClient client, IMessage message) throws Exception {
	demo.printMessage(message);
    }

    /**
     * All client admin messages can be processed via the adminMessage() 
     * callback method.
     */
    public void adminMessage(IClient client, IMessage message) throws Exception {
	demo.printMessage(message);
    }

    /**
     * All incoming presence updates from the IM server can be processed via 
     * the incomingBuddy() callback method.
     */
    public void incomingBuddy(IClient client, IBuddy buddy) throws Exception {
	// In this application we will not perform any extra handling of the 
	// buddy messages
    }

    public void connectionLost(IClient client, String reason) throws Exception {
	if (!demo.isUserQuit)
	    System.out.println("IGateway.connectionLost(" + reason + ")");
    }

}
