package com.are_commerce.ecommercewebsite.Controller;

import com.are_commerce.ecommercewebsite.Model.AuthenticationRequest;
import com.are_commerce.ecommercewebsite.Security.JwtTokenUtil;
import com.are_commerce.ecommercewebsite.Service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        //List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        //authorities.stream().map(role -> role.getAuthority());

            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/dashboard");
            } else {
                response.sendRedirect("/home");
            }
    }

}
