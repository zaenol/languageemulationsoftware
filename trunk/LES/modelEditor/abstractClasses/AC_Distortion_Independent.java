package modelEditor.abstractClasses;

import modelEditor.eventsListeners.BubbleDown_Event;
import modelEditor.eventsListeners.BubbleDown_Listener;

public abstract class AC_Distortion_Independent extends AC_Distortion{
	
	
	public AC_Distortion_Independent(String id, boolean anomic, boolean agrammatic, boolean distortion_error, boolean distortion_correction){
		super(id,anomic,agrammatic,distortion_error,distortion_correction);
	}


	
}
