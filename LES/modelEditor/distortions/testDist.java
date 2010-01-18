package modelEditor.distortions;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modelEditor.abstractClasses.AC_Distortion_BubbleDown;
import modelEditor.eventsListeners.BubbleDown_Event;

public class testDist extends AC_Distortion_BubbleDown{
	
	JLabel myGlobalValue = new JLabel("Global: 0%");
	JLabel myLocalValue = new JLabel("Local: 0%");
	
	public testDist(){
		super("Test Distortion");
		this.setANOMIC(true);
		this.setERROR(true);
		this.bodyPanel.add(myGlobalValue);
		this.bodyPanel.add(myLocalValue);
	}

	public void getXML() {
		// TODO Auto-generated method stub
		
	}

	public void initID() {
		// TODO Auto-generated method stub
		
	}

	public String[] parseString(String[] messages) {
		// TODO Auto-generated method stub
		return messages;
	}

	public void setXML() {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		myGlobalValue.setText("Global: "+this.getSeverityValue_global()+"%");
		myLocalValue.setText("Local: "+this.getSeverityValue_local()+"%");
		
	}

}
