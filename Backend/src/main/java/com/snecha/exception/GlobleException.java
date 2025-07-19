package com.snecha.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobleException {

	@ExceptionHandler(SellerException.class)
	ResponseEntity<ErrorDetails> sellerExceptionHandler(SellerException se,WebRequest req){
		ErrorDetails errorDetails=new ErrorDetails();
		errorDetails.setError(se.getMessage());
		errorDetails.setDetails(req.getDescription(false));
		errorDetails.setTimestamp(LocalDateTime.now());
	
		return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
		}
	
	@ExceptionHandler(ProductException.class)
	ResponseEntity<ErrorDetails> productExceptionHandler(ProductException se,WebRequest req){
		ErrorDetails errorDetails=new ErrorDetails();
		errorDetails.setError(se.getMessage());
		errorDetails.setDetails(req.getDescription(false));
		errorDetails.setTimestamp(LocalDateTime.now());
	
		return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
		}
	
	
}
