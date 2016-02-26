package SCICT.NLP.Persian.Constants;

import SCICT.NLP.Persian.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Persian Combination State

/**
 The state of combination spacing of two Persian words
*/
public enum PersianCombinationSpacingState
{
	/**
	 Combine with Pseudo-space
	*/
	PseudoSpace(1),
	/**
	 Combine seprately by a white space
	*/
	WhiteSpace(2),
	/**
	 Combine with no space and make a word
	*/
	Continous(3);

	private int intValue;
	private static java.util.HashMap<Integer, PersianCombinationSpacingState> mappings;
	private static java.util.HashMap<Integer, PersianCombinationSpacingState> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PersianCombinationSpacingState.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, PersianCombinationSpacingState>();
				}
			}
		}
		return mappings;
	}

	private PersianCombinationSpacingState(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static PersianCombinationSpacingState forValue(int value)
	{
		return getMappings().get(value);
	}
}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion
