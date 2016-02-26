package SCICT.NLP.Persian.Constants;

import Helper.StringExtensions;

/**
 Persian Part-of-Speech (POS) tags
*/
public class PersianPOSTag
{
	/**
	 Adverb
	*/
	public static final PersianPOSTag ADV = new PersianPOSTag(1);

	/**
	 Adjective
	*/
	public static final PersianPOSTag AJ = new PersianPOSTag(1 * 2);

	/**
	 Measurment Units
	*/
	public static final PersianPOSTag CL = new PersianPOSTag(1 * 2 * 2);

	/**
	 Conjunction
	*/
	public static final PersianPOSTag CONJ = new PersianPOSTag(1 * 2 * 2 * 2);

	/**
	 Determiner
	*/
	public static final PersianPOSTag DET = new PersianPOSTag(1 * 2 * 2 * 2 * 2);

	/**
	 Interjection 
	*/
	public static final PersianPOSTag INT = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2);

	/**
	 Noun
	*/
	public static final PersianPOSTag N = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2);

	/**
	 Numbers
	*/
	public static final PersianPOSTag NUM = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	/**
	 Preposition
	*/
	public static final PersianPOSTag P = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	/**
	 Postposition 
	*/
	public static final PersianPOSTag POSTP = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	/**
	 Pronoun
	*/
	public static final PersianPOSTag PRO = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	/**
	 Punctuation
	*/
	public static final PersianPOSTag PUNC = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	/**
	 Rests, Not recognized
	*/
	public static final PersianPOSTag RES = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	/**
	 Verb
	*/
	public static final PersianPOSTag V = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	/**
	 User aadded words, not yet tagged
	*/
	public static final PersianPOSTag UserPOS = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	/**
	 Ends with a vowel
	*/
	public static final PersianPOSTag VowelEnding = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	/**
	 Ends with a consonant
	*/
	public static final PersianPOSTag ConsonantalEnding = new PersianPOSTag(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	private int intValue;
	private static java.util.HashMap<Integer, PersianPOSTag> mappings;
	private static java.util.HashMap<Integer, PersianPOSTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PersianPOSTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, PersianPOSTag>();
				}
			}
		}
		return mappings;
	}

	private PersianPOSTag(int value)
	{
		intValue = value;
		synchronized (PersianPOSTag.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static PersianPOSTag forValue(int value)
	{
		synchronized (PersianPOSTag.class)
		{
			PersianPOSTag enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new PersianPOSTag(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
	
	public boolean Has(PersianPOSTag value)
	{
		int andResult=value.getValue()& this.getValue();
		boolean result=(andResult==value.getValue());
		return result;
	}


	public  PersianPOSTag Set(PersianPOSTag value)
	{

		try
		{
			int newValue=(this.getValue()| value.getValue());
			return (PersianPOSTag)(Object)(newValue);
		}
		catch (RuntimeException ex)
		{
			throw new IllegalArgumentException(String.format("Could not append value from enumerated type '%1$s'.", PersianPOSTag.class.getName()), ex);
		}
	}

	public  PersianPOSTag Clear(PersianPOSTag value)
	{


		try
		{
			int newValue=(this.getValue() & ~value.getValue());
			return (PersianPOSTag)(Object)(newValue);
		}
		catch (RuntimeException ex)
		{
			throw new IllegalArgumentException(String.format("Could not remove value from enumerated type '%1$s'.",PersianPOSTag.class.getName()), ex);
		}
	}

	public static PersianPOSTag[] values()
	{
		return getMappings().values().toArray(new PersianPOSTag[0]);
	}

	public static PersianPOSTag ToEnum(String value)
	{
		String[] values = StringExtensions.SplitRemoveEmptyElements(value," \\;");
        
		PersianPOSTag enumVal=null;
        try
        {
            enumVal =forValue(getEnumValue(values[0]));

            String enumStr;
            for (int i = 1; i < values.length ;i++)
            {
                enumStr = values[i];
                enumVal = forValue(getEnumValue(enumStr));
            }
        }
        catch (Exception e)
        {
            enumVal =forValue(0);
        }
        return enumVal;
	}
	
	private static int getEnumValue(String name)
	{
		if(name.equalsIgnoreCase("ADV"))
			return 1;
		else if(name.equalsIgnoreCase("AJ"))
			return 2;
		else if(name.equalsIgnoreCase("CL"))
			return 4;
		else if(name.equalsIgnoreCase("CONJ"))
			return 8;
		else if(name.equalsIgnoreCase("DET"))
			return 16;
		else if(name.equalsIgnoreCase("INT"))
			return 32;
		else if(name.equalsIgnoreCase("N"))
			return 64;
		else if(name.equalsIgnoreCase("NUM"))
			return 128;
		else if(name.equalsIgnoreCase("P"))
			return 256;
		else if(name.equalsIgnoreCase("POSTP"))
			return 512;
		else if(name.equalsIgnoreCase("PRO"))
			return 1024;
		else if(name.equalsIgnoreCase("PUNC"))
			return 2048;
		else if(name.equalsIgnoreCase("RES"))
			return 4096;
		else if(name.equalsIgnoreCase("V"))
			return 8192;
		else if(name.equalsIgnoreCase("UserPOS"))
			return 16384;
		else if(name.equalsIgnoreCase("VowelEnding"))
			return 32768;
		else if(name.equalsIgnoreCase("ConsonantalEnding"))
			return 65536;
		else return 0;
	}














}