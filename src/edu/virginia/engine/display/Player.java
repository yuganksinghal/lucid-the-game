package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.engine.SoundManager;
import map.Map;

public class Player extends Sprite {
	private int xGrid;
	private int yGrid;

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
	public void update(ArrayList<String> pressedKeys, Map map) {
		super.update(pressedKeys, map);
		playerInput(pressedKeys, map);
	}

	public void playerInput(ArrayList<String> keys, Map map) {
		if (keys.contains("D")) {
			// this.setAccelX(1);
			if (this.xGrid < map.getWidth())
				this.setPosX((int) (++xGrid) * map.getTileWidth());

		} else if (keys.contains("A")) {
			if (this.xGrid > 0)
				this.setPosX((int) (--xGrid) * map.getTileWidth());
		} else
			this.setAccelX(0);
		if (keys.contains("S")) {
			if (this.yGrid < map.getHeight())
				this.setPosY((int) (++yGrid) * map.getTileHeight());
			System.out.println(yGrid);
		}
		if (keys.contains("W")) {
			if (this.yGrid > 0)
				this.setPosY((int) (--yGrid) * map.getTileHeight());
			// velocity must be actively applied
			// if (this.grounded) {
			// this.setVelocityY(-JUMP_HEIGHT);
			// SoundManager.playSoundSO("baby_x.wav");
			// }
		} else
			//this.setAccelY(0);
		if (keys.contains("Z"))
			this.setRotationDelta(-1);
		if (keys.contains("X"))
			this.setRotationDelta(+1);
		if (keys.contains("N"))
			this.setScaleDelta(0.01);
		if (keys.contains("M"))
			this.setScaleDelta(-0.01);
	}

	public boolean isPlayer() {
		return true;
	}
}