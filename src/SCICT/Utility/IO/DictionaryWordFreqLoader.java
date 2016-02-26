package SCICT.Utility.IO;


/**
 Load words and usage frequency from dictionary file
*/
public class DictionaryWordFreqLoader extends DictionaryLoader
{
	/**
	 Pars line's content
	
	@param word Extracted word
	@param freq Extracted word's usage frequency
	@return True if word successfully extracted, otherwise False
	*/
	public final boolean NextTerm(Helper.RefObject<String> word, Helper.RefObject<Integer> freq)
	{
		word.argvalue = "";
		freq.argvalue = 0;

		String line = null;
		Helper.RefObject<String> tempRef_line = new Helper.RefObject<String>(line);
		boolean tempVar = !NextLine(tempRef_line);
			line = tempRef_line.argvalue;
		if (tempVar)
		{
			return false;
		}

		String[] terms = line.split("[\\t]", -1);

		if (terms.length >= 2)
		{
			if (terms[0].length() > 0 && terms[1].length() > 0)
			{
				word.argvalue = terms[0];

				try{
					freq.argvalue=Integer.parseInt(terms[1]);
					return true;
				}catch(Exception ex)
				{					}
				
			}
		}

		return false;
	}

	/**
	 Add a term to dictionary
	
	@param word word
	@param freq word's usage frequency
	@return True if word successfully added, otherwise False
	*/
	public final boolean AddTerm(String word, int freq)
	{
		if (!AddLine(word + "\t" + (new Integer(freq)).toString()))
		{
			return false;
		}

		return true;
	}

	/**
	 Add a term to dictionary
	
	@param word word
	@param freq word's usage frequency
	@param fileName File name
	@return True if word successfully added, otherwise False
	*/
	public final boolean AddTerm(String word, int freq, String fileName)
	{
		if (!AddLine(word + "\t" + (new Integer(freq)).toString(), fileName))
		{
			return false;
		}

		return true;
	}
}