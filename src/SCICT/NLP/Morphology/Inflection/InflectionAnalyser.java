package SCICT.NLP.Morphology.Inflection;
/*
 * 
 * 
 * 
 * the Has Methods in used in this class are implemented 
 * as static which seems to stablish some logical errore
 * all Has method shoud correct
 * 
 * 
 * 
 * 
 * 
 * 
 */

import Helper.ArraysUtility;
import SCICT.NLP.Persian.Constants.PersianAlphabets;
import SCICT.NLP.Persian.Constants.PersianCombinationSpacingState;
import SCICT.NLP.Persian.Constants.PersianPOSTag;
import SCICT.NLP.Persian.Constants.PersianSuffixes;
import SCICT.NLP.Persian.Constants.PersianSuffixesCategory;
import SCICT.NLP.Persian.Constants.PseudoSpace;

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
 Analysis Persian inflection rules 
*/
public final class InflectionAnalyser
{
	/**
	 Check whether that given composition is a valid composition as phonetic rules
	
	@param stem Stem word
	@param suffix Suffix word
	@param combinationSpacingState Spacing state
	@return True if given composition is correct
	*/
	public static boolean IsValidPhoneticComposition(String stem, String suffix)
	{
		if (stem.endsWith("ا"))
		{
			if (PhoneticCompositinRulesForAlef(stem, suffix)) //start with Alef
			{
				return true;
			}
		}
		else if (stem.endsWith("ی"))
		{
			if (PhoneticCompositinRulesForYaa(stem, suffix)) // Start With Yaa
			{
				return true;
			}
		}
		else if (stem.endsWith("ه"))
		{
			if (PhoneticCompositinRulesForHeh(stem, suffix)) // Start with Heh
			{
				return true;
			}
		}
		else
		{
			if (PhoneticCompositinRulesForConsonants(stem, suffix)) // Start with Consonants
			{
				return true;
			}
		}

		return false;
	}

	/**
	 Check whether that given composition is a valid composition as phonetic rules
	
	@param stem Stem word
	@param suffix Suffix word
	@param pos POS Tag of the stem
	@param suffixCategory Suffix category that matchs the rule
	@return True if given composition is correct
	*/
	public static boolean IsValidPhoneticComposition(String stem, String suffix, PersianPOSTag pos, Helper.RefObject<PersianSuffixesCategory> suffixCategory)
	{
		if (stem.endsWith("ا"))
		{
			if (PhoneticCompositinRulesForAlef(stem, suffix, suffixCategory)) //start with Alef
			{
				return true;
			}
		}
		else if (stem.endsWith("ی"))
		{
			if (PhoneticCompositinRulesForYaa(stem, suffix, suffixCategory)) // Start With Yaa
			{
				return true;
			}
		}
		else if (stem.endsWith("ه"))
		{
			if (PhoneticCompositinRulesForHeh(stem, suffix, pos, suffixCategory)) // Start with Heh
			{
				return true;
			}
		}
		else if (stem.endsWith("و"))
		{
			if (PhoneticCompositinRulesForVav(stem, suffix, pos, suffixCategory)) // Start with Heh
			{
				return true;
			}
		}
		else
		{
			if (PhoneticCompositinRulesForConsonants(stem, suffix, suffixCategory)) // Start with Consonants
			{
				return true;
			}
		}

		return false;
	}

	/**
	 Check whether that given composition is a valid suffix declension referring stem's POS
	
	@param stem Stem word
	@param suffix Suffix word
	@param combinationSpacingState Spacing state
	@param pos Stem's POS tag
	@param suffixCategory Suffix Category
	@return True if given composition is correct
	@exception NotImplementedException
	*/
	public static boolean IsValidDeclension(String stem, String suffix, PersianCombinationSpacingState combinationSpacingState, PersianPOSTag pos, PersianSuffixesCategory suffixCategory)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 Check whether that given composition is a valid suffix declension referring stem's POS
	
	@param pos Stem's POS tag
	@param suffixCategory Suffix Category
	@return True if given composition is correct
	@exception NotImplementedException
	*/
	public static boolean IsValidDeclension(PersianPOSTag pos, PersianSuffixesCategory suffixCategory)
	{
		if (pos.Has(PersianPOSTag.N) || pos.Has(PersianPOSTag.CL) || pos.Has(PersianPOSTag.PRO))
		{
			if (suffixCategory.Has(PersianSuffixesCategory.IndefiniteYaa))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.ObjectivePronoun))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.PluralSignAan))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.PluralSignHaa))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.ToBeVerb))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.YaaBadalAzKasre))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.YaaNesbat))
			{
				return true;
			}
		}

		if (pos.Has(PersianPOSTag.AJ))
		{
			if (suffixCategory.Has(PersianSuffixesCategory.IndefiniteYaa))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.ObjectivePronoun))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.ToBeVerb))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.YaaBadalAzKasre))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.YaaNesbat))
			{
				return true;
			}
			if (suffixCategory.Has(PersianSuffixesCategory.ComparativeAdjectives))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 Get equal suffix which is correct as phonetic rules 
	
	@param stem Stem word
	@param suffix Suffix
	@param suffixCategory Suffix Category
	@return Equal correct suffix
	*/
	public static String EqualSuffixWithCorrectPhonetic(String stem, String suffix, PersianSuffixesCategory suffixCategory)
	{
		//if (IsValidPhoneticComposition(stem, suffix))
		//{
		//    return suffix;
		//}

		if (stem.endsWith("ا"))
		{
			suffix = ModifySuffixForAlef(suffix, suffixCategory);
		}
		else if (stem.endsWith("ی"))
		{
			suffix = ModifySuffixForYaa(suffix, suffixCategory);
		}
		else if (stem.endsWith("ه"))
		{
			suffix = ModifySuffixForHeh(suffix, suffixCategory);
		}
		else
		{
			suffix = ModifySuffixForConsonant(suffix, suffixCategory);
		}

		return suffix;
	}

	/**
	 Calculate spacing state of combining given stem and suffix
	
	@param stem Stem word
	@param suffix suffix
	@param pos Pos Tag
	@return Spacing state
	*/
	public static PersianCombinationSpacingState CalculateSpacingState(String stem, String suffix, PersianPOSTag pos)
	{
		if (stem.endsWith("ا"))
		{
			return SpacingStateForAlef(stem, suffix); // Start with Alef
		}
		else if (stem.endsWith("ی"))
		{
			return SpacingStateForYaa(stem, suffix); // Start With Yaa
		}
		else if (stem.endsWith("ه"))
		{
			return SpacingStateForHeh(stem, suffix, pos); // Start with Heh
		}
		else
		{
			return SpacingStateForConsonants(stem, suffix); // Start with Consonants
		}
	}

	/**
	 Return POS category of possible words that an accept suffixes in given category
	
	@param suffixCategory Suffix categoy
	@return POS tag(s)
	*/
	public static PersianPOSTag AcceptingPOS(PersianSuffixesCategory suffixCategory)
	{
		PersianPOSTag posTag = PersianPOSTag.UserPOS;


		if (suffixCategory.Has(PersianSuffixesCategory.IndefiniteYaa) || suffixCategory.Has(PersianSuffixesCategory.ObjectivePronoun) || suffixCategory.Has(PersianSuffixesCategory.ToBeVerb) || suffixCategory.Has(PersianSuffixesCategory.YaaBadalAzKasre) || suffixCategory.Has(PersianSuffixesCategory.YaaNesbat))
		{
			//posTag = posTag.Set(PersianPOSTag.AJ);
			//posTag = posTag.Set(PersianPOSTag.CL);
			posTag = posTag.Set(PersianPOSTag.N);
			//posTag = posTag.Set(PersianPOSTag.PRO);
			posTag = posTag.Clear(PersianPOSTag.UserPOS);
		}

		if (suffixCategory.Has(PersianSuffixesCategory.PluralSignAan) || suffixCategory.Has(PersianSuffixesCategory.PluralSignHaa))
		{
			//posTag = posTag.Clear(PersianPOSTag.AJ);
			//posTag = posTag.Set(PersianPOSTag.CL);
			posTag = posTag.Set(PersianPOSTag.N);
			//posTag = posTag.Set(PersianPOSTag.PRO);
			posTag = posTag.Clear(PersianPOSTag.UserPOS);
		}

		if (suffixCategory.Has(PersianSuffixesCategory.OrdinalEnumerableAdjective))
		{
			posTag = posTag.Set(PersianPOSTag.NUM);
			posTag = posTag.Set(PersianPOSTag.N);
			posTag = posTag.Clear(PersianPOSTag.UserPOS);
		}

		if (suffixCategory.Has(PersianSuffixesCategory.ComparativeAdjectives))
		{
			posTag = posTag.Set(PersianPOSTag.AJ);
			posTag = posTag.Clear(PersianPOSTag.UserPOS);
		}

		return posTag;
	}

	/** 
	 Determine the state of ending with consonant or vowel letter
	 
	 @param word Inflected Word
	 @param suffix Suffix
	 @param stem Stem
	 @param suffixCategory Suffix Category
	 @return 
	*/
	public static PersianPOSTag ConsonantVowelState(String word, String suffix, String stem, PersianSuffixesCategory suffixCategory)
	{
		if (!stem.endsWith("ه") && !stem.endsWith("و"))
		{
			return PersianPOSTag.forValue(0);
		}

		if (suffixCategory.Is(PersianSuffixesCategory.ComparativeAdjectives) || suffixCategory.Is(PersianSuffixesCategory.OrdinalEnumerableAdjective) || suffixCategory.Is(PersianSuffixesCategory.PluralSignHaa))
		{
			return PersianPOSTag.forValue(0);
		}

		if (stem.endsWith("ه"))
		{
			if (PhoneticCompositinRulesForHeh(stem, suffix, PersianPOSTag.VowelEnding))
			{
				if (word.equals((stem + PseudoSpace.ZWNJ + suffix)) || PersianSuffixes.PluralSignAanPermutedForHaa[0].equals(suffix))
				{
					return PersianPOSTag.VowelEnding;
				}
			}
			if (PhoneticCompositinRulesForHeh(stem, suffix, PersianPOSTag.ConsonantalEnding))
			{
				if (word.equals((stem + suffix)))
				{
					return PersianPOSTag.ConsonantalEnding;
				}
			}
		}
		else if (stem.endsWith("و"))
		{
			if (!suffixCategory.Has(PersianSuffixesCategory.YaaBadalAzKasre))
			{
				if (PhoneticCompositinRulesForVav(stem, suffix, PersianPOSTag.VowelEnding))
				{
					return PersianPOSTag.VowelEnding;
				}
				else if (PhoneticCompositinRulesForVav(stem, suffix, PersianPOSTag.ConsonantalEnding))
				{
					return PersianPOSTag.ConsonantalEnding;
				}
			}
		}


		return PersianPOSTag.forValue(0);
	}


	private static PersianCombinationSpacingState SpacingStateForYaa(String stem, String suffix)
	{
		if (stem.endsWith("ی"))
		{
			if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForHaaYaa)) // Ye nakareh
			{
				return PersianCombinationSpacingState.PseudoSpace;
			}
			if (ArraysUtility.indexOf(PersianSuffixes.ObjectivePronounsPermutedForHaaYaa, suffix) > -1)
			{
				return PersianCombinationSpacingState.PseudoSpace;
			}
			else if (ArraysUtility.indexOf(PersianSuffixes.ToBeVerbsPermutedForHaaYaa, suffix) > -1)
			{
				return PersianCombinationSpacingState.PseudoSpace;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
			{
				return PersianCombinationSpacingState.PseudoSpace;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForHaaYaa)) // yaa nesbat
			{
				return PersianCombinationSpacingState.PseudoSpace;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
			{
				return PersianCombinationSpacingState.PseudoSpace;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
			{
				return PersianCombinationSpacingState.Continous;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
			{
				return PersianCombinationSpacingState.PseudoSpace;
			}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveAmbigus))
			//{
			//    return PersianCombinationSpacingState.PseudoSpace;
			//}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveOrdinal))
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
		}

		return PersianCombinationSpacingState.WhiteSpace;
	}

	private static PersianCombinationSpacingState SpacingStateForHeh(String stem, String suffix, PersianPOSTag pos)
	{
		if (stem.endsWith("ه"))
		{
			if (pos.Has(PersianPOSTag.VowelEnding))
			{
				//if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.YaaBadalAzKasre)) // ye badal az kasre ezafe
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}
				//else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForHaaYaa)) // ye nakare
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsPermutedForHaaYaa))
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}
				//else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsPermutedForHaaYaa))
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForHaaYaa)) // yaa nesbat
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanPermutedForHaa) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveAmbigus))
				//{
				//    return PersianCombinationSpacingState.Continous;
				//}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}

				/*
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveAmbigus))
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveOrdinal))
				//{
				//    return PersianCombinationSpacingState.Continous;
				//}
				*/

				return PersianCombinationSpacingState.PseudoSpace;
			}
			else
			{
				if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaBase)) // ye nakare
				{
					return PersianCombinationSpacingState.Continous;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsBase) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives) && !suffix.startsWith(PersianSuffixes.EnumerableAdjectiveOrdinal[0]))
				{
					return PersianCombinationSpacingState.Continous;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsBase))
				{
					return PersianCombinationSpacingState.Continous;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					return PersianCombinationSpacingState.PseudoSpace;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatBase)) // yaa nesbat
				{
					return PersianCombinationSpacingState.Continous;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
				{
					return PersianCombinationSpacingState.Continous;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
				{
					return PersianCombinationSpacingState.PseudoSpace;
				}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveAmbigus))
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveOrdinal))
				//{
				//    return PersianCombinationSpacingState.Continous;
				//}
			}
		}

		return PersianCombinationSpacingState.WhiteSpace;
	}

	private static PersianCombinationSpacingState SpacingStateForAlef(String stem, String suffix)
	{
		if (stem.endsWith("ا"))
		{
			//if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.YaaBadalAzKasre)) // yaa badal az kasre ezafe
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			//else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForAlef)) // yaa nakare
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsPermutedForAlef))
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			//else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsPermutedForAlef))
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanPermutedForAlef))
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForAlef)) // yaa nesbat
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}

			/*
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveAmbigus))
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveOrdinal))
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			 */

			return PersianCombinationSpacingState.Continous;
		}

		return PersianCombinationSpacingState.WhiteSpace;
	}

	private static PersianCombinationSpacingState SpacingStateForConsonants(String stem, String suffix)
	{
		if (Helper.StringExtensions.EndsWith(stem,Helper.StringExtensions.ToStringArray(PersianAlphabets.getConsonants())))
		{
			if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaBase)) // ye nakare
			{
				return PersianCombinationSpacingState.Continous;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsBase) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives) && !suffix.startsWith(PersianSuffixes.EnumerableAdjectiveOrdinal[0]))
			{
				return PersianCombinationSpacingState.Continous;
			}
			else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsBase))
			{
				return PersianCombinationSpacingState.Continous;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
			{
				return PersianCombinationSpacingState.Continous;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatBase))
			{
				return PersianCombinationSpacingState.Continous;
			}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveOrdinal))
			//{
			//    return PersianCombinationSpacingState.Continous;
			//}
			else if (Helper.StringExtensions.EndsWith(stem,Helper.StringExtensions.ToStringArray(PersianAlphabets.getConsonantsNonStickers())))
			{
				if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					return PersianCombinationSpacingState.Continous;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
				{
					return PersianCombinationSpacingState.Continous;
				}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveAmbigus))
				//{
				//    return PersianCombinationSpacingState.Continous;
				//}
			}
			else if (Helper.StringExtensions.EndsWith(stem,Helper.StringExtensions.ToStringArray(PersianAlphabets.getConsonantsStickers())))
			{
				if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					return PersianCombinationSpacingState.PseudoSpace;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
				{
					return PersianCombinationSpacingState.PseudoSpace;
				}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveAmbigus))
				//{
				//    return PersianCombinationSpacingState.PseudoSpace;
				//}
			}
		}

		return PersianCombinationSpacingState.WhiteSpace;
	}

	private static boolean PhoneticCompositinRulesForYaa(String stem, String suffix)
	{
		if (stem.endsWith("ی"))
		{
			if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForHaaYaa)) // Ye nakareh
			{
				return true;
			}
			if (ArraysUtility.indexOf(PersianSuffixes.ObjectivePronounsPermutedForHaaYaa, suffix) > -1)
			{
				return true;
			}
			else if (ArraysUtility.indexOf(PersianSuffixes.ToBeVerbsPermutedForHaaYaa, suffix) > -1)
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForHaaYaa) && PhoneticCompositinRulesForYaa(suffix.substring(0, 2), (suffix.substring(0, 0) + suffix.substring(0 + 2)).replace((new Character(PseudoSpace.ZWNJ)).toString(), ""))) // yaa nesbat
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
			{
				return true;
			}
		}
		return false;
	}
	private static boolean PhoneticCompositinRulesForYaa(String stem, String suffix, Helper.RefObject<PersianSuffixesCategory> suffixCategory)
	{
		
		if (stem.endsWith("ی"))
		{
			if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForHaaYaa)) // Ye nakareh
			{
				suffixCategory.argvalue = PersianSuffixesCategory.IndefiniteYaa;
				return true;
			}
			if (ArraysUtility.indexOf(PersianSuffixes.ObjectivePronounsPermutedForHaaYaa, suffix) > -1)
			{
				suffixCategory.argvalue = PersianSuffixesCategory.ObjectivePronoun;
				return true;
			}
			else if (ArraysUtility.indexOf(PersianSuffixes.ToBeVerbsPermutedForHaaYaa, suffix) > -1)
			{
				suffixCategory.argvalue = PersianSuffixesCategory.ToBeVerb;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.PluralSignHaa;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForHaaYaa) && PhoneticCompositinRulesForYaa(suffix.substring(0, 2), (suffix.substring(0, 0) + suffix.substring(0 + 2)).replace((new Character(PseudoSpace.ZWNJ)).toString(), ""))) // yaa nesbat
			{
				suffixCategory.argvalue = PersianSuffixesCategory.YaaNesbat;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
			{
				suffixCategory.argvalue = PersianSuffixesCategory.ComparativeAdjectives;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.PluralSignAan;
				return true;
			}
		}

		suffixCategory.argvalue = PersianSuffixesCategory.forValue(0);
		return false;
	}

	private static boolean PhoneticCompositinRulesForHeh(String stem, String suffix)
	{
		if (stem.endsWith("ه"))
		{
			if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.YaaBadalAzKasre)) // ye badal az kasre ezafe
			{
				return true;
			}
			else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForHaaYaa) || Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaBase)) // ye nakare
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsPermutedForHaaYaa) || (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsBase) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives) && !suffix.startsWith(PersianSuffixes.EnumerableAdjectiveOrdinal[0])))
			{
				return true;
			}
			else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsPermutedForHaaYaa) || Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsBase))
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
			{
				return true;
			}
			else if ((Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForHaaYaa) && PhoneticCompositinRulesForYaa(suffix.substring(0, 2), (suffix.substring(0, 0) + suffix.substring(0 + 2)).replace((new Character(PseudoSpace.ZWNJ)).toString(), ""))) || (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatBase) && PhoneticCompositinRulesForYaa(suffix.substring(0, 1), (suffix.substring(0, 0) + suffix.substring(0 + 1)).replace((new Character(PseudoSpace.ZWNJ)).toString(), ""))))
				// yaa nesbat
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
			{
				return true;
			}
			else if ((Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanPermutedForHaa) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveAmbigus)) || Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
			{
				return true;
			}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjective))
			//{
			//    return true;
			//}
		}

		return false;
	}
	private static boolean PhoneticCompositinRulesForHeh(String stem, String suffix, PersianPOSTag pos)
	{
		if (stem.endsWith("ه"))
		{
			if (pos.Has(PersianPOSTag.VowelEnding))
			{
				if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.YaaBadalAzKasre)) // ye badal az kasre ezafe
				{
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForHaaYaa)) // ye nakare
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsPermutedForHaaYaa))
				{
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsPermutedForHaaYaa))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForHaaYaa) && PhoneticCompositinRulesForYaa(suffix.substring(0, 2), (suffix.substring(0, 0) + suffix.substring(0 + 2)).replace((new Character(PseudoSpace.ZWNJ)).toString(), "")))
				// yaa nesbat
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanPermutedForHaa) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveAmbigus))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
				{
					return true;
				}
			}
			else
			{
				if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaBase)) // ye nakare
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsBase) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives) && !suffix.startsWith(PersianSuffixes.EnumerableAdjectiveOrdinal[0]))
				{
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsBase))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatBase) && PhoneticCompositinRulesForYaa(suffix.substring(0, 1), (suffix.substring(0, 0) + suffix.substring(0 + 1)).replace((new Character(PseudoSpace.ZWNJ)).toString(), "")))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
				{
					return true;
				}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjective))
				//{
				//    return true;
				//}
			}
		}

		return false;
	}
	private static boolean PhoneticCompositinRulesForHeh(String stem, String suffix, PersianPOSTag pos, Helper.RefObject<PersianSuffixesCategory> suffixCategory)
	{
		if (stem.endsWith("ه"))
		{
			if (pos.Has(PersianPOSTag.VowelEnding))
			{
				if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.YaaBadalAzKasre)) // ye badal az kasre ezafe
				{
					suffixCategory.argvalue = PersianSuffixesCategory.YaaBadalAzKasre;
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForHaaYaa)) // ye nakare
				{
					suffixCategory.argvalue = PersianSuffixesCategory.IndefiniteYaa;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsPermutedForHaaYaa))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ObjectivePronoun;
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsPermutedForHaaYaa))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ToBeVerb;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.PluralSignHaa;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForHaaYaa) && PhoneticCompositinRulesForYaa(suffix.substring(0, 2), (suffix.substring(0, 0) + suffix.substring(0 + 2)).replace((new Character(PseudoSpace.ZWNJ)).toString(), "")))
				// yaa nesbat
				{
					suffixCategory.argvalue = PersianSuffixesCategory.YaaNesbat;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanPermutedForHaa) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjectiveAmbigus))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.PluralSignAan;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ComparativeAdjectives;
					return true;
				}
			}
			else
			{
				if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaBase)) // ye nakare
				{
					suffixCategory.argvalue = PersianSuffixesCategory.IndefiniteYaa;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsBase) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives) && !suffix.startsWith(PersianSuffixes.EnumerableAdjectiveOrdinal[0]))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ObjectivePronoun;
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsBase))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ToBeVerb;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.PluralSignHaa;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatBase) && PhoneticCompositinRulesForYaa(suffix.substring(0, 1), (suffix.substring(0, 0) + suffix.substring(0 + 1)).replace((new Character(PseudoSpace.ZWNJ)).toString(), "")))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.YaaNesbat;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ComparativeAdjectives;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.PluralSignAan;
					return true;
				}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjective))
				//{
				//    suffixCategory = PersianSuffixesCategory.OrdinalEnumerableAdjective;
				//    return true;
				//}
			}
		}

		suffixCategory.argvalue = PersianSuffixesCategory.forValue(0);
		return false;
	}

	private static boolean PhoneticCompositinRulesForAlef(String stem, String suffix)
	{
		if (stem.endsWith("ا"))
		{
			if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.YaaBadalAzKasre)) // yaa badal az kasre ezafe
			{
				return true;
			}
			else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForAlef)) // yaa nakare
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsPermutedForAlef))
			{
				return true;
			}
			else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsPermutedForAlef))
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanPermutedForAlef))
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForAlef) && PhoneticCompositinRulesForYaa(suffix.substring(0, 2), (suffix.substring(0, 0) + suffix.substring(0 + 2)).replace((new Character(PseudoSpace.ZWNJ)).toString(), ""))) // yaa nesbat
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
			{
				return true;
			}
		}
		return false;
	}
	private static boolean PhoneticCompositinRulesForAlef(String stem, String suffix, Helper.RefObject<PersianSuffixesCategory> suffixCategory)
	{
		if (stem.endsWith("ا"))
		{
			if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.YaaBadalAzKasre)) // yaa badal az kasre ezafe
			{
				suffixCategory.argvalue = PersianSuffixesCategory.YaaBadalAzKasre;
				return true;
			}
			else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForAlef)) // yaa nakare
			{
				suffixCategory.argvalue = PersianSuffixesCategory.IndefiniteYaa;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsPermutedForAlef))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.ObjectivePronoun;
				return true;
			}
			else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsPermutedForAlef))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.ToBeVerb;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.PluralSignHaa;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanPermutedForAlef))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.PluralSignAan;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForAlef) && PhoneticCompositinRulesForYaa(suffix.substring(0, 2), (suffix.substring(0, 0) + suffix.substring(0 + 2)).replace((new Character(PseudoSpace.ZWNJ)).toString(), ""))) // yaa nesbat
			{
				suffixCategory.argvalue = PersianSuffixesCategory.YaaNesbat;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
			{
				suffixCategory.argvalue = PersianSuffixesCategory.ComparativeAdjectives;
				return true;
			}
		}

		suffixCategory.argvalue = PersianSuffixesCategory.forValue(0);
		return false;
	}

	private static boolean PhoneticCompositinRulesForVav(String stem, String suffix, PersianPOSTag pos)
	{
		if (stem.endsWith("و"))
		{
			if (pos.Has(PersianPOSTag.VowelEnding))
			{

				if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.YaaBadalAzKasre)) // yaa badal az kasre ezafe
				{
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForAlef)) // yaa nakare
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsPermutedForAlef))
				{
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsPermutedForAlef))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanPermutedForAlef))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForAlef) && PhoneticCompositinRulesForYaa(suffix.substring(0, 2), (suffix.substring(0, 0) + suffix.substring(0 + 2)).replace((new Character(PseudoSpace.ZWNJ)).toString(), "")))
					// yaa nesbat
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
				{
					return true;
				}
			}
			else
			{
				if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaBase)) // ye nakare
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsBase) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives) && !suffix.startsWith(PersianSuffixes.EnumerableAdjectiveOrdinal[0]))
				{
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsBase))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatBase) && PhoneticCompositinRulesForYaa(suffix.substring(0, 1), (suffix.substring(0, 0) + suffix.substring(0 + 1)).replace((new Character(PseudoSpace.ZWNJ)).toString(), "")))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
				{
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
				{
					return true;
				}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjective))
				//{
				//    return true;
				//}
			}
		}

		return false;
	}
	private static boolean PhoneticCompositinRulesForVav(String stem, String suffix, PersianPOSTag pos, Helper.RefObject<PersianSuffixesCategory> suffixCategory)
	{
		if (stem.endsWith("و"))
		{
			if (pos.Has(PersianPOSTag.VowelEnding))
			{

				if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.YaaBadalAzKasre)) // yaa badal az kasre ezafe
				{
					suffixCategory.argvalue = PersianSuffixesCategory.YaaBadalAzKasre;
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaPermutedForAlef)) // yaa nakare
				{
					suffixCategory.argvalue = PersianSuffixesCategory.IndefiniteYaa;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsPermutedForAlef))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ObjectivePronoun;
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsPermutedForAlef))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ToBeVerb;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.PluralSignHaa;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanPermutedForAlef))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.PluralSignAan;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatPermutedForAlef) && PhoneticCompositinRulesForYaa(suffix.substring(0, 2), (suffix.substring(0, 0) + suffix.substring(0 + 2)).replace((new Character(PseudoSpace.ZWNJ)).toString(), "")))
				// yaa nesbat
				{
					suffixCategory.argvalue = PersianSuffixesCategory.YaaNesbat;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives)) // sefate tafsili
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ComparativeAdjectives;
					return true;
				}
			}
			else
			{
				if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaBase)) // ye nakare
				{
					suffixCategory.argvalue = PersianSuffixesCategory.IndefiniteYaa;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsBase) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives) && !suffix.startsWith(PersianSuffixes.EnumerableAdjectiveOrdinal[0]))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ObjectivePronoun;
					return true;
				}
				else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsBase))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ToBeVerb;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.PluralSignHaa;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatBase) && PhoneticCompositinRulesForYaa(suffix.substring(0, 1), (suffix.substring(0, 0) + suffix.substring(0 + 1)).replace((new Character(PseudoSpace.ZWNJ)).toString(), "")))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.YaaNesbat;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.PluralSignAan;
					return true;
				}
				else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
				{
					suffixCategory.argvalue = PersianSuffixesCategory.ComparativeAdjectives;
					return true;
				}
				//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjective))
				//{
				//    suffixCategory = PersianSuffixesCategory.OrdinalEnumerableAdjective;
				//    return true;
				//}
			}
		}

		suffixCategory.argvalue = PersianSuffixesCategory.forValue(0);
		return false;
	}


	private static boolean PhoneticCompositinRulesForConsonants(String stem, String suffix)
	{
		if (Helper.StringExtensions.EndsWith(stem,Helper.StringExtensions.ToStringArray(PersianAlphabets.getConsonants())))
		{
			if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaBase)) // ye nakare
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsBase) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives) && !suffix.startsWith(PersianSuffixes.EnumerableAdjectiveOrdinal[0]))
			{
				return true;
			}
			else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsBase))
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatBase) && PhoneticCompositinRulesForYaa(suffix.substring(0, 1), (suffix.substring(0, 0) + suffix.substring(0 + 1)).replace((new Character(PseudoSpace.ZWNJ)).toString(), "")))
			{
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
			{
				return true;
			}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjective))
			//{
			//    return true;
			//}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
			{
				return true;
			}

		}

		return false;
	}
	private static boolean PhoneticCompositinRulesForConsonants(String stem, String suffix, Helper.RefObject<PersianSuffixesCategory> suffixCategory)
	{
		if (Helper.StringExtensions.EndsWith(stem,Helper.StringExtensions.ToStringArray(PersianAlphabets.getConsonants())))
		{
			if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.IndefiniteYaaBase)) // ye nakare
			{
				suffixCategory.argvalue = PersianSuffixesCategory.IndefiniteYaa;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ObjectivePronounsBase) && !Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives) && !suffix.startsWith(PersianSuffixes.EnumerableAdjectiveOrdinal[0]))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.ObjectivePronoun;
				return true;
			}
			else if (Helper.StringExtensions.IsIn(suffix,PersianSuffixes.ToBeVerbsBase))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.ToBeVerb;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignAanBase))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.PluralSignAan;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.YaaNesbatBase) && PhoneticCompositinRulesForYaa(suffix.substring(0, 1), (suffix.substring(0, 0) + suffix.substring(0 + 1)).replace((new Character(PseudoSpace.ZWNJ)).toString(), "")))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.YaaNesbat;
				return true;
			}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.PluralSignHaa))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.PluralSignHaa;
				return true;
			}
			//else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.EnumerableAdjective))
			//{
			//    suffixCategory = PersianSuffixesCategory.OrdinalEnumerableAdjective;
			//    return true;
			//}
			else if (Helper.StringExtensions.StartsWith(suffix,PersianSuffixes.ComparativeAdjectives))
			{
				suffixCategory.argvalue = PersianSuffixesCategory.ComparativeAdjectives;
				return true;
			}

		}

		suffixCategory.argvalue = PersianSuffixesCategory.forValue(0);
		return false;
	}


	private static String ModifySuffixForAlef(String suffix, PersianSuffixesCategory category)
	{
		if (category.Has(PersianSuffixesCategory.IndefiniteYaa))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.IndefiniteYaaPermutedForAlef, PersianSuffixes.IndefiniteYaaBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.IndefiniteYaaPermutedForAlef, PersianSuffixes.IndefiniteYaaPermutedForHaaYaa);
		}
		else if (category.Has(PersianSuffixesCategory.YaaNesbat))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.YaaNesbatPermutedForAlef, PersianSuffixes.YaaNesbatBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.YaaNesbatPermutedForAlef, PersianSuffixes.YaaNesbatPermutedForHaaYaa);
		}
		else if (category.Has(PersianSuffixesCategory.ObjectivePronoun))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ObjectivePronounsPermutedForAlef, PersianSuffixes.ObjectivePronounsBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ObjectivePronounsPermutedForAlef, PersianSuffixes.ObjectivePronounsPermutedForHaaYaa);
		}
		else if (category.Has(PersianSuffixesCategory.ToBeVerb))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ToBeVerbsPermutedForAlef, PersianSuffixes.ToBeVerbsBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ToBeVerbsPermutedForAlef, PersianSuffixes.ToBeVerbsPermutedForHaaYaa);
		}
		else if (category.Has(PersianSuffixesCategory.PluralSignAan))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.PluralSignAanPermutedForAlef, PersianSuffixes.PluralSignAanBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.PluralSignAanPermutedForAlef, PersianSuffixes.PluralSignAanPermutedForHaa);
		}

		return suffix;
	}
	private static String ModifySuffixForYaa(String suffix, PersianSuffixesCategory category)
	{
		if (category.Has(PersianSuffixesCategory.IndefiniteYaa))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.IndefiniteYaaPermutedForHaaYaa, PersianSuffixes.IndefiniteYaaBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.IndefiniteYaaPermutedForHaaYaa, PersianSuffixes.IndefiniteYaaPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.YaaNesbat))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.YaaNesbatPermutedForHaaYaa, PersianSuffixes.YaaNesbatBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.YaaNesbatPermutedForHaaYaa, PersianSuffixes.YaaNesbatPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.ObjectivePronoun))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ObjectivePronounsPermutedForHaaYaa, PersianSuffixes.ObjectivePronounsBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ObjectivePronounsPermutedForHaaYaa, PersianSuffixes.ObjectivePronounsPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.ToBeVerb))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ToBeVerbsPermutedForHaaYaa, PersianSuffixes.ToBeVerbsBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ToBeVerbsPermutedForHaaYaa, PersianSuffixes.ToBeVerbsPermutedForAlef);
		}

		return suffix;
	}
	private static String ModifySuffixForHeh(String suffix, PersianSuffixesCategory category)
	{
		if (category.Has(PersianSuffixesCategory.IndefiniteYaa))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.IndefiniteYaaPermutedForHaaYaa, PersianSuffixes.IndefiniteYaaBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.IndefiniteYaaPermutedForHaaYaa, PersianSuffixes.IndefiniteYaaPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.YaaNesbat))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.YaaNesbatPermutedForHaaYaa, PersianSuffixes.YaaNesbatBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.YaaNesbatPermutedForHaaYaa, PersianSuffixes.YaaNesbatPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.ObjectivePronoun))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ObjectivePronounsPermutedForHaaYaa, PersianSuffixes.ObjectivePronounsBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ObjectivePronounsPermutedForHaaYaa, PersianSuffixes.ObjectivePronounsPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.ToBeVerb))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ToBeVerbsPermutedForHaaYaa, PersianSuffixes.ToBeVerbsBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ToBeVerbsPermutedForHaaYaa, PersianSuffixes.ToBeVerbsPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.PluralSignAan))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.PluralSignAanPermutedForHaa, PersianSuffixes.PluralSignAanBase);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.PluralSignAanPermutedForHaa, PersianSuffixes.PluralSignAanPermutedForAlef);
		}

		return suffix;
	}
	private static String ModifySuffixForConsonant(String suffix, PersianSuffixesCategory category)
	{
		if (category.Has(PersianSuffixesCategory.IndefiniteYaa))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.IndefiniteYaaBase, PersianSuffixes.IndefiniteYaaPermutedForHaaYaa);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.IndefiniteYaaBase, PersianSuffixes.IndefiniteYaaPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.YaaNesbat))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.YaaNesbatBase, PersianSuffixes.YaaNesbatPermutedForHaaYaa);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.YaaNesbatBase, PersianSuffixes.YaaNesbatPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.ObjectivePronoun))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ObjectivePronounsBase, PersianSuffixes.ObjectivePronounsPermutedForHaaYaa);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ObjectivePronounsBase, PersianSuffixes.ObjectivePronounsPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.ToBeVerb))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ToBeVerbsBase, PersianSuffixes.ToBeVerbsPermutedForHaaYaa);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.ToBeVerbsBase, PersianSuffixes.ToBeVerbsPermutedForAlef);
		}
		else if (category.Has(PersianSuffixesCategory.PluralSignAan))
		{
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.PluralSignAanBase, PersianSuffixes.PluralSignAanPermutedForHaa);
			suffix = ReplaceWrongSuffixWithCorrectEquivallent(suffix, PersianSuffixes.PluralSignAanBase, PersianSuffixes.PluralSignAanPermutedForAlef);
		}

		return suffix;
	}

	private static String ReplaceWrongSuffixWithCorrectEquivallent(String suffix, String[] correcSuffixes, String[] wrongSuffixes)
	{
		if (Helper.StringExtensions.StartsWith(suffix,wrongSuffixes))
		{
			for (int i = 0; i < wrongSuffixes.length; ++i)
			{
				if (suffix.startsWith(wrongSuffixes[i]))
				{
					suffix = suffix.substring(0, 0) + suffix.substring(0 + wrongSuffixes[i].length());
					if (correcSuffixes.length > i)
					{
						suffix = correcSuffixes[i] + suffix;
					}
					break;
				}
			}
		}

		return suffix;
	}


}