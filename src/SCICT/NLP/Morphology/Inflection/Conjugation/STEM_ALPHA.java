﻿package SCICT.NLP.Morphology.Inflection.Conjugation;

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



public enum STEM_ALPHA
{
	A_B,
	B_A,
	A_A,
	B_B,
	A_X,
	B_X,
	X_A,
	X_B,
	X_X;

	public int getValue()
	{
		return this.ordinal();
	}

	public static STEM_ALPHA forValue(int value)
	{
		return values()[value];
	}
}