package SCICT.NLP.Utility.WordContainer;

import java.io.File;
import java.io.IOException;

import Helper.LinqSimulationArrayList;
import SCICT.NLP.Persian.Constants.PersianPOSTag;
import SCICT.Utility.IO.DictionaryWordFreqPOSLoader;
import SCICT.Utility.IO.FileTools;

/**
 A data structure for efficient management of words, This structure is a character level tree.
*/
public class WordFreqPOSContainerTree
{
	protected String m_dictionaryFileName;
	protected long m_wordsCount;

	private final DictionaryWordFreqPOSLoader m_dicWFPOS = new DictionaryWordFreqPOSLoader();

	private NodeWithFreqandPOS m_root;

	private long m_freqSummation;

	/**
	 Class Constructor
	*/
	public WordFreqPOSContainerTree()
	{
		m_root = new NodeWithFreqandPOS('*', false, 0, PersianPOSTag.UserPOS.toString());
	}

	/**
	 Class Constructor
	
	@param wordContainerConfig Configuration
	@exception Exception
	*/
	public WordFreqPOSContainerTree(WordContainerTreeConfig wordContainerConfig)
	{
		this();
		m_dictionaryFileName = wordContainerConfig.DictionaryFileName;

		if (!this.Load())
		{
			throw new RuntimeException("Could not load dictionary file!");
		}

	}
	private void AddWordToMemory(String word, int freq, String pos)
	{
		NodeWithFreqandPOS curNode = this.m_root;
		int i = 0, length = word.length();
		char[]charArray=word.toCharArray();
		for (char c : charArray)
		{
			if (++i == length)
			{
				curNode = curNode.AddLink(GenerateNode(c, freq, true, pos));

				//add nubmer of dictionary words
				this.m_freqSummation += freq;

				++this.m_wordsCount;
			}
			else
			{
				curNode = curNode.AddLink(GenerateNode(c, 0, false, pos));
			}
		}
	}

	private boolean AddWordToFile(String word, int freq, String pos)
	{
		return m_dicWFPOS.AddTerm(word, freq, pos);
	}

	protected final boolean AddWordToFile(String word, int freq, String pos, String fileName)
	{
		return m_dicWFPOS.AddTerm(word, freq, pos, fileName);
	}

	private void RemoveFromMemory(String word)
	{
		NodeWithFreqandPOS leaf = IndexOf(word);
		if (leaf != null)
		{
			leaf.LogicalRemove();
		}
	}

	private void RemoveFromFile(String word)
	{
		try
		{
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//			using (FileStream fstream = new FileStream(this.m_dictionaryFileName, FileMode.Open, FileAccess.ReadWrite))
			File fstream = new File(this.m_dictionaryFileName);
			try
			{
				long offset = FileTools.GetWordStartPositionInFile(fstream, word);

				if (offset == -1)
				{
					//throw new Exception("Word was in the dictionary, but not in the file.");
					return;
				}

				FileTools.RemoveLineFromPosition(fstream, offset);
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally
			{
				
			}
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	private boolean Load()
	{
		if (!m_dicWFPOS.LoadFile(m_dictionaryFileName))
		{
			return false;
		}

		String word = null, pos = null;
		int freq = 0;
		while (!m_dicWFPOS.getEndOfStream())
		{
			Helper.RefObject<String> tempRef_word = new Helper.RefObject<String>(word);
			Helper.RefObject<Integer> tempRef_freq = new Helper.RefObject<Integer>(freq);
			Helper.RefObject<String> tempRef_pos = new Helper.RefObject<String>(pos);
			m_dicWFPOS.NextTerm(tempRef_word, tempRef_freq, tempRef_pos);
			word = tempRef_word.argvalue;
			freq = tempRef_freq.argvalue;
			pos = tempRef_pos.argvalue;
			this.AddWordToMemory(word, freq, pos);
		}

		m_dicWFPOS.CloseFile();

		return true;
	}

	private static void Clear(NodeWithFreqandPOS node)
	{
		for (NodeWithFreqandPOS curNode : node.GetLinks())
		{
			Clear(curNode);
		}

		node.Clear();
	}

	/** 
	 Teaverse Tree on Depth First Type
	 
	 @return 
	*/
	private String[] DoDFSTraverse(NodeWithFreqandPOS node)
	{
		if (node.HaveLinks())
		{
			LinqSimulationArrayList<String> retDFS = new LinqSimulationArrayList<String>();
			for (NodeWithFreqandPOS curNode : node.GetLinks())
			{
				retDFS.addAll(DoDFSTraverse(curNode));
			}

			java.util.ArrayList<String> suggestions = new java.util.ArrayList<String>();
			if (node.getValue() != this.m_root.getValue())
			{
				if (node.getIsEndOfWord())
				{
					suggestions.add((new StringBuilder()).append(node.getValue()).toString());
				}

				StringBuilder sb = new StringBuilder();
				for (String str : retDFS)
				{
					sb.append(str);
					sb.append(node.getValue());
					suggestions.add(sb.toString());
					sb.delete(0, sb.length());
				}

				return suggestions.toArray(new String[0]);
			}
			else
			{
				return retDFS.toArray(new String[0]);
			}
		}

		if (node.getIsEndOfWord())
		{
			return new String[] {(new StringBuilder()).append(node.getValue()).toString()};
		}
		else
		{
			return new String[0];
		}
	}

	/** 
	 Teaverse Tree on Breath First Type
	 
	 @return 
	*/
	private String[] DoBFSTraverse(NodeWithFreqandPOS node)
	{
		if (node.HaveLinks())
		{
			java.util.ArrayList<String> suggestions = new java.util.ArrayList<String>();
			StringBuilder sb = new StringBuilder();
			for (NodeWithFreqandPOS curNode : node.GetLinks())
			{
				sb.append(DoDFSTraverse(curNode));
				suggestions.add(sb.toString());
				sb.delete(0, sb.length());
			}

			return suggestions.toArray(new String[0]);
		}

		return new String[] {(new Character(node.getValue())).toString()};
	}

	/** 
	 Utility Function, Generate Node from Letter
	 
	 @param c Letter
	 @param freq Usage frequency
	 @param isEndLetter end of word
	 @param pos POS tag of word
	 @return 
	*/
	private static NodeWithFreqandPOS GenerateNode(char c, int freq, boolean isEndLetter, String pos)
	{
		return new NodeWithFreqandPOS(c, isEndLetter, freq, pos);
	}
	
	/** 
	 Check if a word exists
	 
	 @param word Word
	 @return If the dictionary contains the word, returns true, else returns false.
	*/
	public final boolean Contain(String word)
	{
		NodeWithFreqandPOS leaf = IndexOf(word);

		if (leaf == null || !leaf.getIsEndOfWord())
		{
			return false;
		}

		return true;
	}

	/** 
	 Check if a word exists
	 
	 @param word Word
	 @param freq Word's usage frequency
	 @return If the dictionary contains the word, returns true, else returns false.
	*/
	public final boolean Contain(String word, Helper.RefObject<Integer> freq)
	{
		freq.argvalue = 0;
		NodeWithFreqandPOS leaf = IndexOf(word);

		if (leaf == null || !leaf.getIsEndOfWord())
		{
			return false;
		}
		freq.argvalue = leaf.getWordFrequency();
		return true;
	}

	/** 
	 Check if a word exists
	 
	 @param word Word
	 @param posTag Word's POS tag
	 @return If the dictionary contains the word, returns true, else returns false.
	*/
	public final boolean Contain(String word, Helper.RefObject<PersianPOSTag> posTag,Object redundunt)
	{
		posTag.argvalue = PersianPOSTag.UserPOS;

		NodeWithFreqandPOS leaf = IndexOf(word);

		if (leaf == null || !leaf.getIsEndOfWord())
		{
			return false;
		}

		posTag.argvalue = PersianPOSTag.ToEnum(leaf.getPOSTag());

		return true;
	}

	/** 
	 Check if a word exists
	 
	 @param word Word
	 @param freq Word's usage frequency
	 @param posTag Word's POS tag
	 @return If the dictionary contains the word, returns true, else returns false.
	*/
	public final boolean Contain(String word, Helper.RefObject<Integer> freq, Helper.RefObject<PersianPOSTag> posTag)
	{
		freq.argvalue = 0;
		posTag.argvalue = PersianPOSTag.UserPOS;

		NodeWithFreqandPOS leaf = IndexOf(word);

		if (leaf == null || !leaf.getIsEndOfWord())
		{
			return false;
		}
		freq.argvalue = leaf.getWordFrequency();

		posTag.argvalue = PersianPOSTag.ToEnum(leaf.getPOSTag());

		return true;
	}

	/** 
	 Add a word to tree
	 
	 @param word Word
	 @param freq Word's usage frequency
	 @param posTag Word's pos
	*/
	public final boolean AddWord(String word, int freq, PersianPOSTag posTag)
	{
		try
		{
			int existingFreq = 0;
			PersianPOSTag existingPOS = PersianPOSTag.forValue(0);
			Helper.RefObject<Integer> tempRef_existingFreq = new Helper.RefObject<Integer>(existingFreq);
			Helper.RefObject<PersianPOSTag> tempRef_existingPOS = new Helper.RefObject<PersianPOSTag>(existingPOS);
			boolean tempVar = this.Contain(word, tempRef_existingFreq, tempRef_existingPOS);
				existingFreq = tempRef_existingFreq.argvalue;
			existingPOS = tempRef_existingPOS.argvalue;
			if (tempVar)
			{
				if (existingPOS.Has(posTag) && existingFreq == freq)
				{
					return false;
				}
				else
				{
					RemoveFromFile(word);
				}
			}

			AddWordToMemory(word, freq, posTag.toString());
			AddWordToFile(word, freq, posTag.toString());

			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Add a word to tree
	 
	 @param word Word
	 @param freq Word's usage frequency
	 @param posTag Word's pos
	*/
	public final boolean AddWordBlind(String word, int freq, PersianPOSTag posTag)
	{
		try
		{
			AddWordToMemory(word, freq, posTag.toString());
			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Add a word to tree
	 
	 @param word Word
	 @param freq Word's usage frequency
	 @param posTag Word's pos
	 @param fileName File name
	*/
	public final boolean AddWord(String word, int freq, PersianPOSTag posTag, String fileName)
	{
		try
		{
			int existingFreq = 0;
			PersianPOSTag existingPOS = PersianPOSTag.forValue(0);
			Helper.RefObject<Integer> tempRef_existingFreq = new Helper.RefObject<Integer>(existingFreq);
			Helper.RefObject<PersianPOSTag> tempRef_existingPOS = new Helper.RefObject<PersianPOSTag>(existingPOS);
			boolean tempVar = this.Contain(word, tempRef_existingFreq, tempRef_existingPOS);
				existingFreq = tempRef_existingFreq.argvalue;
			existingPOS = tempRef_existingPOS.argvalue;
			if (tempVar)
			{
				if (existingPOS.Has(posTag) && existingFreq == freq)
				{
					return false;
				}
				else
				{
					RemoveFromFile(word);
				}
			}

			AddWordToMemory(word, freq, posTag.toString());
			return AddWordToFile(word, freq, posTag.toString(), fileName);
		}
		catch (RuntimeException ex)
		{
			return false;
		}
	}

	/** 
	 Remove word from Dictinary
	 
	 @param word Word
	*/
	public final void RemoveWord(String word)
	{
		try
		{
			RemoveFromFile(word);
			RemoveFromMemory(word);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/**
	 Append a new dictionary
	
	@param fileName Absolute path of dictionary
	@return True if successfully added
	@exception Exception
	*/
	public final int AppendDictionary(String fileName)
	{
		if (!m_dicWFPOS.LoadFile(fileName))
		{
			return 0;
		}

		String word = null, pos = null;
		int freq = 0;
		int extractedWordCount = 0;
		while (!m_dicWFPOS.getEndOfStream())
		{
			Helper.RefObject<String> tempRef_word = new Helper.RefObject<String>(word);
			Helper.RefObject<Integer> tempRef_freq = new Helper.RefObject<Integer>(freq);
			Helper.RefObject<String> tempRef_pos = new Helper.RefObject<String>(pos);
			m_dicWFPOS.NextTerm(tempRef_word, tempRef_freq, tempRef_pos);
			word = tempRef_word.argvalue;
			freq = tempRef_freq.argvalue;
			pos = tempRef_pos.argvalue;
			this.AddWordToMemory(word, freq, pos);
			extractedWordCount++;
		}

		return extractedWordCount;
	}

	/** 
	 Word frequency
	 
	 @param word word
	 @return usage frequency
	*/
	public final int WordFrequency(String word)
	{
		NodeWithFreqandPOS leaf = IndexOf(word);

		if (leaf == null || !leaf.getIsEndOfWord())
		{
			return 0;
		}

		return leaf.getWordFrequency();
	}

	/** 
	 Word pos
	 
	 @param word word
	 @return POS tag
	*/
	public final PersianPOSTag WordPOS(String word)
	{
		NodeWithFreqandPOS leaf = IndexOf(word);

		if (leaf == null || !leaf.getIsEndOfWord())
		{
			return PersianPOSTag.forValue(0);
		}

		return PersianPOSTag.ToEnum(leaf.getPOSTag());
	}

	/**
	 Total frequency
	*/
	public final long getFreqSummation()
	{
		return this.m_freqSummation;
	}

	/**
	 Number of existing words
	*/
	public final long getDictionaryWordsCount()
	{
		return this.m_wordsCount;
	}

	/**
	 Clear all words
	*/
	public final void Clear()
	{
		Clear(this.m_root);
	}


	protected final NodeWithFreqandPOS IndexOf(String subWord)
	{
		NodeWithFreqandPOS curNode = this.m_root;
		if(subWord==null||subWord=="")
			return null;
		
		char[]charArray=subWord.toCharArray();
		
		for (char c : charArray)
		{
			if (curNode != null)
			{
				curNode = curNode.GetNextNode(c);
			}
		}

		if (curNode == this.m_root)
		{
			curNode = null;
		}

		return curNode;
	}

	protected final String[] TraverseFrom(NodeWithFreqandPOS node)
	{
		String[] words = DoDFSTraverse(node);
		java.util.ArrayList<String> strList = new java.util.ArrayList<String>();
		for (String str : words)
		{
			strList.add(ReverseString(str));
		}
		return strList.toArray(new String[0]);
	}

	/** 
	 Traverse Tree to Retrieve All Words
	 
	 @param traversType Traversal Type
	 @return All Words
	*/
	protected final String[] TraverseTree(TreeTraveralType traversType)
	{
		if (traversType == TreeTraveralType.BFS)
		{
			return DoBFSTraverse(this.m_root);
		}
		else if (traversType == TreeTraveralType.DFS)
		{
			String[] words = DoDFSTraverse(this.m_root);
			java.util.ArrayList<String> strList = new java.util.ArrayList<String>();
			for (String str : words)
			{
				strList.add(ReverseString(str));
			}
			return strList.toArray(new String[0]);
		}

		return new String[0];
	}

	/** 
	 Utility Function, Reverse a word
	 
	 @return 
	*/
	protected final String ReverseString(String word)
	{
		StringBuilder sb = new StringBuilder();
		char[] letters = word.toCharArray();

		for (int i = letters.length - 1; i >= 0; --i)
		{
			sb.append(letters[i]);
		}

		return sb.toString();
	}
}