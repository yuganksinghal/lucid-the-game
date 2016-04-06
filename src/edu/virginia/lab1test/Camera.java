package edu.virginia.lab1test;

import java.awt.Point;

import edu.virginia.engine.display.DisplayObject;

public class Camera {
	Point offset;
	int width;
	int height;
	DisplayObject focus;
	
	/**
	 * @param width
	 * @param height
	 * @param focus
	 */
	public Camera(int width, int height, DisplayObject focus) {
		offset = new Point(0,0);
		this.width = width;
		this.height = height;
		this.focus = focus;
	}
	
	
	/**
	 * 
	 */
	public void update() {
		// TODO: update camera mechanics
		//if map.height < game.height, center map vertically. 
		
		//else:
//		offset.x = (int) ((width/2) + (focus.getUnscaledWidth()/2));
		offset.x = -focus.getPos().x + (int) (width/2) - focus.getUnscaledWidth(); 
		
		//if map.width < game.width, center map horizontally. 
		
		//else:
		offset.y = -focus.getPos().y + (int) (height/2) - focus.getUnscaledHeight(); 
//		System.out.println("offset x: " + offset.x + "\toffset y: " + offset.y);
	}
}
