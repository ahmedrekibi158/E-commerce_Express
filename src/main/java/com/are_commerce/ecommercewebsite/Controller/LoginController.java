package com.are_commerce.ecommercewebsite.Controller;

import com.are_commerce.ecommercewebsite.Model.LoginRequest;
import com.are_commerce.ecommercewebsite.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/users")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        System.out.println("login method");
        String token = userService.authenticate(loginRequest);
        System.out.println("login method"+token);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/login2")
    public ResponseEntity<String> login(@RequestParam String username,@RequestParam String password){
        System.out.println("login method2"+username);
        String token = userService.authenticate(new LoginRequest(username, password));
        System.out.println("login method2"+token);
        return ResponseEntity.ok(token);
    }
    @GetMapping("/user")
    public String loginTest(){
        System.out.println("method test");
        //String token = userService.authenticate(new LoginRequest(username, password));
        //System.out.println("login method2"+token);
        return "User page";
    }
    @GetMapping("/admin")
    public String loginTest2(){
        System.out.println("method test 2 admin");
        //String token = userService.authenticate(new LoginRequest(username, password));
        //System.out.println("login method2"+token);
        return "Admin page";
    }

}
