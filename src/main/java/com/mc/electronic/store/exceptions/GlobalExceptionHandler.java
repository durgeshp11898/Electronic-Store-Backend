package com.mc.electronic.store.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.mc.electronic.store.dtos.ApiResponseMessage;


@RestControllerAdvice
public class GlobalExceptionHandler {

	//handle ResourceNotFound Exception
	Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundHandler(ResourceNotFoundException ex){
		logger.info("Exception Handler invoked");
		ApiResponseMessage apiResponseMessage=
				ApiResponseMessage.builder().message(ex.getMessage()).httpStatus(HttpStatus.NOT_FOUND).success(true).build();
		return new ResponseEntity<>(apiResponseMessage,HttpStatus.NOT_FOUND);
	}
	
	//MethodArgumentNotValidException handler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		List<ObjectError> allErros = ex.getBindingResult().getAllErrors();
		Map<String, Object> responseMap= new HashMap<>();
		
		allErros.stream().forEach(errorObject ->{
			String message= errorObject.getDefaultMessage();
			String field= ((FieldError) errorObject).getField();
			responseMap.put(message, field);
	
		});
		return new ResponseEntity<>(responseMap,HttpStatus.BAD_REQUEST);
	}
}
