package SCICT.NLP.Utility.StringDistance;

import SCICT.NLP.Persian.Constants.*;
import SCICT.Utility.*;
import SCICT.NLP.Utility.*;

/**
 Indicates String Distance Algorithm
*/
public enum StringDistanceAlgorithm
{
	/**
	 Hamming Distnace Algorithm
	*/
	Hamming(1),
	/**
	 Levenestain Distnace Algorithm
	*/
	Levenestain(2),
	/**
	 JaroWinkler Distnace Algorithm
	*/
	JaroWinkler(3),
	/**
	 Damerau-Levenestain Distnace Algorithm
	*/
	DamerauLevenestain(4),
	/**
	 Wagner-Fischer Distnace Algorithm
	*/
	WagnerFischer(5),
	/**
	 Needleman Distnace Algorithm
	*/
	Needleman(6),
	/**
	 GNULevenesain Distnace Algorithm
	*/
	GNULevenesain(7),
	/**
	 Cosine Distnace Algorithm
	*/
	Cosine(8),
	/**
	 Kashefi Distnace Algorithm
	*/
	Kashefi(9);

	private int intValue;
	private static java.util.HashMap<Integer, StringDistanceAlgorithm> mappings;
	private static java.util.HashMap<Integer, StringDistanceAlgorithm> getMappings()
	{
		if (mappings == null)
		{
			synchronized (StringDistanceAlgorithm.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, StringDistanceAlgorithm>();
				}
			}
		}
		return mappings;
	}

	private StringDistanceAlgorithm(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static StringDistanceAlgorithm forValue(int value)
	{
		return getMappings().get(value);
	}
}