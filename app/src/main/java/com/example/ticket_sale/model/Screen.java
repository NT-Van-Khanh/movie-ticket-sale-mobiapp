package com.example.ticket_sale.model;

public class Screen {
    private String id;
    private String name;
    private Theater theater;
    private Integer[][] seatPositions;
//    private SeatPosition[][] seats;
    public Screen() {
    }

    public Screen(String id, String name, Theater theater) {
        this.id = id;
        this.name = name;
        this.theater = theater;
    }

    public Integer[][] getSeatPositions() {
        return seatPositions;
    }

    public void setSeatPositions(Integer[][] seatPositions) {
        this.seatPositions = seatPositions;
    }
//    public SeatPosition[][] getSeats() {
//        return seats;
//    }
//
//    public void setSeats(SeatPosition[][] seats) {
//        this.seats = seats;
//    }

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
