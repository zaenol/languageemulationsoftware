package modelEditor.abstractClasses;

import modelEditor.eventsListeners.BubbleUp_Listener;
import modelEditor.eventsListeners.BubbleUp_Event;
import java.util.Vector;

public abstract class AC_Distortion_BubbleUp extends AC_Distortion{

	private Vector<BubbleUp_Listener> listeners = new Vector<BubbleUp_Listener>();
	
	public AC_Distortion_BubbleUp(String id, boolean anomic, boolean agrammatic, boolean distortion_error, boolean distortion_correction){
		super(id,anomic,agrammatic,distortion_error,distortion_correction);
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
