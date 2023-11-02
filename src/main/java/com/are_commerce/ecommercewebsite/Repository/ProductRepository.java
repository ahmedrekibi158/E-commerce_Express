package com.are_commerce.ecommercewebsite.Repository;

import com.are_commerce.ecommercewebsite.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    public boolean existsByName(String name);
}
