package com.jw.util;

public enum HttpStatusCode {
	DATA_NOT_FOUND("ks1100", "data not found");

	private final String code;
	private final String value;

	HttpStatusCode(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String code() {
		return code;
	}
	
	public String value() {
		return value;
	}

}
