package com.are_commerce.ecommercewebsite.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Users")
public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private List<String> orders; // Store order IDs here
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
