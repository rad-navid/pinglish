package SCICT.NLP.Morphology.Inflection.Conjugation;


public class VerbEntry
{

	public String pastStem;
	public String presentStem;
	public ENUM_VERB_TRANSITIVITY transitivity = ENUM_VERB_TRANSITIVITY.values()[0];
	public ENUM_VERB_TYPE verbType = ENUM_VERB_TYPE.values()[0];
	public String pishvand;
	public String felyar;
	public String harfe_ezafe;

	private STEM_ALPHA alph_start_mazi = STEM_ALPHA.values()[0];
	private STEM_ALPHA alph_start_moza = STEM_ALPHA.values()[0];
	private STEM_ALPHA alph_end_mazi = STEM_ALPHA.values()[0];
	private STEM_ALPHA alph_end_moza = STEM_ALPHA.values()[0];
	private STEM_ALPHA alph_bound_mazi = STEM_ALPHA.values()[0];
	private STEM_ALPHA alph_bound_moza = STEM_ALPHA.values()[0];

	public VerbEntry()
	{
		this.pastStem = "";
		this.presentStem = "";
		this.transitivity = ENUM_VERB_TRANSITIVITY.INVALID;
		this.verbType = ENUM_VERB_TYPE.INVALID;
		this.pishvand = "";
		this.felyar = "";
		this.harfe_ezafe = "";
	}

	public VerbEntry(String past_stem, String present_stem, ENUM_VERB_TRANSITIVITY transitivity)
	{
		this.SetVerbEntry(past_stem, present_stem, transitivity, ENUM_VERB_TYPE.SADE, "", "", "");
	}

	public VerbEntry(String past_stem, String present_stem, ENUM_VERB_TRANSITIVITY transitivity, ENUM_VERB_TYPE verb_type, String _pishvand, String _felyar, String h_ezafe)
	{
		this.SetVerbEntry(past_stem, present_stem, transitivity, verb_type, _pishvand, _felyar, h_ezafe);
	}

	public final void SetVerbEntry(String past_stem, String present_stem, ENUM_VERB_TRANSITIVITY transitivity, ENUM_VERB_TYPE verb_type, String _pishvand, String _felyar, String h_ezafe)
	{
		this.pastStem = past_stem;
		this.presentStem = present_stem;
		this.transitivity = transitivity;
		this.verbType = verb_type;
		this.pishvand = _pishvand;
		this.felyar = _felyar;
		this.harfe_ezafe = h_ezafe;
		this.SetStemAlpha();
	}

	public final void SetVerbEntry(String pastStem, String presentStem, ENUM_VERB_TRANSITIVITY transitivity)
	{
		this.SetVerbEntry(pastStem, presentStem, transitivity, ENUM_VERB_TYPE.SADE, "", "", "");
	}

	public final String GetStem(ENUM_TENSE_TIME time, ENUM_TENSE_PASSIVITY passivity)
	{
		String verbStem = "";

		switch (time)
		{
			case MAZI_E_SADE:
			case MAZI_E_ESTEMRARI:
			case MAZI_E_BAEID:
			case MAZI_E_MOSTAMAR:
			case MAZI_E_SADEYE_NAGHLI:
			case MAZI_E_ESTEMRARIE_NAGHLI:
			case MAZI_E_BAEIDE_NAGHLI:
			case MAZI_E_MOSTAMARE_NAGHLI:
			case MAZI_E_ELTEZAMI:
			case AYANDE:
				verbStem = this.pastStem;
				break;
			case MOZARE_E_EKHBARI:
			case MOZARE_E_MOSTAMAR:
			case MOZARE_E_ELTEZAMI:
			case AMR:
				switch (passivity)
				{
					case ACTIVE:
						verbStem = this.presentStem;
						break;
					case PASSIVE:
						verbStem = this.pastStem;
						break;
					default:
						verbStem = "";
						break;
				}
				break;

			default:
				verbStem = "";
				break;
		}
		return verbStem;
	}

	public final String GetStem(Verb v)
	{
		return this.GetStem(v.getTenseTime(), v.getTensePassivity());
	}

	public final String GetTail(STEM_TIME st)
	{
		String stem = "";

		switch (st)
		{
			case MAZI:
				stem = this.pastStem;
				break;
			case MOZARE:
				stem = this.presentStem;
				break;
		}

		stem = stem.substring(1);

		return stem;
	}

	public final STEM_ALPHA StartingAlpha(STEM_TIME stemTime)
	{
		switch (stemTime)
		{
			case MAZI:
				return this.alph_start_mazi;
			case MOZARE:
				return this.alph_start_moza;
			default:
				break;
		}
		return STEM_ALPHA.X_X;
	}

	public final STEM_ALPHA EndingAlpha(STEM_TIME stemTime)
		/* only for present stem */
	{
		switch (stemTime)
		{
			case MAZI:
				return alph_end_mazi;
			case MOZARE:
				return alph_end_moza;

			default:
				break;
		}
		return STEM_ALPHA.X_X;
	}

	public final STEM_ALPHA BoundingAlpha(STEM_TIME stemTime)
	{
		STEM_ALPHA start, end, bound;

		start = this.StartingAlpha(stemTime);
		end = this.EndingAlpha(stemTime);
		bound = STEM_ALPHA.X_X;

		if (start.getValue() == STEM_ALPHA.A_X.getValue() && end == STEM_ALPHA.X_A)
		{
			bound = STEM_ALPHA.A_A;
		}
		else if (start.getValue() == STEM_ALPHA.A_X.getValue() && end == STEM_ALPHA.X_B)
		{
			bound = STEM_ALPHA.A_B;
		}
		else if (start.getValue() == STEM_ALPHA.B_X.getValue() && end == STEM_ALPHA.X_A)
		{
			bound = STEM_ALPHA.B_A;
		}
		else if (start.getValue() == STEM_ALPHA.B_X.getValue() && end == STEM_ALPHA.X_B)
		{
			bound = STEM_ALPHA.B_B;
		}

		return bound;
	}

	public final boolean IsDoHamkard()
	{
		if (this.pastStem.equals("کرد") || this.pastStem.equals("نمود"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private void SetStemAlpha()
	{
		String alpha = "";

		alpha = this.pastStem.substring(0, 1);
		alph_start_mazi = (alpha.equals("آ") || alpha.equals("ا")) ? STEM_ALPHA.A_X : STEM_ALPHA.B_X;
		if (alpha.equals("v"))
		{
			pastStem = pastStem.substring(1);
		}

		alpha = this.presentStem.substring(0, 1);
		alph_start_moza = (alpha.equals("آ") || alpha.equals("ا")) ? STEM_ALPHA.A_X : STEM_ALPHA.B_X;
		if (alpha.equals("v"))
		{
			presentStem = presentStem.substring(1);
		}

		alpha = this.presentStem.substring(presentStem.length() - 1);
		alph_end_moza = (alpha.equals("ا") || alpha.equals("آ") || alpha.equals("و")) ? STEM_ALPHA.X_A : STEM_ALPHA.X_B;
		if (alpha.equals("v"))
		{
			presentStem = presentStem.substring(0, presentStem.length() - 1);
		}

		alph_end_mazi = STEM_ALPHA.X_X;
	}
}