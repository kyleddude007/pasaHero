package com.pasahero.android;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import android.os.AsyncTask;
import android.view.View;

public class RequestPNRTableTask extends AsyncTask<URL, Void, Hashtable<String, String>>{
	private TripPlannerInterface pnrListener;
	private View fareView;
	private String end;
	
	public RequestPNRTableTask(TripPlannerInterface pnrListener,  String end, View fareView){
		this.pnrListener = pnrListener;
		this.fareView = fareView;
		this.end =end;
	}
	
	@Override
	protected Hashtable<String, String> doInBackground(URL... urls) {
		URL url = urls[0];
		TripPlanner planner = new TripPlanner();
		Hashtable<String, String> pnrTable = null;
		try {
			pnrTable = planner.getPNRTable(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pnrTable;
	}

	@Override
	protected void onPostExecute(Hashtable<String, String> pnrTable) {
		pnrListener.pnrTableRead(pnrTable, end, fareView);
	}

}
