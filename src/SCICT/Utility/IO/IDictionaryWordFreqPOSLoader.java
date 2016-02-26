package SCICT.Utility.IO;

import SCICT.Utility.*;

/**
 Dictionary of words with usage frequency intreface
*/
public interface IDictionaryWordFreqPOSLoader
{
	boolean NextTerm(Helper.RefObject<String> word, Helper.RefObject<Integer> freq, Helper.RefObject<String> pos);
	boolean AddTerm(String word, int freq, String pos);
}