package com.barleyyy.orders.services;

import com.barleyyy.orders.entities.Order;
import com.barleyyy.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getSpecifiedOrder(int id) {
        return orderRepository.findById(id);
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }
}
