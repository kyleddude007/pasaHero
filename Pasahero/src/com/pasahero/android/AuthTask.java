package com.pasahero.android;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import android.os.AsyncTask;

public class AuthTask extends AsyncTask<Map<String, String>, Void, User> {

	private AuthTaskInterface authListener;
	public static final int LOG_IN = 0;
	public static final int SIGN_UP = 1;
	public static final int LOG_OUT = 2;
	private int authType;

	public AuthTask(AuthTaskInterface authListener, int authType) {
		this.authListener = authListener;
		this.authType = authType;
	}

	@Override
	protected User doInBackground(Map<String, String>... maps) {
		Map<String, String> map = maps[0];
		User user = null;
		;
		try {
			if (authType == LOG_IN) {
				user = User.login(map.get(Config.EMAIL),
						map.get(Config.PASSWORD));
			}else if(authType == SIGN_UP){
				user = User.signup(map.get(Config.EMAIL),
						map.get(Config.PASSWORD));
			}else if(authType == LOG_OUT){
				user = User.logout();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	protected void onPostExecute(User user) {
		if(authType == LOG_OUT){
			authListener.onLogOut();
		}else{
			authListener.userReady(user);
		}
	}

	public interface AuthTaskInterface {
		public void userReady(User user);
		public void onLogOut();
	}
}
