package com.pasahero.android;

import java.util.Date;

public class Leg {

	private String mode;
	private String route;
	private String agencyTimeZoneOffset;
	private Date startTime;
	private Date endTime;
	private double distance;
	private Terminus from;
	private Terminus to;
	private LegGeometry legGeometry;
	private long duration;
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getAgencyTimeZoneOffset() {
		return agencyTimeZoneOffset;
	}
	public void setAgencyTimeZoneOffset(String agencyTimeZoneOffset) {
		this.agencyTimeZoneOffset = agencyTimeZoneOffset;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public Terminus getFrom() {
		return from;
	}
	public void setFrom(Terminus from) {
		this.from = from;
	}
	public Terminus getTo() {
		return to;
	}
	public void setTo(Terminus to) {
		this.to = to;
	}
	public LegGeometry getLegGeometry() {
		return legGeometry;
	}
	public void setLegGeometry(LegGeometry legGeometry) {
		this.legGeometry = legGeometry;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
}
