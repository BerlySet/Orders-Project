package com.barleyyy.orders.controllers;

import com.barleyyy.orders.dto.ResponseData;
import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.services.UserService;
import com.barleyyy.orders.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    private User validUser;
    private User invalidUser;
    private final List<User> users = new ArrayList<>();
    private List<String> messages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.OCTOBER, 22);
        Date dateOfBirth = calendar.getTime();
        LocalDateTime timestamp = LocalDateTime.now();
        validUser = new User("Valid User", dateOfBirth, Gender.LAKI_LAKI, "validuser@gmail.com", "password", timestamp, timestamp);
        invalidUser = new User("Invalid User", dateOfBirth, Gender.PEREMPUAN, "invaliduser@gmail.com", "password", timestamp, timestamp);
        users.add(validUser);
        users.add(new User("Valid User 2", dateOfBirth, Gender.PEREMPUAN, "validuser2@gmail.com", "password", timestamp, timestamp));
    }

    @Test
    void getAllUsers() {
        messages.add("Get All Users Success!");

        Mockito.when(userService.getALlUsers()).thenReturn(users);
        ResponseEntity<ResponseData<List<User>>> result = userController.index();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertTrue(result.getBody().isStatus());
        assertNotNull(result.getBody().getPayload());

        Mockito.verify(userService, Mockito.times(1)).getALlUsers();
    }

    @Test
    void registerValidUser() {
        messages.add("Register Success!");

        Mockito.when(userService.registerUser(validUser)).thenReturn(validUser);
        ResponseEntity<ResponseData<User>> result = userController.register(validUser);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertTrue(result.getBody().isStatus());
        assertNotNull(result.getBody().getPayload());

        Mockito.verify(userService, Mockito.times(1)).registerUser(validUser);
    }

    @Test
    void registerInvalidUser() {
        messages.add("User with email " + invalidUser.getEmail() + " already exists");

        Mockito.when(userService.registerUser(invalidUser)).thenThrow(new RuntimeException("User with email " + invalidUser.getEmail() + " already exists"));
        ResponseEntity<ResponseData<User>> result = userController.register(invalidUser);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getMessages());
        assertEquals(messages, result.getBody().getMessages());
        assertFalse(result.getBody().isStatus());
        assertNull(result.getBody().getPayload());

        Mockito.verify(userService, Mockito.times(1)).registerUser(invalidUser);
    }

    @Test
    void getUserLoggedIn() {
    }

    @Test
    void updateProfile() {
    }
}