package com.pasahero.android;

public class WalkSteps {

	private double distance;
	private String streetName;
	private String absoluteDirection;
	private boolean stayOn;
	private double longitude;
	private double latitude;
	private double elevation;
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
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
	public boolean isStayOn() {
		return stayOn;
	}
	public void setStayOn(boolean stayOn) {
		this.stayOn = stayOn;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getElevation() {
		return elevation;
	}
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
}
