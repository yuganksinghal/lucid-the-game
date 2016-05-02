package edu.virginia.engine.map;

import java.awt.Point;

import edu.virginia.engine.Sys;
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
		Sys.MC.addEventListener(this, "INTERACT_EVENT");
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		if (event.eventType.equals("INTERACT_EVENT")) {
			InteractEvent ie = (InteractEvent) event;
			if (ie.getX() == pointA.x && ie.getY() == pointA.y) {
				if (pointB == null)
					return;
				//move player to pointB
				Sys.MC.teleport(pointB.x, pointB.y, Sys.currentMap);
			}
		}
	}
	
	public void setPointB(Point B){
		this.pointB = B;
	}
}
