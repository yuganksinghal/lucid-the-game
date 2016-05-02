package edu.virginia.quest;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.display.Walkable;
import edu.virginia.engine.events.DialogChangeEvent;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.InteractEvent;
import edu.virginia.engine.events.LucidityChangeEvent;

public class FinalQuest extends Quest{
	
	int QUEST_STATE = 0;
	
	final int NOT_STARTED = 0;
	final int GAME_COMPLETED = 1;
	final int INSIGHTFUL_DIALOGUE = 2;
	ArrayList<IEventListener> el;
	
	public FinalQuest(ArrayList<IEventListener>EL){
		super();
		((EventDispatcher) EL.get(0)).addEventListener(this, "DIALOG_EVENT");
		this.addEventListener(EL.get(0), "DIALOG_CHANGE_EVENT");
		el = EL;
	}
	
	public void handleEvent(Event event) {
		switch (QUEST_STATE) {

		case NOT_STARTED:
			if (event.eventType.equals("DIALOG_EVENT")) {
				DialogEvent de = (DialogEvent) event;
				if (de.speakerID.equals("cross")) {					
					QUEST_STATE++;
					System.out.println("Quest Started!");
					//Sys.finalPortal.setPointB(null);
					Sys.LUCIDITY = 0;
					LucidityChangeEvent lce = new LucidityChangeEvent(Sys.LUCIDITY);
					this.dispatchEvent(lce);
				
					//TODO: 20, 120
				}
			}
			break;
			
		case GAME_COMPLETED:
			if(event.eventType.equals("INTERACT_EVENT")){
				if(((InteractEvent)event).getX() == 109 && (((InteractEvent)event).getY() == 25)){
					Sys.Blackout = 1;
					Sys.LUCIDITY = 4;
					LucidityChangeEvent lce = new LucidityChangeEvent(Sys.LUCIDITY);
					this.dispatchEvent(lce);
					Sys.MC.teleport(20, 120, Sys.currentMap);
					ArrayList<String> dial = new ArrayList<String>();
					dial.add("You try to open the door");
					dial.add("...");
					dial.add(".....");
					dial.add("It's locked");
					dial.add("You strangely feel tired");
					dial.add("You black out");
					DialogEvent de = new DialogEvent("church");
					de.setDialog(dial);
					this.dispatchEvent(de);
					ArrayList<String> dia = new ArrayList<String>();
					dia.add("...");
					dia.add("Test Dialogue");
					dia.add("...");
					DialogEvent dae = new DialogEvent("hospital");
					dae.setDialog(dial);
					this.dispatchEvent(dae);
					QUEST_STATE++;
					
					//here we teleport the players
					((Walkable) (el.get(1))).teleport(20,121,Sys.currentMap);
					((Walkable) (el.get(1))).face(0);;
					((Walkable) (el.get(2))).teleport(21, 121, Sys.currentMap);
					((Walkable) (el.get(2))).face(0);
					((Walkable) (el.get(3))).teleport(19, 121, Sys.currentMap);
					((Walkable) (el.get(3))).face(0);
					((Walkable) (el.get(4))).teleport(15, 120, Sys.currentMap);
					((Walkable) (el.get(4))).face(3);
					((Walkable) (el.get(5))).teleport(15, 119, Sys.currentMap);
					((Walkable) (el.get(5))).face(3);
				}
				
			}
			break;
		case INSIGHTFUL_DIALOGUE:
			System.out.println("FINAL DIALOGUE. AFTER THIS, GAME ENDS");
			//here we add the dialog for the end of the game
			//and then we exit out of the game at the end of the dialog
		}
	}

}
