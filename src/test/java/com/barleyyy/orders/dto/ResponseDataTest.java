package com.barleyyy.orders.dto;

import com.barleyyy.orders.entities.Order;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResponseDataTest {
    private ResponseData<Order> responseData = new ResponseData<>();
    private Order order;
    private List<String> messages = new ArrayList<>();
    private LocalDateTime timestamp;
    private String responseDataString;

    @BeforeEach
    void setUp() {
        timestamp = LocalDateTime.now();
        order = new Order(1, "Laptop", 10, "Jl. Veteran", "089507153745", timestamp, timestamp);
        messages.add("sample message");
        responseData.setStatus(true);
        responseData.setMessages(messages);
        responseData.setPayload(order);
        responseDataString = "ResponseData{status=true, messages=[sample message], payload=Order{id=1, name='Laptop', count=10, address='Jl. Veteran', phoneNumber='089507153745', createdAt="+ timestamp + ", updatedAt=" + timestamp + "}}";
    }

    @Test
    void isStatus() {
        assertTrue(responseData.isStatus());
    }

    @Test
    void setStatus() {
        responseData.setStatus(false);
        assertFalse(responseData.isStatus());
    }

    @Test
    void getMessages() {
        assertEquals(messages, responseData.getMessages());
    }

    @Test
    void setMessages() {
        messages.add("another sample message");
        responseData.setMessages(messages);
        assertEquals(messages, responseData.getMessages());
    }

    @Test
    void getPayload() {
        assertEquals(order, responseData.getPayload());
    }

    @Test
    void setPayload() {
        Order newOrder = new Order(2, "Personal Computer", 10, "Jl. Veteran", "089507153745", timestamp, timestamp);
        responseData.setPayload(newOrder);
        assertEquals(newOrder, responseData.getPayload());
    }

    @Test
    void testToString() {
        assertEquals(responseDataString, responseData.toString());
    }
}