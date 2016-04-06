package edu.virginia.engine.display;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import map.Map;

public class AnimatedSprite extends Sprite {

	HashMap<String, ArrayList<BufferedImage>> animations;
	private String currentAnimationID;
	private ArrayList<BufferedImage> currentAnimation;
	private double lastTime;
	private double animationTime;
	private int startIndex;
	private int frames;
	private int currentFrame;
	private boolean isAnimated;

	
	public AnimatedSprite(String id) {
		super(id);
		construct();
		// TODO Auto-generated constructor stub
	
	}
	public AnimatedSprite(String id, String img) {
		super(id,img);
		construct();
		addAnimationFrame("IDLE", this.readImage(img));
		setAnimation("IDLE");
	}
	
	private void construct() {
		animations = new HashMap<String, ArrayList<BufferedImage>>();
		currentAnimationID = "IDLE";
		this.lastTime = System.currentTimeMillis();
	}
	public void addAnimation(String animationId, ArrayList<BufferedImage> images) {
		animations.put(animationId, images);
	}
	
	public void addAnimationFrame(String animationId, BufferedImage img) {
		if (animations.get(animationId) == null) {
			animations.put(animationId, new ArrayList<BufferedImage>());
		}
		animations.get(animationId).add(img);
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys, Map m) {
		super.update(pressedKeys, m);
		animate();
		
	}
	
	public void setAnimation(String s) {
		currentAnimationID = s;
		currentFrame = 0;
		this.currentAnimation = this.animations.get(s);
		frames = this.currentAnimation.size();
	}
	
	public void animate() {		
		double currentTime = System.currentTimeMillis();
		if (currentTime - this.lastTime > this.animationTime) {
			this.lastTime = currentTime;
			if (currentFrame < frames - 1) {
				currentFrame ++;
			} else currentFrame = 0;
//			System.out.println(currentFrame + "/" + frames);
		}
		
		this.setImage(currentAnimation.get(currentFrame));
	}
	public void pause() {
		
	}
	
	
	protected void setAnimationSpeed(int i) {
		animationTime = i;
	}
	
	protected String getAnimationID() {
		return currentAnimationID;
	}


}
