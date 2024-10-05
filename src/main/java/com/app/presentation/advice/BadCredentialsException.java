package com.app.presentation.advice;

public class BadCredentialsException extends RuntimeException{

    public BadCredentialsException(String msj){
        super(msj);
    }
}
