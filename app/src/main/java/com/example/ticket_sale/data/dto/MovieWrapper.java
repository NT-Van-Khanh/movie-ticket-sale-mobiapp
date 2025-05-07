package com.example.ticket_sale.data.dto;

public class MovieWrapper {
    private String id;
    private MovieDTO filmDto;
    private MovieFormat subDto;

    public MovieWrapper() {
    }

    public MovieDTO getFilmDto() {
        return filmDto;
    }

    public void setFilmDto(MovieDTO filmDto) {
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
