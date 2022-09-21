package com.barleyyy.orders.entities;

import com.barleyyy.orders.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserTest {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private User user;
    private Date dateOfBirth;
    private LocalDateTime timestamp;
    private LocalDateTime newTimestamp;
    private String userString;

    @BeforeEach
    public void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.OCTOBER, 22);
        dateOfBirth = calendar.getTime();
        timestamp = LocalDateTime.now();
        newTimestamp = LocalDateTime.of(1970, Month.OCTOBER, 22, 10, 45);
        user = new User("Valid User", dateOfBirth, Gender.LAKI_LAKI, "validuser@gmail.com", "password", timestamp, timestamp);
        userString = "User{id=0, fullName='Valid User', dateOfBirth=" + dateOfBirth + ", gender=LAKI_LAKI, email='validuser@gmail.com', password='password', createdAt="+ timestamp + ", updatedAt=" + timestamp + "}";
    }

    @Test
    void getId() {
        assertEquals(0, user.getId());
    }

    @Test
    void getFullName() {
        assertEquals("Valid User", user.getFullName());
    }

    @Test
    void setFullName() {
        user.setFullName("New Name Valid User");
        assertEquals("New Name Valid User", user.getFullName());
    }

    @Test
    void getDateOfBirth() {
        assertEquals(dateOfBirth, user.getDateOfBirth());
    }

    @Test
    void setDateOfBirth() {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(1980, Calendar.JANUARY, 2);
        dateOfBirth = newCalendar.getTime();

        user.setDateOfBirth(dateOfBirth);
        assertEquals(dateOfBirth, user.getDateOfBirth());
    }

    @Test
    void getGender() {
        assertEquals("LAKI_LAKI", user.getGender());
    }

    @Test
    void setGender() {
        user.setGender(Gender.PEREMPUAN);
        assertEquals("PEREMPUAN", user.getGender());
    }

    @Test
    void getEmail() {
        assertEquals("validuser@gmail.com", user.getEmail());
    }

    @Test
    void setEmail() {
        user.setEmail("newemail@gmail.com");
        assertEquals("newemail@gmail.com", user.getEmail());
    }

    @Test
    void getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        System.out.println(authorities);
        assertEquals(authorities, user.getAuthorities());
    }

    @Test
    void getPassword() {
        assertNotNull(user.getPassword());
        assertEquals("password", user.getPassword());
    }

    @Test
    void getUsername() {
        assertEquals("validuser@gmail.com", user.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(user.isEnabled());
    }

    @Test
    void setPassword() {
        user.setPassword(bCryptPasswordEncoder.encode("newpassword"));

        assertNotNull(user.getPassword());
        assertNotEquals("newpassword", user.getPassword());
    }

    @Test
    void getCreatedAt() {
        assertEquals(timestamp, user.getCreatedAt());
    }

    @Test
    void setCreatedAt() {
        user.setCreatedAt(newTimestamp);
        assertEquals(newTimestamp, user.getCreatedAt());
    }

    @Test
    void getUpdatedAt() {
        assertEquals(timestamp, user.getUpdatedAt());
    }

    @Test
    void setUpdatedAt() {
        user.setUpdatedAt(newTimestamp);
        assertEquals(newTimestamp, user.getUpdatedAt());
    }

    @Test
    void testToString() {
        assertEquals(userString, user.toString());
    }
}