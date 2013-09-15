package com.pasahero.android;

import java.io.IOException;
import java.net.URL;

import android.os.AsyncTask;
import android.view.View;

public class RequestFareTask extends AsyncTask<URL, Void, Fare> {

	private TripPlannerInterface fareListener;
	private View fareView;
	private String legMode;

	public RequestFareTask(TripPlannerInterface fareListener, String legMode,
			View fareView) {
		this.fareListener = fareListener;
		this.legMode = legMode;
		this.fareView = fareView;
	}

	@Override
	protected Fare doInBackground(URL... urls) {
		URL url = urls[0];
		TripPlanner planner = new TripPlanner();
		Fare fare = null;
		try {
			fare = planner.getFare(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fare;
	}

	@Override
	protected void onPostExecute(Fare fare) {
		System.out.println("Response: " + fare);
		if (fare != null) {
			fareListener.fareReady(fare, legMode, fareView);
		}else{
			fareListener.fareUnavailable();
		}
	}

}
