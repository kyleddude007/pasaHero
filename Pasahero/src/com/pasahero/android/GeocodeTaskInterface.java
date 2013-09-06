package com.pasahero.android;

import java.util.List;

import android.location.Address;

public interface GeocodeTaskInterface {
	public void geocodeFinish(List<Address> result);
}
