package edu.virginia.engine.tween;

import edu.virginia.engine.display.DisplayObject;

public class Tween {
	private DisplayObject obj;
	private TweenableParam tweenparam;
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
				this.start = this.obj.getPos().x;
				break;
			case Y:
				this.start = this.obj.getPos().y;
				break;
			case SCALE_X:
				this.start = this.obj.getScaleX();
				break;
			case SCALE_Y:
				this.start = this.obj.getScaleY();
				break;
			case ALPHA:
				this.start = this.obj.getAlpha();
				break;
			default: break;
		}
	}
	
	
	public TweenableParam getParam() {
		return this.tweenparam;
	}
	
	public double getEnd() {
		return this.end;
	}
	
	public void animate(TweenableParam tweenparam2, double start, double end, double time) {
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
	
	public void setValue(TweenableParam tweenparam2, double value) {
		switch (tweenparam2) {
			case X:
				this.obj.setPosition((int) value, this.obj.getPos().y);
				break;
			case Y:
				this.obj.setPosition(this.obj.getPos().x, (int) value);
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
