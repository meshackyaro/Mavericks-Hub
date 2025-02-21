package com.mavericksstube.maverickshub.exceptions;

public class CommentCannotBeDeletedException extends RuntimeException{
    public CommentCannotBeDeletedException(String message) {
        super(message);
    }
}
