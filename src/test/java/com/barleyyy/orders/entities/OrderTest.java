package com.barleyyy.orders.entities;

import com.barleyyy.orders.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Autowired
    private Order order;
    private User user;
    private User anotherUser;
    private Date dateOfBirth;
    LocalDateTime timestamp;
    LocalDateTime newTimestamp;
    String orderToString;

    @BeforeEach
    private void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.OCTOBER, 22);
        dateOfBirth = calendar.getTime();
        timestamp = LocalDateTime.now();
        newTimestamp = LocalDateTime.of(1970, Month.OCTOBER, 22, 10, 45);
        order = new Order(1, "Laptop", 10, "Jl. Veteran", "089507153745", timestamp, timestamp);
        user = new User("Valid User", dateOfBirth, Gender.LAKI_LAKI, "validuser@gmail.com", "password", timestamp, timestamp);
        order.setUser(user);
        anotherUser = new User("Another Valid User", dateOfBirth, Gender.PEREMPUAN, "validuser2@gmail.com", "password", timestamp, timestamp);
        orderToString = "Order{id=1, name='Laptop', count=10, address='Jl. Veteran', phoneNumber='089507153745', createdAt="+ timestamp + ", updatedAt=" + timestamp + "}";
    }

    @Test
    void getUser() {
        assertEquals(user, order.getUser());
    }

    @Test
    void setUser() {
        order.setUser(anotherUser);
        assertEquals(anotherUser, order.getUser());
    }

    @Test
    void getId() {
        assertEquals(1, order.getId());
    }

    @Test
    void setId() {
        order.setId(2);
        assertEquals(2, order.getId());
    }

    @Test
    void getName() {
        assertEquals("Laptop", order.getName());
    }

    @Test
    void setName() {
        order.setName("New Laptop");
        assertEquals("New Laptop", order.getName());
    }

    @Test
    void getCount() {
        assertEquals(10, order.getCount());
    }

    @Test
    void setCount() {
        order.setCount(20);
        assertEquals(20, order.getCount());
    }

    @Test
    void setCountZero() throws Exception {
        order.setCount(0);
        assertEquals(10, order.getCount());
    }

    @Test
    void getAddress() {
        assertEquals("Jl. Veteran", order.getAddress());
    }

    @Test
    void setAddress() {
        order.setAddress("Jl. Baru");
        assertEquals("Jl. Baru", order.getAddress());
    }

    @Test
    void getPhoneNumber() {
        assertEquals("089507153745", order.getPhoneNumber());
    }

    @Test
    void setPhoneNumber() {
        order.setPhoneNumber("081212341234");
        assertEquals("081212341234", order.getPhoneNumber());
    }

    @Test
    void getCreatedAt() {
        assertEquals(timestamp, order.getCreatedAt());
    }

    @Test
    void setCreatedAt() {
        order.setCreatedAt(newTimestamp);
        assertEquals(newTimestamp, order.getCreatedAt());
    }

    @Test
    void getUpdatedAt() {
        assertEquals(timestamp, order.getUpdatedAt());
    }

    @Test
    void setUpdatedAt() {
        order.setUpdatedAt(newTimestamp);
        assertEquals(newTimestamp, order.getUpdatedAt());
    }

    @Test
    void testToString() {
        assertEquals(orderToString, order.toString());
    }
}