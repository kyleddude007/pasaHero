package com.pasahero.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripPlanner {

	final String API_URL = "http://192.168.0.103:8080/opentripplanner-api-webapp/ws/";
	final int HTTP_OK = 200;
	final String GET = "GET";
	final String POST = "POST";

	private URL url;
	private String requestType;
	private TripPlannerInterface requestItineraryInterface;

	public TripPlanner() {

	}

	public TripPlanner(TripPlannerInterface requestItineraryInterface) {
		this.requestItineraryInterface = requestItineraryInterface;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public URL getUrl() {
		return url;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setUrl(String urlString) {
		try {
			this.url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void get(String urlString) {
		try {
			URL url = new URL(urlString);
			getRequest(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Response get(URL url) {
		Response response = null;
		try {
			response = getRequest(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public void get(Hashtable<String, String> params) {
		try {
			URL url = Utils.contsructUrl(API_URL, params);
			getRequest(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Fare getFare(URL url) throws IOException{
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		int responseCode = conn.getResponseCode();
		if (responseCode != HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ responseCode);
		}
		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = conn.getInputStream();
		FareDummy dummy = mapper.readValue(stream, FareDummy.class);
		conn.disconnect();
		System.out.println("fare resp: "+dummy.getResponse().get(0).getType());
		return dummy.getResponse().get(0);
	}
	
	private Response getRequest(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		int responseCode = conn.getResponseCode();
		if (responseCode != HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ responseCode);
		}
		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = conn.getInputStream();
		Response response = mapper.readValue(stream, Response.class);
		conn.disconnect();
		System.out.println(response);
		return response;
	}
}
