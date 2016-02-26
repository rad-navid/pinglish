package SCICT.NLP.Morphology.Inflection.Conjugation;

import SCICT.NLP.Morphology.Inflection.*;

public enum STEM_TIME
{
	MAZI,
	MOZARE,
	UNSET;

	public int getValue()
	{
		return this.ordinal();
	}

	public static STEM_TIME forValue(int value)
	{
		return values()[value];
	}
}