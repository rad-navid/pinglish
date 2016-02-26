package SCICT.NLP.Morphology.Inflection.Conjugation;

import SCICT.Utility.*;
import SCICT.NLP.Morphology.Inflection.*;

public enum ENUM_TENSE_CASE
{
	CASE_ONE(0x00010000),
	CASE_TWO(0x00020000),
	CASE_THREE(0x00030000),
	CASE_FOUR(0x00040000),
	CASE_FIVE(0x00050000),
	CASE_SIX(0x00060000),
	CASE_SEVEN(0x00070000),
	CASE_EIGHT(0x00080000),
	CASE_NINE(0x00090000),
	CASE_TEN(0x000A0000),
	INVALID(0x000F0000);

	private int intValue;
	private static java.util.HashMap<Integer, ENUM_TENSE_CASE> mappings;
	private static java.util.HashMap<Integer, ENUM_TENSE_CASE> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ENUM_TENSE_CASE.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ENUM_TENSE_CASE>();
				}
			}
		}
		return mappings;
	}

	private ENUM_TENSE_CASE(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ENUM_TENSE_CASE forValue(int value)
	{
		return getMappings().get(value);
	}
}