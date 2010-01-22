package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class View_modelEditor {

	JFrame frame;
	Container container;
	
	String titleText = "Aphasia Model Editor";
	JLabel title = new JLabel(titleText,JLabel.CENTER);
	
	JPanel anomicPanel=new JPanel(new GridLayout(1,1));
	JPanel agrammaticPanel=new JPanel(new GridLayout(1,1));
	JPanel otherPanel=new JPanel(new GridLayout(1,1));
	
	//JPanel distortionClassifications;
	JTabbedPane tabbedPane = new JTabbedPane();
	
	public View_modelEditor() {
		frame = new JFrame(titleText);
		frame.setSize(1024,768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = frame.getContentPane();
		container.setLayout(new BorderLayout());
	
		//distortionClassifications = new JPanel();
		//distortionClassifications.setLayout(new GridLayout(1, 2));
		
		tabbedPane.addTab("Anomic", null, anomicPanel,"Anomic Errors and Corrections");
		tabbedPane.addTab("Agrammatic", null, agrammaticPanel,"Agrammatic Errors and Corrections");
		tabbedPane.addTab("Other", null, otherPanel,"Other Errors and Corrections");
		
		//distortionClassifications.add(west);
		//distortionClassifications.add(east);
		
		container.add(title, BorderLayout.NORTH);
		container.add(tabbedPane,BorderLayout.CENTER);
		
		frame.setVisible(true);
		
	}
	public void setAnomic(JPanel panel){
		anomicPanel.removeAll();
		anomicPanel.add(panel);
		//distortionClassifications.remove(west);
		//distortionClassifications.add(panel);
		//west = panel;
		frame.setVisible(frame.isVisible());
	}
	public void setAgrammatic(JPanel panel){
		agrammaticPanel.removeAll();
		agrammaticPanel.add(panel);
		//distortionClassifications.remove(east);
		//distortionClassifications.add(panel);
		//east = panel;
		frame.setVisible(frame.isVisible());
		
	}

}
