package com.are_commerce.ecommercewebsite.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Document(collection = "user")
public class User extends DBUser{

    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private List<String> orders; // Store order IDs here
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    //public User(String username, String password, Collection<? extends GrantedAuthority> authorities){}

}

/*

Billing Address: The user's billing address for payment and shipping purposes.

Shipping Address: The user's shipping address, which may be different from the billing address.

Profile Picture: An optional image representing the user.

Orders: A collection of orders placed by the user, including order history and details.

Wishlist: A list of products the user has added to their wishlist for future purchase.

Payment Methods: Information about the user's saved payment methods, such as credit cards or PayPal accounts.

Reviews and Ratings: Information about product reviews and ratings provided by the user.

Account Status: User account status, such as active, suspended, or banned.

Account Creation Date: The date and time when the user account was created.

Last Login Date: The date and time of the user's last login.

Newsletter Subscriptions: Whether the user is subscribed to newsletters or marketing updates.

Roles and Permissions: User roles and permissions to control access to certain features or admin capabilities.

Preferences: User-specific preferences, such as currency, language, or notification settings.

Shopping Cart: A temporary storage of products a user has added for immediate purchase. */
