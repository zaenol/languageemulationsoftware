package modelEditor.abstractClasses;

import modelEditor.interfaces.I_Element;


public abstract class AC_Distortion extends AC_Element {

	private boolean ANOMIC = false;
	private boolean AGRAMMATIC = false;
	private boolean DISTORTION_ERROR = false;
	private boolean DISTORTION_CORRECTION = false;
	
	public AC_Distortion(String id, boolean anomic, boolean agrammatic, boolean distortion_error, boolean distortion_correction) {
		super(id);
		this.setANOMIC(anomic);
		this.setAGRAMMATIC(agrammatic);
		this.setERROR(distortion_error);
		this.setCORRECTION(distortion_correction);
		
	}

	public boolean isANOMIC() {
		return ANOMIC;
	}

	public void setANOMIC(boolean anomic) {
		ANOMIC = anomic;
	}

	public boolean isAGRAMMATIC() {
		return AGRAMMATIC;
	}

	public void setAGRAMMATIC(boolean agrammatic) {
		AGRAMMATIC = agrammatic;
	}

	public boolean isDISTORTION_ERROR() {
		return DISTORTION_ERROR;
	}

	public void setERROR(boolean distortion_error) {
		DISTORTION_ERROR = distortion_error;
	}

	public boolean isDISTORTION_CORRECTION() {
		return DISTORTION_CORRECTION;
	}

	public void setCORRECTION(boolean distortion_correction) {
		DISTORTION_CORRECTION = distortion_correction;
	}

	

	
}
