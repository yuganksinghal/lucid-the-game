package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.dialog.Dialog;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.InteractEvent;

public class Sign extends EventDispatcher implements IEventListener  {
	private ArrayList<Point> tiles;
	
	private String dialog;
	
	public Sign(int y, int x) {
		tiles = new ArrayList<Point>();
		tiles.add(new Point(x,y));
		dialog = "This is a boat! Yay :)";
	}
	
	public void addTile(int y, int x) {
		tiles.add(new Point(x,y));
	}
	
	ArrayList<Dialog> dialogs;
	Dialog currentDialog = new Dialog();
	@Override
	public void handleEvent(Event event) {
		if (event.eventType.equals("INTERACT_EVENT")) {
			InteractEvent e = (InteractEvent) event;
			for (Point p : tiles) {
				int x = p.x;
				int y = p.y;
				if (e.getX() == x && e.getY() == y) {
					System.out.println("SIGN WORKS");
					DialogEvent de = new DialogEvent(null);
					ArrayList<String> dia = new ArrayList<String>();
					dia.add("this is a boat");
					dia.add("yay!");
					dia.add("MOOORRREEE");
					de.setDialog(dia);
					this.dispatchEvent(de);
					break;
				//LAUNCH DIALOGUE
				}
			}
		}
	}
	
}
