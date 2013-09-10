package com.pasahero.android;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class Utils {

	public static Object ifExists(Object object) {
		try {
			return object;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static String insertToTemplate(String template,String pattern, String insert) {
			return template.replace(pattern, insert);
	}

	public static String insertToTemplate(String template,Map<String, String> patternPairs) {
		String inserted="";
		for (Map.Entry<String, String> pair : patternPairs.entrySet()) {
		    String key = pair.getKey();
		    String value =pair.getValue();
		    inserted.replace(key, value);
		}
		return inserted;
	}
	
	public static Object assignIfNotNull(Object... objects){
		int count = 0;
		while(objects[count]==null && count<objects.length){
			count++;
		}
		return objects[count];
	}
}
