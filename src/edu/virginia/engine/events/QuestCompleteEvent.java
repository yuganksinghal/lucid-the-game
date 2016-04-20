package edu.virginia.engine.events;

public class QuestCompleteEvent extends Event {
	public String questId;
	
	
	public QuestCompleteEvent(String questId) {
		this.questId = questId;
	}
	
	
}
