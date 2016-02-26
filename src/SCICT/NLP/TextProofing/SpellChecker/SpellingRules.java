package SCICT.NLP.TextProofing.SpellChecker;

import SCICT.NLP.Morphology.Inflection.*;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.Parsers.*;
import SCICT.NLP.Morphology.Lemmatization.*;
import SCICT.NLP.Utility.WordGenerator.*;
import SCICT.Utility.*;

/**
 Different rules for spellchecking
*/
public class SpellingRules
{
	/**
	 Consider space correction for dictionary words
	*/
	public static final SpellingRules VocabularyWordsSpaceCorrection = new SpellingRules(1);
	/**
	 Consider space correction for different affix combinations
	*/
	public static final SpellingRules AffixSpaceCorrection = new SpellingRules(1 * 2);
	/**
	 Consider suggestions that may appear by assuming word as incomplete and complete rest of it (e.g. compu -> computer)
	*/
	public static final SpellingRules CheckForCompletetion = new SpellingRules(1 * 2 * 2);
	/**
	 Consider space correction for affix combination with dictionary words
	*/
	public static final SpellingRules AffixSpaceCorrectionForVocabularyWords = new SpellingRules(1 * 2 * 2 * 2);
	/**
	 Ignore writing of mocker Yeh of Kasra as "Heh with Yeh above" (e.g. "خانۀ" instead of "خانه‌ی")
	*/
	public static final SpellingRules IgnoreHehYa = new SpellingRules(1 * 2 * 2 * 2 * 2);
	/**
	 Ignore single letters
	*/
	public static final SpellingRules IgnoreLetters = new SpellingRules(1 * 2 * 2 * 2 * 2 * 2);

	private int intValue;
	private static java.util.HashMap<Integer, SpellingRules> mappings;
	private static java.util.HashMap<Integer, SpellingRules> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SpellingRules.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, SpellingRules>();
				}
			}
		}
		return mappings;
	}

	private SpellingRules(int value)
	{
		intValue = value;
		synchronized (SpellingRules.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static SpellingRules forValue(int value)
	{
		synchronized (SpellingRules.class)
		{
			SpellingRules enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new SpellingRules(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}