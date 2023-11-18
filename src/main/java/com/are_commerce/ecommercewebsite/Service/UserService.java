package com.are_commerce.ecommercewebsite.Service;

import com.are_commerce.ecommercewebsite.Model.LoginRequest;
import com.are_commerce.ecommercewebsite.Model.User;
import com.are_commerce.ecommercewebsite.Repository.UserRepository;
//import com.are_commerce.ecommercewebsite.Security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //@Autowired
    //private final SecurityConfig securityConfig =new SecurityConfig();

    public String authenticate(LoginRequest loginRequest){
        System.out.println("user service");
        User user = userRepository.findByUsername(loginRequest.getUsername());
        //if(user!=null && securityConfig.isPasswordValid(loginRequest.getPassword(),user.getPassword()))
        if(user!=null && loginRequest.getPassword().equals(user.getPassword()))
            return "Valide";
        else
            return "Invalide";
    }

    public User addUser(User user){
        userRepository.save(user);
        return user;
    }
    public User deleteUser(User user){
        userRepository.delete(user);
        return user;
    }
    public User updateUser(User user){
        userRepository.save(user);
        return user;
    }
    public Optional<User> getUser(String id, User user){
        return userRepository.findById(id);
    }
}
