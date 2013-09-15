package com.pasahero.android;

import java.util.Hashtable;

import android.view.View;

public interface TripPlannerInterface {
	public void loadItinerary(Response response);
	public void fareReady(Fare fare, String legMode, View fareView);
	public void pnrTableRead(Hashtable<String, String> pnrTable, String end, View fareView);
	public void fareUnavailable();
}
