package com.example.ticket_sale.model;

public class OrderDisplayItem {
    private String title;
    private Long unitPrice;
    private Integer quantity;

    public OrderDisplayItem() {
    }

    public OrderDisplayItem(String title, Long unitPrice, Integer quantity) {
        this.title = title;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getTotalPrice(){
        return unitPrice * quantity;
    }
}
