package SCICT.NLP.Utility.PinglishConverter;

import Helper.CharacterMappingInfoComparator;
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
 Holds all possible mapping information for a letter.
*/
public class CharacterMapping
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	private LinqSimulationArrayList<CharacterMappingInfo> m_values;
	private char m_letter;
	private boolean m_caseSensitive;
	//private readonly object m_label;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Initializes a new instance of the <see cref="CharacterMapping"/> class.
	 
	 @param letter The letter which this instance will hold its mappings.
	 @param values Mapping values for the given letter.
	*/
	public CharacterMapping(char letter, CharacterMappingInfo[] values)
	{
		this(letter, false, values);
	}

	/** 
	 Initializes a new instance of the <see cref="CharacterMapping"/> class.
	 
	 @param letter The letter which this instance will hold its mappings.
	 @param caseSensitive if set to <c>true</c> [case sensitive].
	 @param values Mapping values for the given letter.
	*/
	private CharacterMapping(char letter, boolean caseSensitive, CharacterMappingInfo[] values)
	{
		this.m_letter = letter;
		this.m_caseSensitive = caseSensitive;
		this.m_values = new LinqSimulationArrayList<CharacterMappingInfo>(values);
		this.m_values=this.m_values.Sort(m_values.toArray(new CharacterMappingInfo[0]),new CharacterMappingInfoComparator());
	}

	/** 
	 Gets the letter which this instance holds its mapping information.
	 
	 <value>The letter.</value>
	*/
	public final char getLetter()
	{
		return this.m_letter;
	}

	/** 
	 Gets a value indicating whether this instance is case sensitive.
	 
	 <value>
	 	<c>true</c> if this instance is case sensitive; otherwise, <c>false</c>.
	 </value>
	*/
	public final boolean getIsCaseSensitive()
	{
		return this.m_caseSensitive;
	}

	/** 
	 Gets the corresponding mapping information of the <see cref="Letter"/>
	 
	 <value>The values.</value>
	*/
	public final CharacterMappingInfo[] getValues()
	{
		return (this.m_values != null) ? this.m_values.toArray(new CharacterMappingInfo[0]) : null;
	}

	/** 
	 Returns a <see cref="System.String"/> that represents this instance.
	 
	 @return 
	 A <see cref="System.String"/> that represents this instance.
	 
	*/
	@Override
	public String toString()
	{
		if (this.m_letter != '\0')
		{
			return (new Character(this.m_letter)).toString();
		}
		return "";
		/*else
		{
		    return this.m_label.ToString();
		}*/
	}
}