package SCICT.NLP.Morphology.Inflection.Conjugation;


public class ENUM_VERB_TYPE
{
	public static final ENUM_VERB_TYPE SADE = new ENUM_VERB_TYPE(0x01000000);
	public static final ENUM_VERB_TYPE PISHVANDI = new ENUM_VERB_TYPE(0x02000000);
	public static final ENUM_VERB_TYPE PISHVANDI_MORAKKAB = new ENUM_VERB_TYPE(0x04000000);
	public static final ENUM_VERB_TYPE MORAKKAB = new ENUM_VERB_TYPE(0x08000000);
	public static final ENUM_VERB_TYPE EBARATE_FELI = new ENUM_VERB_TYPE(0x00100000);
	public static final ENUM_VERB_TYPE NAGOZAR = new ENUM_VERB_TYPE(0x00200000);
	public static final ENUM_VERB_TYPE ESNADI = new ENUM_VERB_TYPE(0x00400000);
	public static final ENUM_VERB_TYPE GHEIRE_SHAKHSI = new ENUM_VERB_TYPE(0x00800000);
	public static final ENUM_VERB_TYPE INVALID = new ENUM_VERB_TYPE(0x00010000);
	public static final ENUM_VERB_TYPE INFINITIVE = new ENUM_VERB_TYPE(0x00020000);

	private int intValue;
	private static java.util.HashMap<Integer, ENUM_VERB_TYPE> mappings;
	private static java.util.HashMap<Integer, ENUM_VERB_TYPE> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ENUM_VERB_TYPE.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ENUM_VERB_TYPE>();
				}
			}
		}
		return mappings;
	}

	private ENUM_VERB_TYPE(int value)
	{
		intValue = value;
		synchronized (ENUM_VERB_TYPE.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static ENUM_VERB_TYPE forValue(int value)
	{
		synchronized (ENUM_VERB_TYPE.class)
		{
			ENUM_VERB_TYPE enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new ENUM_VERB_TYPE(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
	
	public static int GetDefualtObjectsValue(ENUM_VERB_TYPE defualt)
	{
		return defualt.getValue();
	}
	
	public boolean Has(ENUM_VERB_TYPE value)
	{
		int andResult=value.getValue()&this.getValue();
		boolean result=(andResult==value.getValue());
		return result;
	}
	
	public static ENUM_VERB_TYPE[] values()
	{
		return getMappings().values().toArray(new ENUM_VERB_TYPE[0]);
	}
	public boolean Is(ENUM_VERB_TYPE verbType)
	{
		if(this.getValue()==verbType.getValue())
			return true;
		return false;
	}
}