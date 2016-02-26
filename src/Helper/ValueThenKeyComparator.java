package Helper;

import java.util.Comparator;
import java.util.Map;

public class ValueThenKeyComparator<K extends Comparable<? super K>,
V extends Comparable<? super V>>
	implements Comparator<Map.Entry<K, V>> {
	
	public int compare(Map.Entry<K, V> a, Map.Entry<K, V> b)
	{
		int cmp1 = a.getValue().compareTo(b.getValue());
		if (cmp1 != 0) {
			return -1*cmp1;
		} else
		{
			return a.getKey().compareTo(b.getKey());
		}
	}
	
}