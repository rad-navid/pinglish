package SCICT.NLP.Persian.Constants;


public class PersianSuffixesCategory
{
	public static final PersianSuffixesCategory ObjectivePronoun = new PersianSuffixesCategory(1);
	public static final PersianSuffixesCategory ToBeVerb = new PersianSuffixesCategory(1 * 2);
	public static final PersianSuffixesCategory IndefiniteYaa = new PersianSuffixesCategory(1 * 2 * 2);
	public static final PersianSuffixesCategory YaaBadalAzKasre = new PersianSuffixesCategory(1 * 2 * 2 * 2);
	public static final PersianSuffixesCategory YaaNesbat = new PersianSuffixesCategory(1 * 2 * 2 * 2 * 2);
	public static final PersianSuffixesCategory OrdinalEnumerableAdjective = new PersianSuffixesCategory(1 * 2 * 2 * 2 * 2 * 2);
	public static final PersianSuffixesCategory ComparativeAdjectives = new PersianSuffixesCategory(1 * 2 * 2 * 2 * 2 * 2 * 2);
	public static final PersianSuffixesCategory PluralSignHaa = new PersianSuffixesCategory(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2);
	public static final PersianSuffixesCategory PluralSignAan = new PersianSuffixesCategory(1 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2);

	private int intValue;
	private static java.util.HashMap<Integer, PersianSuffixesCategory> mappings;
	private static java.util.HashMap<Integer, PersianSuffixesCategory> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PersianSuffixesCategory.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, PersianSuffixesCategory>();
				}
			}
		}
		return mappings;
	}

	private PersianSuffixesCategory(int value)
	{
		intValue = value;
		synchronized (PersianSuffixesCategory.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static PersianSuffixesCategory forValue(int value)
	{
		synchronized (PersianSuffixesCategory.class)
		{
			PersianSuffixesCategory enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new PersianSuffixesCategory(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
	
	public boolean Has(PersianSuffixesCategory value)
	{

		int andResult=value.getValue()& this.getValue();
		boolean result=(andResult==value.getValue());
		return result;
	}
	public boolean Is(PersianSuffixesCategory value)
	{
		try
		{
			return this.getValue() == value.getValue();
		}
		catch (java.lang.Exception e)
		{
			return false;
		}
	}
	
	public  PersianSuffixesCategory Set(PersianSuffixesCategory value)
	{

		try
		{
			int newValue=(this.getValue()| value.getValue());
			return PersianSuffixesCategory.forValue(newValue);
		}
		catch (RuntimeException ex)
		{
			throw new IllegalArgumentException(String.format("Could not append value from enumerated type '%1$s'.", PersianSuffixesCategory.class.getName()), ex);
		}
	}

	public  PersianSuffixesCategory Clear(PersianSuffixesCategory value)
	{


		try
		{
			int newValue=(this.getValue() & ~value.getValue());
			return (PersianSuffixesCategory)(Object)(newValue);
		}
		catch (RuntimeException ex)
		{
			throw new IllegalArgumentException(String.format("Could not remove value from enumerated type '%1$s'.",PersianSuffixesCategory.class.getName()), ex);
		}
	}

	
}