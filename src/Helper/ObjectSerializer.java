package Helper;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import SCICT.NLP.Utility.PinglishConverter.PatternStorage;
import SCICT.NLP.Utility.PinglishConverter.PinglishConverter;
import SCICT.NLP.Utility.PinglishConverter.PinglishString;
import android.content.res.AssetManager;
import android.os.Environment;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ObjectSerializer {
	
	
	public static void serializeObject(Object o,OutputStream outputStream) { 
		try { 
			ObjectOutput out = new ObjectOutputStream(outputStream); 
		    out.writeObject(o); 
		    out.close(); 
		 
		    outputStream.flush();
		    outputStream.close();
		    } catch(IOException ioe) { 
		     // Log.e("serializeObject", "error", ioe); 
		    } 
		} 


	public static Object deserializeObject(InputStream inputStream) { 
		try { 
			ObjectInputStream in = new ObjectInputStream(inputStream); 
		    Object object = in.readObject(); 
		    in.close();  
		    return object; 
		    } catch(ClassNotFoundException cnfe) { 
		     // Log.e("deserializeObject", "class not found error", cnfe); 
		    	return null; 
		    } catch(IOException ioe) { 
		    	//  Log.e("deserializeObject", "io error", ioe); 
		    	return null; 
		    } 
		} 
	
	public static void SavePattern_Externalizable(Externalizable object)
	{
		try{
			FileOutputStream stream=(FileOutputStream)getOutpuStream("pattern.obj");
			ObjectOutputStream ostream= new ObjectOutputStream(stream);
			object.writeExternal(ostream);	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	}
		
	
	public static void savePattern(PinglishConverter pinglishCOnvertor)
	{
		
		try {
			FileOutputStream stream=(FileOutputStream)getOutpuStream("pattern.obj");
			ObjectOutputStream ostream= new ObjectOutputStream(stream);
			ostream.writeObject(pinglishCOnvertor.get_patternStorage());
			ostream.close();
				
		}catch(Exception ex){
			System.out.println(ex);
			}
	}
		
	
	public static void SaveListOfWords(LinqSimulationArrayList<PinglishString>words)
	{
		try {
			FileOutputStream stream=(FileOutputStream)getOutpuStream("words.obj");
			ObjectOutputStream ostream= new ObjectOutputStream(stream);
			ostream.writeObject(words);
			ostream.close();
				
		}catch(Exception ex){
			System.out.println(ex);
			}	
	}
	
	
	public static void saveLearnedDatatoXmL(PinglishConverter pinglishCOnvertor)
	{
		//Create Folder
		File folder = new File(Environment.getExternalStorageDirectory().toString()+"/Pinglish/Data/");
		if(!folder.exists())
			folder.mkdirs();
		
		  //Save the path as a string value
		String extStorageDirectory = folder.toString();
		
		  //Create New file and name it Image2.PNG
		File file = new File(extStorageDirectory+"/learnedobject.xml");
		try {
			FileOutputStream stream=new FileOutputStream(file);
			//ObjectOutputStream ostream= new ObjectOutputStream(stream);
			//ostream.writeObject(pinglishCOnvertor.get_patternStorage());
			//ostream.close();
				
			XStream xstream = new XStream(new DomDriver()); // does not require XPP3 library
			xstream.toXML(pinglishCOnvertor.get_patternStorage(),stream);
						
		}catch(Exception ex){
			System.out.println(ex);
			}
	}
	
	
	public static PatternStorage loadPattern(InputStream assetsStream)
	{
		try{
			return (PatternStorage)loadSearilizedObjectFromAsset(assetsStream);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public static LinqSimulationArrayList<PinglishString> loadListOfWord(InputStream assetsStream)
	{
		try{
			return (LinqSimulationArrayList<PinglishString>)loadSearilizedObjectFromAsset(assetsStream);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static Object loadSearilizedObjectFromAsset(InputStream assetsStream)
	{
		try {
//			AssetManager am = getAssets();
//		    InputStream stream = am.open("object.obj");
			ObjectInputStream ostream= new ObjectInputStream(assetsStream);
			Object obj= ostream.readObject();				
			ostream.close();
			return obj;
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			return null;
	}
	
	public static PinglishConverter loadFromAssets_Externalizable(InputStream assetsStream)
	{
		try {
//			AssetManager am = getAssets();
//		    InputStream stream = am.open("object.obj");
			ObjectInputStream ostream= new ObjectInputStream(assetsStream);
			PinglishConverter pinglishconverter=new PinglishConverter();	
			pinglishconverter.readExternal(ostream);
			return pinglishconverter;
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		public static PinglishConverter loadPattern(String Filename)
		{
			try{
				FileInputStream stream=(FileInputStream)getInputStream(Filename);
				ObjectInputStream ostream= new ObjectInputStream(stream);
				PinglishConverter pinglishconverter=new PinglishConverter();
				pinglishconverter.readExternal(ostream);
				return pinglishconverter;
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		public static Object LoadObject(String fileName)
		{
			try{
				FileInputStream stream=(FileInputStream)getInputStream(fileName);
				ObjectInputStream ostream= new ObjectInputStream(stream);
				Object obj=ostream.readObject();
				return obj;
				
			} 
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		public static void SaveObject(Object object,String fileName)
		{
			try{
				FileOutputStream stream=(FileOutputStream)getOutpuStream(fileName);
				ObjectOutputStream ostream= new ObjectOutputStream(stream);
				ostream.writeObject(object);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		public static OutputStream getOutpuStream(String fileName)
		{
			//Create Folder
			  File folder = new File(Environment.getExternalStorageDirectory().toString()+"/Pinglish/Data/");
			  if(!folder.exists())
				  folder.mkdirs();

			  //Save the path as a string value
			  String extStorageDirectory = folder.toString();

			  //Create New file and name it Image2.PNG
			  File file =  new File(extStorageDirectory+"/"+fileName);
//			  if(type==0)
//				  file = new File(extStorageDirectory+"/pattern.obj");
//			  if(type==1)
//				  file = new File(extStorageDirectory+"/words.obj");
			  try {
				FileOutputStream stream=new FileOutputStream(file);
				return stream;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			  
			
		}
		public static InputStream getInputStream(String fileName)
		{
			String path="/Pinglish/Data/"+fileName;
			File file =  new File(Environment.getExternalStorageDirectory().toString()+path);;
			 
//		  file = new File(Environment.getExternalStorageDirectory().toString()+"/Pinglish/Data/pattern.obj");
//		  file = new File(Environment.getExternalStorageDirectory().toString()+"/Pinglish/Data/words.obj");
				try {
					FileInputStream stream=new FileInputStream(file);
					return stream;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				}
		}

}
