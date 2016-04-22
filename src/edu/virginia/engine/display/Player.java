package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.events.InteractEvent;
import edu.virginia.engine.events.LucidityChangeEvent;
import edu.virginia.engine.map.Map;

public class Player extends Walkable {
	boolean actionPressed = false;
	boolean lucDownPressed = false;
	boolean lucUpPressed = false;
	ArrayList<Item> inventory;
	
	
	public Player(String id, String imageFileName) {
		super(id, imageFileName);
		construct();
		this.addEventListener(Sys.instance, "LUCIDITY_CHANGE_EVENT");
	}

	public Player(String id) {
		super(id);
		construct();
		this.addEventListener(Sys.instance, "LUCIDITY_CHANGE_EVENT");
	}

	private void construct() {
		this.visible = true;
		this.moving = false;
		inventory = new ArrayList<Item>();
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
			if (keys.contains("N")) {
				lucDownPressed = true;
			}
			if (keys.contains("M")) {
				lucUpPressed = true;
			}
			if (lucDownPressed && !keys.contains("N")) {
				System.out.println("pressed N");
				this.dispatchEvent(new LucidityChangeEvent(--Sys.LUCIDITY));
				lucDownPressed = false;
			}
			if (lucUpPressed && !keys.contains("M")) {
				this.dispatchEvent(new LucidityChangeEvent(++Sys.LUCIDITY));
				lucUpPressed = false;
			}
			if (actionPressed && !keys.contains("Z")) {
				interact();
				actionPressed = false;
			}
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