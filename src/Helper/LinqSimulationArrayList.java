package Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import SCICT.NLP.Utility.StringUtil;
import SCICT.NLP.Utility.PinglishConverter.CharacterMappingInfo;
import SCICT.NLP.Utility.PinglishConverter.PinglishString;

public class LinqSimulationArrayList<E> extends ArrayList<E> {
	
	/**
	 * 
	 */
	
	public LinqSimulationArrayList(Collection<E>collection)
	{
		super(collection);
	}
	
	public LinqSimulationArrayList(int size)
	{
		super(size);
		
	}
	public LinqSimulationArrayList()
	{
		super();
	}
	public LinqSimulationArrayList(E[] array)
	{
		super();
		for (E item : array) {
			this.add(item);
		}
	}
	private static final long serialVersionUID = 4859068557376789131L;

	public boolean addAll(E[] collection)
	{
		for (E item : collection) {
			if(item!=null)
				this.add(item);
		}
		return true;
		
	}
	
	public boolean addAll(Iterable<E> collection)
	{
		for (E item : collection) {
			this.add(item);
		}
		return true;
	}
	public boolean addAll(ArrayList<E> collection)
	{
		for (E item : collection) {
			this.add(item);
		}
		return true;
	}
	
	public LinqSimulationArrayList<E> Distinct(E type)
	{
		HashMap<E, E>hs=new HashMap<E,E>(this.size());
		for(E item:this)
			if(item!=null)
				hs.put(item, item);
		this.clear();
		this.addAll(hs.values().iterator());
		hs.clear();
		hs=null;
		return this;
	}
	public LinqSimulationArrayList<E> Distinct(E type, Comparator<E> comparator)
	{
		TreeSet<E> ts=new TreeSet<E>(comparator);
		ts.addAll(this);
		this.clear();
		this.addAll(ts.iterator());
		ts.clear();
		ts=null;
		return this;
		
	}
	
	private boolean addAll(Iterator<E> iterator) {
		if(iterator==null)
			return false;
		while (iterator.hasNext()) 
		{
			this.add(iterator.next());
		}
		return true;
	}

	public LinqSimulationArrayList<E> removeRange(E type,int a, int b)
	{
		for(int i=1;i<=b;i++)
			this.remove(a);
		return this;
	}
	
	public static <E>LinqSimulationArrayList<E> ToList(E[]array) {
		LinqSimulationArrayList<E>list=new LinqSimulationArrayList<E>(array.length);
		for (E e : array)
			list.add(e);
		return list;
		
	}
	
	public LinqSimulationArrayList<E> Sort(E[]array,Comparator<E>comparator)
	{
		Arrays.sort(array,comparator);
		this.clear();
		for (E e : array) {
			this.add(e);
		}
		return this;
		
	}
	public int size()
	{
		try
		{
			int tmp_size=super.size();
			if(tmp_size==1 && this.get(0)==null)
				return 0;
			else
				return tmp_size;
		}
		catch(Exception e)
		{
			return 0;
		}
	}
	
	public static void Update( LinqSimulationArrayList<PinglishString> list, char englishLetter, LinqSimulationArrayList<String> persianLetters)
    {
        int count = list.size();

        while (count > 0)
        {
            PinglishString original = list.get(0);
            for(String value : persianLetters)
            {
                PinglishString fs = original.Clone();
                fs.Append(value, englishLetter);
                list.add(fs);
            }

            list.remove(0);

            --count;
        }
    }
	public static LinqSimulationArrayList<PinglishString> ToLower( LinqSimulationArrayList<PinglishString> list)
    {
        LinqSimulationArrayList<PinglishString> results = new LinqSimulationArrayList<PinglishString>();

        for(PinglishString pinglishString : list)
        {
            results.add(pinglishString.ToLower());
        }

        return results;
    }
	
	 public static void UpdateClone( LinqSimulationArrayList<PinglishString> list, int index, 
			 LinqSimulationArrayList<CharacterMappingInfo> values, LinqSimulationArrayList<CharacterMappingInfo> values2, 
	            char letter, char nextLetter)
	        {
	            if (values.size() == 0)
	                return;

	            int count = list.size();

	            while (count > 0)
	            {
	                PinglishString original = list.get(0);
	                for (CharacterMappingInfo value : values)
	                {
	                	PinglishString fs = original.Clone();
	                    fs.Update(index, value.getValue(), letter);
	                    list.add(fs);
	                }

	                for (CharacterMappingInfo value : values2)
	                {
	                    PinglishString fs = original.Clone();
	                    fs.Update(index, value.getValue(), letter);
	                    fs.Update(index + 1,"", nextLetter);
	                    list.add(fs);
	                }

	                list.remove(0);

	                --count;
	            }
	        }
	 
	 public static LinqSimulationArrayList<String> ToStringList(LinqSimulationArrayList<PinglishString> list, boolean removeErab, boolean removeDuplicates)
     {
		 LinqSimulationArrayList<String> pinglishResults = new LinqSimulationArrayList<String>();
         for (PinglishString fstr : list)
         {
             if (removeErab)
                 pinglishResults.add(StringUtil.RemoveErab(fstr.getPersianString()));
             else
                 pinglishResults.add(fstr.getPersianString());
         }
         if (removeDuplicates)
         {
             pinglishResults = pinglishResults.Distinct("");
         }
         return pinglishResults;
     }

}
