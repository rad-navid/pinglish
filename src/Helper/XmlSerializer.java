package Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import SCICT.NLP.Utility.PinglishConverter.PinglishString;
import android.os.Environment;

public class XmlSerializer {


	public XmlSerializer()
	{
		 
	}

	public  LinqSimulationArrayList<PinglishString> Load(InputStream inputStream)
	{
		try {
		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    factory.setNamespaceAware(true);
		    XmlPullParser parser = factory.newPullParser();
		    parser.setInput(inputStream, null);
		    int eventType = parser.getEventType();
		    
		    LinqSimulationArrayList<PinglishString>arrayOfPinglishString;
		    
		    while (eventType != XmlPullParser.END_DOCUMENT) {
		        if (eventType == XmlPullParser.START_DOCUMENT) {
		        	arrayOfPinglishString=new LinqSimulationArrayList<PinglishString>(5000);
		        	eventType = parser.nextTag();
		        	if(parser.getName().equalsIgnoreCase("ArrayOfPinglishString"))
		        	{
		        	eventType = parser.nextTag();
		       
		        	
		            while (parser.getName().equalsIgnoreCase("PinglishString")) {
		            	PinglishString pinglishString=new PinglishString();
		            	eventType = parser.nextTag();
		            	if (parser.getName().equalsIgnoreCase("PersianLetters"))
		            	{
		            		ArrayList<String>persianLetters=new LinqSimulationArrayList<String>();
		            		eventType = parser.nextTag();
		            		while(parser.getName().equalsIgnoreCase("string"))
		            		{
		            			String leter=parser.nextText();
		            			persianLetters.add(leter);
		            			eventType = parser.nextTag();
		            		}
		            		pinglishString.setPersianLetters(persianLetters);
		            	}
		            	eventType = parser.nextTag();
		            	if (parser.getName().equalsIgnoreCase("EnglishLetters"))
		            	{
		            		ArrayList<Character>englishLetters=new LinqSimulationArrayList<Character>();
		            		eventType = parser.nextTag();
		            		while(parser.getName().equalsIgnoreCase("char"))
		            		{
		            			int code=Integer.parseInt(parser.nextText());
		            			englishLetters.add(new Character((char)code));
		            			eventType = parser.nextTag();
		            		}
		            		pinglishString.setEnglishLetters(englishLetters);
		            	}
		            	eventType = parser.nextTag();
		            	arrayOfPinglishString.add(pinglishString);
		            	eventType = parser.nextTag();
		            } 
		        }
		            return arrayOfPinglishString;
		        }
		    }
		} catch  (Exception exx)
		{
			
		}
		return null;
	}
	public  void Save(LinqSimulationArrayList<PinglishString> data)  
	{
		 //Create Folder
		  File folder = new File(Environment.getExternalStorageDirectory().toString()+"/Pinglish/Data");
		  if(!folder.exists())
			  folder.mkdirs();

		  //Save the path as a string value
		  String extStorageDirectory = folder.toString();

		  //Create New file and name it Image2.PNG
		  File file = new File(extStorageDirectory, "data.xml");
		  try {
			FileOutputStream stream=new FileOutputStream(file);
			LoaderClass loaderClass=new LoaderClass();
			loaderClass.mappingList=data;
			//xstream.toXML(loaderClass, stream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		
		
	}
	public  LinqSimulationArrayList<PinglishString> StandardLoad(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException  
	{	 
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	 
		Document doc = dBuilder.parse(inputStream);
	 
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
		if (doc.hasChildNodes()) {
			 //System.out.println(doc.getChildNodes().getLength()+"");
			return GetArrayOfPinglishString(doc.getChildNodes());
				
				 
		}//end if		
		return null;
	}

	 private  LinqSimulationArrayList<PinglishString> GetArrayOfPinglishString(NodeList nodeList) {
		 	
		    for (int count = 0; count < nodeList.getLength(); count++) {
		 
			Node tempNode = nodeList.item(count);
		 
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
		 
				// get node name and value
		 
				if (tempNode.hasChildNodes()) {
				
					return GetPinglishString(tempNode.getChildNodes());
					// loop again if has child nodes
					//printNote(tempNode.getChildNodes());
		 
				}
		 

		 
			}
		 
		    }
			return null;
	 }

	 private  LinqSimulationArrayList<PinglishString> GetPinglishString(NodeList nodeList) {
		 	
		 	LinqSimulationArrayList<PinglishString>list=new LinqSimulationArrayList<PinglishString>();
		 	for (int count = 0; count < nodeList.getLength(); count++) {
		 
			Node tempNode = nodeList.item(count);
		 
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
		 
				// get node name and value
				if (tempNode.hasChildNodes()) {
					PinglishString pingishString=new PinglishString();
					for(int pp=0;pp<tempNode.getChildNodes().getLength();pp++)
					{
						Node currentNode=tempNode.getChildNodes().item(pp);
						if(currentNode.getNodeName().equalsIgnoreCase("PersianLetters"))
						{
							pingishString.setPersianLetters(GetPersianLetters(currentNode.getChildNodes()));
						}
						else if(currentNode.getNodeName().equalsIgnoreCase("EnglishLetters"))
						{
							pingishString.setEnglishLetters(GetEnglishLetters(currentNode.getChildNodes()));
						}
						// loop again if has child nodes
						//printNote(tempNode.getChildNodes());
					}
					list.add(pingishString);
		 
				} 
			}
		 
		    }
		 	return list;
	 }
	 private  ArrayList<String> GetPersianLetters(NodeList nodeList) {
		 
		 ArrayList<String> persianString=new ArrayList<String>(nodeList.getLength());
		    for (int count = 0; count < nodeList.getLength(); count++) {
		 
			Node tempNode = nodeList.item(count);
		 
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				persianString.add(tempNode.getTextContent());
			}
		 
		    }
		    return persianString;
	 }
	 private  ArrayList<Character> GetEnglishLetters(NodeList nodeList) {
		 ArrayList<Character>EnglishLetters=new ArrayList<Character>(nodeList.getLength());
		    for (int count = 0; count < nodeList.getLength(); count++) {
		 
			Node tempNode = nodeList.item(count);
		 
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				int charcode=Integer.parseInt(tempNode.getTextContent());
				char character=(char)charcode;
				EnglishLetters.add(character);
		 
			}
		 
		    }
		    return EnglishLetters;
	 }


}
class LoaderClass
{
	LinqSimulationArrayList<PinglishString>mappingList;
}