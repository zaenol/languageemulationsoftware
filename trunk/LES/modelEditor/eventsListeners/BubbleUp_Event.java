package modelEditor.eventsListeners;

import java.awt.AWTEvent;
import java.util.EventObject;

import modelEditor.interfaces.I_Element;




//http://www.javaworld.com/javaworld/javaqa/2002-03/01-qa-0315-happyevent.html
public class BubbleUp_Event extends EventObject{
	double theDouble;

	public BubbleUp_Event(I_Element source) {
        super(source);
    }
	public BubbleUp_Event(I_Element source,Double value) {
        super(source);
        theDouble = value;
    }

	public Double getValue(){
		return new Double(theDouble);
	}
	

	
}
