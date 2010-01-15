package modelEditor.interfaces;

import java.util.EventListener;

import javax.swing.JPanel;

public interface I_Element{
	
	
	
	public abstract JPanel getGUI();
	public abstract void getXML();
	public abstract void setXML();
	/**
	 * Given a series of messages to send, apply specific distorition
	 * @param messages
	 */
	public abstract String[] parseString(String[] messages);
	/**
	 * Forces all instantiations of distortions to create an ID value
	 */
	public abstract void initID(String id);
	

}
