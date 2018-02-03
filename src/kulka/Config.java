package kulka;
import org.w3c.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Config {
	public  final static String ConfigFile="src\\kulka\\config.xml";
	public static int FrameWidth;
	public static  int FrameHeight; 
	public static  int FrameSetX;
	public static  int FrameSetY;
	public static  String Title;
	public static  String HighScoreFile;
	public static  String world1;
	public static  String world2;
	public   int ballX;
	public   int ballY;
	public   int goalX;
	public   int goalY;
	public double size;
	public Config(){
		getCoridantes();
	}
	public static void Configuration(){
		try{
		
		File xmlInputFile = new File(ConfigFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlInputFile);
        doc.getDocumentElement().normalize();
        FrameWidth=Integer.parseInt(doc.getElementsByTagName("FrameWidth").item(0).getTextContent());
        FrameHeight=Integer.parseInt(doc.getElementsByTagName("FrameHeight").item(0).getTextContent());
        FrameSetX=Integer.parseInt(doc.getElementsByTagName("FrameSetX").item(0).getTextContent());
        FrameSetY=Integer.parseInt(doc.getElementsByTagName("FrameSetY").item(0).getTextContent());
        Title=doc.getElementsByTagName("Title").item(0).getTextContent();
        HighScoreFile=doc.getElementsByTagName("HighScoreFile").item(0).getTextContent();
        world1=doc.getElementsByTagName("World1").item(0).getTextContent();
        world2=doc.getElementsByTagName("World2").item(0).getTextContent();
        
		}
	catch(Exception e){
	    e.printStackTrace();
	        }
	}
	public  void  worldConfiguration(ObstacleList obstacleList,String worldXml) throws ParserConfigurationException{
		
		try {
			File xmlInputFile= new File(worldXml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(xmlInputFile);
			doc.getDocumentElement().normalize();
			NodeList world1List = doc.getElementsByTagName("Obstacle");
			for(int i=0; i<world1List.getLength();i++){
				Node world1 = world1List.item(i);
				if(world1.getNodeType()== Node.ELEMENT_NODE){
					Element obstacleElement = (Element) world1;
					obstacleList.addObstacle(Integer.parseInt(obstacleElement.getElementsByTagName("X").item(0).getTextContent()),Integer.parseInt(obstacleElement.getElementsByTagName("Y").item(0).getTextContent()), Integer.parseInt(obstacleElement.getElementsByTagName("width").item(0).getTextContent()),Integer.parseInt(obstacleElement.getElementsByTagName("height").item(0).getTextContent()));			
				}
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	public void getCoridantes(){
		try{
		File xmlInputFile= new File("src\\kulka\\config.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlInputFile);
		doc.getDocumentElement().normalize();
	    ballX=Integer.parseInt(doc.getElementsByTagName("BallX").item(0).getTextContent());
        ballY=Integer.parseInt(doc.getElementsByTagName("BallY").item(0).getTextContent());
        goalX=Integer.parseInt(doc.getElementsByTagName("GoalX").item(0).getTextContent());
        goalY=Integer.parseInt(doc.getElementsByTagName("GoalY").item(0).getTextContent());
        size=Double.parseDouble(doc.getElementsByTagName("SizeObstacle").item(0).getTextContent());
        
	}
	catch(IOException e){
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
}

