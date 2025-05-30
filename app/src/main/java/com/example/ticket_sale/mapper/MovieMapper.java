package com.example.ticket_sale.mapper;

import com.example.ticket_sale.R;
import com.example.ticket_sale.data.dto.MovieDTO;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.MovieFormat;

import java.util.List;
import java.util.stream.Collectors;

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
        List<MovieFormat> mvFormats = movieDTO.getFormat().stream()
                                                    .map(MovieFormatMapper::toMovieFormat)
                                                    .collect(Collectors.toList());
        movie.setMovieFormats(mvFormats);
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
        movie.setImageResId(R.drawable.mv_nhoc_quay);   // Không có trong DTO
        return movie;
    }
}
