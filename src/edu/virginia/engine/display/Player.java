package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.engine.SoundManager;

public class Player extends Sprite {
	private int JUMP_HEIGHT = 30;

	public Player(String id, String imageFileName) {
		super(id, imageFileName);
		construct();
	}
	public Player(String id) {
		super(id);
		construct();
	}
	private void construct() {
		this.setFixed(false);
		this.visible = true;
	}
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		playerInput(pressedKeys);
	}
	
	
	public void playerInput(ArrayList<String> keys) {
		if (keys.contains("D")) {
			this.setAccelX(1);
		}
		else if (keys.contains("A")) {
			this.setAccelX(-1);
		}
		else this.setAccelX(0);
		if (keys.contains("S")) {
			this.setAccelY(1);
		}
		if (keys.contains("W")) {
			//velocity must be actively applied
			if (this.grounded) {
				this.setVelocityY(-JUMP_HEIGHT);
				SoundManager.playSoundSO("baby_x.wav");
			}
		}
		else this.setAccelY(0);
		if (keys.contains("Z")) this.setRotationDelta(- 1);
		if (keys.contains("X")) this.setRotationDelta(+ 1);
		if (keys.contains("N")) this.setScaleDelta(0.01);
		if (keys.contains("M")) this.setScaleDelta(-0.01);
	}
	
	public boolean isPlayer() {
		return true;
	}
}