package SCICT.NLP.Utility.PinglishConverter;

import gnu.trove.map.hash.TCharObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.Serializable;

public class PatternStorage implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5894956429832458858L;
	//private LinqSimulationHashMap<Character, LetterPatternContainer> characters = new LinqSimulationHashMap<Character, LetterPatternContainer>();
	private TCharObjectHashMap<LetterPatternContainer>characters=new TCharObjectHashMap<LetterPatternContainer>();
	public final boolean AddOrUpdatePattern(char ch, String prefix, String postfix, String mapping)
	{
		LetterPatternContainer chTree;
		if (!characters.keySet().contains(ch))
		{
			chTree = new LetterPatternContainer(ch);
			characters.put(ch, chTree);
		}
		else
		{
			chTree = characters.get(ch);
		}

		chTree.AddPattern(prefix, postfix, mapping);
		return true;
	}

	public final boolean ContainsPattern(char ch, String prefix, String postfix)
	{
		TObjectIntHashMap<String> mapping = null;
		Helper.RefObject<TObjectIntHashMap<String>> tempRef_mapping = new Helper.RefObject<TObjectIntHashMap<String>>(mapping);
		boolean tempVar = ContainsPattern(ch, prefix, postfix, tempRef_mapping);
		mapping = tempRef_mapping.argvalue;
		return tempVar;
	}

	public final boolean ContainsPattern(char ch, String prefix, String postfix, Helper.RefObject<TObjectIntHashMap<String>> mapping)
	{
		mapping.argvalue = null;
		if (!characters.keySet().contains(ch))
		{
			return false;
		}
		return characters.get(ch).ContainsPattern(prefix, postfix, mapping);
	}

	public final TObjectIntHashMap<String> getItem(char ch, String prefix, String postfix)
	{
		TObjectIntHashMap<String> mapping = null;
		Helper.RefObject<TObjectIntHashMap<String>> tempRef_mapping = new Helper.RefObject<TObjectIntHashMap<String>>(mapping);
		boolean tempVar = ContainsPattern(ch, prefix, postfix, tempRef_mapping);
			mapping = tempRef_mapping.argvalue;
		if (tempVar)
		{
			return mapping;
		}

		return null;
	}
}