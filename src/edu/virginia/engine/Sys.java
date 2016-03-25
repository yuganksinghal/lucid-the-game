package edu.virginia.engine;

import java.util.ArrayList;

import edu.virginia.engine.display.Item;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionManager;
import edu.virginia.engine.events.PickedUpEvent;
import edu.virginia.engine.events.QuestManager;

public class Sys {
	public static ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	public static ArrayList<Item> itemList = new ArrayList<Item>();
	public static QuestManager questManager = new QuestManager();
	public static CollisionManager collisionManager = new CollisionManager();
	public static ArrayList<Sprite> garbage = new ArrayList<Sprite>();
	
	public static void addItem(Item i) {
		itemList.add(i);
		spriteList.add(i);
		questManager.addEventListener(i, PickedUpEvent.COIN_PICKED_UP);
	}
	
	public static void addSprite(Sprite s) {
		spriteList.add(s);
	}
}
