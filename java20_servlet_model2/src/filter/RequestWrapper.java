package filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public final class RequestWrapper extends HttpServletRequestWrapper {
		 
    public RequestWrapper(HttpServletRequest servletRequest) {
    	super(servletRequest);
    }
    
    public String[] getParameterValues(String parameter) {
 
    	String[] values = super.getParameterValues(parameter);
    	if (values==null)  {
    		return null;
    	}
  
    	int count = values.length;
    	String[] encodedValues = new String[count];
  
    	for (int i = 0; i < count; i++) {
    		encodedValues[i] = cleanXSS(values[i]);
    	}
    	return encodedValues;
    }
 
    public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return cleanXSS(value);
    }
 
    public String getHeader(String name) {
		String value = super.getHeader(name);
		
		if (value == null){
			return null;
		}
		
		return cleanXSS(value);
    }
 
    private String cleanXSS(String value) {
    	StringBuffer sb = null;
    	
    	String[] checkStr_arr = {
    		"<script>","</script>",
    		"<javascript>","</javascript>",
    		"<iframe>","</iframe>",
    		"<vbscript>","</vbscript>",
    		"<object>","</object>",
    		"<img>","</img>",
    		"<marquee>","</marquee>",
    		"onerror","onclick","onload","onmouseover","onstart"
    	};
    	
    	for(String checkStr:checkStr_arr){
    		while(value.indexOf(checkStr) != -1){
    			value=value.replaceAll(checkStr, "");
    		}
    		while(value.toLowerCase().indexOf(checkStr) != -1){
    			sb = new StringBuffer(value);
    			sb = sb.replace(value.toLowerCase().indexOf(checkStr), value.toLowerCase().indexOf(checkStr)+checkStr.length(), "");
    			value = sb.toString();
    		}
    	}
    	value = value.replaceAll("eval\\((.*)\\)", "");
    	value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
    	return value;
    }
}