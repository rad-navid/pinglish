package SCICT.Utility.SpellChecker;


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
 An ignore list used to ignore desired words from processing
*/
public class IgnoreList
{
	public static class IgnoreItemChangeEventArgs 
	{
		public String IgnoreItem;
	}

	private final java.util.HashSet<String> m_list = new java.util.HashSet<String>();

	/** 
	 Add a word to ignore list 
	 
	 @param word Input word
	 @return 
	*/
	public final boolean AddToIgnoreList(String word)
	{
		try
		{
			if (!this.m_list.contains(word))
			{
				this.m_list.add(word);
				return true;
			}

			return false;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Remove a word to ignore list 
	 
	 @param word Input word
	 @return 
	*/
	public final boolean RemoveFromIgnoreList(String word)
	{
		try
		{
			if (this.m_list.contains(word))
			{
				this.m_list.remove(word);
			}

			return false;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Clear ignore list
	 
	 @return 
	*/
	public final void ClearIgnoreList()
	{
		try
		{
			this.m_list.clear();
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 check for word existance in ignore list
	 
	 @param word
	 @return 
	*/
	public final boolean IsExistInIgnoreList(String word)
	{
		if (word.length() > 0)
		{
			return this.m_list.contains(word);
		}

		return false;
	}

	
	
}