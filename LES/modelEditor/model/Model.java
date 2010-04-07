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
		
	}
	
	public Model_Message distortMessage(Model_Message message){
		//Model_Message mm = new Model_Message(message);
		Model_Message mm = message;
		
		mm = nonFluencyDist.parseMessage(mm);
		mm = otherDist.parseMessage(mm);
		mm = functionDist.parseMessage(mm);
		mm = wordDist.parseMessage(mm);
		mm = inflectionDist.parseMessage(mm);
		
		
		
		return mm;
	}

	
	public Classification getWordDist() {
		return wordDist;
	}

	public Classification getInflectionDist() {
		return inflectionDist;
	}
    public Classification getFunctionDist() {
		return functionDist;
	}

	public Classification getNonFluencyDist() {
		return nonFluencyDist;
	}
	
	public Classification getOtherDist(){
		return otherDist;
	}
    
}
