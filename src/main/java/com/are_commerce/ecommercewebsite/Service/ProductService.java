package com.are_commerce.ecommercewebsite.Service;

import com.are_commerce.ecommercewebsite.Exceptions.ProductDuplicateException;
import com.are_commerce.ecommercewebsite.Exceptions.ProductInactiveException;
import com.are_commerce.ecommercewebsite.Model.Product;
import com.are_commerce.ecommercewebsite.Exceptions.ProductServiceException;
import com.are_commerce.ecommercewebsite.Exceptions.ProductNotFoundException;

import com.are_commerce.ecommercewebsite.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getAllProducts() throws ProductServiceException, ProductNotFoundException{
        try{
            List<Product> products= productRepository.findAll();
            if(products.isEmpty())
                throw new ProductNotFoundException("No products found.");
            else
                return products;
        }
        catch(DataAccessException ex){
            throw new ProductServiceException("Error retrieving data.",ex);
        }
    }

    public Product getProductById(Long id) throws ProductServiceException, ProductInactiveException, ProductNotFoundException{
        try {
            // Data access logic to retrieve the product by its ID
            Optional<Product> productOptional = productRepository.findById(id);
            if (productOptional.isPresent()) {
                // Business logic to check if the product is active, etc.
                Product product = productOptional.get();
                if (product.getStockQuantity()>0) {
                    return product;
                } else {
                    // Custom exception for inactive product
                    throw new ProductInactiveException("Product is not available.");
                }
            } else {
                // Custom exception for product not found
                throw new ProductNotFoundException("Product not found.");
            }
        } catch (DataAccessException ex) {
            // Handle data access-related exceptions
            throw new ProductServiceException("Error retrieving product.", ex);
        }
    }


    public Product addProduct(Product product) throws ProductServiceException, ProductDuplicateException{
        try{
            if(productRepository.existsByName(product.getName()))
                throw new ProductDuplicateException("Product with the same name already exists.");
            else
                return productRepository.save(product);
        }
        catch(DataAccessException ex){
            throw new ProductServiceException("Error adding the product to the database.",ex);
        }
    }

    public boolean deleteProduct(Long id) throws ProductServiceException, ProductNotFoundException {
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                return true;
            }
            else
                throw new ProductNotFoundException("The product doesn't exist");
        }
        catch (DataAccessException ex){
            throw new ProductServiceException("Error deleting the product from the database.");
        }
    }
    /*
    public Product updateProduct(Product product) throws ProductServiceException, ProductDuplicateException, ProductNotFoundException {
        try{
            boolean existingProductByName=productRepository.existsByName(product.getName());
            Optional<Product> product1=productRepository.findById(product.getProductId());
            if(existingProductByName && product1.isPresent() && !Objects.equals(product.getName(), product1.get().getName()))
                throw new ProductDuplicateException("A product with the same name already exist. Please choose a different name.");
            if(!(productRepository.existsById(product.getProductId())))
                throw new ProductNotFoundException("Product not found. Cannot update a product that doesn't exist.");
            return productRepository.save(product);
        }
        catch(DataAccessException ex){
            throw new ProductServiceException("Error updating the product. Please try again later.");
        }
    }*/
}
