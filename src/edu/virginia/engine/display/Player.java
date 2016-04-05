package edu.virginia.engine.display;

import java.util.ArrayList;

import map.Map;

public class Player extends Walkable {

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
//		System.out.println("X: " + xGrid + ", Y: " + yGrid);
	}
	
	public void playerInput(ArrayList<String> keys, Map map) {
		if (!moving) {

			if (keys.contains("D")) {
				if (!map.checkCollision(yGrid,xGrid+1)) {
					this.right(map);
					this.moving = true;
				}
			}
			else if (keys.contains("A")) {
				if (!map.checkCollision(yGrid,xGrid-1)) {
					this.left(map);
					this.moving = true;
				}
			}
			if (keys.contains("S")) {
				if (!map.checkCollision(yGrid+1,xGrid)) {
					this.down(map);
					this.moving = true;
				}
			}
			if (keys.contains("W")) {
				if (!map.checkCollision(yGrid-1,xGrid)) {
					this.up(map);
					this.moving = true;
				}
			}
		}
	}

	public boolean isPlayer() {
		return true;
	}
}