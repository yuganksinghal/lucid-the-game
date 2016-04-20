package edu.virginia.quest;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.Sys;
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
				if (de.speakerID.equals("dog")) {
					QUEST_STATE++;
				}
			}
			break;
		case QUEST_STARTED:
			System.out.println("Looking for medkit...");
			if (event.eventType.equals("INTERACT_EVENT")) {
				InteractEvent ie = (InteractEvent) event;
				System.out.println("it's an interact event! yay!");

				Point p = new Point(78, 38); // TODO: add points for getting MED
				// KIT;
				if (p.x == ie.getX() && p.y == ie.getY()) {
					System.out.println("YOU GOT AN MEDKIT");
					ArrayList<String> dial = new ArrayList<String>();
					dial.add("You patch yourself up with some antibiotics and bandages");
					dial.add("It still hurts.");
					dial.add("You notice a shimmering boy next to the bookshelf.");
					dial.add("Was he always there?");

					DialogEvent de = new DialogEvent("medkit");
					de.setDialog(dial);
					this.dispatchEvent(de);
					QUEST_STATE++;
					LucidityChangeEvent lce = new LucidityChangeEvent(--Sys.LUCIDITY); // TODO:
					// make
					// it
					// Sys.LUCIDITY++
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
