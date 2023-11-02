package com.are_commerce.ecommercewebsite.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class Product {
    @Id
    public String productId;
    public String name;
    public String description;
    public double price;
    public String category;
    public int stockQuantity;
    public List<String> images;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

}
