package com.pasahero.android;

import android.graphics.Color;

public class Config {

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String API_URL = "http://12.10.0.114:8080/opentripplanner-api-webapp/ws/plan";
	public static final String SAMPLE_URL = "http://12.10.0.114:8080/opentripplanner-api-webapp/ws/plan?_dc=1378557587873&arriveBy=false&time=8%3A33pm&ui_date=9%2F7%2F2013&mode=TRANSIT%2CWALK&optimize=QUICK&maxWalkDistance=840&walkSpeed=1.341&date=2013-09-07&toPlace=14.559519%2C121.081975&fromPlace=14.589756%2C120.980351";
	public static final double NCR_LAT = 14.5833; // North
	public static final double NCR_LON = 121.0000; // East
	public static final int MAP_ZOOM = 11;
	public static final int MAX_GEO = 5;
	public static final int MAX_ADDRESS_LINE_LENGTH = 4; // This is a guess from
															// observation
	public static final String ARRIVE_BY = "arriveBy";
	public static final String DATE = "date";
	public static final String TIME = "time";
	public static final String MODE = "mode";
	public static final String OPTIMIZE = "optimize";
	public static final String MAX_WALK_DISTANCE = "maxWalkDistance";
	public static final String WALK_SPEED = "walkSpeed";
	public static final String TO_PLACE = "toPlace";
	public static final String FROM_PLACE = "fromPlace";
	public static final float ROUTE_STROKE_WIDTH = 3;
	public static final int ROUTE_DEFAULT_COLOR = Color.BLUE;
	public static final String MODE_WALK = "WALK";
	public static final String MODE_RAIL ="RAIL";
	public static final String MODE_TRANSIT = "TRANSIT";
	public static final String MODE_BUS ="BUS";
	public static String FONTAWESOME_URL = "fonts/fontawesome-webfont.ttf";
	public static long TEXT_CHANGED_WAIT = 1000;
	public static int TEXT_CHANGE_COUNT  = 4;
	public static String LOC_NAME_PATTERN = "<%location_name%>";
	public static String TERIMINAL_TITLE_START = "Start at "+LOC_NAME_PATTERN;
	public static String TERMINAL_TITLE_END = "End at "+LOC_NAME_PATTERN;
	public static String WALK_TITLE = "Start at "+LOC_NAME_PATTERN;
	public static String DIRECTION_PATTERN = "<%direction%>";
	public static String DISTANCE_PATTERN = "<%distance%>";
	public static String WALK_STEP_TEXT = "Walk "+DIRECTION_PATTERN+" on "+LOC_NAME_PATTERN+ " - "+DISTANCE_PATTERN;
	public static String TEMPLATE_BUS_TITLE = "Bus "+LOC_NAME_PATTERN;
	public static String TIME_PATTERN = "<%time%>";
	public static String TEMPLATE_DEPART = TIME_PATTERN+" Depart "+LOC_NAME_PATTERN;
	public static String TEMPLATE_ARRIVE = TIME_PATTERN+" Arrive "+LOC_NAME_PATTERN;
	public static String SERVICE_PATTERN = "<%service%>";
	public static String TEMPLATE_SERVICE_RUN_BY = "Service run by "+SERVICE_PATTERN;
	public static String SHORT_DATE_FORMAT = "H:mm a";
	public static String DURATION_READABLE_FORMAT ="%d minutes";
}
