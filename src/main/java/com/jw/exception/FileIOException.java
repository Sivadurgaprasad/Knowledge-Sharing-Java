package com.jw.exception;

public class FileIOException extends KSFileHandlingException {

	private static final long serialVersionUID = 1662040458331576238L;

	public FileIOException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public FileIOException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public FileIOException(String errorMessage) {
		super(errorMessage);
	}
}
