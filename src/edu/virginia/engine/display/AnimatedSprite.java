package edu.virginia.engine.display;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import edu.virginia.engine.map.Map;

public class AnimatedSprite extends Sprite {

	HashMap<String, ArrayList<BufferedImage>> animations;
	private String currentAnimationID;
	private ArrayList<BufferedImage> currentAnimation;
	private double lastTime;
	private double animationTime;
	private double animationLength;
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
		addAnimationFrame("DEFAULT", null);
		setAnimation("DEFAULT");
//		this.setImage((BufferedImage) null);
	}
	
	private void construct() {
		animations = new HashMap<String, ArrayList<BufferedImage>>();
		this.lastTime = System.currentTimeMillis();
	}
	public void addAnimation(String animationId, ArrayList<BufferedImage> images) {
		animations.put(animationId, images);
	}
	
	public void addAnimationFrame(String animationId, BufferedImage img) {
		if (animations.get(animationId) == null) {
			animations.put(animationId, new ArrayList<BufferedImage>());
//			System.out.println("Created new animation: " + animationId);
		}
		animations.get(animationId).add(img);
//		System.out.println("Added frame to animation: " + animationId);
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys, Map m) {
		super.update(pressedKeys, m);
		animate();
		
	}
	
	public void setAnimation(String s) {
		if (this.animations.get(s) == null) {
			System.out.println("NO SUCH ANIMATION");
			return;
		}
		currentAnimationID = s;
		currentFrame = 0;
		this.currentAnimation = this.animations.get(s);
		frames = this.currentAnimation.size();
		setAnimationLength(this.animationLength);
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
	
	
	protected void setAnimationLength(double i) {
		animationTime = i / frames;
		animationLength = i;
	}
	
	protected String getAnimationID() {
		return currentAnimationID;
	}


}
