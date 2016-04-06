package edu.virginia.lab1test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Item;
import edu.virginia.engine.display.NPC;
import edu.virginia.engine.display.Player;
import edu.virginia.engine.display.Sign;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import map.Map;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class Lucid extends Game implements IEventListener {

	boolean actionPressed;
	
	/* Create a sprite object for our game. We'll use player */
	Player player;
	Sprite babyMario;
	Item coin;
	Item coin2;
	Sprite platform;
	Camera camera;
	Sign s;
	NPC clone;
	final static int GAME_WIDTH = 600;
	final static int GAME_HEIGHT = 500;
	
	ArrayList<String> dialog;
	int dialogItr = 0;
	
	int GAME_STATE = 0;
	final static int DEFAULT = 0;
	final static int DIALOG = 1;
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */

	public Lucid() {
		super("~LUCID~", GAME_WIDTH, GAME_HEIGHT);
		actionPressed = false;
		

		loadedMap = new Map("alphatest.tmx");
		
		player = new Player("player", "Player.png");
		player.teleport(3, 3, loadedMap);
		Sys.addSprite(player);
		
		clone = new NPC("clone","Player.png");
		clone.teleport(5, 5, loadedMap);
		Sys.addSprite(clone);
		
		camera = new Camera(GAME_WIDTH, GAME_HEIGHT, player);
		s = new Sign(1,5);
		s.addEventListener(this, "DIALOG_EVENT");
		clone.addEventListener(this, "DIALOG_EVENT");
		player.addEventListener(s, "INTERACT_EVENT");
		player.addEventListener(clone, "INTERACT_EVENT");
		
		dialog = new ArrayList<String>();
		dialog.add("YOU SHOULD NEVER SEE THIS");
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys, Map map){
		
		if (GAME_STATE == DEFAULT) {
			/* Make sure player is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
			for (Sprite s : Sys.spriteList) {
				if (s != null) s.update(pressedKeys, map);
				if (!s.exists()) Sys.garbage.add(s);
			}
		
			Sys.update();
			if (camera != null) camera.update();
		} else if (GAME_STATE == DIALOG) {
			//look out for the right key to be pressed to continue dialog
			if (pressedKeys.contains("Z")) {
				actionPressed = true;
			}
			if (actionPressed && !pressedKeys.contains("Z")) {
				if (dialogItr >= dialog.size() - 1 ) {
					GAME_STATE = DEFAULT;
					dialogItr = 0;
					System.out.println("dialog over!");
				} else {
					dialogItr++;
					System.out.println("next line of dialog... (" + dialogItr + ")");
				}
				
				actionPressed = false;
			}
		}
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure player gets drawn to
	 * the screen, we need to make sure to override this method and call player's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		if (camera != null) g.translate(camera.offset.x, camera.offset.y);
		if (loadedMap == null) return;
		//draw background
		loadedMap.drawBackground(g);

		//draw sprites
		for (Sprite s : Sys.spriteList) {
			if (s != null) s.draw(g);
		}
		
		//draw foreground
		loadedMap.drawForeground(g);
		
		if (camera != null) g.translate(-camera.offset.x, -camera.offset.y);

		if (GAME_STATE == DIALOG) {
			//TODO: set text size as a function of line length?
			//TODO: break up text into multiple lines somehow.
			g.setColor(Color.WHITE);
			g.fillRect(20, GAME_HEIGHT-130, GAME_WIDTH-40, 110);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Helvetica", Font.PLAIN, 20));
			g.drawString(dialog.get(dialogItr), 50, GAME_HEIGHT-100);
		}
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		Lucid game = new Lucid();
		game.start();
	}

	@Override
	public void handleEvent(Event event) {
		if (event.eventType.equals("DIALOG_EVENT")) {
			DialogEvent e = (DialogEvent) event;
			this.dialog = e.getDialog();
			GAME_STATE = DIALOG;
			
		}
	}
}
