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
import edu.virginia.engine.events.LucidityChangeEvent;
import edu.virginia.quest.AlphaQuest;
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
	Sign bench;
	NPC clone;
	AlphaQuest alphaQuest;
	Map map3;
	Map map4;
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
		
		//INITIALIZE MAP
		map3 = new Map("alpha3.tmx");
		map4 = new Map("alpha4.tmx");
		loadedMap = map3;
		
		
		
		
		// INITIALIZE SPRITES
		
		player = new Player("player", "Player.png");
		player.teleport(3, 3, loadedMap);
		Sys.addSprite(player);
		
		clone = new NPC("clone","Player.png");
		clone.teleport(5, 5, loadedMap);
		Sys.addSprite(clone);
		
		// INITIALIZE CAMERA
		
		camera = new Camera(GAME_WIDTH, GAME_HEIGHT, player);
		
		// INITIALIZE BENCHES
		
		bench = new Sign(12,11);
		bench.addTile(13, 11);
		bench.addTile(14, 11);
		bench.addTile(12, 20);
		bench.addTile(13, 20);
		bench.addTile(14, 20);
		
		// ADD LISTENERS
		
		bench.addEventListener(this, "DIALOG_EVENT");
		clone.addEventListener(this, "DIALOG_EVENT");
		player.addEventListener(bench, "INTERACT_EVENT");
		player.addEventListener(clone, "INTERACT_EVENT");
		
		dialog = new ArrayList<String>();
		dialog.add("YOU SHOULD NEVER SEE THIS");
		
		
		// INITIALIZE QUESTS
		
		alphaQuest = new AlphaQuest();
		clone.addEventListener(alphaQuest, "DIALOG_EVENT");
		player.addEventListener(alphaQuest, "INTERACT_EVENT");
		alphaQuest.addEventListener(this, "LUCIDITY_CHANGE_EVENT");
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
		loadedMap.drawBackground2(g);

		//draw sprites
		for (Sprite s : Sys.spriteList) {
			if (s != null) s.draw(g);
		}
		
		//draw foreground
		loadedMap.drawForeground(g);
		
		if (camera != null) g.translate(-camera.offset.x, -camera.offset.y);

		if (GAME_STATE == DIALOG && dialog.size() > 0) {
			//TODO: set text size as a function of line length?
			//TODO: break up text into multiple lines somehow.
			g.setColor(Color.WHITE);
			g.fillRect(20, GAME_HEIGHT-130, GAME_WIDTH-40, 110);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Helvetica", Font.PLAIN, 20));
			g.drawString(dialog.get(dialogItr), 50, GAME_HEIGHT-100);
		} else GAME_STATE = DEFAULT;
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
		if (event.eventType.equals("LUCIDITY_CHANGE_EVENT")) {
			System.out.println("Saw LCE!");
			LucidityChangeEvent lce = (LucidityChangeEvent) event;
			int luc = lce.lucidity;
			switch (luc) {
			case 3:
				loadedMap = map3;
				break;
			case 4:
				loadedMap = map4;
				System.out.println("LOADED NEW MAP :D");
				break;
			}
		}
	}
}
