package Helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	
	public static String ReplaceAllRegex(String str, String regex, String with, boolean ignoreCase)
	{
		if (ignoreCase)
		{
			regex+="(?i)"+regex;
		}
		return str.replaceAll(regex, with);
	}
	public static String ReplaceFirstRegex(String str, String regex, String with, boolean ignoreCase)
	{
		
		if (ignoreCase)
		{
			regex+="(?i)"+regex;
		}
		return str.replaceFirst(regex, with);
	}
	public static String ReplaceLastRegex(String str, String regex, String with)
	{
		/// must became correct
		Pattern ptrn=Pattern.compile(regex);
		Matcher macher=ptrn.matcher(str);
		 while(macher.find()){  
	            macher.group();  
	        }
		 return macher.group();
	}
	
	public static boolean MatchesRegex(String str, String pattern, boolean ignoreCase)
	{
		Pattern ptrn=Pattern.compile(pattern);
		Matcher macher=ptrn.matcher(str);
		return macher.matches();
	}

}
