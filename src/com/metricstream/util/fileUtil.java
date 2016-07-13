package com.metricstream.util;

import java.io.File;  
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.metricstream.domain.ObjectItem;

import gnu.trove.map.hash.THashMap;
 
public class fileUtil {
	private Node getchild(Document doc,ObjectItem ob,int id, THashMap<Integer, String> iupentrylist){
		Element newnode=doc.createElement("Entry");
		newnode.setAttribute("id",Integer.toString(id));
		newnode.appendChild(getChildElement(doc,"Relation",ob.getRelation()));
		newnode.appendChild(getObjectElement(doc,"First_Object",iupentrylist.get(ob.getObjectone())));
		newnode.appendChild(getObjectElement(doc,"Second_Object",iupentrylist.get(ob.getObjecttwo())));
		return newnode;
	}
		
	private Node getObjectElement(Document doc, String tagname,String name) {
		Element objectnode=doc.createElement(tagname);
		objectnode.appendChild(getChildElement(doc,"NAME",name));
		objectnode.appendChild(getChildElement(doc,"TYPE","Dashboard"));
		objectnode.appendChild(getChildElement(doc,"USER","SYSTEMI"));
		return objectnode;
	}

	private Node getChildElement(Document doc, String type,String name) {
		Element relationnode=doc.createElement(type);
		relationnode.appendChild(doc.createCDATASection(name));
		return relationnode;
	}

	
	public void generateXml(ArrayList<ObjectItem> objects,THashMap<Integer,String> iupentrylist)throws Exception{	  
       DocumentBuilderFactory newfactory=DocumentBuilderFactory.newInstance();
       DocumentBuilder newbuild;
       int seqnumber=1;
       try{
    	   newbuild=newfactory.newDocumentBuilder();
    	   Document doc=newbuild.newDocument();
    	   Element rootelement=doc.createElement("Entries");
    	   doc.appendChild(rootelement);
    	   for(ObjectItem ob:objects){
    		   rootelement.appendChild(getchild(doc,ob,seqnumber,iupentrylist));
    		   seqnumber++;
    	   }
    	   writeToFile(doc);
    			
    	   }
       catch(Exception e ){
    	   e.printStackTrace();
	}
       
	   
 }
	
	
	private void writeToFile(Document doc)throws Exception{
		try{
		    Transformer transformer=TransformerFactory.newInstance().newTransformer();
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    StreamResult output=new StreamResult(new File("E:\\sequenceXML.xml"));
		    DOMSource source=new DOMSource(doc);
		    transformer.transform(source, output);
		  
		} catch(Exception e){
			e.printStackTrace();
			System.err.println("Writing to the xml file failed");
			System.out.println(e);
		}	
	}

}
