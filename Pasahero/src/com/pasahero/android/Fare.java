package com.pasahero.android;

import org.codehaus.jackson.annotate.JsonProperty;

public class Fare {

	private String id;
	private double discounted;
	private int distance;
	private String distanceUnit;
	private double regular;
	private double singleJourney;
	private double storedValue;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	@JsonProperty("distance_unit")
	public String getDistanceUnit() {
		return distanceUnit;
	}

	@JsonProperty("distance_unit")
	public void setDistanceUnit(String distanceUnit) {
		this.distanceUnit = distanceUnit;
	}

	public double getRegular() {
		return regular;
	}

	public void setRegular(double regular) {
		this.regular = regular;
	}

	@JsonProperty("single_journey")
	public double getSingleJourney() {
		return singleJourney;
	}

	@JsonProperty("single_journey")
	public void setSingleJourney(double singleJourney) {
		this.singleJourney = singleJourney;
	}

	@JsonProperty("stored_value")
	public double getStoredValue() {
		return storedValue;
	}

	@JsonProperty("stored_value")
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
