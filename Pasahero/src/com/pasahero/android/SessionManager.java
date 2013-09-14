package com.pasahero.android;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	SharedPreferences pref;
	Editor editor;
	Context context;

	// Constructor
	public SessionManager(Context context) {
		this.context = context;
		pref = context.getSharedPreferences(Config.PREF_NAME,
				Config.PREF_PRIVATE_MODE);
		editor = pref.edit();
	}
	
    public void createLoginSession(String email){
        editor.putBoolean(Config.IS_LOGGED_IN, true);      
        editor.putString(Config.EMAIL, email);
        editor.commit();
    }
    
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(Config.EMAIL, pref.getString(Config.EMAIL, null));
        return user;
    }
    
    public boolean isLoggedIn(){
    	return pref.getBoolean(Config.IS_LOGGED_IN, false);
    }
    
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            Intent i = new Intent(context, AuthActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
            context.startActivity(i);
        }
         
    }
    
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();         
        Intent i = new Intent(context, AuthActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
     
 

}