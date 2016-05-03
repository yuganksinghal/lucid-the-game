package edu.virginia.lab1test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.SoundManager;
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
import edu.virginia.quest.FinalQuest;
import edu.virginia.quest.MansionQuest;
import edu.virginia.quest.MushroomHuntQuest;
import edu.virginia.quest.SleepingHandsomeQuest;

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
	NPC priest;
	NPC dog;
	NPC partTimeWorker;
	NPC mom;
	NPC boy;
	NPC oldMan;
	Sign cross;
	Mirror mirror;
	MansionQuest mansionQuest;
	DogBiteQuest dogBiteQuest;
	MushroomHuntQuest mushroomHuntQuest;
	SleepingHandsomeQuest sleeptingHandsomeQuest;
	FinalQuest finalQuest;
	Map map3;
	Map map4;
	final static int GAME_WIDTH = 600;
	final static int GAME_HEIGHT = 500;
	private boolean changeStateToDialog = false;
	
	ArrayList<String> dialog;
	int dialogItr = 0;
	
	int GAME_STATE = 2;
	final static int DEFAULT = 0;
	final static int DIALOG = 1;
	final static int TITLE_SCREEN = 2;
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */

	public Lucid() {
		super("~LUCID~", GAME_WIDTH, GAME_HEIGHT);
		actionPressed = false;
		
		Sys.instance = this;
		
		//INITIALIZE MAP
		Sys.maps = new Map[5];
		Sys.maps[0] = new Map("betatest0.tmx");
		Sys.maps[1] = new Map("betatest1.tmx");
		Sys.maps[2] = new Map("betatest2.tmx");
		Sys.maps[3] = new Map("betatest3.tmx");
		Sys.maps[4] = new Map("betatest4.tmx");
		Sys.currentMap = Sys.maps[2];		
		
		
		// INITIALIZE SPRITES
		
		player = new Player("player", "Player.png", "Playertemp.png");
		player.teleport(17, 7, Sys.currentMap);
		Sys.addSprite(player);
		Sys.MC = player;
		
		
		
		priest = new NPC("Priest","Priest.png", "Priesttemp.png");
		priest.teleport(18, 22, Sys.currentMap);
		priest.addDialogLine("Hey you.");
		priest.addDialogLine("Yeah kid, I'm talking to you...");
		priest.addDialogLine("Can you get an icon for me from that mansion?");
		priest.addDialogLine("I'm not allowed in there...");
		priest.addDialogLine("It looks like the one on my robe.");
		priest.addDialogLine("Thanks, kid.");
		priest.addDialogLine(";)");
		
		partTimeWorker = new NPC("Part-Time Worker", "Girl.png", "GirlTemp.png");
		partTimeWorker.teleport(4, 8, Sys.currentMap);
		partTimeWorker.face(3);
		partTimeWorker.addDialogLine("What are you looking at, dweeb??");
		partTimeWorker.addDialogLine("I'm on break!");
		partTimeWorker.addDialogLine("Leave me alone!");
		Sys.addSprite(partTimeWorker);
		
		
		oldMan = new NPC("Old Man", "OldMan.png", "OldMantemp.png");
		oldMan.teleport(20, 7, Sys.currentMap);
		Sys.addSprite(oldMan);
		oldMan.addDialogLine("Don't be afraid of my pupper!");
		oldMan.addDialogLine("His bark is worse than his bite.");
		oldMan.addDialogLine("*You sense the man is lying.*");
		
		dog = new NPC("Dog", "Dog.png", "Dogtemp.png");
		dog.teleport(19, 7, Sys.currentMap);
		dog.addDialogLine("*the old man's dog bites you before");
		dog.addDialogLine("the old man can pull back on its leash*");
		dog.addDialogLine("OLD MAN: Oh my! I'm so sorry.");
		dog.addDialogLine("Why don't you go home and get that cleaned up?");
		dog.addDialogLine("Don't sue me! Sue my dog!");
		Sys.addSprite(dog);
		
		mom = new NPC("Mom", "Mom.png", "Momtemp.png");
		mom.teleport(78,44, Sys.currentMap);
		mom.addDialogLine("Hi honey!");
		mom.addDialogLine("Could you bring me some mushrooms from down the way?");
		mom.addDialogLine("We're making soup!");
		mom.addDialogLine("But before you do that,");
		mom.addDialogLine("why don't you help that old man");
		mom.addDialogLine("outside our house?");
		Sys.addSprite(mom);
		
		boy = new NPC("boy", "Boy.png", "Boytemp.png");
		boy.addDialogLine("Could you please talk to that girl");
		boy.addDialogLine("who works at the convenience store for me?");
		boy.addDialogLine("I've been trying to get her attention all day,");
		boy.addDialogLine(" but she just keeps ignoring me.");
		boy.addDialogLine("I know! Why don't you bring her my ____?");
		boy.addDialogLine("It's on this bookshelf, I'm pretty sure.");
		
		// INITIALIZE CAMERA
		
		cross = new Sign("cross", 109, 7);
		cross.addDialogLine("You stick the cross onto the wall.");
		cross.addDialogLine("The hum gets louder.");
		
		camera = new Camera(GAME_WIDTH, GAME_HEIGHT, player);
		
		mirror = new Mirror("mirror",76,36);
		
		// ADD LISTENERS
		
		
		dialog = new ArrayList<String>();
		dialog.add("YOU SHOULD NEVER SEE THIS");
		
		ArrayList<IEventListener> mansion = new ArrayList<IEventListener>();
		mansion.add(priest);
		// INITIALIZE QUESTS
		mansionQuest = new MansionQuest(mansion);
		
		
		
		ArrayList<IEventListener> dogbite = new ArrayList<IEventListener>();
		dogbite.add(dog);
		dogbite.add(oldMan);
		dogbite.add(mom);
		// INITIALIZE QUESTS
		dogBiteQuest = new DogBiteQuest(dogbite);
		
		ArrayList<IEventListener> sleepingHandsome = new ArrayList<IEventListener>();
		sleepingHandsome.add(boy);
		sleepingHandsome.add(partTimeWorker);
		sleeptingHandsomeQuest = new SleepingHandsomeQuest(sleepingHandsome);
		
		ArrayList<IEventListener> mushroom = new ArrayList<IEventListener>();
		mushroom.add(mom);
		mushroomHuntQuest = new MushroomHuntQuest(mushroom);
		
		
		ArrayList<IEventListener> finalQ = new ArrayList<IEventListener>();
		finalQ.add(cross);
		finalQ.add(mom);
		finalQ.add(partTimeWorker);
		finalQ.add(oldMan);
		finalQ.add(boy);
		finalQ.add(dog);
		finalQuest = new FinalQuest(finalQ);
		
		
		//DECLARE PORTALS
		
		//portals to player house
		Portal homeDoor1 = new Portal(79,46,18,5);
		Portal homeDoor2 = new Portal(18,4,79,45);
		
		//portals to chapel
		Portal chapelDoor1 = new Portal(65,9,109,24);
		//Portal chapelDoor2 = new Portal(109,25,65,10);
		
		//portals to haunted mansion
		Portal mansionDoor1 = new Portal(14,35,135,59);
		Portal mansionDoor2 = new Portal(135,60,14,36);

//		SoundManager.playLoop("ahem_x.wav");
		SoundManager.loopMusic("Lucid.wav");
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys, Map map){
		
		//REMOVE THIS EVENTUALLY

//		else Sys.Blackout = 0;
		
		if (GAME_STATE == TITLE_SCREEN) {
			//add title screen stuff here (key detection)
			if (!pressedKeys.isEmpty()) GAME_STATE = 0;
		}
		else if (GAME_STATE == DEFAULT) {
			if (Sys.Blackout > 0) {
				Sys.Blackout -= 0.01;
			}
			if (Sys.Blackout < 0) {
				Sys.Blackout = 0;
			}
			/* Make sure player is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
			for (Sprite s : Sys.spriteList) {
				if (s != null) s.update(pressedKeys, map);
			}
		
			Sys.update();
			if (camera != null) camera.update();
			if (changeStateToDialog) {
				changeStateToDialog = false;
				GAME_STATE = DIALOG;
			}

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
			if (s != null && s.isVisible()) s.draw(g);
		}
		
		//draw foreground
		Sys.currentMap.drawForeground(g);
		
		
		if (camera != null) g.translate(-camera.offset.x, -camera.offset.y);
		
		
		
		g.setColor(new Color(255, 100, 100, 100-(100*Sys.LUCIDITY/4)));
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		
		if (Sys.Blackout > 1) Sys.Blackout = 1;
		if (Sys.Blackout < 0) Sys.Blackout = 0;
		g.setColor(new Color(0, 0, 0, (int) (Sys.Blackout*255)));
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
		System.out.println("Lucid.java got an event!");
		if (event.eventType.equals("DIALOG_EVENT")) {
			DialogEvent e = (DialogEvent) event;
			this.dialog = e.getDialog();
			changeStateToDialog = true;
		}
		if (event.eventType.equals("LUCIDITY_CHANGE_EVENT")) {
			System.out.println("Saw LCE!");
			LucidityChangeEvent lce = (LucidityChangeEvent) event;
			int luc = lce.lucidity;
			if (luc>=4){
				Sys.LUCIDITY = 4;
				luc = 4;
			} 
			if (luc<=0){
				Sys.LUCIDITY = 0;
				luc = 0;
			}
			switch (luc) {
			case 0:
				System.out.println("Lucidity O");
				Sys.currentMap = Sys.maps[0];
				break;
			case 1:
				System.out.println("LUCIDITY 1");
				Sys.addSprite(boy);
				Sys.addSprite(priest);
				Sys.currentMap = Sys.maps[1];
				boy.teleport(83, 37, Sys.currentMap);
				priest.teleport(24, 27, Sys.currentMap);
				break;
			case 2:
				priest.teleport(159, 159, Sys.currentMap);
				boy.teleport(158, 159, Sys.currentMap);
				System.out.println("Lucidity 2");
				Sys.garbage.add(boy);
				Sys.garbage.add(priest);
				Sys.currentMap = Sys.maps[2];
				break;
			case 3:
				System.out.println("LUCIDITY 3");
				Sys.currentMap = Sys.maps[3];
				break;
			case 4:
				System.out.println("LUCIDITY 4");
				Sys.currentMap = Sys.maps[4];
				break;
			}
		}
	}
}
