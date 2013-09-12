package com.pasahero.android;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItineraryAdapter extends ArrayAdapter<Itinerary>{

	private List<Itinerary> itineraries;
	private Context context;
	
	public ItineraryAdapter(Context context, List<Itinerary> itineraries) {
		super(context, R.layout.itinerary_row_layout, itineraries);
		this.context = context;
		this.itineraries = itineraries;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.itinerary_row_layout, parent, false);
		Itinerary it = itineraries.get(position);
		TextView timeBounds = (TextView) rowView.findViewById(R.id.itinerary_row_time_boundaries);
		timeBounds.setText(Utils.getShortTime(it.getStartTime())+" - "+Utils.getShortTime(it.getEndTime()));
		TextView duration = (TextView) rowView.findViewById(R.id.itinerary_row_duration);
		duration.setText(Utils.toDurationReadable(it.getDuration()));
		TextView transfers = (TextView) rowView.findViewById(R.id.itinerary_row_num_transfers);
		transfers.setText(it.getTransfers()+"");
		return rowView;
	}
	
	

}
