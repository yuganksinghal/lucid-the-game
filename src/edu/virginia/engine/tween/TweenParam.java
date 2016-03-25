package edu.virginia.engine.tween;

import edu.virginia.lab1test.LabOneGame.TWEENABLEPARAM;

public class TweenParam {
	
	private TWEENABLEPARAM param; 
	private double startVal;
	private double endVal;
	private double time;

	public TweenParam(TWEENABLEPARAM paramToTween, double startVal, double endVal, double time) {
		this.param = paramToTween;
		this.startVal = startVal;
		this.endVal = endVal;
		this.time = time;
	}
	
	public TWEENABLEPARAM getParam() {
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
