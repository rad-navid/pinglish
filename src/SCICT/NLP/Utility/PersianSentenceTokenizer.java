package SCICT.NLP.Utility;

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


public class PersianSentenceTokenizer
{
	protected static final String EOS = "\0";

	protected static final String PuncEos = "[\\.؟?!…]";
	protected static final String WhiteSpaceExceptNewLine = "[ \t\u000B\f]";
	protected static final String PuncEosGroup = String.format("(%1$s+)", PuncEos);
	/** 
	 The order in the following definition is important
	*/
	protected static final String EndOfLine = "\\r\\n|\\n\\r|\\n|\\r";
	protected static final String ParagraphPattern = String.format("(%1$s)", EndOfLine);

	protected static final String OpeningPunctuations = "[\\(\\[\\{«‘“]";
	protected static final String ClosingPunctuations = "[\\)\\]\\}»’”]";
	protected static final String SymmetricPunctuations = "[\\'\\\"]";

	// replace with $1$2 i.e. remove $3
	protected static final String InitialsPattern = String.format("(%1$s|^)(\\s*[\\w\\d]{1,4}\\s*%1$s)(%2$s)", PuncEos, EOS);

	// replace with $1 i.e. remove $2
	protected static final String OneLetterInitialsPattern = String.format("(\\b[\\w\\d]\\s*%1$s\\s*)(%2$s)", PuncEos, EOS);

	//replace with $2$1
	protected static final String ClosingPuncRepairPattern = String.format("(%1$s)(\\s*%2$s\\s*)", EOS, ClosingPunctuations);

	// Symmetric Punctuations are only accepted if they are stuck to the EOS, because it might be meant opening a quotation.
	//replace with $2$1
	protected static final String SymmetricPuncRepairPattern = String.format("(%1$s)(%2$s\\s*)", EOS, SymmetricPunctuations);

	// replace with $2$1$3
	// forces spaces at the beginning of sentences to be appended to the end of previous sentences
	protected static final String SenteceBeginWithNonSpace = String.format("(.%1$s)(%2$s+)(\\S|%3$s)", EOS, WhiteSpaceExceptNewLine, EndOfLine);

	/** 
	 Tokenizes the specified string into sentences.
	 
	 @param s The string to extract sentences from.
	 @return 
	*/
	public static String[] Tokenize(String s)
	{
		// NOTE: the order of statements in this method is important

		// punctuations happenning at the end of sentence
		s = StringUtil.ReplaceAllRegex(s, PuncEosGroup, "$1" + EOS);

		while (StringUtil.MatchesRegex(s, InitialsPattern))
		{
			s = StringUtil.ReplaceFirstRegex(s, InitialsPattern, "$1$2");
		}

		while (StringUtil.MatchesRegex(s, OneLetterInitialsPattern))
		{
			s = StringUtil.ReplaceFirstRegex(s, OneLetterInitialsPattern, "$1");
		}


		s = StringUtil.ReplaceAllRegex(s, ClosingPuncRepairPattern, "$2$1");
		s = StringUtil.ReplaceAllRegex(s, SymmetricPuncRepairPattern, "$2$1");

		s = StringUtil.ReplaceAllRegex(s, SenteceBeginWithNonSpace, "$2$1$3");


		// remove EOS at the end of sentence
		s = StringUtil.ReplaceAllRegex(s, String.format("(%1$s)($|%2$s)", EOS, EndOfLine), "$2");

		// new-line means a new sentence
		s = StringUtil.ReplaceAllRegex(s, ParagraphPattern, "$1" + EOS);

		// this should be done in the end
		s = StringUtil.ReplaceAllRegex(s, String.format("%1$s+", EOS), EOS);
		return s.split(java.util.regex.Pattern.quote(EOS), -1);
	}
}