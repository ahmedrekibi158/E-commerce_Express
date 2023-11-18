package com.are_commerce.ecommercewebsite.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "order")
public class Order {
    @Id
    private String id;
    private String userId; // Store user ID here
    private List<OrderItem> products;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String shippingAddress;
    private String status;

    // Getters and setters
}
