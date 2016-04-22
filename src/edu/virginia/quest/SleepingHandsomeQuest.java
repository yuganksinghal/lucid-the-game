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

public class SleepingHandsomeQuest extends Quest {

	int QUEST_STATE = 0;

	final int NOT_STARTED = 0;
	final int QUEST_STARTED = 1;
	final int ITEM_GATHERED = 2;
	final int QUEST_COMPLETED = 3;

	public SleepingHandsomeQuest(ArrayList<IEventListener> EL) {
		super();
		for (IEventListener a : EL) {
			((EventDispatcher) a).addEventListener(this, "DIALOG_EVENT");
			this.addEventListener(a, "DIALOG_CHANGE_EVENT");
		}
		this.addEventListener(this, "DIALOG_EVENT");
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub

		switch (QUEST_STATE) {

		case NOT_STARTED:
			if (event.eventType.equals("DIALOG_EVENT")) {
				DialogEvent de = (DialogEvent) event;
				System.out.println("checking if speaker is clone");
				if (de.speakerID.equals("boy")) {
					System.out.println("It is! Yay! Proceeding through quest!");
					QUEST_STATE++;
					System.out.println("Quest Started!");
				}
			}
			break;
		case QUEST_STARTED:
			System.out.println("Looking for a book...");
			if (event.eventType.equals("INTERACT_EVENT")) {
				InteractEvent ie = (InteractEvent) event;
				System.out.println("it's an interact event! yay!");

				Point bookshelf = new Point(82, 36);
					if (bookshelf.x == ie.getX() && bookshelf.y == ie.getY()) {
						ArrayList<String> dial = new ArrayList<String>();
						dial.add("You got ____.");
						dial.add("It seems strange");
						DialogEvent de = new DialogEvent("bookshelf");
						de.setDialog(dial);
						this.dispatchEvent(de);
						QUEST_STATE++;
						ArrayList<String> dia = new ArrayList<String>();
						dia.add("Awesome! You got the ____.");
						dia.add("Don't forget to give it to her.");
						DialogChangeEvent dce = new DialogChangeEvent(dia, "boy");
						
						this.dispatchEvent(dce);
						
						ArrayList<String> dia2 = new ArrayList<String>();
						dia2.add("This ___...");
						dia2.add("Who told you about this?");
						dia2.add("Just leave me alone!");
						dia2.add("*Your lucidity level has decreased.*");
						DialogChangeEvent dce2 = new DialogChangeEvent(dia2, "Part-Time Worker");
						
						this.dispatchEvent(dce2);
					}
				System.out.println(QUEST_STATE);
				// TODO: implement comparable interface for different objects,
				// including points
				// TODO: standardize x,y stuff so x,y for parameters and y,x in
				// method bodies
			}
			break;
		case ITEM_GATHERED:
			System.out.println("Items Gathered Executing");
			if (event.eventType.equals("DIALOG_EVENT")) {
				DialogEvent de = (DialogEvent) event;
				System.out.println("checking if speaker is clone");
				if (de.speakerID.equals("Part-Time Worker")) {
					System.out.println("YOU FINISHED THE QUEST :)");
					QUEST_STATE++;
					System.out.println("Quest Completed! Lucidity Level Decreased!");
				}
			}
			System.out.println(QUEST_STATE);
			break;
		case QUEST_COMPLETED:
			System.out.println("LAST STATE: " + event.eventType);
			LucidityChangeEvent lce = new LucidityChangeEvent(--Sys.LUCIDITY); // TODO:
			// make
			// it
			// Sys.LUCIDITY++
			this.dispatchEvent(lce);
			ArrayList<String> dia = new ArrayList<String>();
			break;
		}

	}

}
