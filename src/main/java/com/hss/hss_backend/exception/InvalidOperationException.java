package com.hss.hss_backend.exception;

public class InvalidOperationException extends RuntimeException {
    
    public InvalidOperationException(String message) {
        super(message);
    }
    
    public InvalidOperationException(String resource, String operation) {
        super(String.format("Invalid operation '%s' on %s", operation, resource));
    }
}
