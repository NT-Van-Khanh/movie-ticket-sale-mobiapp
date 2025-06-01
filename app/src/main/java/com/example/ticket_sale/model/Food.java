package com.example.ticket_sale.model;

public class Food {
    private String id;
    private String title;
    private Long price;
    private Integer imageResId;
    private String imageLink;
    private String description;
    private FoodType foodType;

    public Food() {

    }

    public Food(String id, String title,  String imageLink,Long price, FoodType foodType) {
        this.foodType = foodType;
        this.id = id;
        this.price = price;
        this.title = title;
        this.imageLink = imageLink;
    }
    public Food(String id, String title,  String imageLink,Long price, String description, FoodType foodType) {
        this.foodType = foodType;
        this.id = id;
        this.price = price;
        this.title = title;
        this.imageLink = imageLink;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
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

    public Integer getImageResId() {
        return imageResId;
    }

    public void setImageResId(Integer imageResId) {
        this.imageResId = imageResId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
