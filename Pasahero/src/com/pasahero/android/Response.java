package com.pasahero.android;

public class Response {

	private Plan plan;
	private RequestParameters requestParameters;
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
}

