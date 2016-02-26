package SCICT.Utility.SpellChecker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import Helper.ArraysUtility;
import Helper.StringExtensions;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.*;
import SCICT.Utility.*;

/**
 Tools for dictionary
*/
public class DictionaryTools
{
	//private Dictionary<string, int> m_entry = new Dictionary<string, int>();
	private final java.util.TreeMap<String, Integer> m_entry = new java.util.TreeMap<String, Integer>();

	/**
	 Load a dictionary
	
	@param dictionaryFileName Dictionary path
	@return 
	@exception Exception
	*/
	public final boolean LoadDic(String dictionaryFileName)
	{
		try
		{
			if (dictionaryFileName.length() == 0)
			{
				return false;
			}

			if (!(new java.io.File(dictionaryFileName)).isFile())
			{
				return false;
			}

			Scanner reader = new Scanner(dictionaryFileName);
			try
			{
				int tmpLen = 0;
				//int maxLen = 0;
				//string tmpWord = "";

				while (reader.hasNext())
				{
					String line = reader.nextLine();
					String[] words = line.split("[ \\t]");

					//words[0] = StringUtil.ExtractPersianWordsStandardized(words[0])[0];
					if (words.length == 2)
					{
						tmpLen = words[0].length();
						if (tmpLen > 0 && words[1].length() > 0)
						{
							int res = 0;
							Helper.RefObject<Integer> tempRef_res = new Helper.RefObject<Integer>(res);
							boolean tempVar = false;
							try{
								 tempRef_res.argvalue=Integer.parseInt(words[1]);
								 tempVar=true;
							}
							catch(Exception exx)
							{							}
								res = tempRef_res.argvalue;
							if (tempVar)
							{
								if (!this.m_entry.containsKey(words[0]))
								{
									//if (tmpLen > maxLen)
									//{
									//    maxLen = tmpLen;
									//    tmpWord = words[0];
									//}
									// word does not exist
									this.m_entry.put(words[0], res);
								}
								else
								{
									this.m_entry.put(words[0], Math.max(this.m_entry.get(words[0]), res));
								}
							}
						}
					}
				}

				reader.close();
			}
			finally
			{
				reader=null;
			}

			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/*
	public bool LoadDic3(string dictionaryFileName)
	{
	    try
	    {
	        if (dictionaryFileName.Length == 0)
	        {
	            return false;
	        }

	        if (!File.Exists(dictionaryFileName))
	        {
	            return false;
	        }

	        using (StreamReader reader = new StreamReader(dictionaryFileName))
	        {
	            //int maxLen = 0;
	            //string tmpWord = "";

	            while (!reader.EndOfStream)
	            {
	                string line = reader.ReadLine();
	                string[] words = line.Split(' ', '\t');

	                //words[0] = StringUtil.ExtractPersianWordsStandardized(words[0])[0];
	                if (words.Length == 1)
	                {
	                    if (!this.m_entry.ContainsKey(words[0]))
	                    {
	                        this.m_entry.Add(words[0], 1);
	                    }
	                    else
	                    {
	                        this.m_entry[words[0]]++;
	                    }
	                }
	            }

	            reader.Close();
	        }

	        return true;
	    }
	    catch (Exception ex)
	    {
	        throw ex;
	    }
	}
	*/

	/**
	 Load a dictionary into given data structure
	
	@param dictionaryFileName Dictionary path
	@param entry Dictionary data structure
	@return 
	@exception Exception
	*/
	public final boolean LoadDic(String dictionaryFileName, java.util.HashMap<String, Integer> entry,Object redundunt)
	{
		try
		{
			if (dictionaryFileName.length() == 0)
			{
				return false;
			}

			if (!(new java.io.File(dictionaryFileName)).isFile())
			{
				return false;
			}

//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//			using (StreamReader reader = new StreamReader(dictionaryFileName))
			Scanner reader = new Scanner(dictionaryFileName);
			try
			{
				int tmpLen = 0;

				while (reader.hasNext())
				{
					String line = reader.nextLine();
					String[] words = line.split("[ \\t]");

					if (words.length == 2)
					{
						tmpLen = words[0].length();
						if (tmpLen > 0 && words[1].length() > 0)
						{
							int res = 0;
							Helper.RefObject<Integer> tempRef_res = new Helper.RefObject<Integer>(res);
							boolean tempVar =false;
							try{
								tempRef_res.argvalue= Integer.parseInt(words[1]);
								tempVar=true;
							}catch(Exception exx){}
								res = tempRef_res.argvalue;
							if (tempVar)
							{
								if (!entry.containsKey(words[0]))
								{
									entry.put(words[0], res);
								}
								else
								{
									entry.put(words[0], Math.max(entry.get(words[0]) + 1, res));
								}
							}
						}
					}
				}

				reader.close();
			}
			finally
			{
				reader=null;
			}

			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}

	/**
	 Load a dictionary into given data structure considering affix combination
	
	@param dictionaryFileName Dictionary path
	@param entry Dictionary data structure
	@return 
	@exception Exception
	*/
	public final boolean LoadDic(String dictionaryFileName, java.util.HashMap<String, int[]> entry)
	{
		try
		{
			if (dictionaryFileName.length() == 0)
			{
				return false;
			}

			if (!(new java.io.File(dictionaryFileName)).isFile())
			{
				return false;
			}

//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//			using (StreamReader reader = new StreamReader(dictionaryFileName))
			Scanner reader = new Scanner(dictionaryFileName);
			try
			{
				while (reader.hasNext())
				{
					String line = reader.nextLine();
					String[] words = line.split("[ \\t]");

					if (words.length == 3)
					{
						if (words[0].length() > 0 && words[1].length() > 0 && words[2].length() > 0)
						{
							int count = 0;
							Helper.RefObject<Integer> tempRef_count = new Helper.RefObject<Integer>(count);
							boolean tempVar = false;
							try{
								tempRef_count.argvalue=Integer.parseInt(words[1]);
								tempVar=true;
							}catch(Exception exx){}
								count = tempRef_count.argvalue;
							if (tempVar)
							{
								int containAffix = 0;
								Helper.RefObject<Integer> tempRef_containAffix = new Helper.RefObject<Integer>(containAffix);
								boolean tempVar2 = false;
								try{
									tempRef_containAffix.argvalue=Integer.parseInt(words[2]);
									tempVar2=true;
								}catch(Exception exe){}
									containAffix = tempRef_containAffix.argvalue;
								if (tempVar2)
								{
									java.util.ArrayList<Integer> attr = new java.util.ArrayList<Integer>();
									if (!entry.containsKey(words[0]))
									{
										attr.add(count);
										attr.add(containAffix);

										entry.put(words[0], ArraysUtility.toInt(attr.toArray(new Integer[0])));
									}
									else
									{
										attr.add(Math.max(entry.get(words[0])[0] + 1, count));
										attr.add(containAffix);

										entry.put(words[0], ArraysUtility.toInt(attr.toArray(new Integer[0])));
									}
								}
							}
						}
					}
				}

				reader.close();
			}
			finally
			{
				reader=null;
			}

			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}


	/**
	 Genrate a dictionary (language model) from a text corpus
	
	@param corpusFileName Courpus path
	@return 
	@exception Exception
	*/
	public final boolean GenerateLanguageModel(String corpusFileName)
	{
		try
		{
			if (corpusFileName.length() == 0)
			{
				return false;
			}

			Scanner reader = new Scanner(corpusFileName);

			while (reader.hasNext())
			{
				String line = reader.nextLine();
				String[] words = StringExtensions.SplitRemoveEmptyElements(line,PersianAlphabets.Delimiters);

				//string[] words = StringUtil.ExtractPersianWordsStandardized(line);

				for (String word : words)
				{
					//if (word.Length > 0)
					//{
					if (!this.m_entry.containsKey(word))
					{
						// word does not exist
						this.m_entry.put(word, 1);
					}
					else
					{
						// word exist, add the count of word
						Integer key=this.m_entry.get(word);
						key=new Integer(key.intValue()+1);
					}
					//}
				}
			}

			reader.close();

			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}
	/**
	 Dump dictionary to disk
	
	@param fileName File path
	@param count Word with smaller usage frequency does not dumped
	@param length Word with smaller length does not dumped
	@return 
	@exception Exception
	*/
	public final boolean DumpDic(String fileName, int count, int length)
	{
		try
		{
			if (fileName.length() > 0)
			{
				String refineWord = "";
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				Set<String> keys= this.m_entry.keySet();
				for (String key : keys)
				{
					if (key.length() > length)
					{
						int value=this.m_entry.get(key);
						if (value> count)
						{
							refineWord = StringUtil.RefineAndFilterPersianWord(key);
							//refineWord = pair.Key;
							writer.write(refineWord + " " +value+"");
							writer.newLine();
						}
						else
						{
							//Debug.WriteLine(pair.getKey());
						}
					}
					else
					{
						//Debug.WriteLine(pair.getKey());
					}
				}
				writer.close();
			}

			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}



	/*
	public bool ExtractDic(string fileName, int count, int length)
	{
	    try
	    {
	        if (fileName.Length > 0)
	        {
	            string refineWord = "";
	            StreamWriter writer = new StreamWriter(fileName);
	            foreach (KeyValuePair<string, int> pair in this.m_entry)
	            {
	                if (pair.Key.Length <= length)
	                {
	                    if (pair.Value <= count)
	                    {
	                        refineWord = StringUtil.RefineAndFilterPersianWord(pair.Key);
	                        //refineWord = pair.Key;
	                        writer.WriteLine(refineWord + " " + pair.Value.ToString());
	                    }
	                }
	            }
	            writer.Close();
	        }

	        return true;
	    }
	    catch (Exception ex)
	    {
	        throw ex;
	    }
	}
	public bool DumpRelaxDic(string fileName)
	{
	    try
	    {
	        if (fileName.Length > 0)
	        {
	            Dictionary<string, int> myDic = new Dictionary<string, int>();

	            //PersianSuffixRecognizer PersianSuffixRecognizer = new PersianSuffixRecognizer(false);
	            //ReversePatternMatcherPatternInfo[] affixeResults = null;
	            //string wordWithoutAffix = "";

	            //bool flag = false;
	            //string tmpword = "";

	            foreach (KeyValuePair<string, int> pair in this.m_entry)
	            {
	                if (pair.Key.Contains("می‌"))
	                {
	                    if (myDic.ContainsKey(pair.Key))
	                    {
	                        myDic[pair.Key]++;
	                    }
	                    else
	                    {
	                        myDic.Add(pair.Key, 1);
	                    }
	                }
	            }

	            #region Affix-Stripper
	            //wordWithoutAffix = pair.Key;

	            //affixeResults = PersianSuffixRecognizer.MatchForAffix(wordWithoutAffix);
	            //if (affixeResults.Length == 0)
	            //{
	            //    if (myDic.ContainsKey(wordWithoutAffix))
	            //    {
	            //        myDic[wordWithoutAffix]++;
	            //    }
	            //    else
	            //    {
	            //        myDic.Add(wordWithoutAffix, 1);
	            //    }

	            //    continue;
	            //}

	            //flag = false;

	            //foreach (ReversePatternMatcherPatternInfo rpm in affixeResults)
	            //{
	            //    tmpword = rpm.BaseWord;

	            //    if (this.m_entry.ContainsKey(tmpword))
	            //    {
	            //        if (myDic.ContainsKey(tmpword))
	            //        {
	            //            myDic[tmpword]++;
	            //        }
	            //        else
	            //        {
	            //            myDic.Add(tmpword, 1);
	            //        }

	            //        flag = true;
	            //        break;
	            //    }

	            //if (!flag)
	            //{
	            //    if (myDic.ContainsKey(wordWithoutAffix))
	            //    {
	            //        myDic[wordWithoutAffix]++;
	            //    }
	            //    else
	            //    {
	            //        myDic.Add(wordWithoutAffix, 1);
	            //    }
	            //}
	            #endregion

	            Dictionary<string, int> mibasedic = new Dictionary<string, int>();
	            Dictionary<string, int> mipsdic = new Dictionary<string, int>();
	            string meStriper = "";
	            foreach (KeyValuePair<string, int> pair in myDic)
	            {
	                this.m_entry.Remove(pair.Key);

	                //meStriper = pair.Key.Remove(0, 2);
	                //meStriper = StringUtil.RefineAndFilterPersianWord(meStriper);

	                //if (meStriper.Length == 0)
	                //{
	                //    continue;
	                //}

	                //if (!this.m_entry.ContainsKey(meStriper))
	                //{
	                //    if (mibasedic.ContainsKey(pair.Key))
	                //    {
	                //        mibasedic[pair.Key]++;
	                //    }
	                //    else
	                //    {
	                //        mibasedic.Add(pair.Key, pair.Value);
	                //    }
	                //}
	                //else
	                //{
	                //    if (mipsdic.ContainsKey("می‌" + meStriper))
	                //    {
	                //        mipsdic["می‌" + meStriper]++;
	                //    }
	                //    else
	                //    {
	                //        mipsdic.Add("می‌" + meStriper, pair.Value);
	                //    }
	                //}
	            }

	            StreamWriter writer = new StreamWriter(fileName + ".mibaseps");
	            foreach (KeyValuePair<string, int> pair in myDic)
	            {
	                writer.WriteLine(pair.Key + " " + pair.Value.ToString());
	            }
	            writer.Close();

	            StreamWriter writer2 = new StreamWriter(fileName + ".mibasewps");
	            foreach (KeyValuePair<string, int> pair in m_entry)
	            {
	                writer2.WriteLine(pair.Key + " " + pair.Value.ToString());
	            }
	            writer2.Close();

	            //StreamWriter writer3 = new StreamWriter(fileName + ".wmi");
	            //foreach (KeyValuePair<string, int> pair in this.m_entry)
	            //{
	            //    writer3.WriteLine(pair.Key + " " + pair.Value.ToString());
	            //}
	            //writer3.Close();

	            return true;
	        }

	        return false;
	    }
	    catch (Exception ex)
	    {
	        throw ex;
	    }
	}
	*/

	private boolean DumpSortedDicByCount(String fileName)
	{
		try
		{
			if (fileName.length() > 0)
			{
				java.util.HashMap<String, Integer> sortedDic = SortDicByCount();

				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
				for (java.util.Map.Entry<String, Integer> pair : sortedDic.entrySet())
				{
					writer.write(pair.getKey() + " " + pair.getValue().toString());
					writer.newLine();
				}
				writer.close();
			}

			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	private java.util.HashMap<String, Integer> SortDicByCount()
	{
		if (this.m_entry.size() > 0)
		{
			java.util.ArrayList<String> myList = new java.util.ArrayList<String>();
			Set<String>keys= this.m_entry.keySet();
			for (String key : keys)
			{
				myList.add(key);
			}

			String temp;
			for (int i = 0; i < myList.size() - 1; ++i)
			{
				for (int j = i + 1; j < myList.size(); ++j)
				{
					if (this.m_entry.get(myList.get(i)) < this.m_entry.get(myList.get(j)))
					{
						temp = myList.get(i);
						myList.set(i, myList.get(j));
						myList.set(j, temp);
					}
				}
			}

			java.util.HashMap<String, Integer> tmpDic = new java.util.HashMap<String, Integer>();
			for (String str : myList)
			{
				tmpDic.put(str, this.m_entry.get(str));
			}

			return tmpDic;
		}

		return new java.util.HashMap<String, Integer>();
	}

	/**
	 Append another dictionary
	
	@param dictionaryFileName Dictionary path
	@return 
	@exception Exception
	*/
	public final boolean AppendDictionary(String dictionaryFileName)
	{
		try
		{
			if (dictionaryFileName.length() == 0)
			{
				return false;
			}

			if (!(new java.io.File(dictionaryFileName)).isFile())
			{
				return false;
			}

//			using (StreamReader reader = new StreamReader(dictionaryFileName))
			Scanner reader = new Scanner(dictionaryFileName);
			try
			{
				int tmpLen = 0;
				//int maxLen = 0;
				//string tmpWord = "";

				while (reader.hasNext())
				{
					String line = reader.nextLine();
					String[] words = line.split("[ \\t]");

					//words[0] = StringUtil.ExtractPersianWordsStandardized(words[0])[0];
					if (words.length == 2)
					{
						tmpLen = words[0].length();
						if (tmpLen > 0 && words[1].length() > 0)
						{
							int res = 0;
							Helper.RefObject<Integer> tempRef_res = new Helper.RefObject<Integer>(res);
							boolean tempVar =false;
							try{
								tempRef_res.argvalue=Integer.parseInt(words[1]);
								tempVar=true;
							}catch(Exception exx){}
								res = tempRef_res.argvalue;
							if (tempVar)
							{
								if (!this.m_entry.containsKey(words[0]))
								{
									//if (tmpLen > maxLen)
									//{
									//    maxLen = tmpLen;
									//    tmpWord = words[0];
									//}
									// word does not exist
									this.m_entry.put(words[0], res);
								}
								else
								{
									this.m_entry.put(words[0], Math.max(this.m_entry.get(words[0]), res));
								}
							}
						}
					}
				}

				reader.close();
			}
			finally
			{
				reader=null;
			}

			return true;
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
	}
}