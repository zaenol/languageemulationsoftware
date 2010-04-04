package modelEditor.abstractClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelEditor.interfaces.I_Element;

public abstract class AC_Element implements I_Element {
	String ID = "NOT SET";
	
	private JPanel masterPanel;
	private JPanel contentPanel;
	
	protected JPanel headerPanel;
	protected JPanel bodyPanel;
	
	private JPanel masterPanelNorth;
	
	//protected JPanel content;

	public AC_Element(String id) {
		initID(id);
		masterPanel = new JPanel();
		masterPanel.setSize(40, 40);
		masterPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		masterPanel.setLayout(new BorderLayout());
	
		//masterPanel.add(new JLabel(ID,JLabel.CENTER),BorderLayout.NORTH);
		
		contentPanel = new JPanel(new BorderLayout());
		masterPanel.add(contentPanel,BorderLayout.CENTER);
		
		headerPanel = new JPanel();
		contentPanel.add(headerPanel,BorderLayout.NORTH);
		
		bodyPanel = new JPanel();
		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
		contentPanel.add(bodyPanel,BorderLayout.CENTER);
		
		masterPanelNorth = new JPanel();
		masterPanelNorth.setLayout(new BoxLayout(masterPanelNorth,BoxLayout.X_AXIS));
		masterPanelNorth.add(new JLabel(ID,JLabel.CENTER));
		masterPanel.add(masterPanelNorth,BorderLayout.NORTH);
		
	}
	protected void addToTopOfMasterPanel(JComponent component){
		masterPanelNorth.add(component);
	}

	public JPanel getGUI(){
		return masterPanel;
	}

	public void initID(String id) {
		ID = id;

	}

	/*
	public String[] parseString(String[] messages) {
		// TODO Auto-generated method stub
		//http://www.totheriver.com/learn/xml/xmltutorial.html
		return this.parseString_local(messages);
	}*/

	public void setValuesFromXML() {
		// TODO Auto-generated method stub

	}
	
	public void setBorder(Color color){
		masterPanel.setBorder(BorderFactory.createLineBorder(color));
	}

	public double round4Decimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.####");
    	return Double.valueOf(twoDForm.format(d));
	}
	public double round2Decimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
    	return Double.valueOf(twoDForm.format(d));
	}

	
	public String getID() {
		return ID;
	}

	
}
