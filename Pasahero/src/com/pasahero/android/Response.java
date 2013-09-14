package com.pasahero.android;

import java.util.Hashtable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Response {

	private Plan plan;
	private Error error;
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
		return "[requestParameters: "+requestParameters.toString();
	}
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	@JsonProperty("error")
	public void setError(Error error) {
		this.error = error;
	}
}

