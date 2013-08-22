package com.example.pasahero;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AuthActivity extends RoboActivity{

	@InjectView(R.id.et_emailAddress) EditText emailAddress;
	@InjectView(R.id.et_password) EditText password;
	@InjectView(R.id.b_okay) Button okay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Typeface font = Typefaces.get(this, Config.FONTAWESOME);
		setContentView(R.layout.activity_auth);
		okay.setTypeface(font);
	}
	
	
}
