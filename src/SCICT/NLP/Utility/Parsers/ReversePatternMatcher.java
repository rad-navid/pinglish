package SCICT.NLP.Utility.Parsers;

import Helper.LinqSimulationArrayList;

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
 This class matches and finds patterns occuring in the end of a string.
 It makes use of some special wild-card symbols which suits the Persian language more.
 For a list of the possible wild-cards see the "Symbolic Character Constants" region of the code.
*/
public class ReversePatternMatcher
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields
	/** 
	 List of patterns that should be checked in the end of each input
	*/
	private LinqSimulationArrayList<String> listEndingPatterns = new LinqSimulationArrayList<String>();

	/** 
	 List of search node which provide the means for reading from each ending pattern
	 character by character or disable each node during the process.
	*/
	private java.util.ArrayList<SearchNode> listSearchNodes = new java.util.ArrayList<SearchNode>();

	/** 
	 An instance of the <see cref="ReverseStringReader"/> class that helps 
	 reading a string content in reverse order in linear time.
	*/
	private ReverseStringReader reverseStringReader = new ReverseStringReader();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods

	/** 
	 Sets the ending patterns from the sequence of strings provided.
	 
	 @param patterns The sequence of patterns to add.
	*/
	public final void SetEndingPatterns(Iterable<String> patterns)
	{
		listEndingPatterns.clear();
		for (String str : patterns)
		{
			AddEndingPattern(str, false);
		}

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		listEndingPatterns = listEndingPatterns.Distinct("");

		listSearchNodes = SearchNode.CreateSearchNodeList(listEndingPatterns);
	}

	/** 
	 Adds all possible pattern-combinations of an ending-pattern string 
	 to the list of ending patterns.
	 
	 @param pattern The pattern string to add
	*/
	private void AddEndingPattern(String pattern, boolean checkDuplicates)
	{
		String revPattern = ReverseString(pattern.trim());

		for (String str : GeneratePossiblePatterns(revPattern))
		{
			AddAtomicEndingPattern(str, checkDuplicates);
		}
	}

	/** 
	 Adds the ending pattern directly to the list of ending-patterns if it
	 has not been already added.
	 
	 @param pattern The pattern to add.
	*/
	private void AddAtomicEndingPattern(String pattern, boolean checkDuplicates)
	{
		//if (pattern.Length > 0 && !listEndingPatterns.Contains(pattern))
		//    listEndingPatterns.Add(pattern);
		if (pattern.length() > 0)
		{
			if (checkDuplicates)
			{
				if (!listEndingPatterns.contains(pattern))
				{
					listEndingPatterns.add(pattern);
				}
			}
			else
			{
				listEndingPatterns.add(pattern);
			}
		}
	}

	/** 
	 Generates all possible non-optional patterns from a given pattern.
	 e.g. A(Space*)B  --> AB , A(Space+)B
	 
	 @param pat The input pattern.
	 @return 
	*/
	private Iterable<String> GeneratePossiblePatterns(String pat)
	{
		java.util.ArrayList<String> lstPatterns = new java.util.ArrayList<String>();
		char[]charArray=pat.toCharArray();
		for (char ch : charArray)
		{
			if (IsOptionalSymbol(ch))
			{
				java.util.ArrayList<String> newList = new java.util.ArrayList<String>();
				if (lstPatterns.size() <= 0)
				{
					lstPatterns.add("");
				}

				for (String item : lstPatterns)
				{
					newList.add(item);
					//newList.Add(item + ch);
					newList.add(item + NonOptionalSymbolFor(ch));
				}

				lstPatterns = newList;
			}
			else
			{
				if (lstPatterns.size() <= 0)
				{
					lstPatterns.add((new Character(ch)).toString());
				}
				else
				{
					for (int i = 0; i < lstPatterns.size(); ++i)
					{
						lstPatterns.set(i, lstPatterns.get(i) + ch);
					}
				}
			}
		}

		return lstPatterns;
	}

	/** 
	 Reverses the specified string.
	 
	 @param str The string to reverse.
	*/
	private String ReverseString(String str)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = str.length() - 1; i >= 0; i--)
		{
			sb.append(str.charAt(i));
		}

		return sb.toString();
	}

	/** 
	 Determines whether the character from the pattern string can be
	 equal to the character from the input string. The pattern string 
	 can contain regex symbol characters. e.g. A space character can be 
	 equal to Space+ symbol.
	 
	 @param chPattern The character from pattern string.
	 @param chInput The character from input string.
	 @return 
	*/
	private boolean AreCharactersEqual(char chPattern, char chInput)
	{
		if (chPattern == chInput)
		{
			return true;
		}
		else if (chPattern == ' ')
		{
			switch (chInput)
			{
				case ' ':
					return true;
				default:
					return false;
			}
		}
		else if (chPattern == HalfSpace || chPattern == SymbolHalfSpace)
		{
			switch (chInput)
			{
				case SymbolHalfSpace:
				case HalfSpace:
					return true;
				default:
					return false;
			}
		}
		else if (chPattern == SymbolSpaceOrHalfSpace)
		{
			switch (chInput)
			{
				case SymbolHalfSpace:
				case HalfSpace:
				case ' ':
				case SymbolSpaceOrHalfSpace:
					return true;
				default:
					return false;
			}
		}
		else if (chPattern == SymbolSpaceOrHalfSpacePlus)
		{
			switch (chInput)
			{
				case SymbolHalfSpace:
				case HalfSpace:
				case ' ':
				case SymbolSpacePlus:
				case SymbolSpaceOrHalfSpace:
				case SymbolSpaceOrHalfSpacePlus:
					return true;
				default:
					return false;
			}
		}
		else if (chPattern == SymbolSpaceOrHalfSpaceStar)
		{
			switch (chInput)
			{
				case SymbolHalfSpace:
				case HalfSpace:
				case ' ':
				case SymbolSpacePlus:
				case SymbolSpaceOrHalfSpace:
				case SymbolSpaceOrHalfSpacePlus:
				case SymbolSpaceStar:
				case SymbolSpaceOrHalfSpaceStar:
					return true;
				default:
					return false;
			}
		}
		else if (chPattern == SymbolSpacePlus)
		{
			switch (chInput)
			{
				case ' ':
				case SymbolSpacePlus:
					return true;
				case SymbolSpaceOrHalfSpace:
				case SymbolSpaceOrHalfSpacePlus:
				case SymbolSpaceStar:
				case SymbolSpaceOrHalfSpaceStar:
				case SymbolHalfSpace:
				case HalfSpace:
				default:
					return false;
			}
		}
		else if (chPattern == SymbolSpaceStar)
		{
			switch (chInput)
			{
				case ' ':
				case SymbolSpacePlus:
				case SymbolSpaceStar:
					return true;
				default:
					return false;
			}
		}
		else if (chPattern == SymbolHalfSpaceQuestionMark)
		{
			switch (chInput)
			{
				case SymbolHalfSpace:
				case HalfSpace:
				case SymbolHalfSpaceQuestionMark:
					return true;
				default:
					return false;
			}
		}

		return false;
	}

	/** 
	 Resets the search nodes at the start of each pattern-matching operation.
	*/
	private void ResetSearchNodes()
	{
		for (SearchNode node : listSearchNodes)
		{
			node.Reset();
		}
		
	}

	public final ReversePatternMatcherPatternInfo[] Match(String input)
	{
		return Match(input, false);
	}

	/** 
	 Matches the input string with the ending patterns provided before and returns a 
	 sequence of <see cref="ReversePatternMatcherPatternInfo"/> objects which will hold information of the matched pattern.
	 
	 @param input The input.
	 @return 
	*/
	public final ReversePatternMatcherPatternInfo[] Match(String input, boolean uniqueResults)
	{
		java.util.ArrayList<ReversePatternMatcherPatternInfo> lstFounds = new java.util.ArrayList<ReversePatternMatcherPatternInfo>();
		if (input.length() <= 0)
		{
			return lstFounds.toArray(new ReversePatternMatcherPatternInfo[0]);
		}

		ResetSearchNodes();
		boolean[] inactiveSearchNodes = new boolean[listSearchNodes.size()]; // automatically initialized to false

		SearchNode curNode;
		boolean removeNode = true;

		char curChar = reverseStringReader.ReadFirstChar(input);
		while (reverseStringReader.HasMoreChars())
		{
			for (int j = listSearchNodes.size() - 1; j >= 0; j--)
			{
				if (!inactiveSearchNodes[j])
				{
					removeNode = true; // the node should be removed by default unless it matches the input
					curNode = listSearchNodes.get(j);
					if (curNode.getFinished())
					{
						ReversePatternMatcherPatternInfo foundPattern = new ReversePatternMatcherPatternInfo(input.substring(0, reverseStringReader.GetCurrentIndex() + 1), input.substring(reverseStringReader.GetCurrentIndex() + 1));

						if (!uniqueResults)
						{
							lstFounds.add(0, foundPattern);
						}
						else
						{
							if (!lstFounds.contains(foundPattern))
							{
								lstFounds.add(0, foundPattern);
							}
						}

					}
					else
					{
						try
						{
							if (AreCharactersEqual(curNode.GetChar(), curChar))
							{
								removeNode = false;
							}
						}
						catch (java.lang.Exception e)
						{
						}
					}

					if (removeNode)
					{
						inactiveSearchNodes[j] = true;
						//lstSearchNodes.RemoveAt(j);
					}
				}
			} // for ... nodes

			if (listSearchNodes.size() <= 0)
			{
				break;
			}

			curChar = reverseStringReader.ReadNextChar();
		}

		return lstFounds.toArray(new ReversePatternMatcherPatternInfo[0]);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Symbolic Character Constants

	/** 
	 The character used to indicate a single half-space
	*/
	public static final char SymbolHalfSpace = '*';

	/** 
	 The character used to indicate an optional half-space
	*/
	public static final char SymbolHalfSpaceQuestionMark = '%';

	/** 
	 The character used to indicate one or more space characters
	*/
	public static final char SymbolSpacePlus = '#';

	/** 
	 The character used to indicate zero or more space characters
	*/
	public static final char SymbolSpaceStar = '@';

	/** 
	 The character used to indicate either space or half-space character
	*/
	public static final char SymbolSpaceOrHalfSpace = '|';

	/** 
	 The character used to indicate one or more space or half-space characters
	*/
	public static final char SymbolSpaceOrHalfSpacePlus = '$';

	/** 
	 The character used to indicate zero or more space or half-space characters
	*/
	public static final char SymbolSpaceOrHalfSpaceStar = '&';

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Symbolic Characters Utilities

	/** 
	 The standard half-space character used in Persian
	*/
	public static final char HalfSpace = '\u200C';

	/** 
	 Determines whether the specified symbolic character is an optional symbol. 
	 Optional symbols are those that can occur or not, for example 
	 star and question mark closures make a symbol optional.
	 Here optional symbols are:
	 <code>
		 SymbolHalfSpaceQuestionMark
		 SymbolSpaceOrHalfSpaceStar
		 SymbolSpaceStar
	 </code>
	 
	 @param ch The character to check
	 @return 
	 	<c>true</c> if the specified symbolic character is an optional symbol; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsOptionalSymbol(char ch)
	{
		switch (ch)
		{
			case SymbolHalfSpaceQuestionMark:
			case SymbolSpaceOrHalfSpaceStar:
			case SymbolSpaceStar:
				return true;
		}
		return false;
	}

	/** 
	 Returns the non-optional version of the specified symbolic character if it is
	 an optional symbolic character. 
	 e.g. Non-optional version for Space-Star is Space-Plus.
	 
	 @param ch The character to process
	 @return 
	*/
	public static char NonOptionalSymbolFor(char ch)
	{
		switch (ch)
		{
			case SymbolHalfSpaceQuestionMark:
				return SymbolHalfSpace;
			case SymbolSpaceOrHalfSpaceStar:
				return SymbolSpaceOrHalfSpacePlus;
			case SymbolSpaceStar:
				return SymbolSpacePlus;
		}
		return ch;
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IN-CLASS: ReverseStringReader

	/** 
	 A Helper class that helps reading a string in the reverse order and checking the
	 pattern symbols in a linear time.
	*/
	private static class ReverseStringReader
	{
		/** 
		 The input string that is going to be read character by character
		*/
		private String inputString = "";

		/** 
		 The index at which the input string has been read
		*/
		private int readingIndex = 0;

		/** 
		 The length of the input string
		*/
		private int inputStringLength;

		/** 
		 Initializes the reading-state variables and starts by returning the first character
		 in reverse order.
		 
		 @param strInput The input string.
		 @exception ArgumentException If the input string is null or empty
		 @return 
		*/
		public final char ReadFirstChar(String strInput)
		{
			if (strInput == null || strInput.length() <= 0)
			{
				throw new IllegalArgumentException("String argument is null or empty.");
			}
			else
			{
				inputString = strInput;
				inputStringLength = inputString.length();
				readingIndex = inputStringLength;
				return ReadChar();
			}
		}

		/** 
		 Reads the next character in the reverse order.
		 This method does not necessarily read only one character. It may read 
		 several characters if they make a symbol character. e.g. If a sequence of spaces
		 are met a Space-Plus symbol is returned instead of them.
		 
		 @return 
		*/
		public final char ReadNextChar()
		{
			return ReadChar();
		}

		/** 
		 Determines whether there are characters left that are not read yet.
		 
		 @return 
		 	<c>true</c> if there are characters left that are not read yet; otherwise, <c>false</c>.
		 
		*/
		public final boolean HasMoreChars()
		{
			// if it is equal to zero then it has finished reading the last char and there's nothing left to read.
			return readingIndex > 0;
		}

		/** 
		 Gets the index at which the string has been read so far.
		 
		 @return 
		*/
		public final int GetCurrentIndex()
		{
			return readingIndex;
		}

		/** 
		 Reads the next character in the reverse order. This method is 
		 aware of <see cref="ReversePatternMatcher"/>'s special symbols, and returns 
		 those symbols if the characters at the input string match the symbol.
		 This method does not necessarily read only one character. It may read 
		 several characters if they make a symbol character. e.g. If a sequence of spaces
		 are met a Space-Plus symbol is returned instead of them.
		 
		 @return 
		*/
		private char ReadChar()
		{
			readingIndex--;
			char ch = inputString.charAt(readingIndex);
			if (Character.isWhitespace(ch) || ch == ReversePatternMatcher.HalfSpace)
			{
				if (ch == ReversePatternMatcher.HalfSpace)
				{
					ch = ReversePatternMatcher.SymbolHalfSpace;
				}
				else
				{
					ch = ' ';
				}

				readingIndex--;
				while (readingIndex >= 0 && (Character.isWhitespace(inputString.charAt(readingIndex)) || inputString.charAt(readingIndex) == ReversePatternMatcher.HalfSpace))
				{
					if (inputString.charAt(readingIndex) == ReversePatternMatcher.HalfSpace)
					{
						switch (ch) // switch on previously read chars
						{
							case ' ':
							case ReversePatternMatcher.SymbolHalfSpace:
							case ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus:
							case ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar: // won't happen, just in case
							case ReversePatternMatcher.SymbolSpacePlus:
							case ReversePatternMatcher.SymbolSpaceStar: // won't happen, just in case
							case ReversePatternMatcher.SymbolSpaceOrHalfSpace: // won't happen, just in case
								ch = ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus;
								break;
						}
					}
					else // i.e. I read a space character
					{
						switch (ch) // switch on previously read chars
						{
							case ' ':
							case ReversePatternMatcher.SymbolSpacePlus:
							case ReversePatternMatcher.SymbolSpaceStar: // won't happen, just in case
								ch = ReversePatternMatcher.SymbolSpacePlus;
								break;
							case ReversePatternMatcher.SymbolHalfSpace:
							case ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus:
							case ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar: // won't happen, just in case
							case ReversePatternMatcher.SymbolSpaceOrHalfSpace: // won't happen, just in case
								ch = ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus;
								break;
						}
					}

					readingIndex--;
				}

				readingIndex++; // take back one extra char read, the one which violated while's condition
			}

			return ch;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IN-CLASS: SearchNode

	/** 
	 Holds ending patterns, and provide the means for reading their content character
	 by character, and makes it easy to enable and disable each node.
	*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
	private static class SearchNode implements java.lang.Comparable
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Public Properties
		/** 
		 Gets or sets a value indicating whether this <see cref="SearchNode"/> is 
		 finished with being active in the list. A node is called Finished when its input is
		 read completely or the input has no chance of matching the input string.
		 
		 <value><c>true</c> if finished; otherwise, <c>false</c>.</value>
		*/
		private boolean privateFinished;
		public final boolean getFinished()
		{
			return privateFinished;
		}
		private void setFinished(boolean value)
		{
			privateFinished = value;
		}

		/** 
		 Gets or sets the pattern.
		 
		 <value>The pattern.</value>
		*/
		private String privatePattern;
		public final String getPattern()
		{
			return privatePattern;
		}
		private void setPattern(String value)
		{
			privatePattern = value;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Private Fields

		/** 
		 The index at which the pattern string has been read.
		*/
		private int Index = 0;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Public Methods

		/** 
		 Initializes a new instance of the <see cref="SearchNode"/> class.
		 
		 @param pattern The pattern.
		*/
		public SearchNode(String pattern)
		{
			setPattern(pattern);
			Reset();
		}

		/** 
		 Resets this instance, by setting Finished to false, and the reading index to 0.
		*/
		public final void Reset()
		{
			setFinished(false);
			Index = 0;
		}

		/** 
		 Gets the current character.
		 
		 @return 
		*/
		public final char GetChar()
		{
			if (!getFinished())
			{
				char ch = getPattern().charAt(Index);
				Index++;
				if (Index >= getPattern().length())
				{
					setFinished(true);
				}
				return ch;
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region IComparable Members

		/** 
		 Compares the current instance with another object of the same type. 
		 The comparison is made based upon the nodes' pattern strings only.
		 
		 @param obj An object to compare with this instance.
		 @return 
		 A 32-bit signed integer that indicates the relative order of the objects being compared. 
		 The return value has these meanings: 
		 Value Meaning Less than zero This instance is less than <paramref name="obj"/>. 
		 Zero This instance is equal to <paramref name="obj"/>. 
		 Greater than zero This instance is greater than <paramref name="obj"/>.
		 
		 @exception T:System.ArgumentException
		 	<paramref name="obj"/> is not the same type as this instance. 
		*/
		public final int compareTo(Object obj)
		{
			SearchNode otherNode = (SearchNode)((obj instanceof SearchNode) ? obj : null);
			if (otherNode != null)
			{
				return this.getPattern().compareTo(otherNode.getPattern());
			}
			else
			{
				return 1;
			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Public Static Member

		/** 
		 Returns a sequence of search node list created from the specified ending-patterns.
		 
		 @param listPatterns The list of ending-patterns.
		 @return 
		*/
		public static java.util.ArrayList<SearchNode> CreateSearchNodeList(Iterable<String> listPatterns)
		{
			java.util.ArrayList<SearchNode> searchNodes = new java.util.ArrayList<SearchNode>();
			for (String str : listPatterns)
			{
				searchNodes.add(new SearchNode(str));
			}
			return searchNodes;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}