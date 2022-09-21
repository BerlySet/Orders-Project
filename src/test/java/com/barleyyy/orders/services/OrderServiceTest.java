package com.barleyyy.orders.services;

import com.barleyyy.orders.entities.Order;
import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.repository.OrderRepository;
import com.barleyyy.orders.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private UserService userService;
    @Autowired
    private OrderService orderService;

    private List<Order> orders = new ArrayList<>();
    private Order order;
    private User user;

    @BeforeEach
    private void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.OCTOBER, 22);
        Date dateOfBirth = calendar.getTime();
        LocalDateTime timestamp = LocalDateTime.now();
        order = new Order(1, "Laptop HP Probook", 10, "Jl. Veteran", "089507153745", timestamp, timestamp);
        orders.add(order);
        orders.add(new Order(2, "Laptop Asus Vivobook", 10, "Jl. Veteran", "089507153745", timestamp, timestamp));
        user = new User("Valid User", dateOfBirth, Gender.LAKI_LAKI, "validuser@gmail.com", "password", timestamp, timestamp);
    }

    @Test
    void getAllOrders() {
        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        assertEquals(orders, orderService.getAllOrders());

        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getSpecifiedOrder() {
        Mockito.when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        assertEquals(Optional.of(order), orderService.getSpecifiedOrder(1));

        Mockito.verify(orderRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void store() {
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        Mockito.when(userService.getUserLoggedIn()).thenReturn(user);
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
        Mockito.when(orderRepository.findOrderByName("Laptop")).thenReturn(orders);
        assertEquals(orders, orderRepository.findOrderByName("Laptop"));

        Mockito.verify(orderRepository, Mockito.times(1)).findOrderByName("Laptop");
    }
}