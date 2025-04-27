package com.example.ticket_sale.data.model;

import com.google.gson.annotations.SerializedName;

public class Slider {
    private String id;
    private String name;

    @SerializedName("image")
    private String imageLink;
    private String responseData;

    public Slider() {
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

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
