package com.are_commerce.ecommercewebsite.Exceptions;


public class ProductServiceException extends Exception{
    public ProductServiceException(String message, Throwable cause){
        super(message,cause);
    }
    public ProductServiceException(String message){
        super(message);
    }

}
