package edu.virginia.engine;

import java.util.ArrayList;
import java.util.HashMap;

import edu.virginia.dialog.DialogManager;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Item;
import edu.virginia.engine.display.Player;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.Walkable;
import edu.virginia.engine.events.CollisionManager;
import edu.virginia.engine.events.PickedUpEvent;
import edu.virginia.engine.events.QuestManager;
import edu.virginia.engine.map.Map;
import edu.virginia.engine.map.Portal;
import edu.virginia.engine.tween.TweenJuggler;

public class Sys {
	
	//CONSTANTS
	
	final static double HORIZONTAL_DRAG = 1.1;
	final static double JUMP_HEIGHT = 3;
	final static double VERTICAL_DRAG = 1.05;
	final static double HORIZONTAL_SPEED = 1;
	final static double GRAVITY = 1;
	public final static int TILE_SIZE = 32;
	public static int LUCIDITY = 2;
	public static int Blackout = 0;
	//STATIC STORAGE
	
	public static ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	public static Game instance;
	public static Player MC;
	public static ArrayList<Item> itemList = new ArrayList<Item>();
	public static QuestManager questManager = new QuestManager();
	public static CollisionManager collisionManager = new CollisionManager();
	public static ArrayList<Sprite> garbage = new ArrayList<Sprite>();
	public static ArrayList<Sprite> toAdd = new ArrayList<Sprite>();
	public static TweenJuggler tweenJuggler = new TweenJuggler();
	public static DialogManager dialogManager = new DialogManager();
	public static ArrayList<Portal> portals = new ArrayList<Portal>();
	public static ArrayList<Walkable> walkables = new ArrayList<Walkable>();
	
//	public static HashMap<Map, String> maps = new HashMap<Map, String>();
	public static Map currentMap;
	public static Map[] maps;

	
	public static void addItem(Item i) {
		itemList.add(i);
		spriteList.add(i);
		questManager.addEventListener(i, PickedUpEvent.COIN_PICKED_UP);
	}
	
	public static void addSprite(Sprite s) {
		toAdd.add(s);
	}
	
	public static void update() {
		for (Sprite s : Sys.garbage) {
			Sys.spriteList.remove(s);
		}for (Sprite s : Sys.toAdd) {
			Sys.spriteList.add(s);
		}
		Sys.garbage.clear();
		Sys.toAdd.clear();
		Sys.tweenJuggler.nextFrame();
	}
}
