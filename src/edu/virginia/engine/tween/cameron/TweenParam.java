package edu.virginia.engine.tween.cameron;

public class TweenParam {
	double startVal;
	double endVal;
	double duration;
	double startTime;
	double endTime;
	TweenableParam param;
	TransitionParams transitionType;
	public boolean complete;
	public TweenParam(TweenableParam paramToTween, double startVal, double endVal, double time, TransitionParams transitionType) {
		this.param = paramToTween;
		this.startVal = startVal;
		this.endVal = endVal;
		this.duration = time;
		this.transitionType = transitionType;
	}
	
	public TweenableParam getParam() {
		return param;
	}
	
	public double getStartVal() {
		return startVal;
	}
	
	public double getEndVal() {
		return endVal;
	}
	
	public double getTweenDuration() {
		return duration;
	}
	public void setTransitionType(TransitionParams type) {
		transitionType = type;
	}

}
