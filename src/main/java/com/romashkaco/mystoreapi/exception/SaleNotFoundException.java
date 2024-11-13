package com.romashkaco.mystoreapi.exception;

public class SaleNotFoundException extends RuntimeException {
    public SaleNotFoundException(Long id) {
        super("Supply with ID " + id + " not found");
    }
}
