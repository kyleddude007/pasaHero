package com.pasahero.android;

public class Config {

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String API_URL="http://192.168.0.102:8080/";
	public static final String SAMPLE_URL = "http://192.168.0.102:8080/opentripplanner-api-webapp/ws/plan?_dc=1378035374631&arriveBy=false&time=7%3A35pm&ui_date=9%2F1%2F2013&mode=TRANSIT%2CWALK&optimize=QUICK&maxWalkDistance=840&walkSpeed=1.341&date=2013-09-01&toPlace=14.569218%2C121.066418&fromPlace=14.566829%2C121.059294";
	public static final double NCR_LAT = 14.5833; //North
	public static final double NCR_LON = 121.0000; //East
	public static final int MAX_GEO = 5;
	public static final int MAX_ADDRESS_LINE_LENGTH = 4; //This is a guess from observation
}
