package com.are_commerce.ecommercewebsite.Repository;
import com.are_commerce.ecommercewebsite.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DBUserRepository extends MongoRepository<User, Integer> {
    public User findByUsername(String username);
}