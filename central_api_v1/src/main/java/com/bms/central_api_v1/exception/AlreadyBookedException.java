package com.bms.central_api_v1.exception;

public class AlreadyBookedException extends RuntimeException{
    public AlreadyBookedException(String message){
        super(message);
    }
}
