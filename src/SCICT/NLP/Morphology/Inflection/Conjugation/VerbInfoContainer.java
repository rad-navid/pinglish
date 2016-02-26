package SCICT.NLP.Morphology.Inflection.Conjugation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerbInfoContainer
{
	private java.util.ArrayList<VerbEntry> entrys;
	private int m_index;

	public VerbInfoContainer()
	{
		entrys = new java.util.ArrayList<VerbEntry>();
		m_index = 0;
	}

	public final void ResetIndex()
	{
		m_index = 0;
	}

	private void addVerbEntry(String mazi, String mozare, ENUM_VERB_TRANSITIVITY transitivity, ENUM_VERB_TYPE verb_type, String _pishvand, String _felyar, String h_ezafe)
	{
		VerbEntry ve = new VerbEntry();
		ve.SetVerbEntry(mazi, mozare, transitivity, verb_type, _pishvand, _felyar, h_ezafe);
		this.entrys.add(ve);
	}

	public final boolean LoadStemFile(String fileName)
	{
		this.entrys.clear();

		// Specify file, instructions, and privelegdes
		File file = new File(fileName);

		// Create a new stream to read from a file
		BufferedReader sr = null;
		try {
			sr = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF16"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//int startPos = 0;
		String r = "([\\d])\\s+([\\d])\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)";
		Pattern pattern=Pattern.compile(r);
		Matcher m;

		String mazi = "";
		String mozare = "";
		ENUM_VERB_TRANSITIVITY trans = ENUM_VERB_TRANSITIVITY.INVALID;
		ENUM_VERB_TYPE vtype = ENUM_VERB_TYPE.INVALID;
		String felyar = "";
		String pishvand = "";
		String h_ezafe = "";

		String input = null;
		try {
			while ((input = sr.readLine()) != null)
			{
				m = pattern.matcher(input);

				if (m.find())
				{
					// Verb Type
					int tmp_val=Integer.parseInt(m.group(1));
					switch (tmp_val)
					{
						case 1:
							vtype = ENUM_VERB_TYPE.SADE;
							break;
						case 2:
							vtype = ENUM_VERB_TYPE.PISHVANDI;
							break;
						case 3:
							vtype = ENUM_VERB_TYPE.MORAKKAB;
							break;
						case 4:
							vtype = ENUM_VERB_TYPE.PISHVANDI_MORAKKAB;
							break;
						case 5:
							vtype = ENUM_VERB_TYPE.EBARATE_FELI;
							break;
						case 6:
							vtype = ENUM_VERB_TYPE.NAGOZAR;
							break;
						case 7:
							break;
						default:
							vtype = ENUM_VERB_TYPE.INVALID;
							break;
					}

					// Verb Transitivity
					if (m.group(2).equalsIgnoreCase("0"))
					{
						trans = ENUM_VERB_TRANSITIVITY.INTRANSITIVE;
					}
					else if (m.group(2).equalsIgnoreCase("1"))
					{
						trans = ENUM_VERB_TRANSITIVITY.TRANSITIVE;
					}
					else if (m.group(2).equalsIgnoreCase("2"))
					{
						trans = ENUM_VERB_TRANSITIVITY.BILATERAL;
					}

					// Verb Stem
					mazi = m.group(3);
					mozare = m.group(4);

					// Components
					felyar = m.group(5);
					if (felyar.equals("-"))
					{
						felyar = "";
					}
					pishvand = m.group(6);
					if (pishvand.equals("-"))
					{
						pishvand = "";
					}
					h_ezafe = m.group(7);
					if (h_ezafe.equals("-"))
					{
						h_ezafe = "";
					}

					this.addVerbEntry(mazi, mozare, trans, vtype, pishvand, felyar, h_ezafe);
				}
			}
			// Close StreamReader
			sr.close();

			// Close file
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		

		if (this.entrys.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

/*    public GroupPattern GetStemPatten(STEM_TIME st, STEM_ALPHA vs, ENUM_VERB_TYPE verb_type, ENUM_PATTERN_GENERALITY generality)
    {
        GroupPattern dp = new GroupPattern();
        dp.stemAlpha = vs;
        dp.stemTime = st;
        string generalVerb = ".{2,4}";

        switch (st)
        {
            case STEM_TIME.MAZI:
                switch (vs)
                {
                    case STEM_ALPHA.A_X:
                    case STEM_ALPHA.A_B:
                    case STEM_ALPHA.A_A:
                        if (generality == ENUM_PATTERN_GENERALITY.EXPLICIT)
                        {
                            this.entrys.ForEach(delegate(VerbEntry ve)
                            {
                                if ((ve.StartingAlpha(STEM_TIME.MAZI) == STEM_ALPHA.A_X) && (ve.verbType == verb_type))
                                    dp.addUnit(ve.GetTail(STEM_TIME.MAZI));
                            });
                            dp.pattern = "[آا](?:" + dp.pattern + ")";
                        }
                        else
                        {
                            dp.pattern = "[آا](?:" + generalVerb + ")";
                        }
                        break;

                    case STEM_ALPHA.B_X:
                    case STEM_ALPHA.B_B:
                    case STEM_ALPHA.B_A:
                        if (generality == ENUM_PATTERN_GENERALITY.EXPLICIT)
                        {
                            this.entrys.ForEach(delegate(VerbEntry ve)
                            {
                                if ((ve.StartingAlpha(STEM_TIME.MAZI) == STEM_ALPHA.B_X) && (ve.verbType == verb_type))
                                    dp.addUnit(ve.pastStem);
                            });
                        }
                        else
                        {
                            dp.pattern = generalVerb;
                        }
                        break;

                    default:
                        break;
                }
                break;

            case STEM_TIME.MOZARE:
                switch (vs)
                {
                    case STEM_ALPHA.A_B:
                        if (generality == ENUM_PATTERN_GENERALITY.EXPLICIT)
                        {
                            this.entrys.ForEach(delegate(VerbEntry ve)
                            {
                                if ((ve.BoundingAlpha(STEM_TIME.MOZARE) == STEM_ALPHA.A_B) && (ve.verbType == verb_type))
                                    dp.addUnit(ve.GetTail(STEM_TIME.MOZARE));
                            });
                            dp.pattern = "[آا](?:" + dp.pattern + ")";
                        }
                        else
                        {
                            dp.pattern = "[آا](?:" + generalVerb + ")";
                        }
                        break;

                    case STEM_ALPHA.A_A:
                        if (generality == ENUM_PATTERN_GENERALITY.EXPLICIT)
                        {
                            this.entrys.ForEach(delegate(VerbEntry ve)
                            {
                                if ((ve.BoundingAlpha(STEM_TIME.MOZARE) == STEM_ALPHA.A_A) && (ve.verbType == verb_type))
                                    dp.addUnit(ve.GetTail(STEM_TIME.MOZARE));
                            });
                            dp.pattern = "[آا](?:" + dp.pattern + ")";
                        }
                        else
                        {
                            dp.pattern = "[آا](?:" + generalVerb + ")";
                        }
                        break;

                    case STEM_ALPHA.B_B:
                        if (generality == ENUM_PATTERN_GENERALITY.EXPLICIT)
                        {
                            this.entrys.ForEach(delegate(VerbEntry ve)
                            {
                                if ((ve.BoundingAlpha(STEM_TIME.MOZARE) == STEM_ALPHA.B_B) && (ve.verbType == verb_type))
                                    dp.addUnit(ve.presentStem);
                            });
                        }
                        else
                        {
                            dp.pattern = generalVerb;
                        }
                        break;

                    case STEM_ALPHA.B_A:
                        if (generality == ENUM_PATTERN_GENERALITY.EXPLICIT)
                        {
                            this.entrys.ForEach(delegate(VerbEntry ve)
                            {
                                if ((ve.BoundingAlpha(STEM_TIME.MOZARE) == STEM_ALPHA.B_A) && (ve.verbType == verb_type))
                                    dp.addUnit(ve.presentStem);
                            });
                        }
                        else
                        {
                            dp.pattern = "[آا](?:" + generalVerb + ")";
                        }
                        break;

                    default:
                        break;
                }
                break;
        }

        return dp;
    }*/

	public final GroupPattern GetPishvandPattern(ENUM_VERB_TYPE vtype)
	{
		GroupPattern gp = new GroupPattern();
		java.util.ArrayList<String> strList = new java.util.ArrayList<String>();

		for (VerbEntry ve : this.entrys) 
		{
			if ((! ve.pishvand.equalsIgnoreCase("")) && (ve.verbType == vtype) && !strList.contains(ve.pishvand))
			{
				strList.add(ve.pishvand);
			}
		}

		for (String s : strList) {
			gp.addUnit(s);
		}

		return gp;
	}

	public final GroupPattern GetFelyarPattern(ENUM_VERB_TYPE vtype)
	{
		GroupPattern gp = new GroupPattern();
		java.util.ArrayList<String> strList = new java.util.ArrayList<String>();

		for (VerbEntry ve : this.entrys) 
		{
			if ((!ve.felyar.equalsIgnoreCase("")) && (ve.verbType == vtype) && !strList.contains(ve.felyar))
			{
				strList.add(ve.felyar);
			}
		}

		for (String s : strList) {
			gp.addUnit(s);
		}

		return gp;
	}

	public final VerbEntry GetVerbEntry()
	{
		if (m_index < entrys.size())
		{
			VerbEntry item=entrys.get(m_index++);
			return item;
		}
		else
		{
			return null;
		}
	}

}