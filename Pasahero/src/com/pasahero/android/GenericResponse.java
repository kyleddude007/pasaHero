package com.pasahero.android;

import java.util.Hashtable;
import java.util.Vector;

public class GenericResponse {

	private Vector<Fare> response;
	private Vector<Hashtable<String, String>> pnrTable;

	public Vector<Fare> getResponse() {
		return response;
	}

	public void setResponse(Vector<Fare> response) {
		this.response = response;
	}

	public Vector<Hashtable<String, String>> getPnrTable() {
		return pnrTable;
	}

	public void setPnrTable(Vector<Hashtable<String, String>> pnrTable) {
		this.pnrTable = pnrTable;
	}
}
