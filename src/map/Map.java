package map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Map {
	Tile[][] foreground;
	boolean[][] collidables;
	Tile[][] background;
	boolean[][] occupied;

	boolean debug = true;

	int width;
	int height;
	int tileWidth;
	int tileHeight;
	ArrayList<Tileset> tilesets;

	public Map(String mapFile) {
		tilesets = new ArrayList<Tileset>();

		// take in an XML file from res/maps/<mapFile>
		//

		try {
			File fXmlFile = new File("res" + File.separator + "maps" + File.separator + mapFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// normalize document
			doc.getDocumentElement().normalize();

			// CREATE TILESETS IN ENGINE

			NodeList tilesetList = doc.getElementsByTagName("tileset");

			// (untested)
			for (int i = 0; i < tilesetList.getLength(); i++) {
				Node tNode = tilesetList.item(i);
				Element tElement = (Element) tNode;
				int firstGid = Integer.parseInt(tElement.getAttribute("firstgid"));
				int tileWidth = Integer.parseInt(tElement.getAttribute("tilewidth"));
				int tileHeight = Integer.parseInt(tElement.getAttribute("tileheight"));

				String tilesetName = tElement.getAttribute("name");

				Node imageNode = tNode.getFirstChild();
				Element iElement = (Element) tElement.getElementsByTagName("image").item(0);
				int imageHeight = Integer.parseInt(iElement.getAttribute("height"));
				int imageWidth = Integer.parseInt(iElement.getAttribute("width"));
				String tilesetImagePath = iElement.getAttribute("source");

				Tileset ts = new Tileset(tilesetName, tilesetImagePath, tileWidth, tileHeight, firstGid, imageWidth,
						imageHeight);
				tilesets.add(ts);
			}

			// tilesets finished being initialized

			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			// tileheight
			// tilewidth
			// height
			// width
			NodeList nList = doc.getElementsByTagName("map");
			Node map = nList.item(0);
			Element eElement = (Element) map;
			this.width = Integer.parseInt(eElement.getAttribute("width"));
			this.height = Integer.parseInt(eElement.getAttribute("height"));
			this.tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
			this.tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
			if (debug)
				System.out.println(width + " " + height + " " + tileWidth + " " + tileHeight);

			NodeList layerList = doc.getElementsByTagName("layer");

			Node backgroundLayer = layerList.item(0);
			// Node collisionLayer = layerList.item(1);
			Node foregroundLayer = layerList.item(1);

			this.background = new Tile[height][width];
			this.foreground = new Tile[height][width];

			NodeList backgroundTiles = backgroundLayer.getChildNodes().item(1).getChildNodes();
			NodeList foregroundTiles = foregroundLayer.getChildNodes().item(1).getChildNodes();

			for (int j = 0; j < this.height; j++) {
				for (int i = 0; i < this.width; i++) {
					Node curNode = backgroundTiles.item((j * this.width + i) * 2 + 1); // turns
					// out
					// there
					// is
					// a
					// blank
					// "text"
					// node
					// between
					// each
					// node
					// when
					// parsing
					// xml
					Element tempElement = (Element) curNode;
					Tile t = new Tile(); // TODO: refer to tiled tutorial for
					// how to do this
					int gid = Integer.parseInt(tempElement.getAttribute("gid"));
					for (Tileset sheet : tilesets) {
						if ((gid > sheet.lastGid) || (gid < sheet.firstGid))
							continue;
						t.setImage(sheet.makeTileImage(gid));
						t.setPortal(false);
						t.setPortalTo(null);
						break;
					}
					this.background[j][i] = t;
				}
			}

			for (int j = 0; j < this.height; j++) {
				for (int i = 0; i < this.width; i++) {
					Node curNode = foregroundTiles.item((j * this.width + i) * 2 + 1);
					Element tempElement = (Element) curNode;
					Tile t = new Tile(); // TODO: refer to tiled tutorial for
					// how to do this
					int gid = Integer.parseInt(tempElement.getAttribute("gid"));
					for (Tileset sheet : tilesets) {
						if ((gid > sheet.lastGid) || (gid < sheet.firstGid))
							continue;
						t.setImage(sheet.makeTileImage(gid));
						t.setPortal(false);
						t.setPortalTo(null);
						break;
					}
					this.foreground[j][i] = t;
				}
			}
			
			for (int j = 0; j < this.height; j++) {
				for (int i = 0; i < this.width; i++) {
					Node curNode = foregroundTiles.item((j * this.width + i) * 2 + 1);
					Element tempElement = (Element) curNode;
					// how to do this
					int gid = Integer.parseInt(tempElement.getAttribute("gid"));
					if(gid != 0)
						this.collidables[j][i] = true;
					else
						this.collidables[j][i] = false;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void drawBackground(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		for (int j = 0; j < this.height; j++) {
			for (int i = 0; i < this.width; i++) {
				if (background[j][i].getImage() == null)
					continue;
				g2d.translate(i * tileWidth, j * tileHeight);
				g2d.drawImage(background[j][i].getImage(), null, null);
				g2d.translate(-i * tileWidth, -j * tileHeight);
			}
		}
	}

	public void drawForeground(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		for (int j = 0; j < this.height; j++) {
			for (int i = 0; i < this.width; i++) {
				if (foreground[j][i].getImage() == null)
					continue;
				g2d.translate(i * tileWidth, j * tileHeight);
				g2d.drawImage(foreground[j][i].getImage(), null, null);
				g2d.translate(-i * tileWidth, -j * tileHeight);
			}
		}
	}

	public boolean[][] getCollidables() {
		return collidables;
	}

	public void setCollidables(boolean[][] collidables) {
		this.collidables = collidables;
	}

	public boolean[][] getOccupied() {
		return occupied;
	}

	public void setOccupied(boolean[][] occupied) {
		this.occupied = occupied;
	}
	
	public void occupy(int i, int j) {
		this.occupied[i][j] = true;
	}
	public void vacate(int i, int j) {
		this.occupied[i][j] = false;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public boolean checkCollision(int i, int j) {
		return collidables[i][j] && occupied[i][j];
	}

}
