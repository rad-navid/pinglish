package SCICT.NLP.Utility.StringDistance;

import Helper.StringExtensions;
import SCICT.NLP.Persian.Constants.*;
import SCICT.Utility.*;
import SCICT.NLP.Utility.*;

public final class StringDistanceAlgorithms
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region static variables

	private static boolean s_exportResultAsSimilarity = true;
	public static boolean getExportResultAsSimilarity()
	{
		return s_exportResultAsSimilarity;
	}
	public static void setExportResultAsSimilarity(boolean value)
	{
		s_exportResultAsSimilarity = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 The Hamming distance H is defined only for strings of the same length. 
	 For two strings s and t, H(s, t) is the number of places in which the two string differ, i.e., have different characters.
	 ref: http://www.cut-the-knot.org/do_you_know/Strings.shtml
	 We also add difference of length of two strings to result.
	 
	 @param word1 First word
	 @param word2 Second word
	*/
	public static double Hamming(String word1, String word2)
	{
		// this algorithm normally computes un-normalized distance between two string.
		int difference = 0;

		// iStep 1: count differing characters:
		for (int counter1 = 0, counter2 = 0; (counter1 < word1.length()) && (counter2 < word2.length()); counter1++, counter2++)
		{
			if (word1.charAt(counter1) != word2.charAt(counter2))
			{
				difference++;
			}
		}
		// iStep 2: add |len2-len1| to difference variable:
		difference += Math.abs(word1.length() - word2.length());

		return ExportResult(difference, word1.length(), word2.length(), false);
	}

	/** 
	 Cosine similarity is a common vector based similarity measure similar to dice coefficient. 
	 Whereby the input string is transformed into vector space so that the Euclidean cosine rule can be used to determine similarity. 
	 The cosine similarity is often paired with other approaches to limit the dimensionality of the problem. 
	 For instance with simple strings at list of stopwords are used to exclude from the dimensionality of the comparison.
	 ref: http://www.dcs.shef.ac.uk/~sam/stringmetrics.html#cosine
	 
	 @param word1 First word
	 @param word2 Second word
	*/
	public static double Cosine(String word1, String word2)
	{
		// this algorithm normally computes normalized similarity between two string.                      
		String uniqueCharacters = "";

		for (int i = 0; i < word1.length(); i++)
		{
			if (uniqueCharacters.indexOf(word1.charAt(i)) < 0)
			{
				uniqueCharacters += word1.charAt(i);
			}
		}

		int uniqueCharactersOfWord1 = uniqueCharacters.length();

		for (int i = 0; i < word2.length(); i++)
		{
			if (uniqueCharacters.indexOf(word2.charAt(i)) < 0)
			{
				uniqueCharacters += word2.charAt(i);
			}
		}

		int uniqueCharactersOfBothWords = uniqueCharacters.length();

		// uniquCharacter variable is not useful later. so we use it to count uniqu characters of string 2
		uniqueCharacters = "";

		for (int i = 0; i < word2.length(); i++)
		{
			if (uniqueCharacters.indexOf(word2.charAt(i)) < 0)
			{
				uniqueCharacters += word2.charAt(i);
			}
		}

		int uniqueCharactersOfWord2 = uniqueCharacters.length();

		int commonTerms = uniqueCharactersOfWord1 + uniqueCharactersOfWord2 - uniqueCharactersOfBothWords;
		// iStep 2: add |len2-len1| to difference variable:
		double similarity = commonTerms / (Math.sqrt(uniqueCharactersOfWord1) * Math.sqrt(uniqueCharactersOfWord2));


		return ExportResult(similarity, true);
	}

	/** 
	 The Levenshtein distance between two strings is given by the minimum number of operations needed to transform one string into the other, 
	 where an operation is an insertion, deletion, or substitution of a single character.
	 ref: http://en.wikipedia.org/wiki/Levenshtein_distance
	 
	 @param word1 First word
	 @param word2 Second word
	*/
	public static double Levenstein(String word1, String word2)
	{
		// this algorithm normally computes un-normalized distance between two string.
		int len1 = word1.length();
		int len2 = word2.length();
		int[][] distance = new int[len1 + 1][len2 + 1];

		for (int i = 0; i < len1 + 1; i++)
		{
			distance[i][0] = i;
		}

		for (int i = 0; i < len2 + 1; i++)
		{
			distance[0][i] = i;
		}

		int cost;
		for (int i = 0; i < len1; i++)
		{
			for (int j = 0; j < len2; j++)
			{
				cost = (word1.charAt(i) == word2.charAt(j)) ? 0 : 1;

				distance[i + 1][j + 1] = Minimum(distance[i][j + 1] + 1, distance[i + 1][j] + 1, distance[i][j] + cost); // substitution -  insertion -  deletion
			}
		}
		double levensteinResult = distance[len1][len2];

		// normalize result:
		return ExportResult(levensteinResult, len1, len2, false);
	}

	/** 
	 The Levenshtein distance between two strings is given by the minimum number of operations needed to transform one string into the other, 
	 where an operation is an insertion, deletion, or substitution of a single character.
	 ref: http://en.wikipedia.org/wiki/Levenshtein_distance
	 
	 @param word1 First word
	 @param word2 Second word
	*/
	public static double DamerauLevenstein(String word1, String word2)
	{
		// this algorithm normally computes un-normalized distance between two string.
		int len1 = word1.length();
		int len2 = word2.length();
		int[][] distance = new int[len1 + 1][len2 + 1];

		for (int i = 0; i < len1 + 1; i++)
		{
			distance[i][0] = i;
		}

		for (int i = 0; i < len2 + 1; i++)
		{
			distance[0][i] = i;
		}


		int cost;
		for (int i = 0; i < len1; i++)
		{
			for (int j = 0; j < len2; j++)
			{
				cost = (word1.charAt(i) == word2.charAt(j)) ? 0 : 1;

				int transposeCost = 2;
				if (j > 0 && i > 0)
				{
					if (word1.charAt(i) == word2.charAt(j - 1) && word1.charAt(i - 1) == word2.charAt(j))
					{
						transposeCost = 1;
					}

					distance[i + 1][j + 1] = Minimum(distance[i][j + 1] + 1, distance[i + 1][j] + 1, distance[i][j] + cost, distance[i - 1][j - 1] + transposeCost); // substitution -  insertion -  deletion

				}
				else
				{
					distance[i + 1][j + 1] = Minimum(distance[i][j + 1] + 1, distance[i + 1][j] + 1, distance[i][j] + cost); // insertion -  deletion
				}
			}
		}
		double levensteinResult = distance[len1][len2];

		// normalize result:
		return ExportResult(levensteinResult, len1, len2, false);
	}

	/** 
	 The Wagner-Fischer distance between two strings is given by the minimum number of operations needed to transform one string into the other, 
	 where an operation is an insertion, deletion, or substitution of a single character.
	 
	 @param word1 First word
	 @param word2 Second word
	*/
	public static double WagnerFischer(String word1, String word2)
	{
		// this algorithm normally computes un-normalized distance between two string.
		int len1 = word1.length();
		int len2 = word2.length();
		double[][] distance = new double[len1 + 1][len2 + 1];

		for (int i = 0; i < len1 + 1; i++)
		{
			distance[i][0] = i;
		}

		for (int i = 0; i < len2 + 1; i++)
		{
			distance[0][i] = i;
		}


		double cost;
		for (int i = 0; i < len1; i++)
		{
			for (int j = 0; j < len2; j++)
			{
				cost = (word1.charAt(i) == word2.charAt(j)) ? 0 :.367;

				double transposeCost = 2;
				if (j > 0 && i > 0)
				{
					if (word1.charAt(i) == word2.charAt(j - 1) && word1.charAt(i - 1) == word2.charAt(j))
					{
						transposeCost = .733;
					}

					distance[i + 1][j + 1] = Minimum(distance[i][j + 1] + .569, distance[i + 1][j] + .679, distance[i][j] + cost, distance[i - 1][j - 1] + transposeCost); // substitution -  insertion -  deletion

				}
				else
				{
					distance[i + 1][j + 1] = Minimum(distance[i][j + 1] + .8, distance[i + 1][j] + .9, distance[i][j] + cost); // insertion -  deletion
				}
			}
		}
		double levensteinResult = distance[len1][len2];

		// normalize result:
		return ExportResult(levensteinResult, len1, len2, false);
	}

	/** 
	 The Levenshtein distance between two strings is given by the minimum number of operations needed to transform one string into the other, 
	 where an operation is an insertion, deletion, or substitution of a single character.
	 ref: http://en.wikipedia.org/wiki/Levenshtein_distance
	 
	 @param word1 First word
	 @param word2 Second word
	 @return Not normalized result obtain here
	*/
//ORIGINAL LINE: public static unsafe double GNULevenstein(string word1, string word2)
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to the 'unsafe' modifier in Java:
	public static double GNULevenstein(String word1, String word2)
	{
		return Levenstein(word1, word2);
	}

	/** 
	 The Jaro distance metric takes into account typical spelling deviations, this work comes from the following paper.
	 Jaro, M. A. 1989 "Advances in record linking methodology as applied to the 1985 census of Tampa Florida". Journal of the American Statistical Society 64:1183-1210 
	 Briefly, for two strings s and t, let s' be the characters in s that are "common with" t, and let t' be the charcaters in t that are "common with" s; 
	 roughly speaking, a character a in s is "in common" with t if the same character a appears in about the place in t.
	 Note that Jaro result is Normalized. 1 means maximum similarity, 0 means maximum difference.
	 ref:  http://en.wikipedia.org/wiki/Jaro-Winkler_distance
	 
	 @param word1 First word
	 @param word2 Second word
	*/
	public static double JaroWinckler(String word1, String word2)
	{
		// this algorithm normally computes normalized similarity between two string.
		int len1 = word1.length();
		int len2 = word2.length();

		//get half the length of the string rounded up - (this is the distance used for acceptable transpositions)
		int halflen = ((Math.min(len1, len2)) / 2) + ((Math.min(len1, len2)) % 2);

		String common1 = GetCommonCharactersForJaroMethod(word1, word2, halflen);
		String common2 = GetCommonCharactersForJaroMethod(word2, word1, halflen);

		//check for zero in common (no similarity)
		if (common1.length() == 0 || common2.length() == 0)
		{
			return 0.0;
		}

		//check for same length common strings returning 0.0f is not the same
		if (common1.length() != common2.length())
		{
			return 0.0;
		}

		//get the number of transpositions
		double transpositions = 0;
		for (int i = 0; i < common1.length(); i++)
		{
			if (common1.charAt(i) != common2.charAt(i))
			{
				transpositions++;
			}
		}
		transpositions /= 2.0;

		double m = common1.length();

		//calculate jaro metric
		double similarity = (m / len1 + m / len2 + (m - transpositions) / m) / 3.0;

		return ExportResult(similarity, true);
	}

	/** 
	 compute number of common characters and transpositions:
	 
	 @param s1 first string
	 @param s2 second string
	 @param windowLen windows length
	 @return common characters string
	*/
	private static String GetCommonCharactersForJaroMethod(String s1, String s2, int windowLen)
	{
		//int windowLen = Math.Min (3 , ((int)( Math.Max(len1 , len2)/2.0))-1); // window len = floor[max(l1,l2)/2]-1 OR  3
		String commonString = "";

		// create a copy of string 2:
		String copy = s2;

		for (int i = 0; i < s1.length(); i++)
		{
			char ch = s1.charAt(i);

			//set boolean for quick loop exit if found
			boolean foundIt = false;

			//compare char with range of characters to either side
			for (int j = Math.max(0, i - windowLen); !foundIt && j <= Math.min(i + windowLen, s2.length() - 1); j++)
			{
				//check if found
				if (copy.charAt(j) == ch)
				{
					foundIt = true;

					//append character found
					commonString = commonString + ch;

					//alter copied s2 for processing
					copy = (copy.substring(0, j) + StringExtensions.Insert(copy.substring(j + 1),j, "-"));
				}
			}
		}
		return commonString;
	}

	/** 
	 TThis approach is known by various names, Needleman-Wunch, Needleman-Wunch-Sellers, Sellers and the Improving Sellers algorithm. 
	 This is similar to the basic edit distance metric, Levenshtein distance, this adds an variable cost adjustment to the cost of a gap, i.e. insert/deletion, in the distance metric. 
	 So the Levenshtein distance can simply be seen as the Needleman-Wunch distance with G=1.
	 Where G = "gap cost" and SubstitutionCost is again an arbitrary distance function on characters (e.g. related to typographic frequencies, amino acid substitutibility, etc). 
	 ref:  http://www.dcs.shef.ac.uk/~sam/stringmetrics.html#needleman
	 
	 @param word1 First word
	 @param word2 Second word
	 @param needlemanConfig NeedlemanConfig
	 @return  Normalized similarity between [0..1]. 0 means minimum similarity and 1 means maxumim smilarity
	*/
	public static double NeedlemanWunch(String word1, String word2, NeedlemanConfig needlemanConfig)
	{
		// this algorithm normally computes normalized similarity between two string.
		double needlemanWunch = GetUnNormalisedSimilarityForNeedleman(word1, word2, needlemanConfig);

		//normalize into zero to one region from min max possible
		double maxValue = Math.max(word1.length(), word1.length());

		if (needlemanConfig.getNeedlemanMaxSubstituteRange() > needlemanConfig.getGapCost())
		{
			maxValue *= needlemanConfig.getNeedlemanMaxSubstituteRange();
		}
		else
		{
			maxValue *= needlemanConfig.getGapCost();
		}

		//check for 0 maxLen
		if (maxValue == 0)
		{
			return ExportResult(1.0, true); //as both strings identically zero length
		}
		else
		{
			return ExportResult(needlemanWunch / maxValue, false);
		}

	}
	private static double ComputeNeedlemanSubstitutionCost(char p1, char p2, NeedlemanConfig needlemanConfig)
	{
		// we can use normalized euclidean distance between characters of keyboard here.
		// but cost of insertion and deletion errors should be specified according to substitution. 

		// euclidean distance is normalized between [0..1].
		return needlemanConfig.getNeedlemanMaxSubstituteRange() * needlemanConfig.getKeyboard().NormalizedEuclideanDistance(p1, p2);
	}
	private static double GetUnNormalisedSimilarityForNeedleman(String word1, String word2, NeedlemanConfig needlemanConfig)
	{
		// this algorithm normally computes un-normalized distance between two string.
		int len1 = word1.length();
		int len2 = word2.length();
		double[][] distance = new double[len1 + 1][len2 + 1];

		for (int i = 0; i < len1 + 1; i++)
		{
			distance[i][0] = i;
		}

		for (int i = 0; i < len2 + 1; i++)
		{
			distance[0][i] = i;
		}

		double substitutionCost;
		double gapCost = needlemanConfig.getGapCost();

		for (int i = 0; i < len1; i++)
		{
			for (int j = 0; j < len2; j++)
			{
				substitutionCost = ComputeNeedlemanSubstitutionCost(word1.charAt(i), word2.charAt(j), needlemanConfig);

				distance[i + 1][j + 1] = Minimum(distance[i][j + 1] + gapCost, distance[i + 1][j] + gapCost, distance[i][j] + substitutionCost); // substitution -  insertion -  deletion
			}
		}
		double result = (float)distance[len1][len2];

		// to normalized this result you should use Needleman normalization function directly, 
		// because normal normalization functions do not work to normalize this result!

		return result;
	}

	/** 
	 This is a New Perisan String Distance Metric Based on Needleman and Levenstein Similarity Metric.
	 
	 @param word1 First word
	 @param word2 Second word
	*/

	/** @param word1 First word
	 @param word2 Second word
	 @param kashefiConfig KashefiConfig
	 @return  Normalized similarity between [0..1]. 0 means minimum similarity and 1 means maxumim smilarity
	*/
	public static double KashefiMeasure(String word1, String word2, KashefiConfig kashefiConfig)
	{
		// this algorithm normally computes normalized similarity between two string.
		double kashefiMeasure = GetUnNormalisedSimilarityForKashefiMeasure(word1, word2, kashefiConfig);

		//normalize into zero to one region from min max possible

		double maxValue = Math.min(kashefiConfig.getSubstituteGapCost(), kashefiConfig.getTranspositionGapCost()) * Math.min(word1.length(), word2.length());
		maxValue += Math.max(kashefiConfig.getInsertGapCost(), kashefiConfig.getDeleteGapCost()) * (Math.max(word1.length(), word2.length()) - Math.min(word1.length(), word2.length()));

		//check for 0 maxLen
		if (maxValue == 0)
		{
			return ExportResult(1.0, true); //as both strings identically zero length
		}
		else
		{
			//return ExportResult(kashefiMeasure / maxValue, false);
			return ExportResult(kashefiMeasure / Math.max(word1.length(), word2.length()), false);
		}

	}
	private static double GetUnNormalisedSimilarityForKashefiMeasure(String word1, String word2, KashefiConfig kashefiConfig)
	{
		// this algorithm normally computes un-normalized distance between two string.
		int len1 = word1.length();
		int len2 = word2.length();
		double[][] distance = new double[len1 + 1][len2 + 1];

		for (int i = 0; i < len1 + 1; i++)
		{
			distance[i][0] = i;
		}

		for (int i = 0; i < len2 + 1; i++)
		{
			distance[0][i] = i;
		}

		double substitutionCost = 0;
		double insertGapCost = kashefiConfig.getInsertGapCost();
		double deleteGapCost = kashefiConfig.getDeleteGapCost();
		double transposeCost;

		for (int i = 0; i < len1; i++)
		{
			for (int j = 0; j < len2; j++)
			{
				substitutionCost = 0;

				if (i == j)
				{
					substitutionCost = ComputeKashefiSubstitutionCost(word1.charAt(i), word2.charAt(j), kashefiConfig);
				}

				if ((j > 0 && i > 0))
				{
					transposeCost = ComputeKashefiTranspositionCost(word1, i, word2, j, kashefiConfig);

					insertGapCost = ComputeKashefiInsertionCost(word1, i, word2, j, kashefiConfig);

					distance[i + 1][j + 1] = Minimum(distance[i][j + 1] + deleteGapCost, distance[i + 1][j] + insertGapCost, distance[i][j] + substitutionCost, distance[i - 1][j - 1] + transposeCost); // substitution -  insertion -  deletion

				}
				else
				{
					distance[i + 1][j + 1] = Minimum(distance[i][j + 1] + deleteGapCost, distance[i + 1][j] + insertGapCost, distance[i][j] + substitutionCost); // insertion -  deletion
				}
			}
		}
		double result = (float)distance[len1][len2];

		// to normalized this result you should use Needleman normalization function directly, 
		// because normal normalization functions do not work to normalize this result!

		return result;
	}

	private static double ComputeKashefiTranspositionCost(String word1, int i, String word2, int j, KashefiConfig kashefiConfig)
	{
		double transpositionCost = 1;
		if (word1.charAt(i) == word2.charAt(j - 1) && word1.charAt(i - 1) == word2.charAt(j))
		{
			transpositionCost = kashefiConfig.getTranspositionGapCost();
		}

		return transpositionCost;
	}
	private static double ComputeKashefiInsertionCost(String word1, int i, String word2, int j, KashefiConfig kashefiConfig)
	{
		double insertionCost = kashefiConfig.getInsertGapCost();
		if (word1.charAt(i) == word2.charAt(j - 1))
		{
			if (StringExtensions.IsIn(word2.substring(j, j + 1),StringExtensions.ToStringArray(PersianAlphabets.PseudoPersianAlphabet)))
			{
				insertionCost = .1;
			}
		}

		return insertionCost;
	}
	private static double ComputeKashefiSubstitutionCost(char p1, char p2, KashefiConfig kashefiConfig)
	{
		if (p1 == p2)
		{
			return 0;
		}

		if (PersianHomophoneLetters.AreHomophone(p1, p2))
		{
			return 0.236;
		}

		if (PersianHomoshapeLetters.AreHomoshape(p1, p2))
		{
			return 0.369;
		}

		double substitutionCost = kashefiConfig.getKeyboard().NormalizedEuclideanDistance(p1, p2);

		substitutionCost = substitutionCost * (1 - kashefiConfig.getSubstituteGapCost()) + kashefiConfig.getSubstituteGapCost();

		return substitutionCost;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private utilities

	private static int Minimum(int a, int b, int c)
	{
		int min = Math.min(a, b);
		min = Math.min(min, c);

		//min = a;
		//if (b < min) min = b;
		//if (c < min) min = c;

		return min;
	}
	private static int Minimum(int a, int b, int c, int d)
	{
		int min = Math.min(a, b);
		min = Math.min(min, c);
		min = Math.min(min, d);

		//min = a;
		//if (b < min) min = b;
		//if (c < min) min = c;

		return min;
	}
	private static double Minimum(double a, double b, double c)
	{
		double min = Math.min(a, b);
		min = Math.min(min, c);

		//min = a;
		//if (b < min) min = b;
		//if (c < min) min = c;

		return min;
	}
	private static double Minimum(double a, double b, double c, double d)
	{
		double min = Math.min(a, b);
		min = Math.min(min, c);
		min = Math.min(min, d);

		//min = a;
		//if (b < min) min = b;
		//if (c < min) min = c;

		return min;
	}

	private static int Maximum(int a, int b, int c)
	{
		int max = Math.max(a, b);
		max = Math.max(max, c);

		return max;
	}
	private static double Maximum(double a, double b, double c)
	{
		double max = Math.max(a, b);
		max = Math.max(max, c);

		return max;
	}

	private static double ConvertSimilarityToDistance(double normalizedSimilarity)
	{
		return 1 - normalizedSimilarity;
	}

	private static double ConvertDistanceToSimilarity(double normalizedDistance)
	{
		return 1 - normalizedDistance;
	}

	private static double ExportResult(double currentValue, int word1Len, int word2Len, boolean inputExplainesSimilarity)
	{
		if (getExportResultAsSimilarity())
		{
			// we should export results as similarity metric:
			if (inputExplainesSimilarity)
			{
				return currentValue / GetMaxLength(word1Len, word2Len);
			}
			else
			{
				return ConvertDistanceToSimilarity(currentValue / GetMaxLength(word1Len, word2Len));
			}
		}
		else
		{
			// we should export results as distance:
			if (inputExplainesSimilarity)
			{
				return ConvertSimilarityToDistance(currentValue / GetMaxLength(word1Len, word2Len));
			}
			else
			{
				return currentValue / GetMaxLength(word1Len, word2Len);
			}
		}
	}

	private static double ExportResult(double currentValue, boolean inputExplainesSimilarity)
	{
		if (getExportResultAsSimilarity())
		{
			// we should export results as similarity metric:
			if (inputExplainesSimilarity)
			{
				return currentValue;
			}
			else
			{
				return ConvertDistanceToSimilarity(currentValue);
			}
		}
		else
		{
			// we should export results as distance:
			if (inputExplainesSimilarity)
			{
				return ConvertSimilarityToDistance(currentValue);
			}
			else
			{
				return currentValue;
			}
		}
	}

	private static int GetMaxLength(int word1Len, int word2Len)
	{
		return (word1Len < word2Len) ? word2Len : word1Len;
	}

	private static int GetMaxLength(String word1, String word2)
	{
		return (word1.length() < word2.length()) ? word2.length() : word1.length();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
} // class