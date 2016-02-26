package SCICT.NLP.Morphology.Inflection.Conjugation;

import SCICT.Utility.*;
import SCICT.NLP.Morphology.Inflection.*;

public enum PRONOUN_TYPE
{
	MAZI,
	MOZARE,
	NAGHLI,
	AMR,
	INVALID;

	public int getValue()
	{
		return this.ordinal();
	}

	public static PRONOUN_TYPE forValue(int value)
	{
		return values()[value];
	}
}