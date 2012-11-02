package test;

import org.w3c.dom.*;   
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;
import javax.xml.parsers.*;

public class xmlReader {
	intString rresult=new intString();
	public intString viewDocument(String inputString) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	 	DocumentBuilder db;
	 	db = dbf.newDocumentBuilder();
		Document doc = db.parse(inputString);
		Element element = doc.getDocumentElement();
		//get down to the desired part
	    NodeList content = element.getElementsByTagName("content");
	   //.
	    Node test=content.item(content.getLength()-1);
	    NodeList property=test.getChildNodes();
	    Node property0=property.item(0);
	    NodeList property2=property0.getChildNodes();
	    Node property3=property2.item(1);
	    
	    //test region
	    	for(int i=0;i<content.getLength()-1;i++){
	    	NodeList content5 = element.getElementsByTagName("content");
		 // System.out.println(content5.getLength());
		    Node test5=content5.item(i);
		    NodeList property5=test5.getChildNodes();
		    Node property05=property5.item(0);
		    NodeList property25=property05.getChildNodes();
		    Node property35=property25.item(4);
		    String ut=property35.getFirstChild().getNodeValue();
		   // System.out.println("here is result: "+property35.getFirstChild().getNodeValue());
	    //System.out.println(property3.getFirstChild().getNodeValue());
		    rresult.totalnumber=Integer.parseInt(property3.getFirstChild().getNodeValue());
		    rresult.urls.add(ut);
	    	}
		return rresult;//Integer.parseInt(property3.getFirstChild().getNodeValue());
     }
	
}
