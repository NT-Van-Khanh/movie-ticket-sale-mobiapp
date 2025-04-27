package com.example.ticket_sale.model;

public class Seat {
    private String id;
//    private String type;
    private String name;
    private String status;
    private String description;
    private Integer slot;
    private SeatType seatType;
    public Seat() {
    }

    public Seat(String id, String name, String status, String description, Integer slot, SeatType seatType) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.slot = slot;
        this.seatType = seatType;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }
}
