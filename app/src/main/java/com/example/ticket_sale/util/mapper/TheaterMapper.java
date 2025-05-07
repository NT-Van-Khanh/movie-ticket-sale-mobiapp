package com.example.ticket_sale.util.mapper;

import com.example.ticket_sale.R;
import com.example.ticket_sale.data.dto.Theater;
import com.example.ticket_sale.model.MovieTheater;

public class TheaterMapper {
    public static MovieTheater toTheater(Theater theater) {
        if (theater == null) return null;

        MovieTheater movieTheater = new MovieTheater();
        movieTheater.setId(theater.getId());
        movieTheater.setName(theater.getName());
        movieTheater.setAddress(theater.getAddress());
        movieTheater.setImageResId(R.drawable.cinema2);
        return movieTheater;
    }
}
