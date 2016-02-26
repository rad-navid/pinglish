package SCICT.NLP.Persian.Constants;

import SCICT.NLP.Persian.*;

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



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region PseudoSpace

/** 
 Holds PseudoSpace related constants
*/
public final class PseudoSpace
{
	/** 
	 The main standard PseudoSpace (Zero Width Non-Joiner)
	*/
	public static final char ZWNJ = '\u200C';

	/** 
	 alternative PseudoSpace (Zero Width Space)
	*/
	public static final char ZWS = '\u200B';

	/** 
	 alternative PseudoSpace (Zero Width Joiner)
	*/
	public static final char ZWJ = '\u200D';

	/** 
	 alternative PseudoSpace used by Microsoft Word (Ctrl + -)
	*/
	public static final char MSWPS = '\u00AC';

	static
	{
		Load();
	}

	/** 
	 Loads this instance.
	*/
	public static void Load()
	{
		// Nothing to-do now
	}
}