package com.example.ticket_sale.model;

public abstract class Item {
    private String id;
    private String title;
    private Long price;
    private Integer imageResId;
    private String imageLink;

    public Item() {
    }

    public Item(String id, String imageLink, Integer imageResId, String name, Long price) {
        this.id = id;
        this.imageLink = imageLink;
        this.imageResId = imageResId;
        this.title = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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
}
