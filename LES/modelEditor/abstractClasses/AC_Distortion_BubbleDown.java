package modelEditor.abstractClasses;

import modelEditor.eventsListeners.BubbleDown_Event;
import modelEditor.eventsListeners.BubbleDown_Listener;

public abstract class AC_Distortion_BubbleDown extends AC_Distortion implements BubbleDown_Listener {
	
	private double severityValue_global = 0;
	private double severityValue_local = 0;
	private boolean released = false; // if released is true, use local value
	
	public AC_Distortion_BubbleDown(String id){
		super(id);
	}

	public void bubbleDownEventDetected(BubbleDown_Event e) {
		Double value = e.getValue();
		this.setSeverityValue_global(value);
		if(!released)
			this.setSeverityValue_local(value);
	}

	public double getSeverityValue_global() {
		return severityValue_global;
	}

	public void setSeverityValue_global(double severityValue_global) {
		this.severityValue_global = severityValue_global;
	}

	public double getSeverityValue_local() {
		return severityValue_local;
	}

	public void setSeverityValue_local(double severityValue_local) {
		this.severityValue_local = severityValue_local;
	}

	public boolean isReleased() {
		return released;
	}

	public void setReleased(boolean released) {
		if(!released){
			severityValue_local = severityValue_global;
		}
		this.released = released;
	}

	
}
