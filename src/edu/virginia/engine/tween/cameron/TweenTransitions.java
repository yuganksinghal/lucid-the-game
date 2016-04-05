package edu.virginia.engine.tween.cameron;

public class TweenTransitions {
	
	public TweenTransitions() {
	}
	
	public static double applyTransition(TransitionParams transition, double percentDone) {
		double x = percentDone;
		switch (transition) {
		case LINEAR:
			return linear(percentDone);
		case POLYNOMIAL_IN:
			return easeInOut(percentDone);
		case STEP:
			break;
//		case QUADRATIC:
		default:
			return x;
		}
		return x;
	}
	public static double linear(double percentDone) {
		return percentDone / 100;
	}
	public static double easeInOut(double percentDone) {
		double output = percentDone;
		output /= 100;
		output *= output;
		
		return output;
	}
}
