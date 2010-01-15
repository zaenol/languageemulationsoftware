package modelEditor.eventsListeners;



import java.util.EventListener;
import java.util.Vector;

public interface BubbleDown_Listener extends EventListener {
	public abstract void bubbleDownEventDetected(BubbleDown_Event e);
}
