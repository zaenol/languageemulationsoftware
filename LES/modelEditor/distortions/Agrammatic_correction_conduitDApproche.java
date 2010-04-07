package modelEditor.distortions;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_Independent;
import modelEditor.model.Model_Message;
import modelEditor.model.Model_Message_posWord.PosWord;

public class Agrammatic_correction_conduitDApproche extends AC_Distortion_Independent {

	public Agrammatic_correction_conduitDApproche() {
		super("Conduit d'approche", false, false, false, true);
		// TODO Auto-generated constructor stub
	}

	public Document getXML() {
		// TODO Auto-generated method stub
		return null;
	}

	public Model_Message parseMessage(Model_Message messages) {
		// TODO Auto-generated method stub
		return messages;
	}
	

	/*
	public void parseMessageWord(PosWord posWord){
		
	}*/


	public void setValuesFromXML_local(Document dom) {
		// TODO Auto-generated method stub

	}

}
