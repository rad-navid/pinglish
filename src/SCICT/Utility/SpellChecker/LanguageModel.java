package SCICT.Utility.SpellChecker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import SCICT.NLP.Utility.StringUtil;

/**
 Generate a dictionary and freqency of usage of each word from text corpus
*/
public class LanguageModel
{
	private final java.util.HashMap<String, Integer> m_entry = new java.util.HashMap<String, Integer>();
	//private SortedDictionary<string, int> m_entry = new SortedDictionary<string, int>();

	/**
	 Add a word
	
	@param word Word
	*/
	public final void AddWord(String word)
	{
		if (!this.m_entry.containsKey(word))
		{
			// word does not exist
			this.m_entry.put(word, 1);
		}
		else
		{
			// word exist, add the count of word
			Integer key=this.m_entry.get(word);
			key=new Integer(key.intValue()+1);
		}
	}
	/**
	 Add word with usage frequency
	
	@param word Word
	@param freq Usage frequency
	*/
	public final void AddWord(String word, int freq)
	{
		if (!this.m_entry.containsKey(word))
		{
			// word does not exist
			this.m_entry.put(word, freq);
		}
		else
		{
			// word exist, add the count of word
			this.m_entry.put(word, freq);
		}
	}
	/**
	 Add alist of word
	
	@param wordList List of word
	*/
	public final void AddWord(String[] wordList)
	{
		for (String wrd : wordList)
		{
			if (!this.m_entry.containsKey(wrd))
			{
				// word does not exist
				this.m_entry.put(wrd, 1);
			}
			else
			{
				// word exist, add the count of word
				Integer key=this.m_entry.get(wrd);
				key=new Integer(key.intValue()+1);
			}
		}
	}
	/**
	 Add a text corpus
	
	@param text Text string
	*/
	public final void AddPlainText(String text)
	{
		String[] words = StringUtil.ExtractPersianWordsStandardized(text);

		for (String word : words)
		{
			if (!this.m_entry.containsKey(word))
			{
				// word does not exist
				this.m_entry.put(word, 1);
			}
			else
			{
				// word exist, add the count of word
				Integer key=this.m_entry.get(word);
				key=new Integer(key.intValue()+1);
			}
		}
	}
	/**
	 Save dictionary to disk
	
	@param fileName Absolute path of file
	@exception Exception
	*/
	public final void SaveOnDisk(String fileName)
	{
		try
		{
			if (fileName.length() > 0)
			{
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				for (java.util.Map.Entry<String, Integer> pair : this.m_entry.entrySet())
				{
					writer.write(pair.getKey() + " " + pair.getValue().toString());
					writer.newLine();
				}
				writer.close();
			}
			else
			{
				throw new RuntimeException("File name is not valid!");
			}
		}
		catch (RuntimeException ex)
		{
			throw ex;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 Save dictionary to disk
	
	@param fileName Absolute path of file
	@param append Append dictionary to existing file
	@exception Exception
	*/
	public final void SaveOnDisk(String fileName, boolean append)
	{
		try
		{
			if (fileName.length() > 0)
			{
				if (append)
				{
					(new DictionaryTools()).LoadDic(fileName, this.m_entry,null);
				}

				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				for (java.util.Map.Entry<String, Integer> pair : this.m_entry.entrySet())
				{
					writer.write(pair.getKey() + " " + pair.getValue().toString());
					writer.newLine();
				}
				writer.close();
			}
			else
			{
				throw new RuntimeException("File name is not valid!");
			}
		}
		catch (RuntimeException ex)
		{
			throw ex;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}