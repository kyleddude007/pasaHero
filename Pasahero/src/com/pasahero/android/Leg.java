package com.pasahero.android;

import java.util.Date;
import java.util.Vector;

import org.opentripplanner.util.model.EncodedPolylineBean;

public class Leg {

	private String mode;
	private String route;
	private String agencyUrl;
	private String agencyName;
	private String agencyId;
	private String routeColor;
	private String routeTextColor;
	private String routeShortName;
	private String routeLongName;
	private String boardRule;
	private String alightRule;
	private String rentedBike;
	private boolean bogusNonTransitLeg;
	private String routeId;
	private String interlineWithPreviousLeg;
	private String tripShortName;
	private String headsign;
	private String tripId;
	private String agencyTimeZoneOffset;
	private Date startTime;
	private Date endTime;
	private double distance;
	private Terminus from;
	private Terminus to;
	private EncodedPolylineBean legGeometry;
	private long duration;
	private Vector<Step> steps;
	private Vector<?> intermediateStops; 
	private Fare fare;
	
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
	public EncodedPolylineBean getLegGeometry() {
		return legGeometry;
	}
	public void setLegGeometry(EncodedPolylineBean legGeometry) {
		this.legGeometry = legGeometry;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getAgencyUrl() {
		return agencyUrl;
	}
	public void setAgencyUrl(String agencyUrl) {
		this.agencyUrl = agencyUrl;
	}
	public String getRouteColor() {
		return routeColor;
	}
	public void setRouteColor(String routeColor) {
		this.routeColor = routeColor;
	}
	public String getRouteTextColor() {
		return routeTextColor;
	}
	public void setRouteTextColor(String routeTextColor) {
		this.routeTextColor = routeTextColor;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public String getInterlineWithPreviousLeg() {
		return interlineWithPreviousLeg;
	}
	public void setInterlineWithPreviousLeg(String interlineWithPreviousLeg) {
		this.interlineWithPreviousLeg = interlineWithPreviousLeg;
	}
	public String getTripShortName() {
		return tripShortName;
	}
	public void setTripShortName(String tripShortName) {
		this.tripShortName = tripShortName;
	}
	public String getHeadsign() {
		return headsign;
	}
	public void setHeadsign(String headsign) {
		this.headsign = headsign;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public Vector<Step> getSteps() {
		return steps;
	}
	public void setSteps(Vector<Step> steps) {
		this.steps = steps;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	public String getRouteShortName() {
		return routeShortName;
	}
	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}
	public String getRouteLongName() {
		return routeLongName;
	}
	public void setRouteLongName(String routeLongName) {
		this.routeLongName = routeLongName;
	}
	public String getBoardRule() {
		return boardRule;
	}
	public void setBoardRule(String boardRule) {
		this.boardRule = boardRule;
	}
	public String getAlightRule() {
		return alightRule;
	}
	public void setAlightRule(String alightRule) {
		this.alightRule = alightRule;
	}
	public String getRentedBike() {
		return rentedBike;
	}
	public void setRentedBike(String rentedBike) {
		this.rentedBike = rentedBike;
	}
	public boolean isBogusNonTransitLeg() {
		return bogusNonTransitLeg;
	}
	public void setBogusNonTransitLeg(boolean bogusNonTransitLeg) {
		this.bogusNonTransitLeg = bogusNonTransitLeg;
	}
	public Vector<?> getIntermediateStops() {
		return intermediateStops;
	}
	public void setIntermediateStops(Vector<?> intermediateStops) {
		this.intermediateStops = intermediateStops;
	}
	public Fare getFare() {
		return fare;
	}
	public void setFare(Fare fare) {
		this.fare = fare;
	}
}
