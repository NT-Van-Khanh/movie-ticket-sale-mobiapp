package com.example.ticket_sale.data.model;

public class MovieWrapper {
    private String id;
    private Movie filmDto;
    private MovieFormat subDto;

    public MovieWrapper() {
    }

    public Movie getFilmDto() {
        return filmDto;
    }

    public void setFilmDto(Movie filmDto) {
        this.filmDto = filmDto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MovieFormat getSubDto() {
        return subDto;
    }

    public void setSubDto(MovieFormat subDto) {
        this.subDto = subDto;
    }
}
