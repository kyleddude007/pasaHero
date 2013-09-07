package com.pasahero.android;

import java.util.Hashtable;

/**
 * Represents the discrete source and destination geographical region
 * 
 * @author cassiewang
 * 
 */
public class Terminus {

	private String name;
	private double lon;
	private double lat;
	private String arrival;
	private String departure;
	private String orig;
	private String zoneId;
	private Geometry geometry;
	private String stopCode;
	private Hashtable<String, String> stopId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLongitude() {
		return lon;
	}

	public void setLatitude(double lat) {
		this.lat = lat;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getStopCode() {
		return stopCode;
	}

	public void setStopCode(String stopCode) {
		this.stopCode = stopCode;
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

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getOrig() {
		return orig;
	}

	public void setOrig(String orig) {
		this.orig = orig;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public Hashtable<String, String> getStopId() {
		return stopId;
	}

	public void setStopId(Hashtable<String, String> stopId) {
		this.stopId = stopId;
	}
	
	@Override
	public String toString(){
		return name+" : [ "+lon+" , "+lat+" ]";
	}
}
