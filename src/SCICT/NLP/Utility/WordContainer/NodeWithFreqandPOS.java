package SCICT.NLP.Utility.WordContainer;

import Helper.LinqSimulationArrayList;
import Helper.StringExtensions;
import SCICT.NLP.Utility.*;

/**
 Node structure which can store words' usage frequency and POS tag
*/
public class NodeWithFreqandPOS
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Members

	private final java.util.TreeMap<Character, NodeWithFreqandPOS> m_links = new java.util.TreeMap<Character, NodeWithFreqandPOS>();
	protected char m_value;
	protected boolean m_isEndLetter;

	protected int m_freq;
	protected String m_pos;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/////<summary>
	///// Class Constructor
	/////</summary>
	/////<param name="letter">character value</param>
	//public NodeWithFreq(char letter) : 
	//    base(letter){}

	/////<summary>
	///// Class Constructor
	/////</summary>
	/////<param name="letter">character value</param>
	/////<param name="isEndLetter">Current letter is the final letter of corresponding word?</param>
	//public NodeWithFreq(char letter, bool isEndLetter) :
	//    base(letter, isEndLetter){}

	/**
	 Class Constructor
	
	@param letter character value
	@param isEndLetter Current letter is the final letter of corresponding word?
	@param count Usage frequency of word
	@param pos POS tag
	*/
	public NodeWithFreqandPOS(char letter, boolean isEndLetter, int count, String pos)
	{
		this.m_value = letter;
		this.m_isEndLetter = isEndLetter;

		this.m_freq = count;
		this.m_pos = pos;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/**
	 Unicode value of current node
	*/
	public final char getValue()
	{
		return this.m_value;
	}

	/**
	 Number of childs
	*/
	public final int getLinkCount()
	{
		return this.m_links.size();
	}

	/**
	 Search for having a child
	
	@param c child's Unicode value
	@return True if contain child, otherwise False
	*/
	public final boolean ContainLetter(char c)
	{
		return this.m_links.containsKey(c);
	}

	/**
	 Check if current node have any child
	
	@return True if having any child, otherwise False
	*/
	public final boolean HaveLinks()
	{
		return this.m_links.isEmpty() ? false : true;
	}

	/**
	 Check if current node is final letter of a word
	*/
	public final boolean getIsEndOfWord()
	{
		return this.m_isEndLetter;
	}


	/**
	 Usage frequency of word (if current node is final letter of word)
	*/
	public final int getWordFrequency()
	{
		return this.m_freq;
	}

	/**
	 Add a child node
	
	@param node Child node
	@return Node pointer to added child node
	*/
	public final NodeWithFreqandPOS AddLink(NodeWithFreqandPOS node)
	{
		if (!this.m_links.containsKey(node.m_value))
		{
			this.m_links.put(node.m_value, node);
		}
		else if (node.getIsEndOfWord())
		{
			this.m_links.get(node.m_value).m_isEndLetter = node.getIsEndOfWord();
			this.m_links.get(node.m_value).m_freq = this.m_links.get(node.m_value).m_freq > node.getWordFrequency() ? this.m_links.get(node.m_value).m_freq : node.getWordFrequency();

			if (!this.m_links.get(node.m_value).m_pos.equals(node.m_pos))
			{
				this.m_links.get(node.m_value).m_pos = mergePOS(node.m_pos, this.m_links.get(node.m_value).m_pos);
			}
		}

		//if (node.IsEndOfWord && !this.m_links[node.m_value].m_isEndLetter)

		return this.m_links.get(node.m_value);
	}

	private String mergePOS(String posStr1, String posStr2)
	{
		String[] posList1 = StringExtensions.SplitRemoveEmptyElements(posStr1,new String(new char[] {',', ' '}));
		String[] posList2 = StringExtensions.SplitRemoveEmptyElements(posStr2,new String(new char[] {',', ' '}));

		LinqSimulationArrayList<String> finalList = new LinqSimulationArrayList<String>();

		finalList.addAll(posList1);
		finalList.addAll(posList2);

		finalList = finalList.Distinct("");

		String finalStr = "";
		for (String pos : finalList)
		{
			finalStr += pos + ", ";
		}

		finalStr = finalStr.substring(0, finalStr.length() - 2) + finalStr.substring(finalStr.length() - 2 + 2);

		return finalStr;
	}

	/**
	 Get a pointer to a child node by child value
	
	@param letter Child's unicode value
	@return Node pointer to child node
	*/
	public final NodeWithFreqandPOS GetNextNode(char letter)
	{
		if (this.ContainLetter(letter))
		{
			return this.m_links.get(letter);
		}

		return null;
	}

	/**
	 Get all child nodes
	
	@return Child nodes
	*/
	public final NodeWithFreqandPOS[] GetLinks()
	{
		java.util.ArrayList<NodeWithFreqandPOS> nodes = new java.util.ArrayList<NodeWithFreqandPOS>();
		Iterable<NodeWithFreqandPOS> itarator= this.m_links.values();
		for (NodeWithFreqandPOS link :itarator)
		{
			nodes.add(link);
		}

		return nodes.toArray(new NodeWithFreqandPOS[0]);
	}

	/**
	 Remove a child node
	
	@param node Child node
	*/
	public final void RemoveLink(NodeWithFreqandPOS node)
	{
		if (this.m_links.containsKey(node.m_value))
		{
			this.m_links.remove(node.m_value);
		}
	}

	/**
	 Logically remove current word (if current node is final letter of a word)
	*/
	public final void LogicalRemove()
	{
		this.m_isEndLetter = false;
	}

	/**
	 Remove current word and all the words that start with this word (if current node is final letter of a word)
	*/
	public final void Clear()
	{
		LogicalRemove();
		this.m_links.clear();
	}

	/**
	 POS tag of of word (if current node is final letter of word)
	*/
	public final String getPOSTag()
	{
		return this.m_pos;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}