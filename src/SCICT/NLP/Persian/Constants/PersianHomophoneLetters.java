package SCICT.NLP.Persian.Constants;

import Helper.ArraysUtility;

/**
 Homophone letters in Persian, homophone words are those that can pronounce the same
*/
public final class PersianHomophoneLetters
{

	/**
	 Zain homophone family
	*/
	public static char[] ZainFamily = new char[] {'ز', 'ض', 'ظ', 'ذ'};
	/**
	 Seen homophone family
	*/
	public static char[] SeenFamily = new char[] {'س', 'ث', 'ص'};
	/**
	 Teh homophone family
	*/
	public static char[] TehFamily = new char[] {'ت', 'ط'};
	/**
	 Ghain homophone family
	*/
	public static char[] GhainFamily = new char[] {'ق', 'غ'};
	/**
	 Hah homophone family
	*/
	public static char[] HahFamily = new char[] {'ه', 'ح'};
	/**
	 Alef homophone family
	*/
	public static char[] AlefFamily = new char[] {'ا', 'آ'};
	/**
	 Hamza homophone family
	*/
	public static char[] HamzaFamily = new char[] {'ئ', 'أ', 'ی', 'ؤ', 'ء', 'إ', 'ا', 'و'};

	/**
	 Get all homophone letters
	*/
	public static char[][] getAllHomophones()
	{
		java.util.ArrayList<char[]> all = new java.util.ArrayList<char[]>();
		all.add(ZainFamily);
		all.add(SeenFamily);
		all.add(TehFamily);
		all.add(GhainFamily);
		all.add(HahFamily);
		all.add(AlefFamily);
		all.add(HamzaFamily);

		return all.toArray(new char[1][1]);
	}

	/**
	 Check if two letters are homophone
	
	@param c1 c1
	@param c2 c2
	@return True if c1 and c2 are homophone
	*/
	public static boolean AreHomophone(char c1, char c2)
	{
		for (char[] homophoneFamily : getAllHomophones())
		{
			if (ArraysUtility.Contains(homophoneFamily, c1) && ArraysUtility.Contains(homophoneFamily,c2))
			{
				return true;
			}
		}

		return false;
	}
}