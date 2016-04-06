package edu.virginia.engine.display;

import java.awt.image.BufferedImage;

import edu.virginia.engine.Sys;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenParam;
import edu.virginia.engine.tween.TweenableParam;
import map.Map;

public class Walkable extends AnimatedSprite {
	double SPEED;
	protected boolean moving;
	protected int facing; //(0-3)
	protected final static int FACE_UP = 0;
	protected final static int FACE_RIGHT = 1;
	protected final static int FACE_DOWN = 2;
	protected final static int FACE_LEFT = 3;
	
	/*
	 * 0: up
	 * 1: right
	 * 2: down
	 * 3: left
	 */


	public Walkable(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		construct();
	}
	
	public Walkable(String id, String image) {
		super(id, image);
		parseSpriteSheet(image);
		construct();
	}
	
	private void construct() {
		SPEED = 250;
		this.setAnimationLength(SPEED);
	}

	public void up(Map m) {
		//tween position up by one tile
		m.vacate(xGrid, yGrid);
		TweenParam tp = new TweenParam(TweenableParam.Y, this.yGrid * Sys.TILE_SIZE, this.yGrid * Sys.TILE_SIZE - Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
		facing = FACE_UP;
		--yGrid;
		this.setAnimation("WALKING_UP");
		m.occupy(xGrid, yGrid);
	}
	
	public void down(Map m) {
		m.vacate(xGrid, yGrid);

		//tween position down by one tile
		TweenParam tp = new TweenParam(TweenableParam.Y, this.yGrid * Sys.TILE_SIZE, this.yGrid * Sys.TILE_SIZE + Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
		facing = FACE_DOWN;
		this.setAnimation("WALKING_DOWN");
		++yGrid;
		
		m.occupy(xGrid, yGrid);
	}
	
	public void left(Map m) {
		m.vacate(xGrid, yGrid);

		//tween position left by one tile
		TweenParam tp = new TweenParam(TweenableParam.X, this.xGrid * Sys.TILE_SIZE, this.xGrid * Sys.TILE_SIZE - Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
		facing = FACE_LEFT;
		this.setAnimation("WALKING_LEFT");

		--xGrid;
		
		m.occupy(xGrid, yGrid);
	}
	
	public void right(Map m) {
		m.vacate(xGrid, yGrid);

		//tween position right by one tile
		TweenParam tp = new TweenParam(TweenableParam.X, this.xGrid * Sys.TILE_SIZE, this.xGrid * Sys.TILE_SIZE + Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
		facing = FACE_RIGHT;
		this.setAnimation("WALKING_RIGHT");

		++xGrid;
		
		m.occupy(xGrid, yGrid);
	}

	public void stop() {
		// TODO Auto-generated method stub
		this.moving = false;
		switch (this.facing) {
		case FACE_UP:
			setAnimation("IDLE_UP");
			break;
		case FACE_RIGHT:
			setAnimation("IDLE_RIGHT");
			break;
		case FACE_DOWN:
			setAnimation("IDLE_DOWN");
			break;
		case FACE_LEFT:
			setAnimation("IDLE_LEFT");
			break;
		}
	}
	
	public void face(int i) {
		switch (i) {
		case FACE_UP:
			setAnimation("IDLE_UP");
			facing = FACE_UP;
			break;
		case FACE_RIGHT:
			facing = FACE_RIGHT;
			setAnimation("IDLE_RIGHT");
			break;
		case FACE_DOWN:
			facing = FACE_DOWN;
			setAnimation("IDLE_DOWN");
			break;
		case FACE_LEFT:
			facing = FACE_LEFT;
			setAnimation("IDLE_LEFT");
			break;
		}
	}
	public void teleport(int xTile, int yTile, Map m) {
		this.pos.x = (int) (xTile * Sys.TILE_SIZE);
		this.pos.y = (int) (yTile * Sys.TILE_SIZE);
		this.xGrid = xTile;
		this.yGrid = yTile;
		m.occupy(xTile, yTile);
	}
	
	
	private void parseSpriteSheet(String sheetName) {
		BufferedImage spriteSheet = this.readImage(sheetName);
		for (int j = 0 ; j < 5 ; j++) {
			for (int i = 0 ; i < 4 ; i++) {
				BufferedImage crop = spriteSheet.getSubimage(i*Sys.TILE_SIZE, j*Sys.TILE_SIZE, Sys.TILE_SIZE, Sys.TILE_SIZE);
				switch (j) {
				case 0:
					switch (i) {
					case 0:
						addAnimationFrame("IDLE_UP", crop);
						break;
					case 1:
						addAnimationFrame("IDLE_RIGHT", crop);
						break;
					case 2:
						addAnimationFrame("IDLE_DOWN", crop);
						this.setAnimation("IDLE_DOWN");
						break;
					case 3:
						addAnimationFrame("IDLE_LEFT", crop);
						break;
					}
					break;
				case 1:
					addAnimationFrame("WALKING_UP", crop);
					break;
				case 2:
					addAnimationFrame("WALKING_RIGHT", crop);
					break;
				case 3:
					addAnimationFrame("WALKING_DOWN", crop);
					break;
				case 4:
					addAnimationFrame("WALKING_LEFT", crop);
					break;
				}
			}
		}
	}

}
