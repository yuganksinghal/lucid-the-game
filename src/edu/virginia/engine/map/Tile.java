package edu.virginia.engine.map;

import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage image;
	private boolean isPortal;
	private Map portalTo;
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public boolean isPortal() {
		return isPortal;
	}
	public void setPortal(boolean isPortal) {
		this.isPortal = isPortal;
	}
	public Map getPortalTo() {
		return portalTo;
	}
	public void setPortalTo(Map portalTo) {
		this.portalTo = portalTo;
	}
	
	
	
}
