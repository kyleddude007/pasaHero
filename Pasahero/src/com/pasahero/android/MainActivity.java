package com.pasahero.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startActivity(new Intent(getApplicationContext(),PasaheroMapActivity.class));
		//startActivity(new Intent(getApplicationContext(),AuthActivity.class));
		//session = new SessionManager(this);
		//session.checkLogin();
		
		/*try {
			URL[] urls = { new URL(Config.SAMPLE_URL) };
			new RequestItineraryTask(this).execute(urls);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}*/
		

		/*
		 * StrictMode.ThreadPolicy policy = new
		 * StrictMode.ThreadPolicy.Builder() .permitAll().build();
		 * StrictMode.setThreadPolicy(policy); RouteManager mgr = new
		 * RouteManager(); mgr.get(
		 * "http://192.168.0.103:8080/opentripplanner-api-webapp/ws/plan?_dc=1378035374631&arriveBy=false&time=7%3A35pm&ui_date=9%2F1%2F2013&mode=TRANSIT%2CWALK&optimize=QUICK&maxWalkDistance=840&walkSpeed=1.341&date=2013-09-01&toPlace=14.569218%2C121.066418&fromPlace=14.566829%2C121.059294&"
		 * );
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
