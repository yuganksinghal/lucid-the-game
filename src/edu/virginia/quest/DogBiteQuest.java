/**
 * 
 */
package edu.virginia.quest;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.events.DialogChangeEvent;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.InteractEvent;
import edu.virginia.engine.events.LucidityChangeEvent;

/**
 * @author Yugank Singhal
 *
 */

public class DogBiteQuest extends Quest {

	int QUEST_STATE = 0;

	final int NOT_STARTED = 0;
	final int QUEST_STARTED = 1;
	final int QUEST_COMPLETED = 2;
	
	public DogBiteQuest(ArrayList<IEventListener>EL){
		super();
		for (IEventListener a: EL){
			((EventDispatcher) a).addEventListener(this, "DIALOG_EVENT");
			this.addEventListener(a, "DIALOG_CHANGE_EVENT");
		}
		this.addEventListener(this, "DIALOG_EVENT");
		System.out.println("DBQ: " + this.listeners);
	}

	@Override
	public void handleEvent(Event event) {
		System.out.println("got Event: " + event.eventType);
		// TODO Auto-generated method stub
		switch (QUEST_STATE) {
		case NOT_STARTED:
			if (event.eventType.equals("DIALOG_EVENT")) {
				DialogEvent de = (DialogEvent) event;
				System.out.println("checking if speaker is clone");
				if (de.speakerID.equals("Dog")) {
					QUEST_STATE++;
					//when player gets bitten:
					//change mom dialog
					//change dog dialog
					//change guy dialog
					ArrayList<String> momDia = new ArrayList<String>();
					ArrayList<String> dogDia = new ArrayList<String>();
					ArrayList<String> guyDia = new ArrayList<String>();
					
					momDia.add("You're bleeding!");
					momDia.add("You should put a bandage on that...");
					
					dogDia.add("The dog growls menacingly...");
					dogDia.add("You don't try to pet it.");
					
					guyDia.add("I have lawyers too, you know!");
					guyDia.add("I promise he's usually friendly!");
					
					DialogChangeEvent momDCE = new DialogChangeEvent(momDia, "Mom");
					this.dispatchEvent(momDCE);
					
					DialogChangeEvent dogDCE = new DialogChangeEvent(dogDia, "Dog");
					this.dispatchEvent(dogDCE);
					
					DialogChangeEvent guyDCE = new DialogChangeEvent(guyDia, "Old Man");
					this.dispatchEvent(guyDCE);
					
					
				}
			}
			break;
		case QUEST_STARTED:
			System.out.println("Looking for medkit...");
			if (event.eventType.equals("INTERACT_EVENT")) {
				InteractEvent ie = (InteractEvent) event;
				System.out.println("it's an interact event! yay!");

				Point p = new Point(78, 38); // TODO: add points for getting MED KIT;
				if (p.x == ie.getX() && p.y == ie.getY()) {
					System.out.println("YOU GOT AN MEDKIT");
					ArrayList<String> dial = new ArrayList<String>();
					dial.add("You patch yourself up with some antibiotic ointment");
					dial.add("and a bandaid. It still stings...");
					dial.add("You notice a shimmering group of boys...");
					dial.add("Were they always there?");
					
					DialogEvent de = new DialogEvent("medkit");
					de.setDialog(dial);
					this.dispatchEvent(de);
					
					ArrayList<String> momDia = new ArrayList<String>();
					momDia.add("Glad to see you all patched up!");
					momDia.add("Do you think you could get some mushrooms for me?");
					momDia.add("There are a bunch growing to the south of town.");
					momDia.add("I need one of the bigs ones for my soup.");
					momDia.add("Thanks honey!");
					momDia.add("Oh! Before I forget, don't eat them raw.");
					momDia.add("They're poisonous when they're raw!");
					momDia.add("Thanks honey! I'll be waiting on you!");
					DialogChangeEvent dce = new DialogChangeEvent(momDia, "Mom");
					this.dispatchEvent(dce);
					
					QUEST_STATE++;
					LucidityChangeEvent lce = new LucidityChangeEvent(--Sys.LUCIDITY); // TODO:
					this.dispatchEvent(lce);
				}
				// TODO: implement comparable interface for different objects,
				// including points
				// TODO: standardize x,y stuff so x,y for parameters and y,x in
				// method bodies
			}
			break;
		case QUEST_COMPLETED:
			System.out.println("LAST STATE");

			break;
		}

	}

}
