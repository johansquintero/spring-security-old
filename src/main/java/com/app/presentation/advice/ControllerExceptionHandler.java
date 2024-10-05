package com.app.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class,RuntimeException.class})
    public ResponseEntity<Map<String, String>> runtimeException(BadCredentialsException badCredentialsException) {
        Map<String, String> response = new HashMap<>();
        response.put("errorMessage", badCredentialsException.getMessage());
        response.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
