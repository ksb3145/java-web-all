package common.util;

public class StringUtil {

	public static String strNullCheck(String str){
		return ((null == str) || "".equals(str)) ? " " : str; 
	}
	
}
