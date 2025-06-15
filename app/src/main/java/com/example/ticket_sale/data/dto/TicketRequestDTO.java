package com.example.ticket_sale.data.dto;

public class TicketRequestDTO {
    private String id;

    public TicketRequestDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
