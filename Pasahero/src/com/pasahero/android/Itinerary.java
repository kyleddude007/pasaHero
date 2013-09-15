package com.pasahero.android;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.mapquest.android.maps.GeoPoint;

public class Itinerary {

	private long duration;
	private Date startTime;
	private Date endTime;
	private long walkTime;
	private long transitTime;
	private long waitingTime;
	private double walkDistance; 
	private double elevationLost;
	private double elevationGained;
	private int transfers;
	private double fare;
	private Vector<Leg> legs;
	private boolean tooSloped;
	private int routeColor;
	private List<GeoPoint> lineDataDecode;
	
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
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
	public long getWalkTime() {
		return walkTime;
	}
	public void setWalkTime(long walkTime) {
		this.walkTime = walkTime;
	}
	public long getTransitTime() {
		return transitTime;
	}
	public void setTransitTime(long transitTime) {
		this.transitTime = transitTime;
	}
	public long getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
	}
	public double getWalkDistance() {
		return walkDistance;
	}
	public void setWalkDistance(double walkDistance) {
		this.walkDistance = walkDistance;
	}
	public double getElevationLost() {
		return elevationLost;
	}
	public void setElevationLost(long elevationLost) {
		this.elevationLost = elevationLost;
	}
	public double getElevationGained() {
		return elevationGained;
	}
	public void setElevationGained(long elevationGained) {
		this.elevationGained = elevationGained;
	}
	public int getTransfers() {
		return transfers;
	}
	public void setTransfers(int transfers) {
		this.transfers = transfers;
	}
	public Vector<Leg> getLegs() {
		return legs;
	}
	public void setLegs(Vector<Leg> legs) {
		this.legs = legs;
	}
	public boolean isTooSloped() {
		return tooSloped;
	}
	public void setTooSloped(boolean tooSloped) {
		this.tooSloped = tooSloped;
	}
	public int getRouteColor() {
		return routeColor;
	}
	public void setRouteColor(int routeColor) {
		this.routeColor = routeColor;
	}
	public List<GeoPoint> getLineDataDecode() {
		return lineDataDecode;
	}
	public void setLineDataDecode(List<GeoPoint> lineDataDecode) {
		this.lineDataDecode = lineDataDecode;
	}


}
