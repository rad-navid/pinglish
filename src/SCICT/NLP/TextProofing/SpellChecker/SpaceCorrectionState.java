package SCICT.NLP.TextProofing.SpellChecker;

import SCICT.NLP.Morphology.Inflection.*;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.Parsers.*;
import SCICT.NLP.Morphology.Lemmatization.*;
import SCICT.NLP.Utility.WordGenerator.*;
import SCICT.Utility.*;

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


/**
 Differnet state of correct spacing problems
*/
public enum SpaceCorrectionState
{
	/**
	 Mistakenly omission of a white space (e.g. computersoftware)
	*/
	SpaceDeletation(1),
	/**
	 Mistakenly omission of many white spaces (e.g. computersoftwarearchitecture)
	*/
	SpaceDeletationSerrially(2),
	/**
	 Mistakenly Insertion of a white space in left side of word (e.g. comput er software)
	*/
	SpaceInsertationLeft(3),
	/**
	 Mistakenly Insertion of white spaces in left side of word and its parted words (e.g. com p u ter software)
	*/
	SpaceInsertationLeftSerrially(4),
	/**
	 Mistakenly Insertion of a white space in right side of word (e.g. computer so ftware)
	*/
	SpaceInsertationRight(5),
	/**
	 Mistakenly Insertion of white spaces in right side of word and its parted words (e.g. computer s o ftware)
	*/
	SpaceInsertationRightSerrially(6),
	/**
	 Spacing is correct
	*/
	None(7);

	private int intValue;
	private static java.util.HashMap<Integer, SpaceCorrectionState> mappings;
	private static java.util.HashMap<Integer, SpaceCorrectionState> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SpaceCorrectionState.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, SpaceCorrectionState>();
				}
			}
		}
		return mappings;
	}

	private SpaceCorrectionState(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SpaceCorrectionState forValue(int value)
	{
		return getMappings().get(value);
	}
}