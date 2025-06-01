package com.example.ticket_sale.model;

import java.util.List;

public class FoodType {
    private String id;
    private String name;
    private List<Food> foods;

    public FoodType() {
    }

    public FoodType(String id, String name, List<Food> foods) {
        this.foods = foods;
        this.id = id;
        this.name = name;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
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
