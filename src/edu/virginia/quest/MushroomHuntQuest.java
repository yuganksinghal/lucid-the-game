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
		Sys.MC.addEventListener(this, "INTERACT_EVENT");
		this.addEventListener(EL.get(0), "DIALOG_CHANGE_EVENT");
		this.questId = "MUSHROOM_HUNT";
		
	}


	@Override
	public void handleEvent(Event event) {
		System.out.println("got mushroom quest event");

		switch (QUEST_STATE) {

		case NOT_STARTED:
			if (event.eventType.equals("DIALOG_EVENT")) {
				DialogEvent de = (DialogEvent) event;
				if (de.speakerID.equals("Mom")) {
					QUEST_STATE++;
					ArrayList<String> dia = new ArrayList<String>();
					dia.add("Hey hon.");
					dia.add("Did you get those mushrooms like I asked?");
					DialogChangeEvent dce = new DialogChangeEvent(dia, "Mom");
					this.dispatchEvent(dce);
					
				}
			}
			break;
		case QUEST_STARTED:
			if (event.eventType.equals("INTERACT_EVENT")) {
				InteractEvent ie = (InteractEvent) event;
				ArrayList<Point> mushroomPatch = new ArrayList<Point>();
				mushroomPatch.add(new Point(9, 29));  //TODO: switch points
				mushroomPatch.add(new Point(13, 29));
				mushroomPatch.add(new Point(16, 29));
				mushroomPatch.add(new Point(9, 30));
				mushroomPatch.add(new Point(18, 30));
				mushroomPatch.add(new Point(11, 31));
				mushroomPatch.add(new Point(14, 31));
				mushroomPatch.add(new Point(9, 32));
				mushroomPatch.add(new Point(17, 32));
				mushroomPatch.add(new Point(11, 33));
				mushroomPatch.add(new Point(10, 34));
				mushroomPatch.add(new Point(17, 34));
				mushroomPatch.add(new Point(10, 35));
				for (Point p : mushroomPatch) {
					if (p.x == ie.getX() && p.y == ie.getY()) {
						ArrayList<String> dial = new ArrayList<String>();
						dial.add("You pocket the mushrooms and think about dinner...");
						dial.add("Your stomach growls in anticipation.");
						DialogEvent de = new DialogEvent("fountain");
						de.setDialog(dial);
						this.dispatchEvent(de);
						QUEST_STATE++;
						ArrayList<String> dia = new ArrayList<String>();
						dia.add("Thanks kiddo!");
						dia.add("*your mother's warm smile grounds you*");
						dia.add("*your lucidity level has increased*");
						DialogChangeEvent dce = new DialogChangeEvent(dia,"Mom");
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
			System.out.println("Items Gathered Executing");
			if (event.eventType.equals("DIALOG_EVENT")) {
				DialogEvent de = (DialogEvent) event;
				System.out.println("checking if speaker is clone");
				if (de.speakerID.equals("Mom")) {
					QUEST_STATE++;
					LucidityChangeEvent lce = new LucidityChangeEvent(++Sys.LUCIDITY);
					this.dispatchEvent(lce);
					ArrayList<String> dia = new ArrayList<String>();
					dia.add("You didn't eat the mushrooms, did you?");
					dia.add("If you eat those raw, they'll mess with your head, I heard.");
					dia.add("People have been reporting weird visions of some sorta house");
					dia.add("near the mushroom field.");
					DialogChangeEvent dce = new DialogChangeEvent(dia,"Mom");
					this.dispatchEvent(dce);
				}
			}
			System.out.println(QUEST_STATE);
			break;
		case QUEST_COMPLETED:
			break;
		}
	}


}

