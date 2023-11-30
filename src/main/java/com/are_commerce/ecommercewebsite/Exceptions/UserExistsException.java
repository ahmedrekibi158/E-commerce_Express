package com.are_commerce.ecommercewebsite.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class UserExistsException extends Exception{

    public UserExistsException(String message){
        super(message);
    }
}
