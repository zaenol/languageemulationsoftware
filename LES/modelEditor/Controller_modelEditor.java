package modelEditor;

import modelEditor.model.Model;
import modelEditor.view.View_modelEditor;

public class Controller_modelEditor {
	
	Model model;
	View_modelEditor view;
	
	
	public Controller_modelEditor(){
		model = new Model();
		view = new View_modelEditor();
		view.setWordPanel(model.getWordDist().getGUI());
		view.setInflectionPanel(model.getInflectionDist().getGUI());
		view.setNonFluencyPanel(model.getNonFluencyDist().getGUI());
		view.setFunctionWordPanel(model.getFunctionDist().getGUI());
	}
	

	public void setVisible(boolean visible){
		view.setVisible(visible);
	}

}