package SCICT.NLP.Utility.StringDistance;

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
 Define Language of Current Keyboard Layout
*/
public enum KeyboardLanguages
{
	/**
	 Used as Persian Standard Keyboad Layout 
	*/
	Persian(0),
	/**
	 Used as English Standard Keyboad Layout
	*/
	English(1);

	private int intValue;
	private static java.util.HashMap<Integer, KeyboardLanguages> mappings;
	private static java.util.HashMap<Integer, KeyboardLanguages> getMappings()
	{
		if (mappings == null)
		{
			synchronized (KeyboardLanguages.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, KeyboardLanguages>();
				}
			}
		}
		return mappings;
	}

	private KeyboardLanguages(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static KeyboardLanguages forValue(int value)
	{
		return getMappings().get(value);
	}
}