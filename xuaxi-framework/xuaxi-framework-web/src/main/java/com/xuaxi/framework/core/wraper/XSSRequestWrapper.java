package com.xuaxi.framework.core.wraper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.core.MediaType;

import org.springframework.mock.web.DelegatingServletInputStream;
 
public class XSSRequestWrapper extends HttpServletRequestWrapper {
	
    private static Pattern[] patterns = new Pattern[]{
        // Script fragments
        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
        // src='...'
        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // lonely script tags
        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // eval(...)
        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // expression(...)
        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // javascript:...
        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
        // vbscript:...
        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
        // onload(...)=...
        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
        
    };

    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
    	ServletInputStream in = super.getInputStream();
    	String type = this.getContentType();
    	StringBuffer data = new StringBuffer();
    	if(type==null || !type.toLowerCase().startsWith(MediaType.MULTIPART_FORM_DATA.toLowerCase()) ){
    		if(in != null){
    			BufferedReader br = null;
        		try {
					br = new BufferedReader(new InputStreamReader(in));
					if(br != null){
						String str = br.readLine();
					
						while(str!=null){
							data.append(str);
							str = br.readLine();
						}
					}
				} catch (Exception e) { 
					e.printStackTrace();
				}finally{
					if(br != null){
						br.close();
					}
				}
        	}
    		if(data == null || data.length() == 0){
    			data  = new StringBuffer("");
    		}
			String safeData = stripXSS(data.toString()); 
			if(safeData!=null){ 
				InputStream   inputstream   =   new   ByteArrayInputStream(safeData.getBytes());   
				in  = new DelegatingServletInputStream(inputstream); 
			}
    	} 
    	
    	return in;
    }
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
 
        if (values == null) {
            return null;
        }
 
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }
 
        return encodedValues;
    }
 
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
 
        return stripXSS(value);
    }
 
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    }
 
    private String stripXSS(String value) {
        if (value != null) {
            value = value.replaceAll("\0", "");
            for (Pattern scriptPattern : patterns){
                value = scriptPattern.matcher(value).replaceAll("");
            }
            value = value.replaceAll("<", "＜");
            value = value.replaceAll("&#60;", "＜");
            value = value.replaceAll("\u0026\u006c\u0074\u003b", "＜");
            value = value.replaceAll(">", "＞");
            value = value.replaceAll("&#62;", "＞"); 
            value = value.replaceAll("\u0026\u0067\u0074\u003b", "＞");
        } 
        return value;
    } 
}