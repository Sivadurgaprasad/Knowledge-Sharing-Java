package com.jw.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jw.model.Error;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { DataAccessNotFoundException.class, FileIOException.class,
			InvalidInputDataException.class, KSFileHandlingException.class, NullDirectoryException.class,
			ObjectNotFoundException.class, InvalidDataAccessException.class })
	public ResponseEntity<Error> handleIOException(JavaWebGlobalException ex, WebRequest request) {
		Error error = new Error(ex.getErrorCode(), ex.getErrorMessage(), request.getDescription(true));
		return new ResponseEntity<Error>(error, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<Error> handleContraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		Error error = new Error("ks1107", ex.getMessage(), "Invalid Input");
		return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
