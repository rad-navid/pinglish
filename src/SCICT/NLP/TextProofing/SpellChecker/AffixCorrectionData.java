package SCICT.NLP.TextProofing.SpellChecker;

import SCICT.NLP.Morphology.Inflection.*;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.Parsers.*;
import SCICT.NLP.Morphology.Lemmatization.*;
import SCICT.NLP.Utility.WordGenerator.*;
import SCICT.Utility.*;

public class AffixCorrectionData
{
	public String m_word;
	public int m_suggestionCount;
	public boolean m_havePhoneticSuffixProblem;
	public String[] m_suggestions;
}