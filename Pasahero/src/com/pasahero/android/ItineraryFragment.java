package com.pasahero.android;

import java.util.Hashtable;
import java.util.Vector;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ItineraryFragment extends Fragment implements PasaHeroMapInterface {
	private Vector<Itinerary> itineraries;
	private View start;
	private View end;
	private View bus;
	private View rail;
	private View walk;
	private ListView walkStepsView;
	private Activity activity;
	private Resources resources;
	private Plan plan;
	private Typeface fontawesome;
	private View mainView;
	private ViewGroup parent;
	private View itineraryPane;
	private StepsAdapter stepsAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		fontawesome = Typefaces.get(activity, Config.FONTAWESOME_URL);
		resources = activity.getResources();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.fragment_itinerary, container,
				false);
		parent = (ViewGroup) mainView.findViewById(R.id.itinerary_pane);
		System.out.print("THE PAREENTNTWT ");
		System.out.println(parent);
		return mainView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;

	}

	@Override
	public void onDetach() {
		super.onDetach();

	}

	@Override
	public void navButtonClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void planReceived(Plan plan) {
		this.plan = plan;
		itineraries = this.plan.getItineraries();
		setUpTerminals(mainView, plan);
		itineraryPane = (View) mainView.findViewById(R.id.itinerary_pane);
		Itinerary it = itineraries.get(0);
		Vector<Leg> legs = it.getLegs();
		for (Leg leg : legs) {
			String mode = leg.getMode();
			if (mode.equals(Config.MODE_WALK)) {
				View walkView = activity.getLayoutInflater().inflate(
						R.layout.itinerary_walk, parent, false);
				TextView walkText = (TextView) walkView
						.findViewById(R.id.walk_title);
				walkText.setText(Utils.insertToTemplate(Config.WALK_TITLE,
						Config.LOC_NAME_PATTERN, leg.getTo().getName()));
				Vector<Step> steps = leg.getSteps();
				final ListView stepsView = (ListView) walkView
						.findViewById(R.id.walk_steps);
				stepsAdapter = new StepsAdapter(activity, steps);
				stepsView.setAdapter(stepsAdapter);
				stepsView.setVisibility(View.GONE);
				Button showToggle = (Button) walkView.findViewById(R.id.show_toggle);
				showToggle.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View view) {
						if(stepsView.isShown()){
							stepsView.setVisibility(View.GONE);
						}else{
							stepsView.setVisibility(View.VISIBLE);
						}
					}
					
				});
				parent.addView(walkView);
			} else if (mode.equals(Config.MODE_BUS)) {
				View busView = activity.getLayoutInflater().inflate(
						R.layout.itinerary_transit, parent, false);
				TextView busTitle = (TextView) busView
						.findViewById(R.id.transit_title);
				busTitle.setText(Utils.insertToTemplate(
						Config.TEMPLATE_BUS_TITLE, Config.LOC_NAME_PATTERN,
						leg.getRouteLongName()));
				TextView busDepart = (TextView) busView
						.findViewById(R.id.transit_depart);
				Hashtable<String, String> departPairs = new Hashtable<String, String>();
				departPairs.put(Config.TIME_PATTERN,
						Utils.getShortTime(leg.getStartTime()));
				departPairs.put(Config.LOC_NAME_PATTERN, leg.getFrom()
						.getName());
				busDepart.setText(Utils.insertToTemplate(
						Config.TEMPLATE_DEPART, departPairs));
				TextView busArrive = (TextView) busView
						.findViewById(R.id.transit_arrive);
				Hashtable<String, String> arrivePairs = new Hashtable<String, String>();
				arrivePairs.put(Config.TIME_PATTERN,
						Utils.getShortTime(leg.getEndTime()));
				arrivePairs.put(Config.LOC_NAME_PATTERN, leg.getTo().getName());
				busArrive.setText(Utils.insertToTemplate(
						Config.TEMPLATE_ARRIVE, arrivePairs));
				TextView busDuration = (TextView) busView
						.findViewById(R.id.transit_duration);
				busDuration
						.setText(Utils.toDurationReadable(leg.getDuration()));
				TextView busService = (TextView) busView
						.findViewById(R.id.transit_service);
				busService.setText(Utils.insertToTemplate(
						Config.TEMPLATE_SERVICE_RUN_BY, Config.SERVICE_PATTERN,
						leg.getAgencyName()));
				parent.addView(busView);
			} else if (mode.equals(Config.MODE_RAIL)) {
				View railView = activity.getLayoutInflater().inflate(
						R.layout.itinerary_transit, parent, false);
				TextView railTitle = (TextView) railView
						.findViewById(R.id.transit_title);
				railTitle.setText(Utils.insertToTemplate(
						Config.TEMPLATE_RAIL_TITLE, Config.LOC_NAME_PATTERN,
						leg.getRouteLongName()));
				TextView railDepart = (TextView) railView
						.findViewById(R.id.transit_depart);
				Hashtable<String, String> departPairs = new Hashtable<String, String>();
				departPairs.put(Config.TIME_PATTERN,
						Utils.getShortTime(leg.getStartTime()));
				departPairs.put(Config.LOC_NAME_PATTERN, leg.getFrom()
						.getName());
				railDepart.setText(Utils.insertToTemplate(
						Config.TEMPLATE_DEPART, departPairs));
				TextView railArrive = (TextView) railView
						.findViewById(R.id.transit_arrive);
				Hashtable<String, String> arrivePairs = new Hashtable<String, String>();
				arrivePairs.put(Config.TIME_PATTERN,
						Utils.getShortTime(leg.getEndTime()));
				arrivePairs.put(Config.LOC_NAME_PATTERN, leg.getTo().getName());
				railArrive.setText(Utils.insertToTemplate(
						Config.TEMPLATE_ARRIVE, arrivePairs));
				TextView railDuration = (TextView) railView
						.findViewById(R.id.transit_duration);
				railDuration
						.setText(Utils.toDurationReadable(leg.getDuration()));
				TextView railService = (TextView) railView
						.findViewById(R.id.transit_service);
				railService.setText(Utils.insertToTemplate(
						Config.TEMPLATE_SERVICE_RUN_BY, Config.SERVICE_PATTERN,
						leg.getAgencyName()));
				parent.addView(railView);
			}
		}

	}

	public void setUpTerminals(View view, Plan plan) {
		start = (View) view.findViewById(R.id.terminal_start);
		end = (View) view.findViewById(R.id.terminal_end);

		TextView startText = (TextView) view.findViewById(R.id.start_text);
		TextView endText = (TextView) view.findViewById(R.id.end_text);

		startText.setText(Utils.insertToTemplate(Config.TERIMINAL_TITLE_START,
				Config.LOC_NAME_PATTERN, plan.getFrom().getName()));
		endText.setText(Utils.insertToTemplate(Config.TERMINAL_TITLE_END,
				Config.LOC_NAME_PATTERN, plan.getTo().getName()));
	}

	public void setUpItineraryView(View view, Itinerary it) {

	}

}
