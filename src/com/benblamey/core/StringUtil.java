package com.benblamey.core;

import java.util.List;

public class StringUtil {

	public static String ToCommaList(String[] items) {
		String result = "";
		
		for (String item : items) {
			if (result.length() > 0) {
				result += ",";
			}
			result += item;
		}
				
		return result;
				
	}

	public static String concat(List<String> readAllLines, String delimiter) {
		String result= "";
		for (String s : readAllLines) {
			result += s + delimiter;
		}
		return result;
	}
}