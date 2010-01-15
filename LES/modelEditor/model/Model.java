package modelEditor.model;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.util.Vector;

import javax.swing.JFrame;

import modelEditor.interfaces.I_Element;

public class Model extends Model_findDistortions{


	JFrame frame;
	
	public Model() {
		super();
		find_distortions();
		
		frame = new JFrame();
		frame.setSize(900,600);
		frame.add(anomicDist.getGUI());
		frame.add(agramaticDist.getGUI());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	

    
}
