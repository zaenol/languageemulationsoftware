
This demo shows how to use JBuddy's various features.  It creates and 
connects an IClient, accepting various commands from the command line.

To run the demo, open a command shell, change into the demo's directory, and 
invoke the Java interpreter.
	
The command should look something like this:
	
	java -cp <LIB_PATH>\JBuddy.jar;classes CmdLineClientDemo <protocol> <userName> <password>

(replace <LIB_PATH> with the path of the JBuddy lib directory.)

Replace the remaining variables with their specific values:

	 <protocol> - the protocol you'd like to use (JSC, AIM, ICQ, MSN, YIM, JABBER, LCS, or SAMETIME)
	 <userName> - the name of the account you'd like to log in with
	 <password> - the password of the account you'd like to log in with

The demo will connect, and you can enter commands from the command line.
