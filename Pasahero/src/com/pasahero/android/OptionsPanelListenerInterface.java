package com.pasahero.android;
import android.app.ProgressDialog;
import android.location.Address;
public interface OptionsPanelListenerInterface {
	public void locationEntered(String location);
	public void planningStarted();
	public void locationSelected(String provider, Address address);
	public void locationChosen(Address location);
	public void itineraryReceived(Plan plan);
	public void readyToReceivMyLocation();
	public void itineraryNotPossible();
}
