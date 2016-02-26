package SCICT.NLP.Utility.StringDistance;


/**
 String Distance Class
*/
public class StringDistanceLayout
{
	/**
	 Get String Distance
	
	@param word1 First Word
	@param word2 Second Word
	@param algorithm String Distance Algorithm
	@return String Distance
	*/
	public final double GetStringDistance(String word1, String word2, StringDistanceAlgorithm algorithm)
	{
		return GetDifference(word1, word2, algorithm, false, new KashefiConfig());
	}
	/**
	 Get String Distance
	
	@param word1 First Word
	@param word2 Second Word
	@param algorithm String Distance Algorithm
	@param kashefiConfig Configuration of Kashefi's String Distance Method
	@return String Distance
	*/
	public final double GetStringDistance(String word1, String word2, StringDistanceAlgorithm algorithm, KashefiConfig kashefiConfig)
	{
		return GetDifference(word1, word2, algorithm, false, kashefiConfig);
	}
	/**
	 Get Similarity Score
	
	@param word1 First Word
	@param word2 Second Word
	@param algorithm String Distance Algorithm
	@return Similarity Score
	*/
	public final double GetWordSimilarity(String word1, String word2, StringDistanceAlgorithm algorithm)
	{
		return GetDifference(word1, word2, algorithm, true, new KashefiConfig());
	}
	/**
	 Get Similarity Score
	
	@param word1 First Word
	@param word2 Second Word
	@param algorithm String Distance Algorithm
	@param kashefiConfig Configuration of Kashefi's String Distance Method
	@return Similarity Score
	*/
	public final double GetWordSimilarity(String word1, String word2, StringDistanceAlgorithm algorithm, KashefiConfig kashefiConfig)
	{
		return GetDifference(word1, word2, algorithm, true, kashefiConfig);
	}

	private static double GetDifference(String word1, String word2, StringDistanceAlgorithm algorithm, boolean exportAsSimilarity, KashefiConfig kashefiConfig)
	{
		double distance = -1;
		StringDistanceAlgorithms.setExportResultAsSimilarity(exportAsSimilarity);

		if (algorithm == StringDistanceAlgorithm.Hamming)
		{
			distance = StringDistanceAlgorithms.Hamming(word1, word2);
		}
		else if (algorithm == StringDistanceAlgorithm.Levenestain)
		{
			distance = StringDistanceAlgorithms.Levenstein(word1, word2);
		}
		else if (algorithm == StringDistanceAlgorithm.GNULevenesain)
		{
			distance = StringDistanceAlgorithms.GNULevenstein(word1, word2);
		}
		else if (algorithm == StringDistanceAlgorithm.Kashefi)
		{
			distance = StringDistanceAlgorithms.KashefiMeasure(word1, word2, kashefiConfig);
		}
		else if (algorithm == StringDistanceAlgorithm.Needleman)
		{
			double nGapCost = (kashefiConfig.getDeleteGapCost() + kashefiConfig.getInsertGapCost()) / 2.0;
			NeedlemanConfig nc = new NeedlemanConfig(kashefiConfig.getKeyboard(), nGapCost, kashefiConfig.getSubstituteGapCost());

			distance = StringDistanceAlgorithms.NeedlemanWunch(word1, word2, nc);
		}
		else if (algorithm == StringDistanceAlgorithm.JaroWinkler)
		{
			distance = StringDistanceAlgorithms.JaroWinckler(word1, word2);
		}
		else if (algorithm == StringDistanceAlgorithm.Cosine)
		{
			distance = StringDistanceAlgorithms.Cosine(word1, word2);
		}
		else if (algorithm == StringDistanceAlgorithm.DamerauLevenestain)
		{
			distance = StringDistanceAlgorithms.DamerauLevenstein(word1, word2);
		}
		else if (algorithm == StringDistanceAlgorithm.WagnerFischer)
		{
			distance = StringDistanceAlgorithms.WagnerFischer(word1, word2);
		}

		return distance;
	}

}
