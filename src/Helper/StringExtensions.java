package Helper;

import java.util.ArrayList;

public final class StringExtensions
{

	public static boolean StartsWith(String value, String[] array)
	{
		for (String arrStr : array)
		{
			if (value.startsWith(arrStr))
			{
				return true;
			}
		}

		return false;
	}

	public static boolean EndsWith(String value, String[] array)
	{
		for (String arrStr : array)
		{
			if (value.endsWith(arrStr))
			{
				return true;
			}
		}

		return false;
	}

	public static boolean IsIn(String value, String[] array)
	{
		for (String arrStr : array)
		{
			if (value.equals(arrStr))
			{
				return true;
			}
		}

		return false;
	}

	public static boolean FindsIn(String value, String[] array)
	{
		for (String arrStr : array)
		{
			if (arrStr.contains(value))
			{
				return true;
			}
		}

		return false;
	}


	public static String[] ToStringArray(String value)
	{
		char[] charArray = value.toCharArray();

		java.util.ArrayList<String> strList = new java.util.ArrayList<String>();

		for (char c : charArray)
		{
			strList.add((new Character(c)).toString());
		}

		return strList.toArray(new String[0]);
	}
	
	public static String Insert(String base, int index, String insertion)
	{
		String p1=base.substring(0, index);
		String p2=base.substring(index);
		return p1+insertion+p2;
	}
	
	public static String[] SplitRemoveEmptyElements(String str,String reg)
	{
		String[]array=str.split(reg);
		ArrayList<String>list=new ArrayList<String>();
		for (String item : array) {
			if(!(item=="" && item.isEmpty()))
					list.add(item);
		}
		return list.toArray(new String[0]);
		
	}
	public static int IndexOfAny(String srt,char[]array,int index)
	{
		for(int i=index;i<srt.length();i++)
		{
			for (char c : array) {
				if(	srt.charAt(i)==c)
					return i;
			}
		}
		return -1;
	}

	
}