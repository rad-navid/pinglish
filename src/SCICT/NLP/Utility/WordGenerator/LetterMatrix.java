package SCICT.NLP.Utility.WordGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Helper.ArraysUtility;
import Helper.LinqSimulationArrayList;
import SCICT.NLP.Persian.Constants.*;
import SCICT.NLP.Utility.*;

public class LetterMatrix
{
	private LinqSimulationArrayList<String> m_CharacterMatrix = new LinqSimulationArrayList<String>();
	private LinqSimulationArrayList<String> m_UpperCharacterMatrix = new LinqSimulationArrayList<String>();

	private String m_FirstLine = "ضصثقفغعهخحجچ";
	private String m_SecondLine = "شسیبلاتنمکگ  ";
	private String m_ThirdLine = "ظطزرذدئو    ";

	private String m_UpperFirsLine = "پ          پ";
	private String m_UpperSecondLine = "     آ     ژپ";
	private String m_UpperThirdLine = "  ژؤإأء     ";

	private java.util.HashMap<Character, char[]> m_suggestionDic = new java.util.HashMap<Character, char[]>();

	public LetterMatrix()
	{
		Init();
	}

	private void Init()
	{
		m_UpperCharacterMatrix.add(m_UpperFirsLine);
		m_UpperCharacterMatrix.add(m_UpperSecondLine);
		m_UpperCharacterMatrix.add(m_UpperThirdLine);

		m_CharacterMatrix.add(m_FirstLine);
		m_CharacterMatrix.add(m_SecondLine);
		m_CharacterMatrix.add(m_ThirdLine);

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 12; ++j)
			{
				char[] cArry = {m_CharacterMatrix.get(i).toCharArray()[j], m_UpperCharacterMatrix.get(i).toCharArray()[j]};

				for (char c : cArry)
				{
					if (c != ' ')
					{
						if (!m_suggestionDic.containsKey(c))
						{
							LinqSimulationArrayList<Character> charList = new LinqSimulationArrayList<Character>();
							charList.addAll(ArraysUtility.toCharacter(AdjcentCharacterPeaker(i, j)));
							charList.addAll(ArraysUtility.toCharacter(AdjcentUpperCharacterPeaker(i, j)));
							charList.remove(c);
							//charList.Add(PseudoSpace.ZWNJ);
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
							charList = charList.Distinct('c');

							m_suggestionDic.put(c, ArraysUtility.toChar(charList.toArray(new Character[0])));
						}
						else
						{
							LinqSimulationArrayList<Character> charList = new LinqSimulationArrayList<Character>();
							charList.addAll(ArraysUtility.toCharacter(m_suggestionDic.get(c)));
							charList.addAll(ArraysUtility.toCharacter(AdjcentCharacterPeaker(i, j)));
							charList.addAll(ArraysUtility.toCharacter(AdjcentUpperCharacterPeaker(i, j)));
							charList.remove(c);
							//charList.Add(PseudoSpace.ZWNJ);
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
							charList = charList.Distinct('c');

							m_suggestionDic.put(c,ArraysUtility.toChar(charList.toArray(new Character[0])));
						}
					}
				}
			}
		}

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("./matrix.dat",true));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (java.util.Map.Entry<Character, char[]> pair : m_suggestionDic.entrySet())
		{
			sb.append("Dic.Add('" + pair.getKey() + "', new char[] {");
			for (int i = 0; i < pair.getValue().length; ++i)
			{
				sb.append("'" + pair.getValue()[i] + "'");
				if (i < pair.getValue().length - 1)
				{
					sb.append(", ");
				}
				else
				{
					sb.append("});");
				}
			}
			try {
				writer.write(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writer.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sb.delete(0, sb.length());
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private char[] AdjcentCharacterPeaker(int i, int j)
	{
		int rowMin = 0;
		int rowMax = 3;
		int colMin = 0;
		int colMax = 12;

		int iStep = 1;
		int jStep = 2;

		LinqSimulationArrayList<Character> charList = new LinqSimulationArrayList<Character>();
		char c;


		if (j - jStep >= colMin)
		{
			c = m_CharacterMatrix.get(i).toCharArray()[j - jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (j + jStep < colMax)
		{
			c = m_CharacterMatrix.get(i).toCharArray()[j + jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (i - iStep >= rowMin)
		{
			c = m_CharacterMatrix.get(i - iStep).toCharArray()[j];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (i + iStep < rowMax)
		{
			c = m_CharacterMatrix.get(i + iStep).toCharArray()[j];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (j - jStep >= colMin && i - iStep >= rowMin)
		{
			c = m_CharacterMatrix.get(i - iStep).toCharArray()[j - jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (j + jStep < colMax && i + iStep < rowMax)
		{
			c = m_CharacterMatrix.get(i + iStep).toCharArray()[j + jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (i - iStep >= rowMin && j + jStep < colMax)
		{
			c = m_CharacterMatrix.get(i - iStep).toCharArray()[j + jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (i + iStep < rowMax && j - jStep >= colMin)
		{
			c = m_CharacterMatrix.get(i + iStep).toCharArray()[j - jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}

		if (i < rowMax && j >= colMin && i >= rowMin && j < colMax)
		{
			c = m_CharacterMatrix.get(i).toCharArray()[j];
			if (c != ' ')
			{
				charList.add(c);
			}
		}

		return ArraysUtility.toChar(charList.toArray(new Character[0]));

	}
	private char[] AdjcentUpperCharacterPeaker(int i, int j)
	{
		int rowMin = 0;
		int rowMax = 3;
		int colMin = 0;
		int colMax = 12;

		int iStep = 1;
		int jStep = 1;

		LinqSimulationArrayList<Character> charList = new LinqSimulationArrayList<Character>();
		char c;

		if (j - jStep >= colMin)
		{
			c = m_UpperCharacterMatrix.get(i).toCharArray()[j - jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (j + jStep < colMax)
		{
			c = m_UpperCharacterMatrix.get(i).toCharArray()[j + jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (i - iStep >= rowMin)
		{
			c = m_UpperCharacterMatrix.get(i - iStep).toCharArray()[j];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (i + iStep < rowMax)
		{
			c = m_UpperCharacterMatrix.get(i + iStep).toCharArray()[j];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (j - jStep >= colMin && i - iStep >= rowMin)
		{
			c = m_UpperCharacterMatrix.get(i - iStep).toCharArray()[j - jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (j + jStep < colMax && i + iStep < rowMax)
		{
			c = m_UpperCharacterMatrix.get(i + iStep).toCharArray()[j + jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (i - iStep >= rowMin && j + jStep < colMax)
		{
			c = m_UpperCharacterMatrix.get(i - iStep).toCharArray()[j + jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}
		if (i + iStep < rowMax && j - jStep >= colMin)
		{
			c = m_UpperCharacterMatrix.get(i + iStep).toCharArray()[j - jStep];
			if (c != ' ')
			{
				charList.add(c);
			}
		}

		if (i < rowMax && j >= colMin && i >= rowMin && j < colMax)
		{
			c = m_UpperCharacterMatrix.get(i).toCharArray()[j];
			if (c != ' ')
			{
				charList.add(c);
			}
		}

		return ArraysUtility.toChar(charList.toArray(new Character[0]));
	}

	public final char[] GetPossibleCharacters(char c)
	{
		return m_suggestionDic.get(c);
	}
}