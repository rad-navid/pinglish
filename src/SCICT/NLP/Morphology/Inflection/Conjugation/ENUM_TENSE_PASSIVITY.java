package SCICT.NLP.Morphology.Inflection.Conjugation;

import SCICT.Utility.*;
import SCICT.NLP.Morphology.Inflection.*;

public enum ENUM_TENSE_PASSIVITY
{
	PASSIVE(0x00000100),
	ACTIVE(0x00000200),
	INVALID(0x00000F00);

	private int intValue;
	private static java.util.HashMap<Integer, ENUM_TENSE_PASSIVITY> mappings;
	private static java.util.HashMap<Integer, ENUM_TENSE_PASSIVITY> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ENUM_TENSE_PASSIVITY.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ENUM_TENSE_PASSIVITY>();
				}
			}
		}
		return mappings;
	}

	private ENUM_TENSE_PASSIVITY(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ENUM_TENSE_PASSIVITY forValue(int value)
	{
		return getMappings().get(value);
	}
}