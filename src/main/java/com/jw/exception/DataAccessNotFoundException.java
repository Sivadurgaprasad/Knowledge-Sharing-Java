package com.jw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NO_CONTENT, reason="Data not available in database")
public class DataAccessNotFoundException extends JavaWebGlobalException {

	private static final long serialVersionUID = -3248303203130422306L;

	public DataAccessNotFoundException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public DataAccessNotFoundException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public DataAccessNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
