package SCICT.NLP.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


public class PersianWordTokenizer
{
	private static Matcher s_regexEngine;
	private static Pattern s_regexpattern;

	
	// this word pattern is not enough, because Erab signs are word boundaries. 
	 //It is recommended to continue in the matched vicinity to include neighboring Erab signs.
	// \\uxxxx Characters are different types of Halfspace characters.
	// \\p{M} matches Erab sign and mid-word spaces
	
	private static final String s_wordPattern = "\\b[\\w\\p{M}\\u200B\\u200C\\u00AC\\u001F\\u200D\\u200E\\u200F]+\\b";

	static
	{
		s_regexpattern = Pattern.compile(Pattern.quote(s_wordPattern));
	}

	public static java.util.ArrayList<String> Tokenize(String text, boolean removeSeparators, boolean standardized)
	{
		java.util.ArrayList<String> tokens = new java.util.ArrayList<String>();

		int strIndex = 0;
		s_regexEngine=s_regexpattern.matcher(text);
		while(s_regexEngine.find())
		{
			
			int start = FindMatchStart(s_regexEngine.start(), text);
			int end = FindMatchEnd(s_regexEngine.end() - 1, text);

			if (!removeSeparators)
			{
				for (int j = strIndex; j < start; j++)
				{
					tokens.add(text.charAt(j)+"");
				}
			}

			if (standardized)
			{
				tokens.add(StringUtil.RefineAndFilterPersianWord(text.substring(start, end + 1)));
			}
			else
			{
				tokens.add(text.substring(start, end + 1));
			}

			strIndex = end + 1;
		}

		// now add trailing strings if any

		if (!removeSeparators)
		{
			for (int j = strIndex; j < text.length(); j++)
			{
				tokens.add(text.charAt(j)+"");
			}
		}

		return tokens;
	}

	public static java.util.ArrayList<String> Tokenize(String text, boolean removeSeparators)
	{
		return Tokenize(text, removeSeparators, false);
	}


	public static java.util.ArrayList<String> Tokenize(String text)
	{
		return Tokenize(text, false, false);
	}

	/** 
	 The regex system does not match trailing erab signs so this code 
	 is here to ensure that the matched word includes trailing characters
	 
	 @param matchEndIndex
	 @param text
	 @return 
	*/
	private static int FindMatchEnd(int matchEndIndex, String text)
	{
		int i;
		for (i = matchEndIndex + 1; i < text.length(); i++)
		{
			if (!StringUtil.IsInArabicWord(text.charAt(i)))
			{
				return i - 1;
			}
		}

		if (i >= text.length())
		{
			return text.length() - 1;
		}
		else
		{
			return matchEndIndex;
		}
	}

	private static int FindMatchStart(int matchStartIndex, String text)
	{
		int i;
		for (i = matchStartIndex - 1; i >= 0; i--)
		{
			if (!StringUtil.IsInArabicWord(text.charAt(i)))
			{
				return i + 1;
			}
		}

		if (i < 0)
		{
			return 0;
		}
		else
		{
			return matchStartIndex;
		}
	}

}