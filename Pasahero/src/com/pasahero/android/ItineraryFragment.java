package com.pasahero.android;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.opentripplanner.util.PolylineEncoder;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mapquest.android.maps.GeoPoint;

public class ItineraryFragment extends Fragment implements PasaHeroMapInterface, TripPlannerInterface {
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
	private ItineraryFragmentInterface itineraryListener;
	private ListView itineraryList;
	private ItineraryAdapter itineraryAdapter;
	private Button moreItinerariesToggle;
	private TypedArray routeColors;
	private int routeColorIndex;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		fontawesome = Typefaces.get(activity, Config.FONTAWESOME_URL);
		resources = activity.getResources();
		initRouteColors();
		setUpIteneraryToggle();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.fragment_itinerary, container,
				false);
		parent = (ViewGroup) mainView.findViewById(R.id.itinerary_pane);
		return mainView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof ItineraryFragmentInterface) {
			itineraryListener = (ItineraryFragmentInterface) activity;
			this.activity = activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet ItineraryFragmentInterface");
		}
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
		setItineraryView(itineraries.get(0));
		setUpItineraryListView();
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

	public void setItineraryView(Itinerary itinerary) {
		parent.removeAllViews();
		Vector<Leg> legs = itinerary.getLegs();
		Vector<GeoPoint> lineData = new Vector<GeoPoint>();
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
				Button showToggle = (Button) walkView
						.findViewById(R.id.show_toggle);
				showToggle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						if (stepsView.isShown()) {
							stepsView.setVisibility(View.GONE);
						} else {
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
				TextView busFare = (TextView) busView.findViewById(R.id.transit_fare);
				try {
					getFare(new URL(Config.PH_API_URL+"/"+Config.PH_API_FARE+"/"+Config.BUS_AIRCON+"/"+Utils.toKm(leg.getDistance())), mode, busFare);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
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
				TextView railFare = (TextView) railView.findViewById(R.id.transit_fare);
				try {
					getFare(new URL(Config.PH_API_URL+"/"+Config.PH_API_FARE+"/"+Config.BUS_AIRCON+"/"+Utils.toKm(leg.getDistance())), mode, railFare);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			List<com.vividsolutions.jts.geom.Coordinate> polyLines = PolylineEncoder
					.decode(leg.getLegGeometry());
			for (com.vividsolutions.jts.geom.Coordinate line : polyLines) {
				lineData.add(new GeoPoint(line.x, line.y));
			}
		}
		itineraryListener.lineDataReady(lineData, getRouteColor());

	}
	
	public void getFare(URL url, String legMode, View fareView){
		System.out.println("url: "+url.toString());
		RequestFareTask fareTask = new RequestFareTask(this,legMode, fareView);
		fareTask.execute(url);
	}

	public void setUpItineraryListView() {
		itineraryList = (ListView) mainView.findViewById(R.id.itinerary_list);
		itineraryAdapter = new ItineraryAdapter(activity, itineraries);
		itineraryList.setAdapter(itineraryAdapter);
		itineraryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				setItineraryView(itineraries.get(position));
			}
		});
		itineraryList.setVisibility(View.GONE);
	}

	public void setUpIteneraryToggle() {
		moreItinerariesToggle = (Button) mainView
				.findViewById(R.id.more_itineraries_toggle);
		moreItinerariesToggle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (itineraryList.isShown()) {
					itineraryList.setVisibility(View.GONE);
				} else {
					itineraryList.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	public void initRouteColors() {
		routeColors = resources.obtainTypedArray(R.array.route_colors);
		routeColorIndex = 0;
	}

	public int getRouteColor() {
		try {
			int color = routeColors.getColor(routeColorIndex,
					Config.ROUTE_DEFAULT_COLOR);
			routeColorIndex++;
			return color;
		} catch (ArrayIndexOutOfBoundsException e) {
			routeColorIndex = 0;
			int color = routeColors.getColor(routeColorIndex,
					Config.ROUTE_DEFAULT_COLOR);
			routeColorIndex++;
			return color;
		}

	}

	@Override
	public void fareReady(Fare fare, String legMode, View fareView) {
		if(legMode.equals(Config.MODE_BUS)){
			//Hashtable<String, String> patterns = new Hashtable<String, String>();
			//patterns.put(Config.FARE_PATTERN, fare.getRegular()+"");
			//patterns.put(Config.FARE_DISCOUNT_PATTERN, fare.getDiscounted()+"");
			//String fareText = Utils.insertToTemplate(Config.TEMPLATE_BUS_AIRCON_FARE, patterns)+"\n"+Utils.insertToTemplate(Config.TEMPLATE_BUS_ORDINARY_FARE, patterns);
			String fareText = fare.getRegular()+"\n"+fare.getDiscounted();
			((TextView)fareView).setText(fareText);
		}else if(legMode.equals(Config.MODE_RAIL)){
			((TextView)fareView).setText(fare.getSingleJourney()+"\n"+fare.getStoredValue());
		}
	}

	@Override
	public void loadItinerary(Response response) {
		// TODO Auto-generated method stub
		
	}

}
