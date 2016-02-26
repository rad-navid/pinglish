package SCICT.NLP.Persian.Constants;

import Helper.LinqSimulationArrayList;

public final class PersianSuffixes
{
	public static String[] ObjectivePronounsBase = new String[] {"مان", "تان", "شان", "ش", "ت", "م"};
	public static String[] ObjectivePronounsPermutedForHaaYaa = new String[] {"مان", "تان", "شان", "اش", "ات", "ام"};
	public static String[] ObjectivePronounsPermutedForAlef = new String[] {"یمان", "یتان", "یشان", "یش", "یت", "یم"};
	public static String[] getObjectivePronouns()
	{
		LinqSimulationArrayList<String> tmpList = new LinqSimulationArrayList<String>();

		tmpList.addAll(ObjectivePronounsBase);
		tmpList.addAll(ObjectivePronounsPermutedForHaaYaa);
		tmpList.addAll(ObjectivePronounsPermutedForAlef);

		return tmpList.toArray(new String[0]);
	}

	public static String[] ToBeVerbsBase = new String[] {"یم", "ید", "ند", "ی", "م", "ست"};
	public static String[] ToBeVerbsPermutedForHaaYaa = new String[] {"ایم", "اید", "اند", "ای", "ام"}; //, "است"
	public static String[] ToBeVerbsPermutedForAlef = new String[] {"ییم", "یید", "یند", "یی", "یم", "ست"};
	public static String[] getToBeVerbs()
	{
		LinqSimulationArrayList<String> tmpList = new LinqSimulationArrayList<String>();

		tmpList.addAll(ToBeVerbsBase);
		tmpList.addAll(ToBeVerbsPermutedForHaaYaa);
		tmpList.addAll(ToBeVerbsPermutedForAlef);

		return tmpList.toArray(new String[0]);
	}

	public static String[] IndefiniteYaaBase = new String[] {"ی"};
	public static String[] IndefiniteYaaPermutedForHaaYaa = new String[] {"ای"};
	public static String[] IndefiniteYaaPermutedForAlef = new String[] {"یی"};
	public static String[] getIndefiniteYaa()
	{
		LinqSimulationArrayList<String> tmpList = new LinqSimulationArrayList<String>();

		tmpList.addAll(IndefiniteYaaBase);
		tmpList.addAll(IndefiniteYaaPermutedForHaaYaa);
		tmpList.addAll(IndefiniteYaaPermutedForAlef);

		return tmpList.toArray(new String[0]);
	}

	public static String[] YaaBadalAzKasre = new String[] {"ی"};

	public static String[] YaaNesbatBase = new String[] {"ی"};
	public static String[] YaaNesbatPermutedForHaaYaa = new String[] {"ای"};
	public static String[] YaaNesbatPermutedForAlef = new String[] {"یی"};
	public static String[] getYaaNesbat()
	{
		LinqSimulationArrayList<String> tmpList = new LinqSimulationArrayList<String>();

		tmpList.addAll(YaaNesbatBase);
		tmpList.addAll(YaaNesbatPermutedForHaaYaa);
		tmpList.addAll(YaaNesbatPermutedForAlef);

		return tmpList.toArray(new String[0]);
	}

	public static String[] EnumerableAdjectiveAmbigus = new String[] {"گانه"};
	public static String[] EnumerableAdjectiveOrdinal = new String[] {"مین", "م"};
	public static String[] getEnumerableAdjective()
	{
		LinqSimulationArrayList<String> tmpList = new LinqSimulationArrayList<String>();

		tmpList.addAll(EnumerableAdjectiveAmbigus);
		tmpList.addAll(EnumerableAdjectiveOrdinal);

		return tmpList.toArray(new String[0]);
	}

	public static String[] ComparativeAdjectives = new String[] {"تر", "ترین"};

	public static String[] PluralSignHaa = new String[] {"ها"};

	public static String[] PluralSignAanBase = new String[] {"ان"};
	public static String[] PluralSignAanPermutedForHaa = new String[] {"گان"};
	public static String[] PluralSignAanPermutedForAlef = new String[] {"یان"};
	public static String[] getPluralSignAan()
	{
		LinqSimulationArrayList<String> tmpList = new LinqSimulationArrayList<String>();

		tmpList.addAll(PluralSignAanBase);
		tmpList.addAll(PluralSignAanPermutedForHaa);
		tmpList.addAll(PluralSignAanPermutedForAlef);

		return tmpList.toArray(new String[0]);
	}

}