package edu.virginia.engine.tween;

public class TweenableParam {
	public static enum TWEENABLEPARAM {
		X, Y, SCALE_X, SCALE_Y, ALPHA
	}
	
	private TWEENABLEPARAM p;
	
	TweenableParam(TWEENABLEPARAM t) {
		
	}
	
	public TWEENABLEPARAM getParam() {
		return this.p;
	}
}
