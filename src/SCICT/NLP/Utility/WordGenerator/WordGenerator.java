package SCICT.NLP.Utility.WordGenerator;

import Helper.ArraysUtility;
import Helper.LinqSimulationArrayList;
import Helper.StringExtensions;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.*;

	/**
 Word Generator Class, This class generates respelling suggestions in given edit distance for a word
	*/
public final class WordGenerator
{
	//private static LetterMatrix s_LetterMatrix = new LetterMatrix();
	//private static Dictionary<char, char[]> m_substitutionMatrix = new Dictionary<char, char[]>();

	private static long m_accuracy = 1;
	private static final int MaxSuggestionCount = 500000;

	/*
	static WordGenerator()
	{
	    m_substitutionMatrix.Add('ض', new char[] { 'ص', 'ش', 'س', 'پ' });
	    m_substitutionMatrix.Add('پ', new char[] { 'ص', 'ش', 'س', 'ض', 'ج', 'گ', 'چ', 'ژ' });
	    m_substitutionMatrix.Add('ص', new char[] { 'ض', 'ث', 'س', 'ی', 'ش', 'پ' });
	    m_substitutionMatrix.Add('ث', new char[] { 'ص', 'ق', 'ی', 'ب', 'س' });
	    m_substitutionMatrix.Add('ق', new char[] { 'ث', 'ف', 'ب', 'ل', 'ی' });
	    m_substitutionMatrix.Add('ف', new char[] { 'ق', 'غ', 'ل', 'ا', 'ب', 'آ' });
	    m_substitutionMatrix.Add('غ', new char[] { 'ف', 'ع', 'ا', 'ت', 'ل', 'آ' });
	    m_substitutionMatrix.Add('ع', new char[] { 'غ', 'ه', 'ت', 'ن', 'ا', 'آ' });
	    m_substitutionMatrix.Add('ه', new char[] { 'ع', 'خ', 'ن', 'م', 'ت' });
	    m_substitutionMatrix.Add('خ', new char[] { 'ه', 'ح', 'م', 'ک', 'ن' });
	    m_substitutionMatrix.Add('ح', new char[] { 'خ', 'ج', 'ک', 'گ', 'م' });
	    m_substitutionMatrix.Add('ج', new char[] { 'ح', 'چ', 'گ', 'ک', 'پ', 'ژ' });
	    m_substitutionMatrix.Add('چ', new char[] { 'ج', 'گ', 'ژ', 'پ' });
	    m_substitutionMatrix.Add('ش', new char[] { 'س', 'ض', 'ظ', 'ط', 'ص', 'پ' });
	    m_substitutionMatrix.Add('س', new char[] { 'ش', 'ی', 'ص', 'ط', 'ض', 'ز', 'ث', 'ظ', 'پ', 'ژ' });
	    m_substitutionMatrix.Add('ی', new char[] { 'س', 'ب', 'ث', 'ز', 'ص', 'ر', 'ق', 'ط', 'ژ', 'ؤ' });
	    m_substitutionMatrix.Add('ب', new char[] { 'ی', 'ل', 'ق', 'ر', 'ث', 'ذ', 'ف', 'ز', 'ؤ', 'إ', 'ژ' });
	    m_substitutionMatrix.Add('ل', new char[] { 'ب', 'ا', 'ف', 'ذ', 'ق', 'د', 'غ', 'ر', 'آ', 'إ', 'أ', 'ؤ' });
	    m_substitutionMatrix.Add('ا', new char[] { 'ل', 'ت', 'غ', 'د', 'ف', 'ئ', 'ع', 'ذ', 'أ', 'ء', 'إ', 'آ' });
	    m_substitutionMatrix.Add('آ', new char[] { 'ل', 'ت', 'غ', 'د', 'ف', 'ئ', 'ع', 'ذ', 'ا', 'أ', 'ء', 'إ' });
	    m_substitutionMatrix.Add('ت', new char[] { 'ا', 'ن', 'ع', 'ئ', 'غ', 'و', 'ه', 'د', 'آ', 'ء', 'أ' });
	    m_substitutionMatrix.Add('ن', new char[] { 'ت', 'م', 'ه', 'و', 'ع', 'خ', 'ئ', 'ء' });
	    m_substitutionMatrix.Add('م', new char[] { 'ن', 'ک', 'خ', 'ه', 'ح', 'و' });
	    m_substitutionMatrix.Add('ک', new char[] { 'م', 'گ', 'ح', 'خ', 'ج' });
	    m_substitutionMatrix.Add('گ', new char[] { 'ک', 'ج', 'ح', 'چ', 'ژ', 'پ' });
	    m_substitutionMatrix.Add('ژ', new char[] { 'گ', 'چ', 'ج', 'پ', 'ط', 'ر', 'ی', 'س', 'ب', 'ز', 'ؤ' });
	    m_substitutionMatrix.Add('ظ', new char[] { 'ط', 'ش', 'س' });
	    m_substitutionMatrix.Add('ط', new char[] { 'ظ', 'ز', 'س', 'ش', 'ی', 'ژ' });
	    m_substitutionMatrix.Add('ز', new char[] { 'ط', 'ر', 'ی', 'س', 'ب', 'ؤ', 'ژ' });
	    m_substitutionMatrix.Add('ر', new char[] { 'ز', 'ذ', 'ب', 'ی', 'ل', 'ژ', 'إ', 'ؤ' });
	    m_substitutionMatrix.Add('ؤ', new char[] { 'ز', 'ذ', 'ب', 'ی', 'ل', 'ر', 'ژ', 'إ' });
	    m_substitutionMatrix.Add('ذ', new char[] { 'ر', 'د', 'ل', 'ب', 'ا', 'ؤ', 'أ', 'آ', 'إ' });
	    m_substitutionMatrix.Add('إ', new char[] { 'ر', 'د', 'ل', 'ب', 'ا', 'ذ', 'ؤ', 'أ', 'آ' });
	    m_substitutionMatrix.Add('د', new char[] { 'ذ', 'ئ', 'ا', 'ل', 'ت', 'إ', 'ء', 'آ', 'أ' });
	    m_substitutionMatrix.Add('أ', new char[] { 'ذ', 'ئ', 'ا', 'ل', 'ت', 'د', 'إ', 'ء', 'آ' });
	    m_substitutionMatrix.Add('ئ', new char[] { 'د', 'و', 'ت', 'ا', 'ن', 'أ', 'آ', 'ء' });
	    m_substitutionMatrix.Add('ء', new char[] { 'د', 'و', 'ت', 'ا', 'ن', 'ئ', 'أ', 'آ' });
	    m_substitutionMatrix.Add('و', new char[] { 'ئ', 'ن', 'ت', 'م', 'ء' });
	}
	*/

	private static String[] GetWordsWithOneEditDistanceSubstitute(String word, Character... alphabet)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			for (char c : alphabet)
			{
				for (int i = 0; i < word.length(); ++i)
				{
					suggestion.add(StringExtensions.Insert((word.substring(0, i) + word.substring(i + 1)),i, (new Character(c)).toString()));
				}
			}

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}

	/*
	private static string[] GetWordsWithOneEditDistanceSubstitute(string word)
	{
	    try
	    {
	        List<string> suggestion = new List<string>();

	        for (int i = 0; i < word.Length; ++i)
	        {
	            foreach (char c in m_substitutionMatrix[word[i]])
	            {
	                suggestion.Add(word.Remove(i, 1).Insert(i, c.ToString()));
	            }
	        }

	        return suggestion.ToArray();
	    }
	    catch (Exception ex)
	    {
	        System.out.printf(string.Format("Error in generating suggestions: {0}", ex.Message));
	    }

	    return new string[] { word };
	}
	*/

	private static String[] GetWordsWithOneEditDistanceSubstitute(String word)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();
			char[] charArray=PersianAlphabets.getAlphabetWithPseudoSpace().toCharArray();
			for (char c : charArray)
			{
				for (int i = 0; i < word.length(); ++i)
				{
					suggestion.add(StringExtensions.Insert((word.substring(0, i) + word.substring(i + 1)),i, (new Character(c)).toString()));
				}
			}

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[] {""};
	}
	private static String[] GetWordsWithOneEditDistanceTranspose(String word)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			for (int i = 0; i < word.length() - 1; ++i)
			{
				suggestion.add(StringExtensions.Insert((word.substring(0, i) + word.substring(i + 1)),i + 1, word.charAt(i)+""));
			}

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}
	private static String[] GetWordsWithOneEditDistanceDelete(String word)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			for (int i = 0; i < word.length(); ++i)
			{
				suggestion.add(word.substring(0, i) + word.substring(i + 1));
			}

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}
	private static String[] GetWordsWithOneEditDistanceInsert(String word, Character... alphabet)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			for (char c : alphabet)
			{
				for (int i = 0; i < word.length() + 1; ++i)
				{
					suggestion.add(StringExtensions.Insert(word,i, (new Character(c)).toString()));
				}
			}

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}
	private static String[] GetWordsWithOneEditDistanceInsert(String word)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();
			
			char[] charArray=PersianAlphabets.getAlphabetWithPseudoSpace().toCharArray();
			for (char c : charArray)
			{
				for (int i = 0; i < word.length() + 1; ++i)
				{
					suggestion.add(StringExtensions.Insert(word,i, (new Character(c)).toString()));
				}
			}

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}

	private static String[] GetWordsWithOneEditDistance(String word)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			suggestion.addAll(GetWordsWithOneEditDistanceDelete(word));
			suggestion.addAll(GetWordsWithOneEditDistanceSubstitute(word));
			suggestion.addAll(GetWordsWithOneEditDistanceInsert(word));
			suggestion.addAll(GetWordsWithOneEditDistanceTranspose(word));

			//Added on 28-March-2010
			//Commented on 30-March-2010
			//suggestion.Add(word);

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}
	private static String[] GetWordsWithOneEditDistance(String word, RespellingGenerationType respellingType)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			if ((respellingType.getValue() & RespellingGenerationType.Delete.getValue()) != 0)
			{
				suggestion.addAll(GetWordsWithOneEditDistanceDelete(word));
			}
			if ((respellingType.getValue() & RespellingGenerationType.Substitute.getValue()) != 0)
			{
				suggestion.addAll(GetWordsWithOneEditDistanceSubstitute(word));
			}
			if ((respellingType.getValue() & RespellingGenerationType.Insert.getValue()) != 0)
			{
				suggestion.addAll(GetWordsWithOneEditDistanceInsert(word));
			}
			if ((respellingType.getValue() & RespellingGenerationType.Transpose.getValue()) != 0)
			{
				suggestion.addAll(GetWordsWithOneEditDistanceTranspose(word));
			}

			//Added on 28-March-2010
			//Commented on 30-March-2010
			//suggestion.Add(word);

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}
	private static String[] GetWordsWithOneEditDistance(String word, RespellingGenerationType respellingType, Character... alphabet)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			if ((respellingType.getValue() & RespellingGenerationType.Delete.getValue()) != 0)
			{
				suggestion.addAll(GetWordsWithOneEditDistanceDelete(word));
			}
			if ((respellingType.getValue() & RespellingGenerationType.Substitute.getValue()) != 0)
			{
				suggestion.addAll(GetWordsWithOneEditDistanceSubstitute(word, alphabet));
			}
			if ((respellingType.getValue() & RespellingGenerationType.Insert.getValue()) != 0)
			{
				suggestion.addAll(GetWordsWithOneEditDistanceInsert(word, alphabet));
			}
			if ((respellingType.getValue() & RespellingGenerationType.Transpose.getValue()) != 0)
			{
				suggestion.addAll(GetWordsWithOneEditDistanceTranspose(word));
			}

			//Added on 28-March-2010
			//Commented on 30-March-2010
			//suggestion.Add(word);

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}

	private static String[] GetWordsTillAnyEditDistance(String[] word, int editDistance)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			if (editDistance == 1)
			{
				for (String localWord : word)
				{
					suggestion.addAll(GetWordsWithOneEditDistance(localWord));
				}
			}
			else
			{
				for (String localWord : word)
				{
					suggestion.addAll(GetWordsTillAnyEditDistance(GetWordsWithOneEditDistance(localWord), editDistance - 1));
				}
			}

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[0];
	}
	private static String[] GetWordsTillAnyEditDistance(String[] word, int editDistance, RespellingGenerationType respellingType)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			if (editDistance == 1)
			{
				for (String localWord : word)
				{
					suggestion.addAll(GetWordsWithOneEditDistance(localWord, respellingType));
				}
			}
			else
			{
				for (String localWord : word)
				{
					suggestion.addAll(GetWordsTillAnyEditDistance(GetWordsWithOneEditDistance(localWord), editDistance - 1, respellingType));
				}
			}

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[0];
	}
	private static String[] GetWordsTillAnyEditDistance(String[] word, int editDistance, RespellingGenerationType respellingType, Character... alphabet)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			if (editDistance == 1)
			{
				for (String localWord : word)
				{
					suggestion.addAll(GetWordsWithOneEditDistance(localWord, respellingType, alphabet));
				}
			}
			else
			{
				for (String localWord : word)
				{
					suggestion.addAll(GetWordsTillAnyEditDistance(GetWordsWithOneEditDistance(localWord, respellingType, alphabet), editDistance - 1, respellingType, alphabet));
				}
			}

			return suggestion.toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[0];
	}

	/**
	 Generates respelling suggestions in given edit distance for a word
	
	@param word Word
	@param editDistance Edit Distance
	@return Respelling Suggestions
	*/
	public static String[] GenerateRespelling(String word, int editDistance)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			if (editDistance == 1)
			{
				suggestion.addAll(GetWordsWithOneEditDistance(word));
			}
			else
			{
				suggestion.addAll(GetWordsTillAnyEditDistance(GetWordsWithOneEditDistance(word), editDistance - 1));
			}
			return suggestion.Distinct("").toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions for any edit distance: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}

	/**
	 Generates respelling suggestions in given edit distance for a word
	
	@param word Word
	@param editDistance Edit Distance
	@param respellingType Type of generating respelling, logically OR desired types
	@return Respelling Suggestions
	*/
	public static String[] GenerateRespelling(String word, int editDistance, RespellingGenerationType respellingType)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			if (editDistance == 1)
			{
				suggestion.addAll(GetWordsWithOneEditDistance(word, respellingType));
			}
			else
			{
				suggestion.addAll(GetWordsTillAnyEditDistance(GetWordsWithOneEditDistance(word), editDistance - 1, respellingType));
			}

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return suggestion.Distinct("").toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions for any edit distance: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}

	/**
	 Generates respelling suggestions in given edit distance for a word
	
	@param word Word
	@param editDistance Edit Distance
	@param respellingType Type of generating respelling, logically OR desired types
	@param alphabet List of charachters used to generate respelling
	@return Respelling Suggestions
	*/
	public static String[] GenerateRespelling(String word, int editDistance, RespellingGenerationType respellingType, Character... alphabet)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

			if (editDistance == 1)
			{
				suggestion.addAll(GetWordsWithOneEditDistance(word, respellingType, alphabet));
			}
			else
			{
				suggestion.addAll(GetWordsTillAnyEditDistance(GetWordsWithOneEditDistance(word, respellingType, alphabet), editDistance - 1, respellingType, alphabet));
			}

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return suggestion.Distinct("").toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions for any edit distance: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}

	/** 
	 Accuracy of generating homophone words, It can be a number between 0 to 1 which 0 means minimum accuracy but fastest and 1 means maximum accuracy but slowest
	 
	 @param p
	*/
	public static void SetAccuracy(double p)
	{
		if (p <= 0 || p > 1)
		{
			p = 1;
		}

		m_accuracy = (long)(MaxSuggestionCount * p);
	}

	/**
	 Generate homophone words of given word, homophone words are those that can pronounce the same
	
	@param word Word
	@return Homophone Words
	*/
	public static String[] GenerateHomophoneWords(String word)
	{
		try
		{
			LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();
			suggestion.add(word);

			for (char[] homoPhoneFamily : PersianHomophoneLetters.getAllHomophones())
			{
				suggestion.addAll(GenerateHomophoneWords(suggestion.toArray(new String[0]), homoPhoneFamily));
			}

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return suggestion.Distinct("").toArray(new String[0]);
		}
		catch (RuntimeException ex)
		{
			System.out.printf(String.format("Error in generating suggestions: %1$s", ex.getMessage()));
		}

		return new String[] {word};
	}

	private static String[] GenerateHomophoneWords(String[] word, char[] homophones)
	{
		LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>(word);
		String[] tmpSuggestion;

		int maxWordLength = word[0].length();
		for (int i = 0; i < maxWordLength; ++i)
		{
			tmpSuggestion = suggestion.toArray(new String[0]);
			for (String localWord : tmpSuggestion)
			{
				if (ArraysUtility.indexOf(homophones, localWord.charAt(i)) != -1)
				{
					suggestion.addAll(GetHomophonePermutation(localWord, homophones, i));
				}

				if (suggestion.size() > m_accuracy)
				{
					break;
				}
			}
		}

		return suggestion.toArray(new String[0]);
	}
	private static String[] GetHomophonePermutation(String word, char[] homophones, int startIndex)
	{
		LinqSimulationArrayList<String> suggestion = new LinqSimulationArrayList<String>();

		for (char c : homophones)
		{
			if (word.charAt(startIndex) != c)
			{
				suggestion.add(StringExtensions.Insert((word.substring(0, startIndex) + word.substring(startIndex + 1)),startIndex, (new Character(c)).toString()));

				//if (!suggestion.Contains(tmp))
				//{
				//    suggestion.Add(tmp);
				//}
			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		return suggestion.Distinct("").toArray(new String[0]);
	}
	private static void AddNonIteratedValues(LinqSimulationArrayList<String> suggestion, String[] p)
	{
		for (String str : p)
		{
			if (!suggestion.contains(str))
			{
				suggestion.add(str);
			}
		}
	}

}