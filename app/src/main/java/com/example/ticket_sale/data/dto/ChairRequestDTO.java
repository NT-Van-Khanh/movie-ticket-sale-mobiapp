package com.example.ticket_sale.data.dto;

public class ChairRequestDTO {
    private String chairCode;
    private Double price;
    private TicketRequestDTO ticket;

    public ChairRequestDTO() {
    }

    public String getChairCode() {
        return chairCode;
    }

    public void setChairCode(String chairCode) {
        this.chairCode = chairCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TicketRequestDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketRequestDTO ticket) {
        this.ticket = ticket;
    }
}
