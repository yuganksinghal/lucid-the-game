package edu.virginia.engine.display;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;

public class Item extends Sprite implements IEventListener {
	
	String itemID;
	int quantity;

	public Item(String id) {
		super(id);
		this.itemID = id;
		construct();
	}
	
	public Item(String id, String img) {
		super(id,img);
		construct();
	}
	private void construct() {
	}

	@Override
	public void handleEvent(Event event) {

	}

}
