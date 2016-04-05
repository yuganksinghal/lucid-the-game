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
					tweens.get(i).animate(tweens.get(i).getParam(), tweens.get(i).getStart(), tweens.get(i).getEnd(), tweens.get(i).getEndTime());
					tweens.get(i).stop();
					tweens.remove(i);
				} else {
					tweens.get(i).update();
				}
			}
		}
	}
	
}
