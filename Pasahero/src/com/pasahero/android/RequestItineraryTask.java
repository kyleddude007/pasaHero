package com.pasahero.android;

import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;

public class RequestItineraryTask extends AsyncTask<URL, Void, Response> {

	private Context callingContext;
	RequestItineraryInterface requestItineraryInterface;

	public void setCallingContext(Context callingContext) {
		this.callingContext = callingContext;
	}

	public RequestItineraryTask(Context callingContext,
			RequestItineraryInterface requestItineraryInterface) {
		this.callingContext = callingContext;
		this.requestItineraryInterface = requestItineraryInterface;
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
		requestItineraryInterface.loadItinerary(response);
	}

}
