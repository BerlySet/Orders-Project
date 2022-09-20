package com.barleyyy.orders.controllers;

import com.barleyyy.orders.dto.ResponseData;
import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.services.UserService;
import com.barleyyy.orders.utils.Gender;
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

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.OCTOBER, 22);
        Date dateOfBirth = calendar.getTime();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        users.add(new User("User 1", dateOfBirth, Gender.LAKI_LAKI, "user1@gmail.com", "password", createdAt, updatedAt));
        users.add(new User("User 2", dateOfBirth, Gender.LAKI_LAKI, "user2@gmail.com", "password", createdAt, updatedAt));
        List<String> messages = new ArrayList<>();
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
    void register() {
    }

    @Test
    void getUserLoggedIn() {
    }

    @Test
    void updateProfile() {
    }
}