package edu.virginia.lab1test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Mirror;
import edu.virginia.engine.display.NPC;
import edu.virginia.engine.display.Player;
import edu.virginia.engine.display.Sign;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.LucidityChangeEvent;
import edu.virginia.engine.map.Map;
import edu.virginia.engine.map.Portal;
import edu.virginia.quest.AlphaQuest;
import edu.virginia.quest.DogBiteQuest;
import edu.virginia.quest.MushroomHuntQuest;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class Lucid extends Game{

	boolean actionPressed;
	
	
	/* Create a sprite object for our game. We'll use player */
	Player player;
	Camera camera;
	Sign bench;
	NPC clone;
	NPC dog;
	NPC partTimeWorker;
	NPC mom;
	Mirror mirror;
	AlphaQuest alphaQuest;
	DogBiteQuest dogBiteQuest;
	MushroomHuntQuest mushroomHuntQuest;
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
		
		Sys.instance = this;
		
		//INITIALIZE MAP
		Sys.maps = new Map[5];
		Sys.maps[0] = new Map("betatest.tmx");
		Sys.maps[1] = new Map("betatest.tmx");
		Sys.maps[2] = new Map("betatest.tmx");
		Sys.maps[3] = new Map("betatest.tmx");
		Sys.maps[4] = new Map("betatest.tmx");
		Sys.currentMap = Sys.maps[2];		
		
		
		// INITIALIZE SPRITES
		
		player = new Player("player", "Player.png");
		player.teleport(16, 16, Sys.currentMap);
		Sys.addSprite(player);
		Sys.MC = player;
		
		
		
		clone = new NPC("clone","Dot.png");
		clone.teleport(18, 22, Sys.currentMap);
		clone.addDialogLine("I'm a little hungry.");
		clone.addDialogLine("Could you find me some ice?");
		clone.addDialogLine("I think I saw a whole bunch in front of that building");
		clone.addDialogLine("You can press Z to pick it up!");
		
		Sys.addSprite(clone);
		
		partTimeWorker = new NPC("Part-Time Worker", "Player.png");
		partTimeWorker.teleport(7, 10, Sys.currentMap);
		partTimeWorker.addDialogLine("WATCHU LOOKING AT!");
		partTimeWorker.addDialogLine("GO AWAY!");
		partTimeWorker.addDialogLine("I'M ON BREAK!");
		Sys.addSprite(partTimeWorker);
		
		dog = new NPC("dog", "Player.png");
		dog.teleport(19, 7, Sys.currentMap);
		dog.addDialogLine("*BITE*");
		dog.addDialogLine("Old Man: Oh, Dear");
		dog.addDialogLine("It seems that Rex bit you.");
		dog.addDialogLine("Why don't you go home and get that cleaned up?");
		dog.addDialogLine("...and remember...");
		dog.addDialogLine("Don't sue me! Sue my Dog!.");
		Sys.addSprite(dog);
		
		mom = new NPC("mom", "Player.png");
		mom.teleport(78,44, Sys.currentMap);
		mom.addDialogLine("Hi honey!");
		mom.addDialogLine("Could you bring me some mushrooms from down the way?");
		mom.addDialogLine("We're making soup!");
		
		Sys.addSprite(mom);
		
		
		Sys.addSprite(partTimeWorker);
		
		// INITIALIZE CAMERA
		
		camera = new Camera(GAME_WIDTH, GAME_HEIGHT, player);
		
		// INITIALIZE BENCHES
		
		bench = new Sign("bench", 12,11);
		bench.addTile(13, 11);
		bench.addTile(14, 11);
		bench.addTile(12, 20);
		bench.addTile(13, 20);
		bench.addTile(14, 20);
		bench.addDialogLine("It's a nice bench...");
		bench.addDialogLine("You wish you could sit on it...");
		bench.addDialogLine(":(");
		
		mirror = new Mirror("mirror",76,36);
		
		// ADD LISTENERS
		
		
		dialog = new ArrayList<String>();
		dialog.add("YOU SHOULD NEVER SEE THIS");
		
		ArrayList<IEventListener> alpha = new ArrayList<IEventListener>();
		alpha.add(clone);
		// INITIALIZE QUESTS
		alphaQuest = new AlphaQuest(alpha);
		
		ArrayList<IEventListener> dogbite = new ArrayList<IEventListener>();
		dogbite.add(dog);
		// INITIALIZE QUESTS
		dogBiteQuest = new DogBiteQuest(dogbite);
		
		ArrayList<IEventListener> mushroom = new ArrayList<IEventListener>();
		mushroom.add(mom);
		mushroomHuntQuest = new MushroomHuntQuest(mushroom);
		
//		SoundManager.playMusic("Lucid.wav");
		
		
		//DECLARE PORTALS
		
		//portals to player house
		Portal homeDoor1 = new Portal(79,46,18,5);
		Portal homeDoor2 = new Portal(18,4,79,45);
		
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
		if (Sys.currentMap == null) return;
		//draw background
		Sys.currentMap.drawBackground(g);
		Sys.currentMap.drawBackground2(g);

		//draw sprites
		for (Sprite s : Sys.spriteList) {
			if (s != null) s.draw(g);
		}
		
		//draw foreground
		Sys.currentMap.drawForeground(g);
		
		
		if (camera != null) g.translate(-camera.offset.x, -camera.offset.y);
		
		
		
		g.setColor(new Color(0, 0, 0, 255-(100*Sys.LUCIDITY/4)));
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		
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
			case 1:
				Sys.currentMap = Sys.maps[0];
				break;
			case 2:
				Sys.currentMap = Sys.maps[1];
				break;
			case 3:
				Sys.currentMap = Sys.maps[2];
				break;
			case 4:
				Sys.currentMap = Sys.maps[3];
				System.out.println("LOADED NEW MAP :D");
				break;
			case 5:
				Sys.currentMap = Sys.maps[4];
				break;
			}
		}
	}
}
