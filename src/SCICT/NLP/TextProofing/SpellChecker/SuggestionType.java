package SCICT.NLP.TextProofing.SpellChecker;

import SCICT.NLP.Morphology.Inflection.*;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.Parsers.*;
import SCICT.NLP.Morphology.Lemmatization.*;
import SCICT.NLP.Utility.WordGenerator.*;
import SCICT.Utility.*;

/**
 Type of suggestion, warning or error
*/
public enum SuggestionType
{
	/**
	 Green for warning types
	*/
	Green(1),
	/**
	 Red for explicit error types
	*/
	Red(2);

	private int intValue;
	private static java.util.HashMap<Integer, SuggestionType> mappings;
	private static java.util.HashMap<Integer, SuggestionType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SuggestionType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, SuggestionType>();
				}
			}
		}
		return mappings;
	}

	private SuggestionType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SuggestionType forValue(int value)
	{
		return getMappings().get(value);
	}
}