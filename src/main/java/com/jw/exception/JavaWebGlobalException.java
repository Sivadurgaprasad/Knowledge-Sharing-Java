package com.jw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@ResponseStatus(code = HttpStatus.RESET_CONTENT)
public class JavaWebGlobalException extends RuntimeException {

	private static final long serialVersionUID = 7289120501057014440L;

	private String errorMessage;
	private String errorCode;

	public JavaWebGlobalException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorMessage = errorMessage;
	}

	public JavaWebGlobalException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public JavaWebGlobalException(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
