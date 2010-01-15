package modelEditor.eventsListeners;



import java.util.EventListener;
import java.util.Vector;

public interface BubbleUp_Listener extends EventListener {
	public abstract void bubbleUpEventDetected(BubbleUp_Event event);
}
