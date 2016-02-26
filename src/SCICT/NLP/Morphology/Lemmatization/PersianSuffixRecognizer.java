package SCICT.NLP.Morphology.Lemmatization;

import java.util.ArrayList;

import Helper.ArraysUtility;
import Helper.LinqSimulationArrayList;
import SCICT.NLP.Morphology.Inflection.InflectionAnalyser;
import SCICT.NLP.Persian.Constants.PersianColloqualSuffixes;
import SCICT.NLP.Persian.Constants.PersianSuffixes;
import SCICT.NLP.Persian.Constants.PersianSuffixesCategory;
import SCICT.NLP.Persian.Constants.PseudoSpace;
import SCICT.NLP.Utility.Parsers.ReversePatternMatcher;
import SCICT.NLP.Utility.Parsers.ReversePatternMatcherPatternInfo;

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
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Suffix Constants

	private static final String[] m_indefiniteYaaPermutation = Helper.ArraysUtility.toArray(IndefiniteYaaPermutation());

	private static final String[] m_yaaBadalAzKasraPermutation = Helper.ArraysUtility.toArray(YaaBadalAzKasraPermutation());

	private static final String[] m_toBeVerbPermutation = Helper.ArraysUtility.toArray(ToBeVerbPermutation());

	private static final String[] m_ojectivePronounPermutation =Helper.ArraysUtility.toArray( ObjectivePronounPermutation());

	private static final String[] m_pluralHaaPermutation = Helper.ArraysUtility.toArray(PluralHaaPermutation());

	private static final String[] m_pluralAnnPermutation = Helper.ArraysUtility.toArray(PluralAnnPermutation());

	private static final String[] m_comparativeAdjectivePermutation = Helper.ArraysUtility.toArray(ComparativeAdjectivePermutation());

	private static final String[] m_yaaNesbatPermutation = Helper.ArraysUtility.toArray(YaaNesbatPermutation());

	private static final String[] m_enumerableAdjectivePermutation = Helper.ArraysUtility.toArray(EnumerableAdjectivePermutation());


	/** 
	 An instance of reverse pattern matcher to help find Suffixes patterns in the end of 
	 each word.
	*/
	private final ReversePatternMatcher m_revPatternMatcher = new ReversePatternMatcher();

	/** 
	 List of Suffixes pattern
	*/
	private LinqSimulationArrayList<String> m_lstPatterns = new LinqSimulationArrayList<String>();

	/** 
	 Initializes a new instance of the <see cref="PersianSuffixRecognizer"/> class.
	 
	 @param useColloquals if set to <c>true</c> it will recognize colloqual affixes as well.
	*/
	public PersianSuffixRecognizer(boolean useColloquals)
	{
		this(useColloquals, false);
	}

	/** 
	 Initializes a new instance of the <see cref="PersianSuffixRecognizer"/> class.
	 
	 @param useColloquals if set to <c>true</c> it will recognize colloqual affixes as well.
	 @param uniqueResults if set to <c>true</c> unique results will be returned.
	*/
	public PersianSuffixRecognizer(boolean useColloquals, boolean uniqueResults)
	{
		setUseColloquals(useColloquals);
		setReturnUniqueResults(uniqueResults);

		PersianSuffixesCategory suffixCategory = PersianSuffixesCategory.forValue(0);
		suffixCategory = suffixCategory.Set(PersianSuffixesCategory.ComparativeAdjectives);
		suffixCategory = suffixCategory.Set(PersianSuffixesCategory.IndefiniteYaa);
		suffixCategory = suffixCategory.Set(PersianSuffixesCategory.ObjectivePronoun);
		suffixCategory = suffixCategory.Set(PersianSuffixesCategory.OrdinalEnumerableAdjective);
		suffixCategory = suffixCategory.Set(PersianSuffixesCategory.PluralSignAan);
		suffixCategory = suffixCategory.Set(PersianSuffixesCategory.PluralSignHaa);
		suffixCategory = suffixCategory.Set(PersianSuffixesCategory.ToBeVerb);
		suffixCategory = suffixCategory.Set(PersianSuffixesCategory.YaaBadalAzKasre);
		suffixCategory = suffixCategory.Set(PersianSuffixesCategory.YaaNesbat);

		InitPatternsList(suffixCategory);

		m_revPatternMatcher.SetEndingPatterns(m_lstPatterns);
	}

	/** 
	 Creates the list of Persian Suffixes patterns.
	*/
	private void InitPatternsList(PersianSuffixesCategory suffixCategory)
	{
		if (suffixCategory.Has(PersianSuffixesCategory.IndefiniteYaa))
		{
			m_lstPatterns.addAll(m_indefiniteYaaPermutation);
		}
		if (suffixCategory.Has(PersianSuffixesCategory.YaaBadalAzKasre))
		{
			m_lstPatterns.addAll(m_yaaBadalAzKasraPermutation);
		}
		if (suffixCategory.Has(PersianSuffixesCategory.ToBeVerb))
		{
			m_lstPatterns.addAll(m_toBeVerbPermutation);
		}
		if (suffixCategory.Has(PersianSuffixesCategory.ObjectivePronoun))
		{
			m_lstPatterns.addAll(m_ojectivePronounPermutation);
		}
		if (suffixCategory.Has(PersianSuffixesCategory.PluralSignHaa))
		{
			m_lstPatterns.addAll(m_pluralHaaPermutation);
		}
		if (suffixCategory.Has(PersianSuffixesCategory.PluralSignAan))
		{
			m_lstPatterns.addAll(m_pluralAnnPermutation);
		}
		if (suffixCategory.Has(PersianSuffixesCategory.YaaNesbat))
		{
			m_lstPatterns.addAll(m_yaaNesbatPermutation);
		}
		if (suffixCategory.Has(PersianSuffixesCategory.ComparativeAdjectives))
		{
			m_lstPatterns.addAll(m_comparativeAdjectivePermutation);
		}
		if (suffixCategory.Has(PersianSuffixesCategory.OrdinalEnumerableAdjective))
		{
			m_lstPatterns.addAll(m_enumerableAdjectivePermutation);
		}
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

	private static Iterable<String> IndefiniteYaaPermutation()
	{
		return MultiplyStrings((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString(), PersianSuffixes.getIndefiniteYaa());
	}

	private static Iterable<String> YaaBadalAzKasraPermutation()
	{
		return MultiplyStrings((new Character(ReversePatternMatcher.SymbolHalfSpace)).toString(), PersianSuffixes.YaaBadalAzKasre);
	}

	private static Iterable<String> ToBeVerbPermutation()
	{
		return MultiplyStrings((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString(), PersianSuffixes.getToBeVerbs());
	}

	private static Iterable<String> ObjectivePronounPermutation()
	{
		return MultiplyStrings((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString(), ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getObjectivePronouns(), PersianSuffixes.ToBeVerbsBase),(PersianSuffixes.getObjectivePronouns())));
	}

	private static Iterable<String> PluralHaaPermutation()
	{
		return MultiplyStrings((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString(),
				ArraysUtility.Concat( MultiplyStrings(PersianSuffixes.PluralSignHaa, m_ojectivePronounPermutation),
					ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.PluralSignHaa, PersianSuffixes.getToBeVerbs()),
						ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.PluralSignHaa, PersianSuffixes.getIndefiniteYaa()),
								ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.PluralSignHaa, PersianSuffixes.YaaBadalAzKasre),
										(PersianSuffixes.PluralSignHaa))))));
	}

	private static Iterable<String> PluralAnnPermutation()
	{
		return MultiplyStrings((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString(),
				ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getPluralSignAan(), m_ojectivePronounPermutation),
						ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getPluralSignAan(), PersianSuffixes.ToBeVerbsBase),
								ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getPluralSignAan(), PersianSuffixes.getIndefiniteYaa()),
										(PersianSuffixes.getPluralSignAan())))));
	}

	private static Iterable<String> ComparativeAdjectivePermutation()
	{
		return MultiplyStrings((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString(),
				ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.ComparativeAdjectives, m_ojectivePronounPermutation),
						ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.ComparativeAdjectives, ToBeVerbPermutation()),
								ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.ComparativeAdjectives, IndefiniteYaaPermutation()),
										ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.ComparativeAdjectives, m_pluralHaaPermutation),
												(PersianSuffixes.ComparativeAdjectives))))));

	}

	private static Iterable<String> EnumerableAdjectivePermutation()
	{
		return MultiplyStrings((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString(),
				ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getEnumerableAdjective(), m_ojectivePronounPermutation),
					ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getEnumerableAdjective(), ToBeVerbPermutation()),
						ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getEnumerableAdjective(), IndefiniteYaaPermutation()),
							ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.EnumerableAdjectiveAmbigus, PluralHaaPermutation()),
								(PersianSuffixes.getEnumerableAdjective()))))));

	}

	private static Iterable<String> YaaNesbatPermutation()
	{
		return MultiplyStrings((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString(),
				ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getYaaNesbat(), m_ojectivePronounPermutation),
					ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getYaaNesbat(), ToBeVerbPermutation()),
						ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getYaaNesbat(), IndefiniteYaaPermutation()),
							ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getYaaNesbat(), m_pluralHaaPermutation),
								ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getYaaNesbat(), m_pluralAnnPermutation),
									ArraysUtility.Concat(MultiplyStrings(PersianSuffixes.getYaaNesbat(), m_comparativeAdjectivePermutation),
										(PersianSuffixes.getYaaNesbat()))))))));

	}

	private Iterable<String> CreateHaaXPatternColloqual(String wordPart)
	{
		return MultiplyStrings(wordPart + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, MultiplyStrings("ها", PersianColloqualSuffixes.ObjectivePronounsColloqual));
	}

	private Iterable<String> CreateAanXPatternColloqual(String wordPart)
	{
		return MultiplyStrings(wordPart, MultiplyStrings("ان", PersianColloqualSuffixes.ObjectivePronounsColloqual));
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
					ArraysUtility.Concat((MultiplyStrings("ای", PersianSuffixes.getObjectivePronouns())),
						ArraysUtility.Concat((MultiplyStrings("ای" + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, PersianSuffixes.ObjectivePronounsPermutedForHaaYaa)),
							(MultiplyStrings("ا", PersianColloqualSuffixes.ObjectivePronounsColloqual)))))); // + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar
	}

	private Iterable<String> CreateObjPronounPatternColloqual(String wordPart)
	{
		return MultiplyStrings(wordPart + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar, PersianColloqualSuffixes.ObjectivePronounsColloqual);
	}

	private Iterable<String> CreateToBePatternColloqual(String wordPart)
	{
		return MultiplyStrings(wordPart + ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar,
				ArraysUtility.Concat(PersianColloqualSuffixes.ToBeVerbsColloqualSeperable,
				(MultiplyStrings(wordPart, PersianColloqualSuffixes.ToBeVerbsColloqualInseperable))));
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
		return m_revPatternMatcher.Match(input, this.getReturnUniqueResults());
	}

	/**
	 Return the suffix category
	
	@param suffix Suffix
	@return Suffix category
	@exception NotImplementedException
	*/
	public final PersianSuffixesCategory SuffixCategory(String suffix)
	{
		PersianSuffixesCategory suffixCategory = PersianSuffixesCategory.forValue(0);
		suffix = suffix.replace((new Character(PseudoSpace.ZWNJ)).toString(), "");

		if (IsInSuffixPattern(suffix, m_comparativeAdjectivePermutation))
		{
			suffixCategory = PersianSuffixesCategory.forValue(suffixCategory.getValue() | PersianSuffixesCategory.ComparativeAdjectives.getValue());
		}
		if (IsInSuffixPattern(suffix, m_indefiniteYaaPermutation))
		{
			suffixCategory = PersianSuffixesCategory.forValue(suffixCategory.getValue() | PersianSuffixesCategory.IndefiniteYaa.getValue());
		}
		if (IsInSuffixPattern(suffix, m_ojectivePronounPermutation))
		{
			suffixCategory = PersianSuffixesCategory.forValue(suffixCategory.getValue() | PersianSuffixesCategory.ObjectivePronoun.getValue());
		}
		if (IsInSuffixPattern(suffix, m_enumerableAdjectivePermutation))
		{
			suffixCategory = PersianSuffixesCategory.forValue(suffixCategory.getValue() | PersianSuffixesCategory.OrdinalEnumerableAdjective.getValue());
		}
		if (IsInSuffixPattern(suffix, m_pluralAnnPermutation))
		{
			suffixCategory = PersianSuffixesCategory.forValue(suffixCategory.getValue() | PersianSuffixesCategory.PluralSignAan.getValue());
		}
		if (IsInSuffixPattern(suffix, m_pluralHaaPermutation))
		{
			suffixCategory = PersianSuffixesCategory.forValue(suffixCategory.getValue() | PersianSuffixesCategory.PluralSignHaa.getValue());
		}
		if (IsInSuffixPattern(suffix, m_toBeVerbPermutation))
		{
			suffixCategory = PersianSuffixesCategory.forValue(suffixCategory.getValue() | PersianSuffixesCategory.ToBeVerb.getValue());
		}
		if (IsInSuffixPattern(suffix, m_yaaBadalAzKasraPermutation))
		{
			suffixCategory = PersianSuffixesCategory.forValue(suffixCategory.getValue() | PersianSuffixesCategory.YaaBadalAzKasre.getValue());
		}
		if (IsInSuffixPattern(suffix, m_yaaNesbatPermutation))
		{
			suffixCategory = PersianSuffixesCategory.forValue(suffixCategory.getValue() | PersianSuffixesCategory.YaaNesbat.getValue());
		}

		return suffixCategory;
	}

	/**
	 Return equal suffix with spacing symbols
	
	@param suffix Suffix
	@return Suffix with spacing symbols
	*/
	public final String EqualSuffixWithSpacingSymbols(String suffix)
	{
		String equalSuffix = suffix;

		suffix = suffix.replace((new Character(PseudoSpace.ZWNJ)).toString(), "");

		Helper.RefObject<String> tempRef_equalSuffix = new Helper.RefObject<String>(equalSuffix);
		boolean tempVar = IsInSuffixPattern(suffix, m_comparativeAdjectivePermutation, tempRef_equalSuffix);
			equalSuffix = tempRef_equalSuffix.argvalue;
		if (tempVar)
		{
		}
		else
		{
			Helper.RefObject<String> tempRef_equalSuffix2 = new Helper.RefObject<String>(equalSuffix);
			boolean tempVar2 = IsInSuffixPattern(suffix, m_indefiniteYaaPermutation, tempRef_equalSuffix2);
				equalSuffix = tempRef_equalSuffix2.argvalue;
			if (tempVar2)
			{
			}
			else
			{
				Helper.RefObject<String> tempRef_equalSuffix3 = new Helper.RefObject<String>(equalSuffix);
				boolean tempVar3 = IsInSuffixPattern(suffix, m_ojectivePronounPermutation, tempRef_equalSuffix3);
					equalSuffix = tempRef_equalSuffix3.argvalue;
				if (tempVar3)
				{
				}
				else
				{
					Helper.RefObject<String> tempRef_equalSuffix4 = new Helper.RefObject<String>(equalSuffix);
					boolean tempVar4 = IsInSuffixPattern(suffix, m_enumerableAdjectivePermutation, tempRef_equalSuffix4);
						equalSuffix = tempRef_equalSuffix4.argvalue;
					if (tempVar4)
					{
					}
					else
					{
						Helper.RefObject<String> tempRef_equalSuffix5 = new Helper.RefObject<String>(equalSuffix);
						boolean tempVar5 = IsInSuffixPattern(suffix, m_pluralAnnPermutation, tempRef_equalSuffix5);
							equalSuffix = tempRef_equalSuffix5.argvalue;
						if (tempVar5)
						{
						}
						else
						{
							Helper.RefObject<String> tempRef_equalSuffix6 = new Helper.RefObject<String>(equalSuffix);
							boolean tempVar6 = IsInSuffixPattern(suffix, m_pluralHaaPermutation, tempRef_equalSuffix6);
								equalSuffix = tempRef_equalSuffix6.argvalue;
							if (tempVar6)
							{
							}
							else
							{
								Helper.RefObject<String> tempRef_equalSuffix7 = new Helper.RefObject<String>(equalSuffix);
								boolean tempVar7 = IsInSuffixPattern(suffix, m_toBeVerbPermutation, tempRef_equalSuffix7);
									equalSuffix = tempRef_equalSuffix7.argvalue;
								if (tempVar7)
								{
								}
								else
								{
									Helper.RefObject<String> tempRef_equalSuffix8 = new Helper.RefObject<String>(equalSuffix);
									boolean tempVar8 = IsInSuffixPattern(suffix, m_yaaBadalAzKasraPermutation, tempRef_equalSuffix8);
										equalSuffix = tempRef_equalSuffix8.argvalue;
									if (tempVar8)
									{
									}
									else
									{
										Helper.RefObject<String> tempRef_equalSuffix9 = new Helper.RefObject<String>(equalSuffix);
										boolean tempVar9 = IsInSuffixPattern(suffix, m_yaaNesbatPermutation, tempRef_equalSuffix9);
											equalSuffix = tempRef_equalSuffix9.argvalue;
										if (tempVar9)
										{
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return equalSuffix;
	}

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
		ArrayList<String> itar=new ArrayList<String>();
		itar.add(first);
		return MultiplyStrings(itar, second);
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
				if (IsSpaceOrPseudoSpaceMark(str1))
				{
					result.add(str1 + str2);
				}
				else
				{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
						///#region Redundant
					//PersianCombinationSpacingState combinationSpacingState;
					//if (StartWithSpaceOrPseudoSpacePlus(str2) || StartWithSpaceOrPseudoSpaceStar(str2))
					//{
					//    combinationSpacingState = PersianCombinationSpacingState.PseudoSpace;
					//    if (InflectionAnalyser.IsValidPhoneticComposition(PurifySymbols(str1), PurifySymbols(str2), combinationSpacingState))
					//    {
					//        result.Add(str1 + str2);
					//    }
					//}
					//if (!StartWithSpaceOrPseudoSpacePlus(str2) || StartWithSpaceOrPseudoSpaceStar(str2))
					//{
					//    combinationSpacingState = PersianCombinationSpacingState.Continous;
					//    if (InflectionAnalyser.IsValidPhoneticComposition(PurifySymbols(str1), PurifySymbols(str2), combinationSpacingState))
					//    {
					//        result.Add(str1 + str2);
					//    }
					//}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
						///#endregion

					if (InflectionAnalyser.IsValidPhoneticComposition(PurifySymbols(str1), PurifySymbols(str2)))
					{
						result.add(str1 + str2);
					}
				}
			}
		}

		return result;
	}

	private static boolean IsSpaceOrPseudoSpaceMark(String str)
	{
		if ((new Character(ReversePatternMatcher.SymbolHalfSpace)).toString().equals(str) || (new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString().equals(str) || (new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpace)).toString().equals(str) || (new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus)).toString().equals(str) || (new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar)).toString().equals(str) || (new Character(ReversePatternMatcher.SymbolSpacePlus)).toString().equals(str) || (new Character(ReversePatternMatcher.SymbolSpaceStar)).toString().equals(str))
		{
			return true;
		}

		return false;
	}

	private static String PurifySymbols(String str)
	{
		String tmpStr = str;

		if (tmpStr.contains(ReversePatternMatcher.SymbolHalfSpace+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolHalfSpace)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolHalfSpaceQuestionMark+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolSpaceOrHalfSpace+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpace)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolSpacePlus+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolSpacePlus)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolSpaceStar+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolSpaceStar)).toString(), "");
		}


		return tmpStr;
	}

	private static boolean StartWithSpaceOrPseudoSpaceStar(String str)
	{
		if (str.startsWith((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString()) || str.startsWith((new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar)).toString()) || str.startsWith((new Character(ReversePatternMatcher.SymbolSpaceStar)).toString()))
		{
			return true;
		}

		return false;
	}

	private static boolean StartWithSpaceOrPseudoSpacePlus(String str)
	{
		if (str.startsWith((new Character(ReversePatternMatcher.SymbolHalfSpace)).toString()) || str.startsWith((new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpace)).toString()) || str.startsWith((new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus)).toString()) || str.startsWith((new Character(ReversePatternMatcher.SymbolSpacePlus)).toString()))
		{
			return true;
		}

		return false;
	}

	private static boolean IsInSuffixPattern(String suffix, String[] patterns)
	{
		for (String pattern : patterns)
		{
			if (PurifySymbols(pattern).equals(suffix))
			{
				return true;
			}
		}

		return false;
	}
	private static boolean IsInSuffixPattern(String suffix, String[] patterns, Helper.RefObject<String> suffixWithSpacingSymbol)
	{
		suffixWithSpacingSymbol.argvalue = suffix;

		for (String pattern : patterns)
		{
			if (PurifySymbols(pattern).equals(suffix))
			{
				suffixWithSpacingSymbol.argvalue = pattern;
				return true;
			}
		}

		return false;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}