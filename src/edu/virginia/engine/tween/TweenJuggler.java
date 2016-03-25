package edu.virginia.engine.tween;

import java.util.ArrayList;

public class TweenJuggler {
	ArrayList<Tween> tweens = new ArrayList<Tween>();
	
	public TweenJuggler() {
		
	}
	
	public void add(Tween t) {
		tweens.add(t);
		return;
	}
	
	public void nextFrame() {
		if (tweens.size() > 0) {
			for(int i = tweens.size()-1; i >= 0; i--) {
				if (tweens.get(i).isComplete()){
					tweens.remove(i);
				} else {
					tweens.get(i).update();
				}
			}
		}
	}
	
}
