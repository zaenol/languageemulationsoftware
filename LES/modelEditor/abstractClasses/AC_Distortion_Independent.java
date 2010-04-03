package modelEditor.abstractClasses;

import modelEditor.eventsListeners.BubbleDown_Event;
import modelEditor.eventsListeners.BubbleDown_Listener;

public abstract class AC_Distortion_Independent extends AC_Distortion{
	
	

	
	public AC_Distortion_Independent(String id, boolean distortion_word, boolean distorition_inflection, boolean distortion_function, boolean distortion_nonfluency){
		super(id,distortion_word,distorition_inflection,distortion_function,distortion_nonfluency,false);
	}
	
	public AC_Distortion_Independent(String id, boolean distortion_word, boolean distorition_inflection, boolean distortion_function, boolean distortion_nonfluency,boolean distortion_other){
		super(id,distortion_word,distorition_inflection,distortion_function,distortion_nonfluency,distortion_other);
	}


	
}
