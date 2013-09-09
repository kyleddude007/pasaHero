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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mapquest.android.Geocoder;

public class OptionsPanelFragment extends Fragment implements
		GeocodeTaskInterface {
	private OptionsPanelListenerInterface optionsListener;
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
		geoRequestor = "";
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
			optionsListener = (OptionsPanelListenerInterface) activity;
			this.activity = activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet MyListFragment.OnItemSelectedListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		optionsListener = null;
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
				switchToGeoHints();
				if(!geoRequestor.equals(Config.FROM_PLACE)){
					resetGeoHints();
				}
				geoRequestor = Config.FROM_PLACE;
				geoInput.setHint(getString(R.string.from_hint));
				geoInput.requestFocus();
			}

		});
		toView = (EditText) mainView.findViewById(R.id.toView);
		toView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!geoRequestor.equals(Config.TO_PLACE)){
					resetGeoHints();
				}
				switchToGeoHints();
				geoRequestor = Config.TO_PLACE;
				geoInput.setHint(getString(R.string.to_hint));
				geoInput.requestFocus();
			}

		});
		
		nav = (Button) mainView.findViewById(R.id.options_nav);
		nav.setTypeface(font);
		nav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switchToMain();
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
					geocodeTask = new GeocodeTask(geoRequestor,
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
		geoList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address address = (Address) parent.getItemAtPosition(position);
				geoInput.setText(GeoArrayAdapter.getShortName(address));
				optionsListener.locationChosen(address);
				if(geoRequestor.equals(Config.FROM_PLACE)){
					fromView.setText(GeoArrayAdapter.getShortName(address));
				}else if(geoRequestor.equals(Config.TO_PLACE)){
					toView.setText(GeoArrayAdapter.getShortName(address));
				}
				switchToMain();
				optionsListener.locationSelected(geoRequestor, address);
			}
			
		});
		
		planButton = (Button) mainView.findViewById(R.id.plan);
		planButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
							}
			
		});
	}
	
	public void switchToGeoHints(){
		optionsPanel.setVisibility(LinearLayout.GONE);
		geoHintsPanel.setVisibility(LinearLayout.VISIBLE);	
	}
	
	public void switchToMain(){
		geoHintsPanel.setVisibility(LinearLayout.GONE);
		optionsPanel.setVisibility(LinearLayout.VISIBLE);
	}
	
	public void resetGeoHints(){
		geoInput.setText("");
		geoAdapter.clear();
		geocodeTask = null;
	}
	
	@Override
	public void geocodeFinish(List<Address> result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void geocodeFinish(String provider, List<Address> result) {
		if (result == null || result.size() == 0) {
			Toast.makeText(activity, "No match found!", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(activity, "Loading results...", Toast.LENGTH_SHORT)
					.show();
			for (Address address : result) {
				geoAdapter.insert(address, 0);
			}
			Address address = result.get(0);
//			optionsListener.locationSelected(provider, address);
		}
	}

	public void geocode(String provider, String location) {
		geocodeTask.execute(location);
	}
}