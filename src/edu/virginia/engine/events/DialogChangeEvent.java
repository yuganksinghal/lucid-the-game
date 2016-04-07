package edu.virginia.engine.events;

import java.util.ArrayList;

public class DialogChangeEvent extends Event {
	public ArrayList<String> dialog;
	
	public DialogChangeEvent(ArrayList<String> dia) {
		dialog = dia;
		this.eventType = "DIALOG_CHANGE_EVENT";
	}
}
