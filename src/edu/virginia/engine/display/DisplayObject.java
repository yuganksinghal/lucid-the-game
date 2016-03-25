package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import edu.virginia.engine.Sys;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.events.PickedUpEvent;
import edu.virginia.lab1test.LabOneGame;

public class DisplayObject implements IEventListener {

	/*
	 * MASTER TO-DO LIST:
	 * DONE: implement checks for scaling and rotation so they don't break hitboxes
	 * TODO: figure out how to fix collision issues with children.
	 * TODO: re-implement item pick-up in a way that works.
	 * TODO: find a way to detect if the character is on the ground
	 */
	
	
	// Fields
	
	protected String id; // all DOs have a unique ID
	private DisplayObject parent;
	private BufferedImage displayImage;

	private boolean exists;
	protected boolean collideable;
	protected boolean visible;
	protected boolean fixed;
	protected boolean grounded;
	protected Point pos;
	protected Point piv;
	protected Point.Double scale;
	protected Point.Double scaleDelta;
	protected Point.Double velocity;
	protected Point.Double acceleration;
	protected int degreesRotation;
	protected int rotationDelta;
	protected float alpha;
	
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
		this.piv = new Point(0,0);
		this.scale = new Point.Double(1,1);
		this.scaleDelta = new Point.Double(0,0);
		this.velocity = new Point.Double(0,0);
		this.acceleration = new Point.Double(0,0);
		this.degreesRotation = 0;
		this.rotationDelta = 0;
		this.alpha = 1f;
		this.piv.x = (int) (getUnscaledWidth() * scale.x / 2);
		this.piv.y = (int) (getUnscaledHeight() * scale.y / 2);
		this.relPos = new Point(0,0);
		this.relScale = new Point.Double(1,1);
		this.relRotation = 0;
		this.grounded = false;
		Sys.collisionManager.addEventListener(this, CollisionEvent.COLLISION_DETECTED);
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
	public void setScale(double x) {
		scale.setLocation(x,x);
	}
	public double getScaleX() {
		return scale.x;
	}
	public double getScaleY() {
		return scale.y;
	}
	public int getRotation() {
		return degreesRotation;
	}
	public void setRotation(int deg) {
		degreesRotation = deg;
	}
	public void setRelativeRotation(int deg) {
		relRotation = deg;
	}
	public void setRelativePosition(int x, int y) {
		relPos.setLocation(x, y);
	}
	public void setParent(DisplayObject obj) {
		parent = obj;
	}
	public void setRelScale(double x) {
		relScale.setLocation(x,x);
	}
	public boolean collidesWith(DisplayObject obj) {
		if (!obj.collideable || !this.collideable || this == obj) return false;
		return this.getHitbox().intersects(obj.getHitbox());
	}
	public void setAccelX(double a) {
		acceleration.x = a;
	}
	public void setAccelY(double a) {
		acceleration.y = a;
	}
	public double getAccelY() {
		return acceleration.y;
	}
	public double getAccelX() {
		return acceleration.x;
	}
	public double getVelY() {
		return velocity.y;
	}
	public double getVelX() {
		return velocity.x;
	}
	public Point.Double getScaleDelta() {
		return scaleDelta;
	}
	public void setScaleDelta(Point.Double scaleDelta) {
		this.scaleDelta = scaleDelta;
	}
	public void setScaleDelta(double s) {
		this.scaleDelta = new Point.Double(s,s);
	}
	public int getRotationDelta() {
		return rotationDelta;
	}
	public void setRotationDelta(int rotationDelta) {
		this.rotationDelta = rotationDelta;
	}
	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<String> pressedKeys) {
		//normalize values
		if (degreesRotation >= 360 || degreesRotation <= -360) degreesRotation %= 360;
		if (scale.x < 0.01) scale.x = 0.05;
		if (scale.y < 0.01) scale.y = 0.05;


		
		//make movements
		
		if (parent == null) {

			//
			velocity.x = velocity.x + acceleration.x;
			pos.x = pos.x + (int) velocity.x;
			if (hasCollisions()) {
				pos.x -= (int) velocity.x;
				velocity.x -= acceleration.x;
			}

			velocity.y = velocity.y + acceleration.y;
			pos.y = pos.y + (int) velocity.y;
			if (hasCollisions()) {
				pos.y -= (int) velocity.y;
				velocity.y -= acceleration.y;
				this.grounded = true;
			}
			else this.grounded = false;
			
			scale.x += scaleDelta.x;
			scale.y += scaleDelta.y;
			if (hasCollisions()) {
				scale.x -= scaleDelta.x;
				scale.y -= scaleDelta.y;
			}
			//reset scale delta
			scaleDelta.x = scaleDelta.y = 0;
			
			degreesRotation += rotationDelta;
			if (hasCollisions()) {
				degreesRotation -= rotationDelta;
			}
			rotationDelta = 0;

		}
		
		else if (parent != null) {
			/*
			 * NOTES:
			 * drawing should only take into account pivot point, not relative position
			 * for these. which means we could maybe just compensate for the negative position?
			 */
			pos.x = parent.pos.x;
			pos.y = parent.pos.y;
			degreesRotation = parent.degreesRotation + relRotation;
			piv.x = - (int) (1*relPos.x*parent.scale.x) + parent.piv.x;
			piv.y = - (int) (1*relPos.y*parent.scale.y) + parent.piv.y;

			scale.x = parent.scale.x * relScale.x;
			scale.y = parent.scale.y * relScale.y;
		}
		hasCollisions();
	}
	
	private boolean hasCollisions() {
		for (DisplayObject obj : Sys.spriteList) {
			if (!this.id.equals("Test") && this.collidesWith(obj)) {
				Sys.collisionManager.dispatchEvent(new CollisionEvent(this,obj));
				return true;
			}
		}
		return false;
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
					(int) (-this.piv.x), 
					(int) (-this.piv.y),
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
			
			g2d.drawRect(getHitbox().x,getHitbox().y,getHitbox().width,getHitbox().height);
		} else System.out.println("Not drawing " + id);
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,this.alpha));
		g2d.translate(pos.x, pos.y);
//		System.out.println("moving g2d by: " + pos.x + ", " + pos.y);
		g2d.rotate(Math.toRadians(this.degreesRotation), 0, 0);
//		System.out.println("rotating g2d by: " + this.degreesRotation);
		g2d.scale(scale.x, scale.y);
//		System.out.println("scaling g2d by: " + scale.x + ", " + scale.y);
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.scale(1/scale.x,1/scale.y);
		g2d.rotate(Math.toRadians(-this.degreesRotation), 0, 0);
		g2d.translate(-pos.x, -pos.y);
//		System.out.println("moving g2d by: " + -pos.x + ", " + -pos.y);
//		System.out.println("rotating g2d by: " + -this.degreesRotation);
//		System.out.println("scaling g2d by " + (1/scale.x) + ", " + (1/scale.y));

		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

	}
	@Override
	public void handleEvent(Event event) {
		if (event.eventType.equals(CollisionEvent.COLLISION_DETECTED)) {
			CollisionEvent e = (CollisionEvent) event;
			if (e.o1.isPlayer() && Sys.itemList.contains(e.o2) ) {
				Sys.questManager.dispatchEvent(new PickedUpEvent((Item) e.o2));
			}
			else if (e.o2.isPlayer() && Sys.itemList.contains(e.o1)) {
				Sys.questManager.dispatchEvent(new PickedUpEvent((Item) e.o1));

			}
		}
		
	}
	
	public Rectangle getHitbox() {
		
		Point[] points = new Point[4];
		points[0] = new Point(0,0);
		points[1] = new Point((int)(this.getUnscaledWidth()*this.scale.x), 0);
		points[2] = new Point((int)(this.getUnscaledWidth()*this.scale.x), (int)(this.getUnscaledHeight()*this.scale.y));
		points[3] = new Point(0,(int)(this.getUnscaledHeight()*this.scale.y));

		
		double theta = -Math.toRadians(this.degreesRotation);
		double s = Math.sin(theta);
		double c = Math.cos(theta);
		for (int i = 0 ; i < 4 ; i++) {
			
			//translate pivot to origin
			int px = points[i].x - (int) (piv.x*scale.x);
			int py = points[i].y - (int) (piv.y*scale.y);
			
			//calculate rotation
			double newX = (px*c) + (py*s);
			double newY = -(px*s) + (py*c);
			
			//translate back, move point
			points[i].x = (int) newX + pos.x;
			points[i].y = (int) newY + pos.y;
		}
			
		int minx = points[0].x;
		int maxx = points[0].x;
		int miny = points[0].y;
		int maxy = points[0].y;
		for (int i = 1 ; i < 4 ; i++) {
			if (minx > points[i].x) minx = points[i].x;
			if (maxx < points[i].x) maxx = points[i].x;
			if (miny > points[i].y) miny = points[i].y;
			if (maxy < points[i].y) maxy = points[i].y;
		}
		
		Rectangle rect = new Rectangle(minx,miny,maxx-minx,maxy-miny);
			
		return rect;
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
	public void setScaleX(double value) {
		scale.x = value;
	}
	public void setScaleY(double value) {
		scale.y = value;
	}
	public Point getPos() {
		return pos;
	}
	public float getAlpha() {
		return alpha;
	}

}