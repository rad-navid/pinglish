package Helper;

import java.util.ArrayList;

import SCICT.NLP.Utility.PinglishConverter.PinglishString;

public class ArraysUtility {
	public static int indexOf(String[] array, String suffix)
	{
		return indexOf(array, suffix, 0);
	}
	public static int indexOf(String[] array, String suffix,int index)
	{
		for(int i=index;i<array.length;i++)
		{
			String tobeCompared=array[i];
			if(tobeCompared==null)
				continue;
			else if(tobeCompared.equalsIgnoreCase(suffix))
				return i;
		}
		return -1;
	}
	public static String[] toArray(Iterable<String> list)
	{
		ArrayList<String>array=new ArrayList<String>();
		for (String string : list) {
			array.add(string);
		}
		return array.toArray(new String[0]);
	}
	
	public static boolean Contains(char[] list,char arg)
	{
		for (char c : list) {
			if(c==arg)
				return true;
		}
		return false;
	}
	
	public static <T> Iterable<T> Concat(T first,Iterable<T>second)
	{
		LinqSimulationArrayList<T>list=new LinqSimulationArrayList<T>(); 
			list.add(first);
		for (T t : second) 
			list.add(t);
		return list;
	}
	public static <T> Iterable<T> Concat(Iterable<T>first,Iterable<T>second)
	{
		LinqSimulationArrayList<T>list=new LinqSimulationArrayList<T>();
		for (T t : first) 
			list.add(t);
		for (T t : second) 
			list.add(t);
		return list;
	}
	public static <T> Iterable<T> Concat(Iterable<T>first,T[]second)
	{
		LinqSimulationArrayList<T>list=new LinqSimulationArrayList<T>();
		for (T t : first) 
			list.add(t);
		for (T t : second) 
			list.add(t);
		return list;
	}
	public static <T> Iterable<T> Concat(T[]first,T[]second)
	{
		LinqSimulationArrayList<T>list=new LinqSimulationArrayList<T>();
		for (T t : first) 
			list.add(t);
		for (T t : second) 
			list.add(t);
		return list;
	}
	public static <T> Iterable<T> Concat(T[]first,Iterable<T>second)

	{
		LinqSimulationArrayList<T>list=new LinqSimulationArrayList<T>();
		for (T t : first) 
			list.add(t);
		for (T t : second) 
			list.add(t);
		return list;
	}
	
	public static int FindIndex(char[] array,String base)
	{
		char baseChar=base.charAt(base.length() - 1);
		for (int i=0;i<array.length;i++) {
			if(array[i]==baseChar)
				return i;
		}
		return-1;
		
	}
	public static String[]Distinct(String[]array)
	{
		ArrayList<String>list=new ArrayList<String>();
		for (String string : array) 
		{
			if(!list.contains(string))
				list.add(string);
		}
		return list.toArray(array);
	}
	
	public static double Average(double[] col)
	{
		double sum=0;
		for (double element :col) 
		{
			sum+=element;
		}
		return sum/col.length;
	}
	public static int lastIndexOf(byte[]byteBuff,byte value)
	{
		for(int i=byteBuff.length-1;i>0;i--)
		{
			if(byteBuff[i]==value)
				return i;
		}
		return-1;
	}
	public static int indexOf(byte[]byteBuff,byte value,int index)
	{
		for(int i=index;i<byteBuff.length;i++)
		{
			if(byteBuff[i]==value)
				return i;
		}
		return-1;
	}
	public static Character[] toCharacter(char[]array)
	{
		Character Chartarray[]=new Character[array.length];
		for(int i=0;i<array.length;i++)
			Chartarray[i]=new Character(array[i]);
		return Chartarray;
	}
	public static char[] toChar(Character[]array)
	{
		char charArray[]=new char[array.length];
		for(int i=0;i<array.length;i++)
			charArray[i]=array[i].charValue();
		return charArray;
	}
	
	public static int[] toInt(Integer[]array)
	{
		int intArray[]=new int[array.length];
		for(int i=0;i<array.length;i++)
			intArray[i]=array[i].intValue();
		return intArray;
	}
	public static int indexOf(char[] array, char charAt) 
	{	
		for(int i=0;i<array.length;i++)
			if(array[i]==charAt)
				return i;
		return -1;
	}
	
	public static String[] ToStringArray( PinglishString[] array, boolean removeErab, boolean removeDuplicates)
    {
		LinqSimulationArrayList<PinglishString>list=new LinqSimulationArrayList<PinglishString>(array);
        return LinqSimulationArrayList.ToStringList(list,removeErab, removeDuplicates).toArray(new String[0]);
    }
}
