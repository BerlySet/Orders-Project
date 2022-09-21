package com.barleyyy.orders.dto;

public class SearchData {
    public SearchData(String searchKey) {
        this.searchKey = searchKey;
    }

    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
