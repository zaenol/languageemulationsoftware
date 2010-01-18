package modelEditor.model;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.util.Vector;

import javax.swing.JFrame;

import modelEditor.interfaces.I_Element;

public class Model extends Model_findDistortions{


	
	
	public Model() {
		super();
		find_distortions();
		
		
		//frame.add(anomicDist.getGUI());
		//frame.add(agramaticDist.getGUI());
		
	}

	
	public Classification getAnomicDist() {
		return anomicDist;
	}

	public Classification getAgramaticDist() {
		return agramaticDist;
	}
    
}
