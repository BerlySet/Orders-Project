package com.barleyyy.orders.dto;

import com.barleyyy.orders.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateProfileUserDataTest {

    private UpdateProfileUserData updateProfileUserData;
    private Date dateOfBirth;
    private Calendar calendar = Calendar.getInstance();

    @BeforeEach
    public void setUp() {
        calendar.set(2000, Calendar.OCTOBER, 22);
        dateOfBirth = calendar.getTime();
        updateProfileUserData = new UpdateProfileUserData("Valid User", dateOfBirth, Gender.LAKI_LAKI);
    }

    @Test
    void getFullName() {
        assertEquals("Valid User", updateProfileUserData.getFullName());
    }

    @Test
    void setFullName() {
        updateProfileUserData.setFullName("New Valid User");
        assertEquals("New Valid User", updateProfileUserData.getFullName());
    }

    @Test
    void getDateOfBirth() {
        assertEquals(dateOfBirth, updateProfileUserData.getDateOfBirth());
    }

    @Test
    void setDateOfBirth() {
        calendar.set(1980, Calendar.JANUARY, 2);
        Date newDateOfBirth = calendar.getTime();

        updateProfileUserData.setDateOfBirth(newDateOfBirth);
        assertEquals(newDateOfBirth, updateProfileUserData.getDateOfBirth());
        assertNotEquals(dateOfBirth, updateProfileUserData.getDateOfBirth());
    }

    @Test
    void getGender() {
        assertEquals(Gender.LAKI_LAKI, updateProfileUserData.getGender());
    }

    @Test
    void setGender() {
        updateProfileUserData.setGender(Gender.PEREMPUAN);
        assertEquals(Gender.PEREMPUAN, updateProfileUserData.getGender());
    }
}