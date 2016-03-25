package edu.virginia.engine.tween;

import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.lab1test.LabOneGame.TWEENABLEPARAM;

public class Tween {
	private DisplayObject obj;
	private TWEENABLEPARAM tweenparam;
	private long endTime;
	private long startTime;
	private double start;
	private double end;
	
	
	public Tween(DisplayObject s, long time, TweenParam p, double end) {
		this.obj = s;
		this.startTime = System.currentTimeMillis();
		this.endTime = startTime + time;
		this.tweenparam = p.getParam();
		this.end = end;
		switch (p.getParam()) {
			case X:
				this.start = this.obj.getPosition().getX();
				break;
			case Y:
				this.start = this.obj.getPosition().getY();
				break;
			case SCALE_X:
				this.start = this.obj.getScaleX();
				break;
			case SCALE_Y:
				this.start = this.obj.getScaley();
				break;
			case ALPHA:
				this.start = this.obj.getAlpha();
				break;
			default: break;
		}
	}
	
	
	public TWEENABLEPARAM getParam() {
		return this.tweenparam;
	}
	
	public double getEnd() {
		return this.end;
	}
	
	public void animate(TWEENABLEPARAM tweenparam2, double start, double end, double time) {
		double percent = (time - startTime) / (endTime-startTime);
		double value = percent * (end - start) + start;
		this.setValue(tweenparam2, value);
	}
	
	public void update() {
		this.animate(this.tweenparam, this.start, this.end, System.currentTimeMillis());
	}
	
	public boolean isComplete() {
		return System.currentTimeMillis() >= this.endTime;
	}
	
	public void setValue(TWEENABLEPARAM tweenparam2, double value) {
		switch (tweenparam2) {
			case X:
				this.obj.setPosition((int) value, this.obj.getPosition().getY());
				break;
			case Y:
				this.obj.setPosition(this.obj.getPosition().getX(), (int) value);
				break;
			case SCALE_X:
				this.obj.setScaleX(value);
				break;
			case SCALE_Y:
				this.obj.setScaleY(value);
				break;
			case ALPHA:
				this.obj.setAlpha(value);
				break;
			default: break;
		}
		return;
		
	}
	
}
