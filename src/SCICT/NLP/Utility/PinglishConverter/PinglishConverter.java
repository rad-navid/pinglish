package SCICT.NLP.Utility.PinglishConverter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Helper.ArraysUtility;
import Helper.LinqSimulationArrayList;
import SCICT.NLP.Persian.Constants.PseudoSpace;
import SCICT.NLP.Persian.Constants.QuotationMark;
import SCICT.NLP.TextProofing.SpellChecker.SpellCheckerEngine;
import SCICT.NLP.Utility.StringUtil;

// Virastyar
// http://www.virastyar.ir
// Copyright (C) 2011 Supreme Council for Information and Communication Technology (SCICT) of Iran
// 
// This file is part of Virastyar.
// 
// Virastyar is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Virastyar is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Virastyar.  If not, see <http://www.gnu.org/licenses/>.
// 
// Additional permission under GNU GPL version 3 section 7
// The sole exception to the license's terms and requierments might be the
// integration of Virastyar with Microsoft Word (any version) as an add-in.




/** 
 Base class for Pinglish conversion. Provides methods to generate or convert possible words for a 
 given Pinglish string.
*/
public class PinglishConverter implements IPinglishLearner,Externalizable
{
	private java.util.HashMap<String, PreprocessElementInfo> m_preprocessReplacements = new java.util.HashMap<String, PreprocessElementInfo>();
	private java.util.HashMap<Character, String[]> m_bigLettersAtBeginOrEnd = new java.util.HashMap<Character, String[]>();

	private java.util.HashMap<Character, String[]> m_oneLetterWords = new java.util.HashMap<Character, String[]>();

	private PinglishMapping m_converter = new PinglishMapping();
	private SpellCheckerEngine m_speller;

	private boolean m_isSpellerEngineSet;

	private static PinglishStringEqualityComparer s_pinglishStringComparer = new PinglishStringEqualityComparer();


	/**
	 
	*/
	public static CharacterMapping[] MultipleValueCharMap;

	/** 
	 Gets a value indicating whether the speller engine is set for this instance.
	 
	 <value>
	 	<c>true</c> if the speller engine is set for this instance; otherwise, <c>false</c>.
	 </value>
	*/
	public final boolean getIsSpellerEngineSet()
	{
		return m_isSpellerEngineSet;
	}

	/** 
	 Gets the dataset.
	 
	 <value>The dataset.</value>
	*/
	public final Iterable<PinglishString> getDataset()
	{
		return m_converter.getDataSet();
	}

	static
	{

		CharacterMapping attr_b = new CharacterMapping('b', new CharacterMappingInfo[] {new CharacterMappingInfo("ب", 0), new CharacterMappingInfo("ب", 'b', TokenPosition.MiddleOfWord, 0), new CharacterMappingInfo("بع", 'b', TokenPosition.EndOfWord, 0)});

		CharacterMapping attr_d = new CharacterMapping('d', new CharacterMappingInfo[] {new CharacterMappingInfo("د", 0), new CharacterMappingInfo("د", 'd', TokenPosition.MiddleOfWord, 0)});

		CharacterMapping attr_r = new CharacterMapping('r', new CharacterMappingInfo[] {new CharacterMappingInfo("ر", 0), new CharacterMappingInfo("ر", 'r', TokenPosition.MiddleOfWord, 0), new CharacterMappingInfo("", 2)});

		CharacterMapping attr_f = new CharacterMapping('f', new CharacterMappingInfo[] {new CharacterMappingInfo("ف", 0), new CharacterMappingInfo("ف", 'f', TokenPosition.MiddleOfWord, 0)});

		CharacterMapping attr_l = new CharacterMapping('l', new CharacterMappingInfo[] {new CharacterMappingInfo("ل", 0), new CharacterMappingInfo("ل", 'l', TokenPosition.MiddleOfWord, 0)});

		CharacterMapping attr_m = new CharacterMapping('m', new CharacterMappingInfo[] {new CharacterMappingInfo("م", 0), new CharacterMappingInfo("م", 'm', TokenPosition.MiddleOfWord, 0), new CharacterMappingInfo("مع", TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 1)});

		CharacterMapping attr_n = new CharacterMapping('n', new CharacterMappingInfo[] {new CharacterMappingInfo("ن", 0), new CharacterMappingInfo("", TokenPosition.EndOfWord, 2), new CharacterMappingInfo("ن", 'n', TokenPosition.MiddleOfWord, 0)});

		CharacterMapping attr_v = new CharacterMapping('v', new CharacterMappingInfo[] {new CharacterMappingInfo("و", 0), new CharacterMappingInfo("و", 'v', TokenPosition.MiddleOfWord, 0)});

		CharacterMapping attr_w = new CharacterMapping('w', new CharacterMappingInfo[] {new CharacterMappingInfo("و", 0), new CharacterMappingInfo("و", 'w', TokenPosition.MiddleOfWord, 0)});

		CharacterMapping attr_y = new CharacterMapping('y', new CharacterMappingInfo[] {new CharacterMappingInfo("ی", 0), new CharacterMappingInfo("ی", 'y', TokenPosition.MiddleOfWord, 0)});

		CharacterMapping attr_t = new CharacterMapping('t', new CharacterMappingInfo[] {new CharacterMappingInfo("ت", 0), new CharacterMappingInfo("ط", 1), new CharacterMappingInfo("طع", TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 2), new CharacterMappingInfo("تع", TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 2), new CharacterMappingInfo("ت", 't'), new CharacterMappingInfo("ط", 't'), new CharacterMappingInfo("", TokenPosition.MiddleOfWord, 3)});

		//CharacterMapping attr_T = new CharacterMapping('T', true, new CharacterMappingInfo[] 
		//        { 
		//            new CharacterMappingInfo("ت", 1),
		//            new CharacterMappingInfo("ط", 2),
		//            new CharacterMappingInfo("تی", LetterPosition.EndOfWord, 0),

		//            new CharacterMappingInfo("ت", 'T'),
		//            new CharacterMappingInfo("ط", 'T'),
		//        });

		CharacterMapping attr_s = new CharacterMapping('s', new CharacterMappingInfo[] {new CharacterMappingInfo("س", 0), new CharacterMappingInfo("ص", 1), new CharacterMappingInfo("ث", 2), new CharacterMappingInfo("ش", 'h'), new CharacterMappingInfo("س", 's'), new CharacterMappingInfo("ص", 's'), new CharacterMappingInfo("ث", 's'), new CharacterMappingInfo("", 'h', TokenPosition.MiddleOfWord, 3), new CharacterMappingInfo("", TokenPosition.MiddleOfWord, 3)});

		CharacterMapping attr_c = new CharacterMapping('c', new CharacterMappingInfo[] {new CharacterMappingInfo("س", 0), new CharacterMappingInfo("ص", 2), new CharacterMappingInfo("ث", 3), new CharacterMappingInfo("ک", 4), new CharacterMappingInfo("چ", 'h'), new CharacterMappingInfo("سی", TokenPosition.EndOfWord, 1), new CharacterMappingInfo("س", 'c'), new CharacterMappingInfo("ص", 'c'), new CharacterMappingInfo("ک", 'c'), new CharacterMappingInfo("ث", 'c')});

		//CharacterMapping attr_C = new CharacterMapping('C', true, new CharacterMappingInfo[] 
		//        { 
		//            new CharacterMappingInfo("س", 1),
		//            new CharacterMappingInfo("ص", 2),
		//            new CharacterMappingInfo("ث", 3),
		//            new CharacterMappingInfo("ک", 4),
		//            new CharacterMappingInfo("سی", LetterPosition.EndOfWord, 0),

		//            new CharacterMappingInfo("س", 'C'),
		//            new CharacterMappingInfo("ص", 'C'),
		//            new CharacterMappingInfo("ث", 'C'),
		//        });

		CharacterMapping attr_h = new CharacterMapping('h', new CharacterMappingInfo[] {new CharacterMappingInfo("ه", 0), new CharacterMappingInfo("ح", 1), new CharacterMappingInfo(PseudoSpace.ZWNJ+"", TokenPosition.MiddleOfWord, 2), new CharacterMappingInfo("ه", 'h'), new CharacterMappingInfo("ح", 'h')});

		CharacterMapping attr_p = new CharacterMapping('p', new CharacterMappingInfo[] {new CharacterMappingInfo("پ", 0), new CharacterMappingInfo("ف", 'h'), new CharacterMappingInfo("پ", 'p')});


		CharacterMapping attr_j = new CharacterMapping('j', new CharacterMappingInfo[] {new CharacterMappingInfo("ج", 0), new CharacterMappingInfo("ژ", 1), new CharacterMappingInfo("ج", 'j'), new CharacterMappingInfo("ژ", 'j')});

		CharacterMapping attr_g = new CharacterMapping('g', new CharacterMappingInfo[] {new CharacterMappingInfo("گ", 0), new CharacterMappingInfo("ج", 1), new CharacterMappingInfo("ق", 'h', TokenPosition.Any, 0), new CharacterMappingInfo("غ", 'h', TokenPosition.Any, 0), new CharacterMappingInfo("گ", 'g'), new CharacterMappingInfo("ج", 'g')});

		CharacterMapping attr_a = new CharacterMapping('a', new CharacterMappingInfo[] {new CharacterMappingInfo("\u064E", TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 1, "فتحه"), new CharacterMappingInfo("آ", TokenPosition.StartOfWord, 1), new CharacterMappingInfo("ا", 0), new CharacterMappingInfo("", 1), new CharacterMappingInfo("أ", TokenPosition.StartOfWord.OR(TokenPosition.MiddleOfWord), 2), new CharacterMappingInfo("ع", TokenPosition.StartOfWord, 2), new CharacterMappingInfo("ع", '\'', TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 0), new CharacterMappingInfo("ع", TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 0), new CharacterMappingInfo("ا", 'a', TokenPosition.MiddleOfWord, 0), new CharacterMappingInfo("آ", 'a', TokenPosition.StartOfWord, 0), new CharacterMappingInfo("اع", TokenPosition.StartOfWord, 2), new CharacterMappingInfo("\u0647" + PseudoSpace.ZWNJ + "\u0633", 's', TokenPosition.MiddleOfWord, 0), new CharacterMappingInfo("عا", 5), new CharacterMappingInfo("عاً", 'n', TokenPosition.MiddleOfWord, 2), new CharacterMappingInfo("اً", 'n', TokenPosition.MiddleOfWord, 0), new CharacterMappingInfo("ه", TokenPosition.EndOfWord, 2), new CharacterMappingInfo("ی", TokenPosition.EndOfWord, 2), new CharacterMappingInfo("وا", TokenPosition.MiddleOfWord, 2), new CharacterMappingInfo(PseudoSpace.ZWNJ + "ال", 'l', TokenPosition.MiddleOfWord, 3), new CharacterMappingInfo("ئ", TokenPosition.MiddleOfWord, 3), new CharacterMappingInfo("هم", 'm', TokenPosition.MiddleOfWord, 4), new CharacterMappingInfo("ه‌ت", 't', TokenPosition.MiddleOfWord, 4), new CharacterMappingInfo("ه‌ر", 'r', TokenPosition.MiddleOfWord, 4), new CharacterMappingInfo("ه‌ش", 's', TokenPosition.MiddleOfWord, 4)});
															

		CharacterMapping attr_e = new CharacterMapping('e', new CharacterMappingInfo[] {new CharacterMappingInfo("\u0650", TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 0, "کسره"), new CharacterMappingInfo("ا", TokenPosition.Any, 2), new CharacterMappingInfo("ه", TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 1), new CharacterMappingInfo("\u0647" + PseudoSpace.ZWNJ, TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 1), new CharacterMappingInfo("ی", TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 1), new CharacterMappingInfo("یی", TokenPosition.EndOfWord, 2), new CharacterMappingInfo("ع", 2), new CharacterMappingInfo("ئ", 2), new CharacterMappingInfo("ه", 'h'), new CharacterMappingInfo("اع", TokenPosition.StartOfWord, 0), new CharacterMappingInfo("ی", 'i'), new CharacterMappingInfo("ی", 'e'), new CharacterMappingInfo("ه‌ی", 'i', TokenPosition.MiddleOfWord, 5), new CharacterMappingInfo("ه‌ی", 'y', TokenPosition.MiddleOfWord, 5), new CharacterMappingInfo("ه‌ه", 'h', TokenPosition.MiddleOfWord, 5), new CharacterMappingInfo("ه‌", TokenPosition.MiddleOfWord, 5), new CharacterMappingInfo("عه‌ی", 'i', TokenPosition.MiddleOfWord, 5), new CharacterMappingInfo("عه‌ی", 'y', TokenPosition.MiddleOfWord, 5), new CharacterMappingInfo("عه‌ه", 'h', TokenPosition.MiddleOfWord, 5), new CharacterMappingInfo("ه‌ای", 'i', TokenPosition.MiddleOfWord, 5), new CharacterMappingInfo("ه‌ای", 'e', TokenPosition.MiddleOfWord, 5), new CharacterMappingInfo(PseudoSpace.ZWNJ + "ال", 'l', TokenPosition.MiddleOfWord, 3), new CharacterMappingInfo("", TokenPosition.MiddleOfWord, 5)});
															//new CharacterMappingInfo("عه‌", 'i', TokenPosition.MiddleOfWord, 5),

		CharacterMapping attr_i = new CharacterMapping('i', new CharacterMappingInfo[] {new CharacterMappingInfo("ی", 0), new CharacterMappingInfo("ای", 'i'), new CharacterMappingInfo("ی", 'e'), new CharacterMappingInfo("ی", 'y'), new CharacterMappingInfo("ای", TokenPosition.StartOfWord, 1), new CharacterMappingInfo("ی", 'i'), new CharacterMappingInfo("", TokenPosition.MiddleOfWord, 2)});

		CharacterMapping attr_k = new CharacterMapping('k', new CharacterMappingInfo[] {new CharacterMappingInfo("ک", 0), new CharacterMappingInfo("خ", 'h'), new CharacterMappingInfo("ک", 'k'), new CharacterMappingInfo("", 'k')});


		CharacterMapping attr_o = new CharacterMapping('o', new CharacterMappingInfo[] {new CharacterMappingInfo("ا", TokenPosition.StartOfWord, 2), new CharacterMappingInfo("او", TokenPosition.StartOfWord, 3), new CharacterMappingInfo("و", TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 0), new CharacterMappingInfo("\u064f", TokenPosition.MiddleOfWord.OR(TokenPosition.EndOfWord), 1, "ضمه"), new CharacterMappingInfo("ع", 2), new CharacterMappingInfo("وع", TokenPosition.EndOfWord, 2), new CharacterMappingInfo("و", 'o'), new CharacterMappingInfo("و", 'u'), new CharacterMappingInfo(PseudoSpace.ZWNJ + "ال", 'l', TokenPosition.MiddleOfWord, 3)});

		CharacterMapping attr_u = new CharacterMapping('u', new CharacterMappingInfo[] {new CharacterMappingInfo("و", 0), new CharacterMappingInfo("او", TokenPosition.StartOfWord, 1), new CharacterMappingInfo("و", 'u'), new CharacterMappingInfo("", 2)});

		CharacterMapping attr_z = new CharacterMapping('z', new CharacterMappingInfo[] {new CharacterMappingInfo("ز", 0), new CharacterMappingInfo("ذ", 1), new CharacterMappingInfo("ض", 1), new CharacterMappingInfo("ظ", 1), new CharacterMappingInfo("ژ", 'h'), new CharacterMappingInfo("ز", 'z'), new CharacterMappingInfo("ذ", 'z'), new CharacterMappingInfo("ض", 'z'), new CharacterMappingInfo("ظ", 'z')});

		CharacterMapping attr_quotation = new CharacterMapping('\'', new CharacterMappingInfo[] {new CharacterMappingInfo("ع", 0), new CharacterMappingInfo(PseudoSpace.ZWNJ+"", TokenPosition.MiddleOfWord, 1), new CharacterMappingInfo("", 2), new CharacterMappingInfo("آ", 'a', TokenPosition.MiddleOfWord, 3), new CharacterMappingInfo("عه", TokenPosition.EndOfWord, 1)});

		CharacterMapping attr_x = new CharacterMapping('x', new CharacterMappingInfo[] {new CharacterMappingInfo("خ", 0), new CharacterMappingInfo("کس", 1), new CharacterMappingInfo("خ", 'x'), new CharacterMappingInfo("کس", 'x')});


		CharacterMapping attr_q = new CharacterMapping('q', new CharacterMappingInfo[] {new CharacterMappingInfo("ق", 0), new CharacterMappingInfo("غ", 1), new CharacterMappingInfo("ق", 'q'), new CharacterMappingInfo("غ", 'q')});

		MultipleValueCharMap = new CharacterMapping[] {attr_b, attr_d, attr_r, attr_f, attr_l, attr_m, attr_n, attr_v, attr_w, attr_y, attr_a, attr_c, attr_e, attr_g, attr_h, attr_i, attr_j, attr_k, attr_o, attr_p, attr_s, attr_t, attr_u, attr_x, attr_q, attr_z, attr_quotation};
	}

	/** 
	 Initializes the <see cref="PinglishConverter"/> class.
	*/
	public PinglishConverter()
	{
		//TODO: It's better to load them from a file --> In progress -> DONE

		PreprocessElementInfo entryInfo = null;
		///////////////////////////////////////////////////////////////
		entryInfo = new PreprocessElementInfo("U", true);
		entryInfo.Equivalents.add("too");
		this.m_preprocessReplacements.put(entryInfo.getPinglishString(), entryInfo);
		///////////////////////////////////////////////////////////////
		entryInfo = new PreprocessElementInfo("I", true);
		entryInfo.Equivalents.add("man");
		this.m_preprocessReplacements.put(entryInfo.getPinglishString(), entryInfo);
		/////////////////////////////////////////////////////////////////
		entryInfo = new PreprocessElementInfo(QuotationMark.RightSingleQuotationMark+"", false);
		entryInfo.Equivalents.add(QuotationMark.SingleQuotationMark+"");
		this.m_preprocessReplacements.put(entryInfo.getPinglishString(), entryInfo);
		/////////////////////////////////////////////////////////////////
		entryInfo = new PreprocessElementInfo(QuotationMark.Prime+"", false);
		entryInfo.Equivalents.add(QuotationMark.SingleQuotationMark+"");
		this.m_preprocessReplacements.put(entryInfo.getPinglishString(), entryInfo);
		/////////////////////////////////////////////////////////////////
		entryInfo = new PreprocessElementInfo(QuotationMark.SingleHighReveresed9QuotationMark+"", false);
		entryInfo.Equivalents.add(QuotationMark.SingleQuotationMark+"");
		this.m_preprocessReplacements.put(entryInfo.getPinglishString(), entryInfo);
		/////////////////////////////////////////////////////////////////
		entryInfo = new PreprocessElementInfo("k", true);
		entryInfo.Equivalents.add("kei");
		this.m_preprocessReplacements.put(entryInfo.getPinglishString(), entryInfo);
		/////////////////////////////////////////////////////////////////

			///#region m_bigLettersAtBeginOrEnd

		this.m_bigLettersAtBeginOrEnd.put('B', new String[] {"b", "bi"});
		this.m_bigLettersAtBeginOrEnd.put('C', new String[] {"c", "si"});
		this.m_bigLettersAtBeginOrEnd.put('D', new String[] {"d", "di"});
		this.m_bigLettersAtBeginOrEnd.put('E', new String[] {"e", "ee"});
		this.m_bigLettersAtBeginOrEnd.put('F', new String[] {"f", "ef"});
		this.m_bigLettersAtBeginOrEnd.put('G', new String[] {"g", "ji"});
		this.m_bigLettersAtBeginOrEnd.put('K', new String[] {"k", "kei"});
		this.m_bigLettersAtBeginOrEnd.put('M', new String[] {"m","em"});
		this.m_bigLettersAtBeginOrEnd.put('N', new String[] {"n","en"});
		this.m_bigLettersAtBeginOrEnd.put('P', new String[] {"p","pi"});
		this.m_bigLettersAtBeginOrEnd.put('Q', new String[] {"q", "kioo"});
		this.m_bigLettersAtBeginOrEnd.put('S', new String[] {"s", "es"});
		this.m_bigLettersAtBeginOrEnd.put('T', new String[] {"t", "ti"});
		this.m_bigLettersAtBeginOrEnd.put('U', new String[] {"u", "yoo"});
		this.m_bigLettersAtBeginOrEnd.put('V', new String[] {"v", "vi"});
		this.m_bigLettersAtBeginOrEnd.put('Y', new String[] {"y", "vay"});
		this.m_bigLettersAtBeginOrEnd.put('Z', new String[] {"z", "zed"});


		m_oneLetterWords.put('4', new String[] {"baraye"});
		m_oneLetterWords.put('K', new String[] {"kei"});
		m_oneLetterWords.put('U', new String[] {"to"});
		/*m_oneLetterWords.Add('', new string[] { "" });
		m_oneLetterWords.Add('', new string[] { "" });
		m_oneLetterWords.Add('', new string[] { "" });
		m_oneLetterWords.Add('', new string[] { "" });
		m_oneLetterWords.Add('', new string[] { "" });
		m_oneLetterWords.Add('', new string[] { "" });
		m_oneLetterWords.Add('', new string[] { "" });*/

		for (char key : m_oneLetterWords.keySet())
		{
			assert StringUtil.OneLetterPinglishWords.contains(Character.toLowerCase(key));
		}

	}

	/** 
	 Extracts and learns all the mapping information from the given <see cref="PinglishString"/>
	 
	 @param pinglishString The instance of <see cref="PinglishString"/> 
	 which mapping information will be extracted from. 
	*/
	public final void Learn(PinglishString pinglishString)
	{
		m_converter.Learn(pinglishString, true);
	}

	/** 
	 Learns from the specified list of <see cref="PinglishString"/>
	 
	 @param listOfWords The list of words.
	 {@link Learn(PinglishString)}
	*/
	public final void Learn(LinqSimulationArrayList<PinglishString> listOfWords)
	{
		//m_converter.Learn(listOfWords, true);
		m_converter.Learn(listOfWords, false);
	}

	/** 
	 Loads the converter engine from the given file.
	 
	 @param fileName Name of the file.
	*/
	public final void LoadConverter(String fileName)
	{
		m_converter = PinglishMapping.LoadConverterEngine(fileName);
	}

	/** 
	 Saves the converter engine to the given file.
	 
	 @param fileName Name of the file.
	*/
	public final void SaveConverter(String fileName)
	{
		PinglishMapping.SaveConverterEngine(fileName, m_converter);
	}

	/** 
	 Loads the preprocess elements from the file
	 
	 @param filePath The file path.
	 @return 
	*/
	public final int LoadPreprocessElements(String filePath)
	{
		LinqSimulationArrayList<PreprocessElementInfo> list = PinglishConverterUtils.LoadPreprocessElements(filePath);

		for (PreprocessElementInfo item : list)
		{
			this.m_preprocessReplacements.put(item.getPinglishString(), item);
		}
		return list.size();
	}

	/** 
	 Sets the speller engine.
	 
	 @param spellerEngine The speller engine.
	*/
	public final void SetSpellerEngine(SpellCheckerEngine spellerEngine)
	{
		m_speller = spellerEngine;
		m_isSpellerEngineSet = true;
	}

	/** 
	 Returns the equivalent Farsi words, based on the previously learned data.
	 
	 @param pinglishWord The Pinglish word.
	 @return 
	*/
	public final PinglishString SuggestFarsiWordWithMapping(String pinglishWord)
	{
		PinglishString[] results = SuggestFarsiWordsWithMapping(pinglishWord);

		if (results.length != 0)
		{
			return results[0];
		}

		return new PinglishString(pinglishWord);
	}

	/** 
	 Suggests Farsi words for the given Pinglish word, based on the learned dataset.
	 
	 @param pinglishWord The Pinglish word.
	 @return 
	*/
	public final PinglishString[] SuggestFarsiWordsWithMapping(String pinglishWord)
	{
		Iterable<String> revisedPinglishWords = PreprocessWord(pinglishWord);
		LinqSimulationArrayList<PinglishString> results = new LinqSimulationArrayList<PinglishString>();

		LinqSimulationArrayList<LinqSimulationArrayList<PinglishString>> listOfResults = new LinqSimulationArrayList<LinqSimulationArrayList<PinglishString>>();

		for (String word : revisedPinglishWords)
		{
			listOfResults.add(m_converter.SuggestWords(word, false));
		}
		int maxCount =0;
		for(ArrayList<PinglishString>list:listOfResults)
			if(list.size()>maxCount)
				maxCount=list.size();
		
		for (int i = 0; i < maxCount; i++)
		{
			for(ArrayList<PinglishString>list:listOfResults)
				if(i<list.size())
					results.addAll(list);
		}

		return results.Distinct(new PinglishString(), s_pinglishStringComparer).toArray(new PinglishString[0]);
	}

	/** 
	 Suggests Farsi word for the given Pinglish word, based on the learned dataset.
	*/
	public final String SuggestFarsiWord(String pinglishWord, boolean sortWithSpeller)
	{
		String[] results = SuggestFarsiWords(pinglishWord, sortWithSpeller);

		if (results.length != 0)
		{
			return results[0];
		}

		return pinglishWord;
	}

	/** 
	 Suggests Farsi words for the given Pinglish word, based on the learned dataset.
	*/
	public final String[] SuggestFarsiWords(String pinglishWord, boolean sortWithSpeller)
	{
		// 1. Search for exact words
		String[] exactResults = SearchForExactWords(pinglishWord);

		if (exactResults.length != 0)
		{
			return exactResults;
		}

		// 2. Suggest from mappings
		PinglishString[] results = SuggestFarsiWordsWithMapping(pinglishWord);

		String[] pinglishResults = ArraysUtility.ToStringArray(results,true, true);

		if (sortWithSpeller)
		{
			if (m_speller == null)
			{
				throw new UnsupportedOperationException("Speller engine has not been set. User SetSpellerEngine to set it.");
			}

			String[] spellerResults = m_speller.SortSuggestions(pinglishResults, pinglishResults.length);

			return (spellerResults.length!= 0) ? spellerResults : pinglishResults;
		}
		else
		{
			return pinglishResults;
		}
	}

	/** 
	 Generates all possible words. 
	 Note that this method may return hundreds of words if the given Pinglish word contains many letters that have
	 more that one mapping.
	 A preprocess phase is applied to normalize characters in the word.
	 
	 @param pinglishWord The Pinglish word.
	 @return 
	*/
	public final PinglishString[] GenerateAllPossibleWords(String pinglishWord)
	{
		Iterable<String> revisedPinglishWords = PreprocessWord(pinglishWord);

		LinqSimulationArrayList<PinglishString> results = new LinqSimulationArrayList<PinglishString>();

		for (String revisedPinglish : revisedPinglishWords)
		{
			results.addAll(PrivateGenerateAllPossibleWords(revisedPinglish));
		}

		return results.Distinct(new PinglishString(), s_pinglishStringComparer).toArray(new PinglishString[0]);
	}

	private static Iterable<PinglishString> PrivateGenerateAllPossibleWords(String revisedPinglish)
	{
		LinqSimulationArrayList<PinglishString> wordsList = new LinqSimulationArrayList<PinglishString>(java.util.Arrays.asList(new PinglishString[] {new PinglishString()}));

		char ch;
		char nextChar;
		TokenPosition position;
		LinqSimulationArrayList<CharacterMappingInfo> possibleValues = new LinqSimulationArrayList<CharacterMappingInfo>();
		LinqSimulationArrayList<CharacterMappingInfo> possible2Values = new LinqSimulationArrayList<CharacterMappingInfo>();

			///#region iterate through all characters
		for (int chIndex = 0; chIndex < revisedPinglish.length(); chIndex++)
		{

				///#region Prepare Loop Variables
			ch = revisedPinglish.charAt(chIndex); //char.ToLower(revisedPinglish[chIndex]);

			if (chIndex < revisedPinglish.length() - 1)
			{
				nextChar = revisedPinglish.charAt(chIndex + 1);

				if (chIndex == 0)
				{
					position = TokenPosition.StartOfWord;
				}
				else
				{
					position = TokenPosition.MiddleOfWord;
				}
			}
			else
			{
				nextChar = CharacterMappingInfo.EmptyChar;
				position = TokenPosition.EndOfWord;
			}

			// Oh, no! I must generate all possible words from this PinglishWord

			for (CharacterMapping attr : MultipleValueCharMap)
			{

				if(attr.getIsCaseSensitive())
				{	
					if ((attr.getLetter()+"").compareTo(ch+"")!=0)
					{
						continue;
					}
				}
				else{
					if ((attr.getLetter()+"").compareToIgnoreCase(ch+"")!=0)
					{
						continue;
					}
				}

				possibleValues.clear();
				possible2Values.clear();

				for (CharacterMappingInfo attrValue : attr.getValues())
				{
					if (attrValue.getPostfix() == CharacterMappingInfo.EmptyChar)
					{
						if ((attrValue.getPosition().getValue() & position.getValue()) == position.getValue())
						{
							possibleValues.add(attrValue);
						}
					}
					else if (nextChar != CharacterMappingInfo.EmptyChar && (attrValue.getPostfix()+"").compareToIgnoreCase(nextChar+"")==0)
					{
						possible2Values.add(attrValue);
					}
				}

				LinqSimulationArrayList.UpdateClone(wordsList,chIndex, possibleValues, possible2Values, ch, nextChar);
				continue;
			}
		}

		return wordsList;
	}

	private String[] SearchForExactWords(String pinglishWord)
	{
		LinqSimulationArrayList<String> results = new LinqSimulationArrayList<String>();

		for(Entry<String,PreprocessElementInfo> element: this.m_preprocessReplacements.entrySet())
		{
			if(element.getValue().IsExactWord && element.getKey().compareToIgnoreCase(pinglishWord) == 0)
				results.addAll(element.getValue().Equivalents);
		}

		return results.Distinct("").toArray(new String[0]);
	}

	/** 
	 Applies preprocess rules to the given word.
	 
	 @param pinglishWord
	 @return 
	*/
	private Iterable<String> PreprocessWord(String pinglishWord)
	{
		pinglishWord = pinglishWord.trim();
		LinqSimulationArrayList<String> results = new LinqSimulationArrayList<String>();
		if (Helper.DotNetToJavaStringHelper.isNullOrEmpty(pinglishWord))
		{
			return results;
		}

		String[] replacements;

		boolean firstUpperLetterReplacement = true;
		boolean lastUpperLetterReplacement = true;

		if (this.m_bigLettersAtBeginOrEnd.keySet().contains(pinglishWord.charAt(0)))
		{
			for (int i = 1; i < pinglishWord.length() - 2; i++)
			{
				if (!Character.isLowerCase(pinglishWord.charAt(i)))
				{
					firstUpperLetterReplacement = false;
					break;
				}
			}
			if (firstUpperLetterReplacement)
			{
				replacements = this.m_bigLettersAtBeginOrEnd.get(pinglishWord.charAt(0));
				for (int i = 0; i < replacements.length; i++)
				{
					results.add(replacements[i] + pinglishWord.substring(1));
				}
			}
		}
		if (this.m_bigLettersAtBeginOrEnd.keySet().contains(pinglishWord.charAt(pinglishWord.length() - 1)))
		{
			for (int i = pinglishWord.length() - 2; i > 0; i--)
			{
				if (!Character.isLowerCase(pinglishWord.charAt(i)))
				{
					lastUpperLetterReplacement = false;
					break;
				}
			}
			if (lastUpperLetterReplacement && pinglishWord.length() > 1)
			{
				replacements = this.m_bigLettersAtBeginOrEnd.get(pinglishWord.charAt(pinglishWord.length() - 1));
				for (int i = 0; i < replacements.length; i++)
				{
					results.add(pinglishWord.substring(0, pinglishWord.length() - 1) + replacements[i]);
				}
			}
		}

		for (String key : this.m_preprocessReplacements.keySet())
		{
			PreprocessElementInfo preprocess = this.m_preprocessReplacements.get(key);

			if (preprocess.IsWholeWord && key.compareToIgnoreCase(pinglishWord) != 0)
			{
				continue;
			}

			if (!preprocess.IsWholeWord)
			{
				if (preprocess.Position.getValue() == TokenPosition.StartOfWord.getValue() && !pinglishWord.startsWith(key))
				{
					continue;
				}

				if (preprocess.Position.getValue() == TokenPosition.EndOfWord.getValue() && !pinglishWord.endsWith(key))
				{
					continue;
				}

				if ((preprocess.Position.getValue() == TokenPosition.MiddleOfWord.getValue() || preprocess.Position == TokenPosition.Any) && !pinglishWord.toLowerCase().contains(key.toLowerCase()))
				{
					continue;
				}
			}

			replacements = preprocess.Equivalents.toArray(new String[0]);
			for (int i = 0; i < replacements.length; i++)
			{
				results.add(pinglishWord.toLowerCase().replace(key.toLowerCase(), replacements[i]));
			}
		}

		if (results.isEmpty() || !results.contains(pinglishWord))
		{
			results.add(pinglishWord);
		}
			///#region Remove Duplicate characters -- Salaaaaam ==> Salaam (Max 2)

		for (int index = 0; index < results.size(); index++)
		{
			String word = results.get(index).toLowerCase();
			char prevChar = (char)0;

			StringBuilder wordWithNo3Dup = new StringBuilder();
			for (int i = 0; i < word.length(); i++)
			{
				char currChar = word.charAt(i);

				if ((prevChar+"").compareToIgnoreCase(currChar+"")==0)
				{
					wordWithNo3Dup.append(currChar);
					while (i < word.length())
					{
						if ((word.charAt(i)+"").compareToIgnoreCase(currChar+"")==0)
						{
							++i;
						}
						else
						{
							--i;
							break;
						}
					}
				}
				else
				{
					wordWithNo3Dup.append(currChar);
					prevChar = currChar;
				}
			}
			results.set(index, wordWithNo3Dup.toString());
		}
		return results;
	}

	@Override
	public void Learn(ArrayList<PinglishString> listOfWords) {
		
	}

	@Override
	public void readExternal(final ObjectInput inputObject) throws IOException,
			ClassNotFoundException {
		m_bigLettersAtBeginOrEnd=(HashMap<Character, String[]>) inputObject.readObject();
		m_converter=(PinglishMapping) inputObject.readObject();
		m_oneLetterWords=(HashMap<Character, String[]>) inputObject.readObject();
		//s_pinglishStringComparer=(PinglishStringEqualityComparer) inputObject.readObject();
		//m_preprocessReplacements=(HashMap<String, PreprocessElementInfo>) inputObject.readObject();
		
	}

	@Override
	public void writeExternal(final ObjectOutput outputObject) throws IOException {
		outputObject.writeObject(m_bigLettersAtBeginOrEnd);
		outputObject.writeObject(m_converter);
		outputObject.writeObject(m_oneLetterWords);
		//outputObject.writeObject(s_pinglishStringComparer);
		//outputObject.writeObject(m_preprocessReplacements);
		
		
	}
	
	
	public PatternStorage get_patternStorage() {
		return this.m_converter.get_patternStorage();
	}
	public void set_patternStorage(PatternStorage pattern) {
		this.m_converter.set_patternStorage(pattern);
	}
	public void updateDataSet(LinqSimulationArrayList<PinglishString> listOfWords)
	{
		m_converter.updateDataSet(listOfWords);
	}
	
	
	
	
}