package com.pasahero.android;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
		TripPlannerInterface, OptionsPanelListenerInterface,
		ItineraryFragmentInterface {
	private MyLocationOverlay locOverlay;
	protected MapView map;
	AnnotationView annotation;
	private Geocoder geocoder;
	private GeoArrayAdapter adapter;
	private List<Overlay> overlays;
	private Hashtable<Integer, Overlay> markerOverlayHolder;
	private MapController mapCtrl;
	private TripPlannerInterface requestItineraryInterface;
	private Context context;
	private Address from;
	private Address to;
	private Button nav;
	private PasaHeroMapInterface mapListener;
	private PasaHeroMapInterface mapItineraryListener;
	private OptionsPanelFragment optionsFragment;
	private ItineraryFragment itineraryFragment;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pasahero_map);

		// setUpViews();
		this.markerOverlayHolder = new Hashtable<Integer, Overlay>();
		this.context = context;
		this.from = null;
		this.to = null;
		this.requestItineraryInterface = this;
		fragmentManager = getFragmentManager();

		optionsFragment = (OptionsPanelFragment) fragmentManager
				.findFragmentById(R.id.options_panel_fragment);
		mapListener = optionsFragment;
		itineraryFragment = (ItineraryFragment) fragmentManager
				.findFragmentById(R.id.itinerary_fragment);
		mapItineraryListener = itineraryFragment;
		ActionBar bar = this.getActionBar();
		// bar.hide();
		setUpMapView();
		setUpNav();
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
		Typeface font = Typefaces.get(this, Config.FONTAWESOME_URL);
		nav = (Button) findViewById(R.id.options_nav);
		nav.setTypeface(font);
		nav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mapListener.navButtonClicked();
			}
		});
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

	private void setAddress(String provider, Address address) {
		// adapter.setSelected(address);
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
		setAddress(provider, address);
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
		mapItineraryListener.planReceived(plan);

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

}