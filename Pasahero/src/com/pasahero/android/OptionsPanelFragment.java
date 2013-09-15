package com.pasahero.android;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mapquest.android.Geocoder;
import com.mapquest.android.maps.GeoPoint;

public class OptionsPanelFragment extends Fragment implements
		GeocodeTaskInterface, TripPlannerInterface, PasaHeroMapInterface,
		TimePickerDialog.OnTimeSetListener {
	private OptionsPanelListenerInterface optionsListener;
	private GeocodeTaskInterface geoListener;

	private EditText fromView;
	private EditText toView;
	private Button planButton;
	private LinearLayout optionsPanel;
	private LinearLayout geoHintsPanel;
	private EditText geoInput;
	private GeoArrayAdapter geoAdapter;
	private Geocoder geocoder;
	private Activity activity;
	private String geoRequestor;
	private ListView geoList;
	private GeocodeTask geocodeTask;
	private Hashtable<String, Address> targets;
	private TripPlannerInterface itineraryListener;
	private View moreOptionsPanel;
	private Button moreOptionsToggle;
	private Spinner timeOptions;
	private TextView timeDisplay;
	private EditText walkSpeedView;
	private EditText walkDistanceView;
	private boolean arriveBy;
	private TimePickerDialog.OnTimeSetListener timePickerListener;
	private AlertDialog errorDialog;
	private RequestInterruptInterface geocodeTaskListening;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		geocoder = new Geocoder(activity);
		geocodeTask = null;
		geoRequestor = "";
		geoListener = this;
		targets = new Hashtable<String, Address>();
		itineraryListener = this;
		arriveBy = false;
		timePickerListener = this;
		loadDefaultOptions();
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

	private void setMyLocation(EditText view, GeoPoint myLocationPoint) {
		view.setText(Config.MY_LOCATION_STRING);
		Address address = new Address(Locale.ENGLISH);
		address.setLatitude(myLocationPoint.getLatitude());
		address.setLongitude(myLocationPoint.getLongitude());
		address.setLocality(Config.MY_LOCATION_STRING);
		targets.put(Config.FROM_PLACE, address);
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
				if (!geoRequestor.equals(Config.FROM_PLACE)) {
					resetGeoHints();
				}
				geoRequestor = Config.FROM_PLACE;
				geoInput.setHint(getString(R.string.from_hint));
				geoInput.requestFocus();
			}

		});
		fromView.addTextChangedListener(new FireGeocode(Config.FROM_PLACE));
		fromView.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}
			
		});
		toView = (EditText) mainView.findViewById(R.id.toView);
		toView.addTextChangedListener(new FireGeocode(Config.TO_PLACE));
		toView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!geoRequestor.equals(Config.TO_PLACE)) {
					resetGeoHints();
				}
				switchToGeoHints();
				geoRequestor = Config.TO_PLACE;
				geoInput.setHint(getString(R.string.to_hint));
				geoInput.requestFocus();
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
					geoAdapter.clear();
					if(geocodeTask!=null){
						geocodeTask.cancel(true);
					}
					geocodeTask = new GeocodeTask(geoRequestor, geocoder,
							geoListener);
				} else {
					geocodeTask = null;
				}
			}

		});
		geoList = (ListView) mainView.findViewById(R.id.geoList);
		geoAdapter = new GeoArrayAdapter(activity, new Vector<Address>());
		geoList.setAdapter(geoAdapter);
		geoList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address address = (Address) parent.getItemAtPosition(position);
				geoInput.setText(GeoArrayAdapter.getShortName(address));
				optionsListener.locationChosen(address);
				targets.put(geoRequestor, address);
				if (geoRequestor.equals(Config.FROM_PLACE)) {
					fromView.setText(GeoArrayAdapter.getShortName(address));
				} else if (geoRequestor.equals(Config.TO_PLACE)) {
					toView.setText(GeoArrayAdapter.getShortName(address));
				}
				switchToMain();
				optionsListener.locationSelected(geoRequestor, address);
				geocodeTask.cancel(true);
			}

		});

		planButton = (Button) mainView.findViewById(R.id.plan);
		planButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Address from = targets.get(Config.FROM_PLACE);
				Address to = targets.get(Config.TO_PLACE);
				Hashtable<String, String> params = new Hashtable<String, String>();
				params.put(Config.ARRIVE_BY, "false");
				params.put(Config.DATE, new Date().toString());
				params.put(Config.FROM_PLACE,
						from.getLatitude() + "," + from.getLongitude());
				params.put(Config.TO_PLACE,
						to.getLatitude() + "," + to.getLongitude());
				params.put(Config.MAX_WALK_DISTANCE, walkDistanceView.getText()
						.toString());
				params.put(Config.OPTIMIZE, Config.DEFAULT_OPTIMIZE);
				params.put(Config.MODE, Config.DEFAULT_MODE);
				params.put(Config.TIME, timeDisplay.getText().toString());
				params.put(Config.WALK_SPEED, walkSpeedView.getText()
						.toString());
				RequestItineraryTask request = new RequestItineraryTask(activity, itineraryListener);
				System.out.println(Utils.contsructUrl(Config.OTP_API_URL,
						params));
				request.execute(Utils.contsructUrl(Config.OTP_API_URL, params));
			}
		});

		moreOptionsPanel = (View) mainView
				.findViewById(R.id.more_options_panel);
		moreOptionsPanel.setVisibility(View.GONE);

		moreOptionsToggle = (Button) mainView
				.findViewById(R.id.more_options_toggle);
		moreOptionsToggle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (moreOptionsPanel.isShown()) {
					moreOptionsPanel.setVisibility(View.GONE);
					moreOptionsToggle.setText(activity
							.getString(R.string.more_options));
				} else {
					moreOptionsPanel.setVisibility(View.VISIBLE);
					moreOptionsToggle.setText(activity
							.getString(R.string.hide_options));
					loadDefaultOptions();
				}
			}
		});

		timeOptions = (Spinner) mainView
				.findViewById(R.id.spinner_arrive_or_depart);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				activity, R.array.trip_plan_time_options,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timeOptions.setAdapter(adapter);
		timeOptions.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				if (pos == 0) {
					arriveBy = true;
				} else if (pos == 1) {
					arriveBy = false;
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {
				arriveBy = false;
			}
		});

		timeDisplay = (TextView) mainView
				.findViewById(R.id.time_arrive_or_depart);
		timeDisplay.setText(Utils.getShortTime(new Date()));

		timeDisplay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				showTimePickerDialog(view);
			}
		});

		walkSpeedView = (EditText) mainView.findViewById(R.id.walkSpeed);
		walkDistanceView = (EditText) mainView.findViewById(R.id.walkDistance);

	}

	public void showTimePickerDialog(View view) {
		DialogFragment timeFragment = new TimePickerFragment(this);
		timeFragment.show(getFragmentManager(), "timePicker");
	}

	public void switchToGeoHints() {
		optionsPanel.setVisibility(LinearLayout.GONE);
		geoHintsPanel.setVisibility(LinearLayout.VISIBLE);
	}

	public void switchToMain() {
		geoHintsPanel.setVisibility(LinearLayout.GONE);
		optionsPanel.setVisibility(LinearLayout.VISIBLE);
	}

	public void resetGeoHints() {
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
				geoAdapter.insertToTop(address);
			}
			Address address = result.get(0);
		}
	}

	public void geocode(String provider, String location) {
		geocodeTask.execute(location);
	}

	@Override
	public void loadItinerary(Response response) {
		System.out.println("Responsee: " + response);
		Error error = response.getError();
		if (error == null) {
			System.out.println(response);
			optionsListener.itineraryReceived(response.getPlan());
		} else {
			try{
				errorDialog.show();
			}catch(NullPointerException e){
				AlertDialog.Builder errorBuilder = new AlertDialog.Builder(activity);
				errorBuilder.setMessage(error.getMsg());
				errorBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   clearForm();
			        	   errorDialog.dismiss();
			           }
			       });
				errorDialog = errorBuilder.create();
				errorDialog.show();
			}
			optionsListener.itineraryNotPossible();
		}
	}

	
	@Override
	public void navButtonClicked() {
		switchToMain();
	}

	@Override
	public void planReceived(Plan plan) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fareReady(Fare fare, String legMode, View itineraryView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pnrTableRead(Hashtable<String, String> pnrTable, String end,
			View fareView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void myLocationReady(GeoPoint myLocationPoint) {
		setMyLocation(fromView, myLocationPoint);
		optionsListener.locationSelected(Config.FROM_PLACE,
				targets.get(Config.FROM_PLACE));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		timeDisplay.setText(Utils.getShortTime(hourOfDay, minute));
	}

	public void loadDefaultOptions() {
		arriveBy = false;
		timeDisplay.setText(Utils.getShortTime(new Date()));
		walkSpeedView.setText(Config.DEFAULT_WALK_SPEED + "");
		walkDistanceView.setText(Config.DEFAULT_MAX_WALK_DISTANCE + "");
	}
	
	public void clearForm(){
		fromView.setText("");
		toView.setText("");
		loadDefaultOptions();
	}

	@Override
	public void fareUnavailable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void failedToRetrieveItinerary() {
		// TODO Auto-generated method stub
		
	}
	
	public class FireGeocode implements TextWatcher{
		private String caller;
		public FireGeocode(String caller){
			this.caller = caller;
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			if (!geoRequestor.equals(caller)) {
				resetGeoHints();
			}
			switchToGeoHints();
			geoRequestor = caller;
			geoInput.setText(s);
			geoInput.setHint(getString(R.string.to_hint));
			geoInput.requestFocus();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
		}
		
	}

}