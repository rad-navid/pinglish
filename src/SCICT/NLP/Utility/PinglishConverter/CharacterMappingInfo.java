package SCICT.NLP.Utility.PinglishConverter;


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
 Defines mapping information for a character. These information are used in conversion phase. 
*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class CharacterMappingInfo implements java.lang.Comparable<CharacterMappingInfo>
{
	/** 
	 Initializes a new instance of the <see cref="CharacterMappingInfo"/> class.
	 
	 @param value The character which this instance holds its mapping information.
	 @param relativeIndex The relative index of this instance. Instances with high value of <see cref="relativeIndex"/> 
	 has less priority in generation phase.
	*/
	public CharacterMappingInfo(String value, int relativeIndex)
	{
		this(value, EmptyChar, TokenPosition.Any, relativeIndex, value);
	}

	/** 
	 Initializes a new instance of the <see cref="CharacterMappingInfo"/> class.
	 
	 @param value The character which this instance holds its mapping information.
	 @param relativeIndex The relative index of this instance. Instances with high value of <see cref="relativeIndex"/> 
	 has less priority in generation phase.
	 @param name The name of this instance.
	*/
	public CharacterMappingInfo(String value, int relativeIndex, String name)
	{
		this(value, EmptyChar, TokenPosition.Any, relativeIndex, name);
	}

	/** 
	 Initializes a new instance of the <see cref="CharacterMappingInfo"/> class.
	 
	 @param value The character which this instance holds its mapping information.
	 @param postfix The postfix character, if any. 
	 For example: 'h' is a possible postfix for 's' character in Persian transliteration.
	*/
	public CharacterMappingInfo(String value, char postfix)
	{
		this(value, postfix, TokenPosition.Any, 0, value);
	}

	/** 
	 Initializes a new instance of the <see cref="CharacterMappingInfo"/> class.
	 
	 @param value The character which this instance holds its mapping information.
	 @param postfix The postfix character, if any. 
	 For example: 'h' is a possible postfix for 's' character in Persian transliteration.
	 @param name The name of this instance.
	*/
	public CharacterMappingInfo(String value, char postfix, String name)
	{
		this(value, postfix, TokenPosition.Any, 0, name);
	}

	/** 
	 Initializes a new instance of the <see cref="CharacterMappingInfo"/> class.
	 
	 @param value The character which this instance holds its mapping information.
	 @param position The position.
	 @param relativeIndex The relative index of this instance. Instances with high value of <see cref="relativeIndex"/> 
	 has less priority in generation phase.
	*/
	public CharacterMappingInfo(String value, TokenPosition position, int relativeIndex)
	{
		this(value, EmptyChar, position, relativeIndex, value);
	}

	/** 
	 Initializes a new instance of the <see cref="CharacterMappingInfo"/> class.
	 
	 @param value The character which this instance holds its mapping information.
	 @param position The position.
	 @param relativeIndex The relative index of this instance. Instances with high value of <see cref="relativeIndex"/> 
	 has less priority in generation phase.
	 @param name The name of this instance.
	*/
	public CharacterMappingInfo(String value, TokenPosition position, int relativeIndex, String name)
	{
		this(value, EmptyChar, position, relativeIndex, name);
	}

	/** 
	 Initializes a new instance of the <see cref="CharacterMappingInfo"/> class.
	 
	 @param value The character which this instance holds its mapping information.
	 @param postfix The postfix character, if any. 
	 For example: 'h' is a possible postfix for 's' character in Persian transliteration.
	 @param position The position.
	 @param relativeIndex The relative index of this instance. Instances with high value of <see cref="relativeIndex"/> 
	 has less priority in generation phase.
	*/
	public CharacterMappingInfo(String value, char postfix, TokenPosition position, int relativeIndex)
	{
		this(value, postfix, position, relativeIndex, value);

	}

	/** 
	 Initializes a new instance of the <see cref="CharacterMappingInfo"/> class.
	 
	 @param value The character which this instance holds its mapping information.
	 @param postfix The postfix character, if any. 
	 For example: 'h' is a possible postfix for 's' character in Persian transliteration.
	 @param position The position.
	 @param relativeIndex The relative index of this instance. Instances with high value of <see cref="relativeIndex"/> 
	 has less priority in generation phase.
	 @param name The name.
	*/
	public CharacterMappingInfo(String value, char postfix, TokenPosition position, int relativeIndex, String name)
	{
		this.setValue(value);
		this.setPostfix(postfix);
		this.setPosition(position);
		this.setRelativeIndex(relativeIndex);
		this.setName(name);
	}

	/** 
	 Gets or sets The character which this instance holds its mapping information.
	 
	 <value>The character which this instance holds its mapping information.</value>
	*/
	private String privateValue;
	public final String getValue()
	{
		return privateValue;
	}
	private void setValue(String value)
	{
		privateValue = value;
	}

	/** 
	 Gets or sets the postfix.
	 
	 <value>The postfix character, if any.
	 For example: 'h' is a possible postfix for 's' character in Persian transliteration.</value>
	*/
	private char privatePostfix;
	public final char getPostfix()
	{
		return privatePostfix;
	}
	private void setPostfix(char value)
	{
		privatePostfix = value;
	}

	/** 
	 Empty character which is used by the classes of this namespace.
	*/
	public static final char EmptyChar = '\0';

	/** 
	 Empty string which is used by the classes of this namespace.
	*/
	public static final String EmptyString = "\0";

	/** 
	 Gets or sets the position.
	 
	 <value>The position.</value>
	*/
	private TokenPosition privatePosition = TokenPosition.values()[0];
	public final TokenPosition getPosition()
	{
		return privatePosition;
	}
	private void setPosition(TokenPosition value)
	{
		privatePosition = value;
	}

	/** 
	 Gets or sets the relative index of this instance.
	 
	 <value>The relative index of this instance. Instances with high value of <see cref="RelativeIndex"/> 
	 has less priority in generation phase.</value>
	*/
	private int privateRelativeIndex;
	public final int getRelativeIndex()
	{
		return privateRelativeIndex;
	}
	private void setRelativeIndex(int value)
	{
		privateRelativeIndex = value;
	}

	/** 
	 Gets or sets the name of this instance.
	 
	 <value>The name.</value>
	*/
	private String privateName;
	public final String getName()
	{
		return privateName;
	}
	private void setName(String value)
	{
		privateName = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Overroaled Methods

	/** 
	 Determines whether the specified <see cref="System.Object"/> is equal to this instance.
	 
	 @param obj The <see cref="System.Object"/> to compare with this instance.
	 @return 
	 	<c>true</c> if the specified <see cref="System.Object"/> is equal to this instance; otherwise, <c>false</c>.
	 
	 @exception T:System.NullReferenceException
	 The <paramref name="obj"/> parameter is null.
	 
	*/
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}

		if (obj instanceof CharacterMappingInfo)
		{
			CharacterMappingInfo otherObj = (CharacterMappingInfo)obj;
			return (otherObj.getValue().equals(this.getValue()));
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	/** 
	 Compares the current object with another object of the same type.
	 
	 @param other An object to compare with this object.
	 @return 
	 A 32-bit signed integer that indicates the relative order of the objects being compared. The return value has the following meanings:
	 Value
	 Meaning
	 Less than zero
	 This object is less than the <paramref name="other"/> parameter.
	 Zero
	 This object is equal to <paramref name="other"/>.
	 Greater than zero
	 This object is greater than <paramref name="other"/>.
	 
	*/
	public final int compareTo(CharacterMappingInfo other)
	{
		if (other == null)
		{
			return 1;
		}

		return (new Integer(this.getRelativeIndex())).compareTo(other.getRelativeIndex());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}