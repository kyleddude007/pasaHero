package com.pasahero.android;

import android.view.View;

public interface TripPlannerInterface {
	public void loadItinerary(Response response);
	public void fareReady(Fare fare, String legMode, View fareView);
}
