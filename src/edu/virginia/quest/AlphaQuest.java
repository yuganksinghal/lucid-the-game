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

public class AlphaQuest extends Quest {

	// FSM:
	// 0 - you haven't talked to quest giver
	// 1 - you just talked to quest giver
	// 2 - you have the icicle
	// 3 - you have talked to the quest giver and turned in the icicle (LUCID++)

	int QUEST_STATE = 0;

	final int NOT_STARTED = 0;
	final int QUEST_STARTED = 1;
	final int ITEM_GATHERED = 2;
	final int QUEST_COMPLETED = 3;
	
	public AlphaQuest(ArrayList<IEventListener>EL){
		super();
		((EventDispatcher) EL.get(0)).addEventListener(this, "DIALOG_EVENT");
		this.addEventListener(EL.get(0), "DIALOG_CHANGE_EVENT");
	}

	@Override
	public void handleEvent(Event event) {
		switch (QUEST_STATE) {

		case NOT_STARTED:
			if (event.eventType.equals("DIALOG_EVENT")) {
				DialogEvent de = (DialogEvent) event;
				System.out.println("checking if speaker is clone");
				if (de.speakerID.equals("clone")) {
					System.out.println("It is! Yay! Proceeding through quest!");
					QUEST_STATE++;
					System.out.println("Quest Started!");
				}
			}
			break;
		case QUEST_STARTED:
			System.out.println("Looking for icicle...");
			if (event.eventType.equals("INTERACT_EVENT")) {
				InteractEvent ie = (InteractEvent) event;
				System.out.println("it's an interact event! yay!");

				ArrayList<Point> fountain = new ArrayList<Point>();
				fountain.add(new Point(15, 12));
				fountain.add(new Point(16, 12));
				fountain.add(new Point(15, 13));
				fountain.add(new Point(16, 13));
				fountain.add(new Point(15, 14));
				fountain.add(new Point(16, 14));
				for (Point p : fountain) {
					if (p.x == ie.getX() && p.y == ie.getY()) {
						System.out.println("YOU GOT AN ICICLE FUCK YEAH");
						ArrayList<String> dial = new ArrayList<String>();
						dial.add("You break off some of the ice and put it in your pocket.");
						dial.add("It's real cold.");
						DialogEvent de = new DialogEvent("fountain");
						de.setDialog(dial);
						this.dispatchEvent(de);
						QUEST_STATE++;
						ArrayList<String> dia = new ArrayList<String>();
						dia.add("You found the ice! Thank you so much.");
						dia.add("Looks like I'll live another day!...");
						dia.add("*Your lucidity level has increased.*");
						DialogChangeEvent dce = new DialogChangeEvent(dia);
						this.dispatchEvent(dce);
					}
				}
				// TODO: implement comparable interface for different objects,
				// including points
				// TODO: standardize x,y stuff so x,y for parameters and y,x in
				// method bodies
			}
			break;
		case ITEM_GATHERED:
			if (event.eventType.equals("DIALOG_EVENT")) {
				DialogEvent de = (DialogEvent) event;
				System.out.println("checking if speaker is clone");
				if (de.speakerID.equals("clone")) {
					System.out.println("YOU FINISHED THE QUEST :)");
					QUEST_STATE++;
					System.out.println("Quest Completed! Lucidity Level Increased!");
				}
			}
			break;
		case QUEST_COMPLETED:
			System.out.println("LAST STATE");
			LucidityChangeEvent lce = new LucidityChangeEvent(++Sys.LUCIDITY); // TODO:
			// make
			// it
			// Sys.LUCIDITY++
			this.dispatchEvent(lce);
			ArrayList<String> dia = new ArrayList<String>();
			dia.add("I have to eat once every few years...");
			dia.add("...");
			dia.add("...or my tummy gets upset.");
			DialogChangeEvent dce = new DialogChangeEvent(dia);
			this.dispatchEvent(dce);
			break;
		}
	}

}
