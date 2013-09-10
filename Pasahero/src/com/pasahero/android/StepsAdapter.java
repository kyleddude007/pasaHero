package com.pasahero.android;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StepsAdapter extends ArrayAdapter<Step> {

	private Context context;
	private List<Step> steps;
	
	public StepsAdapter(Context context, List<Step> steps) {
		super(context, R.layout.step_layout, steps);
		this.context = context;
		this.steps =steps;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.step_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.step_instruction);
		textView.setText((position+1)+". "+steps.get(position).toString());
		return rowView;
	}

}
