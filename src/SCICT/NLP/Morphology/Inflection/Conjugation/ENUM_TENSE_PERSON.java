package SCICT.NLP.Morphology.Inflection.Conjugation;


public enum ENUM_TENSE_PERSON
{
	SINGULAR_FIRST(0x00000001),
	SINGULAR_SECOND(0x00000002),
	SINGULAR_THIRD(0x00000003),
	PLURAL_FIRST(0x00000004),
	PLURAL_SECOND(0x00000005),
	PLURAL_THIRD(0x00000006),
	UNMACHED_SEGMENT(0x00000007),
	INVALID(0x0000000F);

	private int intValue;
	private static java.util.HashMap<Integer, ENUM_TENSE_PERSON> mappings;
	private static java.util.HashMap<Integer, ENUM_TENSE_PERSON> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ENUM_TENSE_PERSON.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ENUM_TENSE_PERSON>();
				}
			}
		}
		return mappings;
	}

	private ENUM_TENSE_PERSON(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ENUM_TENSE_PERSON forValue(int value)
	{
		return getMappings().get(value);
	}
}