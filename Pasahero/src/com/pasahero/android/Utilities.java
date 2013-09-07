package com.pasahero.android;

public class Utilities {

	public static Object ifExists(Object object){
		try{
			return object;
		}catch(NullPointerException e){
			return null;
		}
	}
}
