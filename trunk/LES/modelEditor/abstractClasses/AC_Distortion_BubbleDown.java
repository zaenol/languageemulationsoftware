package modelEditor.abstractClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import modelEditor.eventsListeners.BubbleDown_Event;
import modelEditor.eventsListeners.BubbleDown_Listener;

public abstract class AC_Distortion_BubbleDown extends AC_Distortion implements BubbleDown_Listener {
	
	private double severityValue_global = 0;
	private double severityValue_local = 0;


	private releaseBoxClass releaseBox;
	
	public AC_Distortion_BubbleDown(String id, boolean anomic, boolean agrammatic, boolean distortion_error, boolean distortion_correction){
		super(id,anomic,agrammatic,distortion_error,distortion_correction);
		
		releaseBox = new releaseBoxClass();
		
		//this.headerPanel.add(releaseBox.getReleaseBox());
		this.addToTopOfMasterPanel(releaseBox.getReleaseBox());
		
		//releaseBox.addActionListener(this);
	}

	public void bubbleDownEventDetected(BubbleDown_Event e) {
		Double value = e.getValue();
		this.setSeverityValue_global(value);
		if(!releaseBox.isReleased())
			this.setSeverityValue_local(value);
		update();
	}

	public double getSeverityValue_global() {
		return severityValue_global;
	}

	public void setSeverityValue_global(double severityValue_global) {
		this.severityValue_global = severityValue_global;
		update();
	}

	public double getSeverityValue_local() {
		return severityValue_local;
	}

	public void setSeverityValue_local(double severityValue_local) {
		this.severityValue_local = severityValue_local;
		update();
	}

	public boolean isReleased() {
		return releaseBox.isReleased();
	}

	public void setReleased(boolean released) {
		if(!released){
			severityValue_local = severityValue_global;
		}
		
		releaseBox.setReleased(released);
		update();
	}
	private class releaseBoxClass implements ActionListener{
		private JCheckBox releaseBox;
		private boolean released = false; // if released is true, use local value
		
		public releaseBoxClass(){
			releaseBox = new JCheckBox("Manual Value Change");
			releaseBox.addActionListener(this);
		}

		public JCheckBox getReleaseBox() {
			return releaseBox;
		}

		public void setReleaseBox(JCheckBox releaseBox) {
			this.releaseBox = releaseBox;
		}

		public boolean isReleased() {
			return released;
		}

		public void setReleased(boolean released) {
			this.released = released;
		}

		public void actionPerformed(ActionEvent e) {
			AC_Distortion_BubbleDown.this.setReleased(this.releaseBox.isSelected());
			
		}
		
	}

	
}
