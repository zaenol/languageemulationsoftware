package chatClient.model.jbuddy;

/*
 * (c) 2006 Zion Software, LLC. All Rights Reserved.
 */

import com.zion.jbuddy.conference.*;


/**
 * Handles incoming conference invitations and forced joins.
 */
public class ConferenceServiceHandler implements IConferenceServiceListener {

    private CmdLineClientDemo demo;
    
    
    public ConferenceServiceHandler(CmdLineClientDemo demo) {
	this.demo = demo;
    }
    
    
    // Implement this callback to handle incoming conference invitations
    // (if we register for these events).
    public void invitationReceived(ConferenceServiceEvent event) {
	IConference conference = event.getConference();
	
	// save the conference so it doesn't get GC'ed
	String id = demo.getNextConferenceID();
        demo.conferences.put(id, conference);

        System.out.print(event.getBuddy().getName() + " has invited you to a conference");
        
        String displayName = conference.getDisplayName();
        if (displayName != null && displayName.length() != 0)
            System.out.print(" titled '" + displayName + "'");

        String message = event.getInvitationMessage();
        if (message != null && message.length() != 0)
            System.out.print(": " + event.getInvitationMessage());
        
        System.out.println();
        System.out.println("Please respond now with 'acceptconference " + id + "' or 'declineconference " + id + "'");
    }
    
    // Implement this callback to handle automatic joined events
    // (if we register for these events).
    // MSN uses forced joins.
    public void joined(ConferenceServiceEvent event) {
        IConference conference = event.getConference();

        // register for conference events
        conference.addListener(new ConferenceHandler(demo));

        // conference.connect(); // not necessary for forced joins!
        
        // save it so it's not GC'd
        String id = demo.getNextConferenceID();
        demo.conferences.put(id, conference);

        System.out.print("You have just joined a conference");

        String displayName = conference.getDisplayName();
        if (displayName != null && displayName.length() != 0)
            System.out.print(" titled '" + conference.getDisplayName() + "'");
        
        System.out.println(" (id: " + id + ")");
    }    

}
