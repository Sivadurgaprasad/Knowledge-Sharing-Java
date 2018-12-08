package com.jw.exception;

public class NullDirectoryException extends KSFileHandlingException {
	private static final long serialVersionUID = -8775240450337193777L;

	public NullDirectoryException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public NullDirectoryException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public NullDirectoryException(String errorMessage) {
		super(errorMessage);
	}

}
