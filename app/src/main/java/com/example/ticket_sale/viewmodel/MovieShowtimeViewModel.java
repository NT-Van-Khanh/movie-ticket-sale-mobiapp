package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.MovieShowtimeDTO;
import com.example.ticket_sale.data.dto.TheaterDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.MovieShowtimeRepository;
import com.example.ticket_sale.data.repository.TheaterRepository;

import java.util.List;

public class MovieShowtimeViewModel extends ViewModel {
    private final TheaterRepository theaterRepository;
    private final MovieShowtimeRepository showtimeRepository;

    private LiveData<ApiResponse<List<TheaterDTO>>> theaters;
    private LiveData<ApiResponse<List<MovieShowtimeDTO>>> showtimes;
    public MovieShowtimeViewModel() {
        this.theaterRepository = new TheaterRepository(ApiServiceFactory.getTheaterAPI());
        this.showtimeRepository = new MovieShowtimeRepository(ApiServiceFactory.getMovieShowtimeAPI());
    }

    private void fetchTheaters(){
        this.theaters = theaterRepository.getAllTheaters();
    }

    private void fetchShowtimes(String brandId, String date, String movieId, String movieFormatId){
        this.showtimes = showtimeRepository.getShowtimesByMovieAndTheater(brandId, date, movieId, movieFormatId);
    }

    public LiveData<ApiResponse<List<TheaterDTO>>> getTheaters(){
        if(theaters == null) fetchTheaters();
        return theaters;
    }

    public LiveData<ApiResponse<List<MovieShowtimeDTO>>> getShowtimes(String brandId, String date,
                                                                      String movieId, String movieFormatId){
        if(showtimes == null) fetchShowtimes(brandId, date, movieId, movieFormatId);
        return showtimes;
    }
}
