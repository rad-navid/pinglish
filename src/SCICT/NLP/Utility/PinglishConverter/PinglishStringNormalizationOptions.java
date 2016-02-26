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
 Represents options in PinglishString normalization.
*/
public class PinglishStringNormalizationOptions
{
	/** 
	 Use the default settings
	*/
	public static final PinglishStringNormalizationOptions None = new PinglishStringNormalizationOptions(0);

	/** 
	 Lowercase English letters
	*/
	public static final PinglishStringNormalizationOptions LowercaseEnglishLetters = new PinglishStringNormalizationOptions(1);

	/** 
	 No Erab in Persian letters
	*/
	public static final PinglishStringNormalizationOptions NoErabPersianLetters = new PinglishStringNormalizationOptions(2);

	/** 
	 No duplicate entries
	*/
	public static final PinglishStringNormalizationOptions NoDuplicatesEntries = new PinglishStringNormalizationOptions(4);

	/** 
	 Sort entries
	*/
	public static final PinglishStringNormalizationOptions SortBasedOnEnglishLetters = new PinglishStringNormalizationOptions(8);

	private int intValue;
	private static java.util.HashMap<Integer, PinglishStringNormalizationOptions> mappings;
	private static java.util.HashMap<Integer, PinglishStringNormalizationOptions> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PinglishStringNormalizationOptions.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, PinglishStringNormalizationOptions>();
				}
			}
		}
		return mappings;
	}

	private PinglishStringNormalizationOptions(int value)
	{
		intValue = value;
		synchronized (PinglishStringNormalizationOptions.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static PinglishStringNormalizationOptions forValue(int value)
	{
		synchronized (PinglishStringNormalizationOptions.class)
		{
			PinglishStringNormalizationOptions enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new PinglishStringNormalizationOptions(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
	public boolean Has(PinglishStringNormalizationOptions value)
	{
		int andResult=value.getValue()&this.getValue();
		boolean result=(andResult==value.getValue());
		return result;
	}
}