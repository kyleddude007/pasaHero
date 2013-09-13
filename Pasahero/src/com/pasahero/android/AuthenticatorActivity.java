package com.pasahero.android;

import roboguice.activity.RoboAccountAuthenticatorActivity;
import roboguice.inject.InjectView;
import android.widget.EditText;

public class AuthenticatorActivity extends RoboAccountAuthenticatorActivity{
/*
	@InjectView(R.id.email) EditText emailView;
	@InjectView(R.id.password) EditText passwordView;
	@InjectView(R.id.submit) EditText submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authenticator);
		submit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String email = emailView.getText().toString();
				String password = passwordView.getText().toString();
				submit(email, password);
			}
			
		});
	}

	public void submit(String email, String password) {
	    new AsyncTask<Void, Void, Intent>() {
	        @Override
	        protected Intent doInBackground(Void... params) {
	            //String authtoken = sServerAuthenticate.userSignIn(email, password, mAuthTokenType);
	            final Intent res = new Intent();
	            res.putExtra(AccountManager.KEY_ACCOUNT_NAME, email);
	            res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
	            res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);
	            res.putExtra(PARAM_USER_PASS, password);
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
*/
}
