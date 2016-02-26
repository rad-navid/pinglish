package Helper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Encoding {

	private final static Charset UTF8_CHARSET = Charset.forName("UTF-8");

	public static String decodeUTF8(byte[] bytes) {
	    return new String(bytes, UTF8_CHARSET);
	}

	public static byte[] encodeUTF8(String string) {
	    return string.getBytes(UTF8_CHARSET);
	}
	public static int GetChars(byte[]byteBuff, int byteIndex, int byteBuffLen, char[]charBuff, int charBuffIndex)
	{
	    byte[] data =new byte[byteBuffLen];
	    
	    for(int index=0, i=byteIndex ;i<byteBuffLen;index++, i++)
	    	data[index]=byteBuff[i];
	       
	    String text1=null;
		try {
			text1 = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   // if the charset is UTF-8  
	   
		
	    char[] chars = text1.toCharArray();
		if(chars==null)
			return 0;
		
	    for (char c : chars) {
			charBuff[charBuffIndex]=c;
			charBuffIndex++;
		}  
	    return chars.length;
		
	}

	public static int GetByteCount(char[]array,int index,int count)
	{
		int lastpos=index+count-1;
		String txt="";
		for(int i=index;i<lastpos;i++)
			txt+=array[i];
		return  encodeUTF8(txt).length;
	}
}
