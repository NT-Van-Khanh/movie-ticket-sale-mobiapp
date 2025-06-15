package com.example.ticket_sale.mapper;

import com.example.ticket_sale.R;
import com.example.ticket_sale.data.dto.MovieDTO;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.MovieType;
import com.example.ticket_sale.util.ViLocaleUtil;

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
        movie.setDescription(movieDTO.getDescription());
        movie.setImageLink(movieDTO.getImageLink());
        movie.setTrailerLink(movieDTO.getTrailerLink());

        List<MovieFormat> mvFormats = movieDTO.getFormat().stream()
                                                    .map(MovieFormatMapper::toMovieFormat)
                                                    .collect(Collectors.toList());
        movie.setMovieFormats(mvFormats);
        List<MovieType> mvTypes = movieDTO.getMovieTypes().stream()
                                    .map(MovieTypeMapper::toMovieType)
                            .collect(Collectors.toList());
        movie.setMovieTypes(mvTypes);
        try {
            movie.setDuration(Integer.parseInt(movieDTO.getDuration()));
        } catch (NumberFormatException e) {
            movie.setDuration(120); // hoặc gán giá trị mặc định nếu muốn
        }

        String content = movieDTO.getContent();
        List<String> openingDate = ViLocaleUtil.extractValuesFromHtml(content,"Khởi chiếu:");
        movie.setOpeningDate(openingDate.isEmpty() ? null : openingDate.get(0));
        movie.setRating(null);
        List<String> actors = ViLocaleUtil.extractValuesFromHtml(content,"Diễn viên:");
        movie.setActor(String.join(", ", actors));
        List<String> directors = ViLocaleUtil.extractValuesFromHtml(content,"Đạo diễn:");
        movie.setDirector(String.join(", ", directors));
        return movie;
    }

    private String extractActor(String content){
        String keyword = "Diễn viên:";
        int start = content.indexOf(keyword);
        if (start == -1) return "";

        start += keyword.length();
        int end = content.indexOf("</p>", start);
        if (end == -1) end = content.length();

        return content.substring(start, end).trim();
    }

}
