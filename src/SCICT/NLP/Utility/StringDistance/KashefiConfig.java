package SCICT.NLP.Utility.StringDistance;


/**
 Kashefi String Distnace Metric Configuration Class
*/
public class KashefiConfig
{
	private KeyboardKeyDistance m_keyboard;
	/**
	 Define keyboard layout
	*/
	public final KeyboardKeyDistance getKeyboard()
	{
		return this.m_keyboard;
	}

	private double m_insertGapCost = .679;
	/**
	 Gap Cost of Mistakanly Insertaion of a letter
	*/
	public final double getInsertGapCost()
	{
		return this.m_insertGapCost;
	}

	private double m_deleteGapCost = .569;
	/**
	 Gap Cost of Mistakenly Omission of a letter
	*/
	public final double getDeleteGapCost()
	{
		return this.m_deleteGapCost;
	}

	private double m_transpositionGapCost = .725;
	/**
	 Gap Cost of Mistakenly Transposition of two adjacent letter
	*/
	public final double getTranspositionGapCost()
	{
		return this.m_transpositionGapCost;
	}

	private double m_substituteGapCost = .367;
	/**
	 Maximum Cost of Substitution of Two Letters
	*/
	public final double getSubstituteGapCost()
	{
		return this.m_substituteGapCost;
	}

	/**
	 Class Constructor
	
	@param keyboardKeyDistance Keboard Layout
	@param kashefiInsertGapCost Gap Cost of Mistakanly Insertaion of a letter
	@param kashefiDeleteGapCost Gap Cost of Mistakenly Omission of a letter
	@param kashefiMaxSubstituteRange Maximum Cost of Substitution of Two Letters
	@param kashefiTransCost Transposition Cost
	*/
	public KashefiConfig(KeyboardKeyDistance keyboardKeyDistance, double kashefiInsertGapCost, double kashefiDeleteGapCost, double kashefiMaxSubstituteRange, double kashefiTransCost)
	{
		this.m_substituteGapCost = kashefiMaxSubstituteRange;
		this.m_insertGapCost = kashefiInsertGapCost;
		this.m_deleteGapCost = kashefiDeleteGapCost;
		this.m_transpositionGapCost = kashefiTransCost;

		this.m_keyboard = keyboardKeyDistance;
	}
	/**
	 Class Constructor
	 Set Default values
	*/
	public KashefiConfig()
	{
		this.m_keyboard = new KeyboardKeyDistance();
	}
}