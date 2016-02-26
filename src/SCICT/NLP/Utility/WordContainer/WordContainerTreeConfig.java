package SCICT.NLP.Utility.WordContainer;

import SCICT.NLP.Persian.Constants.*;
import SCICT.Utility.IO.*;
import SCICT.Utility.*;
import SCICT.NLP.Utility.*;

/**
 Word container tree data structure's configuration
*/
public class WordContainerTreeConfig
{
	/**
	 The absolute path of dictionary
	*/
	public String DictionaryFileName;

	/** 
	 Number of returned words, 0 for all
	*/
	public long SuggestionCount;
}