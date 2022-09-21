package com.barleyyy.orders.controllers;

import com.barleyyy.orders.dto.ResponseData;
import com.barleyyy.orders.dto.SearchData;
import com.barleyyy.orders.entities.Order;
import com.barleyyy.orders.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @MockBean
    private SearchData searchData;

    @Autowired
    private OrderController orderController;

    private final int validId = 1;
    private final int invalidId = 9999;
    private Order order;
    private List<Order> orders = new ArrayList<>();
    private List<String> messages = new ArrayList<>();

    @BeforeEach
    private void setUp() {
        LocalDateTime timestamp = LocalDateTime.now();
        order = new Order(1, "Laptop", 10, "Jl. Veteran", "089507153745", timestamp, timestamp);
        orders.add(order);
        orders.add(new Order(2, "Personal Computer", 10, "Jl. Veteran", "089507153745", timestamp, timestamp));
    }

    @Test
    void getAllOrdersSuccess() {
        messages.add("Get All Orders Success!");

        Mockito.when(orderService.getAllOrders()).thenReturn(orders);
        ResponseEntity<ResponseData<Iterable<Order>>> result = orderController.index();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertTrue(result.getBody().isStatus());
        assertNotNull(result.getBody().getPayload());

        Mockito.verify(orderService, Mockito.times(1)).getAllOrders();
    }

    @Test
    void getAllOrdersButNoOrderSaved() {
        messages.add("Get All Orders Success!");

        Mockito.when(orderService.getAllOrders()).thenReturn(new ArrayList<>());
        ResponseEntity<ResponseData<Iterable<Order>>> result = orderController.index();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertTrue(result.getBody().isStatus());
        assertNotNull(result.getBody().getPayload());

        Mockito.verify(orderService, Mockito.times(1)).getAllOrders();
    }

    @Test
    void findByIdSuccess() {
        messages.add("Get Order Success!");

        Mockito.when(orderService.getSpecifiedOrder(validId)).thenReturn(Optional.of(order));
        ResponseEntity<ResponseData<Optional<Order>>> result = orderController.findById(validId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertTrue(result.getBody().isStatus());
        assertNotNull(result.getBody().getPayload());

        Mockito.verify(orderService, Mockito.times(1)).getSpecifiedOrder(validId);
    }

    @Test
    void findByIdFailed() {
        messages.add("Order Not Found!");

        Mockito.when(orderService.getSpecifiedOrder(invalidId)).thenReturn(Optional.empty());
        ResponseEntity<ResponseData<Optional<Order>>> result = orderController.findById(invalidId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertFalse(result.getBody().isStatus());
        assertNotNull(result.getBody().getPayload());

        Mockito.verify(orderService, Mockito.times(1)).getSpecifiedOrder(invalidId);
    }

    @Test
    void addOrder() {
        messages.add("Add Order Success!");

        Mockito.when(orderService.store(order)).thenReturn(order);
        ResponseEntity<ResponseData<Order>> result = orderController.addOrder(order);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertTrue(result.getBody().isStatus());
        assertNotNull(result.getBody().getPayload());

        Mockito.verify(orderService, Mockito.times(1)).store(order);
    }

    @Test
    void deleteOrderExist() {
        messages.add("Delete Order Success!");

        Mockito.when(orderService.delete(validId)).thenReturn(true);
        ResponseEntity<ResponseData<Order>> result = orderController.deleteOrder(validId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertTrue(result.getBody().isStatus());
        assertNull(result.getBody().getPayload());

        Mockito.verify(orderService, Mockito.times(1)).delete(validId);
    }

    @Test
    void deleteOrderNotExist() {
        messages.add("Delete Order Fail! ID Not Found");

        Mockito.when(orderService.delete(invalidId)).thenReturn(false);
        ResponseEntity<ResponseData<Order>> result = orderController.deleteOrder(invalidId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertFalse(result.getBody().isStatus());
        assertNull(result.getBody().getPayload());
    }

    @Test
    void update() {
        messages.add("Update Order Success!");

        Mockito.when(orderService.store(order)).thenReturn(order);
        ResponseEntity<ResponseData<Order>> result = orderController.update(order);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertTrue(result.getBody().isStatus());
        assertNotNull(result.getBody().getPayload());

        Mockito.verify(orderService, Mockito.times(1)).store(order);
    }

    @Test
    void getOrderByName() {
        messages.add("Search Order Success!");

        Mockito.when(searchData.getSearchKey()).thenReturn("Key");
        Mockito.when(orderService.findByName("Key")).thenReturn(orders);
        ResponseEntity<ResponseData<List<Order>>> result = orderController.getOrderByName(searchData);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertTrue(result.getBody().isStatus());
        assertNotNull(result.getBody().getPayload());

        Mockito.verify(orderService, Mockito.times(1)).findByName("Key");
    }
}