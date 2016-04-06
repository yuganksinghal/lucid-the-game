package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.engine.events.InteractEvent;
import map.Map;

public class Player extends Walkable {
	int LUCIDITY = 5;
	boolean actionPressed = false;
	
	
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
				if (!map.checkCollision(yGrid,xGrid+1)) {
					this.right(map);
					this.moving = true;
					this.facing = FACE_RIGHT;
				}
			}
			else if (keys.contains("A")) {
				if (!map.checkCollision(yGrid,xGrid-1)) {
					this.left(map);
					this.moving = true;
					this.facing = FACE_LEFT;
				}
			}
			if (keys.contains("S")) {
				if (!map.checkCollision(yGrid+1,xGrid)) {
					this.down(map);
					this.moving = true;
					this.facing = FACE_DOWN;
				}
			}
			if (keys.contains("W")) {
				if (!map.checkCollision(yGrid-1,xGrid)) {
					this.up(map);
					this.moving = true;
					this.facing = FACE_UP;
				}
			}
			if (keys.contains("Z")) {
				actionPressed = true;
			}
			if (actionPressed && !keys.contains("Z")) {
				interact();
				actionPressed = false;
			}
			this.setAnimation("IDLE");
		} 
		else if (!getAnimationID().equals("WALKING")) setAnimation("WALKING");
	}

	public boolean isPlayer() {
		return true;
	}
	
	private void interact() {
		int xTile = xGrid;
		int yTile = yGrid;
		switch (facing) {
		case FACE_UP:
			yTile--;
			break;
		case FACE_DOWN:
			yTile++;
			break;
		case FACE_RIGHT:
			xTile++;
			break;
		case FACE_LEFT:
			xTile--;
			break;
		}
		InteractEvent e = new InteractEvent(xTile, yTile);
		this.dispatchEvent(e);
//		System.out.println("attemdpting interaction...");
	}
}