package com.epam.esm.exception.entity;

public class ExceptionResponseBody{
	int code;
	String message;
	String timestamp;
	public ExceptionResponseBody(int code, String message, String timestamp) {
		this.code = code;
		this.message = message;
		this.timestamp = timestamp;
	}
	
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getResponseMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
