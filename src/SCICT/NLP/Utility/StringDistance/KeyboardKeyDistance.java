package SCICT.NLP.Utility.StringDistance;

import java.util.Arrays;

import Helper.KeyboardKeyComparator;
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
 Calculate Euclidean and Cosine Distance between two letters on given keyboard layout
*/
public class KeyboardKeyDistance
{
	private KeyboardConfig m_keyboardConfig;
	private LinqSimulationArrayList<KeyboardKey> m_keys;

	/**
	 Keyboard layout
	*/
	public final KeyboardConfig getCurrentConfig()
	{
		return m_keyboardConfig;
	}

	private double m_maximumDistance;
	private double m_minimumDistance;

	/** 
	 default consturcor which also sets the language to Persian.
	*/
	public KeyboardKeyDistance()
	{
		this.m_keyboardConfig = new KeyboardConfig();
		this.m_keys = new LinqSimulationArrayList<KeyboardKey>();

		Reconfig(this.m_keyboardConfig);

	}
	/** 
	 this function adds a row of m_keys to m_keys list. a row contains real characters on keyboard and can start from "Q", "A" and "Z".
	 
	 @param startX for "Q" row it is 1.0, for "A" row it is "1.5" and for "Z" row it is 2.
	 @param y for "Q" row it is 3.0, for "A" row it is "2" and for "Z" row it is 1.
	 @param values list of letters in each row, e.g. for a English standard qwerty keyboard layout it could be "asdfghjkl;'" for second row
	*/
	private void AddARow(float startX, float y, String values)
	{
		for (int i = 0; i < values.length(); i++)
		{
			this.m_keys.add(new KeyboardKey(i + startX, y, values.charAt(i)));
		}
	}

	/** 
	 this function computes maximum possible distance between m_keys in the defined keyboard.
	*/
	private void SetMaximumDistance()
	{
		double max = 0;
		for (int i = 0; i < this.m_keys.size(); i++)
		{
			for (int j = i + 1; j < this.m_keys.size(); j++)
			{
				max = Math.max(max, EuclideanDistance(this.m_keys.get(i).Value, this.m_keys.get(j).Value));
			}
		}
		this.m_maximumDistance = max;
	}

	/** 
	 this function computes minimum possible distance between m_keys in the defined keyboard.
	*/
	private void SetMinimumDistance()
	{
		this.m_minimumDistance = EuclideanDistance('ب', 'ل');
	}

	/** 
	 this function sets keyboadr language to Farsi and sets up Keys list.
	*/
	public final void Reconfig(KeyboardConfig aConfig)
	{
		// there is a cartesian environment for key layout which is centered bottom left position of keyboard plate.
		// distance between two adjacent key in a row is 1.0 and height between two adjacent column is 1.0. 
		// but the second row of m_keys (<A> to <'> are start from position 1.5.

		this.m_keys.clear();

		//  there is 30 key on keyboard in 3 rows.
		AddARow(1.0f, 2.0f, aConfig.FirstRowCharacters); // from <Q> to <]>
		AddARow(1.5f, 1.0f, aConfig.SecondRowCharacters); // from <A> to <'>
		AddARow(2.0f, 0.0f, aConfig.ThirdRowCharacters); // from <Z> to <,>

		// after normal m_keys, there is still some other m_keys which should be set according to keyboard layout in farsi for some keyboards.
		for (KeyboardKey key : aConfig.OtherCharacters)
		{
			this.m_keys.add(key);
		}
		
		
	
		this.m_keys=this.m_keys.Sort(this.m_keys.toArray(new KeyboardKey[0]),new KeyboardKeyComparator());

		this.m_keyboardConfig = aConfig;

		SetMaximumDistance();
		SetMinimumDistance();
	}

	/** 
	 this function calculates simple Cosine distance between two characters on keyboard.
	 Note that this distance is not normalized!
	 If any of these characters doesn't exist in current keyboard setting, then this function returns Maximum Distance value specified in current object.
	 
	 @param ch1
	 @param ch2
	 @return returns distance between two characters in keyboard.
	*/
	public final double CosineDistance(char ch1, char ch2)
	{
		KeyboardKey aKey1 = new KeyboardKey();
		KeyboardKey aKey2 = new KeyboardKey();

		aKey1.Value = ch1;
		int index1 = Arrays.binarySearch(m_keys.toArray(new KeyboardKey[0]), aKey1);
		

		if (index1 >= 0)
		{
			aKey1 = this.m_keys.get(index1);
		}
		else
		{
			return this.m_maximumDistance;
		}

		aKey2.Value = ch2;
		int index2 = Arrays.binarySearch(m_keys.toArray(new KeyboardKey[0]), aKey2);

		if (index2 >= 0)
		{
			aKey2 = m_keys.get(index2);
		}
		else
		{
			return this.m_maximumDistance;
		}

		double result = ((aKey1.X * aKey2.X) + (aKey1.Y * aKey2.Y)) / (Math.sqrt(aKey1.X * aKey1.X + aKey1.Y * aKey1.Y) * Math.sqrt(aKey2.X * aKey2.X + aKey2.Y * aKey2.Y));

		//if both m_keys use shift, so we don't include shift in their distance, else shift will add 1.0 ro their real distance
		if (aKey1.UseShift != aKey2.UseShift)
		{
			result += 1;
		}

		return result;
	}

	/** 
	 this function calculates simple Cosine distance between two characters on keyboard. 
	 
	 @param ch1
	 @param ch2
	 @return result is in range [0..1]. 0 means that two characters are near each other.
	*/
	public final double NormalizedCosineDistance(char ch1, char ch2)
	{
		double result = EuclideanDistance(ch1, ch2);
		return result / this.m_maximumDistance;
	}

	/** 
	 this function calculates simple euclidean distance between two characters on keyboard.
	 Note that this distance is not normalized!
	 If any of these characters doesn't exist in current keyboard setting, then this function returns Maximum Distance value specified in current object.
	 
	 @param ch1
	 @param ch2
	 @return returns distance between two characters in keyboard.
	*/
	public final double EuclideanDistance(char ch1, char ch2)
	{
		KeyboardKey aKey1 = new KeyboardKey();
		KeyboardKey aKey2 = new KeyboardKey();

		aKey1.Value = ch1;
		int index1 = Arrays.binarySearch(m_keys.toArray(new KeyboardKey[0]), aKey1);
		
		if (index1 >= 0)
		{
			aKey1 = this.m_keys.get(index1);
		}
		else
		{
			return this.m_maximumDistance;
		}

		aKey2.Value = ch2;
		int index2 = Arrays.binarySearch(m_keys.toArray(new KeyboardKey[0]), aKey2);

		if (index2 >= 0)
		{
			aKey2 = m_keys.get(index2);
		}
		else
		{
			return this.m_maximumDistance;
		}

		double result = Math.sqrt((aKey1.X - aKey2.X) * (aKey1.X - aKey2.X) + (aKey1.Y - aKey2.Y) * (aKey1.Y - aKey2.Y));
		if (aKey1.UseShift != aKey2.UseShift)
		{
			// There is difference beetween using Shift key mistakenly and not using it mistakenly.
			// Shift key rarely mistakenly used, but it's more possible the not to use shift key.

			if (aKey1.UseShift)
			{
				//AVG distance
				result += (m_maximumDistance + m_minimumDistance) / 2;
			}
			else if (aKey2.UseShift)
			{
				//nothing more
				result += 0;
			}
		}
		return result;
	}

	/**
	 Minimum possible distance
	*/
	public final double getMinimumNormalizedDistance()
	{
		return m_minimumDistance / m_maximumDistance;
	}

	/**
	 Maximum possible distance
	*/
	public final double getMaximumNormalizedDistance()
	{
		return m_maximumDistance / m_maximumDistance;
	}

	/** 
	 this function calculates simple euclidean distance between two characters on keyboard. 
	 
	 @param ch1
	 @param ch2
	 @return result is in range [0..1]. 0 means that two characters are near each other.
	*/
	public final double NormalizedEuclideanDistance(char ch1, char ch2)
	{
		double result = EuclideanDistance(ch1, ch2);
		return result / this.m_maximumDistance;
	}
}