package edu.virginia.engine.events;

public class CollisionManager extends EventDispatcher {
	@Override
	public void dispatchEvent(Event event) {
		super.dispatchEvent(event);
		CollisionEvent e = (CollisionEvent) event;
//		System.out.println("Collision between " + ((CollisionEvent) event).o1.getId() + " and " + ((CollisionEvent) event).o2.getId() + ".");
	}
}
