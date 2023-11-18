package com.are_commerce.ecommercewebsite.Repository;

import com.are_commerce.ecommercewebsite.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    public User findByUsername(String username);
}
