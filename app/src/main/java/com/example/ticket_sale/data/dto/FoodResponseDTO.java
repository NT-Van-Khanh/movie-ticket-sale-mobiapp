package com.example.ticket_sale.data.dto;

import com.google.gson.annotations.SerializedName;

public class FoodResponseDTO {
    private String id;
    private Long price;
    private String name;

    @SerializedName("image")
    private String imageLink;//link image

    private FoodTypeDTO typeDish;
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

    public FoodTypeDTO getTypeDish() {
        return typeDish;
    }

    public void setTypeDish(FoodTypeDTO typeDish) {
        this.typeDish = typeDish;
    }
}
