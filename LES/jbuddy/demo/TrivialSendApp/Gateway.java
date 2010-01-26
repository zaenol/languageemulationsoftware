package jbuddy.demo.TrivialSendApp;

/*
 * (c) 2006 Zion Software, LLC. All Rights Reserved.
 */

import com.zion.jbuddy.*;


/**
 * This is a bare-bones implementation of the IGateway interface.  It merely 
 * prints out messages whenever the callback methods are fired.
 */
public class Gateway implements IGateway {

    public void incomingMessage(IClient client, IMessage message) {
        // code to processing incoming messages     
        System.out.println("incomingMessage: " + message);
    }

    public void adminMessage(IClient client, IMessage message) {
        // code to process incoming administrative messages or errors
        System.out.println("adminMessage: " + message);
    }

    public void incomingBuddy(IClient client, IBuddy buddy) {
        // code to process incoming buddy and presence messages
        System.out.println("incomingBuddy: " + buddy);
    }

    public void connectionLost(IClient client, String reason) {
        // code to process connection lost by client with reason
        System.out.println("connectionLost: " + client.getName() + " (reason: " + reason + ")");
    }
}
