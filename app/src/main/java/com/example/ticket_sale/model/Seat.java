package com.example.ticket_sale.model;

public class Seat {
    private String id;
    private String title;
    private Long price;
    private String status;
    private String description;
    private Integer slot;
    private SeatType seatType;
    public Seat() {
    }

    public Seat(String id, String title, String status, String description, Integer slot, SeatType seatType, Long price) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.description = description;
        this.slot = slot;
        this.seatType = seatType;
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
