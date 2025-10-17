package com.hss.hss_backend.exception;

public class InsufficientStockException extends RuntimeException {
    
    public InsufficientStockException(String message) {
        super(message);
    }
    
    public InsufficientStockException(String product, int requested, int available) {
        super(String.format("Insufficient stock for %s. Requested: %d, Available: %d", product, requested, available));
    }
}
