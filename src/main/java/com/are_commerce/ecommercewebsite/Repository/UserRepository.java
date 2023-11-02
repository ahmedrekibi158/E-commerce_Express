package com.are_commerce.ecommercewebsite.Repository;

import com.are_commerce.ecommercewebsite.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
