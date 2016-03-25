package edu.virginia.engine.events;

import edu.virginia.engine.display.Item;

public class PickedUpEvent extends Event {
	public static String COIN_PICKED_UP = "coin picked up";
	public Item item;
	
	public PickedUpEvent(Item i) {
		this.eventType = COIN_PICKED_UP;
		item = i;
	}
}
