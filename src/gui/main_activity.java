package gui;

import gui.PinglishTextBox.onKeyPressed;
import gui.PinglishTextBox.onSelectionChangedListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import peaceworld.sms.pinglish.R;
import Helper.ObjectSerializer;
import SCICT.NLP.Utility.PinglishConverter.PatternStorage;
import SCICT.NLP.Utility.PinglishConverter.PinglishConverter;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devsmart.android.ui.HorizontalListView;
import com.tgczALJ.FAKIuGN133468.Airpush;

public class main_activity extends Activity {
	
	private final int HamraheAvalEtebari_Farsi=106;
	private final int HamraheAvalDaemi_Farsi=89;
	private final int HamraheAvalEtebari_English=264;
	private final int HamraheAvalDaemi_English=222;
	private final int Irancell_Farsi=100;
	private final int Irancell_English=160;
	private final int Writtel_Farsi=105;
	private final int Writtel_English=195;
	private int simName;//0 hamraheAval , 1 irancell, 2 writtel
	private String OperatorName;
	
	private int Pcost=0;
	private int Ecost=0;
	
	private boolean reducedCostEnabled=true;
	private boolean  simEtebare=false;
	private boolean completeLoad=false;

	private PinglishTextBox pinglishText;
	private PinglishTextBox persianText;
	private TextView msgbox;
	private TextView costView;
	private ProgressBar progress;
	private CheckBox reducedCost,simType;
	
	private HorizontalListView listOfSuggestions;
	
	private HashMap<String, Word> map;
	private LinkedList<String> queue;
	private ArrayAdapter<String> SuggestionsArrayAdapter ;
	static PinglishConverter m_pinglishConverter;
	
	private Handler handeler;
	private Thread conversionThread;
	private Thread cursorThread;;
	

	private Airpush airpush;
	private com.pad.android.xappad.AdController leadboltNotification;
	private com.pad.android.xappad.AdController leadboltIconAd;
	private com.pad.android.iappad.AdController leadboltInterstitial;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
		setContentView(R.layout.main_layout);
		
		//436845073
		//init pinglish reuired
		map=new HashMap<String, Word>();
		queue=new LinkedList<String>();
		pinglishText=(PinglishTextBox)findViewById(R.id.PinglishText);
		pinglishText.addTextChangedListener(textWatcher);
		pinglishText.addOnSelectionChangedListener(CursorListener);
		//pinglishText.setOnKeyPressedListener(keyListener);

		persianText=(PinglishTextBox)findViewById(R.id.PersianText);
		listOfSuggestions=(HorizontalListView)findViewById(R.id.listOfSuggestions);
		listOfSuggestions.setOnItemClickListener(itemClickListener);
		SuggestionsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		Button sendBtn=(Button)findViewById(R.id.sendText);
		sendBtn.setOnClickListener(sendText);
		Button copyBtn=(Button)findViewById(R.id.copyText);
		copyBtn.setOnClickListener(copyText);
		msgbox=(TextView)findViewById(R.id.msgbox);
		costView=(TextView)findViewById(R.id.costView);
		reducedCost=(CheckBox)findViewById(R.id.reducedCost);
		reducedCost.setOnCheckedChangeListener(checkedChanged);
		simType=(CheckBox)findViewById(R.id.simType);
		simType.setOnCheckedChangeListener(checkedChanged);
		progress=(ProgressBar)findViewById(R.id.progressBar_loading);
		
		completeLoad=false;
		handeler=new Handler();
		
		getOperatorName();
		costView.setText(getCost(persianText.getText(), pinglishText.getText()));	
		
		loadLearnedData();
		
	}
	
	
	private void CreatAdObjects()
	{
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {

				handeler.post(new Runnable() {
					@Override
					public void run() {			
						try{
							//airpush 
							if(airpush==null)
								airpush=new Airpush(getApplicationContext());
							airpush.startIconAd();
							airpush.startPushNotification(false);
							}catch(Exception e)
							{	}
						
					
						}
					});
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//leadBolt
				try{
					if(leadboltNotification==null)
						leadboltNotification = new com.pad.android.xappad.AdController(getApplicationContext(),"185694739");
					leadboltNotification.loadNotification();
				}catch(Exception e)
				{  }
				try{
					if(leadboltIconAd==null)
						leadboltIconAd = new com.pad.android.xappad.AdController(getApplicationContext(),"274381904");
					leadboltIconAd.loadIcon();
				}catch(Exception e)
				{  }
				try{
					if(leadboltInterstitial==null)
						leadboltInterstitial=new com.pad.android.iappad.AdController(main_activity.this,"436845073");
					leadboltInterstitial.loadAd();
				}catch(Exception e)
				{  }
				try{
				if(airpush!=null)
					airpush.startSmartWallAd();
				}catch(Exception e)
				{  }

				
			}
		});
		t.start();
	}
	
	private void loadLearnedData()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					AssetManager am = getAssets();
				    InputStream stream = am.open("pattern.obj");
					PatternStorage pattern=ObjectSerializer.loadPattern(stream);
					m_pinglishConverter.set_patternStorage(pattern);
					completeLoad=true;
					handeler.post(new Runnable() {
						
						@Override
						public void run() {
							progress.setVisibility(View.GONE);
							msgbox.setGravity(android.view.Gravity.CENTER);
							//CreatAdObjects();
							msgbox.setText("در حال اصلاح متن فارسی");
							
						}
					});
					
					

					String fullText=pinglishText.getText().toString();
						String[]words=fullText.split("\\s+");
						for(String s : words)
						{
							//Log.d("repair", s);
							Word wordMap=map.get(s);
							if(wordMap!=null && !wordMap.afterCompleteLoad && wordMap.suggestions.length==0)
							{
								String[] suggestions=getSuggestions(s);
								wordMap.afterCompleteLoad=completeLoad;
								wordMap.suggestions=suggestions;
								
								if(suggestions.length>0)
									wordMap.SelectedWord=suggestions[0];
								else
									wordMap.SelectedWord=s;
								wordMap.haveSelectedMap=true;
								map.put(s, wordMap);
							}
							updatePersianText(GetPersianText());	
						}
						
						
					
				
				
					
					
				} catch (OutOfMemoryError e) {
					
					Log.d("memo", e.toString());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d("io", e.toString());
				} 
				finally
				{
					handeler.post(new Runnable() {
						
						@Override
						public void run() {
							LinearLayout loadingBar=(LinearLayout)main_activity.this.findViewById(R.id.loadingBar);
							loadingBar.setVisibility(View.GONE);
						}
					});				
				}
			}
		}).start();
		
		
	}


	private String getCost(Editable persianFullText,Editable EnglishFullText)
	{
		int P_msgCount=(persianFullText.length()/70)+1;
		int P_msgLetter=70-(persianFullText.length()%70);
		int E_msgCount=(EnglishFullText.length()/160)+1;
		int E_msgLetter=160-(EnglishFullText.length()%160);
		
	switch(simName)
	{
	case 0: if(simEtebare)
			{
				Pcost=P_msgCount*HamraheAvalEtebari_Farsi;
				Ecost=E_msgCount*HamraheAvalEtebari_English;
			}
			else{
				Pcost=P_msgCount*HamraheAvalDaemi_Farsi;
				Ecost=E_msgCount*HamraheAvalDaemi_English;
			}
			break;
	case 1:
		Pcost=P_msgCount*Irancell_Farsi;
		Ecost=E_msgCount*Irancell_English;
		break;
	case 2:
		Pcost=P_msgCount*Writtel_Farsi;
		Ecost=E_msgCount*Writtel_English;
		break;
	}
		
	String operator="اپراتور :"	+OperatorName;
	String Ptxt=" فارسی( "+P_msgLetter+"/"+P_msgCount+" ) : "+Pcost+" ریال ";
	String Etxt="فینگلیش ( "+E_msgLetter+"/"+E_msgCount+" ) : "+Ecost +" ریال ";
		
		
		return operator+"\n"+ Ptxt+"\n"+Etxt;
	}
	
	private String GetPersianText()
	{
		String fullText=pinglishText.getText().toString();
		String lines[]=fullText.split("[\\r\\n]");
		String persianText="";
		for (String line : lines) {
			String[]words=line.split("\\s+");
			for(String s : words)
			{
				Word persianWordOnject=map.get(s);
				
				if(persianWordOnject==null)
					continue;
				if(persianWordOnject.haveSelectedMap)
					persianText+=" "+persianWordOnject.SelectedWord;
				else 
					persianText+=" "+s;
			}
			if(!line.equalsIgnoreCase("")&& line!=null)
				persianText+="\n";
		}
		return persianText;
	}
	
	private String[] getSuggestions(String word)
	{
//		SpellCheckerConfig virConfig = new SpellCheckerConfig();
//		virConfig.DicPath = "./dic.dat";
//		virConfig.EditDistance = 2;
//		virConfig.StemPath = "./stem.dat";
//		virConfig.SuggestionCount = 7;
//
//		PersianSpellChecker virastyar = new PersianSpellChecker(virConfig);
		
		
       
        //m_pinglishConverter.LoadPreprocessElements(preprocessFilePath);

       // System.out.println(m_pinglishConverter.SuggestFarsiWord(word, false));
		//String[]tmparr={"xaxax","csCS","ascc","efwr","brgrny","ssdvw"};
		try{
	        String[] suggestions = m_pinglishConverter.SuggestFarsiWords(word, false);
	        return suggestions;
		}catch(Exception e)
		{
			String[] tmp={""};
			return tmp;
		}
	}
	
	private void updatePersianText(final String text)
	{
		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
			 persianText.setText(text);
			 persianText.setSelection(persianText.getText().length());
			 costView.setText(getCost(persianText.getText(), pinglishText.getText()));
			}
		});
		
	}
	
	private void inflateSuggestionToMenu(final String word,final String[]sugestions)
	{
		main_activity.this.runOnUiThread(new Runnable() {
			public void run() {
				
				SuggestionsArrayAdapter.clear();
				SuggestionsArrayAdapter.add(word);
				for(String sug:sugestions)
					SuggestionsArrayAdapter.add(sug);
				listOfSuggestions.setAdapter(SuggestionsArrayAdapter);
				
				SuggestionsArrayAdapter.notifyDataSetChanged();
			}
		});
		
	}
	
	private String ExtractWord(String fullText,int index)
	{
		String ExtractedWord="";
		//Extract Left Side
		int leftIndex=index;
		char tmp;
		while(leftIndex>=0 && !Character.isWhitespace(tmp=fullText.charAt(leftIndex--)))
			ExtractedWord=tmp+ExtractedWord;
		int rightIndex=index+1;
		while(rightIndex<fullText.length() && !Character.isWhitespace(tmp=fullText.charAt(rightIndex++)))
			ExtractedWord+=tmp;
		return ExtractedWord;
	}
	private String ExtractPassedWord(String fullText,int index)
	{
		String ExtractedWord="";
		//Extract Left Side
		int leftIndex=index;
		while(leftIndex>=0 && Character.isWhitespace(fullText.charAt(leftIndex)))
			leftIndex--;
		char tmp;
		while(leftIndex>=0 && !Character.isWhitespace(tmp=fullText.charAt(leftIndex--)))
			ExtractedWord=tmp+ExtractedWord;
		return ExtractedWord;
	}
	
	private void whitespaceTyped(final int index)
	{
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				
				try{
					String word=ExtractPassedWord(pinglishText.getText().toString(), index);	
					if(map.get(word)==null)
						queue.add(word);
					if(queue.size()>0 && (conversionThread==null ||!conversionThread.isAlive()))
						StartCOnversion();
				}
				catch(Exception e)
				{
					
				}
			}
		});
		thread.start();
		
	}
	
	private void newWordTyped(final String keyWord)
	{
		String[] suggestions=getSuggestions(keyWord);
		Word wordObj=map.get(keyWord);
		if(wordObj==null)
			wordObj = new Word();
		wordObj.afterCompleteLoad=completeLoad;
		wordObj.suggestions=suggestions;
		wordObj.word=keyWord;
		if(suggestions.length>0)
			wordObj.SelectedWord=suggestions[0];
		else
			wordObj.SelectedWord=keyWord;
		wordObj.haveSelectedMap=true;
		map.put(keyWord, wordObj);
		inflateSuggestionToMenu(keyWord,suggestions);
		updatePersianText(GetPersianText());
	}
	
	public void onDestroy()
	{
		if(leadboltInterstitial!=null)
			leadboltInterstitial.destroyAd();
		if(conversionThread!=null)
			conversionThread.interrupt();
		conversionThread=null;
		super.onDestroy();
	}
	
	private void getOperatorName(){
		TelephonyManager mTeleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		 mTeleManager.getSimOperatorName();
		 mTeleManager.getNetworkOperatorName();
		 mTeleManager.getSimOperator();
		OperatorName= mTeleManager.getSimOperatorName();
		if(OperatorName.contains("TCI")||OperatorName.contains("tci"))
			simName=0;
		else if(OperatorName.contains("mtn")||OperatorName.contains("MTN"))
			simName=1;
		else if(OperatorName.contains("rightel")||OperatorName.contains("RighTel"))
			simName=2;
		else
			simName=0;
	}
	
	  public boolean onCreateOptionsMenu(Menu menu)
	    {
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.layout.menu, menu);
	        return true;
	    }
	 
	    /**
	     * Event Handling for Individual menu item selected
	     * Identify single menu item by it's id
	     * */
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	 
	        switch (item.getItemId())
	        {
	        case R.id.menu_exit:
	            // Single menu item is selected do something
	            // Ex: launching new activity/screen or show alert message
	        	finish();
	            return true;
	        case R.id.menu_about:
	            // Single menu item is selected do something
	            // Ex: launching new activity/screen or show alert message
	           startActivity(new Intent(main_activity.this, About.class));
	            return true;
	        case R.id.menu_help:
	            // Single menu item is selected do something
	            // Ex: launching new activity/screen or show alert message
	           startActivity(new Intent(main_activity.this, Help.class));
	            return true;
	     
	 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }    
	 
	    private void StartCOnversion()
	    {
	    	conversionThread=new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					while(queue.size()>0)
					{
						String word=queue.removeFirst();
						//Log.d("thread",Thread.currentThread().getId()+" size:"+queue.size()+" "+word);
						newWordTyped(word);
					}
				}
			});
	    	conversionThread.start();
	    }
	
	
	onSelectionChangedListener CursorListener=new onSelectionChangedListener() {
		
		@Override
		public void onSelectionChanged(int selStart, int selEnd) {
			int cursorPosition=pinglishText.getSelectionStart()-1;
			if(cursorPosition<pinglishText.getText().length())
			{
				CursorChangedHandeler(cursorPosition);
			}
			
			
		}
	};
	
	private void CursorChangedHandeler(final int index)
	{
		if(cursorThread!=null && cursorThread.isAlive())
		{
			cursorThread.interrupt();
			cursorThread=null;
		}
		cursorThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				String word=ExtractWord(pinglishText.getText().toString(),index);
				Word wordObj=map.get(word);
				if(wordObj!=null)
				{
					inflateSuggestionToMenu(word,wordObj.suggestions);
				}
			}
		});
		cursorThread.start();
	}
	
	
	onKeyPressed keyListener=new onKeyPressed() {
		
		@Override
		public void keyPressed(int keyCode, KeyEvent event) {
			//Log.d("key",event.toString());
			
			if(keyCode==KeyEvent.KEYCODE_DEL)
			{
				int index=pinglishText.getSelectionStart()-1;
				if(index>=0)
				{
					String word=ExtractPassedWord(pinglishText.getText().toString(), index);
					Word wordMap=map.get(word);
					if(wordMap!=null)
					{
						map.remove(word);
						//Log.d("map", word);
					}
					else
					{
						updatePersianText(GetPersianText());
					}
				}
			
			}
		}
	};
	
	TextWatcher textWatcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			
			
		}
		
		@Override
		public void afterTextChanged(Editable arg0) {
			
			int index=pinglishText.getSelectionStart()-1;
			if(index>=0)
			{
				char newTypedCharacter=pinglishText.getText().charAt(index);
				if(Character.isWhitespace(newTypedCharacter))
				{
					whitespaceTyped(index);
				}
			}
			
		
		}
	};
	
	OnItemClickListener itemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			TextView item=(TextView)arg1;
			int cursorPosition=pinglishText.getSelectionStart()-1;
			String word="";
			if(cursorPosition>=0 && Character.isWhitespace(pinglishText.getText().charAt(cursorPosition)))
				word=ExtractPassedWord(pinglishText.getText().toString(),cursorPosition);
			else
				word=ExtractWord(pinglishText.getText().toString(),cursorPosition);
			
			Word persianWordObject=map.get(word);
			if(persianWordObject!=null)
			{
				persianWordObject.haveSelectedMap=true;
				persianWordObject.SelectedWord=item.getText().toString().trim();
				updatePersianText(GetPersianText());
			}
			
		}
	};
	
	
	
	OnClickListener sendText=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {	
			String smsBody=persianText.getText().toString();
			if(reducedCostEnabled)
				if(Pcost>Ecost)
					smsBody=pinglishText.getText().toString();
			
			
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setType("vnd.android-dir/mms-sms");
			smsIntent.putExtra("sms_body",smsBody);
			//smsIntent.putExtra("address", "Edit number");
			
			startActivity(smsIntent);

			//SmsManager.getDefault().sendTextMessage("029186352512", null, persianText.getText().toString(), null, null);
			CreatAdObjects();
		}
	};
	
	OnCheckedChangeListener checkedChanged=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			
				if(arg0==reducedCost)
					reducedCostEnabled=arg1;
				if(arg0==simType)
					simEtebare=arg1;
				handeler.post(updatePrice);
				
		}
	};
	
	Runnable updatePrice= new Runnable() {
		
		@Override
		public void run() {
			costView.setText(getCost(persianText.getText(), pinglishText.getText()));	
		}
	};
	
	OnClickListener copyText=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			 
			try{
				int sdk = android.os.Build.VERSION.SDK_INT;
				if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
				    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				    clipboard.setText("text to clip");
				} else {
				    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE); 
				    android.content.ClipData clip = android.content.ClipData.newPlainText("text label","text to clip");
				    clipboard.setPrimaryClip(clip);
				}
			}
			catch (Exception e) {
				
			}
			
			CreatAdObjects();
		}
	};
	
}


class Word
{
	boolean haveSelectedMap=false;
	boolean afterCompleteLoad=false;
	String SelectedWord=null; 
	String word;
	String[]suggestions;
}
class InternetInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8619274114849173995L;
	InternetInfo(int arg0,int arg1,long l)
	{
		totalAppLunch=arg0;
		internetConnectionCounter=arg1;
		lastInternetConnectionTime=l;
	}
	int totalAppLunch;
	int internetConnectionCounter;
	long lastInternetConnectionTime;
}
