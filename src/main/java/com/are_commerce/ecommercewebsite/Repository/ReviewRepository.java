package com.are_commerce.ecommercewebsite.Repository;

import com.are_commerce.ecommercewebsite.Model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review,String> {
}
