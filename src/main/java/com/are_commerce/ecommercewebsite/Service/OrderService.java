package com.are_commerce.ecommercewebsite.Service;

import com.are_commerce.ecommercewebsite.Model.Order;
import com.are_commerce.ecommercewebsite.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order addOrder(Order order){
        orderRepository.save(order);
        return order;
    }
    public Order deleteOrder(Order order){
        orderRepository.delete(order);
        return order;
    }
    public Order updateOrder(Order order){
        orderRepository.save(order);
        return order;
    }
    public Optional<Order> getOrder(String id, Order order){
        return orderRepository.findById(id);
    }

}
