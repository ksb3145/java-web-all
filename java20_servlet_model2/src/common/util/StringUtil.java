package common.util;


/**
 * null 체크
 * @author dev2
 *
 */
public class StringUtil {

	public static String strNullCheck(String str){
		return ((null == str) || "".equals(str)) ? " " : str; 
	}
	
}
