package SCICT.NLP.Utility.PinglishConverter;

import java.util.Set;

import Helper.LinqSimulationArrayList;

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
 One-to-one corresponding letters in transliteration.
*/
public final class SingleValueCharMappings
{
	/** 
	 A dictionary contains all single value mappings
	*/
	private static final java.util.TreeMap<Character, Character> s_singleValueCharMap = new java.util.TreeMap<Character, Character>();

	/** 
	 Initializes the <see cref="SingleValueCharMappings"/> class.
	*/
	static
	{
		s_singleValueCharMap.put('b', 'ب');
		s_singleValueCharMap.put('x', 'خ');
		s_singleValueCharMap.put('d', 'د');
		s_singleValueCharMap.put('r', 'ر');
		s_singleValueCharMap.put('f', 'ف');
		s_singleValueCharMap.put('l', 'ل');
		s_singleValueCharMap.put('m', 'م');
		s_singleValueCharMap.put('n', 'ن');
		s_singleValueCharMap.put('v', 'و');
		s_singleValueCharMap.put('w', 'و');
		s_singleValueCharMap.put('y', 'ی');
	}

	/** 
	 Retrieves a mapping Persian letter for the given English character.
	 
	 @param ch
	 @return The mapping letter, if it contains an entry for the give character;
	 otherwise <value>null</value>
	*/
	public static Character TryGetValue(char ch)
	{
		if (s_singleValueCharMap.keySet().contains(ch))
		{
			return s_singleValueCharMap.get(ch);
		}

		return null;
	}

	/**
	*/
	public static LinqSimulationArrayList<Character> getSingleValueCharacters()
	{
		Set<Character> set= s_singleValueCharMap.keySet();
		LinqSimulationArrayList<Character>list=new LinqSimulationArrayList<Character>();
		for(Character c : set)
			list.add(c);
		return list;
	}
}