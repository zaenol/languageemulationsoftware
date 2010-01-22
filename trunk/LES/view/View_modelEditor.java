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
	
	JPanel distortionWordPanel=new JPanel(new GridLayout(1,1));
	JPanel distortionInflectionPanel=new JPanel(new GridLayout(1,1));
	JPanel distortionFunctionPanel=new JPanel(new GridLayout(1,1));
	JPanel distortionNonFluencyPanel=new JPanel(new GridLayout(1,1));
	
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
		
		tabbedPane.addTab("Distortion of Words", null, distortionWordPanel,"-- --");
		tabbedPane.addTab("Distortion of Inflection", null, distortionInflectionPanel,"-- --");
		tabbedPane.addTab("Distortion of Function Words", null, distortionFunctionPanel,"-- --");
		tabbedPane.addTab("Distortion Non Fluency", null, distortionNonFluencyPanel,"-- --");
		tabbedPane.addTab("Other", null, otherPanel,"Other Errors and Corrections");
		
		//distortionClassifications.add(west);
		//distortionClassifications.add(east);
		
		container.add(title, BorderLayout.NORTH);
		container.add(tabbedPane,BorderLayout.CENTER);
		
		frame.setVisible(true);
		
	}
	public void setWordPanel(JPanel panel){
		distortionWordPanel.removeAll();
		distortionWordPanel.add(panel);
		frame.setVisible(frame.isVisible());
	}
	public void setInflectionPanel(JPanel panel){
		distortionInflectionPanel.removeAll();
		distortionInflectionPanel.add(panel);
		frame.setVisible(frame.isVisible());		
	}
	public void setFunctionWordPanel(JPanel panel){
		distortionFunctionPanel.removeAll();
		distortionFunctionPanel.add(panel);
		frame.setVisible(frame.isVisible());
	}
	public void setNonFluencyPanel(JPanel panel){
		distortionNonFluencyPanel.removeAll();
		distortionNonFluencyPanel.add(panel);
		frame.setVisible(frame.isVisible());		
	}

}
