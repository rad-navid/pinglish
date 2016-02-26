package Helper;

import java.util.Comparator;

import SCICT.NLP.Utility.StringDistance.KeyboardKey;

public class KeyboardKeyComparator implements Comparator<KeyboardKey> {

	@Override
	public int compare(KeyboardKey key1, KeyboardKey key2) {
		
			if (key1.Value < key2.Value)
			{
				return -1;
			}

			if (key1.Value > key2.Value)
			{
				return 1;
			}

			return 0;
		
	}

}
