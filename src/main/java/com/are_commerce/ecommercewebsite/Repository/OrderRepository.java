package com.are_commerce.ecommercewebsite.Repository;

import com.are_commerce.ecommercewebsite.Model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {
}
