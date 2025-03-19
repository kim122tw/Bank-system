package com.kim.banking2.exception;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message, Throwable cause) {
    	super(message, cause);
    }
}
