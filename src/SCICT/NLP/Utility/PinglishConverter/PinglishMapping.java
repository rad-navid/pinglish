package SCICT.NLP.Utility.PinglishConverter;

import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.procedure.TObjectIntProcedure;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.integer;

import Helper.LinqSimulationArrayList;
import Helper.LinqSimulationHashMap;
import Helper.ValueThenKeyComparator;
import SCICT.NLP.Persian.Constants.PersianAlphabets;
import SCICT.NLP.Persian.Constants.PseudoSpace;

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




//C# TO JAVA CONVERTER NOTE: There is no Java equivalent to C# namespace aliases:
//using KeyValueList = Dictionary<string, int>;

/** 
 Instance of this class will learns the mappings from sample dataset.
*/
public class PinglishMapping implements Serializable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Members

	/**
	 * 
	 */
	private static final long serialVersionUID = 5209101112066851932L;

	private final MappingSequence m_mappingSequences = new MappingSequence();

	private LinqSimulationArrayList<PinglishString> m_pinglishDataSet = new LinqSimulationArrayList<PinglishString>(4670);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctors
	public PinglishMapping()
	{

	}

	public PinglishMapping(InputStream inputStream)
	{
		try
		{
			LinqSimulationArrayList<PinglishString> list = PinglishConverterUtils.LoadPinglishStrings(inputStream);
			Learn(list, false);
			m_pinglishDataSet.addAll(list.Distinct(new PinglishString(),new PinglishStringEqualityComparer()));
		}
		catch (RuntimeException ex)
		{
			System.out.println(ex);
		}
	}

	public final void Learn(LinqSimulationArrayList<PinglishString> listOfWords, boolean appendToInternalDataset)
	{
		for (PinglishString word : listOfWords)
		{
			Learn(word, false);
		}

		if (appendToInternalDataset)
		{
			m_pinglishDataSet = PinglishConverterUtils.MergePinglishStringLists(m_pinglishDataSet, listOfWords, PinglishStringNormalizationOptions.NoDuplicatesEntries);
		}
	}

	public final void Learn(PinglishString word, boolean appendToInternalDataset)
	{
		//for (int prefixGram = 2; prefixGram >= 0; prefixGram--)
		for (int prefixGram = 1; prefixGram >= 0; prefixGram--)
		{
			//for (int postfixGram = 5; postfixGram >= 0; postfixGram--)
			for (int postfixGram = 2; postfixGram >= 0; postfixGram--)
			{
				m_mappingSequences.LearnWordMapping(word, prefixGram, postfixGram);
			}
		}

		if (appendToInternalDataset && !m_pinglishDataSet.contains(word))
		{
			m_pinglishDataSet.add(word);
		}
	}

	/** 
	 
	 
	 @param pinglishWord
	 @return 
	*/
	public final LinqSimulationArrayList<PinglishString> SuggestWords(String pinglishWord, boolean removeDuplicates)
	{
		LinqSimulationArrayList<PinglishString> words = SuggestByExactSearchInDataset(pinglishWord);

		if (words.size() > 0)
		{
			return words;
		}

		int len = pinglishWord.length();

		words.add(new PinglishString());

		LinqSimulationArrayList<String> charSuggs = new LinqSimulationArrayList<String>();

		for (int index = 0; index < len; ++index)
		{
			charSuggs.clear();

				///#region Dist-5

			charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 0, 5);
			charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 1, 4);
			charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 2, 3);
			charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 3, 2);
			//charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 4, 1);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Dist-4

			//if (charSuggs.Count == 0)
			{
				charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 0, 4);
				charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 1, 3);
				//charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 2, 2);
				//charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 3, 1);
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Dist-3

			if (charSuggs.isEmpty())
			{
				charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 0, 3);
			}
			if (charSuggs.isEmpty())
			{
				charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 1, 2);
			}
			if (charSuggs.isEmpty())
			{
				charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 2, 1);
				//charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 3, 0);
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Dist-2

			if (charSuggs.isEmpty())
			{
				charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 0, 2);
			}
			if (charSuggs.isEmpty())
			{
				charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 1, 1);
				//charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 2, 0);
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Dist-1

			if (charSuggs.isEmpty())
			{
				charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 0, 1);
				charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 1, 0);
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Dist-0

			if (charSuggs.isEmpty())
			{
				charSuggs = GetCharSuggs(m_mappingSequences, pinglishWord, index, charSuggs, 0, 0);
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Heuristical techniques to improve results

			// No Erabs at the begining of the word
			if (index == 0)
			{
				for (int i = charSuggs.size() - 1; i >= 0; i--)
				{
					if (PersianAlphabets.Erabs.contains(charSuggs.get(i)))
					{
						charSuggs.remove(i);
					}
				}
			}

			// No Pseudo-space at the end of the word
			if (index == len - 1)
			{
				for (int i = charSuggs.size() - 1; i >= 0; i--)
				{
					int endIndex = charSuggs.get(i).length() - 1;
					if (endIndex >= 0 && charSuggs.get(i).charAt(endIndex) == PseudoSpace.ZWNJ)
					{
						charSuggs.remove(i);
					}
				}
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

			if (charSuggs.isEmpty())
			{
				// TODO: Generate every possible mapping
				Character map = SingleValueCharMappings.TryGetValue(pinglishWord.charAt(index));
				if (map != null)
				{
					charSuggs.add(map.toString());
				}
				else
				{
					// TODO
					//throw new Exception();
				}
			}

			LinqSimulationArrayList.Update(words,pinglishWord.charAt(index), charSuggs);
		}

		if (removeDuplicates)
		{

			return words.Distinct(new PinglishString(),new PinglishStringEqualityComparer());
		}
		else
		{
			return words;
		}
	}

	public final Iterable<PinglishString> getDataSet()
	{
		return m_pinglishDataSet;
	}

	private LinqSimulationArrayList<PinglishString> SuggestByExactSearchInDataset(String pinglishWord)
	{
		LinqSimulationArrayList<PinglishString> words = new LinqSimulationArrayList<PinglishString>();

		
		for (PinglishString pinglishSample : m_pinglishDataSet)
		{
			if (pinglishWord.equalsIgnoreCase(pinglishSample.getEnglishString()))
			{
				words.add(pinglishSample);
			}
		}

		return words;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Static

	public static PinglishMapping LoadConverterEngine(String fileName)
	{	
		PinglishMapping obj=null;
		 try{
		      //use buffering
		      InputStream file = new FileInputStream( fileName );
		      InputStream buffer = new BufferedInputStream( file );
		      ObjectInput input = new ObjectInputStream ( buffer );
		      try{
		        
		    	   obj = (PinglishMapping)input.readObject();
		        
		      }
		      finally{
		        input.close();
		      }
		    }
		    catch(ClassNotFoundException ex){
		     ex.printStackTrace();
		    }
		    catch(IOException ex){
		      ex.printStackTrace();
		    }
		  return obj;
	}

	public static void SaveConverterEngine(String fileName, PinglishMapping pinglishMapping)
	{
		  try{
		      //use buffering
		      OutputStream file = new FileOutputStream(fileName );
		      OutputStream buffer = new BufferedOutputStream( file );
		      ObjectOutput output = new ObjectOutputStream( buffer );
		      try{
		        output.writeObject(pinglishMapping);
		      }
		      finally{
		        output.close();
		      }
		    }  
		    catch(IOException ex){
		      ex.printStackTrace();
		    }
		
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	private static LinqSimulationArrayList<String> GetCharSuggs(MappingSequence mappingSequences, String pinglishWord, int index, LinqSimulationArrayList<String> charSuggs, int prefixGram, int postfixGram)
	{
		LinqSimulationArrayList<String> tmpSuggs = GetSuggsForKey(mappingSequences, pinglishWord, index, prefixGram, postfixGram);
		charSuggs = Union(charSuggs, tmpSuggs);
		return charSuggs;
	}

	private static LinqSimulationArrayList<String> GetSuggsForKey(MappingSequence mappingSequences, String pinglishWord, int index, int prefixGram, int postfixGram)
	{
		String prefix = MappingSequence.GetPrefixForIndex(pinglishWord, index, prefixGram);
		if (prefix.length() != prefixGram)
		{
			return new LinqSimulationArrayList<String>();
		}

		String postfix = MappingSequence.GetPostfixForIndex(pinglishWord, index, postfixGram);
		if (postfix.length() != postfixGram)
		{
			return new LinqSimulationArrayList<String>();
		}

		char ch = pinglishWord.charAt(index);

		LinqSimulationArrayList<String> charSuggs = null;
		try
		{
			// TODO: If Key does not exist ?!
			TObjectIntHashMap<String> listOfAllSuggs = mappingSequences.getItem(ch, prefix, postfix);

			if (listOfAllSuggs != null)
			{
				final List<Map.Entry<String, Integer>> list=new  ArrayList<Map.Entry<String, Integer>>();
				listOfAllSuggs.forEachEntry(new TObjectIntProcedure<String>() {

					@Override
					public boolean execute(String arg0, int arg1) {
						return list.add(new ListEntry(arg0, arg1));
					}
				});
			
					LinqSimulationArrayList<String>query=new LinqSimulationArrayList<String>();
				
					
					
					Collections.sort(list, new ValueThenKeyComparator<String, Integer>());
				for(java.util.Map.Entry<String, Integer> pair : list)
				{
					query.add(pair.getKey());
				}
					charSuggs = query;
			}
			else
			{
				charSuggs = new LinqSimulationArrayList<String>();
			}
		}
		catch (Exception ex)
		{
		 ex.printStackTrace();
		}

		return charSuggs;
	}


	/** 
	 
	 
	 Affects the first parameter
	 @return 
	*/
	private static LinqSimulationArrayList<String> Union(LinqSimulationArrayList<String> firstList, LinqSimulationArrayList<String> secondList)
	{
		if (secondList == null)
		{
			return firstList;
		}

		for (String str : secondList)
		{
			if(!firstList.contains(str))
				firstList.add(str);
		}
		return firstList;
	}

	public PatternStorage get_patternStorage() {
		return this.m_mappingSequences.get_patternStorage();
	}
	public void set_patternStorage(PatternStorage pattern) {
		this.m_mappingSequences.set_patternStorage(pattern);
	}
	public void updateDataSet(LinqSimulationArrayList<PinglishString> listOfWords)
	{
		m_pinglishDataSet = listOfWords;//PinglishConverterUtils.MergePinglishStringLists_NewFaster(m_pinglishDataSet, listOfWords, PinglishStringNormalizationOptions.NoDuplicatesEntries);
	}
}
class ListEntry implements Entry<String, Integer>
{
	String key;
	Integer value;
	public ListEntry(String s,int i)
	{
		key=s;
		value=new Integer(i);
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public Integer getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public Integer setValue(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}