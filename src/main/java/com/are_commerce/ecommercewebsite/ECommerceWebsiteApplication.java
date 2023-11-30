package com.are_commerce.ecommercewebsite;

import com.are_commerce.ecommercewebsite.Security.RsaKeysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan("com.are_commerce.ecommercewebsite.*")
@SpringBootApplication
@EnableConfigurationProperties(RsaKeysConfig.class)
public class ECommerceWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceWebsiteApplication.class, args);
    }

}
