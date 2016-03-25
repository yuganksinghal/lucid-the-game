package edu.virginia.engine.display;

import java.awt.Polygon;


public class Hitbox {
	public int[] pointsX;
	public int[] pointsY;
	private int[] oPointsX;
	private int[] oPointsY;
	private DisplayObject owner;
	
	
	
	public Hitbox(DisplayObject obj) {
		pointsX = new int[4];
		pointsY = new int[4];
		oPointsX = new int[4];
		oPointsY = new int[4];
		owner = obj;
		
		
		//assigns original points
		
		oPointsX[0] = pointsX[0] = 0;
		oPointsY[0] = pointsY[0] = 0;
		
		oPointsX[1] = pointsX[1] = (int) (owner.getUnscaledWidth()*owner.scale.x);
		oPointsY[1] = pointsY[1] = 0;
		
		oPointsX[3] = pointsX[3] = 0;
		oPointsY[3] = pointsY[3] = (int) (owner.getUnscaledHeight()*owner.scale.y);

		oPointsX[2] = pointsX[2] = (int) (owner.getUnscaledWidth()*owner.scale.x);
		oPointsY[2] = pointsY[2] = (int) (owner.getUnscaledHeight()*owner.scale.y);
		
		rotate(-Math.toRadians(owner.degreesRotation));
		
		//translate according to pivot
		
		for (int i = 0 ; i < pointsX.length ; i++) { pointsX[i] += owner.pos.x - owner.piv.x;}
		for (int i = 0 ; i < pointsX.length ; i++) { pointsY[i] += owner.pos.y - owner.piv.y;}

	}
	
	/**
	 * Constructs a hitbox used for testing purposes, to see if collisions will occur if the Sprite moves to a new location.
	 * 
	 * 
	 * @param	posx	X-coordinate of position
	 * @param	posy	Y-coordinate of position
	 * @param	width	width of hitbox
	 * @param	height	height of hitbox
	 * @param	rotation	degrees rotated
	 * @param	pivx	x-coordinate of pivot
	 * @param	pivy	y-coordinate of pivot
	 * @return			a hitbox that can be used for testing collisions
	 */
	public Hitbox(int posx, int posy, int width, int height, int rotation, int pivx, int pivy,double scalex, double scaley) {
		pointsX = new int[4];
		pointsY = new int[4];
		oPointsX = new int[4];
		oPointsY = new int[4];
		
		
		//assigns original points
		
		oPointsX[0] = pointsX[0] = 0;
		oPointsY[0] = pointsY[0] = 0;
		
		oPointsX[1] = pointsX[1] = width;
		oPointsY[1] = pointsY[1] = 0;
		
		oPointsX[3] = pointsX[3] = 0;
		oPointsY[3] = pointsY[3] = height;

		oPointsX[2] = pointsX[2] = width;
		oPointsY[2] = pointsY[2] = height;
		
		testRotate(-Math.toRadians(rotation),pivx,pivy,scalex,scaley);
		
		//translate according to pivot
		
		for (int i = 0 ; i < pointsX.length ; i++) { pointsX[i] += posx - pivx;}
		for (int i = 0 ; i < pointsX.length ; i++) { pointsY[i] += posy - pivy;}

	}
	/**
	 * Updates the hitbox to the latest coordinates of its owner.
	 */
	public void update() {
		pointsX[0] = 0;
		pointsY[0] = 0;
		
		pointsX[1] = (int) (owner.getUnscaledWidth()*owner.scale.x);
		pointsY[1] = 0;
		
		pointsX[3] = 0;
		pointsY[3] = (int) (owner.getUnscaledHeight()*owner.scale.y);

		pointsX[2] = (int) (owner.getUnscaledWidth()*owner.scale.x);
		pointsY[2] = (int) (owner.getUnscaledHeight()*owner.scale.y);
		
		rotate(-Math.toRadians(owner.degreesRotation));
		
		for (int i = 0 ; i < pointsX.length ; i++) { pointsX[i] += owner.pos.x - (int) (owner.piv.x*owner.scale.x);}
		for (int i = 0 ; i < pointsX.length ; i++) { pointsY[i] += owner.pos.y - (int) (owner.piv.y*owner.scale.y);}
	}
	
	/**
	 * Rotates the hitbox around the owner's pivot point
	 * @param	rad	The number of radians the hitbox should be rotated.
	 */
	public void rotate(double rad) {
		double theta = rad;
		double s = Math.sin(theta);
		double c = Math.cos(theta);
		
		for (int i = 0 ; i < pointsX.length ; i++) {
			
			//translate pivot to origin
			int px = pointsX[i] - (int) (owner.piv.x*owner.scale.x);
			int py = pointsY[i] - (int) (owner.piv.y*owner.scale.y);
			
			//calculate rotation
			double newX = (px*c) + (py*s);
			double newY = -(px*s) + (py*c);
			
			//translate back, move point
			pointsX[i] = (int) newX + (int) (owner.piv.x*owner.scale.x);
			pointsY[i] = (int) newY + (int) (owner.piv.y*owner.scale.y);
		}
	}
	
	public void testRotate(double rad, int pivx, int pivy, double scalex, double scaley) {
		double theta = rad;
		double s = Math.sin(theta);
		double c = Math.cos(theta);
		
		for (int i = 0 ; i < pointsX.length ; i++) {
			
			//translate pivot to origin
			int px = pointsX[i] - (int) (pivx*scalex);
			int py = pointsY[i] - (int) (pivy*scaley);
			
			//calculate rotation
			double newX = (px*c) + (py*s);
			double newY = -(px*s) + (py*c);
			
			//translate back, move point
			pointsX[i] = (int) newX + (int) (pivx*scalex);
			pointsY[i] = (int) newY + (int) (pivy*scaley);
		}
	}
	
	/**
	 * Checks if two hitboxes collide.
	 * @param hb The other, non-this hitbox that is being checked against.
	 * @return True if the hitboxes collide. Otherwise, returns false.
	 */
	public boolean collidesWith(Hitbox hb) {
		for (int i = 0 ; i < 4 ; i++) {
			if (this.containsPoint(hb.pointsX[i], hb.pointsY[i])) {
				return true;
			}
		}
		
		for (int i = 0 ; i < 4 ; i++) {
			if (hb.containsPoint(this.pointsX[i], this.pointsY[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Creates a polygon from the hitbox to check if it contains a specific point (x,y).
	 * @param x	the x-coordinate of the point
	 * @param y	the y-coordinate of the point
	 * @return	True if the hitbox contains point (x,y). Otherwise false.
	 */
	private boolean containsPoint(int x, int y) {
		Polygon p = new Polygon(pointsX, pointsY, pointsX.length);
		return p.contains(x,y);
	}


	public String toString() {
		String s = "X: ";
		for (int i = 0 ; i < 4 ; i++) {
			s += "\t" + pointsX[i];
		}
		s += "\nY: ";
		for (int i = 0 ; i < 4 ; i++) {
			s += "\t" + pointsY[i];
		}
		return s;
	}
}
