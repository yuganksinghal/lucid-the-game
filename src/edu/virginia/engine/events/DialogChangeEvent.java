package edu.virginia.engine.events;

import java.util.ArrayList;

public class DialogChangeEvent extends Event {
	public ArrayList<String> dialog;
	public String speakerId;
	
	public DialogChangeEvent(ArrayList<String> dia, String speakerId) {
		dialog = dia;
		this.eventType = "DIALOG_CHANGE_EVENT";
		this.speakerId = speakerId;
	}
}
