package com.are_commerce.ecommercewebsite.Model;

import java.util.Set;

public class OrderDTO {
    private Long id;
    private UserDTO user;
    private Set<ProductDTO> products;
    private Double total;
    // getters and setters
}
