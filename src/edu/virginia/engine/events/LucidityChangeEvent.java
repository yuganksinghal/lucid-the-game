package edu.virginia.engine.events;

public class LucidityChangeEvent extends Event {
	public int lucidity;
	public LucidityChangeEvent(int i) {
		lucidity = i;
		this.eventType = "LUCIDITY_CHANGE_EVENT";
	}
}
