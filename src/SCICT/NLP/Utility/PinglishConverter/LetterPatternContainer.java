package SCICT.NLP.Utility.PinglishConverter;

import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.strategy.HashingStrategy;

import java.io.Serializable;

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

public class LetterPatternContainer implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2421113335297263633L;
	//private final LinqSimulationHashMap<String, LinqSimulationHashMap<String, Integer>> m_patterns = new LinqSimulationHashMap<String, LinqSimulationHashMap<String, Integer>>();
	private final TCustomHashMap<String, TObjectIntHashMap<String>>m_patterns= new TCustomHashMap<String, TObjectIntHashMap<String>>(new MyHashingStrategy());
	
	public LetterPatternContainer(char ch)
	{
		setLetter(ch);
	}

	public final void AddPattern(String prefix, String postfix, String mapping)
	{
		TObjectIntHashMap<String> mappings = null;
		Helper.RefObject<TObjectIntHashMap<String>> tempRef_mappings = new Helper.RefObject<TObjectIntHashMap<String>>(mappings);
		boolean tempVar = !ContainsPattern(prefix, postfix, tempRef_mappings);
			mappings=tempRef_mappings.argvalue;
		if (tempVar)
		{
			String key = GetKey(prefix, postfix);
			mappings = new TObjectIntHashMap<String>();
			m_patterns.put(key, mappings);
		}
		if (mappings.containsKey(mapping))
        {
            mappings.increment(mapping);
        }
        else
        {
        	mappings.put(mapping, 1);
        }
		//LinqSimulationHashMap.AddOrUpdate(mappings,mapping);
	}

	private String GetKey(String prefix, String postfix)
	{
		String key;
		if (prefix == null && postfix == null)
		{
			key = "";
		}
		else
		{
			assert prefix != null && postfix != null;
			key = String.format("%1$s%2$s%3$s", prefix, MappingSequence.Separator, postfix);
		}
		return key;
	}

	public final boolean ContainsPattern(String prefix, String postfix, Helper.RefObject<TObjectIntHashMap<String>> mapping)
	{
		mapping.argvalue = null;
		String key = GetKey(prefix, postfix);
		
		if (m_patterns.containsKey(key))
		{
			mapping.argvalue = m_patterns.get(key);
			return true;
		}
		

		return false;
	}

	public final TObjectIntHashMap<String> getItem(char ch, String prefix, String postfix)
	{
		TObjectIntHashMap<String> mapping = null;
		Helper.RefObject<TObjectIntHashMap<String>> tempRef_mapping = new Helper.RefObject<TObjectIntHashMap<String>>(mapping);
		TObjectIntHashMap<String> tempVar = this.ContainsPattern(prefix, postfix, tempRef_mapping) ? mapping : null;
		mapping = tempRef_mapping.argvalue;
		return tempVar;
	}

	private char privateLetter;
	public final char getLetter()
	{
		return privateLetter;
	}
	public final void setLetter(char value)
	{
		privateLetter = value;
	}
	
	
}
class MyHashingStrategy implements HashingStrategy<String>,Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int computeHashCode(String arg0) {
		// TODO Auto-generated method stub
		return arg0.hashCode();
	}

	@Override
	public boolean equals(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return arg0.hashCode()==arg1.hashCode();
	}
	
}