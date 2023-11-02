package com.are_commerce.ecommercewebsite.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private String productId; // Store product ID here
    private String userId; // Store user ID here
    private double rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
