package SCICT.NLP.Persian.Constants;

import SCICT.NLP.Persian.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Persian POS Tags: Hedy

public class PersianPartOfSpeech
{
	public static final PersianPartOfSpeech Unknown = new PersianPartOfSpeech(0x00000000);

	public static final PersianPartOfSpeech Noun = new PersianPartOfSpeech(0x00000001);
	public static final PersianPartOfSpeech Adjective = new PersianPartOfSpeech(0x00000002);
	public static final PersianPartOfSpeech Adverb = new PersianPartOfSpeech(0x00000004);
	public static final PersianPartOfSpeech Verb = new PersianPartOfSpeech(0x00000008);
	public static final PersianPartOfSpeech Preposition = new PersianPartOfSpeech(0x00000010);
	public static final PersianPartOfSpeech Postposition = new PersianPartOfSpeech(0x00000020);
	public static final PersianPartOfSpeech Pronoun = new PersianPartOfSpeech(0x00000040);
	public static final PersianPartOfSpeech Determiner = new PersianPartOfSpeech(0x00000080);
	public static final PersianPartOfSpeech Conjunction = new PersianPartOfSpeech(0x00000100);
	public static final PersianPartOfSpeech Interjunction = new PersianPartOfSpeech(0x00000200);
	public static final PersianPartOfSpeech Number = new PersianPartOfSpeech(0x00000400);
	public static final PersianPartOfSpeech Classifier = new PersianPartOfSpeech(0x00000800);
	public static final PersianPartOfSpeech Punctuation = new PersianPartOfSpeech(0x00001000);
	// 0x00001FFF

	public static final PersianPartOfSpeech Singular = new PersianPartOfSpeech(0x00002000);
	public static final PersianPartOfSpeech Plural = new PersianPartOfSpeech(0x00004000);
	// 0x00006000

	public static final PersianPartOfSpeech FirstPerson = new PersianPartOfSpeech(0x00008000);
	public static final PersianPartOfSpeech SecondPerson = new PersianPartOfSpeech(0x00010000);
	public static final PersianPartOfSpeech ThirdPerson = new PersianPartOfSpeech(0x00020000);
	// 0x00038000

	public static final PersianPartOfSpeech Comparative = new PersianPartOfSpeech(0x00040000);
	public static final PersianPartOfSpeech Superlative = new PersianPartOfSpeech(0x00080000);
	// 0x000C0000

	//Gerund = 0x00008000,
	//Infinitive = 0x00010000,

	//Reflective = 0x00080000,
	//Indefinite = 0x00100000,
	//Personal = 0x00200000,
	//Demonstrative = 0x00400000,
	//Interrogative = 0x00800000,

	private int intValue;
	private static java.util.HashMap<Integer, PersianPartOfSpeech> mappings;
	private static java.util.HashMap<Integer, PersianPartOfSpeech> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PersianPartOfSpeech.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, PersianPartOfSpeech>();
				}
			}
		}
		return mappings;
	}

	private PersianPartOfSpeech(int value)
	{
		intValue = value;
		synchronized (PersianPartOfSpeech.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static PersianPartOfSpeech forValue(int value)
	{
		synchronized (PersianPartOfSpeech.class)
		{
			PersianPartOfSpeech enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new PersianPartOfSpeech(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}