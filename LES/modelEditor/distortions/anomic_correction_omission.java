package modelEditor.distortions;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_BubbleUp;

public class anomic_correction_omission extends AC_Distortion_BubbleUp {

	public anomic_correction_omission(String id, boolean anomic,
			boolean agrammatic, boolean distortion_error,
			boolean distortion_correction) {
		super(id, anomic, agrammatic, distortion_error, distortion_correction);
		// TODO Auto-generated constructor stub
	}

	public void getXML() {
		// TODO Auto-generated method stub
		this.parseString(null);

	}
	public void setValuesFromXML_local(Document dbf) {
		// TODO Auto-generated method stub
		
	}

	public String[] parseString_local(String[] messages) {
		// TODO Auto-generated method stub
		return null;
	}

}
