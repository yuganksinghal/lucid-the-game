package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.dialog.Dialog;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.InteractEvent;

public class Sign extends EventDispatcher implements IEventListener  {
	private int x;
	private int y;
	
	private String dialog;
	
	public Sign(int y, int x) {
		this.x = x;
		this.y = y;
		dialog = "This is a boat! Yay :)";
	}
	
	ArrayList<Dialog> dialogs;
	Dialog currentDialog = new Dialog();
	@Override
	public void handleEvent(Event event) {
		if (event.eventType.equals("INTERACT_EVENT")) {
			InteractEvent e = (InteractEvent) event;
			if (e.getX() == x && e.getY() == y) {
				System.out.println("SIGN WORKS");
				DialogEvent de = new DialogEvent();
				ArrayList<String> dia = new ArrayList<String>();
				dia.add("this is a boat");
				dia.add("yay!");
				dia.add("MOOORRREEE");
				de.setDialog(dia);
				this.dispatchEvent(de);
				//LAUNCH DIALOGUE
			}
		}
	}
	
}
