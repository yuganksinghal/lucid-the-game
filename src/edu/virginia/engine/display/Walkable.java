package edu.virginia.engine.display;

import edu.virginia.engine.Sys;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenParam;
import edu.virginia.engine.tween.TweenableParam;
import map.Map;

public class Walkable extends Sprite {
	int SPEED;

	public Walkable(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		SPEED = 1000;
	}
	
	public Walkable(String id, String image) {
		super(id, image);
	}
	
	public void up(Map m) {
		//tween position up by one tile
		TweenParam tp = new TweenParam(TweenableParam.Y, this.getPos().y, this.getPos().y - Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
	}
	
	public void down(Map m) {
		//tween position down by one tile
		TweenParam tp = new TweenParam(TweenableParam.Y, this.getPos().y, this.getPos().y + Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
	}
	
	public void left(Map m) {
		//tween position left by one tile
		TweenParam tp = new TweenParam(TweenableParam.X, this.getPos().x, this.getPos().x - Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
	}
	
	public void right(Map m) {
		//tween position right by one tile
		TweenParam tp = new TweenParam(TweenableParam.X, this.getPos().x, this.getPos().x + Sys.TILE_SIZE, SPEED);
		Tween t = new Tween(this, tp);
		Sys.tweenJuggler.add(t);
	}

}
