package com.are_commerce.ecommercewebsite.Exceptions;

public class ProductInactiveException extends Exception {

    public ProductInactiveException(String message) {
        super(message);
    }

    public ProductInactiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
