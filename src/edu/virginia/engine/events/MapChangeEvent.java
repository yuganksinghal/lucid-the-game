package edu.virginia.engine.events;

public class MapChangeEvent extends Event {
	public String mapName;
	
	public MapChangeEvent(String mapName) {
		this.eventType = "MAP_CHANGE_EVENT";
		this.mapName = mapName;
	}
}