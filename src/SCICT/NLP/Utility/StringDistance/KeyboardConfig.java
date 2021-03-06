package SCICT.NLP.Utility.StringDistance;

import SCICT.NLP.Utility.*;

/**
 Keyboard Layout Data Structure
*/
public class KeyboardConfig
{
	/**
	 In a keyboard layout with 3 rows, it indicates the first row.
	*/
	public String FirstRowCharacters;
	/**
	 In a keyboard layout with 3 rows, it indicates the second row.
	*/
	public String SecondRowCharacters;
	/**
	 In a keyboard layout with 3 rows, it indicates the third row.
	*/
	public String ThirdRowCharacters;

	/**
	 Keyboard Language
	*/
	public KeyboardLanguages KeyboardLanguage = KeyboardLanguages.values()[0];
	/**
	 Other charachters that does not fit in 3 rowed structure layout like charachters which must typed with Shift 
	*/
	public java.util.ArrayList<KeyboardKey> OtherCharacters;

	/** 
	 If the language has not been specified, Persian will use as default. 
	*/
	public KeyboardConfig()
	{
		LoadPersianDefault();
	}
	/**
	 Class Constructor
	
	@param language Keyboard Labguage
	*/
	public KeyboardConfig(KeyboardLanguages language)
	{
		this.KeyboardLanguage = language;

		if (language == KeyboardLanguages.Persian)
		{
			LoadPersianDefault();
		}

		if (language == KeyboardLanguages.English)
		{
			LoadEnglishDefault();
		}

	}

	/** 
	 this function loads Persian default keyboard layout into this config object.
	*/
	public final void LoadPersianDefault()
	{
		this.FirstRowCharacters = "ضصثقفغعهخحجچپ";
		this.SecondRowCharacters = "شسیبلاتنمکگ";
		this.ThirdRowCharacters = "ظطزرذدئو";

		this.OtherCharacters = new java.util.ArrayList<KeyboardKey>();

		//this.OtherCharacters.Add(new KeyboardKey(0f, 3f, 'پ'));         // <'> key ('P' in Farsi) - left top
		this.OtherCharacters.add(new KeyboardKey(4f, 0f, 'ژ', true)); // <c> key ('Zh' in Farsi) - left bottom
		this.OtherCharacters.add(new KeyboardKey(6f, 2f, 'آ', true)); // <h> key ('AA' in Farsi) - right top
		this.OtherCharacters.add(new KeyboardKey(8f, 0f, 'ء', true)); // <\> key ('hamze' in Farsi) - middle bottom
		this.OtherCharacters.add(new KeyboardKey(7f, 0f, 'أ', true)); // <\> key ('hamze' in Farsi) - middle bottom
		this.OtherCharacters.add(new KeyboardKey(6f, 0f, 'إ', true)); // <\> key ('hamze' in Farsi) - middle bottom
		this.OtherCharacters.add(new KeyboardKey(6f, 0f, 'ؤ', true)); // <\> key ('hamze' in Farsi) - middle bottom

	}

	/** 
	 this function loads English default keyboard layout into this config object.
	*/
	public final void LoadEnglishDefault()
	{
		this.FirstRowCharacters = "qwertyuiop";
		this.SecondRowCharacters = "asdfghjkl";
		this.ThirdRowCharacters = "zxcvbnm";
		this.OtherCharacters = new java.util.ArrayList<KeyboardKey>();
	}

}