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

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game{

	/* Create a sprite object for our game. We'll use player */
	Player player;
	Sprite babyMario;
	Item coin;
	Item coin2;
	Sprite platform;
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabOneGame() {
		super("Test", 500, 300);
		player = new Player("player", "Mario.png");
		player.setScale(0.3);
		player.setPosition(50, 50);
		babyMario = new Sprite("BabyMario", "Mario.png");
		babyMario.setRelScale(1);
		babyMario.setRelativePosition(500,500);
//		player.addChild(babyMario);
		coin = new Item("Coin", "Coin.png");
		coin.setScale(0.2);
		coin.setPosition(200, 200);
		coin2 = new Item("Coin2", "Coin.png");
		coin2.setScale(0.2);
		coin2.setPosition(270, 200);
		platform = new Sprite("Platform","Platform.png");
		platform.setPosition(200, 260);
		platform.setScale(0.3);
		platform.setScaleDelta(new Point.Double(1,0));
		
		Sys.addSprite(player);
		Sys.addItem(coin);
		Sys.addItem(coin2);
		Sys.addSprite(platform);
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
//		super.update(pressedKeys);
		
		/* Make sure player is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		for (Sprite s : Sys.spriteList) {
			if (s != null) s.update(pressedKeys);
			if (!s.isFixed()) applyPhysics(s);
			if (!s.exists()) Sys.garbage.add(s);
		}
		
		for (Sprite s : Sys.garbage) {
			Sys.spriteList.remove(s);
		}
		Sys.garbage.clear();
	}

	
	public void applyPhysics(Sprite s) {
		double GRAVITY = 2;
		double newVelX = s.getVelX();
		double newVelY = s.getVelY();
		newVelX /= 1.1;
		newVelY /= 1.1;
		s.setVelocity(newVelX, newVelY);
		s.setAccelY(GRAVITY);
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure player gets drawn to
	 * the screen, we need to make sure to override this method and call player's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);

		for (Sprite s : Sys.spriteList) {
			if (s != null) s.draw(g);
		}
		
		g.drawRect(10, 10, 480, 280);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		LabOneGame game = new LabOneGame();
		game.start();
	}
}
