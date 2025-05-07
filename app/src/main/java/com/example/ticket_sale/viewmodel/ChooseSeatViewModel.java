package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.ScreenDTO;
import com.example.ticket_sale.data.dto.Seat;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.ScreenRepository;
import com.example.ticket_sale.data.repository.SeatRepository;

import java.util.List;

public class ChooseSeatViewModel extends ViewModel {
    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;
    private LiveData<ApiResponse<ScreenDTO>> screen;
    private LiveData<ApiResponse<List<Seat>>> seatClasses;
    public ChooseSeatViewModel() {
        this.seatRepository = new SeatRepository(ApiServiceFactory.getSeatAPI());
        this.screenRepository = new ScreenRepository(ApiServiceFactory.getScreenAPI());
    }

    public void fetchScreen(String screenId){
        this.screen = screenRepository.getScreenById(screenId);
    }
    public void fetchSeatClasses(){
        this.seatClasses = seatRepository.getAllSeats();
    }
    public LiveData<ApiResponse<ScreenDTO>> getScreen(){
        return screen;
    }
    public LiveData<ApiResponse<List<Seat>>> getAllSeat(){
        return seatClasses;
    }
}
