package SCICT.NLP.Utility.Stemming.Suffix;

import java.util.ArrayList;

import Helper.ArraysUtility;
import Helper.LinqSimulationArrayList;
import SCICT.NLP.Utility.Parsers.*;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.*;

// Virastyar
// http://www.virastyar.ir
// Copyright (C) 2011 Supreme Council for Information and Communication Technology (SCICT) of Iran
// 
// This file is part of Virastyar.
// 
// Virastyar is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Virastyar is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Virastyar.  If not, see <http://www.gnu.org/licenses/>.
// 
// Additional permission under GNU GPL version 3 section 7
// The sole exception to the license's terms and requierments might be the
// integration of Virastyar with Microsoft Word (any version) as an add-in.


/** 
 Helps recognize suffixes in Persian words.
*/
public class PersianSuffixRecognizer
{

	private static String[] ObjectivePronouns = PersianSuffixes.getObjectivePronouns();
	private static String[] ObjectivePronounsPermutedForAlef = PersianSuffixes.ObjectivePronounsPermutedForAlef;
	private static String[] ObjectivePronounsPermutedForHaaYaa = PersianSuffixes.ObjectivePronounsPermutedForHaaYaa;

	private static String[] ToBeVerbsPermutedForHaaYaa = PersianSuffixes.ToBeVerbsPermutedForHaaYaa;
	private static String[] ToBeVerbs = PersianSuffixes.getToBeVerbs();
	private static String[] ToBeVerbsPermutedForAlef = PersianSuffixes.ToBeVerbsPermutedForAlef;
	
	//private static String[] YaaNakare = PersianSuffixes.YaaNakare;

	private static String[] YaaBadalAzKasre = PersianSuffixes.YaaBadalAzKasre;

	//private static String[] OrdinalEnumerableAdjective = PersianSuffixes.OrdinalEnumerableAdjective;

	private static String[] ComparativeAdjectives = PersianSuffixes.ComparativeAdjectives;

	private static String[] PluralSignHaa = PersianSuffixes.PluralSignHaa;
	private static String[] PluralSignAan = PersianSuffixes.getPluralSignAan();

	private static String[] ToBeVerbsColloqualSeperable = PersianColloqualSuffixes.ToBeVerbsColloqualSeperable;
	private static String[] ToBeVerbsColloqualInseperable = PersianColloqualSuffixes.ToBeVerbsColloqualInseperable;
	private static String[] ObjectivePronounsColloqual = PersianColloqualSuffixes.ObjectivePronounsColloqual;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Construction Stuff

	/** 
	 An instance of reverse pattern matcher to help find Suffixes patterns in the end of 
	 each word.
	*/
	private ReversePatternMatcher revPatternMatcher = new ReversePatternMatcher();

	/** 
	 List of Suffixes pattern
	*/
	private LinqSimulationArrayList<String> lstPatterns = new LinqSimulationArrayList<String>();

	/** 
	 Initializes a new instance of the <see cref="PersianSuffixRecognizer"/> class.
	 
	 @param useColloquals if set to <c>true</c> it will recognize colloqual afiixes as well.
	*/
	public PersianSuffixRecognizer(boolean useColloquals)
	{
		this(useColloquals, false);
	}

	/** 
	 Initializes a new instance of the <see cref="PersianSuffixRecognizer"/> class.
	 
	 @param useColloquals if set to <c>true</c> it will recognize colloqual afiixes as well.
	 @param uniqueResults if set to <c>true</c> unique results will be returned.
	*/
	public PersianSuffixRecognizer(boolean useColloquals, boolean uniqueResults)
	{
		setUseColloquals(useColloquals);
		setReturnUniqueResults(uniqueResults);

		InitPatternsList();

		revPatternMatcher.SetEndingPatterns(lstPatterns);
	}


	/** 
	 Creates the list of Persian Suffixes patterns.
	*/
	private void InitPatternsList()
	{
		lstPatterns.add("ی");
		//lstPatterns.Add("یی");
		//lstPatterns.Add("ئی");

		lstPatterns.add("گان");
		lstPatterns.add("گانی");
		lstPatterns.add("ان");
		//lstPatterns.Add("یان");

		lstPatterns.add("انی");
		lstPatterns.addAll(CreatePluralXPattern("ی"));
		//lstPatterns.AddRange(CreateHaaXPattern("یی"));
		//lstPatterns.AddRange(CreateHaaXPattern("ئی"));
		lstPatterns.addAll(CreateObjPronounPattern("ی"));
		//lstPatterns.AddRange(CreateObjPronounPattern("یی"));
		//lstPatterns.AddRange(CreateObjPronounPattern("ئی"));
		lstPatterns.addAll(CreateObjPronounPattern(""));
		lstPatterns.addAll(CreateObjPronounPattern("ان"));
		lstPatterns.addAll(CreateObjPronounPattern("گان"));
		lstPatterns.addAll(CreateToBePattern("ی"));
		lstPatterns.addAll(CreateToBePattern("انی"));
		lstPatterns.addAll(CreateToBePattern("ان"));
		lstPatterns.addAll(CreateToBePattern("گان"));
		lstPatterns.addAll(CreateToBePattern("گانی"));
		lstPatterns.addAll(CreatePluralXPattern(""));
		lstPatterns.addAll(CreateToBePattern(""));
		lstPatterns.addAll(CreateCompAdjPattern(""));
		lstPatterns.addAll(CreateCompAdjPattern("ی"));
		//lstPatterns.AddRange(CreateCompAdjPattern("یی"));
		//lstPatterns.AddRange(CreateCompAdjPattern("ئی"));

		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern("ی"), CreatePluralXPattern("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("یی"), CreateHaaXPattern("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("ئی"), CreateHaaXPattern("")));

		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern("ی"), CreateObjPronounPattern("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("یی"), CreateObjPronounPattern("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("ئی"), CreateObjPronounPattern("")));

		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern("ی"), CreateToBePattern("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("یی"), CreateToBePattern("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("ئی"), CreateToBePattern("")));

		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern(""), CreatePluralXPattern("")));
		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern(""), CreateObjPronounPattern("")));
		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern(""), CreateToBePattern("")));

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Added by Omid
		// added by omid:
		//System.IO.StreamWriter writer = new System.IO.StreamWriter("c:/Suffix");


		//string[] all;
		//all = GetHomophoneWords(lstPatterns.ToArray(), new char[] { '&', ' ', '‌' });
		//List<string> lst = new List<string>();
		//foreach (string str in all)
		//{
		//    if (str.Contains('&'))
		//    {
		//        continue;
		//    }
		//    string nstr = StringUtil.RefinePersianWord(str);
		//    if (!lst.Contains(nstr))
		//    {
		//        lst.Add(nstr);
		//        writer.WriteLine(nstr);
		//    }
		//}
		//writer.Close();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

		if (getUseColloquals())
		{
			AddColloqualPatterns();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Added by Omid
	//private string[] GetHomophoneWords(string[] _word, char[] _homophones)
	//{
	//    List<string> suggestion = new List<string>(_word);
	//    string[] tmpSuggestion;

	//    int maxWordLength = 20;// _word[0].Length;
	//    for (int i = 0; i < maxWordLength; ++i)
	//    {
	//        tmpSuggestion = suggestion.ToArray();
	//        foreach (string word in tmpSuggestion)
	//        {
	//            if (i < word.Length)
	//            {
	//                if (Array.IndexOf(_homophones, word[i]) != -1)
	//                {
	//                    suggestion.AddRange(getHomophonePermoutation(word, _homophones, i));
	//                }
	//            }
	//        }
	//    }

	//    return suggestion.ToArray();
	//}
	//private string[] getHomophonePermoutation(string word, char[] homophones, int startIndex)
	//{
	//    List<string> suggestion = new List<string>();

	//    string tmp = "";
	//    foreach (char c in homophones)
	//    {
	//        if (word[startIndex] != c)
	//        {
	//            tmp = word.Remove(startIndex, 1).Insert(startIndex, c.ToString());
	//            if (!suggestion.Contains(tmp))
	//            {
	//                suggestion.Add(tmp);
	//            }
	//        }
	//    }

	//    tmp = word.Remove(startIndex, 1);
	//    if (!suggestion.Contains(tmp))
	//    {
	//        suggestion.Add(tmp);
	//    }

	//    return suggestion.ToArray();
	//}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Adds the colloqual Suffix patterns to the list of patterns.
	*/
	private void AddColloqualPatterns()
	{
		lstPatterns.addAll(CreatePluralXPatternColloqual("ی"));
		//lstPatterns.AddRange(CreateHaaXPatternColloqual("یی"));
		//lstPatterns.AddRange(CreateHaaXPatternColloqual("ئی"));

		lstPatterns.addAll(CreateObjPronounPatternColloqual("ی"));
		//lstPatterns.AddRange(CreateObjPronounPatternColloqual("یی"));
		//lstPatterns.AddRange(CreateObjPronounPatternColloqual("ئی"));

		lstPatterns.addAll(CreateObjPronounPatternColloqual(""));
		lstPatterns.addAll(CreateObjPronounPatternColloqual("ان"));
		lstPatterns.addAll(CreateObjPronounPatternColloqual("گان"));

		lstPatterns.addAll(CreateToBePatternColloqual("ی"));
		//lstPatterns.AddRange(CreateToBePatternColloqual("یی"));
		//lstPatterns.AddRange(CreateToBePatternColloqual("ئی"));

		lstPatterns.addAll(CreateToBePatternColloqual("انی"));
		lstPatterns.addAll(CreateToBePatternColloqual("ان"));
		lstPatterns.addAll(CreateToBePatternColloqual("گان"));
		lstPatterns.addAll(CreateToBePatternColloqual("گانی"));

		lstPatterns.addAll(CreateAaaXPatternColloqual("ی"));
		//lstPatterns.AddRange(CreateAaaXPatternColloqual("یی"));
		//lstPatterns.AddRange(CreateAaaXPatternColloqual("ئی"));

		lstPatterns.addAll(CreateAaaXPatternColloqual(""));
		lstPatterns.addAll(CreatePluralXPatternColloqual(""));
		lstPatterns.addAll(CreateToBePatternColloqual(""));

		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern("ی"), CreatePluralXPatternColloqual("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("یی"), CreateHaaXPatternColloqual("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("ئی"), CreateHaaXPatternColloqual("")));

		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern("ی"), CreateAaaXPatternColloqual("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("یی"), CreateAaaXPatternColloqual("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("ئی"), CreateAaaXPatternColloqual("")));

		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern(""), CreateAaaXPatternColloqual("")));

		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern("ی"), CreateObjPronounPatternColloqual("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("یی"), CreateObjPronounPatternColloqual("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("ئی"), CreateObjPronounPatternColloqual("")));

		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern(""), CreatePluralXPatternColloqual("")));
		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern(""), CreateObjPronounPatternColloqual("")));
		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern(""), CreateToBePatternColloqual("")));

		lstPatterns.addAll(MultiplyStrings(CreateCompAdjPattern("ی"), CreateToBePatternColloqual("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("یی"), CreateToBePatternColloqual("")));
		//lstPatterns.AddRange(MultiplyStrings(CreateCompAdjPattern("ئی"), CreateToBePatternColloqual("")));
	}

	/** 
	 Gets a value indicating whether the colloqual Suffixes should be recognized.
	 
	 <value><c>true</c> if the colloqual Suffixes should be recognized; otherwise, <c>false</c>.</value>
	*/
	private boolean privateUseColloquals;
	public final boolean getUseColloquals()
	{
		return privateUseColloquals;
	}
	private void setUseColloquals(boolean value)
	{
		privateUseColloquals = value;
	}

	/** 
	 Gets a value indicating whether the returned results should be unique.
	 
	 <value><c>true</c> if the returned results should be unique; otherwise, <c>false</c>.</value>
	*/
	private boolean privateReturnUniqueResults;
	public final boolean getReturnUniqueResults()
	{
		return privateReturnUniqueResults;
	}
	private void setReturnUniqueResults(boolean value)
	{
		privateReturnUniqueResults = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Create Patterns
	private Iterable<String> CreateHaaXPattern(String wordPart)
	{
		return MultiplyStrings(wordPart + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, 
				ArraysUtility.Concat(PluralSignHaa,
						ArraysUtility.Concat((MultiplyStrings("های", ObjectivePronouns)),
								(MultiplyStrings("های" + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, ObjectivePronounsPermutedForHaaYaa)))));
	}
	//private IEnumerable<string> CreateHaaXPattern(string wordPart)
	//{
	//    return MultiplyStrings(wordPart + ReversePatternMatcher.SymbolHalfSpace,
	//                PluralSignHaa.Concat(
	//                MultiplyStrings("های", ObjectivePronouns)).Concat(
	//                MultiplyStrings("های" + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, ObjectivePronounsPermutedForHaaYaa))
	//        );
	//}

	private Iterable<String> CreateHaaXPatternColloqual(String wordPart)
	{
		return MultiplyStrings(wordPart + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, MultiplyStrings("ها", ObjectivePronounsColloqual));
	}

	private Iterable<String> CreateAanXPattern(String wordPart)
	{
		return MultiplyStrings(wordPart,
				ArraysUtility.Concat(PluralSignAan,
						ArraysUtility.Concat((MultiplyStrings("ان", ObjectivePronouns)),
								(MultiplyStrings("ان", ObjectivePronounsPermutedForHaaYaa)))));
	}

	private Iterable<String> CreateAanXPatternColloqual(String wordPart)
	{
		return MultiplyStrings(wordPart, MultiplyStrings("ان", ObjectivePronounsColloqual));
	}


	/** 
	 A Concatenation of CreateHaaxPattern and CreateAanXPattern
	*/
	private Iterable<String> CreatePluralXPattern(String wordPart)
	{
		return ArraysUtility.Concat(CreateHaaXPattern(wordPart),(CreateAanXPattern(wordPart)));
	}

	/** 
	 A Concatenation of CreateHaaxPatternColloqual and CreateAanXPatternColloqual
	*/
	private Iterable<String> CreatePluralXPatternColloqual(String wordPart)
	{
		return ArraysUtility.Concat(CreateHaaXPatternColloqual(wordPart),(CreateAanXPatternColloqual(wordPart)));
	}



	private Iterable<String> CreateAaaXPatternColloqual(String wordPart)
	{
		return MultiplyStrings(wordPart,
				ArraysUtility.Concat((new String[] {"ا", "ای", "ایی", "ائی"}),
					ArraysUtility.Concat((MultiplyStrings("ای", ObjectivePronouns)),
						ArraysUtility.Concat((MultiplyStrings("ای" + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, ObjectivePronounsPermutedForHaaYaa)),
							(MultiplyStrings("ا", ObjectivePronounsColloqual)))))); // + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar
	}

	private Iterable<String> CreateObjPronounPattern(String wordPart)
	{
		return MultiplyStrings(wordPart,
				ArraysUtility.Concat(ObjectivePronouns,
						(MultiplyStrings((new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar)).toString(),
								ObjectivePronounsPermutedForHaaYaa))));
	}

	private Iterable<String> CreateObjPronounPatternColloqual(String wordPart)
	{
		return MultiplyStrings(wordPart + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, ObjectivePronounsColloqual);
	}

	private Iterable<String> CreateToBePatternColloqual(String wordPart)
	{
		return MultiplyStrings(wordPart + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, 
				ArraysUtility.Concat(ToBeVerbsColloqualSeperable,(MultiplyStrings(wordPart, ToBeVerbsColloqualInseperable))));
	}

	private Iterable<String> CreateToBePattern(String wordPart)
	{
		return MultiplyStrings(wordPart + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar,
				ArraysUtility.Concat(ToBeVerbsPermutedForHaaYaa,(MultiplyStrings(wordPart, ToBeVerbs))));
	}

	private Iterable<String> CreateCompAdjPattern(String wordPart)
	{
		return MultiplyStrings(wordPart + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, ComparativeAdjectives);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Interface

	/** 
	 Matches the input string for finding Persian Suffixes.
	 
	 @param input The input string to find Suffixes.
	 @return 
	*/
	public final ReversePatternMatcherPatternInfo[] MatchForSuffix(String input)
	{
		return revPatternMatcher.Match(input, this.getReturnUniqueResults());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Some Utils

	/** 
	 Returns a sequence of strings gained from concatenating string <paramref name="first"/>
	 with all the strings in <paramref name="second"/>.
	 
	 @param first The string to form the left-hand-side of the concatenations.
	 @param second The sequence of strings to form the right-hand-side of the concatenations.
	 @return 
	*/
	private static Iterable<String> MultiplyStrings(String arg1, String[] array2)
	{
		ArrayList<String> iterable2=new ArrayList<String>();
		for (String string : array2) 
			iterable2.add(string);
		return MultiplyStrings(arg1, iterable2);
	}
	private static Iterable<String> MultiplyStrings(String[] array1, String[] array2)
	{
		ArrayList<String> iterable1=new ArrayList<String>();
		for (String string : array1) 
			iterable1.add(string);
		ArrayList<String> iterable2=new ArrayList<String>();
		for (String string : array2) 
			iterable2.add(string);
		
		return MultiplyStrings(iterable1, iterable2);
	}
	private static Iterable<String> MultiplyStrings(String[] array1,Iterable<String> second)
	{
		ArrayList<String> iterable1=new ArrayList<String>();
		for (String string : array1) 
			iterable1.add(string);
		
		return MultiplyStrings(iterable1, second);
	}

	private static Iterable<String> MultiplyStrings(String first, Iterable<String> second)
	{
		return MultiplyStrings(new String[] {first}, second);
	}

	/** 
	 Returns a sequence of strings gained from concatenating all the strings 
	 in <paramref name="first"/> with all the strings in <paramref name="second"/>.
	 
	 @param first The sequence of strings to form the left-hand-side of the concatenations.
	 @param second The sequence of strings to form the right-hand-side of the concatenations.
	*/
	private static Iterable<String> MultiplyStrings(Iterable<String> first, Iterable<String> second)
	{
		java.util.ArrayList<String> result = new java.util.ArrayList<String>();

		for (String str1 : first)
		{
			for (String str2 : second)
			{
//                    if ()
				result.add(str1 + str2);
			}
		}

		return result;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}