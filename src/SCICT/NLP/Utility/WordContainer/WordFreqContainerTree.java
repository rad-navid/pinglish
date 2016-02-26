package SCICT.NLP.Utility.WordContainer;

import java.io.File;
import java.io.IOException;

import Helper.LinqSimulationArrayList;
import SCICT.NLP.Persian.Constants.*;
import SCICT.Utility.IO.*;
import SCICT.Utility.*;
import SCICT.NLP.Utility.*;

/**
 A data structure for efficient management of words, This structure is a character level tree.
*/
public class WordFreqContainerTree
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Members

	protected String m_dictionaryFileName;
	protected long m_wordsCount;

	private final DictionaryWordFreqLoader m_dicWF = new DictionaryWordFreqLoader();

	private NodeWithFreq m_root;

	private long m_freqSummation;
	private long m_totalWordsNumber;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructor

	/**
	 Class Constructor
	*/
	public WordFreqContainerTree()
	{
		m_root = new NodeWithFreq('*', false, 0);
	}

	/**
	 Class Constructor
	
	@param wordContainerConfig Configuration
	@exception Exception
	*/
	public WordFreqContainerTree(WordContainerTreeConfig wordContainerConfig)
	{
		this();
		m_dictionaryFileName = wordContainerConfig.DictionaryFileName;

		if (!this.Load())
		{
			throw new RuntimeException("Could not load dictionary file!");
		}

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	private void AddWordToMemory(String word, int freq)
	{
		NodeWithFreq curNode = this.m_root;
		int i = 0, length = word.length();
		char[] charArray=word.toCharArray();
		for (char c : charArray)
		{
			if (++i == length)
			{
				curNode = curNode.AddLink(GenerateNode(c, freq, true));

				//add number of dictionary words
				this.m_freqSummation += freq;
				this.m_totalWordsNumber++;

				++this.m_wordsCount;
			}
			else
			{
				curNode = curNode.AddLink(GenerateNode(c, 0, false));
			}
		}
	}

	private void AddWordToFile(String word, int freq)
	{
		m_dicWF.AddTerm(word, freq);
	}

	private void RemoveFromMemory(String word)
	{
		NodeWithFreq leaf = IndexOf(word);
		if (leaf != null)
		{
			leaf.LogicalRemove();
		}
	}

	private void RemoveFromFile(String word)
	{
		try
		{
			File fstream = new File(this.m_dictionaryFileName);

			long offset = FileTools.GetWordStartPositionInFile(fstream, word);

			if (offset == -1)
			{
				//throw new Exception("Word was in the dictionary, but not in the file.");
				return;
			}

			FileTools.RemoveLineFromPosition(fstream, offset);

		}
		catch (RuntimeException ex)
		{
			throw ex;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean Load()
	{
		if (!m_dicWF.LoadFile(m_dictionaryFileName))
		{
			return false;
		}

		String word = null;
		int freq = 0;
		while (!m_dicWF.getEndOfStream())
		{
			Helper.RefObject<String> tempRef_word = new Helper.RefObject<String>(word);
			Helper.RefObject<Integer> tempRef_freq = new Helper.RefObject<Integer>(freq);
			m_dicWF.NextTerm(tempRef_word, tempRef_freq);
			word = tempRef_word.argvalue;
			freq = tempRef_freq.argvalue;
			this.AddWordToMemory(word, freq);
		}

		return true;
	}

	private static void Clear(NodeWithFreq node)
	{
		for (NodeWithFreq curNode : node.GetLinks())
		{
			Clear(curNode);
		}

		node.Clear();
	}

	/** 
	 Teaverse Tree on Depth First Type
	 
	 @return 
	*/
	private String[] DoDFSTraverse(NodeWithFreq node)
	{
		if (node.HaveLinks())
		{
			LinqSimulationArrayList<String> retDFS = new LinqSimulationArrayList<String>();
			for (NodeWithFreq curNode : node.GetLinks())
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
	private String[] DoBFSTraverse(NodeWithFreq node)
	{
		if (node.HaveLinks())
		{
			java.util.ArrayList<String> suggestions = new java.util.ArrayList<String>();
			StringBuilder sb = new StringBuilder();
			for (NodeWithFreq curNode : node.GetLinks())
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
	 @return 
	*/
	private static NodeWithFreq GenerateNode(char c, int freq, boolean isEndLetter)
	{
		return new NodeWithFreq(c, isEndLetter, freq);
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Members

	/** 
	 Check if a word exists
	 
	 @param word Word
	 @return If the dictionary contains the word, returns true, else returns false.
	*/
	public final boolean Contain(String word)
	{
		NodeWithFreq leaf = IndexOf(word);

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
		NodeWithFreq leaf = IndexOf(word);

		if (leaf == null || !leaf.getIsEndOfWord())
		{
			return false;
		}
		freq.argvalue = leaf.getWordFrequency();
		return true;
	}

	/** 
	 Add a word to tree
	 
	 @param word Word
	 @param freq Word's usage frequency
	*/
	public final boolean AddWord(String word, int freq)
	{
		try
		{
			if (this.Contain(word))
			{
				return false;
			}

			AddWordToMemory(word, freq);
			AddWordToFile(word, freq);

			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Remove a word from dictionary
	 
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
	public final boolean AppendDictionary(String fileName)
	{
		if (!m_dicWF.LoadFile(fileName))
		{
			return false;
		}

		String word = null;
		int freq = 0;
		while (!m_dicWF.getEndOfStream())
		{
			Helper.RefObject<String> tempRef_word = new Helper.RefObject<String>(word);
			Helper.RefObject<Integer> tempRef_freq = new Helper.RefObject<Integer>(freq);
			m_dicWF.NextTerm(tempRef_word, tempRef_freq);
			word = tempRef_word.argvalue;
			freq = tempRef_freq.argvalue;
			this.AddWordToMemory(word, freq);
		}

		return true;
	}

	/** 
	 Word frequency
	 
	 @param word word
	 @return usage frequency
	*/
	public final int WordFrequency(String word)
	{
		NodeWithFreq leaf = IndexOf(word);

		if (leaf == null || !leaf.getIsEndOfWord())
		{
			return 0;
		}

		return leaf.getWordFrequency();
	}

	/**
	 Total frequency
	*/
	public final long getFreqSummation()
	{
		return this.m_freqSummation;
	}

	/**
	 Total Number of Words
	*/
	public final long getTotalNumberofWords()
	{
		return this.m_totalWordsNumber;
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

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected

	protected final NodeWithFreq IndexOf(String subWord)
	{
		NodeWithFreq curNode = this.m_root;
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

	protected final String[] TraverseFrom(NodeWithFreq node)
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


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}