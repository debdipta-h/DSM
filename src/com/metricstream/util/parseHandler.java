package com.metricstream.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class parseHandler extends DefaultHandler{
	public void startDocument(){
		System.out.println("Start parsing the document");
	}
	public void endDocument(){
		System.out.println("\nend parsing the document");
	}
	public void startElement(String uri, String localName,
            String qName, Attributes attributes)throws SAXException{
		System.out.println("<"+qName+">");
	}
	public void endElement(String uri, String localName,
            String qName)throws SAXException{
		System.out.println("<"+qName+">");
	}
	public void Characters(char ch[], int start, int length)throws SAXException{
		for(int i=start;i<(start+length);i++)
			System.out.println(ch[i]);
	}
	
	
	
	
	

}
