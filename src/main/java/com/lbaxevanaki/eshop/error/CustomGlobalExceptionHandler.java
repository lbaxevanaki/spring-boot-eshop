package com.lbaxevanaki.eshop.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	 private static final Logger LOG = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

    	LOG.error(ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);
        
        return new ResponseEntity<>(body, headers, status);

    }

 
    @ExceptionHandler({ EntityNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
    	 Map<String, Object> body = new LinkedHashMap<>();
         body.put("timestamp", new Date());
         body.put("status", HttpStatus.NOT_FOUND);
         
         List<String> errors = new ArrayList<>(Arrays.asList(ex.getMessage(),  "Entity with id:"+ ex.getMessage() + " does not exist." ));
         
         body.put("errors", errors);
         return new ResponseEntity<>(body,  new HttpHeaders(), HttpStatus.NOT_FOUND);
       
    }
   
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
    	 LOG.error(ex.getMessage());
    	 Map<String, Object> body = new LinkedHashMap<>();
         body.put("timestamp", new Date());
         body.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
         
         List<String> errors = new ArrayList<>(Arrays.asList(ex.getMessage(),  "Unexpected error occured."));
         
         body.put("errors", errors);
         return new ResponseEntity<>(body,  new HttpHeaders(), HttpStatus.NOT_FOUND);
      
    }
    
}