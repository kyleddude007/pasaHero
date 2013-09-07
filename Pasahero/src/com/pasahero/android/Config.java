package com.pasahero.android;

public class Config {

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String API_URL="http://192.168.0.102:8080/opentripplanner-api-webapp/ws/plan";
	public static final String SAMPLE_URL = "http://192.168.0.102:8080/opentripplanner-api-webapp/ws/plan?_dc=1378035374631&arriveBy=false&time=7%3A35pm&ui_date=9%2F1%2F2013&mode=TRANSIT%2CWALK&optimize=QUICK&maxWalkDistance=840&walkSpeed=1.341&date=2013-09-01&toPlace=14.569218%2C121.066418&fromPlace=14.566829%2C121.059294";
	public static final double NCR_LAT = 14.5833; //North
	public static final double NCR_LON = 121.0000; //East
	public static final int MAX_GEO = 5;
	public static final int MAX_ADDRESS_LINE_LENGTH = 4; //This is a guess from observation
	public static final String ARRIVE_BY = "arriveBy";
	public static final String DATE = "date";
	public static final String TIME = "time";
	public static final String MODE = "mode";
	public static final String OPTIMIZE = "optimize";
	public static final String MAX_WALK_DISTANCE = "maxWalkDistance";
	public static final String WALK_SPEED = "walkSpeed";
	public static final String TO_PLACE = "toPlace";
	public static final String FROM_PLACE = "fromPlace";
}
