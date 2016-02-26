package SCICT.NLP.Persian.Constants;

import SCICT.NLP.Persian.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Special Characters

/** 
 Character codes which are used by Microsoft Word for special purposes
*/
public final class WordSpecialCharacters
{
	/** 
	 Code for the character used to delimit formulas in Word 2003.
	*/
	public static final int FormulaDelimiterCode = 1;

	/** 
	 Code for the character used to delimit footnotes.
	*/
	public static final int FootnoteDelimiterCode = 2;

	/** 
	 Character used to delimit formulas in Word 2003
	*/
	public static final char FormulaDelimiter = (char)FormulaDelimiterCode;

	/** 
	 Replacement String in Persian for the formula special character
	*/
	public static final String FormulaDelimiterReplacementString = "(فرمول)";

	/** 
	 Replacement RTF-String in Persian for the formula special character
	*/
	public static final String FormulaDelimiterReplacementRTF = "\\lang1065\\f1\\rtlch(\\'dd\\'d1\\'e3\\'e6\\'e1)";

	/** 
	 Character used to delimit footnotes
	*/
	public static final char FootnoteDelimiter = (char)FootnoteDelimiterCode;

	/** 
	 Replacement String in Persian for the footnote special character
	*/
	public static final String FootnoteDelimiterReplacementString = "(پاورقی)";

	/** 
	 Replacement RTF-String in Persian for the footnote special character
	*/
	public static final String FootnoteDelimiterReplacementRTF = "\\lang1065\\f1\\rtlch(\\'81\\'c7\\'e6\\'d1\\'de\\u1740?)";

	/** 
	 An array of special characters used by Microsoft Word for special purposes.
	*/
	public static final char[] SpecialCharsArray = new char[] {FormulaDelimiter, FootnoteDelimiter};
}