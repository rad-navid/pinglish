package SCICT.NLP.Persian;


/** 
 The main filtering categories used by Persian Char Filters to replace non-standard characters,
 with their standard equivalents.
*/
public class FilteringCharacterCategory
{
	/** 
	 Filter all kinds of Kaaf
	*/
	public static final FilteringCharacterCategory Kaaf = new FilteringCharacterCategory(0x0001);
	/** 
	 Filter all kinds of Yaa
	*/
	public static final FilteringCharacterCategory Yaa = new FilteringCharacterCategory(0x0002);
	/** 
	 Filter all kinds of Half-space
	*/
	public static final FilteringCharacterCategory HalfSpace = new FilteringCharacterCategory(0x0004);
	/** 
	 Filter arabic digits, and replaces them with their Persian counter-part.
	*/
	public static final FilteringCharacterCategory ArabicDigit = new FilteringCharacterCategory(0x0008);
	/** 
	 Filter all kinds of Erabs. Some fonts have their own customized 
	 erab characters, which are considered as non-standard.
	*/
	public static final FilteringCharacterCategory Erab = new FilteringCharacterCategory(0x0010);

	private int intValue;
	private static java.util.HashMap<Integer, FilteringCharacterCategory> mappings;
	private static java.util.HashMap<Integer, FilteringCharacterCategory> getMappings()
	{
		if (mappings == null)
		{
			synchronized (FilteringCharacterCategory.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, FilteringCharacterCategory>();
				}
			}
		}
		return mappings;
	}

	private FilteringCharacterCategory(int value)
	{
		intValue = value;
		synchronized (FilteringCharacterCategory.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static FilteringCharacterCategory forValue(int value)
	{
		synchronized (FilteringCharacterCategory.class)
		{
			FilteringCharacterCategory enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new FilteringCharacterCategory(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}