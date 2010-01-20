package modelEditor.distortions;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.eventsListeners.BubbleDown_Event;


public class testDist extends AC_Distortion_BubbleDown{
	
	JLabel myGlobalValue = new JLabel("Global: 0%");
	JLabel myLocalValue = new JLabel("Local: 0%");
	
	public testDist(){
		super("Test Distortion",false,false,false,false);
		this.bodyPanel.add(myGlobalValue);
		this.bodyPanel.add(myLocalValue);
	}

	public void getXML() {
		// TODO Auto-generated method stub
		
	}
	public void setValuesFromXML_local(Document dom) {
		// TODO Auto-generated method stub
		
	}

	public String[] parseString_local(String[] messages) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update() {
		// TODO Auto-generated method stub
		myGlobalValue.setText("Global: "+this.getSeverityValue_global()+"%");
		myLocalValue.setText("Local: "+this.getSeverityValue_local()+"%");
		
	}

}
