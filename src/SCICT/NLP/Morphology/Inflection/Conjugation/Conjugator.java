package SCICT.NLP.Morphology.Inflection.Conjugation;

import Helper.LinqSimulationArrayList;
import SCICT.Utility.*;
import SCICT.NLP.Morphology.Inflection.*;

// Virastyar
// http://www.virastyar.ir
// Copyright (C) 2011 Supreme Council for Information and Communication Technology (SCICT) of Iran
// 
// This file is part of Virastyar.
// 
// Virastyar is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Virastyar is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Virastyar.  If not, see <http://www.gnu.org/licenses/>.
// 
// Additional permission under GNU GPL version 3 section 7
// The sole exception to the license's terms and requierments might be the
// integration of Virastyar with Microsoft Word (any version) as an add-in.


public class Conjugator
{
	private VerbInfoContainer m_verbInfoContainer;
	private VerbWrapper m_verbWrapper;

	public Conjugator(VerbInfoContainer dic)
	{
		m_verbInfoContainer = dic;
		m_verbInfoContainer.ResetIndex();
	}

	private ENUM_TENSE_TIME[] m_timeList = {ENUM_TENSE_TIME.MAZI_E_SADE, ENUM_TENSE_TIME.MAZI_E_ESTEMRARI, ENUM_TENSE_TIME.MAZI_E_SADEYE_NAGHLI, ENUM_TENSE_TIME.MAZI_E_ESTEMRARIE_NAGHLI, ENUM_TENSE_TIME.MOZARE_E_EKHBARI, ENUM_TENSE_TIME.MOZARE_E_ELTEZAMI, ENUM_TENSE_TIME.AMR};

	public final String[] Conjugate(ENUM_VERB_TYPE verbType)
	{
		VerbEntry ve;
		m_verbWrapper = new VerbWrapper();

		LinqSimulationArrayList<String> lst = new LinqSimulationArrayList<String>();
		m_verbInfoContainer.ResetIndex();
		while ((ve = m_verbInfoContainer.GetVerbEntry()) != null)
		{
			m_verbWrapper.SetVerbEntry(ve);
			m_verbWrapper.setTensePassivity(ENUM_TENSE_PASSIVITY.ACTIVE);

			if (verbType.Has(ENUM_VERB_TYPE.INFINITIVE))
			{
				lst.addAll(m_verbWrapper.PrintInfinitive());
			}

			if (!verbType.Has(ve.verbType))
			{
				continue;
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Conjugate

			for (ENUM_TENSE_TIME tm : m_timeList)
			{
				m_verbWrapper.setTenseTime(tm);
				//foreach (ENUM_TENSE_OBJECT obj in Enum.GetValues(typeof(ENUM_TENSE_OBJECT)))
				for (ENUM_TENSE_POSITIVITY pos : ENUM_TENSE_POSITIVITY.values())
				{
					m_verbWrapper.setTensePositivity(pos);
					if (!m_verbWrapper.IsValidPositivity())
					{
						continue;
					}

					for (ENUM_TENSE_PERSON person : ENUM_TENSE_PERSON.values())
					{
						if (person.getValue() == ENUM_TENSE_PERSON.INVALID.getValue() || person == ENUM_TENSE_PERSON.UNMACHED_SEGMENT)
						{
							continue;
						}

						m_verbWrapper.setTensePerson(person);

						if (m_verbWrapper.isValidTense(ENUM_VERB_TRANSITIVITY.INTRANSITIVE))
						{
							String tmpString=m_verbWrapper.PrintVerb();
							lst.add(tmpString.split("[ ]", -1)[0]);
						}
					}
				}
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion
		}

		//return lst.Distinct().ToArray();
		return lst.toArray(new String[0]);
	}

}