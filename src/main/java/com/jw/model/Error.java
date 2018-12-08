package com.jw.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Error implements Serializable {

	private static final long serialVersionUID = 3787919911740267438L;

	private String errorCode;
	private String errorMessage;
	private String errorDescription;

	public Error(String errorCode, String errorMessage, String errorDescription) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorDescription = errorDescription;
	}

}
