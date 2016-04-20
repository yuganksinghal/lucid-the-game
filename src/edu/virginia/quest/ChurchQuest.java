package edu.virginia.quest;

import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.events.DialogChangeEvent;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.QuestCompleteEvent;

public class ChurchQuest extends Quest {
	//FSM: 0 - the quest is not accessible
	//FSM: 1 - the quest is accessible
	//FSM: 2 - you have talked to the quest giver
	//FSM: 3 - you have lowered your lucidity enough
	//FSM: 4 - you go to the church
	//FSM: 5 - you have interacted with the altar
			//you find your mom's car keys on the altar
			//the interior of the church gains a red tint
	int QUEST_STATE = 0;
	boolean PREREQ_1_COMPLETED = false;
	boolean PREREQ_2_COMPLETED = false;
	
	
	public void handleEvent(Event event) {
		switch (QUEST_STATE) {
		case 0:
			if (event.eventType.equals("QUEST_COMPLETE_EVENT")) {
				QuestCompleteEvent qce = (QuestCompleteEvent) event;
				if (qce.questId.equals("DOG_BITE")) {
					PREREQ_1_COMPLETED = true;
				}
				if (qce.questId.equals("SLEEPING_HANDSOME")) {
					PREREQ_2_COMPLETED = true;
				}
			}
			if (PREREQ_1_COMPLETED && PREREQ_2_COMPLETED) {
				QUEST_STATE++;
				if (Sys.LUCIDITY >= 3) {
					//come back when you're a little less sane
					ArrayList<String> dia = new ArrayList<String>();
					dia.add("I have to eat once every few years...");
					dia.add("...");
					dia.add("...or my tummy gets upset.");
					DialogChangeEvent dce = new DialogChangeEvent(dia, "old man");
					this.dispatchEvent(dce);
				}
				else {
					//go to the woods
				}
			}
			//when prereq quest is complete ++
			break;
		case 1:
			if (Sys.LUCIDITY >= 3) {
				//come back when you're a little less sane
			}
			else {
				//go to the woods
			}
			if (event.eventType.equals("DIALOG_EVENT")) {
				//check if speaker is old man
				DialogEvent de = (DialogEvent) event;
				if (de.speakerID.equals("old man")) {

				}
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
