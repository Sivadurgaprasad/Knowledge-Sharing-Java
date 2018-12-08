package com.jw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NO_CONTENT)
public class ObjectNotFoundException extends JavaWebGlobalException {

	private static final long serialVersionUID = -3374629393565061694L;

	public ObjectNotFoundException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public ObjectNotFoundException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public ObjectNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
