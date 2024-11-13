package com.romashkaco.mystoreapi.exception;

public class SupplyNotFoundException extends RuntimeException {
    public SupplyNotFoundException(Long id) {
        super("Supply with ID " + id + " not found");
    }
}
