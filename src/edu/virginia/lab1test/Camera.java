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
		//if map.height < game.height, center map vertically. 
		
		//else:
		offset.x = (int) ((width/2) - (focus.getUnscaledWidth()/2));
		
		//if map.width < game.width, center map horizontally. 
		
		//else:
		offset.y = (int) ((height/2) - (focus.getUnscaledHeight()/2));
	}
}
