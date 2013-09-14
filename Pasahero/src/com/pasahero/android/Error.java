package com.pasahero.android;

public class Error {

	private int id;
	private String msg;
	private String missing;
	private boolean noPath;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMissing() {
		return missing;
	}
	public void setMissing(String missing) {
		this.missing = missing;
	}
	public boolean isNoPath() {
		return noPath;
	}
	public void setNoPath(boolean noPath) {
		this.noPath = noPath;
	}
	@Override
	public String toString(){
		return id+": "+msg;
	}
}
