package SCICT.NLP.Utility.WordContainer;

import SCICT.NLP.Utility.*;

/**
 Node structure which can store words' usage frequency
*/
public class NodeWithFreq
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Members

	private final java.util.TreeMap<Character, NodeWithFreq> m_links = new java.util.TreeMap<Character, NodeWithFreq>();
	protected int m_freq;
	protected char m_value;
	protected boolean m_isEndLetter;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/**
	 Class Constructor
	
	@param letter character value
	@param isEndLetter Current letter is the final letter of corresponding word?
	@param count Usage frequency of word
	*/
	public NodeWithFreq(char letter, boolean isEndLetter, int count)
	{
		this.m_value = letter;
		this.m_isEndLetter = isEndLetter;

		this.m_freq = count;
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
	
	@param c child's unicode value
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
	public final NodeWithFreq AddLink(NodeWithFreq node)
	{
		if (!this.m_links.containsKey(node.m_value))
		{
			this.m_links.put(node.m_value, node);
		}
		else if (node.getIsEndOfWord())
		{
			this.m_links.get(node.m_value).m_isEndLetter = node.getIsEndOfWord();
			this.m_links.get(node.m_value).m_freq = this.m_links.get(node.m_value).m_freq > node.getWordFrequency() ? this.m_links.get(node.m_value).m_freq : node.getWordFrequency();
		}

		//if (node.IsEndOfWord && !this.m_links[node.m_value].m_isEndLetter)

		return this.m_links.get(node.m_value);
	}

	/**
	 Get a pointer to a child node by child value
	
	@param letter Child's unicode value
	@return Node pointer to child node
	*/
	public final NodeWithFreq GetNextNode(char letter)
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
	public final NodeWithFreq[] GetLinks()
	{
		java.util.ArrayList<NodeWithFreq> nodes = new java.util.ArrayList<NodeWithFreq>();
		Iterable<NodeWithFreq> itarator= this.m_links.values();
		for (NodeWithFreq link :itarator)
		{
			nodes.add(link);
		}

		return nodes.toArray(new NodeWithFreq[0]);
	}

	/**
	 Remove a child node
	
	@param node Child node
	*/
	public final void RemoveLink(NodeWithFreq node)
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


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}