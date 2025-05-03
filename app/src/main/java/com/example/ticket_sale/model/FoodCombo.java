package com.example.ticket_sale.model;

import java.util.List;

public class FoodCombo extends Item {
    private String description;
    private List<Food> foods;

    public FoodCombo() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FoodCombo(String id, String name,  String description, String imageLink, Integer imageResId, Long price) {
        super(id, imageLink, imageResId, name, price);
        this.description = description;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
