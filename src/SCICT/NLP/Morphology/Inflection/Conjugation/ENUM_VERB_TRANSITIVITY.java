package SCICT.NLP.Morphology.Inflection.Conjugation;

import SCICT.Utility.*;
import SCICT.NLP.Morphology.Inflection.*;

public enum ENUM_VERB_TRANSITIVITY
{
	INTRANSITIVE,
	TRANSITIVE,
	BILATERAL,
	INVALID;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ENUM_VERB_TRANSITIVITY forValue(int value)
	{
		return values()[value];
	}
}