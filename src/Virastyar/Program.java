package Virastyar;

import java.io.*;

import android.content.res.AssetManager;

import Helper.ArraysUtility;
import Helper.LinqSimulationArrayList;
import SCICT.NLP.TextProofing.SpellChecker.*;
import SCICT.NLP.Utility.*;
import SCICT.NLP.Utility.PinglishConverter.PinglishConverter;
import SCICT.NLP.Utility.PinglishConverter.PinglishConverterUtils;
import SCICT.NLP.Utility.PinglishConverter.PinglishString;


public class Program
{
	public static void main(String[] args)
	{
		PinglishConvertor();
	}
	
	
	public static void spellchecker()
	{
		System.out.println("Virastyar (C),  Version: M1");

		//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#region Initialize Virastyar Spell Checker
				SpellCheckerConfig virConfig = new SpellCheckerConfig();
				virConfig.DicPath = "./dic.dat";
				virConfig.EditDistance = 2;
				virConfig.StemPath = "./stem.dat";
				virConfig.SuggestionCount = 7;

				PersianSpellChecker virastyar = new PersianSpellChecker(virConfig);
		//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#endregion

				String line = "";
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(System.in,"UTF-16"));
					
						//line = in.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					line="بیثببی";

					String[] tokens ={line};// PersianWordTokenizer.Tokenize(line, false, true).toArray(new String[0]);

					String[] words = tokens;//StringUtil.ExtractPersianWordsStandardized(line);

					int arrayIndex = 0;
					int realIndex = 0;
					for (int i = 0; i < words.length; ++i)
					{
						String curWord = words[i];

		//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
							///#region Calculate Index of Current Word

						int tmpIndex = ArraysUtility.indexOf(tokens, curWord, arrayIndex);
						if (tmpIndex == -1)
						{
							arrayIndex++;
							continue;
						}
						else
						{
							arrayIndex = tmpIndex;
						}

						realIndex = 0;

						for (int j = 0; j < arrayIndex; ++j)
						{
							realIndex += tokens[j].length();
						}

						arrayIndex++;

		//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
							///#endregion

						String nxtWord = i == words.length - 1 ? "" : words[i + 1];
						String preWord = i == 0 ? "" : words[i - 1];

						String[] suggestions = null;
						SuggestionType sugType = SuggestionType.forValue(0);
						SpaceCorrectionState spacingState = SpaceCorrectionState.forValue(0);

						Helper.RefObject<String[]> tempRef_suggestions = new Helper.RefObject<String[]>(suggestions);
						Helper.RefObject<SuggestionType> tempRef_sugType = new Helper.RefObject<SuggestionType>(sugType);
						Helper.RefObject<SpaceCorrectionState> tempRef_spacingState = new Helper.RefObject<SpaceCorrectionState>(spacingState);
						boolean res = virastyar.CheckSpelling(curWord, preWord, nxtWord, 20, tempRef_suggestions, tempRef_sugType, tempRef_spacingState);
						suggestions = tempRef_suggestions.argvalue;
						sugType = tempRef_sugType.argvalue;
						spacingState = tempRef_spacingState.argvalue;
						//Console.WriteLine(preWord + " " + curWord + " " + nxtWord + " " + res.ToString());

						if (res)
						{
							System.out.println("*");
							continue;
						}
						else
						{
							StringBuilder sb = new StringBuilder();
							if (suggestions.length > 0)
							{
								sb.append("&");
								sb.append(" ");
								sb.append(curWord);
								sb.append(" ");
								sb.append(suggestions.length);
								sb.append(" ");
								sb.append(realIndex);
								sb.append(":");
								sb.append(" ");
								for (String sug : suggestions)
								{
									sb.append(sug);
									sb.append(", ");
								}
							}
							else
							{
								sb.append("#");
								sb.append(" ");
								sb.append(curWord);
								sb.append(" ");
								sb.append(realIndex);
							}

							System.out.println(sb.toString());
							continue;
						}
					}

					System.out.println("\n");
				
	}

	public static void PinglishConvertor()
	{
		SpellCheckerConfig virConfig = new SpellCheckerConfig();
		virConfig.DicPath = "./dic.dat";
		virConfig.EditDistance = 2;
		virConfig.StemPath = "./stem.dat";
		virConfig.SuggestionCount = 7;

		PersianSpellChecker virastyar = new PersianSpellChecker(virConfig);
		
        String trainingFilePath = "E:\\Software Development\\Java\\Eclipse\\Yadasht\\Pinglish.xml";
        //string preprocessFilePath = @"C:\Preprocess.dat";

        LinqSimulationArrayList<PinglishString> trainingList = null;
        PinglishConverter m_pinglishConverter = new PinglishConverter();
        m_pinglishConverter.SetSpellerEngine(virastyar);
        try
        {
        	AssetManager am = null;//getAssets();
        	InputStream fs = am.open("myFile.txt");
        	trainingList = PinglishConverterUtils.LoadPinglishStrings(fs);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        m_pinglishConverter.Learn(trainingList);
        //m_pinglishConverter.LoadPreprocessElements(preprocessFilePath);

        System.out.println(m_pinglishConverter.SuggestFarsiWord("khar e", false));
        String[] suggestions = m_pinglishConverter.SuggestFarsiWords("khar e", false);
        for(String s : suggestions)
            System.out.println(s);
	}
}