package com.example.ticket_sale.data.model;

import com.google.gson.annotations.SerializedName;

public class Food {
//    private String id;
//    private Long price;
//    private Long amount;//1073741824L
//    private FoodDTO dishDto;

    private String id;
    private Long price;
    private String name;

    @SerializedName("image")
    private String imageLink;//link image

    private FoodType typeDish;
    private String active;
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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

    public FoodType getTypeDish() {
        return typeDish;
    }

    public void setTypeDish(FoodType typeDish) {
        this.typeDish = typeDish;
    }
}
