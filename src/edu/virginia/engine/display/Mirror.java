package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.InteractEvent;

public class Mirror extends Sign {

	public Mirror(String id, int y, int x) {
		super(id, y, x);
		// TODO Auto-generated constructor stub
		Sys.MC.addEventListener(this, "INTERACT_EVENT");
		this.addEventListener(Sys.instance, "DIALOG_EVENT");
		this.addDialogLine("TEST");
	}
	
	@Override
	public void handleEvent(Event event) {
		System.out.println("saw mirror");
		if (event.eventType.equals("INTERACT_EVENT")) {
			InteractEvent ie = (InteractEvent) event;
			for (Point p : tiles) {
				System.out.println("are you interacting with the mirror...?");
				System.out.println("p.x: " + p.x + ", p.y: " + p.y + ", i.x: " + ie.getX() + ", i.y: " + ie.getY());
				if (p.x == ie.getX() && p.y == ie.getY()) {
					System.out.println("hmmmm");
					ArrayList<String> dia = new ArrayList<String>();
					dia.add("You look deep into the eyes of your reflection...");
					dia.add("LUCIDITY: " + Sys.LUCIDITY);
					
					DialogEvent de = new DialogEvent("mirror");
					de.setDialog(dia);
					this.dispatchEvent(de);
				}
			}
		}
	}

}
