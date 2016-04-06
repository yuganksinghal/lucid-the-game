package edu.virginia.engine.events;

import java.util.ArrayList;

public class DialogEvent extends Event {
	ArrayList<String> dialog;
	public DialogEvent() {
		this.eventType = "DIALOG_EVENT";
		this.dialog = new ArrayList<String>();
	}
	
	public void setDialog(ArrayList<String> dia) {
		this.dialog = dia;
	}
	public ArrayList<String> getDialog() {
		return dialog;
	}
}
