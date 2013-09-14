package com.pasahero.android;

import com.mapquest.android.maps.GeoPoint;

public interface PasaHeroMapInterface {
	public void navButtonClicked();
	public void planReceived(Plan plan);
	public void myLocationReady(GeoPoint myLocationPoint);
}
