package Database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Helper.LinqSimulationArrayList;
import SCICT.NLP.Utility.PinglishConverter.PinglishString;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

    public class DataBaseHelper extends SQLiteOpenHelper{
     
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/peaceworld.sms.pinglish/databases/";
     
    private static String DB_NAME = "words.db";
    
    private SQLiteDatabase myDataBase;
     
    private Context myContext;
    private String cmd="select * from Words"; 
    
    /**
      * Constructor
      * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
      * @param context
      */
    public DataBaseHelper(Context context) {
     
    super(context, DB_NAME, null, 1);
    this.myContext = context;
    }	
     
    /**
      * Creates a empty database on the system and rewrites it with your own database.
      * */
    public void createDataBase() throws IOException{
     
    boolean dbExist = checkDataBase();
    if(dbExist){
    //do nothing - database already exist
    }else{
     
    //By calling this method and empty database will be created into the default system path
    //of your application so we are gonna be able to overwrite that database with our database.
    this.getReadableDatabase();
     
    try {
    copyDataBase();
    
    } catch (IOException e) {
     
    throw new Error("Error copying database");
     
    }
    }
     
    }
     
    /**
      * Check if the database already exist to avoid re-copying the file each time you open the application.
      * @return true if it exists, false if it doesn't
      */
    private boolean checkDataBase(){
     
    SQLiteDatabase checkDB = null;
     
    try{
    String myPath = DB_PATH + DB_NAME;
    checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
     
    }catch(SQLiteException e){
     
    //database does't exist yet.
     
    }
     
    if(checkDB != null){
     
    checkDB.close();
     
    }
     
    return checkDB != null ? true : false;
    }
     
    /**
      * Copies your database from your local assets-folder to the just created empty database in the
      * system folder, from where it can be accessed and handled.
      * This is done by transfering bytestream.
      * */
    private void copyDataBase() throws IOException{
     
    //Open your local db as the input stream
    InputStream myInput = myContext.getAssets().open(DB_NAME);
     
    // Path to the just created empty db
    String outFileName = DB_PATH + DB_NAME;
     
    //Open the empty db as the output stream
    OutputStream myOutput = new FileOutputStream(outFileName);
     
    //transfer bytes from the inputfile to the outputfile
    byte[] buffer = new byte[1024];
    int length;
    while ((length = myInput.read(buffer))>0){
    myOutput.write(buffer, 0, length);
    }
     
    //Close the streams
    myOutput.flush();
    myOutput.close();
    myInput.close();
     
    }
     
    public void openDataBase() throws SQLException{
     
    //Open the database
    String myPath = DB_PATH + DB_NAME;
    myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    
    
    public LinqSimulationArrayList<PinglishString> LoadWords()
    {
    	LinqSimulationArrayList<PinglishString> List=new  LinqSimulationArrayList<PinglishString>(4700);
    	String wordSearchQueryString="SELECT * FROM words";
    	try{
    		
    		Cursor wordsCursor= myDataBase.rawQuery(wordSearchQueryString, null);
    	
    		for (boolean hasItem = wordsCursor.moveToFirst(); hasItem; hasItem = wordsCursor.moveToNext())
    		{
    			
	    		String englishString=wordsCursor.getString(2);
	    		String persianString=wordsCursor.getString(1);
	    		PinglishString pinglishString=new PinglishString(englishString);
	    		char[] chararray=persianString.toCharArray();
	    		ArrayList<String> persianCharArray=new ArrayList<String>(chararray.length);
	    		for (char ch : chararray)
	    		{
	    			persianCharArray.add(ch+"");
	    		}
	    		pinglishString.setPersianLetters(persianCharArray);
	    		List.add(pinglishString);
	    		
    		}
    		return List;
    	}catch(Exception e)
    	{
    		//Log.d("Navid", e.toString());
    		return null;
    	}
    }
    
    public boolean SaveWords(LinqSimulationArrayList<PinglishString> List)
    {
    	try{
    		for (PinglishString pinglishString : List) {
    			
//    			String cmd="INSERT INTO words ( PersianString , EnglishString ) values ( \'"+
//    			pinglishString.getPersianString()+"\' , \'"+ pinglishString.getEnglishString()+"\' ) ";
    			ContentValues initialValues = new ContentValues();
    		    initialValues.put("PersianString", pinglishString.getPersianString());
    		    initialValues.put("EnglishString", pinglishString.getEnglishString());
    		    myDataBase.insert("words", null, initialValues);		
    		}
    		return true;
    	}catch(Exception e)
    	{
    		Log.d("Navid", e.toString());
    		return false;
    	}
    }
     
    @Override
    public synchronized void close() {
     
    if(myDataBase != null)
    myDataBase.close();
     
    super.close();
     
    }
     
    @Override
    public void onCreate(SQLiteDatabase db) {
     
    }
     
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     
    }

	public void setContext(Context contex) {
		this.myContext=contex;
		
	}
     
    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.
     
    }