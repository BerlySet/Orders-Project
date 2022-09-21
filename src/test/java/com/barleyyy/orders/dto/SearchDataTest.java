package com.barleyyy.orders.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchDataTest {

    private SearchData searchData;

    @BeforeEach
    public void setUp() {
        searchData = new SearchData("Key");
    }

    @Test
    void getSearchKey() {
        assertEquals("Key", searchData.getSearchKey());
    }

    @Test
    void setSearchKey() {
        searchData.setSearchKey("New Key");
        assertEquals("New Key", searchData.getSearchKey());
    }
}