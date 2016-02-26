package SCICT.NLP.Utility.Parsers;

import SCICT.NLP.Utility.*;

/** 
 Holds information about the outputs from <see cref="ReversePatternMatcher"/>.
*/
public class ReversePatternMatcherPatternInfo
{
	/** 
	 Gets or sets the body of the word.
	 
	 <value>The body of the word.</value>
	*/
	private String privateBaseWord;
	public final String getBaseWord()
	{
		return privateBaseWord;
	}
	private void setBaseWord(String value)
	{
		privateBaseWord = value;
	}

	/** 
	 Gets or sets the suffix part of the word.
	 
	 <value>The Suffix.</value>
	*/
	private String privateSuffix;
	public final String getSuffix()
	{
		return privateSuffix;
	}
	private void setSuffix(String value)
	{
		privateSuffix = value;
	}

	/** 
	 Initializes a new instance of the <see cref="ReversePatternMatcherPatternInfo"/> class.
	 It also applies some (hard-coded) word construction rules to the words.
	 
	 @param baseWord The stem of the word.
	 @param suffix The affix.
	*/
	public ReversePatternMatcherPatternInfo(String baseWord, String suffix)
	{

		baseWord = StringUtil.TrimEndArabicWord(baseWord);
		suffix = StringUtil.TrimStartArabicWord(suffix);

		//int i;
		//for (i = body.Length - 1;
		//    i >= 0 &&
		//        (body[i] == ReversePatternMatcher.HalfSpace ||
		//         Char.IsWhiteSpace(body[i]));
		//     --i) 
		//{ }

		//int prefLen = i + 1;
		//if (prefLen < body.Length)
		//{
		//    affix = body.Substring(prefLen) + affix;
		//    body = body.Substring(0, prefLen);
		//}



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Apply word construction rules
		if (suffix.startsWith("ان") && baseWord.endsWith("ای")) // e.g. خدایان
		{
			baseWord = baseWord.substring(0, baseWord.length() - 1); // remove last letter
			suffix = "ی" + suffix;
		}
		else if (suffix.startsWith("گان") && !suffix.startsWith("گانه") && !baseWord.endsWith("ه")) // e.g. پرندگان
		{
			baseWord = baseWord + "ه";
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion


		setBaseWord(baseWord);
		setSuffix(suffix);
	}

	/** 
	 Returns a <see cref="T:System.String"/> that represents the current <see cref="T:System.Object"/>.
	 
	 @return 
	 A <see cref="T:System.String"/> that represents the current <see cref="T:System.Object"/>.
	 
	*/
	@Override
	public String toString()
	{
		return String.format("Prefix: %1$s, Affix: %2$s", getBaseWord(), getSuffix());
	}

	/** 
	 Determines whether the specified <see cref="T:System.Object"/> is equal to the current <see cref="T:System.Object"/>.
	 
	 @param obj The <see cref="T:System.Object"/> to compare with the current <see cref="T:System.Object"/>.
	 @return 
	 true if the specified <see cref="T:System.Object"/> is equal to the current <see cref="T:System.Object"/>; otherwise, false.
	 
	 @exception T:System.NullReferenceException
	 The <paramref name="obj"/> parameter is null.
	 
	*/
	@Override
	public boolean equals(Object obj)
	{
		ReversePatternMatcherPatternInfo theObj = (ReversePatternMatcherPatternInfo)((obj instanceof ReversePatternMatcherPatternInfo) ? obj : null);
		if (theObj == null)
		{
			return false;
		}

		return ((this.getSuffix().equals(theObj.getSuffix())) && (this.getBaseWord().equals(theObj.getBaseWord())));
	}

	/** 
	 Serves as a hash function for a particular type.
	 
	 @return 
	 A hash code for the current <see cref="T:System.Object"/>.
	 
	*/
	@Override
	public int hashCode()
	{
		return getSuffix().hashCode() + getBaseWord().hashCode();
	}
}