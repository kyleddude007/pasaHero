package com.pasahero.android;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mapquest.android.Geocoder;
import com.mapquest.android.maps.AnnotationView;
import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.ItemizedOverlay;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.OverlayItem;

public class GeocodeTask extends AsyncTask<String, Void, List<Address>> {

	private Geocoder geocoder;
	private Context context;
	private MapView map;
	private GeoArrayAdapter adapter;
	private AnnotationView annotation;

	public GeocodeTask(Context context, MapView map, Geocoder geocoder,
			GeoArrayAdapter adapter, AnnotationView annotation) {
		this.context = context;
		this.map = map;
		this.geocoder = geocoder;
		this.adapter = adapter;
		this.annotation = annotation;
	}

	protected List<Address> doInBackground(String... location) {
		try {
			return geocoder.getFromLocationName(location[0], Config.MAX_GEO);
		} catch (Exception e) {
			return null;
		}

	}

	protected void onPostExecute(List<Address> result) {
		if (result == null || result.size() == 0) {
			Toast.makeText(context, "No match found!", Toast.LENGTH_SHORT)
					.show();
		} else {
			for(Address address: result){
				/**System.out.print("Feature name: ");
				System.out.println(address.getFeatureName());
				System.out.print("admin area name: ");
				System.out.println(address.getAdminArea());
				System.out.print("contry name: ");
				System.out.println(address.getCountryName());
				System.out.print("locality name: ");
				System.out.println(address.getLocality());
				System.out.print("premises name: ");
				System.out.println(address.getPremises());
				System.out.print("sublocality name: ");
				System.out.println(address.getSubLocality());
				System.out.print("thoroughfare name: ");
				System.out.println(address.getThoroughfare());**/
				System.out.print("Locale");
				System.out.println(address.getLocale());
				//System.out.println(address.)
				addOverlay(address.getLatitude(), address.getLongitude());
				map.getController().setCenter(new GeoPoint(address.getLatitude(), address.getLongitude()));
			}
	//		map.getController().setCenter(new GeoPoint(address.getLatitude(), address.getLongitude()));
			
		}
	}

	private void addOverlay(double lat, double lon) {
        Drawable icon = context.getResources().getDrawable(R.drawable.location_marker);
    	final DefaultItemizedOverlay overlay = new DefaultItemizedOverlay(icon);
    	
    	OverlayItem item = new OverlayItem(new GeoPoint(lat, lon), "New York, NY", "The Big Apple");
    	overlay.addItem(item);
    	
    	// add a tap listener
    	overlay.setTapListener(new ItemizedOverlay.OverlayTapListener() {
			@Override
			public void onTap(GeoPoint pt, MapView mapView) {
				// when tapped, show the annotation for the overlayItem
				int lastTouchedIndex = overlay.getLastFocusedIndex();
				if(lastTouchedIndex>-1){
					OverlayItem tapped = overlay.getItem(lastTouchedIndex);
					annotation.showAnnotationView(tapped);
				}
			}
		});
    	
    	map.getOverlays().add(overlay);
    	map.invalidate();
    }
}
