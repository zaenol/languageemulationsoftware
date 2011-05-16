package modelEditor.abstractClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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

	public static String[] paralinguisticWords = {"...","...","...","um...","uh...","ah...","eh...","er...","...","...","..."};
	public static ArrayList<String> paralinguisticWordsArrayList = new ArrayList(Arrays.asList(paralinguisticWords));;
	
	public static String generateparalinguistic(){
		Random random_ac_element;
		random_ac_element = new Random();
		int i = random_ac_element.nextInt(paralinguisticWords.length);
		return paralinguisticWords[i];
	}
	
	public static String generateparalinguisticWithSpace(){
		String word = generateparalinguistic();
		
		if(word.equals("..."))
			return word;
		else
			return " "+word;
	}
	
	public static String generateparalinguisticOrEmpty(int probability){
		Random random_ac_element;
		random_ac_element = new Random();
		int i = random_ac_element.nextInt(probability);
		if(i==0)
			return "";
		else
			return generateparalinguistic();
	}
	
	public static String generateparalinguisticOrEmptyWithSpace(int probability){
		Random random_ac_element;
		random_ac_element = new Random();
		int i = random_ac_element.nextInt(probability);
		if(i==0)
			return generateparalinguisticWithSpace();
		else
			return "";
			
	}
	
	public static boolean isgParalinguistic(String s){
		if(paralinguisticWordsArrayList.contains(s))
			return true;
		else
			return false;
	}
	
}
