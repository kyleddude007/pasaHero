package com.pasahero.android;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.location.Address;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mapquest.android.Geocoder;

public class OptionsPanelFragment extends Fragment implements
		GeocodeTaskInterface {
	private OptionsPanelListenerInterface listener;
	private GeocodeTaskInterface geoListener;

	private EditText fromView;
	private EditText toView;
	private Button planButton;
	private Button nav;
	private LinearLayout optionsPanel;
	private LinearLayout geoHintsPanel;
	private EditText geoInput;
	private GeoArrayAdapter geoAdapter;
	private Geocoder geocoder;
	private Activity activity;
	private String geoRequestor;
	private ListView geoList;
	private GeocodeTask geocodeTask;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		geocoder = new Geocoder(activity);
		geocodeTask = null;
		geoListener = this;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_options, container,
				false);
		Typeface font = Typefaces.get(activity, Config.FONTAWESOME_URL);
		setUpViews(view, font);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OptionsPanelListenerInterface) {
			listener = (OptionsPanelListenerInterface) activity;
			this.activity = activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet MyListFragment.OnItemSelectedListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}

	private void setUpViews(View mainView, Typeface font) {
		optionsPanel = (LinearLayout) mainView
				.findViewById(R.id.mainOptionsPanel);
		geoHintsPanel = (LinearLayout) mainView
				.findViewById(R.id.geoHintsPanel);
		geoHintsPanel.setVisibility(LinearLayout.GONE);
		fromView = (EditText) mainView.findViewById(R.id.fromView);
		fromView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				optionsPanel.setVisibility(LinearLayout.GONE);
				geoHintsPanel.setVisibility(LinearLayout.VISIBLE);
				geoRequestor = Config.FROM_PLACE;

			}

		});
		nav = (Button) mainView.findViewById(R.id.options_nav);
		nav.setTypeface(font);
		nav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				geoHintsPanel.setVisibility(LinearLayout.GONE);
				optionsPanel.setVisibility(LinearLayout.VISIBLE);
			}
		});
		geoInput = (EditText) mainView.findViewById(R.id.geoInput);
		geoInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if (geocodeTask != null) {
					geocodeTask.execute(s.toString());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (count > Config.TEXT_CHANGE_COUNT) {
					geocodeTask = geocodeTask = new GeocodeTask(geoRequestor,
							geocoder, geoListener);
					geoAdapter.clear();
				} else {
					geocodeTask = null;
				}
			}

		});
		geoList = (ListView) mainView.findViewById(R.id.geoList);
		geoAdapter = new GeoArrayAdapter(activity, new Vector<Address>());
		geoList.setAdapter(geoAdapter);
	}

	@Override
	public void geocodeFinish(List<Address> result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void geocodeFinish(String provider, List<Address> result) {
		System.out.println("Provider: " + provider);
		if (result == null || result.size() == 0) {
			Toast.makeText(activity, "No match found!", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(activity, "Loading results...", Toast.LENGTH_SHORT)
					.show();
			for (Address address : result) {
				System.out.println(address.toString());
				geoAdapter.add(address);
			}
			Address address = result.get(0);
		}
	}

	public void geocode(String provider, String location) {
		geocodeTask.execute(location);

	}
}