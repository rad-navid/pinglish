package SCICT.NLP.TextProofing.SpellChecker;

import Helper.ArraysUtility;
import Helper.LinqSimulationArrayList;
import Helper.LinqSimulationHashMap;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Morphology.Lemmatization.*;
import SCICT.NLP.Utility.StringDistance.*;
import SCICT.NLP.Utility.WordContainer.*;
import SCICT.NLP.Utility.WordGenerator.*;
import SCICT.NLP.Utility.Parsers.*;
import SCICT.NLP.Morphology.Inflection.Conjugation.*;

/**
 Spell Checker Engine
 This Class find and rank respelling suggestions for a incorrectly spelled word
*/
public class SpellCheckerEngine
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Members

	private static final int MMeanTermLength = 30;

	//private Dictionary<string, int> Entry = new Dictionary<string, int>();
	private AutoCompleteWordContainerTree m_wordContainer;

	/**
	 Indicates the Maximum Edit Distance that searched for finding suggestions
	*/
	private int privateEditDistance;
	public final int getEditDistance()
	{
		return privateEditDistance;
	}
	private void setEditDistance(int value)
	{
		privateEditDistance = value;
	}
	/**
	 Number of Suggestions
	*/
	private int privateSuggestionCount;
	public final int getSuggestionCount()
	{
		return privateSuggestionCount;
	}
	private void setSuggestionCount(int value)
	{
		privateSuggestionCount = value;
	}
	/**
	 The absolute path of dictionary file.
	*/
	private String privateDictionaryFileName;
	public final String getDictionaryFileName()
	{
		return privateDictionaryFileName;
	}
	private void setDictionaryFileName(String value)
	{
		privateDictionaryFileName = value;
	}
	/**
	 The absolute path of stem's file.
	*/
	private String privateStemFileName;
	public final String getStemFileName()
	{
		return privateStemFileName;
	}
	private void setStemFileName(String value)
	{
		privateStemFileName = value;
	}

	/**
	 Number of dictionary words
	*/
	public final long getDicWordCount()
	{
		if (this.m_wordContainer == null)
		{
			return 0;
		}

		return this.m_wordContainer.getDictionaryWordsCount();
	}

	private java.util.HashMap<String, Integer> m_localFreqCatche = new java.util.HashMap<String, Integer>();

	protected java.util.HashMap<String, String> m_rankingDetail = new java.util.HashMap<String, String>();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Members

	//protected string currentWord   = null;
	protected boolean m_isInitialized = false;
	protected static final int MaxWordLengthToCheck = 11;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructor

	/**
	 Class Constructor
	
	@param config Spellchecker Configuration
	*/
	public SpellCheckerEngine(SpellCheckerConfig config)
	{
		Init(config);
	}

	/*
	///<summary>
	/// Class Constructor
	///</summary>
	///<param name="config">Spellchecker Configuration</param>
	///<param name="affixCheckForNewWords">Accept affix combination for further added words</param>
	public SpellCheckerEngine(SpellCheckerConfig config, bool affixCheckForNewWords)
	{
	    //Init(config);
	    Init(config, affixCheckForNewWords);
	}
	*/

	private void Init(SpellCheckerConfig config)
	{
		this.setEditDistance(config.EditDistance);
		this.setDictionaryFileName(config.DicPath);
		this.setSuggestionCount(config.SuggestionCount);
		this.setStemFileName(config.StemPath);

		WordContainerTreeConfig wordContainerConfig = new WordContainerTreeConfig();
		wordContainerConfig.DictionaryFileName = this.getDictionaryFileName();
		wordContainerConfig.SuggestionCount = this.getSuggestionCount();

		try
		{
			this.m_wordContainer = new AutoCompleteWordContainerTree(wordContainerConfig);
			if (!CheckDictionaryCorrectness(getDictionaryFileName(), m_wordContainer.getDictionaryWordsCount()))
			{
				throw new DictionaryProblemException();
			}
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}


		try
		{
			VerbInfoContainer verbInfoContainer = new VerbInfoContainer();
			verbInfoContainer.LoadStemFile(getStemFileName());

			Conjugator conjugator = new Conjugator(verbInfoContainer);
			int tempCode=ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.SADE) | 
					ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.PISHVANDI) |
					ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.INFINITIVE);
			ENUM_VERB_TYPE type=ENUM_VERB_TYPE.forValue(tempCode);
			String[] conjugations = conjugator.Conjugate(type);

			//conjugations = conjugations.Distinct("").toArray(new String[0]);

			for (String verb : conjugations)
			{
				this.m_wordContainer.AddWordBlind(verb, 0, PersianPOSTag.V);
			}

		}
		catch (RuntimeException ex)
		{
			throw ex;
		}

		this.m_isInitialized = true;
	}

	/*
	private void Init(SpellCheckerConfig config, bool affixCheckForNewWords)
	{
	    this.EditDistance = config.EditDistance;
	    this.DictionaryFileName = config.DicPath;
	    this.SuggestionCount = config.SuggestionCount;

	    WordContainerTreeConfig wordContainerConfig = new WordContainerTreeConfig();
	    wordContainerConfig.DictionaryFileName = this.DictionaryFileName;
	    wordContainerConfig.SuggestionCount = this.SuggestionCount;

	    this.m_autoCompleteWordContainer = new AutoCompleteWordContainerTree(wordContainerConfig, affixCheckForNewWords);

	    this.m_isInitialized = true;
	}
	*/

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ranking

	/*
	private Dictionary<string, int> CatchFrequency(string[] words)
	{

	    Dictionary<string, int> count = new Dictionary<string, int>();
	    int freq = 0;

	    foreach (string word in words)
	    {
	        if (!count.ContainsKey(word))
	        {
	            freq = WordFrequency(word);
	            count.Add(word, freq);
	        }
	    }

	    return count;
	}
	*/

	private LinqSimulationHashMap<String, Integer> CatchFrequency(String[] words)
	{
		LinqSimulationArrayList<String> userWords = new LinqSimulationArrayList<String>();
		LinqSimulationHashMap<String, Integer> count = new LinqSimulationHashMap<String, Integer>();
		int freq;

		for (String word : words)
		{
			if (!count.containsKey(word))
			{
				freq = WordFrequency(word);
				if (freq == 0)
				{
					userWords.add(word);
				}
				else
				{
					count.put(word, freq);
				}
			}
		}

		double val = count.size() > 0 ? count.Average(count.values()) + (count.Max(count.values()) - count.Average(count.values())) / 1.4 : 1;

		for (String str : userWords)
		{
			count.put(str, (int)val);
		}

		return count;
	}

	private java.util.HashMap<String, Integer> CatchFrequencyWithAffixConsideration(String[] words, Helper.RefObject<Long> avg)
	{
		avg.argvalue = 0l;

		java.util.HashMap<String, Integer> count = new java.util.HashMap<String, Integer>();
		int freq = 0;
		PersianPOSTag posTag = PersianPOSTag.forValue(0);

		PersianSuffixRecognizer suffixRecognizer = new PersianSuffixRecognizer(false, true);

		for (String word : words)
		{
			if (!count.containsKey(word))
			{
				Helper.RefObject<Integer> tempRef_freq = new Helper.RefObject<Integer>(freq);
				Helper.RefObject<PersianPOSTag> tempRef_posTag = new Helper.RefObject<PersianPOSTag>(posTag);
				boolean tempVar = IsRealWord(word, tempRef_freq, tempRef_posTag);
					freq = tempRef_freq.argvalue;
				posTag = tempRef_posTag.argvalue;
				if (tempVar)
				{
					avg.argvalue += freq;
					count.put(word, freq);
				}
				else
				{
					ReversePatternMatcherPatternInfo[] patternInfoArray = suffixRecognizer.MatchForSuffix(word);
					for (ReversePatternMatcherPatternInfo patternInfo : patternInfoArray)
					{
						Helper.RefObject<Integer> tempRef_freq2 = new Helper.RefObject<Integer>(freq);
						Helper.RefObject<PersianPOSTag> tempRef_posTag2 = new Helper.RefObject<PersianPOSTag>(posTag);
						boolean tempVar2 = IsRealWord(patternInfo.getBaseWord(), tempRef_freq2, tempRef_posTag2);
							freq = tempRef_freq2.argvalue;
						posTag = tempRef_posTag2.argvalue;
						if (tempVar2)
						{
							avg.argvalue += freq;
							count.put(word, freq);
							break;
						}
					}
				}
			}
		}

		avg.argvalue = avg.argvalue / words.length;

		return count;
	}

	private java.util.HashMap<String, Integer> CatchFrequencyMitigator(String[] words, Helper.RefObject<Long> localAvg, Helper.RefObject<Long> localSum)
	{

		LinqSimulationArrayList<String> userEnteredWords = new LinqSimulationArrayList<String>();

		int tmplocalAvg = 0, freq;

		for (String word : words)
		{
			if (!this.m_localFreqCatche.containsKey(word))
			{
				freq = WordFrequency(word);
				if (freq == 0)
				{
					userEnteredWords.add(word);
				}
				else
				{
					tmplocalAvg += freq;

					this.m_localFreqCatche.put(word, freq);
				}
			}
			else
			{
				tmplocalAvg += this.m_localFreqCatche.get(word);
			}
		}

		localSum.argvalue = (long) tmplocalAvg;
		tmplocalAvg = tmplocalAvg / (words.length - userEnteredWords.size());

		for (String word : userEnteredWords)
		{
			if (!this.m_localFreqCatche.containsKey(word))
			{
				localSum.argvalue += tmplocalAvg;
				this.m_localFreqCatche.put(word, tmplocalAvg);
			}
		}


		localAvg.argvalue = localSum.argvalue / (long)words.length;

		return this.m_localFreqCatche;
	}

	private void SortByCount(String[] words)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			java.util.HashMap<String, Integer> localCatch = CatchFrequency(words);

			String temp;
			for (int i = 0; i < words.length - 1; ++i)
			{
				for (int j = i + 1; j < words.length; ++j)
				{
					if (localCatch.get(words[i]) < localCatch.get(words[j]))
					{
						temp = words[i];
						words[i] = words[j];
						words[j] = temp;
					}
				}
			}
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	private void HybridSort(String baseWord, String[] words)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			//Dictionary<string, int> frequency = CatchFrequencyMitigator(words, out freqAvg, out freqSum);
			LinqSimulationHashMap<String, Integer> frequency = CatchFrequency(words);


			double freqSum;
			try
			{
				freqSum = frequency.size() > 0 ? frequency.Sum(frequency.values()) : 1;
			}
			catch (OutOfMemoryError e)
			{
				freqSum = 1;
			}

			double freqAvg;
			try
			{
				freqAvg = frequency.size() > 0 ? frequency.Average(frequency.values()): 0;
			}
			catch (OutOfMemoryError e2)
			{
				freqAvg = 0;
			}


			LinqSimulationHashMap<String, Double> similarity = CacheSimilarities(baseWord, words);
			double simSum;
			try
			{
				simSum = similarity.size() > 0 ? similarity.Sum(similarity.values()): 1;
			}
			catch (OutOfMemoryError e3)
			{
				simSum = 1;
			}

			double simAvg;
			try
			{
				simAvg = similarity.size() > 0 ? similarity.Average(similarity.values()) : 0;

			}
			catch (OutOfMemoryError e4)
			{
				simAvg = 0;
			}

			double uniOrderizerCo = freqAvg / simAvg;

			double similarityDeviation = StandardDeviation(similarity, simAvg);
			//double simEffectCo = 1 / (similarityDeviation);

			double freqDeviation = StandardDeviation(frequency, freqAvg,null);
			//double freqEffectCo = 1 / (freqDeviation / freqAvg);


			java.util.HashMap<String, Double> hybridMeasure = new java.util.HashMap<String, Double>();
			for (java.util.Map.Entry<String, Double> pair : similarity.entrySet())
			{
				hybridMeasure.put(pair.getKey(), (2 - similarityDeviation) * (pair.getValue()) + .8 * (frequency.get(pair.getKey()) / freqSum));

				//hybridMeasure.Add(pair.Key, (pair.Value / simSum) * (frequency[pair.Key] / freqSum));
				//hybridMeasure.Add(pair.Key, ((pair.Value * uniOrderizerCo) + localCatch[pair.Key]) * pair.Value);

				//hybridMeasure.Add(pair.Key, (pair.Value * uniOrderizerCo) * simEffectCo + frequency[pair.Key] * pair.Value * (1 - similarityDeviation));

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#region Add ranking detail

				if (!m_rankingDetail.containsKey(pair.getKey()))
				{
					m_rankingDetail.put(pair.getKey(), frequency.get(pair.getKey()).toString() + ", " + pair.getValue().toString());
				}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#endregion

			}

			String temp;
			for (int i = 0; i < words.length - 1; ++i)
			{
				for (int j = i + 1; j < words.length; ++j)
				{
					if (hybridMeasure.get(words[i]) < hybridMeasure.get(words[j]))
					{
						temp = words[i];
						words[i] = words[j];
						words[j] = temp;
					}
				}
			}
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	private static long CalcSessionAvgFreq(java.util.HashMap<String, Integer> freqDic)
	{
		long avg = 0;

		for (java.util.Map.Entry<String, Integer> pair : freqDic.entrySet())
		{
			avg += pair.getValue();
		}

		return avg / freqDic.size();
	}

	private static double StandardDeviation(LinqSimulationHashMap<String, Double> dictionary, double avg)
	{
		double deviation = 0;

		for (java.util.Map.Entry<String, Double> pair : dictionary.entrySet())
		{
			deviation += Math.pow((pair.getValue() - avg), 2);
		}

		deviation = Math.sqrt(deviation / dictionary.size());

		return deviation;
	}

	private static double StandardDeviation(LinqSimulationHashMap<String, Integer> dictionary, double avg,Object redundunt)
	{
		double deviation = 0;

		for (java.util.Map.Entry<String, Integer> pair : dictionary.entrySet())
		{
			deviation += Math.pow((pair.getValue() - avg), 2);
		}

		deviation = Math.sqrt(deviation / dictionary.size());

		return deviation;
	}

	private void SortBySimilarityMeasure(String baseWord, String[] words)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			//Stopwatch stopWatch = new Stopwatch();
			//stopWatch.Start();

			java.util.HashMap<String, Double> similarity = CacheSimilarities(baseWord, words);
			String temp;
			for (int i = 0; i < words.length - 1; ++i)
			{
				for (int j = i + 1; j < words.length; ++j)
				{
					if (similarity.get(words[i]) < similarity.get(words[j]))
					{
						temp = words[i];
						words[i] = words[j];
						words[j] = temp;
					}
				}
			}

			//stopWatch.Stop();
			//if (words.Length > 70)
			//{
			//    using (StreamWriter writer = new StreamWriter("c:/SpellCorrector/sort.log", true))
			//    {
			//        writer.WriteLine(DateTime.Now.ToString() + " - Current Word: " + this.currentWord + " - Word Count: " + words.Length + " - Sort Time: " + stopWatch.ElapsedMilliseconds.ToString());
			//    }
			//}
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}
	private static LinqSimulationHashMap<String, Double> CacheSimilarities(String baseWord, String[] words)
	{
		LinqSimulationHashMap<String, Double> similarity = new LinqSimulationHashMap<String, Double>();

		StringDistanceLayout editDistance = new StringDistanceLayout();


		for (String word : words)
		{
			if(word==null)
				continue;
			if (!similarity.containsKey(word))
			{
				double sim = editDistance.GetWordSimilarity(baseWord, word, StringDistanceAlgorithm.Kashefi);
				similarity.put(word, sim);
			}
		}

		return similarity;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	private String[] GetAllsuggestion(String word, int editdistance)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			if (!IsRealWord(word))
			{
				String[]tmp1=WordGenerator.GenerateRespelling(word, editdistance);
				String[]tmp2=ExtractRealWords(tmp1);
				suggestion.addAll(tmp2);
			}
			else
			{
				suggestion.addAll(ExtractRealWords(WordGenerator.GenerateRespelling(word, 1)));
				//suggestion.Add(word);
			}

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
	 Reconfigure the engine
	 
	 @param sc Spellchecker Configuration
	 @return 
	*/
	public final boolean Reconfigure(SpellCheckerConfig sc)
	{
		this.setEditDistance(sc.EditDistance);
		this.setSuggestionCount(sc.SuggestionCount);

		if (!this.getDictionaryFileName().equals(sc.DicPath))
		{
			this.setDictionaryFileName(sc.DicPath);

			this.m_wordContainer = null;

			WordContainerTreeConfig actc = new WordContainerTreeConfig();
			actc.DictionaryFileName = this.getDictionaryFileName();
			actc.SuggestionCount = this.getSuggestionCount();

			try
			{
				this.m_wordContainer = new AutoCompleteWordContainerTree(actc);
			}
			catch (RuntimeException ex)
			{
				throw ex;
			}
		}

		try
		{
			VerbInfoContainer verbInfoContainer = new VerbInfoContainer();
			verbInfoContainer.LoadStemFile(getStemFileName());

			Conjugator conjugator = new Conjugator(verbInfoContainer);
			int code=(ENUM_VERB_TYPE.SADE.getValue() | 
					ENUM_VERB_TYPE.PISHVANDI.getValue() | ENUM_VERB_TYPE.INFINITIVE.getValue());
			String[] conjugations = conjugator.Conjugate(ENUM_VERB_TYPE.forValue(code));

			for (String verb : conjugations)
			{
				this.m_wordContainer.AddWordBlind(verb, 0, PersianPOSTag.V);
			}

		}
		catch (RuntimeException ex)
		{
			throw ex;
		}

		return true;
	}

	/** 
	 Append another dictionary
	 
	 @param fileName dictionary file name
	@return True if dictionary is successfully added, otherwise False
	*/
	public final boolean AppendDictionary(String fileName)
	{
		try
		{
			if (!this.m_isInitialized)
			{
				throw new RuntimeException("Speller Engine Must be Initialized!");
			}

			int extractedWordCount = this.m_wordContainer.AppendDictionary(fileName);
			return CheckDictionaryCorrectness(fileName, extractedWordCount);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/**
	 Check Dictionary Correctness
	
	@param fileName File name
	@param wordCounts Extracted words count
	@return 
	*/
	public static boolean CheckDictionaryCorrectness(String fileName, long wordCounts)
	{
		long numberOfExpectedWords = (new java.io.File(fileName)).length() / MMeanTermLength;
		if (wordCounts < 0.8 * numberOfExpectedWords)
		{
			return false;
		}

		return true;
	}

	/**
	 Remove all words from dictionary
	
	@exception Exception
	*/
	public final void ClearDictionary()
	{
		try
		{
			if (!this.m_isInitialized)
			{
				throw new RuntimeException("Speller Engine Must be Initialized!");
			}

			setDictionaryFileName("");

			this.m_wordContainer.Clear();
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Add Word to Dictionary

	/** 
	 Add a correct word to dictionary
	 
	 @param word New word
	 @param freq Usage frequency of word
	@return True if word is successfully added, otherwise False
	*/
	public final boolean AddToDictionary(String word)
	{
		try
		{
			if (!this.m_isInitialized)
			{
				throw new RuntimeException("Speller Engine Must be Initialized!");
			}

			return AddToDictionary(word, 0, PersianPOSTag.UserPOS);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Add a correct word to dictionary
	 
	 @param word New word
	 @param fileName File name
	@return True if word is successfully added, otherwise False
	*/
	public boolean AddToDictionary(String word, String fileName)
	{
		try
		{
			if (!this.m_isInitialized)
			{
				throw new RuntimeException("Speller Engine Must be Initialized!");
			}

			return AddToDictionary(word, 0, PersianPOSTag.UserPOS, fileName);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Add a correct word to dictionary
	 
	 @param word New word
	 @param freq Usage frequency of word
	 @param posTag POS tag of word
	@return True if word is successfully added, otherwise False
	*/
	public final boolean AddToDictionary(String word, int freq, PersianPOSTag posTag)
	{
		try
		{
			if (!this.m_isInitialized)
			{
				throw new RuntimeException("Speller Engine Must be Initialized!");
			}

			return this.m_wordContainer.AddWord(word, freq, posTag);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Add a correct word to dictionary
	 
	 @param word New word
	 @param freq Usage frequency of word
	 @param posTag POS tag of word
	 @param fileName File name
	@return True if word is successfully added, otherwise False
	*/
	public final boolean AddToDictionary(String word, int freq, PersianPOSTag posTag, String fileName)
	{
		try
		{
			if (!this.m_isInitialized)
			{
				throw new RuntimeException("Speller Engine Must be Initialized!");
			}

			return this.m_wordContainer.AddWord(word, freq, posTag, fileName);
		}
		catch (RuntimeException ex)
		{
			return false;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Remove a word from dictionary
	 
	 @param word input word
	*/
	public final void RemoveFromDictionary(String word)
	{
		try
		{
			if (!this.m_isInitialized)
			{
				throw new RuntimeException("Speller Engine Must be Initialized!");
			}

			this.m_wordContainer.RemoveWord(word);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Check if the word is correct (exists in dictionary)
	 
	 @param word Word
	 @return True if word is correct, Otherwise False
	*/
	public boolean IsRealWord(String word)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			if (this.m_wordContainer.Contain(word))
			{
				return true;
			}

			return false;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Check if the word is correct (exists in dictionary)
	 
	 @param word Word
	 @param freq Frequency
	 @param posTag POS tag
	 @return True if word is correct, Otherwise False
	@exception Exception
	*/
	public boolean IsRealWord(String word, Helper.RefObject<Integer> freq, Helper.RefObject<PersianPOSTag> posTag)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{

			//if (this.m_autocompletewordcontainer.contain(word, out isbaseword))
			//{
			//    return true;
			//}

			if (this.m_wordContainer.Contain(word, freq, posTag))
			{
				return true;
			}


			return false;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Get All dictionary's words
	 
	 @return 
	*/
	public final String[] GetAllWords()
	{
		return this.m_wordContainer.GetAllWords();
	}

	/** 
	 Complete the rest of incomplete word
	 
	 @param subWord Incomplete word
	 @return Completed words start with incomplete word
	*/
	public final String[] CompleteWord(String subWord)
	{
		return this.m_wordContainer.Complete(subWord);
	}

	/** 
	 Complete the rest of incomplete word
	 
	 @param subWord Incomplete word
	 @param count Number of returned suggestions
	 @return Completed words start with incomplete word
	*/
	public final String[] CompleteWord(String subWord, int count)
	{
		return this.m_wordContainer.Complete(subWord, count);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ranking

	/** 
	 Return most frequent word from list of words
	 
	 @param words list of words
	 @return Most frequent word
	*/
	public final String SortSuggestions(String[] words)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			if (words.length <= 0)
			{
				return "";
			}
			if (words.length == 1)
			{
				return words[0];
			}

			SortByCount(words);

			return words[0];
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Sort a list of words ordered by usage frequency
	 
	 @param words list of words
	 @param suggestionCount Number of returned suggestions
	 @return Sorted list
	*/
	public final String[] SortSuggestions(String[] words, int suggestionCount)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		if (words == null)
		{
			return new String[0];
		}
		if (words.length <= 0)
		{
			return new String[0];
		}
		if (words.length == 1)
		{
			return words;
		}

		String[] suggestions = new String[Math.min(suggestionCount, words.length)];

		try
		{
			SortByCount(words);

			for (int i = 0; i < suggestions.length; ++i)
			{
				suggestions[i] = words[i];
			}

			return suggestions;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 return most string similar word of list with givven word
	 
	 @param baseWord Base word used to compare (Usually incorrect word)
	 @param words List of words (Usually list of respelling suggestions)
	 @return most similar word
	*/
	public final String SortSuggestions(String baseWord, String[] words)
	{
		if (baseWord == null)
		{
			throw new IllegalArgumentException("baseWord");
		}
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			if (words.length <= 0)
			{
				return "";
			}
			if (words.length == 1)
			{
				return words[0];
			}

			//SortBySimilarityMeasure(baseWord, words);
			HybridSort(baseWord, words);

			return words[0];
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 return most string similar word of list with givven word
	 
	 @param baseWord Base word used to compare (Usually incorrect word)
	 @param words List of words (Usually list of respelling suggestions)
	 @param freq Word's frequency
	 @return most similar word
	*/
	protected final String SortSuggestions(String baseWord, String[] words, java.util.HashMap<String, Integer> freq)
	{
		if (baseWord == null)
		{
			throw new IllegalArgumentException("baseWord");
		}
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			if (words.length <= 0)
			{
				return "";
			}
			if (words.length == 1)
			{
				return words[0];
			}

			this.m_localFreqCatche = freq;
			//SortBySimilarityMeasure(baseWord, words);
			HybridSort(baseWord, words);


			return words[0];
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Sort a list of words ordered by string similarity
	 
	 @param baseWord Base word used to compare (Usually incorrect word)
	 @param words List of words (Usually list of respelling suggestions)
	 @param suggestionCount Number of returned suggestions
	 @return Sorted list
	*/
	public final String[] SortSuggestions(String baseWord, String[] words, int suggestionCount)
	{

		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		if (words == null)
		{
			return new String[0];
		}
		if (words.length <= 0)
		{
			return new String[0];
		}
		//if (words.Length == 1)
		//{
		//    return words;
		//}

		int sugLength = suggestionCount;

		if (words.length < suggestionCount)
		{
			sugLength = words.length;
		}

		if (sugLength <= 0)
		{
			sugLength = 1;
		}

		String[] suggestions = new String[sugLength];

		try
		{
			//SortBySimilarityMeasure(baseWord, words);
			HybridSort(baseWord, words);

			for (int i = 0; i < Math.min(suggestions.length, words.length); ++i)
			{
				suggestions[i] = words[i];
			}

			return suggestions;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Sort a list of words ordered by string similarity
	 
	 @param baseWord Base word used to compare (Usually incorrect word)
	 @param words List of words (Usually list of respelling suggestions)
	 @param freq Word frequency
	 @param suggestionCount Number of returned suggestions
	 @return Sorted list
	*/
	protected final String[] SortSuggestions(String baseWord, String[] words, java.util.HashMap<String, Integer> freq, int suggestionCount)
	{

		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		if (words == null)
		{
			return new String[0];
		}
		if (words.length <= 0)
		{
			return new String[0];
		}
		if (words.length == 1)
		{
			return words;
		}

		String[] suggestions = new String[Math.min(suggestionCount, words.length)];

		try
		{
			this.m_localFreqCatche = freq;

			HybridSort(baseWord, words);

			for (int i = 0; i < suggestions.length; ++i)
			{
				suggestions[i] = words[i];
			}

			return suggestions;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Return dictionary (correct) words
	 
	 @param words List of words
	 @return List of dictionary (correct) words
	*/
	public final String[] ExtractRealWords(String[] words)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();
			for (String word : words)
			{
				
				if (this.IsRealWord(word) || word.length() == 0)
				{
					suggestion.add(word);
					//if (!suggestion.Contains(word))
					//{
					//    suggestion.Add(word);
					//}
				}
			}

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return suggestion.Distinct("").toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Return dictionary words including POS
	 
	 @param words List of words
	 @param freqDic Frequency
	 @param posDic POS tag
	 @return List of dictionary words
	*/
	public final String[] ExtractRealWords(String[] words, Helper.RefObject<java.util.HashMap<String, Integer>> freqDic, Helper.RefObject<java.util.HashMap<String, PersianPOSTag>> posDic)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			posDic.argvalue = new java.util.HashMap<String, PersianPOSTag>();
			freqDic.argvalue = new java.util.HashMap<String, Integer>();
			LinqSimulationArrayList<String> suggestions = new LinqSimulationArrayList<String>();

			PersianPOSTag posTag = PersianPOSTag.forValue(0);
			int freq = 0;
			for (String word : words)
			{
				Helper.RefObject<Integer> tempRef_freq = new Helper.RefObject<Integer>(freq);
				Helper.RefObject<PersianPOSTag> tempRef_posTag = new Helper.RefObject<PersianPOSTag>(posTag);
				boolean tempVar = this.IsRealWord(word, tempRef_freq, tempRef_posTag) || word.length() == 0;
					freq = tempRef_freq.argvalue;
				posTag = tempRef_posTag.argvalue;
				if (tempVar)
				{
					if (!suggestions.contains(word))
					{
						suggestions.add(word);
						posDic.argvalue.put(word, posTag);
						freqDic.argvalue.put(word, freq);
					}
				}
			}

			return suggestions.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Get all correct respelling suggestions of an incorrect word including their POS tag
	 
	 @param word (Incorrect) Word
	 @param freqDic Frequency
	 @param posDic POS Tag
	 @return List of correct respellings
	*/
	public final String[] SpellingSuggestions(String word, Helper.RefObject<java.util.HashMap<String, Integer>> freqDic, Helper.RefObject<java.util.HashMap<String, PersianPOSTag>> posDic)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		try
		{
			freqDic.argvalue = new java.util.HashMap<String, Integer>();
			posDic.argvalue = new java.util.HashMap<String, PersianPOSTag>();

			if (!IsRealWord(word))
			{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
				String tempSugestion[]=ExtractRealWords(WordGenerator.GenerateRespelling(word, this.getEditDistance()), freqDic, posDic);
				return ArraysUtility.Distinct(tempSugestion);
			}

			return new String[0];
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Get a list of sorted (ranked) correct respelling suggestions of an incorrect word by similarity
	 
	 @param word Wrong Word
	 @param suggestionCount Number of returned suggestions
	 @return Sorted list of correct respelling suggestions
	*/
	public String[] RankedSpellingSuggestions(String word, int suggestionCount)
	{
		/**omid: recheck
		*/

		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

		try
		{
			/**Double Check
			*/
			int effectiveEditDistance = word.length() > MaxWordLengthToCheck? 1 :this.getEditDistance();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Homophone respelling suggestoins

			if (word.length() > MaxWordLengthToCheck)
			{
				WordGenerator.SetAccuracy(0.1);
			}
			else
			{
				WordGenerator.SetAccuracy(0.5);
			}

			suggestion.addAll(ExtractRealWords(WordGenerator.GenerateHomophoneWords(word)));

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

			suggestion.addAll(SortSuggestions(word, GetAllsuggestion(word, effectiveEditDistance), Math.abs(suggestionCount - suggestion.size())));

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return suggestion.Distinct("").toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Get a list of correct respelling and homophone suggestions of an incorrect word by similarity
	 
	 @param word Wrong Word
	 @return Sorted list of correct respelling suggestions
	*/
	public String[] SpellingSuggestions(String word)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

		try
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Homophone respelling suggestoins

			if (word.length() > MaxWordLengthToCheck)
			{
				WordGenerator.SetAccuracy(0.1);
			}
			else
			{
				WordGenerator.SetAccuracy(0.5);
			}

			suggestion.addAll(ExtractRealWords(WordGenerator.GenerateHomophoneWords(word)));

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

			int effectiveEditDistance = word.length() > MaxWordLengthToCheck ? 1 :this.getEditDistance();

			for (int i = 1; i <= effectiveEditDistance; ++i)
			{
				suggestion.addAll(GetAllsuggestion(word, i));

				//if (suggestion.Count > SuggestionCount / 2)
				if (suggestion.size() > 0)
				{
					break;
				}
			}

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return suggestion.Distinct("").toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Get a list of correct respelling and homophone suggestions of an incorrect word by similarity
	 
	 @param word Wrong Word
	 @return Sorted list of correct respelling suggestions
	*/
	public String[] SpellingSuggestions2(String word)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

		try
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Homophone respelling suggestoins

			if (word.length() > MaxWordLengthToCheck)
			{
				WordGenerator.SetAccuracy(0.1);
			}
			else
			{
				WordGenerator.SetAccuracy(0.5);
			}

			suggestion.addAll(ExtractRealWords(WordGenerator.GenerateHomophoneWords(word)));

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

			int effectiveEditDistance = word.length() > MaxWordLengthToCheck ? 1 :this.getEditDistance();

			if (word.length() >= 6)
			{
				String initialSubSequence = word.substring(0, 5);
				String complementarySubSequence = word.substring(5, word.length());

				for (int i = 1; i <= effectiveEditDistance; ++i)
				{
					String[] initialPartVariation = WordGenerator.GenerateRespelling(initialSubSequence, i);
					String[] secondSeq = new String[] {complementarySubSequence};
					String[] reSpellingVariation = ArraysUtility.toArray(MultiplyStrings(initialPartVariation, secondSeq));

					suggestion.addAll(ExtractRealWords(reSpellingVariation));

					if (suggestion.size() > 0)
					{
						break;
					}
				}

				suggestion.addAll(CompleteWord(initialSubSequence));
			}
			else
			{
				for (int i = 1; i <= effectiveEditDistance; ++i)
				{

					suggestion.addAll(GetAllsuggestion(word, i));

					//if (suggestion.Count > SuggestionCount / 2)
					if (suggestion.size() > 0)
					{
						break;
					}
				}
			}

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return suggestion.Distinct("").toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Get a list of correct respelling and homophone suggestions of an incorrect word by similarity
	 
	 @param word Wrong Word
	 @return Sorted list of correct respelling suggestions
	*/
	public String[] SpellingSuggestions3(String word, int editDistance)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

		try
		{
			if (editDistance == 1)
			{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#region Homophone respelling suggestoins

				if (word.length() > MaxWordLengthToCheck)
				{
					WordGenerator.SetAccuracy(0.1);
				}
				else
				{
					WordGenerator.SetAccuracy(0.5);
				}

				suggestion.addAll(ExtractRealWords(WordGenerator.GenerateHomophoneWords(word)));

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#endregion
			}

			suggestion.addAll(WordGenerator.GenerateRespelling(word, editDistance));

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return suggestion.Distinct("").toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/** 
	 Returns a sequence of strings gained from concatenating all the strings 
	 in <paramref name="first"/> with all the strings in <paramref name="second"/>.
	 
	 @param first The sequence of strings to form the left-hand-side of the concatenations.
	 @param second The sequence of strings to form the right-hand-side of the concatenations.
	*/
	private static Iterable<String> MultiplyStrings(Iterable<String> first, Iterable<String> second)
	{
		LinqSimulationArrayList<String> result = new LinqSimulationArrayList<String>();

		for (String str1 : first)
		{
			for (String str2 : second)
			{
				result.add(str1 + str2);
			}
		}

		return result;
	}
	private static Iterable<String> MultiplyStrings(String[] first, String[] second)
	{
		LinqSimulationArrayList<String> result = new LinqSimulationArrayList<String>();

		for (String str1 : first)
		{
			for (String str2 : second)
			{
				result.add(str1 + str2);
			}
		}

		return result;
	}


	/** 
	 Get a list of correct respelling and homophone suggestions of an incorrect word by similarity
	 
	 @param word Wrong Word
	 @param editDistance Edit distance
	 @return Sorted list of correct respelling suggestions
	*/
	public String[] SpellingSuggestions(String word, int editDistance)
	{
		if (!this.m_isInitialized)
		{
			throw new RuntimeException("Speller Engine Must be Initialized!");
		}

		LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

		try
		{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Homophone respelling suggestoins

			WordGenerator.SetAccuracy(0.1);
			suggestion.addAll(ExtractRealWords(WordGenerator.GenerateHomophoneWords(word)));

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

			suggestion.addAll(GetAllsuggestion(word, editDistance));

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return suggestion.Distinct("").toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/**
	 Return word's frequency
	
	@param word word
	@return 
	*/
	public final int WordFrequency(String word)
	{
		return m_wordContainer.WordFrequency(word);
	}

	/**
	 Return word's POS tag
	
	@param word word
	@return POS tag
	*/
	public final PersianPOSTag WordPOS(String word)
	{
		return m_wordContainer.WordPOS(word);
	}

	/**
	 Save Loaded Dictionary to File
	
	@param fileName File name
	*/
	public final void SaveDistionaryToFile(String fileName)
	{
		m_wordContainer.SaveDictionaryToFile(fileName);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	protected final double CalcAvgFreq()
	{
		if (m_wordContainer != null)
		{
			return (double) m_wordContainer.getFreqSummation() / m_wordContainer.getDictionaryWordsCount();
		}

		return 0;
	}

	protected final double CalcAvgFreq(Double[] freqs)
	{
		if (freqs.length == 1)
		{
			return freqs[0];
		}

		double mult;
		LinqSimulationArrayList<Double> list = new LinqSimulationArrayList<Double>();

		for (int i = 0; i < freqs.length; i += 2)
		{
			if (i < freqs.length - 1)
			{
				mult = Math.sqrt(freqs[i] * freqs[i + 1]);
				if (Double.isNaN(mult))
				{
					double[]tmpArray=new double[] {freqs[i], freqs[i + 1]};
					mult=ArraysUtility.Average(tmpArray);
				}
				list.add(mult);
			}
			else
			{
				list.add(freqs[i]);
			}
		}

		return CalcAvgFreq(list.toArray(new Double[0]));
	}

	protected final double CalcAvgFreq(String[] words)
	{

		//List<double> list = new List<double>();
		//foreach(string str in words)
		//{
		//    list.Add(m_wordContainer.WordFrequency(str));
		//}

		//return CalcAvgFreq(list.ToArray());

		long avg = 0;
		Helper.RefObject<Long> tempRef_avg = new Helper.RefObject<Long>(avg);
		java.util.HashMap<String, Integer> frq = CatchFrequencyWithAffixConsideration(words, tempRef_avg);
		avg = tempRef_avg.argvalue;

		return avg;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}