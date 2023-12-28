package com.cenasa.spaceservice.application.exceptions;

public class ConflictException extends RuntimeException{

    public ConflictException(String message){
        super(message);
    }
}
