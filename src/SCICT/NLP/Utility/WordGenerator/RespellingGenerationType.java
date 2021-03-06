package SCICT.NLP.Utility.WordGenerator;

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
 Indicates the methodes of generating respelling suggestions by insering or omitting a letter, substitution of a letter with other letters and transposing two adjacent letters
*/
public class RespellingGenerationType
{
	/**
	 Transposition of two adjacent letters
	*/
	public static final RespellingGenerationType Transpose = new RespellingGenerationType(1); // Math.Pow(2,0)
	/**
	 insertion of one letter
	*/
	public static final RespellingGenerationType Insert = new RespellingGenerationType(1 * 2);
	/**
	 Omission of one letter
	*/
	public static final RespellingGenerationType Delete = new RespellingGenerationType(1 * 2 * 2);
	/**
	 Substitution of two letters
	*/
	public static final RespellingGenerationType Substitute = new RespellingGenerationType(1 * 2 * 2 * 2);

	private int intValue;
	private static java.util.HashMap<Integer, RespellingGenerationType> mappings;
	private static java.util.HashMap<Integer, RespellingGenerationType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (RespellingGenerationType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, RespellingGenerationType>();
				}
			}
		}
		return mappings;
	}

	private RespellingGenerationType(int value)
	{
		intValue = value;
		synchronized (RespellingGenerationType.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static RespellingGenerationType forValue(int value)
	{
		synchronized (RespellingGenerationType.class)
		{
			RespellingGenerationType enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new RespellingGenerationType(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}