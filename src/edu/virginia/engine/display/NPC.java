package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.dialog.Dialog;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.InteractEvent;
import map.Map;

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
				this.face((e.getFacing() + 2) % 4);
				System.out.println("SIGN WORKS");
				DialogEvent de = new DialogEvent();
				ArrayList<String> dia = new ArrayList<String>();
				dia.add("How's it goin', bud?");
				dia.add("Did you know that the alpha version might be done in time?");
				dia.add("Neither did I!");
				de.setDialog(dia);
				this.dispatchEvent(de);
				//LAUNCH DIALOGUE
			}
		}
	}
	
	@Override
	public void update(ArrayList<String> keys, Map m) {
		super.update(keys, m);
		int rand = (int) (Math.random()*100);
		
		if (!this.moving && rand == 0) {
			int random = (int) (Math.random()*4);
			switch (random) {
			case 0:
				if (!m.checkCollision(yGrid-1, xGrid)) {
					up(m);
					break;
				}
			case 1:
				if (!m.checkCollision(yGrid, xGrid+1)) {
					right(m);
					break;
				}
			case 2:
				if (!m.checkCollision(yGrid+1, xGrid)) {
					down(m);
					break;
				}
			case 3:
				if (!m.checkCollision(yGrid, xGrid-1)) {
					left(m);
					break;
				}
			default:
				System.out.println("bad coding practice in NPC.java :(");
			}
			System.out.println("moved to " + xGrid + ", " + yGrid);
		}
	}
	
	
	
	//timer for player actions
	
}
