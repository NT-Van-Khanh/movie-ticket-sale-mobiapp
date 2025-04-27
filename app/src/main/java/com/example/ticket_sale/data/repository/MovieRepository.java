package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.model.MovieType;
import com.example.ticket_sale.data.network.api.MovieAPI;
import com.example.ticket_sale.data.model.Movie;

import java.util.List;

public class MovieRepository {
    private final MovieAPI movieAPI;

    public MovieRepository(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
    }

    public LiveData<ApiResponse<List<Movie>>> getAllMovies(){
        MutableLiveData<ApiResponse<List<Movie>>> responseData = new MutableLiveData<>();
        movieAPI.getAllMovies().enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() +"_getAllMovies"));
        return responseData;
    }

    public LiveData<ApiResponse<Movie>> getMovieById(String id){
        MutableLiveData<ApiResponse<Movie>> responseData = new MutableLiveData<>();
        movieAPI.getMovieById(id).enqueue(new CustomerCallBack<>(responseData, getClass()+ "_getMovieById"));
        return responseData;
    }

    public LiveData<ApiResponse<List<Movie>>> getMoviesBySubId(String subId){
        MutableLiveData<ApiResponse<List<Movie>>> responseData = new MutableLiveData<>();
        movieAPI.getMoviesBySubId(subId).enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getMoviesBySubId"));

        return responseData;
    }

    public LiveData<ApiResponse<List<Movie>>> getMoviesByStatus(String status){
        MutableLiveData<ApiResponse<List<Movie>>> responseData = new MutableLiveData<>();
        movieAPI.getMoviesByStatus(status).enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getMoviesByStatus"));
        return responseData;
    }




    public LiveData<ApiResponse<List<MovieType>>> getAllMovieTypes(){
        MutableLiveData<ApiResponse<List<MovieType>>> responseData = new MutableLiveData<>();
        movieAPI.getAllMovieTypes().enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getAllMovieTypes"));
        return responseData;
    }

    public LiveData<ApiResponse<MovieType>> getMovieTypeById(String id){
        MutableLiveData<ApiResponse<MovieType>> responseData = new MutableLiveData<>();
        movieAPI.getMovieTypeById(id).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getMovieTypeById"));
        return responseData;
    }
}
