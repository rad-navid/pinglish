package SCICT.NLP.Utility.WordContainer;

import java.util.Collection;

import SCICT.NLP.Utility.*;

// Virastyar
// http://www.virastyar.ir
// Copyright (C) 2011 Supreme Council for Information and Communication Technology (SCICT) of Iran
// 
// This file is part of Virastyar.
// 
// Virastyar is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Virastyar is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Virastyar.  If not, see <http://www.gnu.org/licenses/>.
// 
// Additional permission under GNU GPL version 3 section 7
// The sole exception to the license's terms and requierments might be the
// integration of Virastyar with Microsoft Word (any version) as an add-in.


/**
 Simple character node
*/
public class Node
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Members

	private final java.util.TreeMap<Character, Node> m_links = new java.util.TreeMap<Character, Node>();
	protected char m_value;
	protected boolean m_isEndLetter;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/**
	 Class Constructor
	
	@param letter character value
	*/
	public Node(char letter)
	{
		this.m_value = letter;
	}

	/**
	 Class Constructor
	
	@param letter character value
	@param isEndLetter Current letter is the final letter of corresponding word?
	*/
	public Node(char letter, boolean isEndLetter)
	{
		this.m_value = letter;
		this.m_isEndLetter = isEndLetter;
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
	 Remove a child node
	
	@param node Child node
	*/
	public final void RemoveLink(Node node)
	{
		if (this.m_links.containsKey(node.m_value))
		{
			this.m_links.remove(node.m_value);
		}
	}

	/**
	 Remove a child by value
	
	@param c Child's Unicode value
	*/
	public final void RemoveLink(char c)
	{
		if (this.m_links.containsKey(c))
		{
			this.m_links.remove(c);
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
	 Add a child node
	
	@param node Child node
	@return Node pointer to added child node
	*/
	public final Node AddLink(Node node)
	{
		if (!this.m_links.containsKey(node.m_value))
		{
			this.m_links.put(node.m_value, node);
		}
		//if (node.IsEndOfWord && !this.m_links[node.m_value].m_isEndLetter)
		if (node.getIsEndOfWord())
		{
			this.m_links.get(node.m_value).m_isEndLetter = node.getIsEndOfWord();
		}

		return this.m_links.get(node.m_value);
	}

	/**
	 Get a pointer to a child node by child value
	
	@param letter Child's Unicode value
	@return Node pointer to child node
	*/
	public final Node GetNextNode(char letter)
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
	public final Node[] GetLinks()
	{
		java.util.ArrayList<Node> nodes = new java.util.ArrayList<Node>();
		Iterable<Node> itarator= this.m_links.values();
		for (Node link :itarator)
		{
			nodes.add(link);
		}

		return nodes.toArray(new Node[0]);
	}

}