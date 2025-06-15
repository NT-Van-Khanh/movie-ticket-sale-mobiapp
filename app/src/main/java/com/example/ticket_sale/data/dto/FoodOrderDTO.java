package com.example.ticket_sale.data.dto;

public class FoodOrderDTO {
    private Long price;
    private Integer amount;
    private FoodRequestDTO dishDto;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public FoodRequestDTO getDishDto() {
        return dishDto;
    }

    public void setDishDto(FoodRequestDTO dishDto) {
        this.dishDto = dishDto;
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
