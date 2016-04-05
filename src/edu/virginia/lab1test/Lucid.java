package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Item;
import edu.virginia.engine.display.Player;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.PickedUpEvent;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenJuggler;
import edu.virginia.engine.tween.TweenParam;
import edu.virginia.engine.tween.TweenableParam;
import map.Map;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class Lucid extends Game{

	/* Create a sprite object for our game. We'll use player */
	Player player;
	Sprite babyMario;
	Item coin;
	Item coin2;
	Sprite platform;
	Camera camera;
	static int GAME_WIDTH = 600;
	static int GAME_HEIGHT = 500;
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */

	public Lucid() {
		super("Test", GAME_WIDTH, GAME_HEIGHT);

		player = new Player("player", "Mario.png");
		player.setPosition(0, 0);
		Sys.addSprite(player);

		loadedMap = new Map("alsobad.tmx");
		camera = new Camera(GAME_WIDTH, GAME_HEIGHT, player);
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys, Map map){
//		super.update(pressedKeys);

		/* Make sure player is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		for (Sprite s : Sys.spriteList) {
			if (s != null) s.update(pressedKeys, map);
			if (!s.exists()) Sys.garbage.add(s);
		}
		
		Sys.update();
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure player gets drawn to
	 * the screen, we need to make sure to override this method and call player's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		if (loadedMap == null) return;
		//draw background
		loadedMap.drawBackground(g);

		//draw sprites
		for (Sprite s : Sys.spriteList) {
			if (s != null) s.draw(g);
		}
		
		//draw foreground
		loadedMap.drawForeground(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		Lucid game = new Lucid();
		game.start();
	}
}
