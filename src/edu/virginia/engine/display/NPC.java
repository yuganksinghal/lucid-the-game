package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.events.DialogChangeEvent;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.InteractEvent;
import edu.virginia.engine.map.Map;

public class NPC extends Walkable implements Interactable {
	
	ArrayList<String> dialog;

	public NPC(String id, String imageFileName) {
		super(id, imageFileName);
		construct();
	}
	
	public NPC(String id) {
		super(id);
		construct();
	}
	
	public void construct() {
		dialog = new ArrayList<String>();
	}
	
	public void setDialog(ArrayList<String> dia) {
		this.dialog = dia;
	}
	
	public void addDialogLine(String s) {
		this.dialog.add(s);
		if(!this.hasEventListener(Sys.instance, "DIALOG_EVENT"))
			this.addEventListener(Sys.instance, "DIALOG_EVENT");
		if(!Sys.MC.hasEventListener(this, "INTERACT_EVENT"))
			Sys.MC.addEventListener(this, "INTERACT_EVENT");
	}
	
	public void clearDialog() {
		this.dialog.clear();
	}

	@Override
	public void handleEvent(Event event) {
		if (event.eventType.equals("INTERACT_EVENT")) {
			InteractEvent e = (InteractEvent) event;
			if (e.getX() == xGrid && e.getY() == yGrid) {
				this.face((e.getFacing() + 2) % 4);
				System.out.println("NPC WORKS" + this.getId());
				DialogEvent de = new DialogEvent(id);
				de.setDialog(this.dialog);
				this.dispatchEvent(de);
				//LAUNCH DIALOGUE
			}
		}
		if (event.eventType.equals("DIALOG_CHANGE_EVENT")) {
			DialogChangeEvent dce = (DialogChangeEvent) event;
			if (dce.speakerId.equals(this.id)) {
				this.dialog = dce.dialog;
				System.out.println(this.dialog);
			}
		}
	}
	
	@Override
	public void update(ArrayList<String> keys, Map m) {
		super.update(keys, m);
		int rand = (int) (Math.random()*200);
		
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
				break;
			default:
				System.out.println("bad coding practice in NPC.java :(");
			}
			//System.out.println("moved to " + xGrid + ", " + yGrid);
		}
	}
	
	
	
	//timer for player actions
	
}
