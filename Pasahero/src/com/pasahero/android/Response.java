package com.pasahero.android;

import java.util.Hashtable;
import java.util.List;

public class Response {

	private Plan plan;
	private Hashtable<String, String> error;
	private RequestParameters requestParameters;
	private List<Fare> fares;
	public List<Fare> getFares() {
		return fares;
	}
	public void setFares(List<Fare> fares) {
		this.fares = fares;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public RequestParameters getRequestParameters() {
		return requestParameters;
	}
	public void setRequestParameters(RequestParameters requestParameters) {
		this.requestParameters = requestParameters;
	}
	
	@Override
	public String toString(){
		return plan.toString();
	}
	public Hashtable<String, String> getError() {
		return error;
	}
	public void setError(Hashtable<String, String> error) {
		this.error = error;
	}
}

