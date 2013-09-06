package com.pasahero.android;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GeoArrayAdapter extends ArrayAdapter<Address> {

	private Context context;
	private Vector<Address> addresses;

	public void addResult(Address address) {
		addresses.add(address);
		this.notifyDataSetChanged();
	}

	public void clear() {
		addresses.clear();
		this.notifyDataSetChanged();
	}

	public GeoArrayAdapter(Context context, Vector<Address> addresses) {
		super(context, R.layout.row_layout, addresses);
		this.context = context;
		this.addresses = addresses;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(getShortName(addresses.get(position)));
		return rowView;
	}

	public static List<String> getShortNames(List<Address> addresses){
		Vector<String> shortNames = new Vector<String>();
		for(Address address: addresses){
			shortNames.add(getShortName(address));
		}
		return shortNames;
	}

	public static String getShortName(Address address) {
		String appelation = "";
		for (int i = 0; i < Config.MAX_ADDRESS_LINE_LENGTH; i++) {
			appelation = appelation + address.getAddressLine(i) + " ";
		}
		return appelation;
	}

}
