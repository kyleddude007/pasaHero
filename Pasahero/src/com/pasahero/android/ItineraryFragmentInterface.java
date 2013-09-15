package com.pasahero.android;

import java.util.List;

import com.mapquest.android.maps.GeoPoint;

public interface ItineraryFragmentInterface {
	/**
	 * Line geometry decoded and ready for drawing
	 * @param data The list of points that describe the route for the itinerary
	 * @param color The color of the itinerary to be drawn
	 */
	public void lineDataReady(List<GeoPoint> data, int color);

}
