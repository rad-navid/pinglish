package SCICT.Utility.IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import SCICT.Utility.*;

/**
 Load dictionary file
*/
public class DictionaryLoader
{
	protected String m_filename;
	protected BufferedReader  m_streamReader;
	protected BufferedWriter  m_streamWriter;
	protected boolean m_endOfStream = false;

	/**
	 End of Stream
	*/
	public final boolean getEndOfStream()
	{
		return m_endOfStream;
	}

	/**
	 Load file
	
	@param fileName File name
	@return True if suucessfully loade, otherwise False
	*/
	public final boolean LoadFile(String fileName)
	{
		
		m_filename = fileName;
		if (m_filename.length() == 0)
		{
			return false;
		}

		if (!(new java.io.File(m_filename)).isFile())
		{
			return false;
		}

		try
		{
			m_streamReader = new BufferedReader(new FileReader(m_filename));
		}
		catch (RuntimeException e)
		{
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		m_endOfStream = false;
		return true;
	}

	/**
	 Get next line
	
	@param line Line contents
	@return True if not EOF, False on EOF
	*/
	public final boolean NextLine(Helper.RefObject<String> line)
	{
		line.argvalue = "";

		if (m_streamReader == null)
		{
			return false;
		}

		try
		{
			if ((line.argvalue = m_streamReader.readLine())!=null )
			{
				this.m_endOfStream=false;
				return true;
			}
			else
			{
				this.m_endOfStream=true;
				m_streamReader.close();
				m_streamReader = null;
				return false;
			}
		}
		catch (RuntimeException e)
		{
			try {
				m_streamReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			m_streamReader = null;

			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m_endOfStream;
	}

	/** 
	 Close Stream Reader
	*/
	public final void CloseFile()
	{
		if (m_streamReader != null)
		{
			try {
				m_streamReader.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

	/**
	 Add a term to dictionary
	
	@param line word
	@return True if word successfully added, otherwise False
	*/
	public final boolean AddLine(String line)
	{
		if (m_filename.length() == 0)
		{
			return false;
		}

		if (!(new java.io.File(m_filename)).isFile())
		{
			return false;
		}

		try
		{
			m_streamWriter = new BufferedWriter(new FileWriter( m_filename,true));
			m_streamWriter.write(line);
			m_streamWriter.newLine();
			m_streamWriter.close();
		}
		catch (RuntimeException ex)
		{
			return false;
		} catch (IOException e) {
		
			e.printStackTrace();
		}

		return true;
	}

	/**
	 Add a term to dictionary
	
	@param line word
	@param fileName File name
	@return True if word successfully added, otherwise False
	*/
	public final boolean AddLine(String line, String fileName)
	{
		if (fileName.length() == 0)
		{
			return false;
		}

		if (!(new java.io.File(fileName)).isFile())
		{
			return false;
		}

		try
		{
//			using (m_streamWriter = new StreamWriter(fileName, true))
			m_streamWriter = new BufferedWriter(new FileWriter(fileName,true));
			try
			{
				m_streamWriter.write(line);
				m_streamWriter.newLine();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			finally
			{
				try {
					m_streamWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch (RuntimeException ex)
		{
			return false;
		} catch (IOException e1) {
	
			e1.printStackTrace();
		}

		return true;
	}
}