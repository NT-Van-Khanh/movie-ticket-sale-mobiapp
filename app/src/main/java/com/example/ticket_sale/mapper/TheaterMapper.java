package com.example.ticket_sale.mapper;

import com.example.ticket_sale.R;
import com.example.ticket_sale.data.dto.TheaterDTO;
import com.example.ticket_sale.model.Theater;

public class TheaterMapper {
    public static Theater toTheater(TheaterDTO theaterDTO) {
        if (theaterDTO == null) return null;

        Theater movieTheater = new Theater();
        movieTheater.setId(theaterDTO.getId());
        movieTheater.setName(theaterDTO.getName());
        movieTheater.setAddress(theaterDTO.getAddress());
        movieTheater.setImageResId(R.drawable.cinema2);
        return movieTheater;
    }
}
