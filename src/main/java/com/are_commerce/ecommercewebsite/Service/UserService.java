package com.are_commerce.ecommercewebsite.Service;

import com.are_commerce.ecommercewebsite.Model.User;
import com.are_commerce.ecommercewebsite.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
