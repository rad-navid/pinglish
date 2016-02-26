package SCICT.NLP.Morphology.Inflection.Conjugation;

import SCICT.NLP.Morphology.Inflection.*;

public class GroupPattern
{
	public String pattern;
	public STEM_ALPHA stemAlpha = STEM_ALPHA.values()[0];
	public STEM_TIME stemTime = STEM_TIME.values()[0];
	public int stemCount;

	public GroupPattern()
	{
		this.pattern = "";
		this.stemAlpha = STEM_ALPHA.X_X;
		this.stemTime = STEM_TIME.UNSET;
		this.stemCount = 0;
	}

	public final void addUnit(String stem)
	{
		if (this.stemCount > 0)
		{
			this.pattern += "|" + stem;
		}
		else
		{
			this.pattern += stem;
		}
		this.stemCount++;
	}

	public final boolean verbsEndWithVowel()
	{
		if (this.stemAlpha.getValue() == STEM_ALPHA.A_A.getValue() || this.stemAlpha.getValue() == STEM_ALPHA.B_A.getValue() || this.stemAlpha == STEM_ALPHA.X_A)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public final boolean verbsStartWithA()
	{
		if (this.stemAlpha.getValue() == STEM_ALPHA.A_A.getValue() || this.stemAlpha.getValue() == STEM_ALPHA.A_B.getValue() || this.stemAlpha == STEM_ALPHA.A_X)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}