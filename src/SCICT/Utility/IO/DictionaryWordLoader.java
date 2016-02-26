package SCICT.Utility.IO;

import SCICT.Utility.*;

/**
 Load words from dictionary file
*/
public class DictionaryWordLoader extends DictionaryLoader
{
	/**
	 Next dictionary term
	
	@param word Extracted word
	@return True if word successfully extracted, False if EOF
	*/
	public boolean NextTerm(Helper.RefObject<String> word)
	{
		word.argvalue = "";

		while (true)
		{
			String line = null;
			Helper.RefObject<String> tempRef_line = new Helper.RefObject<String>(line);
			boolean tempVar = !NextLine(tempRef_line);
				line = tempRef_line.argvalue;
			if (tempVar)
			{
				return false;
			}

			String[] terms = line.split("[\\t]", -1);

			if (terms.length >= 1)
			{
				if (terms[0].length() > 0)
				{
					word.argvalue = terms[0];

					return true;
				}
			}
		}
	}

	/**
	 Add a term to dictionary
	
	@param word word
	@return True if word successfully added, otherwise False
	*/
	public boolean AddTerm(String word)
	{
		if (!AddLine(word))
		{
			return false;
		}

		return true;
	}

	/**
	 Add a term to dictionary
	
	@param word word
	@param fileName File name
	@return True if word successfully added, otherwise False
	*/
	public boolean AddTerm(String word, String fileName)
	{
		if (!AddLine(word, fileName))
		{
			return false;
		}

		return true;
	}
}