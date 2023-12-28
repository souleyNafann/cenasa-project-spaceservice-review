package com.cenasa.spaceservice.application.exceptions;

public class  LoginFailedException extends RuntimeException{
    public LoginFailedException(String message){
        super(message);
    }
}
