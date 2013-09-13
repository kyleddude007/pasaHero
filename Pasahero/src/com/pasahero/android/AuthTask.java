package com.pasahero.android;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import android.os.AsyncTask;

public class AuthTask extends AsyncTask<Map<String, String>, Void, User>{

	private AuthTaskInterface authListener;
	
	public AuthTask(AuthTaskInterface authListener){
		this.authListener =authListener;
	}
	
	@Override
	protected User doInBackground(Map<String, String>... maps) {
		Map<String, String> map = maps[0];
		User user = null;;
		try {
			user = User.getUser(map.get(Config.EMAIL), map.get(Config.PASSWORD));
		} catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e){
			e.printStackTrace();
		}
		return user;
	}

	@Override
	protected void onPostExecute(User user) {
		System.out.println("user "+user.toString());
		authListener.userReady(user);
	}
	
	public interface AuthTaskInterface{
		public void userReady(User user);
	}
}
