package Helper;

import java.util.Comparator;

import SCICT.NLP.Utility.PinglishConverter.CharacterMappingInfo;

public class CharacterMappingInfoComparator implements Comparator<CharacterMappingInfo> {

	

	@Override
	public int compare(CharacterMappingInfo o1, CharacterMappingInfo o2) {
		
		if (o2 == null)
		{
			return 1;
		}
		else if(o1 == null)
		{
			return 1;
		}

		return (new Integer(o1.getRelativeIndex())).compareTo(o2.getRelativeIndex());
	}

}
