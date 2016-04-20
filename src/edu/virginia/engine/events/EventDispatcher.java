package edu.virginia.engine.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDispatcher implements IEventDispatcher {
	protected HashMap<String, ArrayList<IEventListener>> listeners = new HashMap<String, ArrayList<IEventListener>>();

	public void addEventListener(IEventListener listener, String eventType) {
		if (listeners.get(eventType) == null) {
			ArrayList<IEventListener> tempList = new ArrayList<IEventListener>();
			listeners.put(eventType, tempList);
		}
		listeners.get(eventType).add(listener);
	}
	public void removeEventListener(IEventListener listener, String eventType) {
		listeners.get(eventType).remove(listener);
	}
	public void dispatchEvent(Event event) {
		event.source = this;
		if (listeners.get(event.eventType) != null)
			for (IEventListener ls : listeners.get(event.eventType)) {
				ls.handleEvent(event);
			}
	}
	public boolean hasEventListener(IEventListener listener, String eventType) {
		if(listeners.containsKey(eventType)){
			return listeners.get(eventType).contains(listener);}
		else
			return false;
	}
}
