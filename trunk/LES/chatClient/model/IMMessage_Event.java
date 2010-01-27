package chatClient.model;

import java.awt.AWTEvent;
import java.util.EventObject;

import modelEditor.interfaces.I_Element;
import modelEditor.model.Model_Message;




//http://www.javaworld.com/javaworld/javaqa/2002-03/01-qa-0315-happyevent.html
public class IMMessage_Event extends EventObject{
	Model_Message theMessage;
	boolean isError = false;
	boolean incoming = false;
	boolean outgoing = false;
	String fromScreenName = "";
	String toScreenName = "";

	public IMMessage_Event(I_Element source) {
        super(source);
    }
	public IMMessage_Event(I_Element source,String value) {
        super(source);
        Model_Message mm = new Model_Message(value);
        theMessage = mm;
    }
	public IMMessage_Event(I_Element source,Model_Message value) {
        super(source);
        theMessage = value;
    }
	
}
