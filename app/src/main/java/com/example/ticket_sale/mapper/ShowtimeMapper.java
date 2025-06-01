package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.MovieShowtimeDTO;
import com.example.ticket_sale.model.Showtime;

public class ShowtimeMapper {
    public static Showtime toShowtime(MovieShowtimeDTO showtimeDTO){
        Showtime showtime = new Showtime();
        showtime.setId(showtimeDTO.getId());
        showtime.setTimeStart(showtimeDTO.getTimeStart().substring(0,5));
        showtime.setTimeEnd(showtimeDTO.getTimeEnd().substring(0,5));
        showtime.setScreenId(showtimeDTO.getRoomId());
        showtime.setMovieId(showtimeDTO.getSubFilmId());
        showtime.setDate(showtimeDTO.getTimestamp());
        return showtime;
    }
}
