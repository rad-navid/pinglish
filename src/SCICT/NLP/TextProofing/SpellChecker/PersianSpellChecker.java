package SCICT.NLP.TextProofing.SpellChecker;

import Helper.ArraysUtility;
import Helper.LinqSimulationArrayList;
import Helper.LinqSimulationHashMap;
import Helper.StringExtensions;
import SCICT.NLP.Morphology.Inflection.*;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.Parsers.*;
import SCICT.NLP.Morphology.Lemmatization.*;
import SCICT.NLP.Utility.WordGenerator.*;
import SCICT.Utility.*;

/**
 Persian Spell Checker
 This Class find and rank respelling suggestions for a incorrectly spelled Persian word
*/
public class PersianSpellChecker extends SpellCheckerEngine
{

	private final PersianSuffixRecognizer m_persianSuffixRecognizer = new PersianSuffixRecognizer(false, true);

	private boolean m_isAffixStripped = false;
	private boolean m_isRefinedforHehYa = false;

	private String m_wordWithoutSuffix, m_suffix;

	private boolean m_ruleVocabularyWordsSpaceCorrection = false;
	private boolean m_ruleAffixSpaceCorrection = false;
	private boolean m_ruleCheckForCompletetion = false;
	private boolean m_ruleAffixSpaceCorrectionForVocabularyWords = false;
	private boolean m_ruleIgnoreHehYa = false;
	private boolean m_ruleIgnoreLetters = false;

	private boolean m_ruleOnePassConvertHehYa = false;
	private boolean m_ruleOnePassConvertMee = false;
	private boolean m_ruleOnePassConvertHaa = false;
	private boolean m_ruleOnePassConvertBe = false;
	private boolean m_ruleOnePassConvertAll = false;

	private static final String SpacingSuggestionMessage = "اصلاح فاصله‌گذاری";
	private static final String ItterationSuggestionMessage = "تکرار اضافی";
	private static final String SuffixSuggestionMessage = "اصلاح پسوند";
	private static final String PrefixSuggestionMessage = "اصلاح پیشوند";
	private static final String RuleBasedSuggestionMessage = "اصلاح مبتنی بر قوانین";
	private static final String KasraRedundantSuggestionMessage = "اصلاح کسره‌ی اضافه";

	private final String[] m_realWordSuffixes = new String[] {"شست", "تیم", "تند", "تست", "میم", "مست", "اتم", "ترش", "مین", "می"};

		///#region Constructor
	/**
	 Class Constructor
	
	@param config Spellchecker configuration
	*/
	public PersianSpellChecker(SpellCheckerConfig config)
	{
		super(config);
		this.SetDefaultSpellingRules();
		this.SetDefaultOnePassSpacingRules();
	}

	/*
	///<summary>
	/// Class Constructor
	///</summary>
	///<param name="config">Spellchecker configuration</param>
	///<param name="affixCheckForNewWords">Accept affix combination for further added words</param>
	public PersianSpellChecker(SpellCheckerConfig config, bool affixCheckForNewWords)
	    : base(config, affixCheckForNewWords)
	{
	    this.SetDefaultSpellingRules();
	    this.SetDefaultOnePassSpacingRules();

	}

	*/
	
		///#region Public Members

	/**
	 Set rules of spellchecking
	
	@param rules Spellchecking rules (Logically OR rules)
	*/
	public final void SetSpellingRules(SpellingRules rules)
	{
		if ((rules.getValue() & SpellingRules.VocabularyWordsSpaceCorrection.getValue()) != 0)
		{
			this.m_ruleVocabularyWordsSpaceCorrection = true;
		}
		if ((rules.getValue() & SpellingRules.AffixSpaceCorrection.getValue()) != 0)
		{
			this.m_ruleAffixSpaceCorrection = true;
		}
		if ((rules.getValue() & SpellingRules.CheckForCompletetion.getValue()) != 0)
		{
			this.m_ruleCheckForCompletetion = true;
		}
		if ((rules.getValue() & SpellingRules.IgnoreHehYa.getValue()) != 0)
		{
			this.m_ruleIgnoreHehYa = true;
		}
		if ((rules.getValue() & SpellingRules.IgnoreLetters.getValue()) != 0)
		{
			this.m_ruleIgnoreLetters = true;
		}
	}

	/**
	 Remove rules of spellchecking
	
	@param rules Spellchecking rules (Logically OR rules)
	*/
	public final void UnsetSpellingRules(SpellingRules rules)
	{
		if ((rules.getValue() & SpellingRules.VocabularyWordsSpaceCorrection.getValue()) != 0)
		{
			this.m_ruleVocabularyWordsSpaceCorrection = false;
		}
		if ((rules.getValue() & SpellingRules.AffixSpaceCorrection.getValue()) != 0)
		{
			this.m_ruleAffixSpaceCorrection = false;
		}
		if ((rules.getValue() & SpellingRules.CheckForCompletetion.getValue()) != 0)
		{
			this.m_ruleCheckForCompletetion = false;
		}
		if ((rules.getValue() & SpellingRules.IgnoreHehYa.getValue()) != 0)
		{
			this.m_ruleIgnoreHehYa = false;
		}
		if ((rules.getValue() & SpellingRules.IgnoreLetters.getValue()) != 0)
		{
			this.m_ruleIgnoreLetters = false;
		}
	}

	/**
	 Set rules of prespelling
	
	@param rules Prespelling rules (Logically OR rules)
	*/
	public final void SetOnePassConvertingRules(OnePassConvertingRules rules)
	{
		if ((rules.getValue() & OnePassConvertingRules.ConvertHaa.getValue()) != 0)
		{
			this.m_ruleOnePassConvertHaa = true;
		}
		if ((rules.getValue() & OnePassConvertingRules.ConvertHehYa.getValue()) != 0)
		{
			this.m_ruleOnePassConvertHehYa = true;
		}
		if ((rules.getValue() & OnePassConvertingRules.ConvertMee.getValue()) != 0)
		{
			this.m_ruleOnePassConvertMee = true;
		}
		if ((rules.getValue() & OnePassConvertingRules.ConvertBe.getValue()) != 0)
		{
			this.m_ruleOnePassConvertBe = true;
		}
		if ((rules.getValue() & OnePassConvertingRules.ConvertAll.getValue()) != 0)
		{
			this.m_ruleOnePassConvertAll = true;
		}
	}

	/**
	 Remove rules of prespelling
	
	@param rules Prespelling rules (Logically OR rules)
	*/
	public final void UnsetOnePassConvertingRules(OnePassConvertingRules rules)
	{
		if ((rules.getValue() & OnePassConvertingRules.ConvertHaa.getValue()) != 0)
		{
			this.m_ruleOnePassConvertHaa = false;
		}
		if ((rules.getValue() & OnePassConvertingRules.ConvertHehYa.getValue()) != 0)
		{
			this.m_ruleOnePassConvertHehYa = false;
		}
		if ((rules.getValue() & OnePassConvertingRules.ConvertMee.getValue()) != 0)
		{
			this.m_ruleOnePassConvertMee = false;
		}
		if ((rules.getValue() & OnePassConvertingRules.ConvertBe.getValue()) != 0)
		{
			this.m_ruleOnePassConvertBe = false;
		}
		if ((rules.getValue() & OnePassConvertingRules.ConvertAll.getValue()) != 0)
		{
			this.m_ruleOnePassConvertAll = false;
		}
	}

	/** 
	 Get affix-striped word
	 
	 @param word Word
	 @return List of plausible simple forms of word
	*/
	public final String[] GetSimpleFormOfWord(String word)
	{
		LinqSimulationArrayList<String> simpleWordList = new LinqSimulationArrayList<String>();

		ReversePatternMatcherPatternInfo[] suffixeResults = this.m_persianSuffixRecognizer.MatchForSuffix(word);
		if (suffixeResults.length == 0)
		{
			simpleWordList.add(word);
			return simpleWordList.toArray(new String[0]);
		}

		for (ReversePatternMatcherPatternInfo rpmpi : suffixeResults)
		{
			simpleWordList.add(rpmpi.getBaseWord());
		}

		simpleWordList.add(word);
		return simpleWordList.toArray(new String[0]);
	}

	/**
	 Prespell text
	
	@param word Current word
	@param preWord Previous word in context
	@param nxtWord Next word in context
	@param suggestionCount Number of returned suggestions
	@param suggestions List of suggestions
	@param suggestionType Type of suggestins (Warning or Error)
	@param spaceCorrectionState State of space correction
	@return True if the current word is correct, Flase if current word is incorrect
	*/
	public final boolean OnePassCorrection(String word, String preWord, String nxtWord, int suggestionCount, Helper.RefObject<String[]> suggestions, Helper.RefObject<SuggestionType> suggestionType, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState)
	{
		suggestionType.argvalue = SuggestionType.Green;
		spaceCorrectionState.argvalue = SpaceCorrectionState.None;

		LinqSimulationArrayList<String> localSug = new LinqSimulationArrayList<String>();
		String tmpSug;

			///#region Convert HaaYaa

		if (this.OnePassConvertingRuleExist(OnePassConvertingRules.ConvertHehYa))
		{
			tmpSug = RefineforHehYa(word);
			if (!word.equals(tmpSug))
			{
				localSug.add(tmpSug);
				suggestions.argvalue = localSug.toArray(new String[0]);
				if (suggestions.argvalue.length > 0)
				{
					return false;
				}
			}
		}

			///#region Convert Be

		if (this.OnePassConvertingRuleExist(OnePassConvertingRules.ConvertBe))
		{
			if (ContainPrefix(word) && !this.ContainWord(word))
			{
				String wordWithoutPrefix = null;
				Helper.RefObject<String> tempRef_wordWithoutPrefix = new Helper.RefObject<String>(wordWithoutPrefix);
				String prefix = ExtractPrefix(word, tempRef_wordWithoutPrefix);
				wordWithoutPrefix = tempRef_wordWithoutPrefix.argvalue;
				if (!wordWithoutPrefix.equals(""))
				{
					if (prefix.equals("به") && this.ContainWord(wordWithoutPrefix))
					{
						tmpSug = prefix + " " + wordWithoutPrefix;

						if (!localSug.contains(tmpSug))
						{
							localSug.add(tmpSug);
							suggestions.argvalue = localSug.toArray(new String[0]);
							if (suggestions.argvalue.length > 0)
							{
								return false;
							}
						}
					}
				}
			}
		}

			///#region Convert Mee + Nemee

		if (this.OnePassConvertingRuleExist(OnePassConvertingRules.ConvertMee))
		{
			if (ContainPrefix(word))
			{
				if (IsStickerPrefix(word))
				{
					if (IsValidPrefix(word, nxtWord))
					{
						tmpSug = CheckForSpaceInsertation(word, word, nxtWord, spaceCorrectionState);
						if (!tmpSug.equals("") && !word.equals(tmpSug))
						{
							if (!localSug.contains(tmpSug))
							{
								localSug.add(tmpSug);
							}
						}
					}
				}
				else
				{
					if (!this.ContainWord(word))
					{
						String wordWithoutPrefix = null;
						Helper.RefObject<String> tempRef_wordWithoutPrefix2 = new Helper.RefObject<String>(wordWithoutPrefix);
						String prefix = ExtractPrefix(word, tempRef_wordWithoutPrefix2);
						wordWithoutPrefix = tempRef_wordWithoutPrefix2.argvalue;
						if (!wordWithoutPrefix.equals(""))
						{
							if ((prefix.equals("می") || prefix.equals("نمی")) && this.ContainWord(wordWithoutPrefix))
							{
								tmpSug = prefix + PseudoSpace.ZWNJ + wordWithoutPrefix;
								if (this.ContainWord(tmpSug))
								{
									if (!localSug.contains(tmpSug))
									{
										localSug.add(tmpSug);
									}
								}
							}
						}
					}
				}
			}

			suggestions.argvalue = localSug.toArray(new String[0]);
			if (suggestions.argvalue.length > 0)
			{
				return false;
			}
		}

			///#region Convert All

		if (this.OnePassConvertingRuleExist(OnePassConvertingRules.ConvertAll))
		{
			if (!IsPrefix(nxtWord))
			{
				if (!CheckSuffixSpacing(word, nxtWord, suggestions, suggestionType, spaceCorrectionState))
				{
					return false;
				}
			}
		}

			///#region OLD
		/*
		if (this.OnePassConvertingRuleExist(OnePassConvertingRules.ConvertAll))
		{
		    if (this.ContainWord(word) && !base.IsRealWord(word))
		    {
		        if (!word.Contains(PseudoSpace.ZWNJ.ToString()))
		        {
		            SpaceCorrectionState scs;
		            ReversePatternMatcherPatternInfo[] rpmp = this.StripAffixs(word);
		            if (rpmp.Length > 0)
		            {
		                foreach (ReversePatternMatcherPatternInfo pair in rpmp)
		                {
		                    if (!pair.Suffix.StartsWith("ها"))
		                    {

		                        tmpSug = CheckForSpaceInsertation(pair.BaseWord, pair.BaseWord, pair.Suffix, out scs);

		                        if (tmpSug == word)
		                        {
		                            break;
		                        }
		                        else if (tmpSug != "")
		                        {
		                            if (!localSug.Contains(tmpSug))
		                            {
		                                localSug.Add(tmpSug);

		                                suggestions = localSug.ToArray();
		                                if (suggestions.Length > 0)
		                                {
		                                    return false;
		                                }

		                            }
		                        }
		                    }
		                }
		            }
		        }
		    }

		    //check Vocabulary Words Space Correction Rule
		    if (IsSuffix(nxtWord) && (!base.IsRealWord(nxtWord) || nxtWord.Length <= 3) && !nxtWord.StartsWith("ها") && !IsPrefix(nxtWord))
		    {
		        tmpSug = CheckForSpaceInsertation(word, word, nxtWord, out spaceCorrectionState);
		        if (tmpSug != "" && tmpSug != word)
		        {
		            if (!localSug.Contains(tmpSug))
		            {
		                localSug.Add(tmpSug);

		                suggestions = localSug.ToArray();
		                if (suggestions.Length > 0)
		                {
		                    return false;
		                }
		            }
		        }
		    }
		}
		*/

			///#region Covert Haa

		if (this.OnePassConvertingRuleExist(OnePassConvertingRules.ConvertHaa))
		{
			if (this.ContainWord(word) && !super.IsRealWord(word))
			{
				if (!word.contains((new Character(PseudoSpace.ZWNJ)).toString()))
				{
					SpaceCorrectionState scs = SpaceCorrectionState.forValue(0);
					ReversePatternMatcherPatternInfo[] rpmp = this.StripAffixs(word);
					if (rpmp.length > 0)
					{
						for (ReversePatternMatcherPatternInfo pair : rpmp)
						{
							if (pair.getSuffix().startsWith("ها"))
							{
								Helper.RefObject<SpaceCorrectionState> tempRef_scs = new Helper.RefObject<SpaceCorrectionState>(scs);
								tmpSug = CheckForSpaceInsertation(pair.getBaseWord(), pair.getBaseWord(), pair.getSuffix(), tempRef_scs);
								scs = tempRef_scs.argvalue;

								if (word.equals(tmpSug))
								{
									break;
								}
								else if (!tmpSug.equals(""))
								{
									if (!localSug.contains(tmpSug))
									{
										localSug.add(tmpSug);

										suggestions.argvalue = localSug.toArray(new String[0]);
										if (suggestions.argvalue.length > 0)
										{
											return false;
										}
									}
								}
							}
						}
					}
				}
			}

			//check Vocabulary Words Space Correction Rule
			if (nxtWord.startsWith("ها"))
			{
				tmpSug = CheckForSpaceInsertation(word, word, nxtWord, spaceCorrectionState);
				if (!tmpSug.equals("") && !word.equals(tmpSug))
				{
					if (!localSug.contains(tmpSug))
					{
						localSug.add(tmpSug);

						suggestions.argvalue = localSug.toArray(new String[0]);
						if (suggestions.argvalue.length > 0)
						{
							return false;
						}
					}
				}
			}
		}
		
			///#endregion

		suggestions.argvalue = new String[0];
		return true;
	}

	/**
	 Check and correct spelling
	
	@param word Current word
	@param preWord Previous word in context
	@param nxtWord Next word in context
	@param suggestionCount Number of returned suggestions
	@param suggestions List of suggestions
	@param suggestionType Type of suggestins (Warning or Error)
	@param spaceCorrectionState State of space correction
	@return True if the current word is correct, Flase if current word is incorrect
	*/
	public final boolean CheckSpelling(String word, String preWord, String nxtWord, int suggestionCount, Helper.RefObject<String[]> suggestions, Helper.RefObject<SuggestionType> suggestionType, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState)
	{
		//Clear ranking detail
		super.m_rankingDetail.clear();

		if (SpellingRuleExist(SpellingRules.IgnoreLetters))
		{
			if (word.length() == 1)
			{
				suggestions.argvalue = new String[0];
				suggestionType.argvalue = SuggestionType.Green;
				spaceCorrectionState.argvalue = SpaceCorrectionState.None;
				return true;
			}
		}

			///#region Refine Word for Heh + Ya

		word = RefineforHehYa(word);

		boolean iterationRes = CheckForIteration(word, nxtWord, suggestionCount, suggestions, suggestionType, spaceCorrectionState, true);
		if (iterationRes == false)
		{
			return false;
		}

		if (!CheckAffixSpacing(word, preWord, nxtWord, suggestions, suggestionType, spaceCorrectionState))
		{
			return false;
		}

			///#region Check for Existance

		if (this.ContainWord(word))
		{
			if (this.m_isRefinedforHehYa == false | this.SpellingRuleExist(SpellingRules.IgnoreHehYa))
			{
				return true;
			}
			else if (this.m_isRefinedforHehYa == true)
			{
				suggestions.argvalue = new String[] {word};
				suggestionType.argvalue = SuggestionType.Red;
				spaceCorrectionState.argvalue = SpaceCorrectionState.None;
				getRankingDetail().put(word, KasraRedundantSuggestionMessage);

				return false;
			}
		}

			///#endregion

		DoAdvancedSpellCheck(word, preWord, nxtWord, suggestionCount, suggestions, suggestionType, spaceCorrectionState);

			///#region Revert HahYa

		if (this.SpellingRuleExist(SpellingRules.IgnoreHehYa))
		{
			suggestions.argvalue = RevertHehYa(suggestions.argvalue);
		}


		return false;
	}

	/** 
	 Add a correct word to dictionary
	 
	 @param userSelectedWord Form of word which user select to add to dictionary
	 @param originalWord Original word without lemmatization
	@return True if word is successfully added, otherwise False
	*/
//C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: public new bool AddToDictionary(string userSelectedWord, string originalWord)
	public final boolean AddToDictionary(String userSelectedWord, String originalWord)
	{
		String suffix = originalWord.substring(0, 0) + originalWord.substring(0 + userSelectedWord.length());

		PersianSuffixesCategory suffixCategory = m_persianSuffixRecognizer.SuffixCategory(suffix);
		PersianPOSTag extractedPOSTag = InflectionAnalyser.AcceptingPOS(suffixCategory);

		extractedPOSTag.Set(InflectionAnalyser.ConsonantVowelState(originalWord, suffix, userSelectedWord, suffixCategory));

		PersianPOSTag existingPOStag = WordPOS(userSelectedWord);
		if (existingPOStag.Has(extractedPOSTag))
		{
			return false;
		}
		else
		{
			extractedPOSTag = extractedPOSTag.Set(existingPOStag);
		}

		return AddToDictionary(userSelectedWord, 0, extractedPOSTag);
	}

	/** 
	 Add a correct word to dictionary
	 
	 @param userSelectedWord Form of word which user select to add to dictionary
	 @param originalWord Original word without lemmatization
	 @param fileName File Name
	@return True if word is successfully added, otherwise False
	*/
	public final boolean AddToDictionary(String userSelectedWord, String originalWord, String fileName)
	{
		String suffix;
		if (originalWord.contains("گان") && !originalWord.contains("ه") && userSelectedWord.endsWith("ه"))
		{
			suffix = originalWord.substring(0, 0) + originalWord.substring(0 + userSelectedWord.length() - 1);
		}
		else
		{
			suffix = originalWord.substring(0, 0) + originalWord.substring(0 + userSelectedWord.length());
			if (suffix.length() > 0)
			{
				if (suffix.charAt(0) == PseudoSpace.ZWNJ)
				{
					suffix = suffix.substring(0, 0) + suffix.substring(0 + 1);
				}
			}
		}

		PersianSuffixesCategory suffixCategory = m_persianSuffixRecognizer.SuffixCategory(suffix);
		PersianPOSTag extractedPOSTag = InflectionAnalyser.AcceptingPOS(suffixCategory);

		extractedPOSTag = extractedPOSTag.Set(InflectionAnalyser.ConsonantVowelState(originalWord, suffix, userSelectedWord, suffixCategory));

		PersianPOSTag existingPOStag = WordPOS(userSelectedWord);
		if (existingPOStag.Has(extractedPOSTag))
		{
			return false;
		}
		else
		{
			extractedPOSTag = extractedPOSTag.Set(existingPOStag);
		}

		return AddToDictionary(userSelectedWord, 0, extractedPOSTag, fileName);
	}

	/**
	 Return suggestion ranking detail
	*/
	public final java.util.HashMap<String, String> getRankingDetail()
	{
		return super.m_rankingDetail;
	}

	/** 
	 check if the word is correct or exist in Ignore List
	 
	 @param word Input word
	 @return 
	*/
	public final boolean ContainWord(String word)
	{
		try
		{
			if (!super.IsRealWord(word))
			{
				this.m_isAffixStripped = true;
				Helper.RefObject<String> tempRef_m_wordWithoutSuffix = new Helper.RefObject<String>(this.m_wordWithoutSuffix);
				Helper.RefObject<String> tempRef_m_suffix = new Helper.RefObject<String>(this.m_suffix);
				boolean tempVar = this.IsRealWordConsideringAffixes(word, tempRef_m_wordWithoutSuffix, tempRef_m_suffix);
				this.m_wordWithoutSuffix = tempRef_m_wordWithoutSuffix.argvalue;
				this.m_suffix = tempRef_m_suffix.argvalue;
				return tempVar;
				//return this.IsRealWordWithoutAffix(word);
			}

			this.m_isAffixStripped = false;
			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/*
	public bool CheckSpelling2(string word, string preWord, string nxtWord, int suggestionCount, out string[] suggestions, out SuggestionType suggestionType, out SpaceCorrectionState spaceCorrectionState)
	{
	    spaceCorrectionState = SpaceCorrectionState.None;
	    suggestionType = SuggestionType.Red;

	    #region Check for: Affix Spacing, Existance and Iteration

	    if (!CheckAffixSpacing(word, preWord, nxtWord, out suggestions, out suggestionType, out spaceCorrectionState))
	    {
	        return false;
	    }

	    bool iterationRes = CheckForIteration(word, nxtWord, suggestionCount, out suggestions, out suggestionType, out spaceCorrectionState, true);
	    if (this.ContainWord2(word))
	    {
	        return iterationRes;
	    }
	    else if (iterationRes == false)
	    {
	        return false;
	    }

	    #endregion

	    doAdvancedSpellCheck2(word, preWord, nxtWord, suggestionCount, out suggestions, out suggestionType, out spaceCorrectionState);

	    return false;
	}
	*/

		///#region Private Method

	private void DoAdvancedSpellCheck(String word, String preWord, String nxtWord, int suggestionCount, Helper.RefObject<String[]> suggestions, Helper.RefObject<SuggestionType> suggestionType, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState)
	{
		spaceCorrectionState.argvalue = SpaceCorrectionState.None;
		suggestionType.argvalue = SuggestionType.Red;

		LinqSimulationArrayList<String> tmpSuggestions = new LinqSimulationArrayList<String>();

			///#region Check for Spacing Correction & Add

		String[] spacingSugs=null;;
		try
		{
			spacingSugs = this.CheckSpellingWithSpacingConsideration(word, preWord, nxtWord, spaceCorrectionState);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}

		if (spacingSugs.length != 0)
		{
			if (spaceCorrectionState.argvalue.getValue() == SpaceCorrectionState.SpaceDeletationSerrially.getValue() || spaceCorrectionState.argvalue.getValue() == SpaceCorrectionState.SpaceInsertationLeftSerrially.getValue() || spaceCorrectionState.argvalue == SpaceCorrectionState.SpaceInsertationRightSerrially)
			{
				// =============== Check Length ================/
				if (word.length() > MaxWordLengthToCheck + 5)
				{
					spacingSugs = AffixSpacingSeriallyCheck(SortSpacingSugs(spacingSugs));

					spacingSugs = SortSpacingSugs(spacingSugs);

					suggestions.argvalue = spacingSugs;
					return;
				}
			}
		}

		String[] tmpSpellingSuggestion;

		ReSpellingData spellingDataClass = new ReSpellingData();
		spellingDataClass.m_word = word;
		try
		{

			ReSpellingSuggestionsThreadSafe((Object)spellingDataClass);
			
			//tmpSpellingSuggestion = ReSpellingSuggestions(word);

			//if (tmpSpellingSuggestion.Length > 0)
			//{
			//    suggestionType = SuggestionType.Red;
			//}
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}

		String[] tmpAffixSuggestions;
		boolean havePhoneticSuffixProblem = false;

		AffixCorrectionData affixDataClass = new AffixCorrectionData();
		affixDataClass.m_word = word;
		affixDataClass.m_suggestionCount = suggestionCount;

		try
		{
			CheckSpellingWithAffixConsiderationThreadSafe((Object)affixDataClass);
			

			//tmpAffixSuggestions = this.CheckSpellingWithAffixConsideration(word, suggestionCount, out havePhoneticSuffixProblem);
			//tmpAffixSuggestions = SortSuggestions(word, tmpAffixSuggestions, suggestionCount / 2);
			tmpSpellingSuggestion = spellingDataClass.m_suggestions;
			tmpAffixSuggestions = affixDataClass.m_suggestions;


				///#region Peak proper number of suggestions considering affix issue

			int removeIndex = Math.min(tmpAffixSuggestions.length, suggestionCount / 2);
			LinqSimulationArrayList<String> tmpAffixSugList = LinqSimulationArrayList.ToList(tmpAffixSuggestions);
			tmpAffixSugList.removeRange("",removeIndex, tmpAffixSuggestions.length - removeIndex);
			tmpAffixSuggestions = tmpAffixSugList.toArray(new String[0]);


			if (tmpAffixSuggestions.length > 0 || tmpSpellingSuggestion.length > 0)
			{
				suggestionType.argvalue = SuggestionType.Red;
			}
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}

			///#region Auto Complete Suggestions

		String[] tmpAutoCompleteSuggestions = new String[0];

		if (spacingSugs.length == 0)
		{
			if (this.SpellingRuleExist(SpellingRules.CheckForCompletetion))
			{
				try
				{
					tmpAutoCompleteSuggestions = this.CompleteWord(word, suggestionCount);

					if (tmpAutoCompleteSuggestions.length > 0)
					{
						suggestionType.argvalue = SuggestionType.Red;
					}
				}
				catch (RuntimeException ex)
				{
					throw ex;
				}
			}
		}
		
			///#region Rule-based Suggestion

		String[] tmpRuleBaseSugs = new String[0];
		try
		{
			tmpRuleBaseSugs = this.RuleBasedSuggestions(word);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}

			///#region Add Corrected Words

		tmpSuggestions.addAll(tmpSpellingSuggestion);
		tmpSuggestions.addAll(tmpAffixSuggestions);
		tmpSuggestions.addAll(tmpAutoCompleteSuggestions);

		String[] finalSpacingSeg = AffixSpacingSeriallyCheck(SortSpacingSugs(spacingSugs));

		//remove iterated results
		String[] restSugs = RemoveIteration(finalSpacingSeg, tmpSuggestions.Distinct("").toArray(new String[0]));

		String[] finalSpellingSeg = SortSuggestions(word, restSugs, Math.min(restSugs.length, suggestionCount));

			///#region Rule-based Correction

		try
		{
			finalSpellingSeg = this.RuleBasedCorrection(finalSpellingSeg);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}

			///#region Correct Spacing of Spelling Suggestion if Needed for an Spacing suggestion

		boolean haveSpacingSuggestion = false;
		if (spaceCorrectionState.argvalue != SpaceCorrectionState.SpaceInsertationLeftSerrially && spaceCorrectionState.argvalue != SpaceCorrectionState.SpaceInsertationRightSerrially && finalSpellingSeg.length != 0)
		{
			Helper.RefObject<Boolean> tempRef_haveSpacingSuggestion = new Helper.RefObject<Boolean>(haveSpacingSuggestion);
			SortSpacingAndSpellingSugs(SortSuggestions(word, finalSpacingSeg, Math.min(suggestionCount / 2, suggestionCount - finalSpellingSeg.length)), finalSpellingSeg, tempRef_haveSpacingSuggestion);
			haveSpacingSuggestion = tempRef_haveSpacingSuggestion.argvalue;

			if (haveSpacingSuggestion)
			{
				finalSpellingSeg = CorrectFinalSuggestionsSpacing(preWord, nxtWord, finalSpellingSeg, spaceCorrectionState.argvalue);
			}
			else
			{
				spaceCorrectionState.argvalue = SpaceCorrectionState.None;
			}
		}

			///#region Finally sort spacing and spelling suggestions together

		String[] finalSugs;
		if (spaceCorrectionState.argvalue.getValue() == SpaceCorrectionState.SpaceDeletation.getValue() || spaceCorrectionState.argvalue.getValue() == SpaceCorrectionState.SpaceInsertationLeft.getValue() || spaceCorrectionState.argvalue.getValue() == SpaceCorrectionState.SpaceInsertationRight.getValue() || spaceCorrectionState.argvalue == SpaceCorrectionState.SpaceDeletationSerrially)
		{
			Helper.RefObject<Boolean> tempRef_haveSpacingSuggestion2 = new Helper.RefObject<Boolean>(haveSpacingSuggestion);
			finalSugs = SortSpacingAndSpellingSugs(SortSuggestions(word, finalSpacingSeg, Math.min(suggestionCount / 2, suggestionCount - finalSpellingSeg.length)), finalSpellingSeg, tempRef_haveSpacingSuggestion2);
			haveSpacingSuggestion = tempRef_haveSpacingSuggestion2.argvalue;

			if (!haveSpacingSuggestion)
			{
				spaceCorrectionState.argvalue = SpaceCorrectionState.None;
			}
		}
		else
		{
			if (finalSpellingSeg.length == 0)
			{
				Helper.RefObject<Boolean> tempRef_haveSpacingSuggestion3 = new Helper.RefObject<Boolean>(haveSpacingSuggestion);
				finalSugs = SortSpacingAndSpellingSugs(SortSuggestions(word, finalSpacingSeg, Math.min(suggestionCount / 2, suggestionCount - finalSpellingSeg.length)), finalSpellingSeg, tempRef_haveSpacingSuggestion3);
				haveSpacingSuggestion = tempRef_haveSpacingSuggestion3.argvalue;

				if (!haveSpacingSuggestion)
				{
					spaceCorrectionState.argvalue = SpaceCorrectionState.None;
				}
			}
			else
			{
				finalSugs = finalSpellingSeg;

				spaceCorrectionState.argvalue = SpaceCorrectionState.None;
			}
		}
		finalSugs = ArraysUtility.Distinct(finalSugs);
			///#region Add Rule-base Suggestion

		LinqSimulationArrayList<String> finalList = new LinqSimulationArrayList<String>();
		if (spaceCorrectionState.argvalue != SpaceCorrectionState.SpaceInsertationLeftSerrially && spaceCorrectionState.argvalue != SpaceCorrectionState.SpaceInsertationRightSerrially && finalSpellingSeg.length != 0)
		{
			if (haveSpacingSuggestion)
			{
				finalList.addAll(CorrectFinalSuggestionsSpacing(preWord, nxtWord, tmpRuleBaseSugs, spaceCorrectionState.argvalue));
			}
			else
			{
				finalList.addAll(tmpRuleBaseSugs);
				spaceCorrectionState.argvalue = SpaceCorrectionState.None;
			}
		}

		if (havePhoneticSuffixProblem)
		{
			finalList.addAll(SortSuggestions(word, tmpAffixSuggestions, tmpAffixSuggestions.length));
		}

		finalList.addAll(finalSugs);

		if (finalList.isEmpty())
		{
			finalList.addAll(spacingSugs);
		}

		suggestions.argvalue = finalList.Distinct("").toArray(new String[0]);

	}

	private String[] CorrectFinalSuggestionsSpacing(String preWord, String nxtWord, String[] sugList, SpaceCorrectionState spaceCorrectionState)
	{
		//Add Pre/Next Word to Corrected Words by: Spelling Correction
		for (int i = 0; i < sugList.length; ++i)
		{
			if (spaceCorrectionState.getValue() == SpaceCorrectionState.SpaceInsertationLeft.getValue() || spaceCorrectionState == SpaceCorrectionState.SpaceInsertationLeftSerrially)
			{
				if (!sugList[i].contains(" "))
				{
					if (nxtWord.length() > 0)
					{
						String rankingKey = sugList[i];

						sugList[i] = sugList[i] + " " + nxtWord;

						String rankingValue = getRankingDetail().get(rankingKey);
						getRankingDetail().remove(rankingKey);
						getRankingDetail().put(sugList[i], rankingValue);
					}
				}
			}
			else if (spaceCorrectionState.getValue() == SpaceCorrectionState.SpaceInsertationRight.getValue() || spaceCorrectionState == SpaceCorrectionState.SpaceInsertationRightSerrially)
			{
				if (!sugList[i].contains(" "))
				{
					if (preWord.length() > 0)
					{
						String rankingKey = sugList[i];

						sugList[i] = preWord + " " + sugList[i];

						String rankingValue = getRankingDetail().get(rankingKey);
						getRankingDetail().remove(rankingKey);
						getRankingDetail().put(sugList[i], rankingValue);
					}
				}
			}
		}

		return sugList;
	}

	private void ReSpellingSuggestionsThreadSafe(Object data)
	{
		ReSpellingData inputData = (ReSpellingData) data;

		String word = inputData.m_word;

		String[] wordParts =  StringExtensions.SplitRemoveEmptyElements(word,PseudoSpace.ZWNJ+"");

		if ((wordParts.length == 1 || wordParts[0].length() <= 3) || (wordParts[0].endsWith("ه") && wordParts[1].startsWith("گ")))
		{

			inputData.m_suggestions = super.SpellingSuggestions2(word);
				///#region Commentet Magic Generation
			//List<string> respellingSuggetions = new List<string>();
			//for (int i = 1; i < EditDistance; ++i)
			//{
			//    string[] tmpSugArray = base.SpellingSuggestions3(word, i);
			//    foreach (string tmpSug in tmpSugArray)
			//    {
			//        if (this.ContainWord(tmpSug))
			//        {
			//            respellingSuggetions.Add(tmpSug);
			//        }
			//    }

			//    if (respellingSuggetions.Count > 0)
			//    {
			//        break;
			//    }
			//}

			//inputData.m_suggestions = respellingSuggetions.Distinct().ToArray();
			return;
		}
		else
		{
			LinqSimulationArrayList<String> suggestions = new LinqSimulationArrayList<String>();
			LinqSimulationArrayList<String> tmpNextPartSugs = new LinqSimulationArrayList<String>();
			LinqSimulationArrayList<String> tmpPrePartSugs = new LinqSimulationArrayList<String>();
			for (String str : wordParts)
			{
				suggestions.clear();
				tmpNextPartSugs.clear();
				tmpNextPartSugs.addAll(super.SpellingSuggestions2(str));
				if (tmpPrePartSugs.isEmpty())
				{
					tmpPrePartSugs.addAll(tmpNextPartSugs);
					continue;
				}

				for (String tmpNextPart : tmpNextPartSugs)
				{
					for (String tmpPrePart : tmpPrePartSugs)
					{
						suggestions.add(tmpPrePart + PseudoSpace.ZWNJ + tmpNextPart);
					}
				}
				tmpPrePartSugs.clear();
				tmpPrePartSugs.addAll(suggestions);
			}

			LinqSimulationArrayList<String> finalSug = new LinqSimulationArrayList<String>(ExtractRealWords(suggestions.Distinct("").toArray(new String[0])));
			if (super.IsRealWord(wordParts[0]))
			{
				finalSug.addAll(CompleteWord(wordParts[0]));
			}

			inputData.m_suggestions = finalSug.Distinct("").toArray(new String[0]);
			return;
		}
	}

	private String[] ReSpellingSuggestions(String word)
	{
		String[] wordParts = StringExtensions.SplitRemoveEmptyElements(word,PseudoSpace.ZWNJ+"");

		if (wordParts.length == 1)
		{
			return super.SpellingSuggestions(word);
			//WordGenerator wordGenerator = new WordGenerator();
			//string[] sugList = wordGenerator.GenerateRespelling(word.Substring(0, 3), 1);

			//List<string> sugs = new List<string>();
			//foreach(string str in sugList)
			//{
			//    sugs.AddRange(CompleteWord(str));
			//}
			//return sugs.Distinct().ToArray();
		}
		else
		{
			LinqSimulationArrayList<String> suggestions = new LinqSimulationArrayList<String>();
			LinqSimulationArrayList<String> tmpNextPartSugs = new LinqSimulationArrayList<String>();
			LinqSimulationArrayList<String> tmpPrePartSugs = new LinqSimulationArrayList<String>();
			for (String str : wordParts)
			{
				suggestions.clear();
				tmpNextPartSugs.clear();
				tmpNextPartSugs.addAll(super.SpellingSuggestions(str));
				if (tmpPrePartSugs.isEmpty())
				{
					tmpPrePartSugs.addAll(tmpNextPartSugs);
					continue;
				}

				for (String tmpNextPart : tmpNextPartSugs)
				{
					for (String tmpPrePart : tmpPrePartSugs)
					{
						suggestions.add(tmpPrePart + PseudoSpace.ZWNJ + tmpNextPart);
					}
				}
				tmpPrePartSugs.clear();
				tmpPrePartSugs.addAll(suggestions);
			}

			LinqSimulationArrayList<String> finalSug = new LinqSimulationArrayList<String>(ExtractRealWords(suggestions.Distinct("").toArray(new String[0])));
			if (super.IsRealWord(wordParts[0]))
			{
				finalSug.addAll(CompleteWord(wordParts[0]));
			}
			return finalSug.Distinct("").toArray(new String[0]);

		}
	}

	private String[] ReSpellingSuggestions(String word, int editDistance)
	{
		String[] wordParts = StringExtensions.SplitRemoveEmptyElements(word,PseudoSpace.ZWNJ+"");

		if (wordParts.length == 1)
		{
			return super.SpellingSuggestions(word, editDistance);
		}
		else
		{
			LinqSimulationArrayList<String> suggestions = new LinqSimulationArrayList<String>();
			LinqSimulationArrayList<String> tmpNextPartSugs = new LinqSimulationArrayList<String>();
			LinqSimulationArrayList<String> tmpPrePartSugs = new LinqSimulationArrayList<String>();
			for (String str : wordParts)
			{
				suggestions.clear();
				tmpNextPartSugs.clear();
				tmpNextPartSugs.addAll(super.SpellingSuggestions(str));
				if (tmpPrePartSugs.isEmpty())
				{
					tmpPrePartSugs.addAll(tmpNextPartSugs);
					continue;
				}

				for (String tmpNextPart : tmpNextPartSugs)
				{
					for (String tmpPrePart : tmpPrePartSugs)
					{
						suggestions.add(tmpPrePart + PseudoSpace.ZWNJ + tmpNextPart);
					}
				}
				tmpPrePartSugs.clear();
				tmpPrePartSugs.addAll(suggestions);
			}
			LinqSimulationArrayList<String> finalSug = new LinqSimulationArrayList<String>(ExtractRealWords(suggestions.Distinct("").toArray(new String[0])));
			if (super.IsRealWord(wordParts[0]))
			{
				finalSug.addAll(CompleteWord(wordParts[0]));
			}
			return finalSug.Distinct("").toArray(new String[0]);

		}
	}

	private String[] ReSpellingSuggestions2(String word)
	{
		String[] wordParts =Helper.StringExtensions.SplitRemoveEmptyElements(word,PseudoSpace.ZWNJ+"");

		if (wordParts.length == 1)
		{
			String[] wordMayParts = SplitFromNonStickerLetters(word);

			if (wordMayParts.length == 1)
			{
				return super.SpellingSuggestions(word);
			}
			else
			{
				wordParts = wordMayParts;
			}
		}

		LinqSimulationArrayList<String> suggestions = new LinqSimulationArrayList<String>();
		LinqSimulationArrayList<String> tmpNextPartSugs = new LinqSimulationArrayList<String>();
		LinqSimulationArrayList<String> tmpPrePartSugs = new LinqSimulationArrayList<String>();
		for (String str : wordParts)
		{
			suggestions.clear();
			tmpNextPartSugs.clear();
			tmpNextPartSugs.addAll(super.SpellingSuggestions(str));
			if (tmpPrePartSugs.isEmpty())
			{
				tmpPrePartSugs.addAll(tmpNextPartSugs);
				continue;
			}

			for (String tmpNextPart : tmpNextPartSugs)
			{
				for (String tmpPrePart : tmpPrePartSugs)
				{
					suggestions.add(tmpPrePart + PseudoSpace.ZWNJ + tmpNextPart);
				}
			}
			tmpPrePartSugs.clear();
			tmpPrePartSugs.addAll(suggestions);
		}

		LinqSimulationArrayList<String> finalSug = new LinqSimulationArrayList<String>(ExtractRealWords(suggestions.Distinct("").toArray(new String[0])));
		if (super.IsRealWord(wordParts[0]))
		{
			finalSug.addAll(CompleteWord(wordParts[0]));
		}

		return finalSug.toArray(new String[0]);
	}

	private String[] SplitFromNonStickerLetters(String word)
	{
		int start = 0;

		LinqSimulationArrayList<Integer> index = new LinqSimulationArrayList<Integer>();
		while (start > -1)
		{
			index.add(StringExtensions.IndexOfAny(word,PersianAlphabets.NonStickerChars, start + 1));
			start = index.get(index.size()-1);
		}

		double min = word.length();
		int bestIndex = 0;
		for (int idx : index)
		{
			if (Math.abs(word.length() / 2 - idx) < min)
			{
				min = Math.abs(word.length() / 2 - idx);
				bestIndex = idx;
			}
		}

		return new String[] {word.substring(0, bestIndex + 1), word.substring(bestIndex + 1, bestIndex + 1 + word.length() - bestIndex - 1)};
	}

	private String[] RuleBasedSuggestions(String word)
	{
		return RuleBasedCorrection(new String[] {word});
	}

	private String[] RuleBasedCorrection(String[] wordArray)
	{
		LinqSimulationArrayList<String> sugList = new LinqSimulationArrayList<String>();

		for (String word : wordArray)
		{
			if (StringExtensions.EndsWith(word,RuleBasedSpelling.PluralAT))
			{
				sugList.addAll(SuffixReplacement(word, RuleBasedSpelling.PluralAT, RuleBasedSpelling.PluralATReplacement));
			}
			if (this.ContainWord(word))
			{
				sugList.add(word);
			}
		}

		return sugList.toArray(new String[0]);
	}

	private String[] SuffixReplacement(String word, String[] replacee, String[] replacement)
	{
		String tmpWord = "";
		for (String rep : replacee)
		{
			if (word.endsWith(rep))
			{
				if (word.lastIndexOf(rep) >= 0)
				{
					tmpWord = word.substring(0, word.lastIndexOf(rep)) + word.substring(word.lastIndexOf(rep) + rep.length());
				}
				//if (tmpWord.EndsWith(PseudoSpace.ZWNJ.ToString()))
				if (tmpWord.length() > 1)
				{
					if (tmpWord.charAt(tmpWord.length() - 1) == PseudoSpace.ZWNJ)
					{
						tmpWord = tmpWord.substring(0, tmpWord.length() - 1) + tmpWord.substring(tmpWord.length());
					}
				}
				if (super.IsRealWord(tmpWord))
				{
					word = tmpWord;
					break;
				}
			}
		}

		if (!word.equals(tmpWord))
		{
			return new String[0];
		}

		LinqSimulationArrayList<String> tmpLst = new LinqSimulationArrayList<String>();
		for (String rep : replacement)
		{
			String sug = tmpWord + rep;
			tmpLst.add(sug);

			//Add ranking detail
			if (!super.m_rankingDetail.containsKey(sug))
			{
				super.m_rankingDetail.put(sug, RuleBasedSuggestionMessage);
			}
		}

		return tmpLst.toArray(new String[0]);
	}

	private static String[] RemoveIteration(String[] baseWord, String[] array)
	{
		LinqSimulationArrayList<String> res = new LinqSimulationArrayList<String>();

		for (String str : array)
		{
			if (ArraysUtility.indexOf(baseWord, str) == -1)
			{
				res.add(str);
			}
		}

		return res.toArray(new String[0]);
	}

	private boolean CheckForIteration(String word, String nxtWord, int suggestionCount, Helper.RefObject<String[]> suggestions, Helper.RefObject<SuggestionType> suggestionType, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState, boolean realWord)
	{
		if (!word.equals(nxtWord))
		{
			suggestions.argvalue = new String[0];
			suggestionType.argvalue = SuggestionType.Green;
			spaceCorrectionState.argvalue = SpaceCorrectionState.None;

			return true;
		}

		LinqSimulationArrayList<String> tmpSug = new LinqSimulationArrayList<String>();
		String sug = CheckForSpaceInsertation(word, word, nxtWord, spaceCorrectionState);
		if (sug.length() != 0)
		{
			tmpSug.add(sug);
		}
		spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceInsertationLeft;

		if (realWord)
		{
			suggestionType.argvalue = SuggestionType.Green;
			//Add One iteration in place of twice
			tmpSug.add(word);
		}
		else
		{
				///#region Check Spelling and Add
			String[] outSug = null;
			Helper.RefObject<String[]> tempRef_outSug = new Helper.RefObject<String[]>(outSug);
			DoAdvancedSpellCheck(word, word, nxtWord, suggestionCount, tempRef_outSug, suggestionType, spaceCorrectionState);
			outSug = tempRef_outSug.argvalue;
			for (String str : outSug)
			{
				if (!tmpSug.contains(str))
				{
					if (tmpSug.size() < suggestionCount)
					{
						tmpSug.add(str);
					}
				}
			}
		}
			///#region Add ranking detail

		for (String str : tmpSug)
		{
			if (!super.m_rankingDetail.containsKey(str))
			{
				super.m_rankingDetail.put(str, ItterationSuggestionMessage);
			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		suggestions.argvalue = tmpSug.Distinct("").toArray(new String[0]);

		return false;
	}

		///#region Spacing

	/** 
	 correct wrong insert/delete of white space between words
	 
	 @param word Input word
	 @param preWord Previous word
	 @param nxtWord Next word
	 @param spaceCorrectionState return the spacing correction state
	 @return 
	 word with corrected spacing
	 
	*/
	private String[] CheckSpellingWithSpacingConsideration(String word, String preWord, String nxtWord, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState)
	{
		LinqSimulationArrayList<String> sugs = new LinqSimulationArrayList<String>();

			///#region Space Deletatin, No: 4, 5

		//string[] aser = AffixSpacingSeriallyCheck(this.CheckForSpaceDeletation(word, out spaceCorrectionState));
		String[] aser = this.CheckForSpaceDeletation(word, spaceCorrectionState);
		sugs.addAll(aser);

		
			///#region Space Insertation, No: 1

		if (sugs.size() <= 0)
		{
			String sug = this.CheckForSpaceInsertation(word, preWord, nxtWord, spaceCorrectionState);
			if (sug.length() > 0)
			{
				LinqSimulationArrayList<String> newSugs = new LinqSimulationArrayList<String>();

				for (String str : sugs)
				{
					if (spaceCorrectionState.argvalue.getValue() == SpaceCorrectionState.SpaceInsertationLeft.getValue() || spaceCorrectionState.argvalue == SpaceCorrectionState.SpaceInsertationLeftSerrially)
					{
						newSugs.add(str + ' ' + nxtWord);
					}
					else if (spaceCorrectionState.argvalue.getValue() == SpaceCorrectionState.SpaceInsertationRight.getValue() || spaceCorrectionState.argvalue == SpaceCorrectionState.SpaceInsertationRightSerrially)
					{
						newSugs.add(preWord + ' ' + str);
					}
				}
				sugs.clear();

				sugs.addAll(newSugs.toArray(new String[0]));
				sugs.add(sug);
			}
		}

		if (sugs.size() <= 0)
		{
			aser = this.CheckForSpaceInsertainDeletation(word, preWord, nxtWord, spaceCorrectionState);
			sugs.addAll(RemoveIteration(sugs.toArray(new String[0]), aser));
		}


		if (sugs.size() <= 0)
		{

			if (word.length() < 60)
			{
				aser = this.CheckForSpaceDeletationSerially(word, 0);
				sugs.addAll(RemoveIteration(sugs.toArray(new String[0]), aser));

				if (sugs.size() > 0)
				{
					spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceDeletationSerrially;
				}
			}
		}


		if (sugs.size() <= 0)
		{
			if (word.length() + preWord.length() < 70 && word.length() + nxtWord.length() < 70)
			{
				aser = CheckForSpaceDeletationSeriallyInsertation(word, preWord, nxtWord, spaceCorrectionState);
				sugs.addAll(RemoveIteration(sugs.toArray(new String[0]), aser));
			}
		}


		for (String str : sugs)
		{
			if (!super.m_rankingDetail.containsKey(str))
			{
				super.m_rankingDetail.put(str, SpacingSuggestionMessage);
			}
		}


		return sugs.toArray(new String[0]);
	}


	private String[] SortSpacingSugs(String[] words)
	{
		if (words.length <= 1)
		{
			return words;
		}

		LinqSimulationHashMap<String, Double> dic = new LinqSimulationHashMap<String, Double>();

		String[] splitedWords;
		double maturityCo = 0, avgFreq;
		for (String str : words)
		{
			if(str==null)
				continue;
			if (!str.contains(" "))
			{
				return words;
			}

			splitedWords = str.split("[ ]", -1);
			avgFreq = CalcAvgFreq(splitedWords);
			if (avgFreq > CalcAvgFreq())
			{
				maturityCo = avgFreq / (splitedWords.length * (1 + CalcNumberofIteration(splitedWords)) + CalcNumberofEqualWords(splitedWords));

				if (CalcNumberofIteration(splitedWords) == 0)
				{
					dic.put(str, maturityCo);
				}
			}
		}

		LinqSimulationArrayList<String> suggestions = new LinqSimulationArrayList<String>();

		double avg = 0;
		if (dic.size() > 2)
		{
			avg = dic.Average(dic.values());
		}
		for (java.util.Map.Entry<String, Double> pair : dic.entrySet())
		{
			if (pair.getValue() >= avg)
			{
				suggestions.add(pair.getKey());
			}
		}

		String temp;
		for (int i = 0; i < suggestions.size() - 1; ++i)
		{
			for (int j = i + 1; j < suggestions.size(); ++j)
			{
				if (dic.get(suggestions.get(i)) < dic.get(suggestions.get(j)))
				{
					temp = suggestions.get(i);
					suggestions.set(i, suggestions.get(j));
					suggestions.set(j, temp);
				}
			}
		}

		return suggestions.toArray(new String[0]);
	}

	private static int CalcNumberofEqualWords(String[] splitedWords)
	{
		int iteration = 0;

		LinqSimulationArrayList<String> myList = new LinqSimulationArrayList<String>(splitedWords);

		for (String str : splitedWords)
		{
			myList.remove(0);
			if (myList.contains(str))
			{
				++iteration;
			}
		}

		return iteration;

	}

	private static int CalcNumberofIteration(String[] splitedWords)
	{
		int iteration = 0;

		for (int i = 0; i < splitedWords.length - 1; ++i)
		{
			if (splitedWords[i].equals(splitedWords[i + 1]))
			{
				++iteration;
			}
		}

		return iteration;
	}

	/*
private static string[] SortSpacingSugs(string[] words, int count)
{
Dictionary<string, int> dic = new Dictionary<string, int>();

foreach (string str in words)
{
	dic.Add(str, str.Split(' ').Length);
}

string temp;
for (int i = 0; i < words.Length - 1; ++i)
{
	for (int j = i + 1; j < words.Length; ++j)
	{
	    if (dic[words[i]] > dic[words[j]])
	    {
	        temp = words[i];
	        words[i] = words[j];
	        words[j] = temp;
	    }
	}
}

string[] suggestions = new string[Math.Min(count, words.Length)];
for (int i = 0; i < suggestions.Length; ++i)
{
	suggestions[i] = words[i];
}

return suggestions;
}
*/


		///#endregion

	private String CheckForSpaceInsertation(String word, String preWord, String nxtWord, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState)
	{
		spaceCorrectionState.argvalue = SpaceCorrectionState.None;
		String suggestion;

			///#region Word Joining Next Word

		if (!word.equals(nxtWord))
		{
			suggestion = word + PseudoSpace.ZWNJ + nxtWord;

			if (!this.ContainWord(suggestion) && !word.equals(suggestion))
			{
				suggestion = word + nxtWord;
			}

			if (this.ContainWord(suggestion) && !word.equals(suggestion))
			{
				spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceInsertationLeft;

				return suggestion;
			}
		}


		if (!word.equals(preWord))
		{
			suggestion = preWord + PseudoSpace.ZWNJ + word;

			if (!this.ContainWord(suggestion) && !word.equals(suggestion))
			{
				suggestion = preWord + word;
			}

			if (this.ContainWord(suggestion) && !word.equals(suggestion))
			{
				spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceInsertationRight;

				return suggestion;
			}
		}

		return "";
	}

	private String[] CheckForSpaceDeletation(String word, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState)
	{
		spaceCorrectionState.argvalue = SpaceCorrectionState.None;

		LinqSimulationArrayList<String> sugs = new LinqSimulationArrayList<String>();

		// Do not replace ZWNJ with white space
		String[] words = SubstituteStepBy(word, PseudoSpace.ZWNJ, ' ');
		sugs.addAll(ExtractTwoRealWordParts(words));

		words = WordGenerator.GenerateRespelling(word, 1, RespellingGenerationType.Insert, ' ');
		sugs.addAll(ExtractTwoRealWordParts(words));

		words = WordGenerator.GenerateRespelling(word, 1, RespellingGenerationType.Insert, PseudoSpace.ZWNJ);
		//sugs.AddRange(ExtractTwoRealWordParts(words));
		sugs.addAll(ExtractRealWords(words));

		if (sugs.size() > 0)
		{
			spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceDeletation;
		}
		return sugs.Distinct("").toArray(new String[]{""});
	}
	private static String[] SubstituteStepBy(String word, char cBase, char cSub)
	{
		LinqSimulationArrayList<String> words = new LinqSimulationArrayList<String>();

		int index = 0;
		do
		{
			index = word.indexOf(cBase, index);
			if (index != -1)
			{
				words.add(Helper.StringExtensions.Insert(
						(word.substring(0, index) + word.substring(index + 1))
						,index, (new Character(cSub)).toString()));
				++index;
			}
		} while (index != -1);

		return words.toArray(new String[0]);
	}
	private String[] ExtractTwoRealWordParts(String[] words)
	{
		LinqSimulationArrayList<String> sugs = new LinqSimulationArrayList<String>();

		String[] wordParts;
		for (String word : words)
		{
			//wordParts = word.Split(PseudoSpace.ZWNJ);
			//if (wordParts.Length == 2)
			//{
			//    if (wordParts[0].Length > 0 && wordParts[1].Length > 0)
			//    {
			//        if (!wordParts[0].Contains(' ') && !wordParts[1].Contains(' '))
			//        {
			//            //Commented on 30 March 2010
			//            //if (this.ContainWord(word))
			//            if (base.IsRealWord(word))
			//            {
			//                sugs.Add(word);
			//            }
			//        }
			//    }
			//}

			wordParts = word.split("[ ]", -1);
			if (wordParts.length == 2)
			{
				if (wordParts[0].length() > 0 && wordParts[1].length() > 0)
				{
					//Commented on 25 Oct 2010
					//if (((ContainWord(wordParts[0]) && wordParts[0].Length >= 4) || base.IsRealWord(wordParts[0])) && 
					//    ((ContainWord(wordParts[1]) && wordParts[1].Length >= 4) || base.IsRealWord(wordParts[1])))
					if (ContainWord(wordParts[0]) && ContainWord(wordParts[1]))
					{
						sugs.add(word);
					}
				}
			}
		}
		return sugs.Distinct("").toArray(new String[]{""});
	}

	private String[] CheckForSpaceInsertainDeletation(String word, String preWord, String postWord, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState)
	{
		spaceCorrectionState.argvalue = SpaceCorrectionState.None;
		LinqSimulationArrayList<String> sugs = new LinqSimulationArrayList<String>();

		String tmpWord = word + postWord;
		if ((!word.equals(postWord)) && (!word.equals(tmpWord)))
		{
			sugs.addAll(CheckForSpaceDeletation(tmpWord, spaceCorrectionState));

			if (sugs.size() > 0)
			{
				spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceInsertationLeft;
			}
		}

		if (sugs.isEmpty())
		{
			tmpWord = preWord + word;
			if ((!word.equals(postWord)) && (!word.equals(tmpWord)))
			{
				sugs.addAll(CheckForSpaceDeletation(tmpWord, spaceCorrectionState));

				if (sugs.size() > 0)
				{
					spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceInsertationRight;
				}
			}
		}

		return sugs.toArray(new String[0]);
	}

	private String[] CheckForSpaceDeletationSerially(String word, int input)
	{
		String incompleteWord;
		String[] midRes;
		String[] midApp;
		LinqSimulationArrayList<String> lst = new LinqSimulationArrayList<String>();
		StringBuilder sb = new StringBuilder();
		int i = 0;
		char[] charArray=word.toCharArray();
		for (char c : charArray)
		{
			++i;
			sb.append(c);
			incompleteWord = sb.toString();
			word = word.substring(0, 0) + word.substring(0 + 1);

			if (i >= input)
			{
				if (this.ContainWord(incompleteWord))
				{
					if (word.equals(""))
					{
						return new String[] {incompleteWord};
					}
					else
					{
						midRes = CheckForSpaceDeletationSerially(word, 0);
						if (midRes.length > 0)
						{
							midApp = AppendList(incompleteWord, midRes);
							lst.addAll(midApp);

							midRes = CheckForSpaceDeletationSerially(incompleteWord + word, i + 1);
							if (midRes.length > 0)
							{
								//midApp = AppendList(incompleteWord, midRes);
								//lst.AddRange(midApp);
								lst.addAll(midRes);

							}

							return lst.toArray(new String[0]);
						}
					}
				}
			}
		}

		return new String[0];
	}

	private static String[] AppendList(String incompleteWord, String[] midRes)
	{
		LinqSimulationArrayList<String> lst = new LinqSimulationArrayList<String>();

		for (String str : midRes)
		{
			lst.add(incompleteWord + " " + str);
		}

		return lst.toArray(new String[0]);
	}

	private String[] CheckForSpaceDeletationSeriallyInsertation(String word, String preWord, String postWord, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState)
	{

		spaceCorrectionState.argvalue = SpaceCorrectionState.None;
		LinqSimulationArrayList<String> sugs = new LinqSimulationArrayList<String>();

		String tmpWord = word + postWord;
		if ((!word.equals(postWord)) && (!word.equals(tmpWord)))
		{
			sugs.addAll(CheckForSpaceDeletationSerially(tmpWord, 0));

			if (sugs.size() > 0)
			{
				spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceInsertationLeftSerrially;
			}
		}

		if (sugs.isEmpty())
		{
			tmpWord = preWord + word;
			if ((!word.equals(postWord)) && (!word.equals(tmpWord)))
			{
				sugs.addAll(CheckForSpaceDeletationSerially(tmpWord, 0));

				if (sugs.size() > 0)
				{
					spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceInsertationRightSerrially;
				}
			}
		}

		return sugs.toArray(new String[0]);
	}

	private String[] SortSpacingAndSpellingSugs(String[] finalSpacingSeg, String[] finalSpellingSeg, Helper.RefObject<Boolean> haveSpacingSug)
	{
		haveSpacingSug.argvalue = false;

		LinqSimulationArrayList<String> tmpSugs = new LinqSimulationArrayList<String>(finalSpellingSeg);

		double avgFreq;
		for (String strSpace : finalSpacingSeg)
		{
			if(strSpace==null)
				continue;
			if (!strSpace.contains(" "))
			{
				tmpSugs.add(0, strSpace);
				haveSpacingSug.argvalue = true;
				continue;
			}

			avgFreq = CalcAvgFreq(strSpace.split("[ ]", -1));

			for (String strSpell : finalSpellingSeg)
			{
				if (WordFrequency(strSpell) < avgFreq)
				{
					tmpSugs.add(tmpSugs.indexOf(strSpell), strSpace);
					haveSpacingSug.argvalue = true;
					break;
				}
			}

			if (finalSpellingSeg.length == 0)
			{
				tmpSugs.add(strSpace);
			}
		}
		return tmpSugs.toArray(new String[0]);
	}

	/** 
	 If the word contain the persian affix, this method check or try to correct its spelling without affix 
	 
	 @param word Input word
	 @param suggestionCount Number of returned suggestions
	 @return 
	 if word don't have affix return an array with one string with lenght 0
	 if word without its affix being correct, return word
	 if word without its affix being incorrect, correct the word without affix and return the corrected word with corresponding affix
	 
	*/
	private String[] CheckSpellingWithAffixConsideration(String word, int suggestionCount)
	{
		LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();
		String tmpSug, stem, suffix;

		ReversePatternMatcherPatternInfo[] rpmpiSet = StripAffixs(word);
		if (rpmpiSet.length == 0)
		{
			return new String[0];
		}

		for (int i = 0; i < Math.min(2, rpmpiSet.length); ++i)
		{
			suffix = rpmpiSet[i].getSuffix();
			stem = rpmpiSet[i].getBaseWord();

			for (String str : super.SpellingSuggestions(word))
			{
				suffix = InflectionAnalyser.EqualSuffixWithCorrectPhonetic(str, suffix, m_persianSuffixRecognizer.SuffixCategory(suffix));

				PersianPOSTag posTag = super.WordPOS(stem);
				tmpSug = CorrectSuffixSpacing(str, suffix, posTag);

				if (!suggestion.contains(tmpSug))
				{
					if (IsRealWordConsideringAffixes(tmpSug))
					{
						suggestion.add(tmpSug);
					}
				}
			}

		}

		return SortSuggestions(word, suggestion.toArray(new String[0]), suggestionCount);
	}
	private String[] CheckSpellingWithAffixConsideration(String word, int suggestionCount, Helper.RefObject<Boolean> phoneticSuffixProblem)
	{
		phoneticSuffixProblem.argvalue = false;

		LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

		String tmpSug, stem, suffix;

		//Dictionary<string, PersianPOSTag> posDic;
		//Dictionary<string, int> freqDic;

		PersianPOSTag posTag;
		ReversePatternMatcherPatternInfo[] rpmpiSet = StripAffixs(word);
		if (rpmpiSet.length == 0)
		{
			return new String[0];
		}


		if (!super.IsRealWord(word))
		{
			for (ReversePatternMatcherPatternInfo suffixPatternInfo : rpmpiSet)
			{
				suffix = suffixPatternInfo.getSuffix();
				stem = suffixPatternInfo.getBaseWord();


				suffix = InflectionAnalyser.EqualSuffixWithCorrectPhonetic(stem, suffix, m_persianSuffixRecognizer.SuffixCategory(suffix));

				posTag = super.WordPOS(stem);
				tmpSug = CorrectSuffixSpacing(stem, suffix, posTag);

				if (!word.equals(tmpSug) && !tmpSug.equals(stem))
				{
					if (super.IsRealWord(stem) && IsRealWordConsideringAffixes(tmpSug))
					{
						suggestion.add(tmpSug);

						//add ranking detail
						if (!super.m_rankingDetail.containsKey(tmpSug))
						{
							super.m_rankingDetail.put(tmpSug, SuffixSuggestionMessage);
						}
					}
				}
			}

			if (suggestion.size() > 0)
			{
				phoneticSuffixProblem.argvalue = true;
				return suggestion.Distinct("").toArray(new String[]{""});
			}
		}


		//for (int i = 0; i < Math.Min(2, rpmpiSet.Length); ++i)
		for (int i = 0; i < rpmpiSet.length; ++i)
		{
			suffix = rpmpiSet[i].getSuffix();
			stem = rpmpiSet[i].getBaseWord();

			//string[] spellingSugs = AllSpellingSuggestions(stem, out freqDic, out posDic);
			//string[] spellingSugs = SortSuggestions(stem, AllSpellingSuggestions(stem, out freqDic, out posDic), suggestionCount);

			String[] spellingSugs = SortSuggestions(stem, ReSpellingSuggestions(stem, 1), suggestionCount);
			//string[] spellingSugs = SortSuggestions(stem, base.SpellingSuggestion(stem), suggestionCount);

			for (String spellSug : spellingSugs)
			{
				//suffix = InflectionAnalyser.EqualSuffixWithCorrectPhonetic(spellSug, suffix,
				//                                                           m_persianSuffixRecognizer.SuffixCategory(suffix));

				posTag = super.WordPOS(spellSug);
				if (IsValidSuffixDeclension(spellSug, suffix, posTag))
				{
					tmpSug = CorrectSuffixSpacing(spellSug, suffix, posTag);
					suggestion.add(tmpSug);

					//if (freqDic[spellSug] > 0)
					//{
					//    freqAffixDic.Add(tmpSug, freqDic[spellSug] / 3);
					//}
					//else
					//{
					//    freqAffixDic.Add(tmpSug, 1);
					//}
				}
			}
		}



		//return SortSuggestions(word, suggestion.ToArray(), freqAffixDic, suggestionCount);
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		return suggestion.Distinct("").toArray(new String[]{""});
	}
	private void CheckSpellingWithAffixConsiderationThreadSafe(Object data)
	{
		AffixCorrectionData inputData = (AffixCorrectionData)data;

		String word = inputData.m_word;
		int suggestionCount = inputData.m_suggestionCount;

		LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

		String tmpSug, stem, suffix;

		//Dictionary<string, PersianPOSTag> posDic;
		//Dictionary<string, int> freqDic;

		PersianPOSTag posTag;
		ReversePatternMatcherPatternInfo[] rpmpiSet = StripAffixs(word);
		if (rpmpiSet.length == 0)
		{
			inputData.m_suggestions = new String[0];
			inputData.m_havePhoneticSuffixProblem = false;
			return;
		}

			///#region If JUST Phonetic Correction is required

		if (!super.IsRealWord(word))
		{
			for (ReversePatternMatcherPatternInfo suffixPatternInfo : rpmpiSet)
			{
				suffix = suffixPatternInfo.getSuffix();
				stem = suffixPatternInfo.getBaseWord();


				suffix = InflectionAnalyser.EqualSuffixWithCorrectPhonetic(stem, suffix, m_persianSuffixRecognizer.SuffixCategory(suffix));

				posTag = super.WordPOS(stem);
				tmpSug = CorrectSuffixSpacing(stem, suffix, posTag);

				if (!word.equals(tmpSug) && !tmpSug.equals(stem))
				{
					if (super.IsRealWord(stem) && IsRealWordConsideringAffixes(tmpSug))
					{
						suggestion.add(tmpSug);

						//add ranking detail
						if (!super.m_rankingDetail.containsKey(tmpSug))
						{
							super.m_rankingDetail.put(tmpSug, SuffixSuggestionMessage);
						}
					}
				}
			}

			if (suggestion.size() > 0)
			{
				inputData.m_suggestions = suggestion.Distinct("").toArray(new String[]{""});
				inputData.m_havePhoneticSuffixProblem = true;
				return;
			}
		}

		//for (int i = 0; i < Math.Min(2, rpmpiSet.Length); ++i)
		for (int i = 0; i < rpmpiSet.length; ++i)
		{
			suffix = rpmpiSet[i].getSuffix();
			stem = rpmpiSet[i].getBaseWord();

			//string[] spellingSugs = AllSpellingSuggestions(stem, out freqDic, out posDic);
			//string[] spellingSugs = SortSuggestions(stem, AllSpellingSuggestions(stem, out freqDic, out posDic), suggestionCount);

			//string[] spellingSugs = SortSuggestions(stem, ReSpellingSuggestions(stem, 1), suggestionCount);
			//string[] spellingSugs = SortSuggestions(stem, base.SpellingSuggestion(stem), suggestionCount);
			String[] spellingSugs = SortSuggestions(stem, super.SpellingSuggestions2(stem), suggestionCount);

			for (String spellSug : spellingSugs)
			{
				//suffix = InflectionAnalyser.EqualSuffixWithCorrectPhonetic(spellSug, suffix,
				//                                                           m_persianSuffixRecognizer.SuffixCategory(suffix));

				posTag = super.WordPOS(spellSug);
				if (IsValidSuffixDeclension(spellSug, suffix, posTag))
				{
					tmpSug = CorrectSuffixSpacing(spellSug, suffix, posTag);
					suggestion.add(tmpSug);

					//if (freqDic[spellSug] > 0)
					//{
					//    freqAffixDic.Add(tmpSug, freqDic[spellSug] / 3);
					//}
					//else
					//{
					//    freqAffixDic.Add(tmpSug, 1);
					//}
				}
			}
		}



		//return SortSuggestions(word, suggestion.ToArray(), freqAffixDic, suggestionCount);
		inputData.m_suggestions = suggestion.Distinct("").toArray(new String[]{""});
		inputData.m_havePhoneticSuffixProblem = false;
		return;
	}

	private ReversePatternMatcherPatternInfo[] StripAffixs(String word)
	{
		return this.m_persianSuffixRecognizer.MatchForSuffix(word);
	}

	private boolean IsRealWordConsideringAffixes(String word)
	{
		ReversePatternMatcherPatternInfo[] affixeResults = this.StripAffixs(word);
		if (affixeResults.length == 0)
		{
			return false;
		}

		PersianPOSTag posTag = PersianPOSTag.forValue(0);
		int freq = 0;
		String stem, suffix;

		for (ReversePatternMatcherPatternInfo rpmpi : affixeResults)
		{
			stem = rpmpi.getBaseWord();
			suffix = rpmpi.getSuffix();

			Helper.RefObject<Integer> tempRef_freq = new Helper.RefObject<Integer>(freq);
			Helper.RefObject<PersianPOSTag> tempRef_posTag = new Helper.RefObject<PersianPOSTag>(posTag);
			boolean tempVar = super.IsRealWord(stem, tempRef_freq, tempRef_posTag);
				freq = tempRef_freq.argvalue;
			posTag = tempRef_posTag.argvalue;
			if (tempVar)
			{
				if (CorrectSuffixSpacing(stem, suffix, posTag).equals(word)) //Spacing
				{
					if (IsValidSuffixDeclension(stem, suffix, posTag))
					{
						return true;
					}
				}
			}
		}

		return false;
	}
	private boolean IsRealWordConsideringAffixes(String word, Helper.RefObject<String> wordWithoutAffix, Helper.RefObject<String> affix)
	{
		wordWithoutAffix.argvalue = "";
		affix.argvalue = "";

		ReversePatternMatcherPatternInfo[] affixeResults = this.StripAffixs(word);
		if (affixeResults.length == 0)
		{
			return false;
		}

		PersianPOSTag posTag = PersianPOSTag.forValue(0);
		int freq = 0;
		String stem, suffix;

		for (ReversePatternMatcherPatternInfo rpmpi : affixeResults)
		{
			stem = rpmpi.getBaseWord();
			suffix = rpmpi.getSuffix();

			Helper.RefObject<Integer> tempRef_freq = new Helper.RefObject<Integer>(freq);
			Helper.RefObject<PersianPOSTag> tempRef_posTag = new Helper.RefObject<PersianPOSTag>(posTag);
			boolean tempVar = super.IsRealWord(stem, tempRef_freq, tempRef_posTag);
				freq = tempRef_freq.argvalue;
			posTag = tempRef_posTag.argvalue;
			if (tempVar)
			{
				if (CorrectSuffixSpacing(stem, suffix, posTag).equals(word)) //Spacing
				{
					if (IsValidSuffixDeclension(stem, suffix, posTag))
					{
						wordWithoutAffix.argvalue = rpmpi.getBaseWord();
						affix.argvalue = rpmpi.getSuffix();

						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean CheckAffixSpacing(String word, String preWord, String nxtWord, Helper.RefObject<String[]> suggestions, Helper.RefObject<SuggestionType> suggestionType, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState)
	{
		suggestionType.argvalue = SuggestionType.Green;
		spaceCorrectionState.argvalue = SpaceCorrectionState.None;

		LinqSimulationArrayList<String> spacingSug = new LinqSimulationArrayList<String>();
		boolean exitFlag = false;
		String sug;

		//check Vocabulary Words Space Correction Rule
		if (ContainPrefix(word) || this.SpellingRuleExist(SpellingRules.VocabularyWordsSpaceCorrection))
		{
			if (IsStickerPrefix(word))
			{
				if (IsValidPrefix(word, nxtWord))
				{
					sug = CheckForSpaceInsertation(word, word, nxtWord, spaceCorrectionState);
					if (!sug.equals("") && !word.equals(sug))
					{
						suggestionType.argvalue = SuggestionType.Red;

						if (!spacingSug.contains(sug))
						{
							spacingSug.add(sug);
						}
						exitFlag = true;
					}
				}
			}
			else
			{
				if (!this.ContainWord(word))
				{
					String wordWithoutPrefix = null;
					Helper.RefObject<String> tempRef_wordWithoutPrefix = new Helper.RefObject<String>(wordWithoutPrefix);
					String prefix = ExtractPrefix(word, tempRef_wordWithoutPrefix);
					wordWithoutPrefix = tempRef_wordWithoutPrefix.argvalue;
					if (!wordWithoutPrefix.equals(""))
					{
						if (prefix.equals("به") && this.ContainWord(wordWithoutPrefix))
						{
							sug = prefix + " " + wordWithoutPrefix;

							spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceDeletation;
							suggestionType.argvalue = SuggestionType.Red;

							if (!spacingSug.contains(sug))
							{
								spacingSug.add(sug);
							}
							exitFlag = true;
						}

						if ((prefix.equals("می") || prefix.equals("نمی")) && this.ContainWord(wordWithoutPrefix))
						{
							sug = prefix + PseudoSpace.ZWNJ + wordWithoutPrefix;
							if (this.ContainWord(sug))
							{
								//spaceCorrectionState = SpaceCorrectionState.SpaceInsertationLeft;
								suggestionType.argvalue = SuggestionType.Red;

								if (!spacingSug.contains(sug))
								{
									spacingSug.add(sug);
								}
								exitFlag = true;
							}
						}
					}
				}
			}


			for (String str : spacingSug)
			{
				if (!super.m_rankingDetail.containsKey(str))
				{
					super.m_rankingDetail.put(str, PrefixSuggestionMessage);
				}
			}


		}

		if (!exitFlag)
		{

			String[] tmpSugArray = null;
			Helper.RefObject<String[]> tempRef_tmpSugArray = new Helper.RefObject<String[]>(tmpSugArray);
			boolean tempVar = !CheckSuffixSpacing(word, nxtWord, tempRef_tmpSugArray, suggestionType, spaceCorrectionState);
				tmpSugArray = tempRef_tmpSugArray.argvalue;
			if (tempVar)
			{
				spacingSug.addAll(tmpSugArray);
				exitFlag = true;

				for (String str : spacingSug)
				{
					if (!super.m_rankingDetail.containsKey(str))
					{
						super.m_rankingDetail.put(str, SuffixSuggestionMessage);
					}
				}
			}
		}

		if (exitFlag)
		{
			suggestions.argvalue = spacingSug.toArray(new String[0]);
			return false;
		}

		suggestions.argvalue = new String[0];
		return true;
	}

	// Correct affixes in spacing suggestions
	private String[] AffixSpacingSeriallyCheck(String[] sugs)
	{
		SpaceCorrectionState scs = SpaceCorrectionState.None;
		SuggestionType st = SuggestionType.forValue(0);

		String[] affixSpacingSugs;

		LinqSimulationArrayList<String> tmpFinalSuggestions = new LinqSimulationArrayList<String>();

		LinqSimulationArrayList<String> tmpSuggestions = new LinqSimulationArrayList<String>();
		LinqSimulationArrayList<String> tmpPrePartSugs = new LinqSimulationArrayList<String>();
		for (String str : sugs)
		{
			if(str==null)
				continue;
			String[] parts = str.split("[ ]", -1);
			for (int i = 0; i < parts.length; ++i)
			{
				affixSpacingSugs = new String[0];

				if (i < parts.length - 1)
				{
					Helper.RefObject<String[]> tempRef_affixSpacingSugs = new Helper.RefObject<String[]>(affixSpacingSugs);
					Helper.RefObject<SuggestionType> tempRef_st = new Helper.RefObject<SuggestionType>(st);
					Helper.RefObject<SpaceCorrectionState> tempRef_scs = new Helper.RefObject<SpaceCorrectionState>(scs);
					CheckAffixSpacing(parts[i], "", parts[i + 1], tempRef_affixSpacingSugs, tempRef_st, tempRef_scs);
					affixSpacingSugs = tempRef_affixSpacingSugs.argvalue;
					st = tempRef_st.argvalue;
					scs = tempRef_scs.argvalue;
				}
				if (affixSpacingSugs.length > 0)
				{
					if (i == 0)
					{
						if (scs.getValue() == SpaceCorrectionState.SpaceInsertationLeft.getValue() || scs == SpaceCorrectionState.SpaceInsertationRight)
						{
							i++;
						}

						tmpPrePartSugs.addAll(affixSpacingSugs);
						continue;
					}

					for (String affixCorectedSug : affixSpacingSugs)
					{
						for (String prePart : tmpPrePartSugs)
						{
							tmpSuggestions.add(prePart + " " + affixCorectedSug);
						}
					}

					if (scs.getValue() == SpaceCorrectionState.SpaceInsertationLeft.getValue() || scs == SpaceCorrectionState.SpaceInsertationRight)
					{
						i++;
					}
				}
				else
				{
					if (i == 0)
					{
						tmpPrePartSugs.add(parts[i]);

						if (scs.getValue() == SpaceCorrectionState.SpaceInsertationLeft.getValue() || scs == SpaceCorrectionState.SpaceInsertationRight)
						{
							i++;
						}

						continue;
					}

					for (String prePart : tmpPrePartSugs)
					{
						tmpSuggestions.add(prePart + " " + parts[i]);
					}

					if (scs.getValue() == SpaceCorrectionState.SpaceInsertationLeft.getValue() || scs == SpaceCorrectionState.SpaceInsertationRight)
					{
						i++;
					}
				}

				tmpPrePartSugs.clear();
				tmpPrePartSugs.addAll(tmpSuggestions);
				tmpSuggestions.clear();
			}

			tmpFinalSuggestions.addAll(tmpPrePartSugs);
			tmpPrePartSugs.clear();
		}

		return tmpFinalSuggestions.Distinct("").toArray(new String[]{""});
	}

	private String RefineforHehYa(String word)
	{
		if (word.endsWith("ۀ"))
		{
			word = word.substring(0, word.length() - 1) + word.substring(word.length());
			word = word + "ه‌ی";

			this.m_isRefinedforHehYa = true;
		}
		else
		{
			this.m_isRefinedforHehYa = false;
		}

		return word;
	}
	private static String[] RevertHehYa(String[] suggestions)
	{
		LinqSimulationArrayList<String> MyList = new LinqSimulationArrayList<String>();
		String tmpStr;
		for (String lstStr : suggestions)
		{
			tmpStr = lstStr;
			if (tmpStr.endsWith("ه‌ی"))
			{
				tmpStr = tmpStr.substring(0, tmpStr.length() - 3) + tmpStr.substring(tmpStr.length() - 3 + 3);
				tmpStr = tmpStr + "ۀ";
			}

			MyList.add(tmpStr);
		}

		return MyList.toArray(new String[0]);
	}

	
		///#region Suffix

	private boolean CheckSuffixSpacing(String word, String nxtWord, Helper.RefObject<String[]> suggestions, Helper.RefObject<SuggestionType> suggestionType, Helper.RefObject<SpaceCorrectionState> spaceCorrectionState)
	{
		suggestionType.argvalue = SuggestionType.Red;
		spaceCorrectionState.argvalue = SpaceCorrectionState.None;

		String tmpSug, stem, suffix;
		LinqSimulationArrayList<String> tmpSugList = new LinqSimulationArrayList<String>();

		//if (!base.IsRealWord(word))
		if (!ContainWord(word))
		{
			ReversePatternMatcherPatternInfo[] suffixPatternInfoArray = m_persianSuffixRecognizer.MatchForSuffix(word);
			if (suffixPatternInfoArray.length > 0)
			{
				for (ReversePatternMatcherPatternInfo suffixPatternInfo : suffixPatternInfoArray)
				{
					suffix = suffixPatternInfo.getSuffix();
					stem = suffixPatternInfo.getBaseWord();

						///#region Exceptions

					if (IsSuffixException(suffix) && super.IsRealWord(stem) && !IsRealWordConsideringAffixes(word))
					{
						if (tmpSugList.isEmpty())
						{
							suggestions.argvalue = new String[0];
							return true;
						}
					}


					//suffix = InflectionAnalyser.EqualSuffixWithCorrectPhonetic(stem, suffix,
					//                                                           m_persianSuffixRecognizer.
					//                                                               SuffixCategory(suffix));

					PersianPOSTag posTag = super.WordPOS(stem);
					tmpSug = CorrectSuffixSpacing(stem, suffix, posTag);

					if (!word.equals(tmpSug) && !tmpSug.equals(stem))
					{
						if (super.IsRealWord(stem) && IsRealWordConsideringAffixes(tmpSug))
						{
							tmpSugList.add(tmpSug);
						}
					}
					else if (word.equals(tmpSug) && !tmpSug.equals(stem))
					{
						if (super.IsRealWord(stem) && IsRealWordConsideringAffixes(tmpSug))
						{
							tmpSugList.clear();
						}
					}
				}

				if (tmpSugList.size() > 0)
				{
					suggestions.argvalue = tmpSugList.Distinct("").toArray(new String[0]);
					return false;
				}
			}
		}
		suffix = nxtWord;
		stem = word;

		/*
		 * Some suffixes may also be real word like امید and مانند
		 * I want to make sure these suffixes do not process like other suffixes to correct spacing
		 * also some others like ای and ها is real word too, but the frequency of use them as suffix is far more than a rel word
		 * so I add check length part
		 */
		if (!m_persianSuffixRecognizer.SuffixCategory(suffix).Is(PersianSuffixesCategory.forValue(0)))
		{
			if (!(super.IsRealWord(suffix) && suffix.length() > 3) && !StringExtensions.IsIn(suffix,m_realWordSuffixes))
			{
				PersianPOSTag posTag = super.WordPOS(stem);
				tmpSug = CorrectSuffixSpacing(stem, suffix, posTag);

				if (!word.equals(tmpSug))
				{
					if (IsRealWordConsideringAffixes(tmpSug))
					{
						spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceInsertationLeft;
						suggestions.argvalue = new String[] {tmpSug};
						return false;
					}
				}

				suffix = InflectionAnalyser.EqualSuffixWithCorrectPhonetic(stem, suffix, m_persianSuffixRecognizer.SuffixCategory(suffix));
				tmpSug = CorrectSuffixSpacing(stem, suffix, posTag);

				if (!word.equals(tmpSug))
				{
					if (IsRealWordConsideringAffixes(tmpSug))
					{
						spaceCorrectionState.argvalue = SpaceCorrectionState.SpaceInsertationLeft;
						suggestions.argvalue = new String[]{tmpSug};
						return false;
					}
				}
			}
		}

		suggestions.argvalue = new String[0];
		return true;
	}

	private boolean IsValidSuffixDeclension(String stem, String suffix, PersianPOSTag posTag)
	{
		PersianSuffixesCategory suffixCategory = PersianSuffixesCategory.forValue(0);
		Helper.RefObject<PersianSuffixesCategory> tempRef_suffixCategory = new Helper.RefObject<PersianSuffixesCategory>(suffixCategory);
		boolean tempVar = InflectionAnalyser.IsValidPhoneticComposition(stem, suffix, posTag, tempRef_suffixCategory);
			suffixCategory = tempRef_suffixCategory.argvalue;
		if (tempVar)
		{
			//PersianSuffixesCategory suffixCategory = m_persianSuffixRecognizer.SuffixCategory(suffix);
			if (InflectionAnalyser.IsValidDeclension(posTag, suffixCategory))
			{
				return true;
			}
		}

		return false;
	}

	private static boolean IsSuffixException(String suffix)
	{
		if (StringExtensions.IsIn(suffix,RuleBasedSpelling.PluralAT))
		{
			return true;
		}

		return false;
	}
	private String CorrectSuffixSpacing(String stem, String suffix, PersianPOSTag pos)
	{
		String equalSuffixWithSpacingSymbols = stem + m_persianSuffixRecognizer.EqualSuffixWithSpacingSymbols(suffix);

		for (int i = 0; i < equalSuffixWithSpacingSymbols.length(); ++i)
		{
			if (IsStartWithSpaceOrPseudoSpacePlusSymbol(equalSuffixWithSpacingSymbols.charAt(i)))
			{
				Helper.StringExtensions.Insert(
						(equalSuffixWithSpacingSymbols.substring(0, i) + equalSuffixWithSpacingSymbols.substring(i + 1)),
						i, (new Character(PseudoSpace.ZWNJ)).toString());
			}
			else if (IsStartWithSpaceOrPseudoSpaceStarSymbol(equalSuffixWithSpacingSymbols.charAt(i)))
			{
				String tmpStem = equalSuffixWithSpacingSymbols.substring(0, i);
				String tmpSuffix = equalSuffixWithSpacingSymbols.substring(i, equalSuffixWithSpacingSymbols.length());
				tmpSuffix = PurifySymbols(tmpSuffix);
				PersianCombinationSpacingState spacingState = InflectionAnalyser.CalculateSpacingState(tmpStem, tmpSuffix, pos);

				if (spacingState == PersianCombinationSpacingState.PseudoSpace)
				{
					equalSuffixWithSpacingSymbols = Helper.StringExtensions.Insert(
							(equalSuffixWithSpacingSymbols.substring(0, i) + equalSuffixWithSpacingSymbols.substring(i + 1)),
							i, (new Character(PseudoSpace.ZWNJ)).toString());
				}
				else if (spacingState == PersianCombinationSpacingState.Continous)
				{
					equalSuffixWithSpacingSymbols = equalSuffixWithSpacingSymbols.substring(0, i) + equalSuffixWithSpacingSymbols.substring(i + 1);
				}
				else
				{
					equalSuffixWithSpacingSymbols = equalSuffixWithSpacingSymbols.substring(0, i) + equalSuffixWithSpacingSymbols.substring(i + 1);
				}

				if (tmpStem.endsWith("ه") && StringExtensions.StartsWith(tmpSuffix,PersianSuffixes.PluralSignAanPermutedForHaa))
				{
					equalSuffixWithSpacingSymbols = equalSuffixWithSpacingSymbols.substring(0, i - 1) + equalSuffixWithSpacingSymbols.substring(i - 1 + 2);

					//if (equalSuffixWithSpacingSymbols[i - 1] == PseudoSpace.ZWNJ &&
					//    PersianAlphabets.NonStickerChars.Contains(equalSuffixWithSpacingSymbols[i - 2]))
					//{
					//    equalSuffixWithSpacingSymbols = equalSuffixWithSpacingSymbols.Remove(i - 1, 1);
					//}
				}
			}
		}

		return equalSuffixWithSpacingSymbols;
	}

	private static boolean IsStartWithSpaceOrPseudoSpaceStarSymbol(char c)
	{
		if (c == ReversePatternMatcher.SymbolHalfSpaceQuestionMark || c == ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar || c == ReversePatternMatcher.SymbolSpaceStar)
		{
			return true;
		}

		return false;
	}
	private static boolean IsStartWithSpaceOrPseudoSpacePlusSymbol(char c)
	{
		if (c == ReversePatternMatcher.SymbolHalfSpace || c == ReversePatternMatcher.SymbolSpaceOrHalfSpace || c == ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus || c == ReversePatternMatcher.SymbolSpacePlus)
		{
			return true;
		}

		return false;
	}
	private static String PurifySymbols(String str)
	{
		String tmpStr = str;

		if (tmpStr.contains(ReversePatternMatcher.SymbolHalfSpace+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolHalfSpace)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolHalfSpaceQuestionMark+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolHalfSpaceQuestionMark)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolSpaceOrHalfSpace+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpace)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpacePlus)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolSpaceOrHalfSpaceStar)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolSpacePlus+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolSpacePlus)).toString(), "");
		}
		if (tmpStr.contains(ReversePatternMatcher.SymbolSpaceStar+""))
		{
			tmpStr = tmpStr.replace((new Character(ReversePatternMatcher.SymbolSpaceStar)).toString(), "");
		}


		return tmpStr;
	}

		///#region Prefix

	private static boolean IsStickerPrefix(String word)
	{
		if (word.equals("می") || word.equals("نمی"))
		{
			return true;
		}

		return false;
	}
	private static boolean IsPrefix(String word)
	{
		if (word.equals("می") || word.equals("نمی") || word.equals("به"))
		{
			return true;
		}

		return false;
	}
	private boolean IsValidPrefix(String prefix, String word)
	{
		if (IsPrefix(prefix) && this.ContainWord(word))
		{
			return true;
		}

		return false;
	}
	private static String ExtractPrefix(String word, Helper.RefObject<String> wordWithoutPrefix)
	{
		wordWithoutPrefix.argvalue = word;

		String prefix = "";

		if (word.startsWith("می"))
		{
			prefix = "می";
			wordWithoutPrefix.argvalue = word.substring(0, 0) + word.substring(0 + 2);
		}
		else if (word.startsWith("نمی"))
		{
			prefix = "نمی";
			wordWithoutPrefix.argvalue = word.substring(0, 0) + word.substring(0 + 3);
		}
		else if (word.startsWith("ب"))
		{
			prefix = "به";
			wordWithoutPrefix.argvalue = word.substring(0, 0) + word.substring(0 + 1);
		}

		return prefix;
	}
	private static boolean ContainPrefix(String word)
	{
		if (word.startsWith("می") || word.startsWith("نمی") || word.startsWith("ب"))
		{
			return true;
		}

		return false;
	}

		///#region Spelling Rules

	private boolean SpellingRuleExist(SpellingRules rule)
	{
		if (rule == SpellingRules.VocabularyWordsSpaceCorrection)
		{
			return this.m_ruleVocabularyWordsSpaceCorrection;
		}
		if (rule == SpellingRules.AffixSpaceCorrection)
		{
			return this.m_ruleAffixSpaceCorrection;
		}
		if (rule == SpellingRules.CheckForCompletetion)
		{
			return this.m_ruleCheckForCompletetion;
		}
		if (rule == SpellingRules.AffixSpaceCorrectionForVocabularyWords)
		{
			return this.m_ruleAffixSpaceCorrectionForVocabularyWords;
		}
		if (rule == SpellingRules.IgnoreHehYa)
		{
			return this.m_ruleIgnoreHehYa;
		}
		if (rule == SpellingRules.IgnoreLetters)
		{
			return this.m_ruleIgnoreLetters;
		}

		return false;
	}

	private boolean OnePassConvertingRuleExist(OnePassConvertingRules rule)
	{
		if (rule == OnePassConvertingRules.ConvertHaa)
		{
			return this.m_ruleOnePassConvertHaa;
		}
		if (rule == OnePassConvertingRules.ConvertHehYa)
		{
			return this.m_ruleOnePassConvertHehYa;
		}
		if (rule == OnePassConvertingRules.ConvertMee)
		{
			return this.m_ruleOnePassConvertMee;
		}
		if (rule == OnePassConvertingRules.ConvertBe)
		{
			return this.m_ruleOnePassConvertBe;
		}
		if (rule == OnePassConvertingRules.ConvertAll)
		{
			return this.m_ruleOnePassConvertAll;
		}

		return false;
	}

	private void SetDefaultSpellingRules()
	{
		this.m_ruleVocabularyWordsSpaceCorrection = false;
		this.m_ruleAffixSpaceCorrection = true;
		this.m_ruleCheckForCompletetion = true;
		this.m_ruleAffixSpaceCorrectionForVocabularyWords = false;
		this.m_ruleIgnoreHehYa = false;
		this.m_ruleIgnoreLetters = false;
	}

	private void SetDefaultOnePassSpacingRules()
	{
		this.m_ruleOnePassConvertHaa = true;
		this.m_ruleOnePassConvertHehYa = true;
		this.m_ruleOnePassConvertMee = true;
		this.m_ruleOnePassConvertBe = true;
		this.m_ruleOnePassConvertAll = true;
	}
}