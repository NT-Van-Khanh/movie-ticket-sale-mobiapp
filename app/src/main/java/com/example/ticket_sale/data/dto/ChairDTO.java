package com.example.ticket_sale.data.dto;

public class ChairDTO {
    private  String id;
    private String chairCode;
    private Double price;
    private TicketDTO ticket;
    private String active;

    public ChairDTO(String chairCode) {
        this.chairCode = chairCode;
    }

    public String getChairCode() {
        return chairCode;
    }

    public void setChairCode(String chairCode) {
        this.chairCode = chairCode;
    }
}
