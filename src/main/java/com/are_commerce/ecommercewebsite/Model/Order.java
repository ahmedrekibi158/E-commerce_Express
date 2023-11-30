package com.are_commerce.ecommercewebsite.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private DBUser user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<Product> products;

    @NotNull
    private Double total;

    @NotNull
    private LocalDateTime orderDate;

    @NotBlank
    private String shippingAddress;

    @NotBlank
    private String status;
}
