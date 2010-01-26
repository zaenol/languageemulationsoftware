
This demo shows how to use JBuddy.  It simply connects to the specified 
protocol and sends a message to a user.

To run the demo, open a command shell, change into the demo's directory, and 
invoke the Java interpreter.
	
The command should look something like this:
	
	java -cp <LIB_PATH>\JBuddy.jar;classes TrivialSendApp <protocol> <userName> <password> <recipient> <message>

(replace <LIB_PATH> with the path of the JBuddy lib directory.)

Replace the remaining variables with their specific values:

	 <protocol> - the protocol you'd like to use (JSC, AIM, ICQ, MSN, YIM, JABBER, LCS, or SAMETIME)
	 <userName> - the name of the account you'd like to log in with
	 <password> - the password of the account you'd like to log in with
	<recipient> - the user you'd like to send a message to
	  <message> - the message contents

The demo will connect, send the message, and disconnect.
