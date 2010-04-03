package modelEditor.abstractClasses;

import modelEditor.eventsListeners.BubbleUp_Listener;
import modelEditor.eventsListeners.BubbleUp_Event;
import java.util.Vector;

public abstract class AC_Distortion_BubbleUp extends AC_Distortion{

	private Vector<BubbleUp_Listener> listeners = new Vector<BubbleUp_Listener>();
	
	public AC_Distortion_BubbleUp(String id, boolean distortion_word, boolean distorition_inflection, boolean distortion_function, boolean distortion_nonfluency){
		super(id,distortion_word,distorition_inflection,distortion_function,distortion_nonfluency,false);
	}
	
	public AC_Distortion_BubbleUp(String id, boolean distortion_word, boolean distorition_inflection, boolean distortion_function, boolean distortion_nonfluency,boolean distortion_other){
		super(id,distortion_word,distorition_inflection,distortion_function,distortion_nonfluency,distortion_other);
	}
	
	public synchronized void addBubbleUpListener(BubbleUp_Listener bubbleUpListener){
		listeners.add(bubbleUpListener);
	}
	public synchronized void removeBubbleUpListener(BubbleUp_Listener bubbleUpListener){
		listeners.remove(bubbleUpListener);
	}

	protected synchronized void fireDoubleEvent(Double value){
		BubbleUp_Event event = new BubbleUp_Event(this,value);
		
		for(BubbleUp_Listener listen:listeners)
			listen.bubbleUpEventDetected(event);
	}

}
