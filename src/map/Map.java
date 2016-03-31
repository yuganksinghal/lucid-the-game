package map;

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
	Tile[][] collidables;
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
		
		//take in an XML file from res/maps/<mapFile>
		//
		
		try {
			File fXmlFile = new File("res" + File.separator + "maps" + File.separator + mapFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			//normalize document
			doc.getDocumentElement().normalize();
			
			
			
			//CREATE TILESETS IN ENGINE
			
			NodeList tilesetList = doc.getElementsByTagName("tileset");
			
			// (untested)
			for (int i = 0 ; i < tilesetList.getLength() ; i++) {
				Node tNode = tilesetList.item(i);
				Element tElement = (Element) tNode;
				int firstGid = Integer.parseInt(tElement.getAttribute("firstgid"));
				int tileWidth = Integer.parseInt(tElement.getAttribute("tilewidth"));
				int tileHeight = Integer.parseInt(tElement.getAttribute("tileheight"));
				
				String tilesetName = tElement.getAttribute("name");
				
				
				Node imageNode = tNode.getFirstChild();
				System.out.println("DEBUG:" + tElement.getChildNodes());
				Element iElement = (Element) tElement.getElementsByTagName("image").item(0);
				int imageHeight = Integer.parseInt(iElement.getAttribute("height"));
				int imageWidth = Integer.parseInt(iElement.getAttribute("width"));
				String tilesetImagePath = iElement.getAttribute("source");

				Tileset ts = new Tileset(tilesetName, tilesetImagePath, tileWidth, tileHeight, firstGid, imageWidth, imageHeight);
				tilesets.add(ts);
			}
			
			//tilesets finished being initialized
			
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			//tileheight
			//tilewidth
			//height
			//width
			NodeList nList = doc.getElementsByTagName("map");
			Node map = nList.item(0);
			Element eElement = (Element) map;
			this.width = Integer.parseInt(eElement.getAttribute("width"));
			this.height = Integer.parseInt(eElement.getAttribute("height"));
			this.tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
			this.tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
			if (debug) System.out.println(width + " " + height + " " + tileWidth + " " + tileHeight);
			
			NodeList layerList = doc.getElementsByTagName("layer");
			
			Node backgroundLayer = layerList.item(0);
			Node collisionLayer = layerList.item(1);
			Node foregroundLayer = layerList.item(2);
			
			this.background = new Tile[width][height];
			
			NodeList backgroundTiles = backgroundLayer.getChildNodes();
			
			for (int j = 0 ; j < this.height ; j++) {
				for (int i = 0 ; i < this.width ; i++) {
					Node curNode = backgroundTiles.item(j*this.height + i);
					Tile t = new Tile(); //TODO: refer to tiled tutorial for how to do this
					this.background[i][j] = t;
				}
			}
			
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
