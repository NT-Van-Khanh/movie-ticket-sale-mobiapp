package com.example.ticket_sale.data.dto;

import com.google.gson.annotations.SerializedName;

public class Slider {
    private String id;
    private String name;

    @SerializedName("image")
    private String imageLink;
    private String responseData;

    private Integer imageResId;

    public Slider() {
    }

    public Integer getImageResId() {
        return imageResId;
    }

    public void setImageResId(Integer imageResId) {
        this.imageResId = imageResId;
    }

    public Slider(String id, String name, String imageLink, Integer imageResId, String responseData) {
        this.id = id;
        this.imageLink = imageLink;
        this.imageResId = imageResId;
        this.name = name;
        this.responseData = responseData;
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
