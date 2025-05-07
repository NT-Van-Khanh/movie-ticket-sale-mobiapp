package com.example.ticket_sale.util.mapper;

import com.example.ticket_sale.data.dto.MovieDTO;
import com.example.ticket_sale.model.Movie;

public class MovieMapper {

    public static Movie toMovie(MovieDTO movieDTO){
        if (movieDTO == null) return null;

        Movie movie = new Movie();

        movie.setId(movieDTO.getId());
        movie.setTitle(movieDTO.getTitle());
        movie.setAge(movieDTO.getAge());
        movie.setNation(movieDTO.getNation());
        movie.setDescription(movieDTO.getDescription() != null ? movieDTO.getDescription() : movieDTO.getContent());
        movie.setImageLink(movieDTO.getImageLink());
        movie.setTrailerLink(movieDTO.getTrailerLink());
//        movie.setMovieTypes(movieDTO.getMovieTypes());
//        movie.setMovieFormats(movieDTO.getFormat());
        try {
            movie.setDuration(Integer.parseInt(movieDTO.getDuration()));
        } catch (NumberFormatException e) {
            movie.setDuration(null); // hoặc gán giá trị mặc định nếu muốn
        }

        // Các trường không có trong DTO
        movie.setOpeningDate(null);  // Không có trong DTO
        movie.setRating(null);       // Không có trong DTO
        movie.setActor(null);        // Không có trong DTO
        movie.setDirector(null);     // Không có trong DTO
        movie.setImageResId(null);   // Không có trong DTO
        return movie;
    }
}
