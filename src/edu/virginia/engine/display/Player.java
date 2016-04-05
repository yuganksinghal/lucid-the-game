package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.engine.SoundManager;
import map.Map;

public class Player extends walkable {
	private int xGrid;
	private int yGrid;

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
	}

	@Override
	public void update(ArrayList<String> pressedKeys, Map map) {
		super.update(pressedKeys, map);
		playerInput(pressedKeys, map);
	}
	
	public void playerInput(ArrayList<String> keys, Map map) {
		if (keys.contains("D")) {
			
		}
		if (keys.contains("A")) {
			
		}
		if (keys.contains("S")) {
			
		}
		if (keys.contains("W")) {
			
		}
	}

	public boolean isPlayer() {
		return true;
	}
}