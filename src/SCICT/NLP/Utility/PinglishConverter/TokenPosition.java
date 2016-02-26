package SCICT.NLP.Utility.PinglishConverter;


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
 Represents the position of a character in a word
*/
public class TokenPosition
{
	public static final TokenPosition None = new TokenPosition(0);
	public static final TokenPosition StartOfWord = new TokenPosition(1);
	public static final TokenPosition MiddleOfWord = new TokenPosition(2);
	public static final TokenPosition EndOfWord = new TokenPosition(4);
	public static final TokenPosition Any = new TokenPosition(1 | 2 | 4);

	private int intValue;
	private static java.util.HashMap<Integer, TokenPosition> mappings;
	private static java.util.HashMap<Integer, TokenPosition> getMappings()
	{
		if (mappings == null)
		{
			synchronized (TokenPosition.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, TokenPosition>();
				}
			}
		}
		return mappings;
	}

	private TokenPosition(int value)
	{
		intValue = value;
		synchronized (TokenPosition.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static TokenPosition forValue(int value)
	{
		synchronized (TokenPosition.class)
		{
			TokenPosition enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new TokenPosition(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
	public static TokenPosition[] values()
	{
		return getMappings().values().toArray(new TokenPosition[0]);
	}
	
	public TokenPosition Set(TokenPosition value)
	{
	 try
     {
		 int result=this.getValue()|value.getValue();
         return forValue(result);
     }
     catch (Exception ex)
     {
         ex.printStackTrace();
     }
	 return null;
	}
	
	public TokenPosition OR(TokenPosition value)
	{
	 try
     {
		 int result=this.getValue()|value.getValue();
         return forValue(result);
     }
     catch (Exception ex)
     {
         ex.printStackTrace();
     }
	return null;
	}
	
}