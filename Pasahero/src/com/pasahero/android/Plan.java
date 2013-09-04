package com.pasahero.android;

import java.util.Date;
import java.util.Vector;

import org.codehaus.jackson.annotate.JsonProperty;

public class Plan {

	private Date date;
	private Terminus from;
	private Terminus to;
	private Vector<Itinerary> itineraries;

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public Vector<Itinerary> getItineraries() {
		return itineraries;
	}
	public void setItineraries(Vector<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}
	
	@Override
	public String toString(){
		return "From : "+from+" To : "+to;
	}
}
