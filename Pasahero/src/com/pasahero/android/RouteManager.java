package com.pasahero.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;
@JsonIgnoreProperties(ignoreUnknown = true)

public class RouteManager {

	final String API_URL = "http://192.168.0.103:8080/opentripplanner-api-webapp/ws/";
	final int HTTP_OK = 200;
	final String GET = "GET";
	final String POST = "POST";

	private URL url;
	private String requestType;
	
	public RouteManager() {
	}
	
	public void setUrl(URL url){
		this.url = url;
	}
	
	public URL getUrl() {
		return url;
	}

	public void setRequestType(String requestType){
		this.requestType = requestType;
	}
	
	public String getRequestType() {
		return requestType;
	}

	
	public void setUrl(String urlString){
		try {
			this.url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void get(String urlString){
		try {
			URL url = new URL(urlString);
			getRequest(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void get(URL url){
		try {
			getRequest(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void get(Hashtable<String, String> params) {
		try {
			URL url = RouteManager.contsructUrl(API_URL, params);
			getRequest(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void getRequest(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		int responseCode = conn.getResponseCode();
		System.out.println("Responded!");
		System.out.println(responseCode);
		if(responseCode != HTTP_OK){
			throw new RuntimeException("Failed : HTTP error code : "+ responseCode);
		}
		
		/*BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String response;
		while((response = br.readLine())!=null){
			System.out.println(response);
		}*/
		
		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = conn.getInputStream();
		Plan plan = mapper.readValue(stream, Plan.class);
		System.out.println(plan);
		conn.disconnect();
	}

	
	public static URL contsructUrl(String urlString,
			Hashtable<String, String> params) throws MalformedURLException {
		Enumeration<String> keys = params.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			urlString = urlString + key + "=" + params.get(key) + "&";
		}
		return new URL(urlString);
	}
}
