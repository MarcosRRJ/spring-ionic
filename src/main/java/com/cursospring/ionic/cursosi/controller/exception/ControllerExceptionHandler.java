package com.cursospring.ionic.cursosi.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cursospring.ionic.cursosi.service.exceptions.AuthorizationException;
import com.cursospring.ionic.cursosi.service.exceptions.DataIntegrityException;
import com.cursospring.ionic.cursosi.service.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandarError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandarError err = new StandarError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandarError> DataIntegrityException(DataIntegrityException e, HttpServletRequest request) {

		StandarError err = new StandarError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandarError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		
		for(FieldError x :  e.getBindingResult().getFieldErrors()){
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandarError> authorization(AuthorizationException e, HttpServletRequest request) {

		StandarError err = new StandarError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);

	}
	
}
