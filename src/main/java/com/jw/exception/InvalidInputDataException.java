package com.jw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Invalid input data")
public class InvalidInputDataException extends JavaWebGlobalException {

	private static final long serialVersionUID = -2396756226826101098L;

	public InvalidInputDataException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public InvalidInputDataException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public InvalidInputDataException(String errorMessage) {
		super(errorMessage);
	}

}
