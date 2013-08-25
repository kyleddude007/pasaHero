package com.example.pasahero;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticatorService extends Service {

	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new AccountAuthenticator(this).getIBinder();
	}
}
