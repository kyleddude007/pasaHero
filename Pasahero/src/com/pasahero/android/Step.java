package com.pasahero.android;

import java.util.Hashtable;

public class Step {

	private double distance;
	private String relativeDirection;
	private String streetName;
	private String absoluteDirection;
	private String exit;
	private boolean stayOn;
	private String bogusName;
	private double lon;
	private double lat;
	private String elevation;

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getRelativeDirection() {
		return relativeDirection;
	}

	public void setRelativeDirection(String relativeDirection) {
		this.relativeDirection = relativeDirection;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getAbsoluteDirection() {
		return absoluteDirection;
	}

	public void setAbsoluteDirection(String absoluteDirection) {
		this.absoluteDirection = absoluteDirection;
	}

	public String getExit() {
		return exit;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

	public boolean isStayOn() {
		return stayOn;
	}

	public void setStayOn(boolean stayOn) {
		this.stayOn = stayOn;
	}

	public String getBogusName() {
		return bogusName;
	}

	public void setBogusName(String bogusName) {
		this.bogusName = bogusName;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getElevation() {
		return elevation;
	}

	public void setElevation(String elevation) {
		this.elevation = elevation;
	}

	@Override
	public String toString() {
		Hashtable<String, String> patternPairs = new Hashtable<String, String>();
		patternPairs.put(Config.DIRECTION_PATTERN, (String) Utils
				.assignIfNotNull(relativeDirection, absoluteDirection));
		patternPairs.put(Config.DISTANCE_PATTERN, distance + "");
		patternPairs.put(Config.LOC_NAME_PATTERN, streetName);
		return Utils.insertToTemplate(Config.WALK_STEP_TEXT, patternPairs);
	}

}
