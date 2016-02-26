package SCICT.NLP.Persian;

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
 Filter for the Persian characters that provide means for replacing non-standard characters with their standard ones.
*/
public class PersianCharFilter implements ICharFilter
{
	/** 
	 dictionary that maps character codes (i.e. their integer value) to their refined string.
	*/
	protected java.util.HashMap<Integer, String> dicCharFilterings = new java.util.HashMap<Integer,String>();

	/** 
	 dictionary that maps character codes to their filtering category that the character belongs
	*/
	protected java.util.HashMap<Integer, FilteringCharacterCategory> dicCharCategories = new java.util.HashMap<Integer, FilteringCharacterCategory>();

	/** 
	 Initializes a new instance of the <see cref="PersianCharFilter"/> class. And fills the 
	 data-structures holding filtering data in a hard-coded way.
	*/
	public PersianCharFilter()
	{
		// Change to Persian Kaaf
		dicCharFilterings.put(0x06AA, StringUtil.StringFromCodes(StandardCharacters.StandardKaaf));
		dicCharFilterings.put(0x0643, StringUtil.StringFromCodes(StandardCharacters.StandardKaaf));
		dicCharFilterings.put(0xFEDA, StringUtil.StringFromCodes(StandardCharacters.StandardKaaf));
		dicCharFilterings.put(0xFED9, StringUtil.StringFromCodes(StandardCharacters.StandardKaaf));

		AddCodesToCategory(FilteringCharacterCategory.Kaaf, 0x06AA, 0x0643, 0xFEDA, 0xFED9);

		// Change to Persian Yaa
		dicCharFilterings.put(0x0649, StringUtil.StringFromCodes(StandardCharacters.StandardYaa));
		dicCharFilterings.put(0x064A, StringUtil.StringFromCodes(StandardCharacters.StandardYaa));
		dicCharFilterings.put(0xFEF1, StringUtil.StringFromCodes(StandardCharacters.StandardYaa));
		dicCharFilterings.put(0xFEF2, StringUtil.StringFromCodes(StandardCharacters.StandardYaa));

		AddCodesToCategory(FilteringCharacterCategory.Yaa, 0x0649, 0x064A, 0xFEF1, 0xFEF2);

		// Change arabic digits to their persian counter-part
		// 0x0660 to 0x0669 -->  0x06F0 to 0x06F9
		dicCharFilterings.put(0x0660, StringUtil.StringFromCodes(StandardCharacters.StandardDigit0));
		dicCharFilterings.put(0x0661, StringUtil.StringFromCodes(StandardCharacters.StandardDigit1));
		dicCharFilterings.put(0x0662, StringUtil.StringFromCodes(StandardCharacters.StandardDigit2));
		dicCharFilterings.put(0x0663, StringUtil.StringFromCodes(StandardCharacters.StandardDigit3));
		dicCharFilterings.put(0x0664, StringUtil.StringFromCodes(StandardCharacters.StandardDigit4));
		dicCharFilterings.put(0x0665, StringUtil.StringFromCodes(StandardCharacters.StandardDigit5));
		dicCharFilterings.put(0x0666, StringUtil.StringFromCodes(StandardCharacters.StandardDigit6));
		dicCharFilterings.put(0x0667, StringUtil.StringFromCodes(StandardCharacters.StandardDigit7));
		dicCharFilterings.put(0x0668, StringUtil.StringFromCodes(StandardCharacters.StandardDigit8));
		dicCharFilterings.put(0x0669, StringUtil.StringFromCodes(StandardCharacters.StandardDigit9));

		AddCodesToCategory(FilteringCharacterCategory.ArabicDigit, 0x0660, 0x0661, 0x0662, 0x0663, 0x0664, 0x0665, 0x0666, 0x0667, 0x0668, 0x0669);

		// Change Half-Spaces (i.e. Zero-Width-Non-Jointers) to 0x200C
		dicCharFilterings.put(0x200B, StringUtil.StringFromCodes(StandardCharacters.StandardHalfSpace));
		dicCharFilterings.put(0x00AC, StringUtil.StringFromCodes(StandardCharacters.StandardHalfSpace));
		dicCharFilterings.put(0x001F, StringUtil.StringFromCodes(StandardCharacters.StandardHalfSpace));
		dicCharFilterings.put(0x200D, StringUtil.StringFromCodes(StandardCharacters.StandardHalfSpace));
		dicCharFilterings.put(0x200E, StringUtil.StringFromCodes(StandardCharacters.StandardHalfSpace));
		dicCharFilterings.put(0x200F, StringUtil.StringFromCodes(StandardCharacters.StandardHalfSpace));

		AddCodesToCategory(FilteringCharacterCategory.HalfSpace, 0x200B, 0x00AC, 0x001F, 0x200D, 0x200E, 0x200F);

		// Change Erabs to standard erab
		dicCharFilterings.put(0xE818, StringUtil.StringFromCodes(StandardCharacters.StandardTashdid, StandardCharacters.StandardFathatan));
		dicCharFilterings.put(0xE820, StringUtil.StringFromCodes(StandardCharacters.StandardFatha));
		dicCharFilterings.put(0xE821, StringUtil.StringFromCodes(StandardCharacters.StandardZamma));
		dicCharFilterings.put(0xE822, StringUtil.StringFromCodes(StandardCharacters.StandardSaaken));
		dicCharFilterings.put(0xE823, StringUtil.StringFromCodes(StandardCharacters.StandardFathatan));
		dicCharFilterings.put(0xE824, StringUtil.StringFromCodes(StandardCharacters.StandardZammatan));
		dicCharFilterings.put(0xE825, StringUtil.StringFromCodes(StandardCharacters.StandardTashdid));
		dicCharFilterings.put(0xE826, StringUtil.StringFromCodes(StandardCharacters.StandardKasra));
		dicCharFilterings.put(0xE827, StringUtil.StringFromCodes(StandardCharacters.StandardKasratan));
		dicCharFilterings.put(0xE828, StringUtil.StringFromCodes(StandardCharacters.StandardTashdid, StandardCharacters.StandardFatha));
		dicCharFilterings.put(0xE829, StringUtil.StringFromCodes(StandardCharacters.StandardTashdid, StandardCharacters.StandardZamma));
		dicCharFilterings.put(0xE82A, StringUtil.StringFromCodes(StandardCharacters.StandardTashdid, StandardCharacters.StandardFathatan));
		dicCharFilterings.put(0xE82B, StringUtil.StringFromCodes(StandardCharacters.StandardTashdid, StandardCharacters.StandardZammatan));
		dicCharFilterings.put(0xE82C, StringUtil.StringFromCodes(StandardCharacters.StandardTashdid, StandardCharacters.StandardKasra));
		dicCharFilterings.put(0xE82D, StringUtil.StringFromCodes(StandardCharacters.StandardTashdid, StandardCharacters.StandardKasratan));

		AddCodesToCategory(FilteringCharacterCategory.Erab, 0xE818, 0xE820, 0xE821, 0xE822, 0xE823, 0xE824, 0xE825, 0xE826, 0xE827, 0xE828, 0xE829, 0xE82A, 0xE82B, 0xE82C, 0xE82D);

		dicCharFilterings.put(0xFE8D, "ا");
		dicCharFilterings.put(0xFE81, "آ");
		dicCharFilterings.put(0xFE83, "أ");
		dicCharFilterings.put(0xFE85, "ؤ");
		dicCharFilterings.put(0xFE87, "إ");
		dicCharFilterings.put(0xFE8F, "ب");
		dicCharFilterings.put(0xFE93, "ۀ");
		dicCharFilterings.put(0xFE95, "ت");
		dicCharFilterings.put(0xFE99, "ث");
		dicCharFilterings.put(0xFE9D, "ج");
		dicCharFilterings.put(0xFEA1, "ح");
		dicCharFilterings.put(0xFEA5, "خ");
		dicCharFilterings.put(0xFEA9, "د");
		dicCharFilterings.put(0xFEAB, "ذ");
		dicCharFilterings.put(0xFEAD, "ر");
		dicCharFilterings.put(0xFEAF, "ز");
		dicCharFilterings.put(0xFEB1, "س");
		dicCharFilterings.put(0xFEB5, "ش");
		dicCharFilterings.put(0xFEB9, "ص");
		dicCharFilterings.put(0xFEBD, "ض");
		dicCharFilterings.put(0xFEC1, "ط");
		dicCharFilterings.put(0xFEC5, "ظ");
		dicCharFilterings.put(0xFEC9, "ع");
		dicCharFilterings.put(0xFECD, "غ");
		dicCharFilterings.put(0xFED1, "ف");
		dicCharFilterings.put(0xFED5, "ق");
		//dicCharFilterings.Add(0xFED9, "ک");
		dicCharFilterings.put(0xFEDD, "ل");
		dicCharFilterings.put(0xFEE1, "م");
		dicCharFilterings.put(0xFEE5, "ن");
		dicCharFilterings.put(0xFEE9, "ه");
		dicCharFilterings.put(0xFEED, "و");
		dicCharFilterings.put(0xFEEF, "ی");
		//dicCharFilterings.Add(0xFEF1, "ی");
	}

	/** 
	 Adds a sequence of character codes to a filtering category
	 
	 @param category The filtering category.
	 @param codes The codes.
	*/
	private void AddCodesToCategory(FilteringCharacterCategory category, Integer... codes)
	{
		for (int code : codes)
		{
			dicCharCategories.put(code, category);
		}
	}

	/** 
	 Filters the char and returns the string for its filtered (i.e. standardized) equivalant.
	 The string may contain 0, 1, or more characters.
	 If the length of the string is 0, then the character should have been left out.
	 If the length of the string is 1, then the character might be left intact or replaced with another character.
	 If the length of the string is more than 1, then there have been no 1-character replacement for this character.
	 It is replaced with 2 or more characters. e.g. some fonts have encoded Tashdid, and Tanvin in one character.
	 To make it standard this character is replaced with 2 characters, one for Tashdid, and the other for Tanvin.
	 
	 @param ch The character to filter.
	 @return 
	*/
	public final String FilterChar(char ch)
	{
		int nCh = (int)ch;
		String result = "";
		if (dicCharFilterings.containsKey(nCh) ? (result = dicCharFilterings.get(nCh)).equals(result) : false)
		{
			return result;
		}
		else
		{
			return (new Character(ch)).toString();
		}
	}

	/** 
	 Filters the char and returns the string for its filtered (i.e. standardized) equivalant.
	 The string may contain 0, 1, or more characters.
	 If the length of the string is 0, then the character should have been left out.
	 If the length of the string is 1, then the character might be left intact or replaced with another character.
	 If the length of the string is more than 1, then there have been no 1-character replacement for this character.
	 It is replaced with 2 or more characters. e.g. some fonts have encoded Tashdid, and Tanvin in one character.
	 To make it standard this character is replaced with 2 characters, one for Tashdid, and the other for Tanvin.
	 
	 @param ch The character to filter.
	 @param ignoreCats The filtering categories to be ignored.
	 @return 
	*/
	public final String FilterChar(char ch, FilteringCharacterCategory ignoreCats)
	{
		int nCh = (int)ch;
		FilteringCharacterCategory cat = FilteringCharacterCategory.forValue(0);
		if (dicCharCategories.containsKey(nCh) ? (cat = dicCharCategories.get(nCh)) == cat : false)
		{
			if ((cat.getValue() & ignoreCats.getValue()) == cat.getValue()) //i.e. if cat bit exists in ignoreCats
			{
				return (new Character(ch)).toString(); // return ch unchanged
			}
			else
			{
				return FilterChar(ch);
			}
		}
		else
		{
			return FilterChar(ch);
		}
	}

	/** 
	 Filters the char and returns the string for its filtered (i.e. standardized) equivalant.
	 The string may contain 0, 1, or more characters.
	 If the length of the string is 0, then the character should have been left out.
	 If the length of the string is 1, then the character might be left intact or replaced with another character.
	 If the length of the string is more than 1, then there have been no 1-character replacement for this character.
	 It is replaced with 2 or more characters. e.g. some fonts have encoded Tashdid, and Tanvin in one character.
	 To make it standard this character is replaced with 2 characters, one for Tashdid, and the other for Tanvin.
	 
	 @param ch The character to filter.
	 @param ignoreList The characters to be ignored.
	 @return 
	*/
	public final String FilterChar(char ch, java.util.HashSet<Character> ignoreList)
	{
		if (ignoreList.contains(ch))
		{
			return (new Character(ch)).toString();
		}
		else
		{
			return FilterChar(ch);
		}
	}

	/** 
	 Filters the char and returns the string for its filtered (i.e. standardized) equivalant.
	 The string may contain 0, 1, or more characters.
	 If the length of the string is 0, then the character should have been left out.
	 If the length of the string is 1, then the character might be left intact or replaced with another character.
	 If the length of the string is more than 1, then there have been no 1-character replacement for this character.
	 It is replaced with 2 or more characters. e.g. some fonts have encoded Tashdid, and Tanvin in one character.
	 To make it standard this character is replaced with 2 characters, one for Tashdid, and the other for Tanvin.
	 
	 @param ch The character to filter.
	 @param ignoreList The characters to be ignored.
	 @param ignoreCats The filtering categories to be ignored.
	 @return 
	*/
	public final String FilterChar(char ch, java.util.HashSet<Character> ignoreList, FilteringCharacterCategory ignoreCats)
	{
		if (ignoreList.contains(ch))
		{
			return (new Character(ch)).toString();
		}
		else
		{
			return FilterChar(ch, ignoreCats);
		}
	}

	/** 
	 Filters every character of the string and returns the filtered string. To see how each character is 
	 filtered see: <see cref="FilterChar(char)"/>
	 
	 @param str The string to be filtered.
	 @return 
	*/
	public final String FilterString(String str)
	{
		StringBuilder sb = new StringBuilder();
		char[] chararray=str.toCharArray();
		for (char c : chararray)
		{
			sb.append(FilterChar(c));
		}
		return sb.toString();
	}

	/** 
	 Filters every character of the string and returns the filtered string. To see how each character is 
	 filtered see: <see cref="FilterChar(char)"/>
	 
	 @param str The string to be filtered.
	 @param ignoreCats The filtering categories to be ignored.
	 @return 
	*/
	public final String FilterString(String str, FilteringCharacterCategory ignoreCats)
	{
		StringBuilder sb = new StringBuilder();
		char[] chararray=str.toCharArray();
		for (char c : chararray)
		{
			sb.append(FilterChar(c, ignoreCats));
		}
		return sb.toString();
	}

	/** 
	 Filters every character of the string and returns the filtered string. To see how each character is 
	 filtered see: <see cref="FilterChar(char)"/>
	 
	 @param str The string to be filtered.
	 @param ignoreList The characters to be ignored.
	 @return 
	*/
	public final String FilterString(String str, java.util.HashSet<Character> ignoreList)
	{
		StringBuilder sb = new StringBuilder();
		char[] chararray=str.toCharArray();
		for (char c : chararray)
		{
			sb.append(FilterChar(c, ignoreList));
		}
		return sb.toString();
	}

	/** 
	 Filters every character of the string and returns the filtered string. To see how each character is 
	 filtered see: <see cref="FilterChar(char)"/>
	 
	 @param str The string to be filtered.
	 @param ignoreList The characters to be ignored.
	 @param ignoreCats The filtering categories to be ignored.
	 @return 
	*/
	public final String FilterString(String str, java.util.HashSet<Character> ignoreList, FilteringCharacterCategory ignoreCats)
	{
		StringBuilder sb = new StringBuilder();
		char[] chararray=str.toCharArray();
		for (char c : chararray)
		{
			sb.append(FilterChar(c, ignoreList, ignoreCats));
		}
		return sb.toString();
	}
}