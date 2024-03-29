package com.pasahero.android;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.opentripplanner.util.PolylineEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mapquest.android.maps.GeoPoint;

public class ItineraryFragment extends Fragment implements
		PasaHeroMapInterface, TripPlannerInterface {
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
	private int routeColorLength;
	private AlertDialog errorDialog;
	
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
		itinerary.setRouteColor(getRouteColor());
		for (Leg leg : legs) {
			String transitType = leg.getTransitType();
			System.out.println("Transit type: " + transitType);
			if (transitType.equals(Config.MODE_WALK)) {
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
				final Button showToggle = (Button) walkView
						.findViewById(R.id.show_toggle);
				showToggle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						if (stepsView.isShown()) {
							stepsView.setVisibility(View.GONE);
							showToggle.setText(resources.getString(R.string.hide_details));
						} else {
							stepsView.setVisibility(View.VISIBLE);
							showToggle.setText(resources.getString(R.string.show_details));
						}
					}

				});
				parent.addView(walkView);
			} else if (transitType.equals(Config.PUB_IDENTIFIER)) {
				View busView = activity.getLayoutInflater().inflate(
						R.layout.itinerary_transit, parent, false);
				ImageView busIcon = (ImageView) busView
						.findViewById(R.id.transit_icon);
				busIcon.setImageDrawable(resources.getDrawable(R.drawable.bus_blue));
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
				TextView busFare = (TextView) busView
						.findViewById(R.id.transit_fare);
				try {
					getFare(new URL(Config.PH_API_URL + "/"
							+ Config.PH_API_FARE + "/" + Config.BUS_AIRCON
							+ "/" + Utils.toKm(leg.getDistance())),
							transitType, busFare);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				try {
					getFare(new URL(Config.PH_API_URL + "/"
							+ Config.PH_API_FARE + "/" + Config.BUS_ORDINARY
							+ "/" + Utils.toKm(leg.getDistance())),
							transitType, busFare);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				parent.addView(busView);
			} else if (transitType.equals(Config.LRT1)
					|| transitType.equals(Config.LRT1_BR)
					|| transitType.equals(Config.LRT2)
					|| transitType.equals(Config.MRT3)) {
				View railView = activity.getLayoutInflater().inflate(
						R.layout.itinerary_transit, parent, false);
				ImageView railIcon = (ImageView) railView
						.findViewById(R.id.transit_icon);
				railIcon.setImageDrawable(resources
						.getDrawable(R.drawable.rail_blue));
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
				TextView railFare = (TextView) railView
						.findViewById(R.id.transit_fare);
				try {
					getFare(new URL(Config.PH_API_URL + "/"
							+ Config.PH_API_FARE + "/" + transitType + "/"
							+ leg.getInterstationDistance()), transitType,
							railFare);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			} else if (transitType.equals(Config.PUJ_IDENTIFIER)) {
				View jeepView = activity.getLayoutInflater().inflate(
						R.layout.itinerary_transit, parent, false);
				ImageView jeepIcon = (ImageView) jeepView
						.findViewById(R.id.transit_icon);
				jeepIcon.setImageDrawable(resources
						.getDrawable(R.drawable.jeep_blue));
				TextView jeepTitle = (TextView) jeepView
						.findViewById(R.id.transit_title);
				jeepTitle.setText(Utils.insertToTemplate(
						Config.TEMPLATE_JEEP_TITLE, Config.LOC_NAME_PATTERN,
						leg.getRouteLongName()));
				TextView jeepDepart = (TextView) jeepView
						.findViewById(R.id.transit_depart);
				Hashtable<String, String> departPairs = new Hashtable<String, String>();
				departPairs.put(Config.TIME_PATTERN,
						Utils.getShortTime(leg.getStartTime()));
				departPairs.put(Config.LOC_NAME_PATTERN, leg.getFrom()
						.getName());
				jeepDepart.setText(Utils.insertToTemplate(
						Config.TEMPLATE_DEPART, departPairs));
				TextView jeepArrive = (TextView) jeepView
						.findViewById(R.id.transit_arrive);
				Hashtable<String, String> arrivePairs = new Hashtable<String, String>();
				arrivePairs.put(Config.TIME_PATTERN,
						Utils.getShortTime(leg.getEndTime()));
				arrivePairs.put(Config.LOC_NAME_PATTERN, leg.getTo().getName());
				jeepArrive.setText(Utils.insertToTemplate(
						Config.TEMPLATE_ARRIVE, arrivePairs));
				TextView jeepDuration = (TextView) jeepView
						.findViewById(R.id.transit_duration);
				jeepDuration
						.setText(Utils.toDurationReadable(leg.getDuration()));
				TextView jeepService = (TextView) jeepView
						.findViewById(R.id.transit_service);
				jeepService.setText(Utils.insertToTemplate(
						Config.TEMPLATE_SERVICE_RUN_BY, Config.SERVICE_PATTERN,
						leg.getAgencyName()));
				parent.addView(jeepView);
				TextView jeepFare = (TextView) jeepView
						.findViewById(R.id.transit_fare);
				try {
					getFare(new URL(Config.PH_API_URL + "/"
							+ Config.PH_API_FARE + "/" + Config.PUJ + "/"
							+ Utils.toKm(leg.getDistance())), transitType,
							jeepFare);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			} else if (transitType.equals(Config.PNR_IDENTIFIER)) {
				View pnrView = activity.getLayoutInflater().inflate(
						R.layout.itinerary_transit, parent, false);
				ImageView pnrIcon = (ImageView) pnrView
						.findViewById(R.id.transit_icon);
				pnrIcon.setImageDrawable(resources.getDrawable(R.drawable.train_blue));
				TextView pnrTitle = (TextView) pnrView
						.findViewById(R.id.transit_title);
				pnrTitle.setText(Utils.insertToTemplate(
						Config.TEMPLATE_JEEP_TITLE, Config.LOC_NAME_PATTERN,
						leg.getRouteLongName()));
				TextView pnrDepart = (TextView) pnrView
						.findViewById(R.id.transit_depart);
				Hashtable<String, String> departPairs = new Hashtable<String, String>();
				departPairs.put(Config.TIME_PATTERN,
						Utils.getShortTime(leg.getStartTime()));
				departPairs.put(Config.LOC_NAME_PATTERN, leg.getFrom()
						.getName());
				pnrDepart.setText(Utils.insertToTemplate(
						Config.TEMPLATE_DEPART, departPairs));
				TextView pnrArrive = (TextView) pnrView
						.findViewById(R.id.transit_arrive);
				Hashtable<String, String> arrivePairs = new Hashtable<String, String>();
				arrivePairs.put(Config.TIME_PATTERN,
						Utils.getShortTime(leg.getEndTime()));
				arrivePairs.put(Config.LOC_NAME_PATTERN, leg.getTo().getName());
				pnrArrive.setText(Utils.insertToTemplate(
						Config.TEMPLATE_ARRIVE, arrivePairs));
				TextView pnrDuration = (TextView) pnrView
						.findViewById(R.id.transit_duration);
				pnrDuration
						.setText(Utils.toDurationReadable(leg.getDuration()));
				TextView pnrService = (TextView) pnrView
						.findViewById(R.id.transit_service);
				pnrService.setText(Utils.insertToTemplate(
						Config.TEMPLATE_SERVICE_RUN_BY, Config.SERVICE_PATTERN,
						leg.getAgencyName()));
				parent.addView(pnrView);
				TextView pnrFare = (TextView) pnrView
						.findViewById(R.id.transit_fare);
				getPNRTable(leg.getFrom().getName(), leg.getTo().getName(),
						pnrFare); 
			}
			List<com.vividsolutions.jts.geom.Coordinate> polyLines = PolylineEncoder
					.decode(leg.getLegGeometry());
			for (com.vividsolutions.jts.geom.Coordinate line : polyLines) {
				lineData.add(new GeoPoint(line.x, line.y));
			}
		}
		itineraryListener.lineDataReady(lineData, getRouteColor());
	}

	public void getFare(URL url, String legMode, View fareView) {
		System.out.println("fare url: " + url.toString());
		RequestFareTask fareTask = new RequestFareTask(this, legMode, fareView);
		fareTask.execute(url);
	}

	public void getPNRTable(String start, String end, View fareView) {
		try {
			URL url = new URL(Config.PH_API_URL + "/" + Config.PH_API_PNR_FARE
					+ "/" + start);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(),
					url.getHost(), url.getPort(), url.getPath(),
					url.getQuery(), url.getRef());
			url = uri.toURL();
			RequestPNRTableTask pnrTask = new RequestPNRTableTask(this, end,
					fareView);
			pnrTask.execute(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

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
	public void fareReady(Fare fare, String transitType, View fareView) {
		System.out.println("transit type: " + transitType);
		if (transitType.equals(Config.PUB_IDENTIFIER)
				|| transitType.equals(Config.PUJ_IDENTIFIER)) {
			// Hashtable<String, String> patterns = new Hashtable<String,
			// String>();
			// patterns.put(Config.FARE_PATTERN, fare.getRegular()+"");
			// patterns.put(Config.FARE_DISCOUNT_PATTERN,
			// fare.getDiscounted()+"");
			// String fareText =
			// Utils.insertToTemplate(Config.TEMPLATE_BUS_AIRCON_FARE,
			// patterns)+"\n"+Utils.insertToTemplate(Config.TEMPLATE_BUS_ORDINARY_FARE,
			// patterns);
			String fareText = ((TextView) fareView).getText().toString();
			fareText = fareText + "\n" + fare.getRegular() + "\n"
					+ fare.getDiscounted();
			((TextView) fareView).setText(fareText);
		} else if (transitType.equals(Config.LRT1)
				|| transitType.equals(Config.LRT1_BR)
				|| transitType.equals(Config.LRT2)
				|| transitType.equals(Config.MRT3)) {
			((TextView) fareView).setText(fare.getSingleJourney() + "\n"
					+ fare.getStoredValue());
		}
		System.out.println("----");
		System.out.println(fare.getType());
		System.out.println(fare.getDiscounted());
		System.out.println(fare.getRegular());
		System.out.println(fare.getSingleJourney());
		System.out.println(fare.getStoredValue());
	}

	@Override
	public void loadItinerary(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pnrTableRead(Hashtable<String, String> pnrTable, String end,
			View fareView) {
		System.out.println("pnr start val: "
				+ pnrTable.get(Config.PNR_TABLE_START_KEY));
		System.out.println("pnr end key: " + end);
		System.out.println("pnr end val: " + pnrTable.get(end));
	}

	@Override
	public void myLocationReady(GeoPoint myLocationPoint) {

	}

	@Override
	public void fareUnavailable() {
		System.out.println("Fare unvailable");
	}

	@Override
	public void failedToRetrieveItinerary() {
		try{
			errorDialog.show();
		}catch(NullPointerException e){
			AlertDialog.Builder errorBuilder = new AlertDialog.Builder(activity);
			errorBuilder.setMessage(Config.HTTP_CONNECT_FAILED);
			errorBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   errorDialog.dismiss();
		           }
		       });
			errorDialog = errorBuilder.create();
			errorDialog.show();
		}
	}

}
