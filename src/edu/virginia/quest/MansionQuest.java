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

public class MansionQuest extends Quest{
	
	int QUEST_STATE = 0;

	final int NOT_STARTED = 0;
	final int QUEST_STARTED = 1;
	final int ITEM_GATHERED = 2;
	final int QUEST_COMPLETED = 3;
	
	public MansionQuest(ArrayList<IEventListener>EL){
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
				if (de.speakerID.equals("Priest")) {
					QUEST_STATE++;
				}
			}
			break;
		case QUEST_STARTED:
			if (event.eventType.equals("INTERACT_EVENT")) {
				InteractEvent ie = (InteractEvent) event;
				ArrayList<Point> cross = new ArrayList<Point>();
				cross.add(new Point(138, 34));
				for (Point p : cross) {
					if (p.x == ie.getX() && p.y == ie.getY()) {
						ArrayList<String> dial = new ArrayList<String>();
						dial.add("You pick up the icon from the table.");
						dial.add("You feel its hum reverberating through your body.");
						DialogEvent de = new DialogEvent("fountain");
						de.setDialog(dial);
						this.dispatchEvent(de);
						QUEST_STATE++;
						ArrayList<String> dia = new ArrayList<String>();
						dia.add("You've seen this before right?");
						dia.add("The trees are particularly thin this time of year.");
						dia.add("*The man looks in the direction of the path to the East.*");
						Sys.currentMap.setCollidable(45, 16, false);
						Sys.currentMap.setCollidable(45, 15, false);
						Sys.currentMap.setCollidable(45, 14, false);
						Sys.currentMap.setCollidable(45, 13, false);
						Sys.currentMap.setCollidable(45, 12, false);
						DialogChangeEvent dce = new DialogChangeEvent(dia, "Priest");
						this.dispatchEvent(dce);
					}
				}
			}
			break;
		case ITEM_GATHERED:
			if (event.eventType.equals("DIALOG_EVENT")) {
				DialogEvent de = (DialogEvent) event;
				if (de.speakerID.equals("Priest")) {
					System.out.println("YOU FINISHED THE QUEST :)");
					QUEST_STATE++;
				}
			}
			System.out.println(QUEST_STATE);
			break;
		case QUEST_COMPLETED:
			ArrayList<String> dia = new ArrayList<String>();
			dia.add("You've seen this before right?");
			dia.add("The trees are particularly thin this time of year.");
			dia.add("*The man looks in the direction of the path to the East.*");
			DialogChangeEvent dce = new DialogChangeEvent(dia, "Priest");
			this.dispatchEvent(dce);
			break;
		}
	}

}

