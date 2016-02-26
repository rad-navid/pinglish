package SCICT.NLP.Morphology.Inflection.Conjugation;

import SCICT.Utility.*;
import SCICT.NLP.Morphology.Inflection.*;

public enum ENUM_TENSE_POSITIVITY
{
	POSITIVE(0x00001000),
	NEGATIVE(0x00002000),
	UNUSUAL_POSITIVE(0x00003000),
	UNUSUAL_NEGATIVE(0x00004000),
	WRONG_UNDETECTED(0x00005000),
	INVALID(0x0000F000);

	private int intValue;
	private static java.util.HashMap<Integer, ENUM_TENSE_POSITIVITY> mappings;
	private static java.util.HashMap<Integer, ENUM_TENSE_POSITIVITY> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ENUM_TENSE_POSITIVITY.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ENUM_TENSE_POSITIVITY>();
				}
			}
		}
		return mappings;
	}

	private ENUM_TENSE_POSITIVITY(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ENUM_TENSE_POSITIVITY forValue(int value)
	{
		return getMappings().get(value);
	}
}