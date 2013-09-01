package com.pasahero.android;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.mapquest.android.maps.AnnotationView;
import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.ItemizedOverlay;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.OverlayItem;
import com.mapquest.android.maps.PolygonOverlay;
import com.mapquest.android.maps.RouteManager;

public class PasaheroMapActivity extends MapActivity {
	private MyLocationOverlay locOverlay;
	protected MapView map;
	AnnotationView annotation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pasahero_map);
		setUpMapView();
		// setUpMyLocation();
		addPolyOverlay();
		addLineOverlay();
		addPoiOverlay();
		displayRoute();

	}

	public void setUpMapView() {
		this.map = (MapView) findViewById(R.id.mapView);
		map.setBuiltInZoomControls(true);
		annotation = new AnnotationView(map);
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

	// add polygon overlay to map
	private void addPolyOverlay() {
		// set custom polygon style
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.FILL);
		paint.setAlpha(40);

		// list of GeoPoint objects to be used to draw polygon
		List<GeoPoint> polyData = new ArrayList<GeoPoint>();
		polyData.add(new GeoPoint(41.000702, -109.049979));
		polyData.add(new GeoPoint(41.002528, -102.051699));
		polyData.add(new GeoPoint(36.993105, -102.042215));
		polyData.add(new GeoPoint(36.999073, -109.045178));

		// apply polygon style & data and add to map
		PolygonOverlay polyOverlay = new PolygonOverlay(paint);
		polyOverlay.setData(polyData);
		map.getOverlays().add(polyOverlay);
	}

	// add line overlay to map
	private void addLineOverlay() {
		// set custom line style
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);

		// list of GeoPoint objects to be used to draw line
		List lineData = new ArrayList();
		lineData.add(new GeoPoint(39.739983, -104.984727));
		lineData.add(new GeoPoint(37.441903, -122.141895));

		// apply line style & data and add to map
		LineOverlay lineOverlay = new LineOverlay(paint);
		lineOverlay.setData(lineData);
		map.getOverlays().add(lineOverlay);
	}

	// add an itemized overlay to map
	private void addPoiOverlay() {

		// use a custom POI marker by referencing the bitmap file directly,
		// using the filename as the resource ID
		Drawable icon = getResources().getDrawable(R.drawable.location_marker);
		final DefaultItemizedOverlay poiOverlay = new DefaultItemizedOverlay(
				icon);

		// set GeoPoints and title/snippet to be used in the annotation view
		OverlayItem poi1 = new OverlayItem(
				new GeoPoint(39.739983, -104.984727), "Denver, Colorado",
				"MapQuest Headquarters");
		poiOverlay.addItem(poi1);
		OverlayItem poi2 = new OverlayItem(
				new GeoPoint(37.441903, -122.141895), "Palo Alto, California",
				"AOL Offices");
		poiOverlay.addItem(poi2);

		// add a tap listener for the POI overlay
		poiOverlay.setTapListener(new ItemizedOverlay.OverlayTapListener() {
			@Override
			public void onTap(GeoPoint pt, MapView mapView) {
				// when tapped, show the annotation for the overlayItem
				int lastTouchedIndex = poiOverlay.getLastFocusedIndex();
				if (lastTouchedIndex > -1) {
					OverlayItem tapped = poiOverlay.getItem(lastTouchedIndex);
					annotation.showAnnotationView(tapped);
				}
			}
		});

		map.getOverlays().add(poiOverlay);
	}

	private void displayRoute() {
		RouteManager routeManager = new RouteManager(this);
		routeManager.setMapView(map);
		routeManager.createRoute("San Francisco, CA", "Fremont, CA");
	}

	@Override
	protected void onResume() {
		// locOverlay.enableMyLocation();
		// locOverlay.enableCompass();
		super.onResume();
	}

	// disable features of the overlay when in the background
	@Override
	protected void onPause() {
		super.onPause();
		// locOverlay.disableCompass();
		// locOverlay.disableMyLocation();
	}

	@Override
	public boolean isRouteDisplayed() {
		return false;
	}
}
