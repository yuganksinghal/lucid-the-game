package edu.virginia.engine.display;

import java.util.ArrayList;

import dialog.Dialog;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;

public class NPC extends Walkable implements IEventListener{
	
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
		if (event.eventType.equals(currentDialog.getTrigger().eventType)) {
			currentDialog = currentDialog.getNext();
		}
	}
	
	
}
