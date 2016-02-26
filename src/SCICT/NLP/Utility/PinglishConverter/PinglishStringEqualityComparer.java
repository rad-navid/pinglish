package SCICT.NLP.Utility.PinglishConverter;


/**
*/
public class PinglishStringEqualityComparer implements java.util.Comparator<PinglishString>
{

	public final boolean equals(PinglishString x, PinglishString y)
	{
		return x.equals(y);
	}

	public final int hashCode(PinglishString obj)
	{
		return obj.hashCode();
	}

	public int compare(PinglishString x, PinglishString y)
	{
		if (!x.getEnglishString().equals(y.getEnglishString()))
		{
			return x.getEnglishString().compareTo(y.getEnglishString());
		}

		return x.getPersianString().compareTo(y.getPersianString());
	}

}