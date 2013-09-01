package com.pasahero.android;

import android.os.Bundle;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;

public class PasaheroMapActivity extends MapActivity {
	private MyLocationOverlay locOverlay;
	protected MapView map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pasahero_map);
		setUpMapView();
		setUpMyLocation();

	}

	public void setUpMapView() {
		this.map = (MapView) findViewById(R.id.mapView);
		map.setBuiltInZoomControls(true);
	}

	private void setUpMyLocation() {
		this.locOverlay = new MyLocationOverlay(this, map);
		locOverlay.enableMyLocation();
		locOverlay.runOnFirstFix(new Runnable() {
			@Override
			public void run() {
				GeoPoint currentLocation = locOverlay.getMyLocation();
				map.getController().animateTo(currentLocation);
				map.getController().setZoom(14);
				map.getOverlays().add(locOverlay);
				locOverlay.setFollowing(true);
			}
		});
	}

	@Override
    protected void onResume() {
      locOverlay.enableMyLocation();
      locOverlay.enableCompass();
      super.onResume();
    }

    // disable features of the overlay when in the background 
    @Override
    protected void onPause() {
      super.onPause();
      locOverlay.disableCompass();
      locOverlay.disableMyLocation();
    }
	
	@Override
	public boolean isRouteDisplayed() {
		return false;
	}
}
