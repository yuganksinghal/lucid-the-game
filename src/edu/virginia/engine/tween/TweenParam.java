package edu.virginia.engine.tween;


public class TweenParam {
	
	private TweenableParam param; 
	private double startVal;
	private double endVal;
	private double time;

	public TweenParam(TweenableParam paramToTween, double startVal, double endVal, double time) {
		this.param = paramToTween;
		this.startVal = startVal;
		this.endVal = endVal;
		this.time = time;
	}
	
	public TweenableParam getParam() {
		return this.param;
	}
	
	public double getStartVal() {
		return this.startVal;
	}
	
	public double getEndVal() {
		return this.endVal;
	}
	
	public double getTweenTime() {
		return this.time;
	}
}
