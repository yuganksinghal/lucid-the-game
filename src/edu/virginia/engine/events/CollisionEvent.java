package edu.virginia.engine.events;

import edu.virginia.engine.display.DisplayObject;

public class CollisionEvent extends Event {
	public static String COLLISION_DETECTED = "Collision Detected";
	public DisplayObject o1;
	public DisplayObject o2;
	
	public CollisionEvent(DisplayObject obj1, DisplayObject obj2) {
		this.eventType = COLLISION_DETECTED;
		o1 = obj1;
		o2 = obj2;
	}
}
