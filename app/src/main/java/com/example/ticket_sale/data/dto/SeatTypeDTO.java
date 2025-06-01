package com.example.ticket_sale.data.dto;

public class SeatTypeDTO {
    private String id;
    private String name;
    private String description;
    private String status;
    private Short slot;

    public SeatTypeDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Short getSlot() {
        return slot;
    }

    public void setSlot(Short slot) {
        this.slot = slot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isValid() {
        return true;
    }
}

