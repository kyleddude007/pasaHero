package com.pasahero.android;

import java.util.Vector;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ItineraryFragment extends Fragment implements PasaHeroMapInterface
{
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
		
	}
	
	public void setUpTerminals(View view, Plan plan){
		start = (View) view.findViewById(R.id.terminal_start);
		end = (View) view.findViewById(R.id.terminal_end);
		
		TextView startText = (TextView) view.findViewById(R.id.start_text);
		TextView endText = (TextView) view.findViewById(R.id.end_text);

		startText.setText(Utils.insertToTemplate(Config.TERIMINAL_TITLE_START, Config.LOC_NAME_PATTERN, plan.getFrom().getName()));
		endText.setText(Utils.insertToTemplate(Config.TERMINAL_TITLE_END, Config.LOC_NAME_PATTERN, plan.getTo().getName()));
	}
	
	public void setUpItineraryView(View view, Itinerary it){
		
		
	}
	
}
