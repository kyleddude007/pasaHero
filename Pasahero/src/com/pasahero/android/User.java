package com.pasahero.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

public class User {

	private String id;
	private String email;
	private String salt;
	private String password;
	private String createdOn;
	private String updatedOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@JsonProperty("created_on")
	public String getCreatedOn() {
		return createdOn;
	}
	
	@JsonProperty("created_on")
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	@JsonProperty("updated_on")
	public String getUpdatedOn() {
		return updatedOn;
	}
	@JsonProperty("updated_on")
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "[ Email: " + email + " , " + "Password: " + password + " ]";
	}

	public static User getUser(String email, String password)
			throws IOException {
		URL url = new URL(Config.PH_API_URL + "/login");
		String jsonString = "{\"" + Config.EMAIL + "\":\"" + email + "\", \""
				+ Config.PASSWORD + "\":\"" + password + "\"}";
		byte[] jsonBytes = jsonString.getBytes();
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setFixedLengthStreamingMode(jsonBytes.length);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		OutputStream os = conn.getOutputStream();
		os.write(jsonBytes);
		os.close();

		int responseCode = conn.getResponseCode();
		if (responseCode != Config.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ responseCode);
		}

		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = conn.getInputStream();
		User user = mapper.readValue(stream, User.class);
		conn.disconnect();
		System.out.println("user resp: "+user.toString());
		return user;
	}
}
