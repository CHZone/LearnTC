package com.cai.learntc.server;

public enum ResponseStateEnum {
	OK(200,"OK"),
	FILE_NOT_FOUND(404,"File Not Found");
	private int statusCode;
	private String statusPhrase;
	
	private ResponseStateEnum(Integer statusCode, String statusPhrase){
		this.statusCode = statusCode;
		this.statusPhrase = statusPhrase;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusPhrase() {
		return statusPhrase;
	}

	public void setStatusPhrase(String statusPhrase) {
		this.statusPhrase = statusPhrase;
	}
	
}
