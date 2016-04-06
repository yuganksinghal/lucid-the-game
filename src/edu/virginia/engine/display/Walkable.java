package edu.virginia.engine.display;

import edu.virginia.engine.Sys;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenParam;
import edu.virginia.engine.tween.TweenableParam;
import map.Map;

public class Walkable extends AnimatedSprite {
	int SPEED;
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
		addAnimationFrame("WALKING", this.readImage(image));

		construct();
	}
	
	private void construct() {
		SPEED = 250;
		addAnimationFrame("WALKING", this.readImage("PlayerWalking.png"));
		this.setAnimationSpeed(100);
	}

	public void up(Map m) {
		//tween position up by one tile
		TweenParam tp = new TweenParam(TweenableParam.Y, this.getPos().y, this.getPos().y - Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
		facing = FACE_UP;
		--yGrid;
		this.setAnimation("WALKING");
	}
	
	public void down(Map m) {
		//tween position down by one tile
		TweenParam tp = new TweenParam(TweenableParam.Y, this.getPos().y, this.getPos().y + Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
		facing = FACE_DOWN;
		++yGrid;
	}
	
	public void left(Map m) {
		//tween position left by one tile
		TweenParam tp = new TweenParam(TweenableParam.X, this.getPos().x, this.getPos().x - Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
		facing = FACE_LEFT;
		--xGrid;
	}
	
	public void right(Map m) {
		//tween position right by one tile
		TweenParam tp = new TweenParam(TweenableParam.X, this.getPos().x, this.getPos().x + Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
		facing = FACE_RIGHT;
		++xGrid;
	}

	public void stop() {
		// TODO Auto-generated method stub
		this.moving = false;
	}

}
