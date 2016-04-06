package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.engine.events.InteractEvent;
import map.Map;

public class Player extends Walkable {
	int LUCIDITY = 5;
	boolean actionPressed = false;
	ArrayList<Item> inventory;
	
	
	public Player(String id, String imageFileName) {
		super(id, imageFileName);
		construct();
	}

	public Player(String id) {
		super(id);
		construct();
	}

	private void construct() {
		this.visible = true;
		this.moving = false;
		inventory = new ArrayList<Item>();
//		this.addAnimationFrame("WALKING_UP", this.readImage("WALKING_UP1.png"));
//		this.addAnimationFrame("WALKING_UP", this.readImage("WALKING_UP2.png"));
//		this.addAnimationFrame("WALKING_DOWN", this.readImage("WALKING_DOWN1.png"));
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
				} else setAnimation("IDLE_RIGHT");
				this.facing = FACE_RIGHT;
			}
			else if (keys.contains("A")) {
				if (!map.checkCollision(yGrid,xGrid-1)) {
					this.left(map);
					this.moving = true;
				} else setAnimation("IDLE_LEFT");
				this.facing = FACE_LEFT;
			}
			if (keys.contains("S")) {
				if (!map.checkCollision(yGrid+1,xGrid)) {
					this.down(map);
					this.moving = true;
				} else setAnimation("IDLE_DOWN");
				this.facing = FACE_DOWN;
			}
			if (keys.contains("W")) {
				if (!map.checkCollision(yGrid-1,xGrid)) {
					this.up(map);
					this.moving = true;
				} else setAnimation("IDLE_UP");
				this.facing = FACE_UP;
			}
			if (keys.contains("Z")) {
				actionPressed = true;
			}
			if (actionPressed && !keys.contains("Z")) {
				interact();
				actionPressed = false;
			}
//			this.setAnimation("IDLE");
		} 
		else {
//			if (!getAnimationID().equals("WALKING")) setAnimation("WALKING");
//			if (!getAnimationID().equals("WALKING_UP") && keys.contains("W")) setAnimation("WALKING_UP");
//			else if (!getAnimationID().equals("WALKING_DOWN") && keys.contains("S")) setAnimation("WALKING_DOWN");
//			else if (!getAnimationID().equals("WALKING_LEFT") && keys.contains("A")) setAnimation("WALKING_LEFT");
//			else if (!getAnimationID().equals("WALKING_RIGHT") && keys.contains("D")) setAnimation("WALKING_RIGHT");

		}
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
		InteractEvent e = new InteractEvent(xTile, yTile, facing);
		this.dispatchEvent(e);
		System.out.println("attempting interaction with objects at " + xTile + ", " + yTile + "...");
	}
}