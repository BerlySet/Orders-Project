package com.barleyyy.orders.services;

import com.barleyyy.orders.entities.Order;
import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getSpecifiedOrder(int id) {
        return orderRepository.findById(id);
    }

    public Order store(Order order) {
        User userLoggedIn = userService.getUserLoggedIn();
        order.setUser(userLoggedIn);
        return orderRepository.save(order);
    }

    public boolean delete(int id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Order> findByName(String name) {
        return orderRepository.findOrderByName("%"+name+"%");
    }
}
