package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.engine.SoundManager;
import map.Map;

public class Player extends Walkable {
	private int xGrid;
	private int yGrid;
	protected boolean moving;

	public Player(String id, String imageFileName) {
		super(id, imageFileName);
		construct();
	}

	public Player(String id) {
		super(id);
		construct();
	}

	private void construct() {
		this.setFixed(false);
		this.visible = true;
		this.moving = false;
	}

	@Override
	public void update(ArrayList<String> pressedKeys, Map map) {
		super.update(pressedKeys, map);
		playerInput(pressedKeys, map);
	}
	
	public void playerInput(ArrayList<String> keys, Map map) {
		if (!moving) {
			
			if (keys.contains("D")) {
				this.right(map);
				this.moving = true;
			}
			else if (keys.contains("A")) {
				this.left(map);
				this.moving = true;
			}
			if (keys.contains("S")) {
				this.down(map);
				this.moving = true;
			}
			if (keys.contains("W")) {
				this.up(map);
				this.moving = true;
			}
		} else System.out.println("moving!");
	}

	public boolean isPlayer() {
		return true;
	}
}