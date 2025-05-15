package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.dto.MovieFormatDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.dto.MovieWrapper;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.network.api.MovieFormatAPI;

import java.util.List;

public class MovieFormatRepository {
    private final MovieFormatAPI movieFormatAPI;

    public MovieFormatRepository(MovieFormatAPI movieFormatAPI) {
        this.movieFormatAPI = movieFormatAPI;
    }

    public LiveData<ApiResponse<List<MovieFormatDTO>>> getAllMovieFormats(){
        MutableLiveData<ApiResponse<List<MovieFormatDTO>>> responseData = new MutableLiveData<>();
        movieFormatAPI.getAllMovieFormats().enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getAllMovieFormats"));
        return responseData;
    }

    public LiveData<ApiResponse<List<MovieWrapper>>> getAllMoviesWithFormats(){
        MutableLiveData<ApiResponse<List<MovieWrapper>>> responseData = new MutableLiveData<>();
        movieFormatAPI.getAllMoviesWithFormats().enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getAllMoviesWithFormats"));
        return responseData;
    }

    public LiveData<ApiResponse<PageResponse<MovieWrapper>>> getMoviesByFormatId(String formatId, String page,
                                                                                 String limit, String asc){
        MutableLiveData<ApiResponse<PageResponse<MovieWrapper>>> responseData = new MutableLiveData<>();
        movieFormatAPI.getMoviesByFormatId(formatId, page, limit, asc).enqueue( new CustomerCallBack<>(responseData,
                getClass().getSimpleName() +"_getMoviesByFormatId"));
        return responseData;
    }
}
