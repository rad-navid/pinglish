package SCICT.NLP.Utility.PinglishConverter;

import java.io.Serializable;
import java.util.HashMap;

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
 Represents a Pinglish word, and its corresponding Persian word
*/
public class PinglishString implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1442168435292661555L;
	public PinglishString()
	{
		setEnglishLetters(new java.util.ArrayList<Character>());
		setPersianLetters(new java.util.ArrayList<String>());
	}

	public PinglishString(String englishString)
	{
		setEnglishLetters(new java.util.ArrayList<Character>());
		setPersianLetters(new java.util.ArrayList<String>());
		char[] chararray=englishString.toCharArray();
		for (char ch : chararray)
		{
			Append((new Character(ch)).toString(), ch);
		}
	}


	/**
	*/
	private java.util.ArrayList<String> privatePersianLetters;
	public final java.util.ArrayList<String> getPersianLetters()
	{
		return privatePersianLetters;
	}
	public final void setPersianLetters(java.util.ArrayList<String> value)
	{
		privatePersianLetters = value;
	}

	/**
	*/
	private java.util.ArrayList<Character> privateEnglishLetters;
	public final java.util.ArrayList<Character> getEnglishLetters()
	{
		return privateEnglishLetters;
	}
	public final void setEnglishLetters(java.util.ArrayList<Character> value)
	{
		privateEnglishLetters = value;
	}
	/** 
	 Gets the Persian string.
	 
	 <value>The Persian string.</value>
	*/
	public final String getPersianString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getPersianLetters().size(); i++)
		{
			if (getPersianLetters().get(i) != null)
			{
				sb.append(getPersianLetters().get(i));
			}
		}
		return sb.toString();
	}

	/** 
	 Gets the english string.
	 
	 <value>The english string.</value>
	*/
	public final String getEnglishString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getEnglishLetters().size(); i++)
		{
			sb.append(getEnglishLetters().get(i));
		}
		return sb.toString();
	}

	/** 
	 Gets the length.
	 
	 <value>The length.</value>
	*/
	public final int getLength()
	{
		return getPersianLetters().size();
	}

	/** 
	 
	 
	 @param index
	 @return 
	 Returns a KeyValuePair: 
	 Key is the English character, and Value is its Persian equivalent.
	 
	*/
	public final java.util.Map.Entry<Character, String> getItem(int index)
	{
		HashMap< Character, String> hs=new HashMap<Character, String>();
		hs.put(getEnglishLetters().get(index), getPersianLetters().get(index));
		return hs.entrySet().iterator().next();
	}

	public final void Update(int index, String persianLetter, char englishLetter)
	{
		assert index <= getLength();

		if (index == getLength())
		{
			Append(persianLetter, englishLetter);
		}
		// Never uncomment this line, unless you know its side-effects in the library
		/*else if (index < Length)
		{
		    UpdateAtPos(index, persianLetter, englishLetter);
		}*/
	}

	/*private void UpdateAtPos(int index, string persianLetter, char englishLetter)
	{
	    englishLetters[index] = englishLetter;
	    m_persianLetters[index] = persianLetter;
	}*/

	/** 
	 Appends the specified letters to this instance.
	 
	 @param persianLetter The Persian letter.
	 @param englishLetter The English letter.
	*/
	public final void Append(String persianLetter, char englishLetter)
	{
		getPersianLetters().add(persianLetter);
		getEnglishLetters().add(englishLetter);
	}

	/** 
	 Clones this instance.
	 
	 @return 
	*/
	public final PinglishString Clone()
	{
		PinglishString cloned = new PinglishString();
		cloned.getPersianLetters().addAll(this.getPersianLetters());
		cloned.getEnglishLetters().addAll(this.getEnglishLetters());
		return cloned;
	}

	public final PinglishString ToLower()
	{
		PinglishString cloned = Clone();
		for (int i = 0; i < getEnglishLetters().size(); i++)
		{
			cloned.getEnglishLetters().set(i, Character.toLowerCase(getEnglishLetters().get(i)));
		}

		return cloned;
	}

	@Override
	public boolean equals(Object obj)
	{
		PinglishString objB = (PinglishString)((obj instanceof PinglishString) ? obj : null);
		if (objB==null)
		{
			return false;
		}

		if (this.getEnglishLetters().size() != objB.getEnglishLetters().size())
		{
			return false;
		}

		if (getPersianLetters().size() != objB.getPersianLetters().size())
		{
			return false;
		}

		for (int i = 0; i < this.getEnglishLetters().size(); i++)
		{
			if (this.getEnglishLetters().get(i) != objB.getEnglishLetters().get(i))
			{
				return false;
			}
		}

		for (int i = 0; i < this.getPersianLetters().size(); i++)
		{
			if (!this.getPersianLetters().get(i).equals(objB.getPersianLetters().get(i)))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		return (getEnglishLetters().hashCode() * getPersianLetters().hashCode());
	}
}