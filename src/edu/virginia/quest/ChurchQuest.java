package edu.virginia.quest;

import edu.virginia.engine.events.Event;

public class ChurchQuest {
	//FSM: 0 - the quest is not accessible
	//FSM: 1 - the quest is accessible
	//FSM: 2 - you have talked to the quest giver
	//FSM: 3 - you have lowered your lucidity enough
	//FSM: 4 - you go to the church
	//FSM: 5 - you have interacted with the altar
			//you find your mom's car keys on the altar
			//the interior of the church gains a red tint
	int QUEST_STATE = 0;
	
	public void handleEvent(Event event) {
		switch (QUEST_STATE) {
		case 0:
			if (event.eventType.equals("QUEST_COMPLETE_EVENT")) {
				QuestCompleteEvent qce = (QuestCompleteEvent) event;
				if (qce.questId.equals("") {
					
				})
			}
			//when prereq quest is complete ++
			break;
		case 1:
			if (event.eventType.equals("DIALOG_EVENT")) {
				
			}
			//if your lucidity is low enough, the man tells you to go to the church
				//QS++
			//if your lucidity isn't low enough yet, the man tells you that
				//"come back to me when you're a little less sane"
			break;
		case 2:
			//if you interact with the item in the church:
				//lower your lucidity by another level, turning room red
				//++
			break;
		case 3:
			//you take the keys back home
			//quest is basically over
			//drops you to lowest lucidity
			//activates ability for final quest to exist
			//++
			break;
		case 4:
			//
			break;
		}
	}
}
