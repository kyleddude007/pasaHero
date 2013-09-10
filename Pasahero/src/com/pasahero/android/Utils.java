package com.pasahero.android;

import java.util.ArrayList;
import java.util.List;

import org.opentripplanner.util.model.EncodedPolylineBean;

public class Utils {

	public static Object ifExists(Object object){
		try{
			return object;
		}catch(NullPointerException e){
			return null;
		}
	}
	
	public static String insertToTemplate(String template, String pattern, String insert){
		return template.replace(pattern, insert);
	}

}
