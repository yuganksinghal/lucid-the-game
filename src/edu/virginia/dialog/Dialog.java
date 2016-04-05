package edu.virginia.dialog;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;

public class Dialog implements IEventListener {
	String text;
	private Dialog next;
	private Event trigger;
	
	
	
	@Override
	public void handleEvent(Event event) {
		if (getTrigger().eventType.equals(event.eventType)) {
			
		}
	}



	public Event getTrigger() {
		return trigger;
	}



	public void setTrigger(Event trigger) {
		this.trigger = trigger;
	}



	public Dialog getNext() {
		return next;
	}



	public void setNext(Dialog next) {
		this.next = next;
	}
	
	//field for the trigger
}
