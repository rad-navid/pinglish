package Helper;

import java.util.Collection;
import java.util.HashMap;



public class LinqSimulationHashMap<K, V> extends HashMap<K, V> {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -1765584846493726275L;
	float loadFactor; 
	public double Average(Collection<Double> col)
	{
		double sum=0;
		for (double element :col) 
		{
			sum+=element;
		}
		return sum/col.size();
	}
	public Integer Average(Collection<Integer> col)
	{
		int sum=0;
		for (int element :col) 
		{
			sum+=element;
		}
		return sum/col.size();
	}
	public Integer Max(Collection<Integer> col)
	{
		int max=Integer.MIN_VALUE;
		for (int element :col) 
		{
			if(max>element)
				max=element;
		}
		return max;
	}
	public int Sum(Collection<Integer> col)
	{
		int sum=0;
		for (int element :col) 
		{
			sum+=element;
		}
		return sum;
	}
	public double Sum(Collection<Double> col)
	{
		double sum=0;
		for (double element :col) 
		{
			sum+=element;
		}
		return sum;
	}
	public static void AddOrUpdate(LinqSimulationHashMap<String,Integer> list,String value)
    {
        if (list.containsKey(value))
        {
            Integer contan= list.get(value);
            contan=new Integer(contan.intValue()+1);
            //list[mappedChar] = 1;
        }
        else
        {
            list.put(value, 1);
        }
    }
	

}
