package SCICT.NLP.Utility.StringDistance;


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


public class NeedlemanConfig
{
	private KeyboardKeyDistance m_keyboard;
	public final KeyboardKeyDistance getKeyboard()
	{
		return this.m_keyboard;
	}

	private double m_gapCost = 1.0;
	public final double getGapCost()
	{
		return this.m_gapCost;
	}

	private double m_needlemanMaxSubstituteRange = 2.0;
	public final double getNeedlemanMaxSubstituteRange()
	{
		return this.m_needlemanMaxSubstituteRange;
	}

	public NeedlemanConfig(KeyboardKeyDistance keyboardKeyDistance, double needlemanGapCost, double needlemanMaxSubstituteRange)
	{
		this.m_needlemanMaxSubstituteRange = needlemanMaxSubstituteRange;
		this.m_gapCost = needlemanGapCost;
		this.m_keyboard = keyboardKeyDistance;
	}
	public NeedlemanConfig()
	{
		this.m_keyboard = new KeyboardKeyDistance();
	}
}