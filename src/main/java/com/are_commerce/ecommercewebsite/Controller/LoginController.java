package com.are_commerce.ecommercewebsite.Controller;

import com.are_commerce.ecommercewebsite.Model.LoginRequest;
import com.are_commerce.ecommercewebsite.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/users")
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public String loginTest(){
        System.out.println("method test");
        //String token = userService.authenticate(new LoginRequest(username, password));
        //System.out.println("login method2"+token);
        return "User page";
    }
    @GetMapping("/admin")
    public String loginTest2(){
        System.out.println("login admin");
        //String token = userService.authenticate(new LoginRequest(username, password));
        //System.out.println("login method2"+token);
        return "redirect:/html/productManagement.html";
    }

}
