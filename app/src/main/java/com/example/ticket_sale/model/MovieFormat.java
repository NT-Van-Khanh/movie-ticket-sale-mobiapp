package com.example.ticket_sale.model;

import java.util.List;

public class MovieFormat {
    String id;
    String name;
    List<Showtime> showtimes;

    public MovieFormat() {
    }

    public MovieFormat(String id, String name, List<Showtime> showtimes) {
        this.id = id;
        this.name = name;
        this.showtimes = showtimes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
}
