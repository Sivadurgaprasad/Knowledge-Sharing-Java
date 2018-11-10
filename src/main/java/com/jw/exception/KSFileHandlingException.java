package com.jw.exception;

public class KSFileHandlingException extends JavaWebGlobalException {

	private static final long serialVersionUID = 8454031784075884105L;

	public KSFileHandlingException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public KSFileHandlingException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public KSFileHandlingException(String errorMessage) {
		super(errorMessage);
	}
}
