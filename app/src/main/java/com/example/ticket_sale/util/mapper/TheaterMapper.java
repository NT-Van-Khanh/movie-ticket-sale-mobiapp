package com.example.ticket_sale.util.mapper;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Theater;

public class TheaterMapper {
    public static Theater toTheater(com.example.ticket_sale.data.dto.Theater theater) {
        if (theater == null) return null;

        Theater movieTheater = new Theater();
        movieTheater.setId(theater.getId());
        movieTheater.setName(theater.getName());
        movieTheater.setAddress(theater.getAddress());
        movieTheater.setImageResId(R.drawable.cinema2);
        return movieTheater;
    }
}
