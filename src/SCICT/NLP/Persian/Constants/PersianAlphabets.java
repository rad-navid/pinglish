package SCICT.NLP.Persian.Constants;

import SCICT.NLP.Persian.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region PersianAlphabet

/** 
 Holds constants related to the persian language
*/
public final class PersianAlphabets
{
	/** 
	 The standard tashdid character.
	*/
	public static final char Tashdid = '\u0651';

	/** 
	 Persian Alphabets
	*/
	public static String getAlphabets()
	{
		return getConsonantsInAllConditions() + getVowels() + PseudoPersianAlphabet;
	}

	/**
	 Persian Alphabets with Pseudo-space (ZWNJ)
	*/
	public static String getAlphabetWithPseudoSpace()
	{
		return getAlphabets() + PseudoSpace.ZWNJ;
	}

	//public static string AlphabetWithPseudoSpace = "آابپتثجچ‌حخدذرزژسشصضطظعغفقکگلمنوهیئأإؤ";

	/** 
	 Persian Delimiters
	*/
	public static final String Delimiters = ".,:;`/\"+-_(){}[]<>*&^%$#@!?؟~/|\\ =1234567890«»،٫٬\n\t٠١٢٣٤٥٦٧٨٩٪٭۰۱۲۳۴۵۶۷۸۹﴾﴿۔ـ؛¸·´­¨¬";

	/**
	 Numbers
	*/
	public static final String Numbers = "٠١٢٣٤٥٦٧٨٩";

	/**
	 Consonant letters
	*/
	public static String getConsonants()
	{
		return getConsonantsInAllConditions() + getConsonantsConditional();
	}

	/**
	 Consonant letters
	*/
	public static String getConsonantsInAllConditions()
	{
		return ConsonantsNonStickerInAllCondition + ConsonantsStickerInAllCondition;
	}

	/**
	 Consonant letters
	*/
	public static String getConsonantsConditional()
	{
		return ConsonantsNonStickerConditional + ConsonantsStickerConditional;
	}

	/**
	 Consonant letters
	*/
	public static String getConsonantsStickers()
	{
		return ConsonantsStickerInAllCondition + ConsonantsStickerConditional;
	}

	/**
	 Consonant letters
	*/
	public static String getConsonantsNonStickers()
	{
		return ConsonantsNonStickerInAllCondition + ConsonantsNonStickerConditional;
	}

	/**
	 Consonant sticker letters
	*/
	public static final String ConsonantsStickerInAllCondition = "بپتثجچحخسشصضطظعغفقکگلمن";

	/**
	 Conditional Consonant sticker letters
	*/
	public static final String ConsonantsStickerConditional = "ه";

	/**
	 Consonant non-sticker letters
	*/
	public static final String ConsonantsNonStickerInAllCondition = "دذرزژ";

	/**
	 Conditional Consonant non-sticker letters
	*/
	public static final String ConsonantsNonStickerConditional = "و";

	/**
	 Vowel letters
	*/
	public static String getVowels()
	{
		return getVowelsConditional() + getVowelsInAllCondition();
	}

	/**
	 Vowel letters
	*/
	public static String getVowelsInAllCondition()
	{
		return VowelsStickerInAllCondition + VowelsNonStickerInAllCondition;
	}

	/**
	 Vowel letters
	*/
	public static String getVowelsConditional()
	{
		return VowelsStickerConditional + VowelsNonStickerConditional;
	}

	/**
	 Vowel letters
	*/
	public static String getVowelsStickers()
	{
		return VowelsStickerInAllCondition + VowelsStickerConditional;
	}

	/**
	 Vowel letters
	*/
	public static String getVowelsNonStickers()
	{
		return VowelsNonStickerInAllCondition + VowelsNonStickerConditional;
	}

	/**
	 Vowel sticker letters
	*/
	public static final String VowelsStickerInAllCondition = "ی";

	/**
	 Vowel sticker letters
	*/
	public static final String VowelsStickerConditional = "هئ";

	/**
	 Vowel non-sticker letters
	*/
	public static final String VowelsNonStickerInAllCondition = "آا";

	/**
	 Vowel non-sticker letters
	*/
	public static final String VowelsNonStickerConditional = "وأإؤ";

	/** 
	 Persian Characters which are always seperate, and cannot stick to a next char
	*/
	public static final char[] NonStickerChars = new char[] {' ', '\t', 'ر', 'ز', 'و', 'ژ', 'ا', 'آ', 'د', 'ذ', 'ٱ', 'ؤ', 'إ', 'أ'};

	/**
	 Pseudo letters like Shaddah and Fathatan
	*/
	public static final String PseudoPersianAlphabet = "ًّ";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Erabs

	/** 
	 Erabs
	*/
	public static final String Erabs = new String(new char[] {(char)StandardCharacters.StandardFatha, (char)StandardCharacters.StandardFathatan, (char)StandardCharacters.StandardKasra, (char)StandardCharacters.StandardKasratan, (char)StandardCharacters.StandardSaaken, (char)StandardCharacters.StandardTashdid, (char)StandardCharacters.StandardZamma, (char)StandardCharacters.StandardZammatan});

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



	static
	{
		Load();
	}

	/** 
	 Loads this instance.
	*/
	public static void Load()
	{
		// Nothing to-do now
	}
}