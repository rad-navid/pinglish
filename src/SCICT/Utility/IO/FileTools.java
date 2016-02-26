package SCICT.Utility.IO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import Helper.ArraysUtility;
import Helper.Encoding;

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
 Generic tools for filing
*/
public class FileTools
{
	/** 
	 Find the position (byte index) of the given word in the specified stream.
	 
	 @param fstream
	 @param word
	 @return 
	 * @throws IOException 
	*/
	public static long GetWordStartPositionInFile(File file, String word) throws IOException
	{

		RandomAccessFile fstream=new RandomAccessFile(file, "rw");

		
//ORIGINAL LINE: byte[] BOM = { 0xEF, 0xBB, 0xBF };
		byte[] BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
		final int BufferSize = 0x10000;

//ORIGINAL LINE: byte[] byteBuff = new byte[BufferSize];
		byte[] byteBuff = new byte[BufferSize];
//ORIGINAL LINE: byte[] zeroBuff = new byte[BufferSize];
		byte[] zeroBuff = new byte[BufferSize];
		char[] charBuff = new char[BufferSize];

		fstream.seek(0);
		
		long startPoisiton = 0;

		int readedCount;

		boolean wholeStrFound = false;

		int buffWriteIndex = 0;
		boolean eof = false;

			///#region Check for BOM - If file is created manually
		if (fstream.read(byteBuff, 0, 3) == 3)
		{
			boolean isEqual = true;
			for (int i = 0; i < BOM.length; i++)
			{
				if (BOM[i] != byteBuff[i])
				{
					isEqual = false;
					break;
				}
			}
			if (isEqual)
			{
				buffWriteIndex = 0;
			}
			else
			{
				buffWriteIndex = 3;
			}
		}

		do
		{
			readedCount = fstream.read(byteBuff, buffWriteIndex, BufferSize - buffWriteIndex);
			eof = (readedCount != BufferSize - buffWriteIndex);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: int lastOperationalIndex = Array.LastIndexOf<byte>(byteBuff, 0x0D);
			int lastOperationalIndex = ArraysUtility.lastIndexOf(byteBuff, (byte) 0x0D);
			if (lastOperationalIndex + 1 != BufferSize && byteBuff[lastOperationalIndex + 1] == 0x0A)
			{
				++lastOperationalIndex;
			}

			int charCount = Encoding.GetChars(byteBuff, 0, lastOperationalIndex + 1, charBuff, 0);

			for (int i = 0; i < charCount; i++)
			{
					///#region Check first character
				if (charBuff[i] == word.charAt(0))
				{
					if ((i - 1) > 0 && charBuff[i - 1] != 0x0A && charBuff[i - 1] != 0x0D)
					{
						continue;
					}

						///#region Searsch to find the match
					wholeStrFound = true;
					for (int j = 1; j < word.length(); j++)
					{
						assert i + j < charBuff.length && charBuff[i + j] != 0;

						if (word.charAt(j) == charBuff[i + j])
						{
							continue;
						}
						else
						{
							wholeStrFound = false;
							break;
						}
						//else // TODO: Remove this after completion
						//{
						//    wholeStrFound = true;
						//}
					}
					if (wholeStrFound && Character.isWhitespace(charBuff[i + word.length()]))
					{
						// Calculate Position
						startPoisiton = (fstream.getFilePointer() - (readedCount + buffWriteIndex)) + Encoding.GetByteCount(charBuff, 0, i);
						return startPoisiton;
					}
				}
			}

			buffWriteIndex = BufferSize - (lastOperationalIndex + 1);
			System.arraycopy(byteBuff, lastOperationalIndex + 1, byteBuff, 0, buffWriteIndex);
			System.arraycopy(zeroBuff, 0, byteBuff, buffWriteIndex, BufferSize - buffWriteIndex);
			System.arraycopy(zeroBuff, 0, charBuff, 0, BufferSize);

		} while (!eof);

		return -1;
	}

	/**
	 Remove a line from file
	
	@param fstream Opened file stream
	@param position position of line
	 * @throws IOException 
	*/
	public static void RemoveLineFromPosition(File file, long position) throws IOException
	{
		
		RandomAccessFile fstream=new RandomAccessFile(file, "rw");
		int BufferSize = 0x400;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] byteBuff = new byte[BufferSize];
		byte[] byteBuff = new byte[BufferSize];
		char[] charBuff = new char[BufferSize];

		// 1. Go to next line
		fstream.read(byteBuff, 0, BufferSize);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: int firstEndOfLine = Array.IndexOf<byte>(byteBuff, 0x0D, 0);
		int firstEndOfLine =ArraysUtility.indexOf(byteBuff,(byte)0x0D, 0);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] wholeOfFile = new byte[fstream.Length - (position + firstEndOfLine + 2)];
		byte[] wholeOfFile = new byte[(int) (fstream.length() - (position + firstEndOfLine + 2))];
		fstream.seek(position + firstEndOfLine + 2);
		int readedCount = fstream.read(wholeOfFile, 0, wholeOfFile.length);
		fstream.seek(position);
		fstream.write(wholeOfFile, 0, wholeOfFile.length);

		fstream.setLength(position + wholeOfFile.length);
	}
}