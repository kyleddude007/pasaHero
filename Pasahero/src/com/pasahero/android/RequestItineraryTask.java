package com.pasahero.android;

import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class RequestItineraryTask extends AsyncTask<URL, Void, Long>{
	
	private Context callingContext;
	
	public void setCallingContext(Context callingContext){
		this.callingContext = callingContext;
	}
	
	public RequestItineraryTask(Context callingContext) {
		this.callingContext = callingContext;
	}
	
	@Override
	protected Long doInBackground(URL... urls) {
		int len = urls.length;
		long totalSize = 0;
		TripPlanner mgr = new TripPlanner();
		for(int i =0; i<len; i++){
			mgr.get(urls[i]);
			if (isCancelled()) break;
		}
		return totalSize;
	}
	

    protected void onPostExecute(Long result) {
        Toast.makeText(callingContext,"Downloaded", Toast.LENGTH_SHORT).show();
    }

}
