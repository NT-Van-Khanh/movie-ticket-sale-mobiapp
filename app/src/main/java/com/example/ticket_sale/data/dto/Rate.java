package com.example.ticket_sale.data.dto;

import com.example.ticket_sale.model.Movie;

public class Rate {
    private String id;
    private int star;
    private String content;
    private String timeStamp;
    private User customer;
    private Movie film;
    private String active;

    public Rate() {
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Movie getFilm() {
        return film;
    }

    public void setFilm(Movie film) {
        this.film = film;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
