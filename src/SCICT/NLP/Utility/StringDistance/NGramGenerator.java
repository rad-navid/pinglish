package SCICT.NLP.Utility.StringDistance;

import Helper.StringExtensions;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.*;

// these functions can insert in Edit Distance class too, but we may need them in other context and other conditions.
// so we decide to create a class here.
public class NGramGenerator
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	// Note: these parameters should be set by initiating the class
	private int nGram;
	public final int getNGram()
	{
		return nGram;
	}
	public final void setNGram(int value)
	{
		nGram = value;
	}

	private NGramScopes nGramScope = NGramScopes.values()[0];
	public final NGramScopes getNGramScope()
	{
		return nGramScope;
	}
	public final void setNGramScope(NGramScopes value)
	{
		nGramScope = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region  Constructors

	public NGramGenerator()
	{
		nGram = 3;
		nGramScope = NGramScopes.HybridLevel;
	}

	public NGramGenerator(int _nGram, NGramScopes _nGramScopes)
	{
		nGram = _nGram;
		nGramScope = _nGramScopes;
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public members

	/** 
	  N-Gram generator according to Constructor's default setting... 
	 
	 @param _aString a sentense or a word.
	 @return list of generated n-gram according to input scope.
	*/
	public final java.util.ArrayList<String> Generate(String _aString)
	{
		return Generate(getNGram(), getNGramScope(), _aString);
	}

	/** 
	 General N-Gram generator... 
	 
	 @param _nGramScopes Gram (Example: 3).
	 @param _aString a sentense or a word.
	 @return list of generated n-gram according to input scope.
	*/
	public final java.util.ArrayList<String> Generate(NGramScopes _nGramScopes, String _aString)
	{
		return Generate(getNGram(), _nGramScopes, _aString);
	}

	/** 
	 General N-Gram generator... 
	 
	 @param _gram Gram (Example: 3).
	 @param _nGramScope Scope for partitioning.
	 @param _inputString a sentense or a word.
	 @return list of generated n-gram according to input scope.
	*/
	public final java.util.ArrayList<String> Generate(int _gram, NGramScopes _nGramScope, String _inputString)
	{
		// all public methods use this to call other n-gram generating functions.
		if (_nGramScope == NGramScopes.HybridLevel)
		{
			return HybridNGram(_gram, _inputString);
		}

		if (_nGramScope == NGramScopes.IntraWordLevel)
		{
			return IntraWordNGram(_gram, _inputString);
		}

		if (_nGramScope == NGramScopes.WordLevel)
		{
			return WordNGram(_gram, _inputString);
		}

		if (_nGramScope == NGramScopes.CleanWordLevel)
		{
			return CleanWordNGram(_gram, _inputString);
		}

		return null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private functions

	private java.util.ArrayList<String> CleanWordNGram(int _gram, String _inputString)
	{
		// step 1: read word separators form a CommonPersian source           
		String separators = PersianAlphabets.Delimiters;
		String[] splitList;

		// step 2: extract all words:
		splitList = StringExtensions.SplitRemoveEmptyElements(_inputString,separators);

		// step 3: generate all n-gram candidates:
		java.util.ArrayList<String> tokens = new java.util.ArrayList<String>();
		int total = splitList.length - _gram;
		String temp = "";

		for (int i = 0; i <= total; i++)
		{
			temp = "";
			for (int j = i; j < _gram + i; j++)
			{
				temp = temp + splitList[j] + " ";
			}
			tokens.add(temp.trim());
		}

		return tokens;
	}

	private java.util.ArrayList<String> WordNGram(int _gram, String _inputString)
	{
		// step 1: read word separators form a CommonPersian source           
		String separators = PersianAlphabets.Delimiters;
		java.util.ArrayList<String> separatorCharacters = new java.util.ArrayList<String>();
		String currentSeparator = "";
		int lastIndex = 0, startIndex = 0;
		java.util.ArrayList<String> splitedString = new java.util.ArrayList<String>();
		//string[] splitList;

		// step 2: extract all words:
		for (int i = 0; i < _inputString.length(); i++)
		{
			if (separators.indexOf(_inputString.charAt(i)) >= 0)
			{
				// separator found:
				startIndex = i;
				currentSeparator = _inputString.charAt(i)+"";
				while ((i < _inputString.length()) && (separators.indexOf(_inputString.charAt(i)) >= 0))
				{
					currentSeparator += _inputString.charAt(i)+"";
					i++;
				}

				separatorCharacters.add(currentSeparator);
				splitedString.add(_inputString.substring(lastIndex, startIndex));

				lastIndex = i;
			}
		}
	   // splitList = _inputString.Split(separators.ToCharArray(), StringSplitOptions.RemoveEmptyEntries);

		// step 3: generate all n-gram candidates:
		java.util.ArrayList<String> tokens = new java.util.ArrayList<String>();
		int total = splitedString.size() - _gram;
		String temp = "";

		for (int i = 0; i <= total; i++)
		{
			temp = "";
			for (int j = i; j < _gram + i; j++)
			{
				temp = temp + splitedString.get(j) + separatorCharacters.get(j);
			}
			tokens.add(temp);
		}

		return tokens;
	}

	/** 
	 Extracts all n-grams from input sentense regarding intera-word level partitioning.
	 it means that if you enter "Hello John", 3gram results are {"Hel", "ell", "llo", "joh", "ohn"}
	 
	 @param _gram
	 @param _aSentense
	 @return 
	*/
	private java.util.ArrayList<String> IntraWordNGram(int _gram, String _inputWord)
	{
		// step 1: read word separators form a CommonPersian source           
		char[] separators = PersianAlphabets.Delimiters.toCharArray();
		String[] splitList;

		// step 2: extract all words:
		splitList =StringExtensions.SplitRemoveEmptyElements(_inputWord,separators.toString());

		int total = splitList.length;
		java.util.ArrayList<String> tokens = new java.util.ArrayList<String>();

		// for each word, extract n-grams separately.
		for (int i = 0; i < splitList.length; i++)
		{
			tokens.addAll(HybridNGram(_gram, splitList[i].trim()));
		}

		return tokens;
	}

	private java.util.ArrayList<String> HybridNGram(int _gram, String _inputString)
	{
		int total = _inputString.length() - _gram;
		java.util.ArrayList<String> tokens = new java.util.ArrayList<String>();

		for (int i = 0; i <= total; i++)
		{
			tokens.add(_inputString.substring(i, i + _gram));
		}

		return tokens;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}