package edu.virginia.engine.map;

import java.awt.Point;

import edu.virginia.engine.display.Interactable;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.InteractEvent;

public class Portal implements Interactable {
	public Point pointA;
	public Point pointB;
	public Map toMap;
	
	public Portal(int ax, int ay, int bx, int by) {
		pointA = new Point(ax, ay);
		pointB = new Point(bx, by);
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		if (event.eventType.equals("INTERACT_EVENT")) {
			InteractEvent ie = (InteractEvent) event;
			if (ie.getX() == pointA.x && ie.getY() == pointA.y) {
				//move player to pointB
			}
			else if (ie.getX() == pointB.x && ie.getY() == pointB.y) {
				//move player to pointA
			}
		}
	}
}
