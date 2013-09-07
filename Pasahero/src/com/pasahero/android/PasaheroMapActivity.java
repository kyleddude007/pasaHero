package com.pasahero.android;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.mapquest.android.maps.MapController;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.Overlay;
import com.mapquest.android.maps.OverlayItem;
import com.mapquest.android.maps.PolygonOverlay;
import com.mapquest.android.maps.RouteManager;

public class PasaheroMapActivity extends MapActivity implements
		GeocodeTaskInterface, RequestItineraryInterface {
	private MyLocationOverlay locOverlay;
	protected MapView map;
	AnnotationView annotation;
	private Geocoder geocoder;
	private GeoArrayAdapter adapter;
	private List<Overlay> overlays;
	private Hashtable<Integer, Overlay> markerOverlayHolder;
	private MapController mapCtrl;
	private RequestItineraryInterface requestItineraryInterface;
	private Context context;
	private Address from;
	private Address to;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pasahero_map);
		setUpMapView();
		getGeocoder();
		setUpViews();
		this.overlays = map.getOverlays();
		this.markerOverlayHolder = new Hashtable<Integer, Overlay>();
		this.context = context;
		this.from = null;
		this.to = null;
		// setUpMyLocation();
		// addPolyOverlay();
		// addLineOverlay();
		// addPoiOverlay();
		// displayRoute();
		this.mapCtrl = map.getController();
		this.requestItineraryInterface = this;
	}

	public void setUpMapView() {
		this.map = (MapView) findViewById(R.id.mapView);
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
		geoList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		geoList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address address = (Address) parent.getItemAtPosition(position);
				overlays.remove(markerOverlayHolder.remove(adapter
						.getSelected().hashCode()));
				map.invalidate();
				setAddress(adapter.getProvider(), address);
				System.out.println("from: "+from.toString());
				System.out.println("to: "+to.toString());
			}

		});

		final EditText fromView = (EditText) findViewById(R.id.fromView);
		fromView.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				EditText et = (EditText) view;
				String from = et.getText().toString();
				if (!hasFocus && !from.matches("")) {
					GeoArrayAdapter.reset(adapter);
					adapter.setProvider(Config.FROM_PLACE);
					geocode(Config.FROM_PLACE, from);
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
					GeoArrayAdapter.reset(adapter);
					adapter.setProvider(Config.TO_PLACE);
					geocode(Config.TO_PLACE, to);
				}
			}

		});

		Button plan = (Button) findViewById(R.id.plan);
		plan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Hashtable<String, String> params = new Hashtable<String, String>();
				params.put(Config.ARRIVE_BY, "false");
				params.put(Config.DATE, new Date().toString());
				params.put(Config.FROM_PLACE, from.getLatitude()+","+from.getLongitude());
				params.put(Config.TO_PLACE, to.getLatitude()+","+to.getLongitude());
				params.put(Config.MAX_WALK_DISTANCE, "840");
				params.put(Config.OPTIMIZE, "QUICK");
				params.put(Config.MODE, "TRANSIT,WALK");
				params.put(Config.TIME, "10:25am");
				params.put(Config.WALK_SPEED, "1.341");
				System.out.println("From: "+from);
				System.out.println("To: "+to);
				RequestItineraryTask request = new RequestItineraryTask(context, requestItineraryInterface);
				request.execute(TripPlanner.contsructUrl(Config.API_URL, params));
			}
		});
	}

	public void geocode(String provider, String location) {
		GeocodeTask geocodeTask = new GeocodeTask(provider, geocoder, this);
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

	public void addMarker(Address address, String label, String blurb) {
		Drawable icon = this.getResources().getDrawable(
				R.drawable.location_marker);
		final DefaultItemizedOverlay overlay = new DefaultItemizedOverlay(icon);

		OverlayItem item = new OverlayItem(new GeoPoint(address.getLatitude(),
				address.getLongitude()), label, blurb);
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
		markerOverlayHolder.put(address.hashCode(), overlay);
		// map.getOverlays().add(overlay);
		map.invalidate();
	}

	public void replaceMarker(DefaultItemizedOverlay oldMarker,
			DefaultItemizedOverlay newMarker) {
		overlays.remove(oldMarker);
		overlays.add(newMarker);
	}

	public void setMarker(DefaultItemizedOverlay overlay) {
		overlays.add(overlay);
		map.invalidate();
	}

	@Override
	public void geocodeFinish(String provider, List<Address> result) {
		System.out.println("Provider: "+provider);
		if (result == null || result.size() == 0) {
			Toast.makeText(this, "No match found!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Loading results...", Toast.LENGTH_SHORT)
					.show();
			for (Address address : result) {
				System.out.println(address.toString());
				adapter.add(address);
			}
			Address address = result.get(0);
			setAddress(provider, address);
		}
	}
	
	private void setAddress(String provider, Address address){
		adapter.setSelected(address);
		addMarker(address, GeoArrayAdapter.getShortName(address), "");
		map.getController()
				.setCenter(
						new GeoPoint(address.getLatitude(), address
								.getLongitude()));
		if(provider.equals(Config.FROM_PLACE)){
			from = address;
		}else if(provider.equals(Config.TO_PLACE)){
			to = address;
		}
	}

	@Override
	public void loadItinerary(Response response) {
		System.out.println(response);
	}

	@Override
	public void geocodeFinish(List<Address> result) {	
	}

}
