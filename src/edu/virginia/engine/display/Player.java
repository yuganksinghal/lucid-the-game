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
		System.out.println("X: " + xGrid + ", Y: " + yGrid);
	}
	
	public void playerInput(ArrayList<String> keys, Map map) {
		if (!moving) {

			if (keys.contains("D")) {
				if (map.checkCollision(xGrid+1,yGrid)) {
					this.right(map);
					this.moving = true;
				}
			}
			else if (keys.contains("A")) {
				if (map.checkCollision(xGrid-1,yGrid)) {
					this.left(map);
					this.moving = true;
				}
			}
			if (keys.contains("S")) {
				if (map.checkCollision(xGrid,yGrid+1)) {
					this.down(map);
					this.moving = true;
				}
			}
			if (keys.contains("W")) {
				if (map.checkCollision(xGrid,yGrid-1)) {
					this.up(map);
					this.moving = true;
				}
			}
		} else System.out.println("moving!");
	}

	public boolean isPlayer() {
		return true;
	}
}