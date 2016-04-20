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

public class MushroomHuntQuest extends Quest {

	int QUEST_STATE = 0;

	final int NOT_STARTED = 0;
	final int QUEST_STARTED = 1;
	final int ITEM_GATHERED = 2;
	final int QUEST_COMPLETED = 3;

	public MushroomHuntQuest(ArrayList<IEventListener>EL){
		super();
		((EventDispatcher) EL.get(0)).addEventListener(this, "DIALOG_EVENT");
		this.addEventListener(EL.get(0), "DIALOG_CHANGE_EVENT");
	}


	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub

		switch (QUEST_STATE) {

		case NOT_STARTED:
			System.out.println("quest hasn't started!");
			if (event.eventType.equals("DIALOG_EVENT")) {
				DialogEvent de = (DialogEvent) event;
				System.out.println("checking if speaker is clone");
				if (de.speakerID.equals("mom")) {
					System.out.println("It is! Yay! Proceeding through quest!");
					QUEST_STATE++;
					System.out.println("Quest Started!");
				}
			}
			break;
		case QUEST_STARTED:
			System.out.println("Looking for mushroom...");
			if (event.eventType.equals("INTERACT_EVENT")) {
				InteractEvent ie = (InteractEvent) event;
				System.out.println("it's an interact event! yay!");

				ArrayList<Point> mushroomPatch = new ArrayList<Point>();
				mushroomPatch.add(new Point(15, 12));  //TODO: switch points
				mushroomPatch.add(new Point(16, 12));
				mushroomPatch.add(new Point(15, 13));
				mushroomPatch.add(new Point(16, 13));
				mushroomPatch.add(new Point(15, 14));
				mushroomPatch.add(new Point(16, 14));
				for (Point p : mushroomPatch) {
					if (p.x == ie.getX() && p.y == ie.getY()) {
						System.out.println("YOU GOT AN ICICLE FUCK YEAH");
						ArrayList<String> dial = new ArrayList<String>();
						dial.add("You found some Mushrooms!");
						dial.add("You feel proud as you think of your mom's warm soup and hug when you get back");
						DialogEvent de = new DialogEvent("fountain");
						de.setDialog(dial);
						this.dispatchEvent(de);
						QUEST_STATE++;
						ArrayList<String> dia = new ArrayList<String>();
						dia.add("Good Boy");
						dia.add("Your mother gently pats you on the head ");
						dia.add("You hug her back.");
						dia.add("*Your lucidity level has increased.*");
						DialogChangeEvent dce = new DialogChangeEvent(dia);
						this.dispatchEvent(dce);
					}
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
				if (de.speakerID.equals("clone")) {
					System.out.println("YOU FINISHED THE QUEST :)");
					QUEST_STATE++;
					System.out.println("Quest Completed! Lucidity Level Increased!");
				}
			}
			System.out.println(QUEST_STATE);
			break;
		case QUEST_COMPLETED:
			System.out.println("LAST STATE: " + event.eventType);
			LucidityChangeEvent lce = new LucidityChangeEvent(++Sys.LUCIDITY); // TODO:
			// make
			// it
			// Sys.LUCIDITY++
			this.dispatchEvent(lce);
			ArrayList<String> dia = new ArrayList<String>();
			dia.add("Are you goin out to play?");
			dia.add("Come back home quickly! Or your soup will get cold");
			DialogChangeEvent dce = new DialogChangeEvent(dia);
			this.dispatchEvent(dce);
			break;
		}
	}


}

