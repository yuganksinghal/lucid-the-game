package edu.virginia.engine.events;


public class QuestManager extends EventDispatcher {
	@Override
	public void dispatchEvent(Event event) {
		super.dispatchEvent(event);
		System.out.println("Quest completed!");
	}
}
