package edu.virginia.engine.tween.cameron;

import java.util.ArrayList;

public class TweenJuggler {
	//Singleton Class
	//has a queue of tweens
	
	private ArrayList<Tween> tweens = new ArrayList<Tween>();
	private ArrayList<Tween> garbage = new ArrayList<Tween>();
	
	public void add(Tween tween) {
		tweens.add(tween);
	}
	
	public void nextFrame() {
		//invoked every frame by game, calls update on every Tween and 
		//cleans up old/complete tweens
		
		//updates to next tween if current one is done
		for (Tween t : tweens) {
			t.update();
			if (t.getParams().isEmpty()) {
				
				garbage.add(t);
			}
		}
		for (Tween t : garbage) {
			tweens.remove(t);
			System.out.println("Removed a tween");
		}
		garbage.clear();
	}

}
