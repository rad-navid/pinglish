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
public class WordContainerTree
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Members

	protected String m_dictionaryFileName;
	protected long m_wordsCount;

	private final DictionaryWordLoader m_dicW = new DictionaryWordLoader();

	private Node m_root;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructor

	/**
	 Class constructor
	*/
	public WordContainerTree()
	{
		this.m_root = new Node('*');
	}

	/**
	 Class Constructor
	
	@param wordContainerConfig Configuration
	@exception Exception
	*/
	public WordContainerTree(WordContainerTreeConfig wordContainerConfig)
	{
		this();
		this.m_dictionaryFileName = wordContainerConfig.DictionaryFileName;

		if (!this.Load())
		{
			throw new RuntimeException("Could not load dictionary file!");
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	private void AddWordToMemory(String word)
	{
		Node curNode = this.m_root;
		int i = 0, length = word.length();
		char[]charArray=word.toCharArray();
		for (char c : charArray)
		{
			if (++i == length)
			{
				curNode = curNode.AddLink(GenerateNode(c, true));

				//add nubmer of dictionary words
				++this.m_wordsCount;
			}
			else
			{
				curNode = curNode.AddLink(GenerateNode(c, false));
			}
		}
	}

	private void AddWordToFile(String word)
	{
		m_dicW.AddTerm(word);
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

	private void RemoveFromMemory(String word)
	{
		Node leaf = IndexOf(word);
		if (leaf != null)
		{
			leaf.LogicalRemove();
		}
	}

	/** 
	 Teaverse Tree on Depth First Type
	 
	 @return 
	*/
	private static void Clear(Node node)
	{
		for (Node curNode : node.GetLinks())
		{
			Clear(curNode);
		}

		node.Clear();
	}

	/** 
	 Teaverse Tree on Depth First Type
	 
	 @return 
	*/
	private String[] DoDFSTraverse(Node node)
	{
		if (node.HaveLinks())
		{
			LinqSimulationArrayList<String> retDFS = new LinqSimulationArrayList<String>();
			for (Node curNode : node.GetLinks())
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
	private String[] DoBFSTraverse(Node node)
	{
		if (node.HaveLinks())
		{
			java.util.ArrayList<String> suggestions = new java.util.ArrayList<String>();
			StringBuilder sb = new StringBuilder();
			for (Node curNode : node.GetLinks())
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
	 @param isEndLetter
	 @return 
	*/
	private static Node GenerateNode(char c, boolean isEndLetter)
	{
		return new Node(c, isEndLetter);
	}

	private boolean Load()
	{
		if (!m_dicW.LoadFile(m_dictionaryFileName))
		{
			return false;
		}

		String word = null;
		while (!m_dicW.getEndOfStream())
		{
			Helper.RefObject<String> tempRef_word = new Helper.RefObject<String>(word);
			m_dicW.NextTerm(tempRef_word);
			word = tempRef_word.argvalue;
			this.AddWordToMemory(word);
		}

		return true;
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
		Node leaf = IndexOf(word);

		if (leaf == null || !leaf.getIsEndOfWord())
		{
			return false;
		}

		return true;
	}

	/** 
	 Add a word to tree
	 
	 @param word Word
	*/
	public final boolean AddWord(String word)
	{
		try
		{
			if (this.Contain(word))
			{
				return false;
			}

			AddWordToMemory(word);
			AddWordToFile(word);

			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
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
	public final boolean AppendDictionary(String fileName)
	{
		if (!m_dicW.LoadFile(fileName))
		{
			return false;
		}

		String word = null;
		while (!m_dicW.getEndOfStream())
		{
			Helper.RefObject<String> tempRef_word = new Helper.RefObject<String>(word);
			m_dicW.NextTerm(tempRef_word);
			word = tempRef_word.argvalue;
			this.AddWordToMemory(word);
		}

		return true;
	}

	/**
	 Clear all words
	*/
	public final void Clear()
	{
		Clear(this.m_root);
	}

	/**
	 Number of existing words
	*/
	public final long getDictionaryWordsCount()
	{
		return this.m_wordsCount;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected

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

	protected final Node IndexOf(String subWord)
	{
		Node curNode = this.m_root;
		char[] array=subWord.toCharArray();
		for (char c : array)
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

	protected final String[] TraverseFrom(Node node)
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
	 Traverse tree to retrieve all words
	 
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

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}