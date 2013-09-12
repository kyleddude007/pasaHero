package com.pasahero.android;

import java.util.List;

import com.mapquest.android.maps.GeoPoint;

public interface ItineraryFragmentInterface {
	public void lineDataReady(List<GeoPoint> data, int color);

}
