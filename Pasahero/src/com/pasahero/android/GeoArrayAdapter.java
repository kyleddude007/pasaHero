package com.pasahero.android;

import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GeoArrayAdapter extends ArrayAdapter<String> {

	private Context context;
	private Vector<String> results;

	public void addResult(String result){
		results.add(result);
		this.notifyDataSetChanged();
	}
	
	public void clear(){
		results.clear();
		this.notifyDataSetChanged();
	}
	
	public GeoArrayAdapter(Context context, Vector<String> results) {
		super(context, R.layout.row_layout, results);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(results.get(position));
		return rowView;
	}
	
	

}
