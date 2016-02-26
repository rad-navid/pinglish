package SCICT.NLP.Morphology.Inflection.Conjugation;

import SCICT.Utility.*;
import SCICT.NLP.Morphology.Inflection.*;

public enum ENUM_TENSE_TIME
{
	MAZI_E_SADE(0x00000010),
	MAZI_E_ESTEMRARI(0x00000020),
	MAZI_E_BAEID(0x00000030),
	MAZI_E_MOSTAMAR(0x00000040),
	MAZI_E_SADEYE_NAGHLI(0x00000050),
	MAZI_E_ESTEMRARIE_NAGHLI(0x00000060),
	MAZI_E_BAEIDE_NAGHLI(0x00000070),
	MAZI_E_MOSTAMARE_NAGHLI(0x00000080),
	MAZI_E_ELTEZAMI(0x00000090),
	MOZARE_E_EKHBARI(0x000000A0),
	MOZARE_E_MOSTAMAR(0x000000B0),
	MOZARE_E_ELTEZAMI(0x000000C0),
	AYANDE(0x000000D0),
	AMR(0x000000E0),
	INVALID(0x000000F0);

	private int intValue;
	private static java.util.HashMap<Integer, ENUM_TENSE_TIME> mappings;
	private static java.util.HashMap<Integer, ENUM_TENSE_TIME> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ENUM_TENSE_TIME.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ENUM_TENSE_TIME>();
				}
			}
		}
		return mappings;
	}

	private ENUM_TENSE_TIME(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ENUM_TENSE_TIME forValue(int value)
	{
		return getMappings().get(value);
	}
}