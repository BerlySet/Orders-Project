package com.barleyyy.orders.services;

import com.barleyyy.orders.dto.UpdateProfileUserData;
import com.barleyyy.orders.entities.User;
import com.barleyyy.orders.repository.UserRepository;
import com.barleyyy.orders.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Mockito.when(userRepository.findByEmail("invaliduser@gmail.com")).thenThrow(new UsernameNotFoundException("user with email invaliduser@gmail.com not found"));

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("invaliduser@gmail.com"));
        assertEquals("user with email invaliduser@gmail.com not found", exception.getMessage());
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
    void getUserLoggedIn() {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(validUser);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertEquals(validUser, userService.getUserLoggedIn());

        Mockito.verify(authentication, Mockito.times(1)).getPrincipal();
    }

    @Test
    void updateProfile() {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(validUser);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UpdateProfileUserData userData = new UpdateProfileUserData("Updated Valid User", dateOfBirth, Gender.LAKI_LAKI);
        User updatedUser = validUser;
        updatedUser.setFullName(userData.getFullName());
        updatedUser.setDateOfBirth(userData.getDateOfBirth());
        updatedUser.setGender(userData.getGender());

        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        assertEquals(updatedUser, userService.updateProfile(userData));
        Mockito.verify(authentication, Mockito.times(1)).getPrincipal();
    }
}