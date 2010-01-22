package modelEditor;

import view.View_modelEditor;
import modelEditor.model.Model;

public class Driver_modelEditor {
	
	Model model;
	View_modelEditor view;
	
	
	public Driver_modelEditor(){
		model = new Model();
		view = new View_modelEditor();
		view.setWordPanel(model.getAnomicDist().getGUI());
		view.setInflectionPanel(model.getAgramaticDist().getGUI());
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Driver_modelEditor me = new Driver_modelEditor();
		

	}

}
