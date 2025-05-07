package com.example.ticket_sale.util.mapper;

import com.example.ticket_sale.data.dto.MovieShowtimeDTO;
import com.example.ticket_sale.model.Showtime;

public class ShowtimeMapper {
    public static Showtime toShowtime(MovieShowtimeDTO showtimeDTO){
        Showtime showtime = new Showtime();
        showtime.setId(String.valueOf(showtimeDTO.getId()));
        showtime.setTimeStart(showtimeDTO.getTimeStart());
        showtime.setTimeEnd(showtimeDTO.getTimeEnd());
        showtime.setScreenId(showtimeDTO.getRoomId());
        showtime.setMovieId(showtimeDTO.getSubFilmId());
        showtime.setDate(showtimeDTO.getTimestamp());
        return showtime;
    }
}
