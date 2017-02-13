package com.benblamey.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class URLUtils {

	public static String GetURL(URL url) {
		try {
			URLConnection yc = url.openConnection();
			BufferedReader in;

			in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			String inputLine;
			String result = "";

			while ((inputLine = in.readLine()) != null) {
				result += inputLine + "\n";
			}
			in.close();

			return result;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static Map<String, String> getQueryMap(String query)  
	 {  
	     String[] params = query.split("&");  
	     Map<String, String> map = new HashMap<String, String>();  
	     for (String param : params)  
	     {  
	         String name = param.split("=")[0];  
	         String value = param.split("=")[1];  
	         map.put(name, value);  
	     }  
	     return map;  
	 }  

}
