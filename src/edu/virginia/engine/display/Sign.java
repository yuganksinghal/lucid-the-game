package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.InteractEvent;

public class Sign extends EventDispatcher implements Interactable  {
	protected ArrayList<Point> tiles;
	
	private ArrayList<String> dialog;
	
	private String id;
	
	public Sign(String id, int y, int x) {
		this.id = id;
		tiles = new ArrayList<Point>();
		tiles.add(new Point(x,y));
		dialog = new ArrayList<String>();
		
		Sys.MC.addEventListener(this, "INTERACT_EVENT");
		this.addEventListener(Sys.instance, "DIALOG_EVENT");
	}
	
	public void addTile(int y, int x) {
		tiles.add(new Point(x,y));
	}
	
	public void addDialogLine(String s) {
		dialog.add(s);
	}
	
	@Override
	public void handleEvent(Event event) {
		if (event.eventType.equals("INTERACT_EVENT")) {
			InteractEvent e = (InteractEvent) event;
			for (Point p : tiles) {
				int x = p.x;
				int y = p.y;
				if (e.getX() == x && e.getY() == y) {
					System.out.println("SIGN WORKS" + this.id);
					DialogEvent de = new DialogEvent(null);
					de.setDialog(dialog);
					this.dispatchEvent(de);
					break;
				//LAUNCH DIALOG
				}
			}
		}
	}
	
}
