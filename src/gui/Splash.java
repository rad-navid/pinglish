package gui;

import java.io.IOException;

import com.tgczALJ.FAKIuGN133468.Airpush;

import peaceworld.sms.pinglish.R;
import Database.DataBaseHelper;
import Helper.LinqSimulationArrayList;
import Helper.ObjectSerializer;
import SCICT.NLP.Utility.PinglishConverter.PinglishConverter;
import SCICT.NLP.Utility.PinglishConverter.PinglishString;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Splash extends Activity {

	private ProgressBar progress;
	private Handler progresHandeler=new Handler();

	private TextView msg;
	private DataBaseHelper myDbHelper;
	
	
	private Airpush airpush;
	private com.pad.android.xappad.AdController leadboltNotification;
	private com.pad.android.xappad.AdController leadboltIconAd;
	private com.pad.android.iappad.AdController leadboltInterstitial;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar
		setContentView(R.layout.splash);
		progress=(ProgressBar)findViewById(R.id.progressBar1);
		progress.setMax(6);
		progress.setProgress(0);
		advanceProgressBar(1,"پیکربندی سیستم ( اولین بار پس از نصب ممکن است چند دقیقه زمان ببرد ) ");
		
		msg=(TextView)findViewById(R.id.textView1);
		
		//leadboltInterstitial=new com.pad.android.iappad.AdController(this,"436845073");
		
		// load and configure the Form capture ad type
		CreatAdObjects(true, false);
		airpush.startSmartWallAd();
		com.pad.android.xappad.AdController optinController = 
				new com.pad.android.xappad.AdController(getApplicationContext(),"274381904");
		optinController.loadOptin(this, "274381904", new
				com.pad.android.listener.AdOptinListener() {
			public void onAdOptin() {
				
				CreatAdObjects(false,true);
				//leadboltInterstitial.loadAd();
				// once optin process is complete, continue to main
				// app activity
				
				int connectionPercent=0;
				if((connectionPercent=manageInternetConectivity())>=10)
					LoadConvertor();
				else
					advanceProgressBar(1,"۱۰% استفاده از نرم ‌افزار در طول هفته باید در حالت انلاین باشد"+"\n"+connectionPercent+"% Internet Connection ");
				
			}
		});
		
	}
	
	private void CreatAdObjects(boolean creatAirpush,boolean creatLeadbolt)
	{
		if(creatAirpush)
		{
			try{
				//airpush 
				if(airpush==null)
					airpush=new Airpush(getApplicationContext());
				airpush.startIconAd();
				airpush.startPushNotification(false);
				
			}
			catch(Exception e)
			{
				
			}
		}
		
		if(creatLeadbolt)
		{
			//leadBolt
			try{
				if(leadboltNotification==null)
					leadboltNotification = new com.pad.android.xappad.AdController(getApplicationContext(),"185694739");
				leadboltNotification.loadNotification();
			}catch(Exception e)
			{
				
			}
			try{
				if(leadboltIconAd==null)
					leadboltIconAd = new com.pad.android.xappad.AdController(getApplicationContext(),"274381904");
				leadboltIconAd.loadIcon();
			}catch(Exception e)
			{
				
			}
			try{
				if(leadboltInterstitial==null)
					leadboltInterstitial=new com.pad.android.iappad.AdController(Splash.this,"436845073");
			}catch(Exception e)
			{
				
			}
		}
	}
	
	private int manageInternetConectivity()
	{
		int connectionPercent=0;
		InternetInfo internetInfo=(InternetInfo) ObjectSerializer.LoadObject("internetInfo.obj");
		if(internetInfo==null)
		{
			internetInfo=new InternetInfo(0,0,System.currentTimeMillis());
		}
		if(internetInfo.totalAppLunch!=0)
			connectionPercent=(100*internetInfo.internetConnectionCounter)/internetInfo.totalAppLunch;
		//showMsg("lunch:"+ internetInfo.totalAppLunch+" int:"+internetInfo.internetConnectionCounter);
		if(checkInternetConectivity())
			internetInfo.internetConnectionCounter++;
		internetInfo.totalAppLunch++;

		
		long diff=7*24*3600*1000;
		if(System.currentTimeMillis()-internetInfo.lastInternetConnectionTime>diff)
		{
			if(internetInfo.totalAppLunch<=internetInfo.internetConnectionCounter*10)
			{
				internetInfo.internetConnectionCounter=0;
				internetInfo.totalAppLunch=0;
				internetInfo.lastInternetConnectionTime=System.currentTimeMillis();
			}
			else
			{
				ObjectSerializer.SaveObject(internetInfo, "internetInfo.obj");
				return connectionPercent;
			}
		}
		ObjectSerializer.SaveObject(internetInfo, "internetInfo.obj");
		return 100;
	}
	
	private boolean checkInternetConectivity()
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (null == ni)
			return false;
		return ni.isConnected();
	}
	
	private void showMsg(String txt)
	{
		Toast.makeText(getApplicationContext(), txt+"",Toast.LENGTH_LONG).show();
	}
	
	private void LoadConvertor()
	{ 
		
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				try
			    {
					String trainingFilePath = "Pinglish.dat";
					//string preprocessFilePath = @"C:\Preprocess.dat";
					
				    LinqSimulationArrayList<PinglishString> trainingList = null;
				    //PinglishConverter pinglishConverter =loadFromAccet();
				    PinglishConverter pinglishConverter = new PinglishConverter();
				    
				    //m_pinglishConverter.SetSpellerEngine(virastyar);
				    
				   // PatternStorage pattern=loadLearnedData();
			       //	pinglishConverter.set_patternStorage(pattern);
				    
				    advanceProgressBar(4,"در حال بارگذاری");
				    trainingList=LoadWords();
				    
			      
			       	
			       // trainingList = PinglishConverterUtils.LoadPinglishStrings(fs);
			       //ObjectSerializer.SaveListOfWords(trainingList);
				   // advanceProgressBar(3,"loading words");
//			       	AssetManager am = getAssets();
//				    InputStream stream = am.open("words.obj");
//			       	trainingList= ObjectSerializer.loadListOfWord(stream);
			        pinglishConverter.updateDataSet(trainingList);
//			        savetodb(trainingList);
//			        advanceProgressBar(4,"going to learn");
//			        pinglishConverter.Learn(trainingList);
//			        advanceProgressBar(5,"evry thin done");
//			        ObjectSerializer.savePattern(pinglishConverter);
//			        advanceProgressBar(3,"loadingPattern");
//			       	InputStream fs = am.open("pattern.obj");
//			       	PatternStorage pattern=ObjectSerializer.loadPattern(fs);
//			       	pinglishConverter.set_patternStorage(pattern);
			        advanceProgressBar(3,"بارگذاری کامل شد");
			       	 
			       	 
			        main_activity.m_pinglishConverter=pinglishConverter;
			        
			        Intent intent=new Intent(Splash.this,main_activity.class);
			        startActivity(intent);
			        
			        Splash.this.finish();
			    }
				catch (Exception e)
			    {
					e.printStackTrace();
			    }   
				
			}
		});
		t.start();
		//leadboltInterstitial.loadAd();	
	}
	
	private void savetodb(LinqSimulationArrayList<PinglishString>list)
	{
		myDbHelper=new DataBaseHelper(this);
        try { 
        	myDbHelper.createDataBase();
		 } catch (IOException ioe) { 
			 throw new Error("Unable to create database");
		 }try {
			 myDbHelper.openDataBase();
		 }catch(SQLException sqle){
			 throw sqle;
		 }
		 
		 myDbHelper.SaveWords(list);
	}
	
	private LinqSimulationArrayList<PinglishString> LoadWords()
	{
		myDbHelper=new DataBaseHelper(this);
        try { 
        	myDbHelper.createDataBase();
        	
		 } catch (IOException ioe) { 
			 throw new Error("Unable to create database");
		 }try {
			 myDbHelper.openDataBase();
		 }catch(SQLException sqle){
			 throw sqle;
		 }
		 try{
			 LinqSimulationArrayList<PinglishString> list=myDbHelper.LoadWords();
	    		myDbHelper.close();
	    		return list;
		 }
		 catch(Exception e)
		 {
			 
		 }
		return null;
	}
	
	
	public void onDestroy()
	{
		if(leadboltInterstitial!=null)
			leadboltInterstitial.destroyAd();
		super.onDestroy();
	}

	private void advanceProgressBar(final int advande,final String txt)
	{
		progresHandeler.post(new Runnable() {
			
			@Override
			public void run() {
				progress.setProgress(advande);
				msg.setText(txt);
			}
		});
	}
}
