package com.barleyyy.orders.services;

import com.barleyyy.orders.entities.Order;
import com.barleyyy.orders.repository.OrderRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @MockBean
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @Test
    void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        orders.add(new Order(1, "Laptop", 10, "Jl. Veteran", "089507153745", createdAt, updatedAt));
        orders.add(new Order(2, "Personal Computer", 10, "Jl. Veteran", "089507153745", createdAt, updatedAt));

        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        assertEquals(orders, orderService.getAllOrders());

        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getSpecifiedOrder() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Order order = new Order(1, "Laptop", 10, "Jl. Veteran", "089507153745", createdAt, updatedAt);

        Mockito.when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        assertEquals(Optional.of(order), orderService.getSpecifiedOrder(1));

        Mockito.verify(orderRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @Disabled
    void store() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Order order = new Order(1, "Laptop", 10, "Jl. Veteran", "089507153745", createdAt, updatedAt);

        Mockito.when(orderRepository.save(order)).thenReturn(order);
        assertEquals(order, orderService.store(order));

        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
    }

    @Test
    void deleteExistUser() {
        int existId = 1;

        Mockito.when(orderRepository.existsById(existId)).thenReturn(true);
        assertTrue(orderService.delete(existId));

        Mockito.verify(orderRepository, Mockito.times(1)).existsById(existId);
        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(existId);
    }

    @Test
    void deleteNotExistUser() {
        int notExistId = 9999;

        Mockito.when(orderRepository.existsById(notExistId)).thenReturn(false);
        assertFalse(orderService.delete(notExistId));

        Mockito.verify(orderRepository, Mockito.times(1)).existsById(notExistId);
        Mockito.verify(orderRepository, Mockito.times(0)).deleteById(notExistId);
    }

    @Test
    void findByName() {
        List<Order> orders = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        orders.add(new Order(1, "Laptop HP", 10, "Jl. Veteran", "089507153745", createdAt, updatedAt));
        orders.add(new Order(2, "Laptop Asus", 10, "Jl. Veteran", "089507153745", createdAt, updatedAt));

        Mockito.when(orderRepository.findOrderByName("Laptop")).thenReturn(orders);
        assertEquals(orders, orderRepository.findOrderByName("Laptop"));

        Mockito.verify(orderRepository, Mockito.times(1)).findOrderByName("Laptop");
    }
}