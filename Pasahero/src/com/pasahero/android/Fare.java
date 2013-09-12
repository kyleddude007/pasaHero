package com.pasahero.android;

public class Fare {

	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private double discounted;
	private int distance;
	private String distanceUnit;
	private double regular;
	private double singleJourney;
	private double storedValue;
	private String type;
	public double getDiscounted() {
		return discounted;
	}
	public void setDiscounted(double discounted) {
		this.discounted = discounted;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getDistanceUnit() {
		return distanceUnit;
	}
	public void setDistanceUnit(String distanceUnit) {
		this.distanceUnit = distanceUnit;
	}
	public double getRegular() {
		return regular;
	}
	public void setRegular(double regular) {
		this.regular = regular;
	}
	public double getSingleJourney() {
		return singleJourney;
	}
	public void setSingleJourney(double singleJourney) {
		this.singleJourney = singleJourney;
	}
	public double getStoredValue() {
		return storedValue;
	}
	public void setStoredValue(double storedValue) {
		this.storedValue = storedValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
