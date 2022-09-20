package com.barleyyy.orders.services;

import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.repository.UserRepository;
import com.barleyyy.orders.utils.Gender;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private Authentication authentication;
    @Autowired
    private UserService userService;

    @Test
    void loadUserByUsernameNotFoundTest() {
        Mockito.when(userRepository.findByEmail("notfound@gmail.com")).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("notfound@gmail.com"));
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail("notfound@gmail.com");
    }

    @Test
    void loadUserByUsernameFoundTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.OCTOBER, 22);
        Date dateOfBirth = calendar.getTime();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        User validUser =  new User("Found", dateOfBirth, Gender.LAKI_LAKI, "found@gmail.com", "password", createdAt, updatedAt);
        Mockito.when(userRepository.findByEmail("found@gmail.com")).thenReturn(Optional.of(validUser));

        assertEquals(validUser, userService.loadUserByUsername("found@gmail.com"));
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail("found@gmail.com");
    }

    @Test
    void registerValidUser() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.OCTOBER, 22);
        Date dateOfBirth = calendar.getTime();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        User newValidUser =  new User("New User", dateOfBirth, Gender.LAKI_LAKI, "newuser@gmail.com", "password", createdAt, updatedAt);
        Mockito.when(userRepository.save(newValidUser)).thenReturn(newValidUser);

        User user = userService.registerUser(newValidUser);

        assertEquals("New User", user.getFullName());
        assertEquals(dateOfBirth, user.getDateOfBirth());
        assertEquals("LAKI_LAKI", user.getGender());
        assertEquals("newuser@gmail.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals(updatedAt, user.getUpdatedAt());

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(newValidUser.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).save(newValidUser);
    }

//    @Test
//    void registerNotValidUser() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2023, Calendar.OCTOBER, 22);
//        Date dateOfBirth = calendar.getTime();
//        LocalDateTime createdAt = LocalDateTime.now();
//        LocalDateTime updatedAt = LocalDateTime.now();
//
//        User newNotValidUser =  new User("Not Valid", dateOfBirth, Gender.LAKI_LAKI, "notvalid@gmail.com", "password", createdAt, updatedAt);
//        Mockito.when(userRepository.findByEmail("notvalid@gmail.com")).thenReturn(Optional.of(newNotValidUser));
//
//        assertThrows(RuntimeException.class, () -> userService.registerUser(newNotValidUser));
//        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(newNotValidUser.getEmail());
//    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.OCTOBER, 22);
        Date dateOfBirth = calendar.getTime();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        User newUser =  new User("User1", dateOfBirth, Gender.LAKI_LAKI, "user1@gmail.com", "password", createdAt, updatedAt);
        users.add(newUser);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        assertEquals(users, userService.getALlUsers());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Disabled
    void getUserLoggedIn() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.OCTOBER, 22);
        Date dateOfBirth = calendar.getTime();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        User user =  new User("User", dateOfBirth, Gender.LAKI_LAKI, "user@gmail.com", "password", createdAt, updatedAt);
        assertEquals(user, userService.getUserLoggedIn());

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.verify(authentication, Mockito.times(1)).getPrincipal();
    }

    @Test
    void updateProfile() {

    }
}