package SCICT.Utility.SpellChecker;

import Helper.LinqSimulationArrayList;

/**
 Log user's activity and sort by frequent of usage in each session
*/
public class SessionLogger
{
	//private Dictionary<string, int> m_entry = new Dictionary<string, int>();
	private final java.util.ArrayList<String> m_entry = new java.util.ArrayList<String>();

	/**
	 Add a usage log
	
	@param word Word
	*/
	public final void AddUsage(String word)
	{
		if (!this.m_entry.contains(word))
		{
			this.m_entry.add(word);
		}
		else
		{
			this.m_entry.remove(word);
			this.m_entry.add(word);
		}
	}

	/**
	 Sort a list of word by usage frequency
	
	@param words Word
	@return Sorted List
	*/
	public final String[] Sort(String[] words)
	{
		Helper.RefObject<String[]> tempRef_words = new Helper.RefObject<String[]>(words);
		String[] existingWords = ExtractModestUsedWordsFromList(tempRef_words);
		words = tempRef_words.argvalue;

		LinqSimulationArrayList<String> finalList = new LinqSimulationArrayList<String>(existingWords);
		finalList.addAll(words);

		return finalList.toArray(new String[0]);
	}

	private String[] ExtractModestUsedWordsFromList(Helper.RefObject<String[]> words)
	{
		LinqSimulationArrayList<String> wordList = new LinqSimulationArrayList<String>(words.argvalue);
		java.util.ArrayList<String> existingWords = new java.util.ArrayList<String>();

		//foreach (KeyValuePair<string, int> pair in this.m_entry)
		for (String str : this.m_entry)
		{
			if (wordList.contains(str))
			{
				existingWords.add(str);
				wordList.remove(str);
			}
		}

		words.argvalue = wordList.toArray(new String[0]);
		java.util.Collections.reverse(existingWords);
		return existingWords.toArray(new String[0]);
	}

}