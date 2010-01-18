package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

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
		
		container.add(title, BorderLayout.NORTH);
		//container.add(west,BorderLayout.WEST);
		container.add(east,BorderLayout.EAST);
		
		frame.setVisible(true);
		
	}
	public void setAnomic(JPanel panel){
		container.remove(west);
		container.add(panel,BorderLayout.CENTER);
		west = panel;
		frame.setVisible(container.isVisible());
	}
	public void setAgrammatic(JPanel panel){
		container.remove(east);
		container.add(panel,BorderLayout.EAST);
		east = panel;
		frame.setVisible(container.isVisible());
		
	}

}
