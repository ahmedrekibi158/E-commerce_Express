package com.are_commerce.ecommercewebsite.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;
}
