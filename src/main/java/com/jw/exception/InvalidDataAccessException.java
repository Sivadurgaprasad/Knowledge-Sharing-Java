package com.jw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NO_CONTENT)
public class InvalidDataAccessException extends JavaWebGlobalException{

	private static final long serialVersionUID = -8536743942580656436L;

	public InvalidDataAccessException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public InvalidDataAccessException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public InvalidDataAccessException(String errorMessage) {
		super(errorMessage);
	}
}
