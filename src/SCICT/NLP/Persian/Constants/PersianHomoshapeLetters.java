package SCICT.NLP.Persian.Constants;

import Helper.ArraysUtility;

/**
 Homoshape letters in Persian, homophone words are those that can pronounce the same
*/
public final class PersianHomoshapeLetters
{

	/**
	 Alef homoshape family
	*/
	public static char[] AlefFamily = new char[] {'أ', 'إ', 'ا'};
	/**
	 Be homoshape family
	*/
	public static char[] BeFamily = new char[] {'ب', 'پ'};
	/**
	 Teh homoshape family
	*/
	public static char[] TehFamily = new char[] {'ت', 'ث'};
	/**
	 Hah homoshape family
	*/
	public static char[] HahFamily = new char[] {'ح', 'ج', 'چ'};
	/**
	 Xah homoshape family
	*/
	public static char[] XahFamily = new char[] {'خ', 'ح'};
	/**
	 Dal homoshape family
	*/
	public static char[] DalFamily = new char[] {'د', 'ذ'};
	/**
	 Zeh homoshape family
	*/
	public static char[] ZehFamily = new char[] {'ر', 'ز'};
	/**
	 Zain homoshape family
	*/
	public static char[] ZainFamily = new char[] {'ض', 'ص'};
	/**
	 Zath homoshape family
	*/
	public static char[] ZathFamily = new char[] {'ظ', 'ط'};
	/**
	 Ghain homoshape family
	*/
	public static char[] GhainFamily = new char[] {'غ', 'ع'};
	/**
	 Kaf homoshape family
	*/
	public static char[] KafFamily = new char[] {'گ', 'ک'};
	/**
	 Ghaf homoshape family
	*/
	public static char[] GhafFamily = new char[] {'ف', 'ق'};

	/**
	 Get all homophone letters
	*/
	public static char[][] getAllHomoshapes()
	{
		java.util.ArrayList<char[]> all = new java.util.ArrayList<char[]>();
		all.add(AlefFamily);
		all.add(BeFamily);
		all.add(TehFamily);
		all.add(HahFamily);
		all.add(XahFamily);
		all.add(DalFamily);
		all.add(ZehFamily);
		all.add(ZainFamily);
		all.add(ZathFamily);
		all.add(GhainFamily);
		all.add(KafFamily);
		all.add(GhafFamily);

		return all.toArray(new char[1][1]);
	}

	/**
	 Check if two letters are homoshape
	
	@param c1 c1
	@param c2 c2
	@return True if c1 and c2 are homoshape
	*/
	public static boolean AreHomoshape(char c1, char c2)
	{
		for (char[] homoshapeFamily : getAllHomoshapes())
		{
			if (ArraysUtility.Contains(homoshapeFamily,c1) && ArraysUtility.Contains(homoshapeFamily,c2))
			{
				return true;
			}
		}

		return false;
	}
}