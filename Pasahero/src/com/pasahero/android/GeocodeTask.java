package com.pasahero.android;

import java.util.List;

import android.location.Address;
import android.os.AsyncTask;

import com.mapquest.android.Geocoder;

public class GeocodeTask extends AsyncTask<String, Void, List<Address>> {

	private Geocoder geocoder;
	private GeocodeTaskInterface geocodeTaskInterface;
	private String provider;

	public GeocodeTask(String provider, Geocoder geocoder,
			GeocodeTaskInterface geocodeTaskInterface) {
		this.geocoder = geocoder;
		this.geocodeTaskInterface = geocodeTaskInterface;
		this.provider = provider;
	}
	
	public GeocodeTask(Geocoder geocoder,
			GeocodeTaskInterface geocodeTaskInterface) {
		this.geocoder = geocoder;
		this.geocodeTaskInterface = geocodeTaskInterface;
	}


	protected List<Address> doInBackground(String... location) {
		try {
			return geocoder.getFromLocationName(location[0], Config.MAX_GEO);
		} catch (Exception e) {
			return null;
		}

	}

	protected void onPostExecute(List<Address> result) {
		geocodeTaskInterface.geocodeFinish(provider, result);
	}

}
