package com.pasahero.android;

import java.util.ArrayList;
import java.util.List;

import org.opentripplanner.util.model.EncodedPolylineBean;

public class Utilities {

	public static Object ifExists(Object object){
		try{
			return object;
		}catch(NullPointerException e){
			return null;
		}
	}

}
