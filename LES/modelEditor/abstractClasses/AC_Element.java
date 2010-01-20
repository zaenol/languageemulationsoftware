package modelEditor.abstractClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelEditor.interfaces.I_Element;

public abstract class AC_Element implements I_Element {
	String ID = "NOT SET";
	
	private JPanel masterPanel;
	private JPanel contentPanel;
	
	protected JPanel headerPanel;
	protected JPanel bodyPanel;
	
	//protected JPanel content;

	public AC_Element(String id) {
		initID(id);
		masterPanel = new JPanel();
		masterPanel.setSize(40, 40);
		masterPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		masterPanel.setLayout(new BorderLayout());
	
		masterPanel.add(new JLabel(ID,JLabel.CENTER),BorderLayout.NORTH);
		
		contentPanel = new JPanel(new BorderLayout());
		masterPanel.add(contentPanel,BorderLayout.CENTER);
		
		headerPanel = new JPanel();
		contentPanel.add(headerPanel,BorderLayout.NORTH);
		
		bodyPanel = new JPanel();
		contentPanel.add(bodyPanel,BorderLayout.CENTER);
		
	}

	public JPanel getGUI(){
		return masterPanel;
	}

	public void initID(String id) {
		ID = id;

	}

	public String[] parseString(String[] messages) {
		// TODO Auto-generated method stub
		//http://www.totheriver.com/learn/xml/xmltutorial.html
		return this.parseString_local(messages);
	}

	public void setValuesFromXML() {
		// TODO Auto-generated method stub

	}
	
	public void setBorder(Color color){
		masterPanel.setBorder(BorderFactory.createLineBorder(color));
	}

}
