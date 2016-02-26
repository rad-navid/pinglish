package SCICT.NLP.Utility.LanguageModel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import Helper.ArraysUtility;
import SCICT.NLP.Morphology.Inflection.InflectionAnalyser;
import SCICT.NLP.Morphology.Lemmatization.PersianSuffixRecognizer;
import SCICT.NLP.Persian.Constants.PersianPOSTag;
import SCICT.NLP.Persian.Constants.PersianSuffixesCategory;
import SCICT.NLP.Utility.StringUtil;
import SCICT.NLP.Utility.Parsers.ReversePatternMatcherPatternInfo;
import SCICT.NLP.Utility.WordContainer.WordFreqPOSContainerTree;

/**
 Extract a POS tagged dictionary from a text corpus
*/
public class POSTaggedDictionaryExtractor
{

	private FileWriter m_streamWriter;

	private final WordFreqPOSContainerTree m_wordContainerExternal = new WordFreqPOSContainerTree();

	private final java.util.HashMap<String, Integer> m_wordList = new java.util.HashMap<String, Integer>();

	private final PersianSuffixRecognizer m_suffixRecognizer = new PersianSuffixRecognizer(false, true);

	private final java.util.HashMap<String, FreqPOSPair> m_finalList = new java.util.HashMap<String, FreqPOSPair>();

	private double m_progressPercent = 0.0;

	/** 
	 Gets the percentage of progress.
	 
	 <value>The progress percent.</value>
	*/
	public final double getProgressPercent()
	{
		return m_progressPercent;
	}
	public final void setProgressPercent(double value)
	{
		if (value > 1.0)
		{
			value = 1.0;
		}
		if (value < 0)
		{
			value = 0.0;
		}
		m_progressPercent = value;
	}
	private boolean Init(String fileName)
	{
		try
		{
			m_streamWriter = new FileWriter(fileName);
			return true;
		}
		catch (RuntimeException e)
		{
			return false;
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
	}

	/**
	 Add a term
	
	@param term Term
	*/
	public final void AddTerm(String term)
	{
		if (m_wordList.containsKey(term))
		{
			Integer key=m_wordList.get(term);
			key=new Integer(key.intValue()+1);
		}
		else
		{
			m_wordList.put(term, 1);
		}
	}

	/**
	 Extract POS tagged dictionary to a file
	
	@param fileName File name
	@return Tru on success
	*/
	public final boolean ExtractPOSTaggedDictionary(String fileName)
	{
		PersianPOSTag pos = PersianPOSTag.forValue(0);
		int totalCount = m_wordList.size();
		int currentStep = 0;
		double remainingPregress = (1.0 - getProgressPercent()) / 0.95;

		double denom = (2.0 / ((totalCount) * (totalCount + 1))) * remainingPregress;

		for (java.util.Map.Entry<String, Integer> pair : m_wordList.entrySet())
		{
			setProgressPercent(getProgressPercent() + ((double)currentStep * denom));
			currentStep++;

			boolean curWordAdded = false;
			String word = pair.getKey();

			Helper.RefObject<PersianPOSTag> tempRef_pos = new Helper.RefObject<PersianPOSTag>(pos);
			boolean tempVar = !m_wordContainerExternal.Contain(word, tempRef_pos,null);
				pos = tempRef_pos.argvalue;
			if (tempVar) //external dictionary does not contains the word
			{
				ReversePatternMatcherPatternInfo[] suffixPatternArray = m_suffixRecognizer.MatchForSuffix(word);
				if (suffixPatternArray.length > 0)
				{
					for (ReversePatternMatcherPatternInfo suffixPattern : suffixPatternArray)
					{
						String stem = suffixPattern.getBaseWord();

						Helper.RefObject<PersianPOSTag> tempRef_pos2 = new Helper.RefObject<PersianPOSTag>(pos);
						boolean tempVar2 = m_wordContainerExternal.Contain(stem, tempRef_pos2,null);
							pos = tempRef_pos2.argvalue;
						if (tempVar2) //external dictionary contains the stem
						{
							curWordAdded = true;
							AddWordToFinalList(stem, m_wordList.get(word), pos);
							break;
						}
						else if (m_wordList.containsKey(stem))
						{
							curWordAdded = true;
							AddToDictionary(stem, word);
							break;
						}
					}
					if (!curWordAdded)
					{
						AddToDictionary(word, word);
					}
				}
				else
				{
					AddToDictionary(word, word);
				}
			}
			else
			{
				//if external dictionary contains the word, add it to file
				AddWordToFinalList(word, m_wordList.get(word), pos);
			}
		}

		return DumpFinalList(fileName);
	}

	/**
	 Add a text corpus
	
	@param text Text string
	*/
	public final void AddPlainText(String text)
	{
		setProgressPercent(getProgressPercent() + 0.05);
		String[] words = ArraysUtility.Distinct(StringUtil.ExtractPersianWordsStandardized(text));
		setProgressPercent(getProgressPercent() + 0.1);

		int length = words.length;
		double denom = (2.0 / ((length) * (length + 1))) * 0.15;

		for (int i = 0; i < length; i++)
		{
			AddTerm(words[i]);
			setProgressPercent(getProgressPercent() + ((double)i * denom));
		}
	}

	/**
	 Append exteranl POS tagged dictionary
	
	@param fileName File name
	*/
	public final void AppendExternalPOSTaggedDictionary(String fileName)
	{
		setProgressPercent(getProgressPercent() + 0.15);
		m_wordContainerExternal.AppendDictionary(fileName);
		setProgressPercent(getProgressPercent() + 0.15);
	}

	/** 
	 Add a correct word to dictionary
	 
	 @param userSelectedWord Form of word which user select to add to dictionary
	 @param originalWord Original word without lemmatization
	@return True if word is successfully added, otherwise False
	*/
	private void AddToDictionary(String userSelectedWord, String originalWord)
	{
		String suffix = originalWord.substring(0, 0) + originalWord.substring(0 + userSelectedWord.length());

		PersianPOSTag extractedPOSTag = PersianPOSTag.UserPOS;

		if (suffix.length() > 0)
		{
			PersianSuffixesCategory suffixCategory = m_suffixRecognizer.SuffixCategory(suffix);
			extractedPOSTag = InflectionAnalyser.AcceptingPOS(suffixCategory);

			extractedPOSTag = extractedPOSTag.Set(PersianPOSTag.UserPOS);
		}

		AddWordToFinalList(userSelectedWord, m_wordList.get(userSelectedWord), extractedPOSTag);
	}

	private void AddWordToFinalList(String word, int freq, PersianPOSTag pos)
	{
		FreqPOSPair pair = new FreqPOSPair();

		if (m_finalList.containsKey(word))
		{
			pair.freq = m_finalList.get(word).freq + 1;
			pair.pos = m_finalList.get(word).pos.Set(pos);

			m_finalList.put(word, pair.clone());
		}
		else
		{
			pair.freq = freq;
			pair.pos = pos;

			m_finalList.put(word, pair.clone());
		}
	}

	private boolean DumpFinalList(String fileName)
	{
		if (!Init(fileName))
		{
			return false;
		}

		try
		{
			int totalCount = m_wordList.size();
			int currentStep = 0;
			double remainingPregress = (1.0 - getProgressPercent());
			double denom = (2.0 / ((totalCount) * (totalCount + 1))) * remainingPregress;

			Set<String>Keys=m_finalList.keySet();
			for (String term : Keys)
			{
				setProgressPercent(getProgressPercent() + ((double)currentStep * denom));
				currentStep++;

				m_streamWriter.write(term+ "\t" + m_finalList.get(term).freq + "\t" + m_finalList.get(term).pos+"\n");
			}

			m_streamWriter.close();
			setProgressPercent(1.0);

			return true;
		}
		catch (RuntimeException e)
		{
			setProgressPercent(1.0);
			return false;
		} catch (IOException e) {
			
			setProgressPercent(1.0);
			e.printStackTrace();
		}
		return false;
	}
}