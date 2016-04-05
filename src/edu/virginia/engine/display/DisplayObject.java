package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import map.Map;

public class DisplayObject {

	/*
	 * MASTER TO-DO LIST:
	 * DONE: implement checks for scaling and rotation so they don't break hitboxes
	 *
	 *
	 */
	
	
	// Fields
	
	protected String id; // all DOs have a unique ID
	private BufferedImage displayImage;

	private boolean exists;
	protected boolean collideable;
	protected boolean visible;
	protected boolean fixed;
	protected boolean grounded;
	protected Point pos;
	protected Point.Double velocity;
	protected float alpha;
	protected int xGrid;
	protected int yGrid;
	
	protected Point relPos;
	protected int relRotation;
	protected Point.Double relScale;
	

	/* The image that is displayed by this object */

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	
	private void construct() {
		this.setExists(true);
		this.fixed = true;
		this.collideable = true;
		this.visible = true;
		this.pos = new Point(0,0);
		this.velocity = new Point.Double(0,0);
		this.alpha = 1f;
		this.relPos = new Point(0,0);
		this.relScale = new Point.Double(1,1);
		this.relRotation = 0;
		this.grounded = false;
	}
	public DisplayObject(String id) {
		this.setId(id);
		construct();
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
		construct();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}


	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("res" + File.separator + "images" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}
	public void setVelocity(double x, double y) {
		velocity.setLocation(x, y);
	}
	public void setVelocityX(double x) {
		velocity.setLocation(x, velocity.y);
	}
	public void setVelocityY(double y) {
		velocity.setLocation(velocity.x, y);
	}
	public void setPosition(int x, int y) {
		pos.setLocation(x,y);
	}
	public double getVelY() {
		return velocity.y;
	}
	public double getVelX() {
		return velocity.x;
	}
	
	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */

	protected void update(ArrayList<String> pressedKeys, Map m) {

	}
	


	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		
		if (displayImage != null && this.visible) {
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);
			
			/* Actually draw the image, perform the pivot point translation here */
			g2d.drawImage(displayImage, 
					0, 
					0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		} 
//		else System.out.println("Not drawing " + id);
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,this.alpha));
		g2d.translate(pos.x, pos.y);
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.translate(-pos.x, -pos.y);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
	}
	
	public String toString() {
		return id;
	}
	public boolean isFixed() {
		return fixed;
	}
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	public boolean isPlayer() {
		return false;
	}
	public boolean exists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
	public void setPosX(double x) {
		this.pos.x = (int) x;
		
	}
	public void setPosY(double val) {
		this.pos.y = (int) val;
	}
	public void setAlpha(double val) {
		alpha = (float) val;
	}
	
	public Point getPos() {
		return pos;
	}
	public float getAlpha() {
		return alpha;
	}

}