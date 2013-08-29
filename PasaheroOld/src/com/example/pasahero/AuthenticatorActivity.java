package com.example.pasahero;

import roboguice.activity.RoboAccountAuthenticatorActivity;
import roboguice.inject.InjectView;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AuthenticatorActivity extends RoboAccountAuthenticatorActivity{

	@InjectView(R.id.et_emailAddress) EditText emailAddressView;
	@InjectView(R.id.et_password) EditText passwordView;
	@InjectView(R.id.b_okay) Button okay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Typeface font = Typefaces.get(this, Config.FONTAWESOME);
		setContentView(R.layout.activity_auth);
		okay.setTypeface(font);
	}
	
	public void submit() {
	    final String emailAddress = emailAddressView.getText().toString();
	    final String password = passwordView.getText().toString();
	    new AsyncTask<Void, Void, Intent>() {
	        @Override
	        protected Intent doInBackground(Void... params) {
	            String authtoken = sServerAuthenticate.userSignIn(emailAddress, password, mAuthTokenType);
	            final Intent res = new Intent();
	            res.putExtra(AccountManager.KEY_ACCOUNT_NAME, userName);
	            res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
	            res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);
	            res.putExtra(PARAM_USER_PASS, userPass);
	            return res;
	        }
	        @Override
	        protected void onPostExecute(Intent intent) {
	            finishLogin(intent);
	        }
	    }.execute();
	}
	
	private void finishLogin(Intent intent) {
	    String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
	    String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
	    final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
	    if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
	        String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
	        String authtokenType = mAuthTokenType;
	        // Creating the account on the device and setting the auth token we got
	        // (Not setting the auth token will cause another call to the server to authenticate the user)
	        mAccountManager.addAccountExplicitly(account, accountPassword, null);
	        mAccountManager.setAuthToken(account, authtokenType, authtoken);
	    } else {
	        mAccountManager.setPassword(account, accountPassword);
	    }
	    setAccountAuthenticatorResult(intent.getExtras());
	    setResult(RESULT_OK, intent);
	    finish();
	}
	
}
