package modelEditor;

import modelEditor.model.Model;
import modelEditor.model.Model_Message;
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
		view.setOtherPnale(model.getOtherDist().getGUI());
	}
	
	public Model_Message distortMessage(Model_Message message){
		return model.distortMessage(message);
	}

	public void setVisible(boolean visible){
		view.setVisible(visible);
	}

}
