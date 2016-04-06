package edu.virginia.engine.events;

public class InteractEvent extends Event {
	private int x;
	private int y;
	public InteractEvent(int x, int y) {
		this.x = x;
		this.y = y;
		this.eventType = "INTERACT_EVENT";
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
