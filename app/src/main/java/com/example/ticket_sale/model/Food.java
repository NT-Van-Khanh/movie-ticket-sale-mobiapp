package com.example.ticket_sale.model;

public class Food extends Item {

    public Food() {
        super();
    }

    public Food(String id,  String title, String imageLink, Integer imageResId, Long price) {
        super(id, imageLink, imageResId, title, price);
    }

}
