package edu.virginia.engine.display;

import edu.virginia.engine.Sys;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.PickedUpEvent;
import edu.virginia.lab1test.LabOneGame;

public class Item extends Sprite implements IEventListener {

	public Item(String id) {
		super(id);
		construct();
	}
	
	public Item(String id, String img) {
		super(id,img);
		construct();
	}
	private void construct() {
		this.fixed = false;
	}

	@Override
	public void handleEvent(Event event) {
		if (event.eventType.equals(PickedUpEvent.COIN_PICKED_UP) && ((PickedUpEvent) event).item == this) {
//			Sys.spriteList.remove(this);
			Sys.itemList.remove(this);
			this.setExists(false);
			System.out.println(this.id + " picked up!");
		}
	}

}
