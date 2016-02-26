package SCICT.NLP.TextProofing.SpellChecker;

import SCICT.NLP.Morphology.Inflection.*;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.Parsers.*;
import SCICT.NLP.Morphology.Lemmatization.*;
import SCICT.NLP.Utility.WordGenerator.*;
import SCICT.Utility.*;

/**
 Rules of Prespelling
*/
public class OnePassConvertingRules
{
	/**
	 Convert "Heh with Yeh above" to "Heh + Pseudospace + Yeh" (e.g. "خانۀ" to "خانه‌ی")
	*/
	public static final OnePassConvertingRules ConvertHehYa = new OnePassConvertingRules(1);
	/**
	 Convert Mee with white space or stickerd to Mee with Pseudospace (e.g. "می توانم" and "میتوانم" to "می‌توانم")
	*/
	public static final OnePassConvertingRules ConvertMee = new OnePassConvertingRules(1 * 2);
	/**
	 Convert Haa with white space or stickerd to Haa with Pseudospace (e.g. "شرکت ها" and "شرکتها" to "شرکت‌ها")
	*/
	public static final OnePassConvertingRules ConvertHaa = new OnePassConvertingRules(1 * 2 * 2);
	/**
	 Convert sticked Be to Be with white space (e.g. "بعنوان" to "به عنوان")
	*/
	public static final OnePassConvertingRules ConvertBe = new OnePassConvertingRules(1 * 2 * 2 * 2);
	/**
	 Correct spacing of combination of all other affixes with dictionary words
	*/
	public static final OnePassConvertingRules ConvertAll = new OnePassConvertingRules(1 * 2 * 2 * 2 * 2);

	private int intValue;
	private static java.util.HashMap<Integer, OnePassConvertingRules> mappings;
	private static java.util.HashMap<Integer, OnePassConvertingRules> getMappings()
	{
		if (mappings == null)
		{
			synchronized (OnePassConvertingRules.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, OnePassConvertingRules>();
				}
			}
		}
		return mappings;
	}

	private OnePassConvertingRules(int value)
	{
		intValue = value;
		synchronized (OnePassConvertingRules.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static OnePassConvertingRules forValue(int value)
	{
		synchronized (OnePassConvertingRules.class)
		{
			OnePassConvertingRules enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new OnePassConvertingRules(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}