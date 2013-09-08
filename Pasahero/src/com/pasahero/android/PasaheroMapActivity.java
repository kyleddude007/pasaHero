package com.pasahero.android;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.opentripplanner.util.PolylineEncoder;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
		GeocodeTaskInterface, RequestItineraryInterface, OptionsPanelListenerInterface {
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
		//setUpViews();
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
		ActionBar bar = this.getActionBar();
		bar.hide();
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
				System.out.println("from: " + from.toString());
				System.out.println("to: " + to.toString());
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
				params.put(Config.FROM_PLACE,
						from.getLatitude() + "," + from.getLongitude());
				params.put(Config.TO_PLACE,
						to.getLatitude() + "," + to.getLongitude());
				params.put(Config.MAX_WALK_DISTANCE, "840");
				params.put(Config.OPTIMIZE, "QUICK");
				params.put(Config.MODE, "TRANSIT,WALK");
				params.put(Config.TIME, "10:25am");
				params.put(Config.WALK_SPEED, "1.341");
				System.out.println("From: " + from);
				System.out.println("To: " + to);
				RequestItineraryTask request = new RequestItineraryTask(
						requestItineraryInterface);
				// request.execute(TripPlanner.contsructUrl(Config.API_URL,
				// params));
				try {
					request.execute(new URL(Config.SAMPLE_URL));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		System.out.println("Provider: " + provider);
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

	private void setAddress(String provider, Address address) {
		adapter.setSelected(address);
		addMarker(address, GeoArrayAdapter.getShortName(address), "");
		map.getController().setCenter(
				new GeoPoint(address.getLatitude(), address.getLongitude()));
		if (provider.equals(Config.FROM_PLACE)) {
			from = address;
		} else if (provider.equals(Config.TO_PLACE)) {
			to = address;
		}
	}

	@Override
	public void loadItinerary(Response response) {
		System.out.println(response);
		Plan plan = response.getPlan();
		Vector<Itinerary> itineraries = plan.getItineraries();
		Vector<Leg> legs = itineraries.get(0).getLegs();
		Vector<GeoPoint> lineData = new Vector<GeoPoint>();
		for (Leg leg : legs) {
			String mode = leg.getMode();
			/*
			 * if (mode.equals(Config.MODE_WALK)) { Vector<Step> steps =
			 * leg.getSteps(); for (Step step : steps) {
			 * //System.out.println("Walk ");
			 * //System.out.println(step.getAbsoluteDirection());
			 * //System.out.println(step.getStreetName());
			 * //System.out.println("-------------");
			 * List<com.vividsolutions.jts.geom.Coordinate> polyLines =
			 * PolylineEncoder.decode(leg.getLegGeometry());
			 * for(com.vividsolutions.jts.geom.Coordinate line: polyLines){
			 * System.out.println(line.x+" , "+line.y+" , "+line.z);
			 * lineData.add(new GeoPoint(line.x, line.y)); } } }else
			 * if(mode.equals(Config.MODE_RAIL)){ System.out.println("Ride");
			 * System.out.println(leg.getFrom().getName());
			 * System.out.println(leg.getTo().getName());
			 * List<com.vividsolutions.jts.geom.Coordinate> polyLines =
			 * PolylineEncoder.decode(leg.getLegGeometry());
			 * for(com.vividsolutions.jts.geom.Coordinate line: polyLines){
			 * System.out.println(line.x+" , "+line.y+" , "+line.z);
			 * lineData.add(new GeoPoint(line.x, line.y)); } }
			 */
			List<com.vividsolutions.jts.geom.Coordinate> polyLines = PolylineEncoder
					.decode(leg.getLegGeometry());
			for (com.vividsolutions.jts.geom.Coordinate line : polyLines) {
				System.out.println(line.x + " , " + line.y + " , " + line.z);
				lineData.add(new GeoPoint(line.x, line.y));
			}
		}
		System.out.println("Line data: ");
		System.out.println(lineData);
		Drawing draw = new Drawing(map, lineData, overlays);
		draw.draw();

	}

	@Override
	public void geocodeFinish(List<Address> result) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar, menu);
		return true;
	}

	@Override
	public void locationEntered(String location) {
		
	}

	@Override
	public void planningStarted() {
		
	}
	
}