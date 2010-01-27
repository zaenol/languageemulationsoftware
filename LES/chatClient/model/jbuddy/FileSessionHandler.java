package chatClient.model.jbuddy;

/*
 * (c) 2006 Zion Software, LLC. All Rights Reserved.
 */

import com.zion.jbuddy.filetransfer.*;


/**
 * Prints out event information and gets rid of unused file sessions.
 */
public class FileSessionHandler implements IFileSessionListener {
    
    private CmdLineClientDemo demo;
    
    
    public FileSessionHandler(CmdLineClientDemo demo) {
	this.demo = demo;
    }
    

    public void accepted(FileSessionEvent event) {
	// The recipient has accepted our outgoing file tranfser invitation and 
	// the file will now be transfered if possible
        System.out.println("FileSessionListener.accepted(" + event + ")");
    }
    
    public void declined(FileSessionEvent event) {
	// The recipient has declined our outgoing file transfer invitation
        System.out.println("FileSessionListener.declined(" + event + ")");
        demo.fileSessions.remove(event.getSource());
    }
    
    public void canceled(FileSessionEvent event) {
	// The recipient has canceled their invitation
        System.out.println("FileSessionListener.canceled(" + event + ")");
        demo.fileSessions.remove(event.getSource());
    }

    public void connected(FileSessionEvent event) {
	// We have established a connection for file transfer
        System.out.println("FileSessionListener.connected(" + event + ")");
        demo.fileSessions.remove(event.getSource());
    }
    
    public void couldNotConnect(FileSessionEvent event) {
	// Unable to establish a connection for file transfer - firewall?
        System.out.println("FileSessionListener.couldNotConnect(" + event + ")");
        demo.fileSessions.remove(event.getSource());
    }
    
    public void beginTransfer(FileSessionEvent event) {
	// The transfer has begun
        System.out.println("FileSessionListener.beginTransfer(" + event + ")");
    }
    
    public void updateProgress(FileSessionEvent event) {
	// This is called if setProgressThreadEnabled(true) is called 
    }

    public void endTransfer(FileSessionEvent event) {
	// The transfer has completed
        System.out.println("FileSessionListener.endTransfer(" + event + ")");
        demo.fileSessions.remove(event.getSource());
    }
    
    public void lostConnection(FileSessionEvent event) {
	// We've lost our file transfer connection
        System.out.println("FileSessionListener.lostConnection(" + event + ")");
        demo.fileSessions.remove(event.getSource());
    }

}

