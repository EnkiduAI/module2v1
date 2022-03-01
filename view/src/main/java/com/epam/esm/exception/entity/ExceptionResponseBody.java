package com.epam.esm.exception.entity;

public class ExceptionResponseBody{
	int code;
	String message;
	public ExceptionResponseBody(int code, String message) {
		this.code = code;
		this.message = message;
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
