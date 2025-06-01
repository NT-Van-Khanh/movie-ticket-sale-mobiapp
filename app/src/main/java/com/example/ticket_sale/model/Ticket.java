package com.example.ticket_sale.model;

public class Ticket {
    private String id;
    private String name;
    private String conditionUse;
    private Long price;
    private String typeTicket;//
    private Short slot;

    public Ticket() {
    }

    public Ticket(String id, String name, Long price, Short slot, String conditionUse,  String typeTicket) {
        this.conditionUse = conditionUse;
        this.id = id;
        this.name = name;
        this.price = price;
        this.slot = slot;
        this.typeTicket = typeTicket;
    }

    public String getConditionUse() {
        return conditionUse;
    }

    public void setConditionUse(String conditionUse) {
        this.conditionUse = conditionUse;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Short getSlot() {
        return slot;
    }

    public void setSlot(Short slot) {
        this.slot = slot;
    }

    public String getTypeTicket() {
        return typeTicket;
    }

    public void setTypeTicket(String typeTicket) {
        this.typeTicket = typeTicket;
    }
}
