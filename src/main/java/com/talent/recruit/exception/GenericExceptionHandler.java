package com.talent.recruit.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.talent.recruit.model.AppResponse;
import com.talent.recruit.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public AppResponse handleDataNotFoundException(DataNotFoundException ex, WebRequest request) {
		log.error("*****DataNotFoundException***{}",ex);
		return new AppResponse(ex.getMessage());
	}

	@ExceptionHandler(ResourceAlreadyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public AppResponse handleNodataFoundException(ResourceAlreadyExistException ex, WebRequest request) {
		log.error("*****ResourceAlreadyExistException***{}",ex);
		return new AppResponse(ex.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
	  return new ResponseEntity<>(new AppResponse(Constants.SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("*****MethodArgumentNotValidException***{}",ex);

		BindingResult result = ex.getBindingResult();

		Map<String, String> errors = result.getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
