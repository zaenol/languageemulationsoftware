package modelEditor.abstractClasses;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelEditor.interfaces.I_Element;

public abstract class AC_Element implements I_Element {
	String ID = "NOT SET";
	protected JPanel panel;

	public AC_Element(String id) {
		initID(id);
		panel = new JPanel();
		panel.setSize(40, 40);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(new JLabel(ID));
	}

	public JPanel getGUI(){
		return panel;
	}

	public void initID(String id) {
		ID = id;

	}

	public String[] parseString(String[] messages) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setXML() {
		// TODO Auto-generated method stub

	}

}
