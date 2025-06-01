package com.example.ticket_sale.data.dto;

public class FoodOrderDTO {
    private String id;
    private Long price;
    private Integer amount;
    private FoodResponseDTO dishDto;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public FoodResponseDTO getDishDto() {
        return dishDto;
    }

    public void setDishDto(FoodResponseDTO dishDto) {
        this.dishDto = dishDto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public FoodOrderDTO() {
    }
}
