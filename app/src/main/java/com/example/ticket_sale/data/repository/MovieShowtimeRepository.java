package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.model.MovieShowtime;
import com.example.ticket_sale.data.network.api.MovieShowtimeAPI;

import java.util.List;

public class MovieShowtimeRepository {
    private final MovieShowtimeAPI movieShowtimeAPI;

    public MovieShowtimeRepository(MovieShowtimeAPI movieShowtimeAPI) {
        this.movieShowtimeAPI = movieShowtimeAPI;
    }

    public LiveData<ApiResponse<List<MovieShowtime>>> getShowtimesByRoomId(String roomId, String date){
        MutableLiveData<ApiResponse<List<MovieShowtime>>> responseData = new MutableLiveData<>();
        movieShowtimeAPI.getShowtimesByRoomId(roomId, date)
                .enqueue(new CustomerCallBack<>(responseData,
                        getClass().getSimpleName() + "_getShowtimesByRoomId"));

        return responseData;
    }

    public  LiveData<ApiResponse<List<MovieShowtime>>> getShowtimesByMovieAndTheater(String branchId, String date, String filmId, String subId){
        MutableLiveData<ApiResponse<List<MovieShowtime>>> responseData = new MutableLiveData<>();
        movieShowtimeAPI.getShowtimesByTheaterAndMovie(branchId, date, filmId, subId)
                .enqueue( new CustomerCallBack<>(responseData,
                        getClass().getSimpleName() + "getShowtimesByMovieAndTheater"));
        return responseData;
    }
}
