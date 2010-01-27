package chatClient.model.jbuddy;

/*
 * (c) 2006 Zion Software, LLC. All Rights Reserved.
 */

import com.zion.jbuddy.filetransfer.*;


/**
 * Handles incoming file session invitations.
 */
public class FileSessionInvitationHandler implements IFileSessionInvitationListener {
    
    private CmdLineClientDemo demo;
    
    
    public FileSessionInvitationHandler(CmdLineClientDemo demo) {
	this.demo = demo;
    }
    

    // Implement this callback to handle incoming file invitation 
    // (if we register for these events).
    public void invitationReceived(FileSessionInvitationEvent event) {
	IFileSession session = event.getFileSession();
	
	// save the session so it won't be GC'ed
	String id = demo.getNextConferenceID();
	demo.fileSessions.put(id, session);
	
	System.out.print(session.getSender() + " has invited you to receive a file '" + session.getFileName() + "' (" + session.getFileSize() + " byte(s))");

	String description = session.getDescription();
	
	if (description != null && description.length() != 0)
	    System.out.print(": " + session.getDescription());
	
	System.out.println();
	System.out.println("Please respond now with 'acceptfile " + id + "' or 'declinefile " + id + "'");
    }

}
