package edu.virginia.engine.display;

import java.awt.Graphics;
import java.util.ArrayList;

import map.Map;

public class DisplayObjectContainer extends DisplayObject {
	
	private ArrayList<DisplayObject> children;

	public DisplayObjectContainer(String id) {
		super(id);
		construct();
	}
	public DisplayObjectContainer(String id, String imageName) {
		super(id, imageName);
		construct();
	}
	
	private void construct() {
		children = new ArrayList<DisplayObject>();
	}

	public void addChild(DisplayObject obj) {
		children.add(obj);
		obj.setParent(this);
	}
	
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		for (DisplayObject child : children) {
			child.draw(g);
		}
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys, Map map) {
		super.update(pressedKeys, map);
		for (DisplayObject child : children) {
			child.update(pressedKeys, map);
		}
	}
	
	@Override
	public boolean collidesWith(DisplayObject obj) {
		if (super.collidesWith(obj)) return true;
		else {
			for (DisplayObject child : children) {
				if (child.collidesWith(obj)) return true;
			}
			return false;
		}
	}
}
