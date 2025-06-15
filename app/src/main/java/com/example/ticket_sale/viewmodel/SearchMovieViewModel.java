package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.MovieDTO;
import com.example.ticket_sale.data.dto.PageRequest;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.repository.MovieRepository;

import java.util.List;

public class SearchMovieViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private LiveData<ApiResponse<PageResponse<MovieDTO>>> searchMovies;

    public SearchMovieViewModel() {
        this.movieRepository = new MovieRepository(ApiServiceFactory.getMovieAPI());
    }

    private void fetchSearchMovies(String page, String searchKeyword){
        PageRequest pageRequest = new PageRequest(page, "6", searchKeyword, "asc","none","name");
        this.searchMovies = movieRepository.getMoviesCustom(pageRequest);
    }

    public LiveData<ApiResponse<PageResponse<MovieDTO>>> searchMovies(String page, String searchKeyword){
        fetchSearchMovies(page, searchKeyword);
        return searchMovies;
    }
}
