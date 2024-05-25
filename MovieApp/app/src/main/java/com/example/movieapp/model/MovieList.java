package com.example.movieapp.model;

import java.util.List;

public class MovieList {
    private String status;
    private List<Item> items;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
