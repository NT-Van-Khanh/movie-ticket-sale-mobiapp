package com.example.ticket_sale.mapper;

import android.os.Parcelable;

import com.example.ticket_sale.data.dto.MovieFormatDTO;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.Showtime;

import java.util.List;

public class MovieFormatMapper {
    public static MovieFormat toMovieFormat(MovieFormatDTO dto){
        if(dto == null) return null;
        MovieFormat mFormat = new MovieFormat();
        mFormat.setId(dto.getId());
        mFormat.setName(dto.getName());
        return mFormat;
    }
}
