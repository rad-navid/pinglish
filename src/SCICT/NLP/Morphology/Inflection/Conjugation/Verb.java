package SCICT.NLP.Morphology.Inflection.Conjugation;

import SCICT.Utility.*;
import SCICT.NLP.Morphology.Inflection.*;

public class Verb
{
	private int ID;

	public Verb()
	{
		this.resetVerb();
	}

	public Verb(int verbID)
	{
		this.ID = verbID;
	}

	public Verb(ENUM_TENSE_TIME time, ENUM_TENSE_PASSIVITY passivity, ENUM_TENSE_POSITIVITY positivity, ENUM_TENSE_PERSON person)
	{
		this.setTense(time, passivity, positivity, person);
	}

	public final void resetVerb()
	{
		this.ID = 0x00FFFFFF;
	}

	public final void setTense(ENUM_TENSE_TIME time, ENUM_TENSE_PASSIVITY passivity, ENUM_TENSE_POSITIVITY positivity, ENUM_TENSE_PERSON person)
	{
		this.resetVerb();
		this.setTensePerson(person);
		this.setTenseTime(time);
		this.setTensePassivity(passivity);
		this.setTensePositivity(positivity);
		this.setTenseType(ENUM_VERB_TYPE.SADE);
	}

	public final void setTense(ENUM_TENSE_TIME time, ENUM_TENSE_PASSIVITY passivity, ENUM_TENSE_POSITIVITY positivity)
	{
		this.resetVerb();
		this.setTenseTime(time);
		this.setTensePassivity(passivity);
		this.setTensePositivity(positivity);
		this.setTenseType(ENUM_VERB_TYPE.SADE);
	}

	public final void setTensePerson(ENUM_TENSE_PERSON person)
	{
		this.ID = this.ID & ~ENUM_TENSE_PERSON.INVALID.getValue();
		this.ID |= person.getValue();
	}

	public final void setTenseTime(ENUM_TENSE_TIME time)
	{
		this.ID = this.ID & ~ENUM_TENSE_TIME.INVALID.getValue();
		this.ID |= time.getValue();
	}

	public final void setTensePassivity(ENUM_TENSE_PASSIVITY passivity)
	{
		this.ID = this.ID & ~ENUM_TENSE_PASSIVITY.INVALID.getValue();
		this.ID |= passivity.getValue();
	}

	public final void setTensePositivity(ENUM_TENSE_POSITIVITY positivity)
	{
		this.ID = this.ID & ~ENUM_TENSE_POSITIVITY.INVALID.getValue();
		this.ID |= positivity.getValue();
	}

	public final void setTenseObject(ENUM_TENSE_OBJECT object_pro)
	{
		this.ID = this.ID & ~ENUM_TENSE_OBJECT.INVALID.getValue();
		this.ID |= object_pro.getValue();
	}

	public final void setTenseType(ENUM_VERB_TYPE type)
	{
		this.ID = this.ID & ~ENUM_VERB_TYPE.INVALID.getValue();
		this.ID |= type.getValue();
	}

	public final ENUM_TENSE_PERSON getTensePerson()
	{
		int mask = ENUM_TENSE_PERSON.INVALID.getValue();

		ENUM_TENSE_PERSON vtp = ENUM_TENSE_PERSON.forValue(this.ID & mask);

		switch (vtp)
		{
			case SINGULAR_FIRST:
			case SINGULAR_SECOND:
			case SINGULAR_THIRD:
			case PLURAL_FIRST:
			case PLURAL_SECOND:
			case PLURAL_THIRD:
			case UNMACHED_SEGMENT:
				return vtp;
			default:
				return ENUM_TENSE_PERSON.INVALID;
		}
	}

	public final ENUM_TENSE_TIME getTenseTime()
	{
		int mask = ENUM_TENSE_TIME.INVALID.getValue();

		ENUM_TENSE_TIME vtt = ENUM_TENSE_TIME.forValue(this.ID & mask);

		switch (vtt)
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
			case MOZARE_E_EKHBARI:
			case MOZARE_E_MOSTAMAR:
			case MOZARE_E_ELTEZAMI:
			case AYANDE:
			case AMR:
				return vtt;
			default:
				return ENUM_TENSE_TIME.INVALID;
		}

	}

	public final ENUM_TENSE_PASSIVITY getTensePassivity()
	{
		int mask = ENUM_TENSE_PASSIVITY.INVALID.getValue();

		ENUM_TENSE_PASSIVITY vtp = ENUM_TENSE_PASSIVITY.forValue(this.ID & mask);

		switch (vtp)
		{
			case ACTIVE:
			case PASSIVE:
				return vtp;
			default:
				return ENUM_TENSE_PASSIVITY.INVALID;
		}
	}

	public final ENUM_TENSE_POSITIVITY getTensePositivity()
	{
		int mask = ENUM_TENSE_POSITIVITY.INVALID.getValue();

		ENUM_TENSE_POSITIVITY vtp = ENUM_TENSE_POSITIVITY.forValue(this.ID & mask);

		switch (vtp)
		{
			case POSITIVE:
			case NEGATIVE:
			case UNUSUAL_NEGATIVE:
			case UNUSUAL_POSITIVE:
			case WRONG_UNDETECTED:
				return vtp;
			default:
				return ENUM_TENSE_POSITIVITY.INVALID;
		}
	}

	public final ENUM_TENSE_OBJECT getTenseObjectPronoun()
	{
		int mask = ENUM_TENSE_OBJECT.INVALID.getValue();

		ENUM_TENSE_OBJECT vto = ENUM_TENSE_OBJECT.forValue(this.ID & mask);

		switch (vto)
		{
			case SINGULAR_FIRST:
			case SINGULAR_SECOND:
			case SINGULAR_THIRD:
			case PLURAL_FIRST:
			case PLURAL_SECOND:
			case PLURAL_THIRD:
			case NOT_SET:
			case WRONGE:
				return vto;
			default:
				return ENUM_TENSE_OBJECT.INVALID;
		}
	}

	public final ENUM_VERB_TYPE getTenseType()
	{
		int mask = ENUM_VERB_TYPE.INVALID.getValue();

		ENUM_VERB_TYPE vtt = ENUM_VERB_TYPE.forValue(this.ID & mask);
		int VerbCode=vtt.getValue();
		
		if(VerbCode==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.SADE)||
				VerbCode==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.PISHVANDI)||
				VerbCode==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.PISHVANDI_MORAKKAB)||
				VerbCode==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.MORAKKAB)||
				VerbCode==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.EBARATE_FELI)||
				VerbCode==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.NAGOZAR)||
				VerbCode==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.ESNADI)||
				VerbCode==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.GHEIRE_SHAKHSI)
				)
			
			return vtt;
		else
			return ENUM_VERB_TYPE.INVALID;
	}

	public final STEM_TIME getStemTime()
	{
		STEM_TIME st = STEM_TIME.UNSET;

		switch (this.getTenseTime())
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
				st = STEM_TIME.MAZI;
				break;
			case MOZARE_E_EKHBARI:
			case MOZARE_E_MOSTAMAR:
			case MOZARE_E_ELTEZAMI:
			case AMR:
				switch (this.getTensePassivity())
				{
					case ACTIVE:
						st = STEM_TIME.MOZARE;
						break;
					case PASSIVE:
						st = STEM_TIME.MAZI;
						break;
				}
				break;
			default:
				break;
		}

		return st;
	}

	public final String printTitlePerson()
	{
		switch (this.getTensePerson())
		{
			case SINGULAR_FIRST:
				return "اول شخص مفرد";
			case SINGULAR_SECOND:
				return "دوم شخص مفرد";
			case SINGULAR_THIRD:
				return "سوم شخص مفرد";
			case PLURAL_FIRST:
				return "اول شخص جمع";
			case PLURAL_SECOND:
				return "دوم شخص جمع";
			case PLURAL_THIRD:
				return "سوم شخص جمع";
			default:
				return "";
		}
	}

	public final String printTitleTime()
	{
		switch (this.getTenseTime())
		{
			case MAZI_E_SADE:
				return "ماضی ساده";
			case MAZI_E_ESTEMRARI:
				return "ماضی استمراری";
			case MAZI_E_BAEID:
				return "ماضی بعید";
			case MAZI_E_MOSTAMAR:
				return "ماضی مستمر";
			case MAZI_E_SADEYE_NAGHLI:
				return "ماضی ساده نقلی";
			case MAZI_E_ESTEMRARIE_NAGHLI:
				return "ماضی استمراری نقلی";
			case MAZI_E_BAEIDE_NAGHLI:
				return "ماضی بعید نقلی";
			case MAZI_E_MOSTAMARE_NAGHLI:
				return "ماضی مستمر نقلی";
			case MAZI_E_ELTEZAMI:
				return "ماضی التزامی";
			case MOZARE_E_EKHBARI:
				return "مضارع اخباری";
			case MOZARE_E_MOSTAMAR:
				return "مضارع مستمر";
			case MOZARE_E_ELTEZAMI:
				return "مضارع التزامی";
			case AYANDE:
				return "آینده";
			case AMR:
				return "امر";
			default:
				return "";
		}
	}

	public final String printTitleStemTime()
	{
		String stemTitle = "";

		switch (this.getTenseTime())
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
				stemTitle = "ماضی";
				break;
			case MOZARE_E_EKHBARI:
			case MOZARE_E_MOSTAMAR:
			case MOZARE_E_ELTEZAMI:
			case AMR:
				switch (this.getTensePassivity())
				{
					case ACTIVE:
						stemTitle = "مضارع";
						break;
					case PASSIVE:
						stemTitle = "ماضی";
						break;
					default:
						stemTitle = "";
						break;
				}
				break;

			default:
				stemTitle = "";
				break;
		}

		return stemTitle;
	}

	public final String printTitlePassivity()
	{
		switch (this.getTensePassivity())
		{
			case ACTIVE:
				return "معلوم";
			case PASSIVE:
				return "مجهول";
			default:
				return "";
		}
	}

	public final String printTitlePositivity()
	{
		switch (this.getTensePositivity())
		{
			case POSITIVE:
				return "مثبت";
			case NEGATIVE:
				return "منفی";
			case WRONG_UNDETECTED:
				return "غلط";
			case UNUSUAL_NEGATIVE:
				return "منفی نامتعارف";
			case UNUSUAL_POSITIVE:
				return "مثبت نامتعارف";
			default:
				return "";
		}
	}

	public final String printTitleObjct()
	{
		String title = "دارای ضمیر مفعولی ";

		switch (this.getTenseObjectPronoun())
		{
			case SINGULAR_FIRST:
				title += "اول شخص مفرد";
				break;
			case SINGULAR_SECOND:
				title += "دوم شخص مفرد";
				break;
			case SINGULAR_THIRD:
				title += "سوم شخص مفرد";
				break;
			case PLURAL_FIRST:
				title += "اول شخص جمع";
				break;
			case PLURAL_SECOND:
				title += "دوم شخص جمع";
				break;
			case PLURAL_THIRD:
				title += "سوم شخص جمع";
				break;
			case WRONGE:
				title += "نادرست";
				break;
			default:
				title = "";
				break;
		}
		return title;
	}

	public final String printTitleType()
	{
		String title = "نوع ";

		if(this.getTenseType().getValue()==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.SADE))
			title += "ساده";
		else if (this.getTenseType().getValue()==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.PISHVANDI))
				title += "پیشوندی";
		else if (this.getTenseType().getValue()==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.PISHVANDI_MORAKKAB))
				title += "پیشوندی مركب";
		else if (this.getTenseType().getValue()==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.MORAKKAB))
				title += "مركب";
		else if (this.getTenseType().getValue()==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.EBARATE_FELI))
				title += "عبارت فعلی";
		else if (this.getTenseType().getValue()==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.NAGOZAR))
				title += "ناگذر یك شخصه";
		else if (this.getTenseType().getValue()==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.ESNADI))
				title += "اسنادی";
		else if (this.getTenseType().getValue()==ENUM_VERB_TYPE.GetDefualtObjectsValue(ENUM_VERB_TYPE.GHEIRE_SHAKHSI))			
				title += "غیر شخصی";
		else
			title += "نامعلوم";
			
		return title;
	}

	public final String printTitleTense()
	{
		return this.printTitleTime() + " " + this.printTitlePassivity() + " " + this.printTitlePositivity();
	}

	public final void setVerbID(int id)
	{
		this.ID = id;
	}

	public final int getVerbID()
	{
		return this.ID;
	}

	public final boolean isValidTense(ENUM_VERB_TRANSITIVITY transitivity)
	{
		if (this.getTenseTime() == ENUM_TENSE_TIME.AMR)
		{
			if (this.getTensePerson() != ENUM_TENSE_PERSON.PLURAL_SECOND && this.getTensePerson() != ENUM_TENSE_PERSON.SINGULAR_SECOND)
			{
				return false;
			}
		}

		if (transitivity == ENUM_VERB_TRANSITIVITY.INTRANSITIVE)
		{
			if (this.getTensePassivity() == ENUM_TENSE_PASSIVITY.PASSIVE)
			{
				return false;
			}
		}

		return true;
	}

	public final boolean IsValidPositivity()
	{
		switch (getTenseTime())
		{
			case MAZI_E_SADE:
			case MAZI_E_SADEYE_NAGHLI:
			case MOZARE_E_ELTEZAMI:
			case AMR:
				if (getTensePositivity() == ENUM_TENSE_POSITIVITY.POSITIVE || getTensePositivity() == ENUM_TENSE_POSITIVITY.NEGATIVE || getTensePositivity() == ENUM_TENSE_POSITIVITY.UNUSUAL_NEGATIVE || getTensePositivity() == ENUM_TENSE_POSITIVITY.UNUSUAL_POSITIVE)
				{
					return true;
				}
				break;

			case MAZI_E_ESTEMRARI:
			case MAZI_E_ESTEMRARIE_NAGHLI:
			case MOZARE_E_EKHBARI:
				if (getTensePositivity() == ENUM_TENSE_POSITIVITY.POSITIVE || getTensePositivity() == ENUM_TENSE_POSITIVITY.NEGATIVE)
				{
					return true;
				}
				break;

			default:
				break;
		}

		return false;
	}
}