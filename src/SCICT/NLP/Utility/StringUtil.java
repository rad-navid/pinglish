package SCICT.NLP.Utility;

import Helper.ArraysUtility;
import Helper.CharacterUtil;
import Helper.LinqSimulationArrayList;
import Helper.Regex;
import SCICT.NLP.Persian.*;
import SCICT.NLP.Persian.Constants.*;

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
 String Utility Class, with special focus on Persian and Arabaic characters.
*/
public class StringUtil
{
	/** 
	 A static reference to an instance of <see cref="PersianCharFilter"/> class.
	*/
	private static PersianCharFilter s_persianCharFilter;

	/** 
	 Initializes the <see cref="StringUtil"/> class.
	*/
	static
	{
		s_persianCharFilter = new PersianCharFilter();
	}

	/** 
	 Replaces all matches of the given regex pattern with the specified replacement pattern.
	 
	 @param str The string to search and replace in.
	 @param regex The regex pattern to be searched.
	 @param with The string (or pattern) to be replaced.
	 @return 
	*/
	public static String ReplaceAllRegex(String str, String regex, String with)
	{
		return ReplaceAllRegex(str, regex, with, false);
	}

	/** 
	 Replaces all matches of the given regex pattern with the specified replacement pattern.
	 
	 @param str The string to search and replace in.
	 @param regex The regex pattern to be searched.
	 @param with The string (or pattern) to be replaced.
	 @param ignoreCase Specifies whether the character casing should be ignored.
	 @return 
	*/
	public static String ReplaceAllRegex(String str, String regex, String with, boolean ignoreCase)
	{
		
		return Regex.ReplaceAllRegex(str, regex, with, ignoreCase);
	}

	/** 
	 Replaces the first instance of the found regex pattern with the specified replacement pattern.
	 
	 @param str The string to search and replace in.
	 @param regex The regex pattern to be searched.
	 @param with The string (or pattern) to be replaced.
	 @param ignoreCase Specifies whether the character casing should be ignored.
	 @return 
	*/
	public static String ReplaceFirstRegex(String str, String regex, String with)
	{
		return ReplaceFirstRegex(str, regex, with, false);
	}

	/** 
	 Replaces the first instance of the found regex pattern with the specified replacement pattern.
	 
	 @param str The string to search and replace in.
	 @param regex The regex pattern to be searched.
	 @param with The string (or pattern) to be replaced.
	 @param ignoreCase Specifies whether the character casing should be ignored.
	 @return 
	*/
	public static String ReplaceFirstRegex(String str, String regex, String with, boolean ignoreCase)
	{
		
		
		return Regex.ReplaceFirstRegex(str, regex, with, ignoreCase);
	}

	/** 
	 Replaces the last instance of the regex pattern found in the given string
	 with the specified replacement pattern.
	 
	 @param str The string to search and replace in.
	 @param regex The regex pattern to be searched.
	 @param with The string (or pattern) to be replaced.
	 @return 
	*/
	public static String ReplaceLastRegex(String str, String regex, String with)
	{
		
		return Regex.ReplaceLastRegex(str, regex, with);
	}

	/** 
	 Specified whether the given string matcheses the given regex pattern.
	 
	 @param str The string to search and replace in.
	 @param pattern The regex pattern to be searched.
	 @return 
	*/
	public static boolean MatchesRegex(String str, String pattern)
	{
		return MatchesRegex(str, pattern, false);
	}

	/** 
	 Specified whether the given string matcheses the given regex pattern.
	 
	 @param str The string to search and replace in.
	 @param pattern The regex pattern to be searched.
	 @param ignoreCase Specifies whether the character casing should be ignored.
	 @return 
	*/
	public static boolean MatchesRegex(String str, String pattern, boolean ignoreCase)
	{
		return Regex.MatchesRegex(str, pattern, ignoreCase);
	}


	/** 
	 Extracts the non arabic content. The return value includes none of the 
	 arabic characters in the input string.
	 
	 @param word The word
	*/
	public static String ExtractNonArabicContent(String word)
	{
		StringBuilder sb = new StringBuilder(word.length());

		for (int i = 0; i < word.length(); ++i)
		{
			if (IsInArabicWord(word.charAt(i)))
			{
				continue;
			}
			sb.append(word.charAt(i));
		}
		return sb.toString();
	}

	/** 
	 Determines whether is white space the specified character.
	 
	 @param ch The character.
	 @return 
	 	<c>true</c> if the specified character is white space; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsWhiteSpace(char ch)
	{
		// Code 2 is a Control character but is used by MS-Word for foot-notes
		// Code 1 is a Control character but is used by MS-Word for formulas
		if ((ch == '\u0002') || (ch == '\u0001'))
		{
			return false;
		}
		else
		{
			return Character.isWhitespace(ch) || Character.isISOControl(ch);
		}
	}

	/** 
	 Determines whether the specified string includes only white space characters.
	 
	 @param word The input string.
	 @return 
	 	<c>true</c> if the specified string contains only white space characters; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsWhiteSpace(String word)
	{
		int i;
		for (i = 0; i < word.length(); ++i)
		{
			if (!IsWhiteSpace(word.charAt(i)))
			{
				break;
			}
		}

		return i >= word.length();
	}

	/** 
	 Trims a string, by removing whitespace chars as well as control chars.
	 
	 @param word The word to trim.
	 @return 
	*/
	public static String TrimWithControlChars(String word)
	{
		return TrimStartWithControlChars(TrimEndWithControlChars(word));
	}

	/** 
	 Trims the start of the string, by removing whitespace chars as well as control chars.
	 
	 @param word The word to trim.
	 @return 
	*/
	private static String TrimStartWithControlChars(String word)
	{
		int len = word.length();
		int i;
		for (i = 0; i < len; ++i)
		{
			if (!(IsWhiteSpace(word.charAt(i))))
			{
				break;
			}
		}

		if (i < len) // i.e. breaked
		{
			return word.substring(i);
		}
		else
		{
			return word;
		}
	}

	/** 
	 Trims end of the string, by removing whitespace chars as well as control chars.
	 
	 @param word The word to trim.
	 @return 
	*/
	private static String TrimEndWithControlChars(String word)
	{
		int len = word.length();
		int i;
		for (i = len - 1; i >= 0; --i)
		{
			if (!(IsWhiteSpace(word.charAt(i))))
			{
				break;
			}
		}

		if (i >= 0) // i.e. breaked
		{
			return word.substring(0, i + 1);
		}
		else
		{
			return ""; // i.e. It was all made of white-spaces
		}
	}

	/** 
	 Trims a string only considering control chars.
	 i.e. it does not remove whitespace chars.
	 
	 @param word The word to trim.
	 @return 
	*/
	public static String TrimOnlyControlChars(String word)
	{
		return TrimStartOnlyControlChars(TrimEndOnlyControlChars(word));
	}

	/** 
	 Trims the start of a string only considering control chars.
	 i.e. it does not remove whitespace chars.
	 
	 @param word The word to trim.
	 @return 
	*/
	public static String TrimStartOnlyControlChars(String word)
	{
		int len = word.length();
		int i;
		for (i = 0; i < len; ++i)
		{
			if (!(!Character.isWhitespace(word.charAt(i)) && Character.isISOControl(word.charAt(i))))
			{
				break;
			}
		}

		if (i < len) // i.e. breaked
		{
			return word.substring(i);
		}
		else
		{
			return word;
		}
	}

	/** 
	 Trims the end of a string only considering control chars.
	 i.e. it does not remove whitespace chars.
	 
	 @param word The word to trim.
	 @return 
	*/
	public static String TrimEndOnlyControlChars(String word)
	{
		int len = word.length();
		int i;
		for (i = len - 1; i >= 0; --i)
		{
			if (!(!Character.isWhitespace(word.charAt(i)) && Character.isISOControl(word.charAt(i))))
			{
				break;
			}
		}

		if (i >= 0) // i.e. breaked
		{
			return word.substring(0, i + 1);
		}
		else
		{
			return ""; // i.e. It was all made of white-spaces
		}
	}

	/** 
	 Trims the beginning of the arabic word. It trims and removes leading white-spaces,
	 together with the half spaces.
	 TrimStart means Trim-Left in English (i.e. Left to Right) Context.
	 
	 @param word The input word
	 @return The input string that its beginning characters has been trimmed.
	*/
	public static String TrimStartArabicWord(String word)
	{
		int len = word.length();
		int i;
		for (i = 0; i < len; ++i)
		{
			if (!(IsWhiteSpace(word.charAt(i)) || IsHalfSpace(word.charAt(i)) || IsMidWordSpace(word.charAt(i))))
			{
				break;
			}
		}

		if (i < len) // i.e. breaked
		{
			return word.substring(i);
		}
		else
		{
			return word;
		}
	}

	/** 
	 Trims the end of an Arabic word. It trims and removes trailing white-spaces,
	 together with the half spaces.
	 TrimEnd means trim-right in English (i.e. Left to Right) context.
	 
	 @param word The word.
	 @return 
	*/
	public static String TrimEndArabicWord(String word)
	{
		int len = word.length();
		int i;
		for (i = len - 1; i >= 0; --i)
		{
			if (!(IsWhiteSpace(word.charAt(i)) || IsHalfSpace(word.charAt(i)) || IsMidWordSpace(word.charAt(i))))
			{
				break;
			}
		}

		if (i >= 0) // i.e. breaked
		{
			return word.substring(0, i + 1);
		}
		else
		{
			return ""; // i.e. It was all made of white-spaces
		}

	}

	/** 
	 Normalizes the spaces and half spaces in word. 
	 It trims the word, removes trailing and leading spaces and half-spaces,
	 and replaces multiple occurrences of half-spaces with only one half-space.
	 Also half-spaces right after Persian/Arabic separate characters are removed.
	 For example, half spaces after "Daal" are completely removed.
	 
	 @param word The word
	 @return The normalized copy of the input string.
	*/
	public static String NormalizeSpacesAndHalfSpacesInWord(String word)
	{
		// IMPORTANT NOTE: Any change in this function should be reflected also in 
		//   Utility.PersianContentReader.RangeUtils.GetRangeIndex
		// Any change here must be reflected there, and 
		//  any change there must be reflected here.

		int len = word.length();
		if (len <= 0)
		{
			return "";
		}
		else if (len == 1)
		{
			return word;
		}

		boolean isContentMet = false;
		StringBuilder sb = new StringBuilder();

		char ch0, ch1;
		ch0 = word.charAt(0);

		int i;
		for (i = 1; i < len; ch0 = ch1, ++i)
		{
			ch1 = word.charAt(i);
			if (isContentMet)
			{
				if (IsHalfSpace(ch0))
				{
					String contentSoFar = sb.toString();
					if (!((contentSoFar.length() > 0) &&
							(ArraysUtility.FindIndex(PersianAlphabets.NonStickerChars, contentSoFar) >= 0)))
					{
						if (!(IsWhiteSpace(ch1) || IsHalfSpace(ch1)))
						{
							sb.append(ch0);
						}
					}
				}
				else
				{
					sb.append(ch0);
				}
			}
			else // i.e. we are still in leading spaces area
			{
				if (!(IsWhiteSpace(ch0) || IsHalfSpace(ch0)))
				{
					isContentMet = true;
					sb.append(ch0);
				}
			}
		}

		if (!(IsWhiteSpace(ch0) || IsHalfSpace(ch0)))
		{
			sb.append(ch0);
		}

		return TrimEndArabicWord(sb.toString());
	}

	/** 
	 returns the number of words that can be counter until we reach the given index.
	 This can be done ignoring erabs (and mid-word spaces) or not.
	 If <code>includeErabs</code> is true, the counting occurs normally, otherwise it is 
	 assumed that we want to count in the refined version of the input string. In this case 
	 the input string might contain erabs and mid-word spaces, but we assume that <code>index</code>
	 is provided from a refined version of this string, so the method ignores erabs and mid-word
	 spaces while counting.
	 
	 @param exp input string
	 @param index if <code>includeErabs</code> is true index in the given string,
	 otherwise index in the refined version of the string
	 @param includeErabs if true counts erabs and mid-word spaces as characters, 
	 otherwise works as if erabs and mid-word spaces do not exist, index is also
	 passed to the function from a refined version of the string.
	 @return 
	*/
	public static int WordCountTillIndex(String exp, int index, boolean includeErabs)
	{
		final int LETTER = 0, DIGIT = 1, SPACE = 3, OTHER = 4; // e.g. punctuation
		int charState = SPACE;

		int wordCount = 0;

		int len = exp.length();
		if (index > len - 1)
		{
			index = len - 1;
		}

		int i = -1;
		char charArray[]=exp.toCharArray();
		for (char curChar : charArray)
		{
			//                curChar = exp[strIndex];

			if (!includeErabs && (IsErabSign(curChar) || IsMidWordSpace(curChar)))
			{
				continue;
			}

			if (IsWhiteSpace(curChar))
			{
				charState = SPACE;
			}
			else if (IsInArabicWord(curChar) || Character.isLetter(curChar))
			{
				if (charState != LETTER)
				{
					wordCount++;
				}
				charState = LETTER;
			}
			else if (Character.isDigit(curChar))
			{
				if (charState != DIGIT)
				{
					wordCount++;
				}
				charState = DIGIT;
			}
			else // e.g. on punctuations
			{
				wordCount++;
				charState = OTHER;
			}

			i++;
			if (i >= index)
			{
				break;
			}
		}

		return wordCount;
	}

	/** 
	 Returns the number of word in the expression in which or before which the index occurs
	 Since it is a count it can be regarded as a 1-based index.
	*/
	public static int WordCountTillIndex(String exp, int index)
	{
		return WordCountTillIndex(exp, index, true);
	}

	/** 
	 returns the start index of the nth word in the expression
	 if the expression contains less word than n then the function returns -1
	 by word we mean characters between two whitespaces. e.g. "[123]" is one word
	 and "[ 123]" is two words.
	 
	 @param exp The input string
	 @param n 0-based index of the word
	*/
	public static int WordStartIndex(String exp, int n)
	{
		if (n < 0)
		{
			return -1;
		}

		final int LETTER = 0, DIGIT = 1, SPACE = 3, OTHER = 4; // e.g. punctuation
		int charState = SPACE;

		int wordCount = -1;

		int index = 0;
		int len = exp.length();

		for (index = 0; index < len; ++index)
		{
			char curChar = exp.charAt(index);
			if (IsWhiteSpace(curChar))
			{
				charState = SPACE;
			}
			else if (IsInArabicWord(curChar) || Character.isLetter(curChar))
			{
				if (charState != LETTER)
				{
					wordCount++;
				}
				charState = LETTER;
			}
			else if (Character.isDigit(curChar))
			{
				if (charState != DIGIT)
				{
					wordCount++;
				}
				charState = DIGIT;
			}
			else // e.g. on punctuations
			{
				wordCount++;
				charState = OTHER;
			}

			if (wordCount >= n)
			{
				return index;
			}
		}

		return -1;
	}

	/** 
	 returns the end index of the nth word in the expression
	 if the expression contains less words than n then the function returns -1
	 by word we mean characters between two whitespaces. e.g. "[123]" is one word
	 and "[ 123]" is two words.
	 
	 @param exp The input string
	 @param n 0-based index of the word
	*/
	public static int WordEndIndex(String exp, int n)
	{
		if (n < 0)
		{
			return -1;
		}

		final int LETTER = 0, DIGIT = 1, SPACE = 3, OTHER = 4; // e.g. punctuation
		int charState = SPACE;

		int wordCount = -1;

		boolean foundNthWord = false;

		int index = 0;
		int len = exp.length();
		char curChar;

		for (index = 0; index < len; ++index)
		{
			curChar = exp.charAt(index);
			if (IsWhiteSpace(curChar))
			{
				charState = SPACE;
				if (foundNthWord)
				{
					return index - 1;
				}
			}
			else if (IsInArabicWord(curChar) || Character.isLetter(curChar))
			{
				if (charState != LETTER)
				{
					if (foundNthWord)
					{
						return index - 1;
					}
					wordCount++;
				}
				charState = LETTER;
			}
			else if (Character.isDigit(curChar))
			{
				if (charState != DIGIT)
				{
					if (foundNthWord)
					{
						return index - 1;
					}
					wordCount++;
				}
				charState = DIGIT;
			}
			else // e.g. on punctuations
			{
				if (foundNthWord)
				{
					return index - 1;
				}
				wordCount++;
				charState = OTHER;
			}

			if (wordCount >= n)
			{
				foundNthWord = true;
			}
		}

		if (foundNthWord)
		{
			return index - 1;
		}

		return -1;
	}

	/** 
	 Determines whether a character can be observed inside an Arabic word.
	 i.e. if it is Arabic Letter or Erab or Haf Space or Mid-word Space
	*/
	public static boolean IsInArabicWord(char ch)
	{
		return (IsArabicLetter(ch) || IsHalfSpace(ch) || IsMidWordSpace(ch) || IsErabSign(ch));
	}

	/** 
	 Determines whether the specified string is all consisting of arabic letters.
	 
	 @param str The string.
	 @return 
	 <c>true</c> if the specified string is all consisting of arabic letters; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsArabicWord(String str)
	{
		char[] charArray=str.toCharArray();
		for (char ch : charArray)
		{
			if (!IsInArabicWord(ch))
			{
				return false;
			}
		}
		return true;
	}

	/** 
	 Removes the middle word spaces. Middle word spaces are characters that happen 
	 in the middle of a word, but does not count as a word constructive character.
	 e.g. مــــــن vs. من
	 
	 @param word The input word
	 @return A copy of the input string with its mid-word-spaces removed.
	*/
	public static String RemoveMidWordSpace(String word)
	{
		StringBuilder sb = new StringBuilder(word.length());

		for (int i = 0; i < word.length(); ++i)
		{
			if (IsMidWordSpace(word.charAt(i)))
			{
				continue;
			}
			sb.append(word.charAt(i));
		}

		return sb.toString();
	}

	/** 
	 Determines whether the specified character can occur in the middle of a number.
	 This does not include digits. E.g. 'e' can happen in a scientific form number, or 
	 '.' and '/' are English and Persian/Arabic floating points respectively.
	 
	 @param ch The character
	 @return 
	 	<c>true</c> if the specified character can occur in the middle of a number; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsMidNumberChar(char ch)
	{
		return (ch == '+') || (ch == '-') || (ch == 'e') || (ch == 'E') || (ch == '.') || (ch == '/'); // for Arabic floating point
	}

	/** 
	 Removes the erab characters from the input string except tashdid and fathatan
	 
	 @param word The word to remove erab from
	 @return The copy of the input string with its erab removed
	*/
	public static String RemoveErab(String word)
	{
		StringBuilder sb = new StringBuilder(word.length());

		for (int i = 0; i < word.length(); ++i)
		{
			if (!IsErabSignExceptFathatan(word.charAt(i)))
			{
				sb.append(word.charAt(i));
			}
		}

		return sb.toString();
	}

	/** 
	 Removes the erab characters from the input string including tashdid and fathatan
	 
	 @param word The word to remove erab from
	 @return The copy of the input string with its erab removed
	*/
	public static String RemoveErabIncludingFathatan(String word)
	{
		StringBuilder sb = new StringBuilder(word.length());

		for (int i = 0; i < word.length(); ++i)
		{
			if (IsErabSign(word.charAt(i)))
			{
				continue;
			}
			sb.append(word.charAt(i));
		}

		return sb.toString();
	}


	/** 
	 Refines and filters Persian char. If the character is Erab or Mid-Word-Space it is removed.
	 If it is a non standard Persian character it is replaced with its standard equivalant char(s).
	 
	 @param ch The character
	 @return A string containing the standard equivalant of the input character; or an empty string
	 if the charactered is either erab or mid-word-space character.
	*/
	public static String RefineAndFilterPersianChar(char ch)
	{
		return RemoveErab(RemoveMidWordSpace(s_persianCharFilter.FilterChar(ch)));
	}

	/** 
	 Trims and normalizes spaces and half-spaces and removes both Erab and Mid-Spaces.
	 It does NOT apply Persian Char Filters.
	*/
	public static String RefinePersianWord(String word)
	{
		return RemoveErab(RemoveMidWordSpace(NormalizeSpacesAndHalfSpacesInWord(word)));
	}

	/** 
	 Trims and normalizes spaces and half-spaces and removes both Erab and Mid-Spaces
	 and applies Persian Char Filters.
	*/
	public static String RefineAndFilterPersianWord(String word)
	{
		return RemoveErab(RemoveMidWordSpace(NormalizeSpacesAndHalfSpacesInWord(s_persianCharFilter.FilterString(word))));
	}

	/** 
	 Filters the characters in the word, e.g. replaces non-standard Kaaf, and Yaa
	 and half-spaces with the standard version.
	 It does NOT remove erabs or mid-word-spaces.
	*/
	public static String FilterPersianWord(String word)
	{
		return s_persianCharFilter.FilterString(word);
	}

	/** 
	 Filters the persian word, ignoring some categories.
	 
	 @param word The word
	 @param ignoreCats The categories to IGNORE
	*/
	public static String FilterPersianWord(String word, FilteringCharacterCategory ignoreCats)
	{
		return s_persianCharFilter.FilterString(word, ignoreCats);
	}

	/** 
	 Filters the Persian word, ignoring a set of characters.
	 
	 @param word The word
	 @param ignoreList The set of characters to be ignored.
	*/
	public static String FilterPersianWord(String word, java.util.HashSet<Character> ignoreList)
	{
		return s_persianCharFilter.FilterString(word, ignoreList);
	}

	/** 
	 Filters the Persian word, ignoring a set of characters, and ignoring some categories.
	 
	 @param word The word
	 @param ignoreList The set of characters to be ignored.
	 @param ignoreCats The categories to IGNORE
	*/
	public static String FilterPersianWord(String word, java.util.HashSet<Character> ignoreList, FilteringCharacterCategory ignoreCats)
	{
		return s_persianCharFilter.FilterString(word, ignoreList, ignoreCats);
	}

	/** 
	 What would be the char index in the refined version of the string
	 
	 @param str The not refined string; string should be trimmed beforehand.
	 @param index index in the not refined string
	 @return corresponding index in the refined string
	*/
	public static int IndexInRefinedString(String str, int index)
	{
		int rindex = -1;
		for (int i = 0; i <= Math.min(index, str.length() - 1); ++i)
		{
			if (IsErabSign(str.charAt(i)) || IsMidWordSpace(str.charAt(i)))
			{
				continue;
			}
			rindex++;
		}
		return rindex;
	}

	/** 
	 Gets the char index in the original not-refined version of the refined string
	 
	 @param strNotRefined The NOT refined string; string should be trimmed beforehand.
	 @param indexInRefined index in the refined string
	 @return corresponding index in the not refined string
	*/
	public static int IndexInNotRefinedString(String strNotRefined, int indexInRefined)
	{
		int rindex = -1;
		int i;
		for (i = 0; i < strNotRefined.length(); ++i)
		{
			if (IsErabSign(strNotRefined.charAt(i)) || IsMidWordSpace(strNotRefined.charAt(i)))
			{
				continue;
			}

			rindex++;
			if (rindex >= indexInRefined)
			{
				break;
			}
		}
		return i;
	}

	/** 
	 Determines whether the specified character is an Arabic letter.
	 
	 @param ch The character
	 @return 
	 	<c>true</c> if the specified character is an Arabic letter; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsArabicLetter(char ch)
	{
		if (('\u0621' <= ch && ch <= '\u063A') || ('\u0641' <= ch && ch <= '\u064A') || ('\u066E' <= ch && ch <= '\u066F') || ('\u0671' <= ch && ch <= '\u06D3') || ('\u06FA' <= ch && ch <= '\u076D') || ('\uFB50' <= ch && ch <= '\uFBFF') || ('\uFDF2' <= ch && ch <= '\uFDFC') || ('\uFE80' <= ch && ch <= '\uFEFC'))
		{
			return true;
		}

		return false;
	}

	/** 
	 Determines whether the specified character is a half-space character.
	 
	 @param ch The character
	 @return 
	 	<c>true</c> if the specified character is a half-space character; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsHalfSpace(char ch)
	{
		if ((ch == '\u200B') || (ch == '\u200C') || (ch == '\u00AC') || (ch == '\u001F') || (ch == '\u200D') || (ch == '\u200E') || (ch == '\u200F'))
		{
			return true;
		}
		return false;
	}

	/** 
	 Determines whether the specified string is all made up of half-space characters.
	 
	 @param str The string to test.
	 @return 
	 	<c>true</c> if the specified string is all made up of half-space character; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsHalfSpace(String str)
	{
		char[] charArray=str.toCharArray();
		for (char ch : charArray)
		{
			if (!IsHalfSpace(ch))
			{
				return false;
			}
		}
		return true;
	}


	/** 
	 Determines whether the specified character, is mid-word-space.
	 
	 @param ch The ch.
	 @return 
	 	<c>true</c> if the specified character, is mid-word-space; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsMidWordSpace(char ch)
	{
		if ((ch == '\u0640') || ('\uFE20' <= ch && ch <= '\uFE23') || (ch == '\u200D'))
		{
			return true;
		}

		return false;
	}

	/** 
	 Determines whether the specified character is an Arabic or Persian digit.
	 
	 @param ch The character
	 @return 
	 	<c>true</c> if the specified character is an Arabic or Persian digit; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsArabicDigit(char ch)
	{
		if (('\u0660' <= ch && ch <= '\u0669') || ('\u06F0' <= ch && ch <= '\u06F9'))
		{
			return true;
		}

		return false;
	}

	/** 
	 Determines whether the specified character is Arabic punctuation.
	 
	 @param ch The ch.
	 @return 
	 	<c>true</c> if the specified character is Arabic punctuation; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsArabicPunctuation(char ch)
	{
		// TODO: check English common punctuation signs
		if (('\u066A' <= ch && ch <= '\u066D') || (ch == '\u06D4') || (ch == '\uFD3E') || (ch == '\uFD3F') || (ch == '\u061F'))
		{
			return true;
		}
		return false;
	}

	/** 
	 Determines whether the specified character is an MS-Word paragraph delimiter.
	 
	 @param ch The character
	 @return 
	 	<c>true</c> if the specified character is an MS-Word paragraph delimiter; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsParagraphDelimiter(char ch)
	{
		if (ch == '\r' || ch == '\n')
		{
			return true;
		}
		return false;
	}

	/** 
	 Determines whether the specified character is sentence delimiter.
	 
	 @param ch The character
	 @return 
	 	<c>true</c> if the specified character is sentence delimiter; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsSentenceDelimiter(char ch)
	{
		final String sentenceDelimiters = ".؟?!\r\n";
		char[] charArray=sentenceDelimiters.toCharArray();
		for (char ch2 : charArray)
		{
			if (ch == ch2)
			{
				return true;
			}
		}
		return false;
	}

	/** 
	 Determines whether the specified character is an erab sign.
	 
	 @param ch The character
	 @return 
	 	<c>true</c> if the specified character is an erab sign; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsErabSign(char ch)
	{
		if (('\u0610' <= ch && ch <= '\u0615') || ('\u064B' <= ch && ch <= '\u065E') || ('\u06D6' <= ch && ch <= '\u06DC') || ('\u06DF' <= ch && ch <= '\u06E8') || ('\u06EA' <= ch && ch <= '\u06EF') || ('\uE820' <= ch && ch <= '\uE82D') || ('\uFC5E' <= ch && ch <= '\uFC62') || (ch == '\u0670') || (ch == '\uE818'))
		{
			return true;
		}
		return false;
	}

	public static boolean IsErabSignExceptFathatan(char ch)
	{
		if (ch == StandardCharacters.StandardFathatan) // ch == StandardCharacters.StandardTashdid ||
		{
			return false;
		}

		return IsErabSign(ch);
	}


	/** 
	 Determines whether the specified string starts 
	 with one of the characters in the second string.
	 
	 @param str The string to be processed
	 @param chars The string containing characters to be compared against
	*/
	public static boolean StringStartsWithOneOf(String str, String chars)
	{
		char[] charArray=chars.toCharArray();
		for (char ch : charArray)
		{
			if (str.startsWith("" + ch))
			{
				return true;
			}
		}
		return false;
	}

	/** 
	 Determines whether a string is a sentence delimiter.
	 
	 @param str The string.
	*/
	public static boolean StringIsASentenceDelim(String str)
	{
		return StringIsADelim(str, false);
	}

	/** 
	 Determines whether a string is a paragraph delimiter.
	 
	 @param str The string.
	*/
	public static boolean StringIsAParagraphDelim(String str)
	{
		return StringIsADelim(str, true);
	}

	/** 
	 Extracts the persian sentences.
	 Note that the sentences are neither trimmed nor normalized.
	 
	 @param text The text to extract sentences from.
	 @return 
	*/
	public static String[] ExtractPersianSentences(String text)
	{
		return PersianSentenceTokenizer.Tokenize(text);
	}

	/** 
	 Determines whether the specified character, is word delimiter.
	 
	 @param ch The character
	 @return 
	 	<c>true</c> if the specified character, is word delimiter; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsWordDelimiter(char ch)
	{
		return IsArabicDigit(ch) || IsArabicPunctuation(ch) || IsMidNumberChar(ch) ||
				IsWhiteSpace(ch) || Character.isISOControl(ch) || Character.isDigit(ch) ||
				CharacterUtil.IsPunctuation(ch) || CharacterUtil.IsSymbol(ch);
	}

	/** 
	 The base method that extracts the Persian words from a string of words.
	 
	 @param line The string of words.
	 @param useCharFilter if set to <c>true</c> uses Persian char 
	 filter to refine the extracted words.
	*/
	private static String[] ExtractPersianWordsBase(String line, boolean useCharFilter)
	{
		java.util.ArrayList<String> listWords = new java.util.ArrayList<String>();
		StringBuilder curWord = new StringBuilder();
		String wordToBeAdded;
		char[] charArray=line.toCharArray();
		for (char c : charArray)
		{
			if (IsWordDelimiter(c))
			{
				if (curWord.toString().length() > 0)
				{
					if (useCharFilter)
					{
						wordToBeAdded = RefineAndFilterPersianWord(curWord.toString());
					}
					else
					{
						wordToBeAdded = RefinePersianWord(curWord.toString());
					}


					listWords.add(wordToBeAdded);
					curWord = new StringBuilder();
				}
				continue;
			}

			if (IsInArabicWord(c))
			{
				curWord.append(c);
			}
		}

		if (curWord.toString().length() > 0)
		{
			if (useCharFilter)
			{
				wordToBeAdded = RefineAndFilterPersianWord(curWord.toString());
			}
			else
			{
				wordToBeAdded = RefinePersianWord(curWord.toString());
			}

			listWords.add(wordToBeAdded);
		}

		return listWords.toArray(new String[0]);
	}

	/** 
	 Extracts the Persian words, without applying Persian word filters to them.
	 
	 @param line The string of words.
	*/
	public static String[] ExtractPersianWords(String line)
	{
		return ExtractPersianWordsBase(line, false);
	}

	/** 
	 Extracts the Persian words, and applies Persian word filters to them.
	 
	 @param line The string of words.
	*/
	public static String[] ExtractPersianWordsStandardized(String line)
	{
		return ExtractPersianWordsBase(line, true);
	}

	/** 
	 Determines whether the specified string is a sentence or paragraph delimiter.
	 
	 @param str The string
	 @param isParagraph if set to <c>true</c> checks for the being paragraph, 
	 otherwise checks for being sentence.
	*/
	public static boolean StringIsADelim(String str, boolean isParagraph)
	{
		if (str.equals(""))
		{
			return false;
		}

		for (int i = str.length() - 1; i >= 0; --i)
		{
			if (isParagraph)
			{
				if (IsParagraphDelimiter(str.charAt(i)))
				{
					return true;
				}
			}
			else
			{
				if (IsSentenceDelimiter(str.charAt(i)))
				{
					return true;
				}
			}

			// if there's no chance of finding delims then break
			if (!Character.isWhitespace(str.charAt(i)) && !Character.isISOControl(str.charAt(i)))
			{
				break;
			}
		}
		return false;
	}

	/** 
	 Gets a String by concatenating codes from parameters.
	 
	 @param charCodes The integer char codes
	*/
	public static String StringFromCodes(Integer... charCodes)
	{
		StringBuilder sb = new StringBuilder(charCodes.length);
		for (int n : charCodes)
		{
			sb.append((char)n);
		}
		return sb.toString();
	}

	/** 
	 Checks whether the specified string contains any of the characters within the 
	 specified character array.
	 
	 @param str The string to check.
	 @param chars The character array to look for.
	 @param index The index of the found character or -1 if no such character is found.
	 @return 
	*/
	public static boolean StringContainsAny(String str, char[] chars, Helper.RefObject<Integer> index)
	{
		index.argvalue = -1;
		int tmpIndex = -1;
		for (char ch : chars)
		{
			tmpIndex = str.indexOf(ch);
			if (tmpIndex >= 0)
			{
				index.argvalue = tmpIndex;
				return true;
			}
		}

		return false;
	}

	/** 
	 Checks whether the specified string contains any of the characters within the 
	 specified character array.
	 
	 @param str The string to check.
	 @param chars The character array to look for.
	 @return 
	*/
	public static boolean StringContainsAny(String str, char[] chars)
	{
		int dummy = 0;
		Helper.RefObject<Integer> tempRef_dummy = new Helper.RefObject<Integer>(dummy);
		boolean tempVar = StringContainsAny(str, chars, tempRef_dummy);
		dummy = tempRef_dummy.argvalue;
		return tempVar;
	}

	//TODO: Do we need to "IsEnglishBlah" methods?

	/** 
	 Determines whether the specified character is an English character.
	 
	 @param ch The character
	 @return 
	 	<c>true</c> if the specified character is an English character; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsAnEnglishLetter(char ch)
	{
		int nCh = (int)ch;

		if (((int)('a') <= nCh && nCh <= (int)('z')) || ((int)('A') <= nCh && nCh <= (int)('Z')))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	/** 
	 The characters who (may) represent a whole word in pinglish.
	 
	 All characters are in lowercase.
	*/
	public static LinqSimulationArrayList<Character> OneLetterPinglishWords = new LinqSimulationArrayList<Character>(java.util.Arrays.asList(new Character[] {'2', '4', 'b', 'c', 'd', 'e', 'i', 'k', 'o', 'r', 'u', 'y', 'B', 'C', 'D', 'E', 'I', 'K', 'O', 'R', 'U', 'Y'}));

	/** 
	 Determines whether the specified word is a pinglish word.
	 
	 @param word The word.
	 @return 
	 	<c>true</c> if the specified word is pinglish word; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsPinglishWord(String word)
	{
		if (Helper.DotNetToJavaStringHelper.isNullOrEmpty(word))
		{
			return false;
		}

		// TODO: I think this would reduce the usability of pinglish converter, 
		// as there are many irrelevant 1 character words in documents
		if (word.length() == 1)
		{
			if (OneLetterPinglishWords.contains(word.charAt(0)))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			int result = 0;
			Helper.RefObject<Integer> tempRef_result = new Helper.RefObject<Integer>(result);
			try{
				tempRef_result.argvalue=Integer.parseInt(word);
				result = tempRef_result.argvalue;
				return false;
			}
			catch(Exception ex)
			{			}
			
		}

		char[] charArray=word.toCharArray();
		for (char ch : charArray)
		{
			if (!(IsAnEnglishLetter(ch) || Character.isDigit(ch) || IsSingleQuote(ch)))
			{
				return false;
			}
		}

		return true;
	}

	/** 
	 Determines whether the given character represents Single Quotation marks (or similar characters like 'Prime')
	 
	 @param ch
	 @return 
	*/
	public static boolean IsSingleQuote(char ch)
	{
		if (ch == QuotationMark.SingleQuotationMark || ch == QuotationMark.Prime || ch == QuotationMark.RightSingleQuotationMark || ch == QuotationMark.SingleHighReveresed9QuotationMark)
		{
			return true;
		}

		return false;
	}

	/** 
	 Extracts words from the specified string of words. This is a general word extraction method.
	 To extract words from Persian sentences specificaly call 
	 <c>ExtractPersianWords</c> and <c>ExtractPersianWordsStandardized</c>.
	 
	 @param line string of words to extract words from
	 @return 
	*/
	public static String[] ExtractWords(String line)
	{
		java.util.ArrayList<String> listWords = new java.util.ArrayList<String>();
		StringBuilder curWord = new StringBuilder();
		String wordToBeAdded;
		char[] charArray=line.toCharArray();
		for (char c : charArray)
		{
			if (IsHappeningInWord(c))
			{
				curWord.append(c);
			}
			else
			{
				if (curWord.toString().length() > 0)
				{
					wordToBeAdded = curWord.toString();

					listWords.add(ApplyWordBuildignRules(wordToBeAdded));
					curWord = new StringBuilder();
				}
				continue;
			}

		}

		if (curWord.toString().length() > 0)
		{
			wordToBeAdded = curWord.toString();
			listWords.add(ApplyWordBuildignRules(wordToBeAdded));
		}

		return listWords.toArray(new String[0]);
	}

	/** 
	 applies word building rules to words recognized by the ExtractWords algorithm, 
	 e.g. it removes dashes from the beginning of the words
	 
	 @param word the word to apply the rules to
	 @return 
	*/
	private static String ApplyWordBuildignRules(String word)
	{
		String result = Helper.DotNetToJavaStringHelper.trim(word, ' ', '\t', '\r', '\n', '-', QuotationMark.SingleQuotationMark, QuotationMark.Prime, QuotationMark.RightSingleQuotationMark, QuotationMark.SingleHighReveresed9QuotationMark);

		return result;
	}

	/** 
	 Determines whehter the specified character is allowed to occur inside a word
	 
	 @param c
	 @return 
	*/
	private static boolean IsHappeningInWord(char c)
	{
		return (Character.isLetterOrDigit(c) || IsSingleQuote(c) || c == '-');
	}



	//To DO
	 public final void StandardiseApostrophesAndStripLeading(Helper.RefObject<String> E)
	 {
		E.argvalue = E.argvalue.replace('’', '\'').replace('`', '\'').replace('"', '\'');
	 }

	/*/// <summary>
	/// Trims the word, if in a sentence works on the first word,
	/// determines the type of the word and returns the number of
	/// characters read.
	/// </summary>
	public static WordType TrimAndGetTypeOfWord(string word, out string outword)
	{
	    WordType type = WordType.SPACE;

	    StringBuilder sb = new StringBuilder(word.Length);
	    int index;
	    char c;
	    int length = word.Length;
	    for (index = 0; index < length; ++index)
	    {
	        c = word[index];

	        if (Char.IsWhiteSpace(c) || Char.IsControl(c))
	        {
	            // do nothing
	        }
	        else if (StringUtil.IsInArabicWord(c))
	        {
	            sb.Append(c);
	            if (type == WordType.SPACE || type == WordType.ARABIC_WORD)
	                type = WordType.ARABIC_WORD;
	            else
	                type = WordType.ILLEGAL;
	        }
	        else if (StringUtil.IsArabicPunctuation(c))
	        {
	            // FIXME: not sure about the Arabic-punctuation part
	            if (type == WordType.ARABIC_NUM || type == WordType.ENGLISH_NUM || type == WordType.ARABIC_PUNC)
	            {
	                // do nothing
	            }
	            else if (type == WordType.SPACE)
	            {
	                type = WordType.ARABIC_PUNC;
	            }
	            else
	            {
	                type = WordType.ILLEGAL;
	            }
	            sb.Append(c);
	        }
	        else if (StringUtil.IsArabicDigit(c))
	        {
	            if (type == WordType.ARABIC_NUM || type == WordType.ARABIC_PUNC ||
	                type == WordType.ENGLISH_PUNC || type == WordType.SPACE)
	            {
	                type = WordType.ARABIC_NUM;
	            }
	            else
	            {
	                type = WordType.ILLEGAL;
	            }
	            sb.Append(c);
	        }
	        else if (Char.IsPunctuation(c) || Char.IsSymbol(c))
	        {
	            if (type == WordType.ARABIC_NUM || type == WordType.ENGLISH_NUM || type == WordType.ENGLISH_PUNC)
	            {
	                // do nothing
	            }
	            else if (type == WordType.SPACE)
	            {
	                type = WordType.ENGLISH_PUNC;
	            }
	            else
	            {
	                type = WordType.ILLEGAL;
	            }
	            sb.Append(c);
	        }
	        else if (Char.IsDigit(c))
	        {
	            if (type == WordType.ENGLISH_NUM || type == WordType.ARABIC_PUNC ||
	                type == WordType.ENGLISH_PUNC || type == WordType.SPACE)
	            {
	                type = WordType.ENGLISH_NUM;
	            }
	            else
	            {
	                type = WordType.ILLEGAL;
	            }
	            sb.Append(c);
	        }
	        else if (Char.IsLetter(c))
	        {
	            if (type == WordType.ENGLISH_WORD || type == WordType.SPACE)
	            {
	                type = WordType.ENGLISH_WORD;
	            }
	            else
	            {
	                type = WordType.ILLEGAL;
	            }
	            sb.Append(c);
	        }
	        else
	        {
	            type = WordType.ILLEGAL;
	            sb.Append(c);
	        }
	    }

	    outword = sb.ToString();
	    return type;
	}*/
}