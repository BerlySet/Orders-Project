package com.barleyyy.orders.services;

import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.repository.UserRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

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
    private LocalDateTime timestamp;
    private Date dateOfBirth;
    private User validUser;
    private User invalidUser;
    private List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.OCTOBER, 22);
        dateOfBirth = calendar.getTime();
        timestamp = LocalDateTime.now();
        validUser = new User("Valid User", dateOfBirth, Gender.LAKI_LAKI, "validuser@gmail.com", "password", timestamp, timestamp);
        User anotherValidUser = new User("Valid User 2", dateOfBirth, Gender.LAKI_LAKI, "validuser2@gmail.com", "password", timestamp, timestamp);
        invalidUser =  new User("Invalid User", dateOfBirth, Gender.PEREMPUAN, "invaliduser@gmail.com", "password", timestamp, timestamp);
        users.add(validUser);
        users.add(anotherValidUser);
    }

    @Test
    void loadUserByUsernameValidTest() {
        Mockito.when(userRepository.findByEmail("validuser@gmail.com")).thenReturn(Optional.of(validUser));

        assertEquals(validUser, userService.loadUserByUsername("validuser@gmail.com"));
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail("validuser@gmail.com");
    }

    @Test
    void loadUserByUsernameInvalidTest() {
        Mockito.when(userRepository.findByEmail("invaliduser@gmail.com")).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("invaliduser@gmail.com"));
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail("invaliduser@gmail.com");
    }

    @Test
    void registerValidUser() {
        Mockito.when(userRepository.save(validUser)).thenReturn(validUser);
        User user = userService.registerUser(validUser);

        assertEquals("Valid User", user.getFullName());
        assertEquals(dateOfBirth, user.getDateOfBirth());
        assertEquals("LAKI_LAKI", user.getGender());
        assertEquals("validuser@gmail.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertEquals(timestamp, user.getCreatedAt());
        assertEquals(timestamp, user.getUpdatedAt());

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(validUser.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).save(validUser);
    }

    @Test
    void registerInValidUser() {
        Mockito.when(userRepository.findByEmail("invaliduser@gmail.com")).thenReturn(Optional.of(invalidUser));

        assertThrows(RuntimeException.class, () -> userService.registerUser(invalidUser));
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(invalidUser.getEmail());
    }

    @Test
    void getAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(users);

        assertEquals(users, userService.getALlUsers());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Disabled
    @WithMockUser(username = "validuser@gmail.com")
    void getUserLoggedIn() {
        assertEquals(validUser, userService.getUserLoggedIn());

        Mockito.when(authentication.getPrincipal()).thenReturn(validUser);
        Mockito.verify(authentication, Mockito.times(1)).getPrincipal();
    }

    @Test
    void updateProfile() {

    }
}