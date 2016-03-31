package Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;

public class Map {
	Tile[][] tiles;
	
	public Map(String mapFile) {
		
		//take in an XML file from res/maps/<mapFile>
		//
		
		try {
			File fXmlFile = new File("res" + File.separator + "maps" + File.separator + mapFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			//normalize document
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
