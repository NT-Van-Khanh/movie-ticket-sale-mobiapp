package com.example.ticket_sale.model;

public class Screen {
    private String id;
    private String name;
    private Theater theater;
    private Seat[][] seats;
    public Screen() {
    }

    public Screen(String id, String name, Theater theater) {
        this.id = id;
        this.name = name;
        this.theater = theater;
    }

    public Screen(String id, String name, Theater theater, Seat[][] seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
        this.theater = theater;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public void setSeats(Seat[][] seats) {
        this.seats = seats;
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

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }
}
