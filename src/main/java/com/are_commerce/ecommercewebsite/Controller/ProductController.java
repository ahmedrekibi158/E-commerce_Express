package com.are_commerce.ecommercewebsite.Controller;

import com.are_commerce.ecommercewebsite.Exceptions.ProductDuplicateException;
import com.are_commerce.ecommercewebsite.Exceptions.ProductInactiveException;
import com.are_commerce.ecommercewebsite.Exceptions.ProductNotFoundException;
import com.are_commerce.ecommercewebsite.Exceptions.ProductServiceException;
import com.are_commerce.ecommercewebsite.Model.Product;
import com.are_commerce.ecommercewebsite.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/admin/products")
@CrossOrigin(origins = "*")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() throws ProductServiceException, ProductNotFoundException{
        List<Product> products=productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) throws ProductNotFoundException, ProductServiceException, ProductInactiveException {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
    }

    @PutMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws ProductServiceException, ProductDuplicateException {
        Product product1=productService.addProduct(product);
        return ResponseEntity.ok(product1);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String id) throws ProductNotFoundException, ProductServiceException {
        boolean deleted= productService.deleteProduct(id);
        return ResponseEntity.ok("The product was successfully deleted.");
    }

    @PostMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws ProductNotFoundException, ProductDuplicateException, ProductServiceException {
        Product product1=productService.updateProduct(product);
        return ResponseEntity.ok(product1);
    }
}
