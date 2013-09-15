package com.pasahero.android;

import android.graphics.Color;

public class Config {

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PH_API_URL = "http://192.168.0.104:15000/ps/api";
	public static final String OTP_API_URL = "http://ec2-54-200-32-77.us-west-2.compute.amazonaws.com/opentripplanner-api-webapp/ws/plan";
	public static final String SAMPLE_URL = "http://ec2-54-200-32-77.us-west-2.compute.amazonaws.com/opentripplanner-api-webapp/ws/plan?_dc=1378557587873&arriveBy=false&time=8%3A33pm&ui_date=9%2F7%2F2013&mode=TRANSIT%2CWALK&optimize=QUICK&maxWalkDistance=840&walkSpeed=1.341&date=2013-09-07&toPlace=14.559519%2C121.081975&fromPlace=14.589756%2C120.980351";
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
	public static final int DEFAULT_MAX_WALK_DISTANCE =840;
	public static final double DEFAULT_WALK_SPEED = 1.341;
	public static final String TO_PLACE = "toPlace";
	public static final String FROM_PLACE = "fromPlace";
	public static final float ROUTE_STROKE_WIDTH = 3;
	public static final int ROUTE_DEFAULT_COLOR = Color.BLUE;
	public static final String MODE_WALK = "WALK";
	public static final String MODE_RAIL = "RAIL";
	public static final String MODE_TRANSIT = "TRANSIT";
	public static final String MODE_BUS = "BUS";
	public static final String FONTAWESOME_URL = "fonts/fontawesome-webfont.ttf";
	public static final long TEXT_CHANGED_WAIT = 1000;
	public static final int TEXT_CHANGE_COUNT = 4;
	public static final String LOC_NAME_PATTERN = "<%location_name%>";
	public static final String TERIMINAL_TITLE_START = "Start at " + LOC_NAME_PATTERN;
	public static final String TERMINAL_TITLE_END = "End at " + LOC_NAME_PATTERN;
	public static final String WALK_TITLE = "Walk to " + LOC_NAME_PATTERN;
	public static final String DIRECTION_PATTERN = "<%direction%>";
	public static final String DISTANCE_PATTERN = "<%distance%>";
	public static final String TEMPLATE_BUS_TITLE = "Bus " + LOC_NAME_PATTERN;
	public static final String TIME_PATTERN = "<%time%>";
	public static final String TEMPLATE_DEPART = TIME_PATTERN + " Depart "
			+ LOC_NAME_PATTERN;
	public static final String TEMPLATE_ARRIVE = TIME_PATTERN + " Arrive "
			+ LOC_NAME_PATTERN;
	public static final String SERVICE_PATTERN = "<%service%>";
	public static final String TEMPLATE_SERVICE_RUN_BY = "Service run by "
			+ SERVICE_PATTERN;
	public static final String SHORT_DATE_FORMAT = "H:mm a";
	public static final String DURATION_READABLE_FORMAT = "%d minutes";
	public static final String TEMPLATE_RAIL_TITLE = "Rail " + LOC_NAME_PATTERN;
	public static final String DISTANCE_FORMAT = "#";
	public static final String DISTANCE_UNIT = "meters";
	public static final String WALK_STEP_TEXT = "Walk " + DIRECTION_PATTERN + " on "
			+ LOC_NAME_PATTERN + " - " + DISTANCE_PATTERN + " " + DISTANCE_UNIT;
	public static final String LRT1 = "LRT1";
	public static final String LRT1_BR = "LRT1-BR";
	public static final String MRT3 = "MRT3";
	public static final String LRT2 = "LRT2";
	public static final String PUJ = "Jeep";
	public static final String BUS_AIRCON = "Aircon";
	public static final String BUS_ORDINARY = "Ordinary";
	public static final String FARE_PATTERN = "<%fare%>";
	public static final String FARE_DISCOUNT_PATTERN = "<%discount%>";
	public static final String TEMPLATE_DISCOUNT = "Student/Elderly/Disabled: "
			+ FARE_DISCOUNT_PATTERN;
	public static final String TEMPLATE_REGULAR = "Fare: " + FARE_PATTERN;
	public static final String TEMPLATE_BUS_ORDINARY_FARE = "ORDINARY: \n"
			+ TEMPLATE_REGULAR + "\n" + TEMPLATE_DISCOUNT;
	public static final String TEMPLATE_BUS_AIRCON_FARE = "AIR-CONDITIONED: \n"
			+ TEMPLATE_REGULAR + "\n" + TEMPLATE_DISCOUNT;
	public static final String TEMPLATE_LRT = "LRT: " + FARE_PATTERN;
	public static final String TEMPLATE_MRT = "MRT: " + FARE_PATTERN;
	public static final String PH_API_FARE = "fares";
	public static final String PUJ_IDENTIFIER = "PUJ";
	public static final String PUB_IDENTIFIER = "PUB";
	public static final String TEMPLATE_JEEP_TITLE = "Jeep " + LOC_NAME_PATTERN;
	public static final int HTTP_OK = 200;
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final int PREF_PRIVATE_MODE = 0;
	public static final String PREF_NAME = "PasaHeroPref";
    public static final String IS_LOGGED_IN = "IsLoggedIn";
    public static final String NAME = "name";
    public static final String LRT1_IDENTIFIER = "LRT 1";
    public static final String LRT2_IDENTIFER = "LRT 2";
    public static final String MRT3_IDENTIFER = "MRT-3";
    public static final String BALINTAWAK_IDENT = "LRT Balintawak";
    public static final String ROOSEVELT_IDENT = "Roosevelt LRT";
    public static final String NORTH_AVE_MRT = "North Avenue MRT";
    public static final double NCR_UPPER_LEFT_LAT = 14.742897;
    public static final double NCR_UPPER_LEFT_LON = 120.886345;
    public static final double NCR_UPPER_RIGHT_LAT = 14.782621;
    public static final double NCR_UPPER_RIGHT_LON = 121.193619;
    public static final double NCR_LOWER_LEFT_LAT = 14.344112;
    public static final double NCR_LOWER_LEFT_LON = 120.882225;
    public static final double NCR_LOWER_RIGHT_LAT =14.355971;
    public static final double NCR_LOWER_RIGHT_LON = 121.160660;
    public static final String PNR_IDENTIFIER = "PNR";
    public static final String PH_API_PNR_FARE = "pnr";
    public static final String PNR_TABLE_START_KEY = "start";
    public static final String MY_LOCATION_STRING = "My Location";
    public static final String DEFAULT_MODE = "TRANSIT,WALK";
    public static final String DEFAULT_OPTIMIZE = "QUICK";
    public static final String IS_LOGGED_OUT = "IsLoggedOut";
    public static final String HTTP_CONNECT_FAILED = "Could not connect to the server.  Try to reset your connection";

}
