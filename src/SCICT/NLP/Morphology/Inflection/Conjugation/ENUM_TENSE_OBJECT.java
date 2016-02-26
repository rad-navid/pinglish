package SCICT.NLP.Morphology.Inflection.Conjugation;

import SCICT.Utility.*;
import SCICT.NLP.Morphology.Inflection.*;

public enum ENUM_TENSE_OBJECT
{
	NOT_SET(0x00100000),
	SINGULAR_FIRST(0x00200000),
	SINGULAR_SECOND(0x00300000),
	SINGULAR_THIRD(0x00400000),
	PLURAL_FIRST(0x00500000),
	PLURAL_SECOND(0x00600000),
	PLURAL_THIRD(0x00700000),
	WRONGE(0x00800000),
	INVALID(0x00F00000);

	private int intValue;
	private static java.util.HashMap<Integer, ENUM_TENSE_OBJECT> mappings;
	private static java.util.HashMap<Integer, ENUM_TENSE_OBJECT> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ENUM_TENSE_OBJECT.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ENUM_TENSE_OBJECT>();
				}
			}
		}
		return mappings;
	}

	private ENUM_TENSE_OBJECT(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ENUM_TENSE_OBJECT forValue(int value)
	{
		return getMappings().get(value);
	}
}