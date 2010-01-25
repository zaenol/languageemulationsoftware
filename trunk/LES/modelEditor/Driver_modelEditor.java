package modelEditor;

import modelEditor.model.Model;
import modelEditor.view.View_modelEditor;

public class Driver_modelEditor {
	
	Model model;
	View_modelEditor view;
	
	
	public Driver_modelEditor(){
		model = new Model();
		view = new View_modelEditor();
		view.setWordPanel(model.getWordDist().getGUI());
		view.setInflectionPanel(model.getInflectionDist().getGUI());
		view.setNonFluencyPanel(model.getNonFluencyDist().getGUI());
		view.setFunctionWordPanel(model.getFunctionDist().getGUI());
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Driver_modelEditor me = new Driver_modelEditor();
		

	}

}
