package modelEditor.distortions;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_Independent;
import modelEditor.model.Model_Message;

public class Agrammatic_error_functionWords extends AC_Distortion_Independent {

	public Agrammatic_error_functionWords() {
		super("Dropping Function Words", false, false, true, false);
		// TODO Auto-generated constructor stub
	}

	public Document getXML() {
		// TODO Auto-generated method stub
		return null;
	}

	public Model_Message parseString(Model_Message messages) {
		// TODO Auto-generated method stub
		return messages;
	}

	public void setValuesFromXML_local(Document dom) {
		// TODO Auto-generated method stub

	}

}