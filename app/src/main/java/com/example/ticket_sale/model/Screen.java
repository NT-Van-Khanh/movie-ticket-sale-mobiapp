package com.example.ticket_sale.model;

public class Screen {
    private String id;
    private String name;
    private MovieTheater theater;

    public Screen() {
    }

    public Screen(String id, String name, MovieTheater theater) {
        this.id = id;
        this.name = name;
        this.theater = theater;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MovieTheater getTheater() {
        return theater;
    }

    public void setTheater(MovieTheater theater) {
        this.theater = theater;
    }
}
