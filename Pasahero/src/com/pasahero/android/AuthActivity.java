package com.pasahero.android;

import java.util.Hashtable;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthActivity extends Activity implements AuthTask.AuthTaskInterface {

	private EditText emailView;
	private EditText passwordView;
	private Button logIn;
	private Button signUp;
	private AuthTask.AuthTaskInterface authListener;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		authListener = this;
		context =this;
		setUpViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auth, menu);
		return true;
	}
	
	public void setUpViews(){
		emailView = (EditText) findViewById(R.id.email);
		passwordView = (EditText) findViewById(R.id.password);
		logIn = (Button) findViewById(R.id.log_in);
		signUp = (Button) findViewById(R.id.sign_up);
		logIn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				System.out.println("log in!");
				Toast.makeText(context, "Loggin in", Toast.LENGTH_SHORT).show();
				AuthTask authTask = new AuthTask(authListener);
				Map<String, String> data = new Hashtable<String, String>();
				data.put(Config.EMAIL, emailView.getEditableText().toString());
				data.put(Config.PASSWORD, passwordView.getEditableText().toString());
				authTask.execute(data);
			}
		});
	}

	@Override
	public void userReady(User user) {
		System.out.println(user);
		System.out.println("Successfully logged in!");
	}

}

