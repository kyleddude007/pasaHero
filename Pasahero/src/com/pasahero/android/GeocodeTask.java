package com.pasahero.android;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.os.AsyncTask;

import com.mapquest.android.Geocoder;

public class GeocodeTask extends AsyncTask<String, Void, List<Address>> {

	private Geocoder geocoder;
	private GeocodeTaskInterface geocodeTaskInterface;
	private String provider;
	private boolean running;

	@Override
	protected void onCancelled() {
		running = false;
	}

	public GeocodeTask(String provider, Geocoder geocoder,
			GeocodeTaskInterface geocodeTaskInterface) {
		this.geocoder = geocoder;
		this.geocodeTaskInterface = geocodeTaskInterface;
		this.provider = provider;
		running = false;
	}

	@Override
	protected List<Address> doInBackground(String... location) {
		running = true;
		while (running) {
			try {
				return geocoder.getFromLocationName(location[0],
						Config.MAX_GEO, Config.NCR_LOWER_LEFT_LAT,
						Config.NCR_LOWER_LEFT_LON, Config.NCR_UPPER_RIGHT_LAT,
						Config.NCR_UPPER_RIGHT_LON);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<Address> result) {
		if (result != null) {
			geocodeTaskInterface.geocodeFinish(provider, result);
		}
	}

}
