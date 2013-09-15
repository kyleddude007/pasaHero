package com.pasahero.android;

import java.util.Hashtable;
import java.util.List;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.mapquest.android.Geocoder;
import com.mapquest.android.maps.AnnotationView;
import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.ItemizedOverlay;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapController;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.Overlay;
import com.mapquest.android.maps.OverlayItem;

public class PasaheroMapActivity extends MapActivity implements
		TripPlannerInterface, OptionsPanelListenerInterface,
		ItineraryFragmentInterface {
	private MyLocationOverlay locOverlay;
	protected MapView map;
	AnnotationView annotation;
	private Geocoder geocoder;
	private List<Overlay> overlays;
	private Hashtable<String, Overlay> markerOverlayHolder;
	private MapController mapCtrl;
	private TripPlannerInterface tripPlannerListening;
	private Context context; 
	private Button nav;
	private PasaHeroMapInterface optionsListening;
	private PasaHeroMapInterface itineraryListening;
	private OptionsPanelFragment optionsFragment;
	private ItineraryFragment itineraryFragment;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private Typeface fontawesome;
	private Button myLocation;
	private GeoPoint currentLocation;
	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pasahero_map);
		session = new SessionManager(this);
		//session.logoutUser();
		//session.checkLogin();
		fontawesome = Typefaces.get(this, Config.FONTAWESOME_URL);

		// setUpViews();
		this.markerOverlayHolder = new Hashtable<String, Overlay>();
		fragmentManager = getFragmentManager();

		optionsFragment = (OptionsPanelFragment) fragmentManager
				.findFragmentById(R.id.options_panel_fragment);
		optionsListening = optionsFragment;
		itineraryFragment = (ItineraryFragment) fragmentManager
				.findFragmentById(R.id.itinerary_fragment);
		itineraryListening = itineraryFragment;

		ActionBar bar = this.getActionBar();
		bar.hide();
		setUpMapView();
		setUpNav();
		setupMyLocation();
		setUpMyLocationButton();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.hide(itineraryFragment);
		fragmentTransaction.commit();
	}

	public void setUpMapView() {
		map = (MapView) findViewById(R.id.mapView);
		mapCtrl = map.getController();
		overlays = map.getOverlays();
		mapCtrl.setZoom(Config.MAP_ZOOM);
		mapCtrl.setCenter(new GeoPoint(Config.NCR_LAT, Config.NCR_LON));
		map.setBuiltInZoomControls(true);
		annotation = new AnnotationView(map);
	}

	public void setUpNav() {
		nav = (Button) findViewById(R.id.options_nav);
		nav.setTypeface(fontawesome);
		nav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				optionsListening.navButtonClicked();
			}
		});
	}

	public void setUpMyLocationButton() {
		myLocation = (Button) findViewById(R.id.my_location);
		myLocation.setTypeface(fontawesome);
		myLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				locOverlay.setFollowing(true);
				optionsListening.myLocationReady(currentLocation);
			}

		});
	}

	protected void setupMyLocation() {
		locOverlay = new MyLocationOverlay(this, map);
		locOverlay.enableMyLocation();
		locOverlay.runOnFirstFix(new Runnable() {
			@Override
			public void run() {
				currentLocation = locOverlay.getMyLocation();
				mapCtrl.animateTo(currentLocation);
				mapCtrl.setZoom(Config.MAP_ZOOM);
				map.getOverlays().add(locOverlay);
				locOverlay.setFollowing(true);
			}
		});
	}

	@Override
	protected void onResume() {
		locOverlay.enableMyLocation();
		// locOverlay.enableCompass();
		super.onResume();
	}

	// disable features of the overlay when in the background
	@Override
	protected void onPause() {
		super.onPause();
		// locOverlay.disableCompass();
		locOverlay.disableMyLocation();
	}

	@Override
	public boolean isRouteDisplayed() {
		return false;
	}

	public void addMarker(String provider, Address address, String label, String blurb) {
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
		markerOverlayHolder.put(provider, overlay);
		map.invalidate();
	}

	private void setAddressMarker(String provider, Address address) {
		removeMarker(provider);
		addMarker(provider, address, address.getLocality(), "");
		mapCtrl.setCenter(
				new GeoPoint(address.getLatitude(), address.getLongitude()));
	}

	/**
	 * Remove marker at the address specified if it exists
	 * @param address
	 */
	private void removeMarker(String provider) {
		if (markerOverlayHolder.containsKey(provider)) {
			Overlay oldMarker = markerOverlayHolder.get(provider);
			overlays.remove(oldMarker);
			markerOverlayHolder.remove(oldMarker);
			map.invalidate();
		}
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

	@Override
	public void locationSelected(String provider, Address address) {	
		setAddressMarker(provider, address);
	}

	@Override
	public void locationChosen(Address address) {

	}

	@Override
	public void itineraryReceived(Plan plan) {
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.hide(optionsFragment);
		fragmentTransaction.show(itineraryFragment);
		fragmentTransaction.commit();
		itineraryListening.planReceived(plan);
	}

	@Override
	public void lineDataReady(List<GeoPoint> data, int color) {
		Drawing draw = new Drawing(map, data, overlays, color);
		draw.draw();
	}

	@Override
	public void loadItinerary(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fareReady(Fare fare, String legMode, View itineraryView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pnrTableRead(Hashtable<String, String> pnrTable, String end,
			View fareView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readyToReceivMyLocation() {
		// TODO Auto-generated method stub

	}

	@Override
	public void itineraryNotPossible() {
		overlays.remove(markerOverlayHolder.get(Config.FROM_PLACE));
		overlays.remove(markerOverlayHolder.get(Config.TO_PLACE));
		map.invalidate();
	}

}