package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.dialog.Dialog;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.InteractEvent;

public class NPC extends Walkable implements IEventListener {
	
	ArrayList<Dialog> dialogs;
	Dialog currentDialog = new Dialog();

	public NPC(String id, String imageFileName) {
		super(id, imageFileName);
		construct();
	}
	
	public NPC(String id) {
		super(id);
		construct();
	}
	
	public void construct() {
		dialogs = new ArrayList<Dialog>();
	}

	@Override
	public void handleEvent(Event event) {
		if (event.eventType.equals("INTERACT_EVENT")) {
			InteractEvent e = (InteractEvent) event;
			if (e.getX() == xGrid && e.getY() == yGrid) {
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
