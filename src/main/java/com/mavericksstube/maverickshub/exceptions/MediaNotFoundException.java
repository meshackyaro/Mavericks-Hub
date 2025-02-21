package com.mavericksstube.maverickshub.exceptions;

public class MediaNotFoundException extends RuntimeException{
    public MediaNotFoundException(String message){
        super(message);
    }
}
