package edu.virginia.engine.tween.cameron;

//CAMERON
import java.util.ArrayList;

import edu.virginia.engine.Sys;
import edu.virginia.engine.display.DisplayObject;

public class Tween {
	private DisplayObject object;
	private ArrayList<TweenParam> params;
	private ArrayList<TweenParam> garbage;
	
	private void construct() {
		params = new ArrayList<TweenParam>();
		garbage = new ArrayList<TweenParam>();
	}
	public Tween(DisplayObject object) {
		construct();
		this.object = object;
	}
	public Tween(DisplayObject object, TweenParam param) {
		construct();
		this.object = object;
		this.animate(param);
	}
	public void animate(TweenParam param) {
		param.startTime = System.currentTimeMillis();
		params.add(param);
		System.out.println("Starting new animation!");
	}
	public void update() { //invoked once per frame by the tweenjuggler. updates this tween/DO
		for (TweenParam p : params) {
			double percentDone = System.currentTimeMillis() - p.startTime;
			percentDone /= p.duration;
			percentDone *= 100;
			if (percentDone >= 100) { //if you add breakpoints the currenTimeMillis will keep ticking, making this statement become true "faster" since it corresponds with real time, not program time
				p.complete = true;
				garbage.add(p);
				break;
			}
			setValue(p.param, p.startVal +
					((p.endVal-p.startVal) * TweenTransitions.applyTransition(p.transitionType, percentDone)));
		}
		for (TweenParam p : garbage) {
			params.remove(p);
//			Sys.dispatcher.dispatchEvent(new TweenCompleteEvent(object,p.transitionType));
			System.out.println("Removed a param");
		}
		garbage.clear();
	}
	public boolean isComplete() {
		return false;
	}
	public void setValue(TweenableParam param, double value) {
		switch (param) {
		case ALPHA:
			object.setAlpha(value);
			break;
		case POS_X:
			object.setPosX(value);
			break;
		case POS_Y:
			object.setPosY(value);
			break;
		case VELOCITY_X:
			object.setVelocityX(value);
			break;
		case VELOCITY_Y:
			object.setVelocityY(value);
			break;
		default:
			break;
		
		}
	}
	public ArrayList<TweenParam> getParams() {
		// TODO Auto-generated method stub
		return params;
	}
}
