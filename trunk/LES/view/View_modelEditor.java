package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View_modelEditor {

	JFrame frame;
	Container container;
	
	String titleText = "Aphasia Model Editor";
	JLabel title = new JLabel(titleText,JLabel.CENTER);
	
	JPanel west=new JPanel();
	JPanel east=new JPanel();
	
	JPanel distortionClassifications;
	
	public View_modelEditor() {
		frame = new JFrame(titleText);
		frame.setSize(900,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = frame.getContentPane();
		container.setLayout(new BorderLayout());
	
		distortionClassifications = new JPanel();
		distortionClassifications.setLayout(new GridLayout(1, 2));
		
		distortionClassifications.add(west);
		distortionClassifications.add(east);
		
		container.add(title, BorderLayout.NORTH);
		container.add(distortionClassifications,BorderLayout.CENTER);
		
		frame.setVisible(true);
		
	}
	public void setAnomic(JPanel panel){
		distortionClassifications.remove(west);
		distortionClassifications.add(panel);
		west = panel;
		frame.setVisible(frame.isVisible());
	}
	public void setAgrammatic(JPanel panel){
		distortionClassifications.remove(east);
		distortionClassifications.add(panel);
		east = panel;
		frame.setVisible(frame.isVisible());
		
	}

}
