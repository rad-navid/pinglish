package SCICT.NLP.Utility.WordContainer;

import Helper.LinqSimulationArrayList;
import SCICT.NLP.Persian.Constants.PersianPOSTag;
import SCICT.NLP.Persian.Constants.PseudoSpace;

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
 A data structure for efficient management of words with auto complete feature, This structure is a character level tree.
*/
public class AutoCompleteWordContainerTree extends WordFreqPOSContainerTree
{
	/**
	 Class Constructor
	
	@param wordContainerConfig Configuration
	*/
	public AutoCompleteWordContainerTree(WordContainerTreeConfig wordContainerConfig)
	{
		super(wordContainerConfig);
	}

	/**
	 Complete the rest of incomplete word
	
	 @param subWord Incomplete word
	 @return Completed words start with incomplete word
	*/
	public final String[] Complete(String subWord)
	{
		NodeWithFreqandPOS node = IndexOf(subWord);
		if (node == null)
		{
			return new String[0];
		}

		String[] restOfWord = TraverseFrom(node);

		java.util.ArrayList<String> completeWords = new java.util.ArrayList<String>();
		//StringBuilder sb = new StringBuilder(subWord.Substring(0, subWord.Length - 1));
		//int start = subWord.Length - 1;
		String origstr = subWord.substring(0, subWord.length() - 1);
		for (String str : restOfWord)
		{
			//sb.Append(str);
			//completeWords.Add(sb.ToString());
			completeWords.add(origstr + str);
			//sb.Remove(start, str.Length);
		}

		return completeWords.toArray(new String[0]);
	}

	/**
	 Complete the rest of incomplete word
	
	 @param subWord Incomplete word
	@param count Number of returned suggestions
	 @return Completed words start with incomplete word
	*/
	public final String[] Complete(String subWord, int count)
	{
		NodeWithFreqandPOS node = IndexOf(subWord);
		if (node == null)
		{
			return new String[0];
		}

		String[] restOfWord = TraverseFrom(node);

		String origstr = subWord.substring(0, subWord.length() - 1);

		java.util.ArrayList<String> completeWords = new java.util.ArrayList<String>();

		for (int i = 0; i < Math.min(count, restOfWord.length); ++i)
		{
			completeWords.add(origstr + restOfWord[i]);
		}

		return completeWords.toArray(new String[0]);
	}

	/**
	 Complete the rest of incomplete word considering PseudoSpase after current part
	
	 @param subWord Incomplete word
	 @return Completed words start with incomplete word
	*/
	public final String[] CompleteWithPseudoSpase(String subWord)
	{
		String[] baseList = Complete(subWord);
		String[] pseudoList = Complete(subWord + PseudoSpace.ZWNJ);

		LinqSimulationArrayList<String> finalList = new LinqSimulationArrayList<String>();
		finalList.addAll(pseudoList);
		for (String str : baseList)
		{
			if (!finalList.contains(str))
			{
				finalList.add(str);
			}
		}

		return finalList.toArray(new String[0]);
	}

	/** 
	 Retrive all existing words
	 
	 @return All words
	*/
	public final String[] GetAllWords()
	{
		return TraverseTree(TreeTraveralType.DFS);
	}

	/**
	 Save Loaded Dictionaryt to File
	
	@param fileName File name
	@return 
	*/
	public final void SaveDictionaryToFile(String fileName)
	{
		String[] allWord = GetAllWords();

		int freq = 0;
		PersianPOSTag POS = PersianPOSTag.forValue(0);
		for (String str : allWord)
		{
			Helper.RefObject<Integer> tempRef_freq = new Helper.RefObject<Integer>(freq);
			Helper.RefObject<PersianPOSTag> tempRef_POS = new Helper.RefObject<PersianPOSTag>(POS);
			boolean tempVar = super.Contain(str, tempRef_freq, tempRef_POS);
				freq = tempRef_freq.argvalue;
			POS = tempRef_POS.argvalue;
			if (tempVar)
			{
				AddWordToFile(str, freq, POS.toString(), fileName);
			}
		}
	}
}