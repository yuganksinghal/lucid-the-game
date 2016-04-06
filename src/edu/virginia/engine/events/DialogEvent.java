package edu.virginia.engine.events;

import java.util.ArrayList;

public class DialogEvent extends Event {
	ArrayList<String> dialog;
	public String speakerID;
	public DialogEvent(String speakerID) {
		this.eventType = "DIALOG_EVENT";
		this.speakerID = speakerID;
	}
	
	public void setDialog(ArrayList<String> dia) {
		this.dialog = dia;
	}
	public ArrayList<String> getDialog() {
		return dialog;
	}
}
