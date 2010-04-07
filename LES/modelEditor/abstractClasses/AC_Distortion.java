package modelEditor.abstractClasses;

import modelEditor.interfaces.I_Element;
import modelEditor.model.Model_Message;


public abstract class AC_Distortion extends AC_Element {

	private boolean DISTORTION_WORD = false;
	private boolean DISTORTION_INFLECTION = false;
	private boolean DISTORTION_FUNCTION = false;
	private boolean DISTORTION_NONFLUENCY = false;
	private boolean DISTORTION_OTHER = false;
	
	public AC_Distortion(String id, boolean distortion_word, boolean distorition_inflection, boolean distortion_function, boolean distortion_nonfluency, boolean distortionOther) {
		super(id);
		this.setDISTORTION_WORD(distortion_word);
		this.setDISTORTION_INFLECTION(distorition_inflection);
		this.setDISTORITION_FUNCTION(distortion_function);
		this.setDISTORITION_NONFLUENCY(distortion_nonfluency);
		
		this.setDISTORTION_OTHER(distortionOther);
		
		if(!DISTORTION_WORD && !DISTORTION_INFLECTION && !DISTORTION_FUNCTION && !DISTORTION_NONFLUENCY && !DISTORTION_OTHER)
			this.setDISTORTION_OTHER(true);
	}

	public boolean isDISTORTION_WORD() {
		return DISTORTION_WORD;
	}

	public void setDISTORTION_WORD(boolean distortion_word) {
		DISTORTION_WORD = distortion_word;
	}

	public boolean isDISTORTION_INFLECTION() {
		return DISTORTION_INFLECTION;
	}

	public void setDISTORTION_INFLECTION(boolean distortion_inflection) {
		DISTORTION_INFLECTION = distortion_inflection;
	}

	public boolean isDISTORTION_FUNCTION() {
		return DISTORTION_FUNCTION;
	}

	public void setDISTORITION_FUNCTION(boolean distortion_function) {
		DISTORTION_FUNCTION = distortion_function;
	}

	public boolean isDISTORTION_NONFLUENCY() {
		return DISTORTION_NONFLUENCY;
	}

	public void setDISTORITION_NONFLUENCY(boolean distortion_nonfluency) {
		DISTORTION_NONFLUENCY = distortion_nonfluency;
	}

	public boolean isDISTORTION_OTHER() {
		return DISTORTION_OTHER;
	}

	public void setDISTORTION_OTHER(boolean distortionOther) {
		DISTORTION_OTHER = distortionOther;
	}

	
}
