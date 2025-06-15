package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.dto.MovieDTO;
import com.example.ticket_sale.data.dto.PageRequest;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.dto.MovieTypeDTO;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.network.api.MovieAPI;

import java.util.List;

public class MovieRepository {
    private final MovieAPI movieAPI;

    public MovieRepository(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
    }

    public LiveData<ApiResponse<List<MovieDTO>>> getAllMovies(){
        MutableLiveData<ApiResponse<List<MovieDTO>>> responseData = new MutableLiveData<>();
        movieAPI.getAllMovies().enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() +"_getAllMovies"));
        return responseData;
    }

    public LiveData<ApiResponse<MovieDTO>> getMovieById(String id){
        MutableLiveData<ApiResponse<MovieDTO>> responseData = new MutableLiveData<>();
        movieAPI.getMovieById(id).enqueue(new CustomerCallBack<>(responseData, getClass()+ "_getMovieById"));
        return responseData;
    }

    public LiveData<ApiResponse<List<MovieDTO>>> getMoviesBySubId(String subId){
        MutableLiveData<ApiResponse<List<MovieDTO>>> responseData = new MutableLiveData<>();
        movieAPI.getMoviesBySubId(subId).enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getMoviesBySubId"));

        return responseData;
    }

    public LiveData<ApiResponse<List<MovieDTO>>> getMoviesByStatus(String status){
        MutableLiveData<ApiResponse<List<MovieDTO>>> responseData = new MutableLiveData<>();
        movieAPI.getMoviesByStatus(status).enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getMoviesByStatus"));
        return responseData;
    }

    public LiveData<ApiResponse<PageResponse<MovieDTO>>> getMoviesCustom(PageRequest pageRequestDTO){
        MutableLiveData<ApiResponse<PageResponse<MovieDTO>>> responseData = new MutableLiveData<>();
        movieAPI.getMoviesCustom(pageRequestDTO.getPage(),
                            pageRequestDTO.getLimit(),
                            pageRequestDTO.getSearchKey(),
                            pageRequestDTO.getSort(),
                            pageRequestDTO.getStatus(),
                            pageRequestDTO.getOrderBy())
                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getMoviesCustom"));
        return responseData;
    }


    public LiveData<ApiResponse<List<MovieTypeDTO>>> getAllMovieTypes(){
        MutableLiveData<ApiResponse<List<MovieTypeDTO>>> responseData = new MutableLiveData<>();
        movieAPI.getAllMovieTypes().enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getAllMovieTypes"));
        return responseData;
    }

    public LiveData<ApiResponse<MovieTypeDTO>> getMovieTypeById(String id){
        MutableLiveData<ApiResponse<MovieTypeDTO>> responseData = new MutableLiveData<>();
        movieAPI.getMovieTypeById(id).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getMovieTypeById"));
        return responseData;
    }
}
