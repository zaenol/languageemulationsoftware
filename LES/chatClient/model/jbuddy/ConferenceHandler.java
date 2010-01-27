package chatClient.model.jbuddy;

/*
 * (c) 2006 Zion Software, LLC. All Rights Reserved.
 */

import com.zion.jbuddy.conference.*;


/**
 * Prints out event information and gets rid of unused conferences.
 */
public class ConferenceHandler implements IConferenceListener {
    
    private CmdLineClientDemo demo;
    

    public ConferenceHandler(CmdLineClientDemo demo) {
	this.demo = demo;
    }
    
    
    public void declined(ConferenceEvent event) {
	System.out.println("ConferenceListener.declined(" + event + ")");
	demo.conferences.values().remove(event.getConference());
    }

    public void connected(ConferenceEvent event) {
	System.out.println("ConferenceListener.connected(" + event + ")");
    }

    public void online(ConferenceEvent event) {
	System.out.println("ConferenceListener.online(" + event + ")");
    }

    public void incomingMessage(ConferenceEvent event) {
	System.out.println("ConferenceListener.incomingMessage(" + event + ")");
    }

    public void adminMessage(ConferenceEvent event) {
	System.out.println("ConferenceListener.adminMessage(" + event + ")");
    }

    public void incomingBuddy(ConferenceEvent event) {
	System.out.println("ConferenceListener.incomingBuddy(" + event + ")");
    }

    public void lostConnection(ConferenceEvent event) {
	System.out.println("ConferenceListener.lostConnection(" + event + ")");
	demo.conferences.values().remove(event.getConference());
    }

}
