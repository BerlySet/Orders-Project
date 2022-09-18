package com.barleyyy.orders.services;

import com.barleyyy.orders.entities.Order;
import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.repository.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private static final Logger log = LogManager.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    public Iterable<Order> getAllOrders() {
        log.info("Call findAll() to get all orders");
        return orderRepository.findAll();
    }

    public Optional<Order> getSpecifiedOrder(int id) {
        log.info("Call findById(id) to get specified order");
        return orderRepository.findById(id);
    }

    public Order store(Order order) {
        log.info("Call userService.getUserLoggedIn() to get user logged");
        User userLoggedIn = userService.getUserLoggedIn();
        log.info("Call setUser() to set user data");
        order.setUser(userLoggedIn);
        log.info("Call save() to save order data");
        return orderRepository.save(order);
    }

    public boolean delete(int id) {
        log.info("Check if order existsById(), if exists then delete it");
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Order> findByName(String name) {
        log.info("Call findOrderByName(%name%) to search order data");
        return orderRepository.findOrderByName("%"+name+"%");
    }
}
