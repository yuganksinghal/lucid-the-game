package edu.virginia.engine.display;

import java.awt.Graphics;
import java.util.ArrayList;

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
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		for (DisplayObject child : children) {
			child.update(pressedKeys);
		}
	}
}
