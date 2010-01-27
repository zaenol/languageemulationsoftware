package chatClient.model;



import java.util.EventListener;
import java.util.Vector;

public interface IMMessage_Listener extends EventListener {
	public abstract void imMessageEventDetected(IMMessage_Event event);
}
