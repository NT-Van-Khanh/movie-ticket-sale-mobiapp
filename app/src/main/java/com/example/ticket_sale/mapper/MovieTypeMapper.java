package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.MovieTypeDTO;
import com.example.ticket_sale.model.MovieType;

public class MovieTypeMapper {
    public static MovieType toMovieType(MovieTypeDTO typeDTO){
        return  new MovieType(typeDTO.getId(),typeDTO.getName());
    }
}
