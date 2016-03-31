package map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tileset {
	String tilesetImagePath;
	int tileWidth;
	int tileHeight;
	int firstGid;
	String tilesetName;
	int imageWidth;
	int imageHeight;
	int tileAmountWidth;
	int tileAmountHeight;
	int lastGid;
	BufferedImage image;
	
	
	
	public Tileset(String tilesetName, String tilesetImagePath, int tileWidth, int tileHeight,
			int firstGid, int imageWidth, int imageHeight) {
		
		
		super();
		this.tilesetImagePath = tilesetImagePath.split("/")[tilesetImagePath.split("/").length -1];
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.firstGid = firstGid;
		this.tilesetName = tilesetName;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.tileAmountWidth = (int) Math.floor(this.imageWidth / this.tileWidth);
		this.tileAmountHeight = (int) Math.floor(this.imageHeight / this.tileHeight);
		this.lastGid = this.tileAmountWidth * (int) Math.floor(this.imageHeight / this.tileHeight) + this.firstGid - 1;
		this.image = readImage(tilesetImagePath);
	}

	private BufferedImage readImage(String imageName) {
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

	public BufferedImage makeTileImage(int gid) {
		if (gid > lastGid || gid < firstGid) {
			int localGid = gid - firstGid;
			int x = localGid % tileAmountWidth;
			int y = Math.floorDiv(localGid, tileAmountWidth);
			
			return image.getSubimage(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
			
			
			
			
		} else System.out.println("GID NOT IN TILESET. SOMEONE MESSED UP");
		return null;
	}
}
