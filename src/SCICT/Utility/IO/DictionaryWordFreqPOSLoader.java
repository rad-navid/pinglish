package SCICT.Utility.IO;

import SCICT.Utility.*;

/**
 Load words, usage frequency and POS tag from dictionary file
*/
public class DictionaryWordFreqPOSLoader extends DictionaryLoader
{
	/**
	 Pars line's content
	
	@param word Extracted word
	@param freq Extracted word's usage frequency
	@param pos Extracted word's POS tag
	@return True if word successfully extracted, otherwise False
	*/
	public final boolean NextTerm(Helper.RefObject<String> word, Helper.RefObject<Integer> freq, Helper.RefObject<String> pos)
	{
		word.argvalue = "";
		freq.argvalue = 0;
		pos.argvalue = "";

		String line = null;
		Helper.RefObject<String> tempRef_line = new Helper.RefObject<String>(line);
		boolean tempVar = !NextLine(tempRef_line);
			line = tempRef_line.argvalue;
		
		if (tempVar)
		{
			return false;
		}

		String[] terms = line.split("[\\t]", -1);

		if (terms.length >= 3)
		{
			if (terms[0].length() > 0 && terms[1].length() > 0 && terms[2].length() > 0)
			{
				word.argvalue = terms[0];
				pos.argvalue = terms[2];

				
				try{
					freq.argvalue=Integer.parseInt(terms[1]);
					return true;
				}
				catch(Exception ex)
				{
					
				}
			}
		}

		return false;
	}

	/**
	 Add a term to dictionary
	
	@param word word
	@param freq word's usage frequency
	@param pos word's POS tag
	@return True if word successfully added, otherwise False
	*/
	public final boolean AddTerm(String word, int freq, String pos)
	{
		if (!AddLine(word + "\t" + (new Integer(freq)).toString() + "\t" + pos))
		{
			return false;
		}

		return true;
	}

	/**
	 Add a term to dictionary
	
	@param word word
	@param freq word's usage frequency
	@param pos word's POS tag
	@param fileName File name
	@return True if word successfully added, otherwise False
	*/
	public final boolean AddTerm(String word, int freq, String pos, String fileName)
	{
		if (!AddLine(word + "\t" + (new Integer(freq)).toString() + "\t" + pos, fileName))
		{
			return false;
		}

		return true;
	}

}