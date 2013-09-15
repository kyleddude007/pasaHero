package com.pasahero.android;

import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class RequestItineraryTask extends AsyncTask<URL, Void, Response> {

	private Context context;
	TripPlannerInterface requestItineraryInterface;
	private ProgressDialog planProgress;

	public RequestItineraryTask(Context context,
			TripPlannerInterface requestItineraryInterface) {
		this.requestItineraryInterface = requestItineraryInterface;
		this.context = context;
	}

	protected void onPreExecute() {
		planProgress = ProgressDialog.show(context, Config.LOADING, Config.ITINERARY_FETCHING_MSG);
	}

	@Override
	protected Response doInBackground(URL... urls) {
		URL url = urls[0];
		TripPlanner planner = new TripPlanner();
		Response response = planner.get(url);
		return response;
	}

	@Override
	protected void onPostExecute(Response response) {
		System.out.println("Response: " + response);
		if (planProgress.isShowing()) {
			planProgress.dismiss();
		}
		if (response != null) {
			requestItineraryInterface.loadItinerary(response);
		} else {
			requestItineraryInterface.failedToRetrieveItinerary();
		}
	}

}
