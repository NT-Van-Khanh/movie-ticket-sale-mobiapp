package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.RateDTO;
import com.example.ticket_sale.data.dto.TotalRateDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.repository.RateRepository;
import com.example.ticket_sale.util.TokenManager;

public class MovieDetailViewModel extends ViewModel {
    private final RateRepository rateRepository;
    private LiveData<ApiResponse<RateDTO>> addRateResult;
    private LiveData<ApiResponse<PageResponse<TotalRateDTO>>> ratesOfMovie;
    private LiveData<ApiResponse<Void>> userCommented;

    public MovieDetailViewModel() {
        this.rateRepository = new RateRepository(ApiServiceFactory.getRateAPI());
    }

    private void fetchAddRate(String movieId, int star,  String comment){
        String userId = TokenManager.getUserIdFromToken();
        this.addRateResult = rateRepository.addRate(userId, movieId, star, comment);
    }

    public LiveData<ApiResponse<RateDTO>> addRate(String movieId, int star, String comment) {
        fetchAddRate(movieId, star, comment);
        return addRateResult;
    }


    private void fetchGetRateOfMovie(String movieId){
        this.ratesOfMovie = rateRepository.getRateByMovieId(movieId,0, 10);
    }

    public LiveData<ApiResponse<PageResponse<TotalRateDTO>>> getRatesOfMovie(String movieId) {
        fetchGetRateOfMovie(movieId);
        return ratesOfMovie;
    }

    private void fetchUserCommented(String movieId){
        String userId = TokenManager.getUserIdFromToken();
        this.userCommented = rateRepository.checkRated(movieId, userId);
    }

    public LiveData<ApiResponse<Void>> userCommented(String movieId){
        fetchUserCommented(movieId);
        return userCommented;
    }


}
