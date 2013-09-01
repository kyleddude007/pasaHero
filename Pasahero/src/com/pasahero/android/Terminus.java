package com.pasahero.android;

/**
 * Represents the discrete source and destination geographical region
 * @author cassiewang
 *
 */
public class Terminus {

	private String name;
	private String longitude;
	private String latitude;
	private Geometry geometry;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
}
