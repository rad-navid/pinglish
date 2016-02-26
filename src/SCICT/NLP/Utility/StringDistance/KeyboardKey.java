package SCICT.NLP.Utility.StringDistance;


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
 Keboard Key (Letters) data structure in Cartesian Coordinate System
*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class KeyboardKey implements java.lang.Comparable<KeyboardKey>
{
	/**
	 Indicates the x-axes position of a key (letter) in Cartesian Coordinate System
	*/
	public double X;
	/**
	 Indicates the y-axes position of a key (letter) in Cartesian Coordinate System
	*/
	public double Y;
	/**
	 Indicates the needs of pressing Shift key to type corresponding key.
	*/
	public boolean UseShift;
	/**
	 Indicates the Unicode value of the key in current keyboard layout
	*/
	public char Value;

	private void Init()
	{
		X = 0.0;
		Y = 0.0;
		UseShift = false;
		Value = 'a';
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/**
	 Class Constructor
	*/
	public KeyboardKey()
	{
		Init();
	}
	/**
	 Class Constructor
	
	@param ch Unicode value of the key in current keyboard layout
	*/
	public KeyboardKey(char ch)
	{
		Init();
		Value = ch;
	}
	/**
	 Class constructor
	
	@param x x-axes position of a key (letter) in Cartesian Coordinate System
	@param y y-axes position of a key (letter) in Cartesian Coordinate System
	*/
	public KeyboardKey(float x, float y)
	{
		Init();
		X = x;
		Y = y;
	}
	/**
	 Class Constructor
	
	@param x x-axes position of a key (letter) in Cartesian Coordinate System
	@param y y-axes position of a key (letter) in Cartesian Coordinate System
	@param ch Unicode value of the key in current keyboard layout
	*/
	public KeyboardKey(float x, float y, char ch)
	{
		Init();
		X = x;
		Y = y;
		Value = ch;
	}
	/**
	 Class Constructor
	
	@param x x-axes position of a key (letter) in Cartesian Coordinate System
	@param y y-axes position of a key (letter) in Cartesian Coordinate System
	@param ch Unicode value of the key in current keyboard layout
	@param useShift Is pressing Shift key needed to type corresponding key.
	*/
	public KeyboardKey(float x, float y, char ch, boolean useShift)
	{
		Init();
		X = x;
		Y = y;
		Value = ch;
		UseShift = useShift;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public final int compareTo(KeyboardKey k)
	{
		if (this.Value < k.Value)
		{
			return -1;
		}

		if (this.Value > k.Value)
		{
			return 1;
		}

		return 0;
	}

}