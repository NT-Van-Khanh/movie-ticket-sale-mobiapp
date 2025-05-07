package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.Theater;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.TheaterRepository;

import java.util.List;

public class TheaterViewModel extends ViewModel {
    private final TheaterRepository theaterRepository;
    private LiveData<ApiResponse<List<Theater>>> theaters;

    public TheaterViewModel() {
        this.theaterRepository = new TheaterRepository(ApiServiceFactory.getTheaterAPI());
        theaters = theaterRepository.getAllTheaters();
    }

    public LiveData<ApiResponse<List<Theater>>> getTheaters() {
        return theaters;
    }
}
