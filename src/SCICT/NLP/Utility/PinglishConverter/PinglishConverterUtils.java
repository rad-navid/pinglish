package SCICT.NLP.Utility.PinglishConverter;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

import android.util.Log;

import Helper.LinqSimulationArrayList;
import Helper.XmlSerializer;

/** 
 Generic methods used by other classes of this library.
*/
public final class PinglishConverterUtils
{
	/** 
	 Merges the two PinglishString lists. 
	 @return A reference to the merged list.
	*/
	public static LinqSimulationArrayList<PinglishString> MergePinglishStringLists(LinqSimulationArrayList<PinglishString> list1, LinqSimulationArrayList<PinglishString> list2, PinglishStringNormalizationOptions options)
	{
		if (list1 == null)
		{
			throw new IllegalArgumentException("list1");
		}

		if (list2 == null)
		{
			throw new IllegalArgumentException("list2");
		}

		// TODO: Implement removeErab
		boolean removeErab = options.Has(PinglishStringNormalizationOptions.NoErabPersianLetters);
		boolean noDuplicates = options.Has(PinglishStringNormalizationOptions.NoDuplicatesEntries);
		boolean lowerCase = options.Has(PinglishStringNormalizationOptions.LowercaseEnglishLetters);
		boolean sort = options.Has(PinglishStringNormalizationOptions.SortBasedOnEnglishLetters);


		LinqSimulationArrayList<PinglishString> lstResult = noDuplicates ? list1.Distinct(new PinglishString(),new PinglishStringEqualityComparer()) : new LinqSimulationArrayList<PinglishString>(list1);

		if (lowerCase)
		{
			lstResult = LinqSimulationArrayList.ToLower(lstResult);
		}

		// Now, lstResult is modified with noDuplicates and lowerCase options
		// Time to add list2 items

		PinglishString ps;
		for (int i = 0; i < list2.size(); ++i)
		{
			ps = list2.get(i);
			if (lowerCase)
			{
				ps = ps.ToLower();
			}

			if (noDuplicates)
			{
				if (!lstResult.contains(ps))
				{
					lstResult.add(ps);
				}
			}
			else
			{
				lstResult.add(ps);
			}
		}

		if (sort)
		{
			lstResult.Sort(lstResult.toArray(new PinglishString[0]),new PinglishStringEqualityComparer());
		}

		return lstResult;
	}
	
	public static LinqSimulationArrayList<PinglishString> MergePinglishStringLists_NewFaster(LinqSimulationArrayList<PinglishString> list1, LinqSimulationArrayList<PinglishString> list2, PinglishStringNormalizationOptions options)
	{
		if (list1 == null)
		{
			throw new IllegalArgumentException("list1");
		}

		if (list2 == null)
		{
			throw new IllegalArgumentException("list2");
		}

		// TODO: Implement removeErab
		boolean removeErab = options.Has(PinglishStringNormalizationOptions.NoErabPersianLetters);
		boolean noDuplicates = options.Has(PinglishStringNormalizationOptions.NoDuplicatesEntries);
		boolean lowerCase = options.Has(PinglishStringNormalizationOptions.LowercaseEnglishLetters);
		boolean sort = options.Has(PinglishStringNormalizationOptions.SortBasedOnEnglishLetters);


		LinqSimulationArrayList<PinglishString>result=new LinqSimulationArrayList<PinglishString>(list1.size()+list2.size());
		result.addAll(list1);
		result.addAll(list2);

		// Now, lstResult is modified with noDuplicates and lowerCase options
		// Time to add list2 items
			if (lowerCase)
			{
				LinqSimulationArrayList.ToLower(result);
			}

			if (noDuplicates)
			{
				
				result.Distinct(new PinglishString(),new PinglishStringEqualityComparer());
			}
			
		

		if (sort)
		{
			result.Sort(new PinglishString[0],new PinglishStringEqualityComparer());
		}

		return result;
	}

	public static LinqSimulationArrayList<PinglishString> MergePinglishStringLists(LinqSimulationArrayList<LinqSimulationArrayList<PinglishString>> lists, PinglishStringNormalizationOptions options)
	{
		LinqSimulationArrayList<PinglishString> result = new LinqSimulationArrayList<PinglishString>();
		if (lists.size() < 1)
		{
			return result;
		}

		for (int i = 0; i < lists.size(); i++)
		{
			result = MergePinglishStringLists(result, lists.get(i), options);
		}

		return result;
	}

	/** 
	 Loads a serialized list of PinglishString from a file.
	 Note: May throws Exception
	 
	 @param filePath
	 @return 
	*/
	public static LinqSimulationArrayList<PinglishString> LoadPinglishStrings(InputStream inputStream)
	{
		XmlSerializer serializer=new XmlSerializer();
		//serializer.Save(filePath);return null;
		//return serializer.Load("\\test.xml");
		try {
			//LinqSimulationArrayList<PinglishString>list=serializer.StandardLoad(inputStream);
			LinqSimulationArrayList<PinglishString>list=serializer.Load(inputStream);
			//serializer.Save(list);
			Log.d("list", list.size()+"");
			return list;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}

	/** 
	 Serialize a list of PinglishString into the given file.
	 
	 @return True if the operation was successful, and false otherwise.
	*/
	public static boolean SavePinglishStrings(LinqSimulationArrayList<PinglishString> list, String targetFile)
	{
//		TextWriter stream = null;
//		try
//		{
//			XmlSerializer serializer = new XmlSerializer(LinqSimulationArrayList<PinglishString>.class);
//			stream = new StreamWriter(File.Open(targetFile, FileMode.Create), Encoding.UTF8);
//			serializer.Serialize(stream, list);
//
//			return true;
//		}
//		catch (RuntimeException ex)
//		{
//			ex.printStackTrace();
//			return false;
//		}
//		finally
//		{
//			if (stream != null)
//			{
//				stream.Close();
//			}
//		}
		return true;
	}

	public static LinqSimulationArrayList<PinglishString> NormalizePinglishStrings(LinqSimulationArrayList<PinglishString> list, PinglishStringNormalizationOptions options)
	{
		if (options == PinglishStringNormalizationOptions.None)
		{
			return list;
		}

		LinqSimulationArrayList<PinglishString> results = list;
		if ((options.getValue() & PinglishStringNormalizationOptions.LowercaseEnglishLetters.getValue()) == PinglishStringNormalizationOptions.LowercaseEnglishLetters.getValue())
		{
			results = LinqSimulationArrayList.ToLower(results);
		}

		if ((options.getValue() & PinglishStringNormalizationOptions.NoDuplicatesEntries.getValue()) == PinglishStringNormalizationOptions.NoDuplicatesEntries.getValue())
		{
			results = results.Distinct(new PinglishString(),new PinglishStringEqualityComparer());
		}

		if ((options.getValue() & PinglishStringNormalizationOptions.NoErabPersianLetters.getValue()) == PinglishStringNormalizationOptions.NoErabPersianLetters.getValue())
		{
			throw new UnsupportedOperationException();
		}

		return results;
	}

	/** 
	 Each row of a preprocess file has more that 1 column, each column is separated by these characters.
	*/
	private static final char[] PreprocessElementInfoSeparators = new char[] {',', ' ', '\t', ';'};

	/** 
	 Loads Pinglish preprocess elements from a file.
	*/
	public static LinqSimulationArrayList<PreprocessElementInfo> LoadPreprocessElements(String filePath)
	{
		String[] elementWithInfos;

		LinqSimulationArrayList<PreprocessElementInfo> preprocessElementInfos = new LinqSimulationArrayList<PreprocessElementInfo>();

		PreprocessElementInfo elementInfo;

		if ((new java.io.File(filePath)).isFile())
		{
			Scanner reader = null;
			try
			{
				reader = new Scanner(filePath);
				String line;
				while (reader.hasNext())
				{
					line = reader.nextLine();
					if (Helper.DotNetToJavaStringHelper.isNullOrEmpty(line) || line.startsWith("#")) // It's for comments
					{
						continue;
					}

					elementWithInfos = Helper.StringExtensions.SplitRemoveEmptyElements(line,"[,;\t]");
					if (elementWithInfos.length <= 1)
					{
						continue;
					}

					String pinglishStr = elementWithInfos[0];
					boolean isWholeWord = false;
					boolean isExactWord = false;
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
					TokenPosition position = TokenPosition.None;
					LinqSimulationArrayList<String> equivalents = new LinqSimulationArrayList<String>();

					for (int i = 1; i < elementWithInfos.length; i++)
					{
						if (!elementWithInfos[i].startsWith("#"))
						{
							equivalents.add(elementWithInfos[i]);
						}
						else
						{
							String info = Helper.DotNetToJavaStringHelper.trimStart(elementWithInfos[i], '#');
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//							switch (info.ToLower())
						String tempVar = info.toLowerCase();
//ORIGINAL LINE: case "start":
							if (tempVar.equals("start"))
							{
							position = position.Set(TokenPosition.StartOfWord);
							}
//ORIGINAL LINE: case "middle":
							else if (tempVar.equals("middle"))
							{
							position = position.Set(TokenPosition.MiddleOfWord);
							}
//ORIGINAL LINE: case "end":
							else if (tempVar.equals("end"))
							{
							position = position.Set(TokenPosition.EndOfWord);
							}
//ORIGINAL LINE: case "wholeword":
							else if (tempVar.equals("wholeword"))
							{
							isWholeWord = true;
							}
//ORIGINAL LINE: case "exact":
							else if (tempVar.equals("exact"))
							{
							isExactWord = true;
							}
						}
					}
					if (position == TokenPosition.None || isWholeWord)
					{
						position = TokenPosition.Any;
					}

					elementInfo = new PreprocessElementInfo(pinglishStr, isWholeWord, isExactWord, position);
					elementInfo.Equivalents.addAll(equivalents);
					preprocessElementInfos.add(elementInfo);
				}
			}
			catch (RuntimeException ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				if (reader != null)
				{
					reader.close();
				}
			}
		}

		return preprocessElementInfos;
	}
}