package SCICT.NLP.Utility.PinglishConverter;

import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.Serializable;

import Helper.LinqSimulationHashMap;

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


//C# TO JAVA CONVERTER NOTE: There is no Java equivalent to C# namespace aliases:
//using KeyValueList = Dictionary<string, int>;

public class MappingSequence implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -186261686823586243L;
	public static final char Separator = '|';
	private PatternStorage m_patternStorage = new PatternStorage();

	public final TObjectIntHashMap<String> getItem(char ch, String prefix, String postfix)
	{
		return m_patternStorage.getItem(ch, prefix, postfix);
	}

	public final void LearnWordMapping(PinglishString word, int prefixGram, int postfixGram)
	{
		int len = word.getEnglishLetters().size();

		for (int i = 0; i < len; ++i)
		{
			String prefix = GetPrefixForIndex(word.getEnglishString(), i, prefixGram);
			String postfix = GetPostfixForIndex(word.getEnglishString(), i, postfixGram);

			if (prefix.length() == prefixGram && postfix.length() == postfixGram)
			{
				UpdateDictionary(word.getEnglishLetters().get(i), prefix, postfix, word.getPersianLetters().get(i));
			}
		}
	}

	public static String GetPrefixForIndex(String str, int index, int prefixGram)
	{
		if (prefixGram >= 0)
		{
			int length = prefixGram;
			if (prefixGram >= index)
			{
				length = index;
			}
			return str.substring(index - length, index - length + length);
		}
		return null;
	}

	public static String GetPostfixForIndex(String str, int index, int postfixGram)
	{
		if (postfixGram >= 0)
		{
			int length = postfixGram;
			if (str.length() <= index + postfixGram)
			{
				length = (str.length() - index) - 1;
			}
			return str.substring(index + 1, index + 1 + length);
		}
		return null;
	}

	private void UpdateDictionary(char ch, String prefix, String postfix, String mappedChar)
	{
		//if (mappedChar == "آ")
		//    mappedChar = "ا";
		m_patternStorage.AddOrUpdatePattern(ch, prefix, postfix, mappedChar);
	}

	public static String CreateKey(String prefix, String postfix, char ch)
	{
		return String.format("%1$s%2$s%3$s%2$s%4$s", prefix, Separator, ch, postfix);
	}

	public PatternStorage get_patternStorage() {
		return this.m_patternStorage;
	}
	public void set_patternStorage(PatternStorage pattern) {
		this.m_patternStorage=pattern;
	}
}