package SCICT.NLP.Morphology.Inflection.Conjugation;


public class VerbWrapper extends Verb
{
	private VerbEntry entry;

	public VerbWrapper()
	{
		this.entry = new VerbEntry("كرد", "كن", ENUM_VERB_TRANSITIVITY.TRANSITIVE);
	}

	public VerbWrapper(VerbEntry ve)
	{
		entry = ve;
	}

	public VerbWrapper(String bon_mazi, String boe_mozare, String pishvand, String felyar, String hezafe, ENUM_VERB_TRANSITIVITY transitivity)
	{
		this.entry = new VerbEntry(bon_mazi, boe_mozare, transitivity, ENUM_VERB_TYPE.INVALID, pishvand, felyar, hezafe);
	}

	public final void SetVerbEntry(VerbEntry ve)
	{
		entry = ve;
	}

	private PRONOUN_TYPE getPronounType()
	{
		PRONOUN_TYPE pt;
		ENUM_TENSE_TIME time = this.getTenseTime();

		switch (time)
		{
			case MAZI_E_SADE:
			case MAZI_E_ESTEMRARI:
			case MAZI_E_BAEID:
			case MAZI_E_MOSTAMAR:
				pt = PRONOUN_TYPE.MAZI;
				break;
			case MAZI_E_SADEYE_NAGHLI:
			case MAZI_E_ESTEMRARIE_NAGHLI:
			case MAZI_E_BAEIDE_NAGHLI:
			case MAZI_E_MOSTAMARE_NAGHLI:
				pt = PRONOUN_TYPE.NAGHLI;
				break;
			case MAZI_E_ELTEZAMI:
			case MOZARE_E_EKHBARI:
			case MOZARE_E_MOSTAMAR:
			case MOZARE_E_ELTEZAMI:
			case AYANDE:
				pt = PRONOUN_TYPE.MOZARE;
				break;
			case AMR:
				pt = PRONOUN_TYPE.AMR;
				break;
			default:
				pt = PRONOUN_TYPE.INVALID;
				break;
		}

		return pt;
	}

	private String printPronoun()
	{
		ENUM_TENSE_TIME time = this.getTenseTime();
		ENUM_TENSE_PERSON person = this.getTensePerson();

		PRONOUN_TYPE pt = this.getPronounType();
		String postfix = "";

		switch (pt)
		{
			case MAZI:
				switch (person)
				{
					case SINGULAR_FIRST:
						postfix = "م";
						break;
					case SINGULAR_SECOND:
						postfix = "ی";
						break;
					case SINGULAR_THIRD:
						postfix = "";
						break;
					case PLURAL_FIRST:
						postfix = "یم";
						break;
					case PLURAL_SECOND:
						postfix = "ید";
						break;
					case PLURAL_THIRD:
						postfix = "ند";
						break;
					default:
						postfix = "";
						break;
				}
				break;

			case MOZARE:
				switch (person)
				{
					case SINGULAR_FIRST:
						postfix = "م";
						break;
					case SINGULAR_SECOND:
						postfix = "ی";
						break;
					case SINGULAR_THIRD:
						postfix = "د";
						break;
					case PLURAL_FIRST:
						postfix = "یم";
						break;
					case PLURAL_SECOND:
						postfix = "ید";
						break;
					case PLURAL_THIRD:
						postfix = "ند";
						break;
					default:
						postfix = "";
						break;
				}
				break;

			case NAGHLI:
				switch (person)
				{
					case SINGULAR_FIRST:
						postfix = "‌ام";
						break;
					case SINGULAR_SECOND:
						postfix = "‌ای";
						break;
					case SINGULAR_THIRD:
						postfix = " است";
						break;
					case PLURAL_FIRST:
						postfix = "‌ایم";
						break;
					case PLURAL_SECOND:
						postfix = "‌اید";
						break;
					case PLURAL_THIRD:
						postfix = "‌اند";
						break;
					default:
						postfix = "";
						break;
				}
				break;

			case AMR:
				switch (person)
				{
					case SINGULAR_SECOND:
						postfix = "";
						break;
					case PLURAL_SECOND:
						postfix = "ید";
						break;
					default:
						postfix = "";
						break;
				}
				break;

			default:
				break;
		}

		return postfix;
	}

	private String printObjectPronoun()
	{
		ENUM_TENSE_OBJECT obj = this.getTenseObjectPronoun();
		String sop = "";

		switch (obj)
		{
			case SINGULAR_FIRST:
				sop = "م";
				break;
			case SINGULAR_SECOND:
				sop = "ت";
				break;
			case SINGULAR_THIRD:
				sop = "ش";
				break;
			case PLURAL_FIRST:
				sop = "مان";
				break;
			case PLURAL_SECOND:
				sop = "تان";
				break;
			case PLURAL_THIRD:
				sop = "شان";
				break;
			default:
				sop = "";
				break;
		}

		return sop;
	}

	private String printStem(String before, String after)
	{
		String stem = "";

		switch (this.getStemTime())
		{
			case MAZI:
				stem = this.entry.pastStem;
				break;

			case MOZARE:
				stem = this.entry.presentStem;
				break;

			default:
				stem = "";
				break;
		}

		/****** Transform Stem ******************************/

		boolean stemStartsWithA = (entry.StartingAlpha(getStemTime()) == STEM_ALPHA.A_X);
		boolean No_Space_Before = (before.length() > 0 && before.charAt(before.length() - 1) != '‌' && before.charAt(before.length() - 1) != ' ');
		boolean stemEndsWithA = (entry.EndingAlpha(getStemTime()) == STEM_ALPHA.X_A);
		boolean No_Space_After = (after.length() > 0 && after.charAt(0) != '‌' && after.charAt(0) != ' ');

		if (stemStartsWithA)
		{
			if (No_Space_Before)
			{
				if (stem.startsWith("آ"))
				{
					stem = "یا" + stem.substring(1);
				}
				else
				{
					stem = "ی" + stem.substring(1);
				}
			}
		}

		if (stemEndsWithA)
		{
			if (No_Space_After)
			{
				stem += "ی";
			}
		}

		return stem;
	}

	private String printPositivity()
	{
		ENUM_TENSE_TIME time = this.getTenseTime();
		ENUM_TENSE_POSITIVITY positivity = this.getTensePositivity();

		switch (time)
		{
			case MAZI_E_SADE:
			case MAZI_E_SADEYE_NAGHLI:
				switch (positivity)
				{
					case POSITIVE:
						return "";
					case NEGATIVE:
						return "ن";
					case UNUSUAL_POSITIVE:
						return "ب";
					case UNUSUAL_NEGATIVE:
						return "م";
					default:
						return "";
				}

			case MAZI_E_ESTEMRARI:
			case MAZI_E_BAEID:
			case MAZI_E_MOSTAMAR:
			case MAZI_E_ESTEMRARIE_NAGHLI:
			case MAZI_E_BAEIDE_NAGHLI:
			case MAZI_E_MOSTAMARE_NAGHLI:
			case MAZI_E_ELTEZAMI:
			case MOZARE_E_EKHBARI:
			case MOZARE_E_MOSTAMAR:
			case AYANDE:
				switch (positivity)
				{
					case POSITIVE:
						return "";
					case NEGATIVE:
						return "ن";
					default:
						return "";
				}

			case MOZARE_E_ELTEZAMI:
			case AMR:
				switch (positivity)
				{
					case POSITIVE:
						return "ب";
					case NEGATIVE:
						return "ن";
					case UNUSUAL_POSITIVE:
						return "";
					case UNUSUAL_NEGATIVE:
						return "م";
					default:
						return "";
				}

			default:
				return "";
		}

	}

	private String printPishvand()
	{
		return this.entry.pishvand;
	}

	private String printFelyar()
	{
		return this.entry.felyar + ((entry.felyar.length() == 0) ? "" : " ");
	}

	private String passiveHole(String v)
	{
		if (this.getTensePassivity() == ENUM_TENSE_PASSIVITY.PASSIVE && this.entry.IsDoHamkard())
		{
			return "";
		}
		else
		{
			return v;
		}
	}

	public final String PrintVerb()
	{
		String verbWord = "";
		ENUM_TENSE_PASSIVITY passivity = this.getTensePassivity();
		ENUM_TENSE_TIME time = this.getTenseTime();

		String positivity = this.printPositivity();
		String pronoun = this.printPronoun();

		switch (time)
		{
			case MAZI_E_SADE:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = printFelyar() + printPishvand() + positivity + this.printStem(positivity, pronoun) + pronoun + this.printObjectPronoun();
						break;
					case PASSIVE:
						verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "شد" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MAZI_E_ESTEMRARI:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = printFelyar() + printPishvand() + positivity + "می‌" + this.printStem("می‌", pronoun) + pronoun;
						break;
					case PASSIVE:
						verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "می‌شد" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MAZI_E_BAEID:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = printFelyar() + printPishvand() + positivity + this.printStem(positivity, "ه بود") + "ه بود" + pronoun;
						break;
					case PASSIVE:
						verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "شده بود" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MAZI_E_MOSTAMAR:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = "داشت" + pronoun + " " + printFelyar() + printPishvand() + positivity + "می‌" + this.printStem("می‌", pronoun) + pronoun;
						break;
					case PASSIVE:
						verbWord = "داشت" + pronoun + " " + printFelyar() + printPishvand() + this.passiveHole(this.printStem(" ", "ه ") + "ه ") + positivity + "می‌شد" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MAZI_E_SADEYE_NAGHLI:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = printFelyar() + printPishvand() + positivity + this.printStem(positivity, "ه") + "ه" + pronoun;
						break;
					case PASSIVE:
						verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "شده" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MAZI_E_ESTEMRARIE_NAGHLI:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = printFelyar() + printPishvand() + positivity + "می‌" + this.printStem("می‌", "ه") + "ه" + pronoun;
						break;
					case PASSIVE:
						verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "می‌شده" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MAZI_E_BAEIDE_NAGHLI:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = printFelyar() + printPishvand() + positivity + this.printStem(positivity, "ه بوده") + "ه بوده" + pronoun;
						break;
					case PASSIVE:
						verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "شده بوده" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MAZI_E_MOSTAMARE_NAGHLI:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = "داشته" + pronoun + " " + printFelyar() + printPishvand() + positivity + "می‌" + this.printStem("می‌", "ه") + "ه" + pronoun;
						break;
					case PASSIVE:
						verbWord = "داشته" + pronoun + " " + printFelyar() + printPishvand() + this.passiveHole(this.printStem(" ", "ه ") + "ه ") + positivity + "می‌شده" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MAZI_E_ELTEZAMI:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = printFelyar() + printPishvand() + positivity + this.printStem(positivity, "ه باش") + "ه باش" + pronoun;
						break;
					case PASSIVE:
						verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "شده باش" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MOZARE_E_EKHBARI:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = printFelyar() + printPishvand() + positivity + "می‌" + this.printStem("می‌", pronoun) + pronoun;
						break;
					case PASSIVE:
						verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "می‌شو" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MOZARE_E_MOSTAMAR:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = "دار" + pronoun + " " + printFelyar() + printPishvand() + positivity + "می‌" + this.printStem("می‌", pronoun) + pronoun;
						break;
					case PASSIVE:
						verbWord = "دار" + pronoun + " " + printFelyar() + printPishvand() + this.passiveHole(this.printStem(" ", "ه ") + "ه ") + positivity + "می‌شو" + pronoun;
						break;
					default:
						break;
				}
				break;

			case MOZARE_E_ELTEZAMI:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = printFelyar() + printPishvand() + positivity + this.printStem(positivity, pronoun) + pronoun;
						break;
					case PASSIVE:
						verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "شو" + pronoun;
						break;
					default:
						break;
				}
				break;

			case AYANDE:
				switch (passivity)
				{
					case ACTIVE:
						verbWord = printFelyar() + printPishvand() + positivity + "خواه" + pronoun + " " + this.printStem(" ", "");
						break;
					case PASSIVE:
						verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "خواه" + pronoun + " شد";
						break;
					default:
						break;
				}
				break;

			case AMR:
				if (this.getTensePerson() == ENUM_TENSE_PERSON.SINGULAR_SECOND || this.getTensePerson() == ENUM_TENSE_PERSON.PLURAL_SECOND)
				{
					switch (passivity)
					{
						case ACTIVE:
							verbWord = printFelyar() + printPishvand() + positivity + this.printStem(positivity, pronoun) + pronoun;
							break;
						case PASSIVE:
							verbWord = printFelyar() + printPishvand() + this.passiveHole(this.printStem("", "ه ") + "ه ") + positivity + "شو" + pronoun;
							break;
						default:
							break;
					}
				}
				else
				{
					verbWord = "";
				}
				break;

			default:
				verbWord = "";
				break;
		}

		return verbWord;
	}

	public final String[] PrintInfinitive()
	{
		java.util.ArrayList<String> infinitives = new java.util.ArrayList<String>();

		if (this.entry.verbType.Is(ENUM_VERB_TYPE.PISHVANDI))
		{
			infinitives.add(this.entry.pishvand + this.entry.pastStem + "ن");
			infinitives.add(this.entry.pishvand + "ن" + this.entry.pastStem + "ن");
		}
		else if (this.entry.verbType.Is(ENUM_VERB_TYPE.SADE))
		{
			infinitives.add(this.entry.pastStem + "ن");
			infinitives.add("ن" + this.entry.pastStem + "ن");
		}

		return infinitives.toArray(new String[0]);
	}

}