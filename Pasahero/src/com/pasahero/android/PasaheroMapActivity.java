package com.pasahero.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mapquest.android.Geocoder;
import com.mapquest.android.maps.AnnotationView;
import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.ItemizedOverlay;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.Overlay;
import com.mapquest.android.maps.OverlayItem;
import com.mapquest.android.maps.PolygonOverlay;
import com.mapquest.android.maps.RouteManager;

public class PasaheroMapActivity extends MapActivity implements
		GeocodeTaskInterface {
	private MyLocationOverlay locOverlay;
	protected MapView map;
	AnnotationView annotation;
	private Geocoder geocoder;
	private GeoArrayAdapter adapter;
	private List<Overlay> overlays;
	private Overlay fromMarker;
	private Overlay toMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pasahero_map);
		setUpMapView();
		getGeocoder();
		setUpViews();
		this.overlays = map.getOverlays();
		// setUpMyLocation();
		// addPolyOverlay();
		// addLineOverlay();
		// addPoiOverlay();
		// displayRoute();

	}

	public void setUpMapView() {
		this.map = (MapView) findViewById(R.id.mapView);
		MapView map = (MapView) findViewById(R.id.mapView);
		map.getController().setZoom(11);
		map.getController().setCenter(
				new GeoPoint(Config.NCR_LAT, Config.NCR_LON));
		map.setBuiltInZoomControls(true);
		annotation = new AnnotationView(map);
	}

	public void setUpViews() {
		ListView geoList = (ListView) findViewById(R.id.geoList);
		adapter = new GeoArrayAdapter(this, new Vector<Address>());
		geoList.setAdapter(adapter);

		final EditText fromView = (EditText) findViewById(R.id.fromView);
		fromView.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				EditText et = (EditText) view;
				String from = et.getText().toString();
				if (!hasFocus && !from.matches("")) {
					adapter.clear();
					geocode(from);
				}
			}

		});

		final EditText toView = (EditText) findViewById(R.id.toView);
		toView.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				EditText et = (EditText) view;
				String to = et.getText().toString();
				if (!hasFocus && !to.matches("")) {
					adapter.clear();
					geocode(to);
				}
			}

		});

		Button plan = (Button) findViewById(R.id.plan);
		plan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// geocode(fromView.getText().toString());

			}
		});
	}

	public void geocode(String location) {
		GeocodeTask geocodeTask = new GeocodeTask(geocoder, this);
		geocodeTask.execute(location);
	}

	protected Geocoder getGeocoder() {
		if (geocoder == null) {
			geocoder = new Geocoder(this);
		}
		return geocoder;
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

	public void addMarker(double lat, double lon, String label, String blurb) {
		Drawable icon = this.getResources().getDrawable(
				R.drawable.location_marker);
		final DefaultItemizedOverlay overlay = new DefaultItemizedOverlay(icon);

		OverlayItem item = new OverlayItem(new GeoPoint(lat, lon), label, blurb);
		overlay.addItem(item);

		overlay.setTapListener(new ItemizedOverlay.OverlayTapListener() {
			@Override
			public void onTap(GeoPoint pt, MapView mapView) {
				int lastTouchedIndex = overlay.getLastFocusedIndex();
				if (lastTouchedIndex > -1) {
					OverlayItem tapped = overlay.getItem(lastTouchedIndex);
					annotation.showAnnotationView(tapped);
				}
			}
		});

		overlays.add(overlay);
		//map.getOverlays().add(overlay);
		map.invalidate();
	}

	public void replaceMarker( DefaultItemizedOverlay oldMarker,  DefaultItemizedOverlay newMarker){
		overlays.remove(oldMarker);
		overlays.add(newMarker);	
	}
	
	public void setMarker( DefaultItemizedOverlay overlay){
		overlays.add(overlay);
		map.invalidate();
	}
	
	@Override
	public void geocodeFinish(List<Address> result) {
		if (result == null || result.size() == 0) {
			Toast.makeText(this, "No match found!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Loading results..." ,Toast.LENGTH_SHORT ).show();
			for (Address address : result) {
				System.out.println(address.toString());
				adapter.add(address);
			}
			Address address = result.get(0);
			addMarker(address.getLatitude(), address.getLongitude(), GeoArrayAdapter.getShortName(address), "");
			map.getController().setCenter(new GeoPoint(address.getLatitude(), address.getLongitude()));
			System.out.println("Action completed");
		}
	}

	
	

}
