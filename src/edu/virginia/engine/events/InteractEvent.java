package edu.virginia.engine.events;

public class InteractEvent extends Event {
	private int x;
	private int y;
	private int facing;
	public InteractEvent(int x, int y, int facing) {
		this.x = x;
		this.y = y;
		this.facing = facing;
		this.eventType = "INTERACT_EVENT";
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	public int getFacing() {
		return facing;
	}
}
