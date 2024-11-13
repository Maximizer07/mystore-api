package com.romashkaco.mystoreapi.exception;

public class InvalidSaleRequestException extends RuntimeException {
    public InvalidSaleRequestException(String message) {
        super(message);
    }
}