package SCICT.NLP.TextProofing.SpellChecker;

import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Morphology.Lemmatization.*;
import SCICT.NLP.Utility.StringDistance.*;
import SCICT.NLP.Utility.WordContainer.*;
import SCICT.NLP.Utility.WordGenerator.*;
import SCICT.NLP.Utility.Parsers.*;
import SCICT.NLP.Morphology.Inflection.Conjugation.*;

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
 configuration Class of Spell Checker
*/
public class SpellCheckerConfig
{
	/**
	 The absolute path of stem's file.
	*/
	public String StemPath;
	/**
	 The absolute path of dictionary file.
	*/
	public String DicPath;
	/**
	 Indicates the Maximum Edit Distance that searched for finding suggestions
	*/
	public int EditDistance;
	/**
	 Number of Suggestions
	*/
	public int SuggestionCount;

	/**
	 Constructor Class
	*/
	public SpellCheckerConfig()
	{

	}

	/**
	 Constructor Class
	
	@param dicPath Absolute path of dictionary file
	@param editDist Maximum Edit Distance that searched for finding suggestions
	@param sugCnt Number of Suggestions
	*/
	public SpellCheckerConfig(String dicPath, int editDist, int sugCnt)
	{
		this.DicPath = dicPath;
		this.EditDistance = editDist;
		this.SuggestionCount = sugCnt;
	}
}