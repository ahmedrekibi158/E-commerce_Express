package com.are_commerce.ecommercewebsite.Service;

import com.are_commerce.ecommercewebsite.Model.AuthenticationRequest;
import com.are_commerce.ecommercewebsite.Security.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final JwtUserDetailsService jwtUserDetailsService;

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    public Map<String, String> authenticate(AuthenticationRequest authenticationRequest) throws Exception {
        Map<String, String> tokens = new HashMap<>();
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        tokens.put("access-token",token);
        return tokens;
    }
    private void authenticate(String username, String password) throws Exception {

        if(username == null || password == null){
            throw new NullPointerException("Username and password are required");
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            logger.info("authenticate completed successfully.");
        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials: {}", e.getMessage());
            throw new BadCredentialsException("Invalid username or password", e);
        } catch (AuthenticationServiceException e) {
            // Handle authentication service exception
            logger.error("Authentication service exception: {}", e.getMessage());
            throw new Exception("AUTH_SERVICE_ERROR", e);
        }
        catch (CredentialsExpiredException | AccountExpiredException | LockedException | DisabledException e){
            logger.error("CredentialsExpiredException or AccountExpiredException or LockedException or EnabledException", e.getMessage());
            // Must handle these errors later
            throw new Exception("CredentialsExpiredException | AccountExpiredException | LockedException | EnabledException");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

}

