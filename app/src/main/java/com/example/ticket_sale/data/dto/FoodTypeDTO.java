package com.example.ticket_sale.data.dto;

import java.util.List;

public class FoodTypeDTO {
    private String id;
    private String name;
    private List<FoodResponseDTO> dishes;
    private String active;

    public FoodTypeDTO() {
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<FoodResponseDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<FoodResponseDTO> dishes) {
        this.dishes = dishes;
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
}
