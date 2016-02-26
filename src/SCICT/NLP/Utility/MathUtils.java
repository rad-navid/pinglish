package SCICT.NLP.Utility;

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


/** 
 Some mathematical utility methods, and string utility methods related to mathematics
*/
public class MathUtils
{
	/** 
	 Determines whether the specified number is power of ten.
	 
	 @param n The number
	 @return 
	 	<c>true</c> if the specified number is power of ten; otherwise, <c>false</c>.
	 
	*/
	public static boolean IsPowerOfTen(long n)
	{
		while (n % 10 == 0)
		{
			n /= 10;
		}

		if (n == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/** 
	 Gets the number of digits of the specified number.
	 
	 @param n The number.
	 @return 
	*/
	public static int DigitCount(long n)
	{
		if (n < 0)
		{
			n = -n;
		}

		int count = 1;
		while (n >= 10)
		{
			n /= 10;
			count++;
		}

		return count;
	}

	/** 
	 Removes the trailing zeros from the string representation of a number.
	 Has some usage in dealing with the mantissa of numbers.
	 
	 @param p The string representation of a number.
	 @return 
	*/
	public static String RemoveTrailingZeros(String p)
	{
		int len = p.length();
		int i;
		for (i = len - 1; i >= 0; i--)
		{
			if (p.charAt(i) != '0')
			{
				break;
			}
		}

		if (i >= 0) // i.e. loop breaked
		{
			return p.substring(0, i + 1);
		}
		else // i.e. the string was all a bunch of zero characters
		{
			return "";
		}
	}

	/** 
	 Normalizes the string representation of a number that is converted to string with F20 format.
	 It removes the trailing zeros, and if the mantissa consists all 
	 of zeros the decimal point is also removed.
	 
	 @param p The string representation of a number.
	 @return 
	*/
	public static String NormalizeForF20Format(String str)
	{
		String result = RemoveTrailingZeros(str);
		if (result.endsWith("."))
		{
			result = result.substring(0, result.length() - 1);
		}
		if (result.length() <= 0)
		{
			result = "0";
		}

		return result;
	}

	/** 
	 Inserts english thousand seperator characters in proper positions inside the string containig a number.
	 
	 @param str The string containing a number.
	 @return 
	*/
	public static String InsertThousandSeperator(String str)
	{
		int len = str.length();
		int dotIndex = str.indexOf('.');
		if (dotIndex < 0)
		{
			dotIndex = len;
		}

		int startIndex = 0;
		for (startIndex = 0; startIndex < dotIndex; ++startIndex)
		{
			if (Character.isDigit(str.charAt(startIndex)))
			{
				break;
			}
		}

		if (startIndex >= dotIndex)
		{
			return str;
		}

		StringBuilder sb = new StringBuilder();

		if (startIndex > 0)
		{
			sb.append(str.substring(0, startIndex)); // append everything before first digit
		}

		if (Character.isDigit(str.charAt(startIndex))) // append first digit also
		{
			sb.append(str.charAt(startIndex));
		}

		int digitsLeft = dotIndex - startIndex - 1;

		for (int i = 1; i <= digitsLeft; ++i)
		{
			if (((digitsLeft - i + 1) % 3) == 0)
			{
				sb.append(",");
			}
			sb.append(str.charAt(startIndex + i));
		}

		if (dotIndex < len)
		{
			sb.append(str.substring(dotIndex)); // append everything after and including the dot
		}

		return sb.toString();
	}

	/** 
	 Creates the ordinal string from the main number string. e.g. "سه" --> "سوم"
	 
	 @param word The word.
	 @return 
	*/
	public static String CreateOrdinalNumber(String word)
	{
		String result = "";

		if (word.equals("سه"))
		{
			result = "سوم";
		}
		else if (word.endsWith("ی"))
		{
			result = word + "‌ام";
		}
		else if (word.endsWith("ن"))
		{
			result = word + "یم";
		}
		else
		{
			result = word + "م";
		}

		return result;
	}
}