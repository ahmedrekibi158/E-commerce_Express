package com.are_commerce.ecommercewebsite.Controller;

import com.are_commerce.ecommercewebsite.Model.AuthenticationRequest;
import com.are_commerce.ecommercewebsite.Service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    //@PostMapping("/admin/dashboard")
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        logger.info("Received a request to /authenticate");

        Map<String, String> idToken = authenticationService.authenticate(authenticationRequest);

        return new ResponseEntity<>(idToken, HttpStatus.OK);
    }
    @GetMapping("/home")
    public String home() {
        return "Home";
    }
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }


}