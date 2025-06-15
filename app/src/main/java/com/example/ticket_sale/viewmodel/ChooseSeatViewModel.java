package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.ChairDTO;
import com.example.ticket_sale.data.dto.ScreenDTO;
import com.example.ticket_sale.data.dto.SeatTypeDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.ScreenRepository;
import com.example.ticket_sale.data.repository.SeatRepository;

import java.util.List;

public class ChooseSeatViewModel extends ViewModel {
    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;
    private LiveData<ApiResponse<ScreenDTO>> screen;
    private LiveData<ApiResponse<List<SeatTypeDTO>>> seatClasses;
    private LiveData<ApiResponse<List<ChairDTO>>> soldSeats;

    public ChooseSeatViewModel() {
        this.seatRepository = new SeatRepository(ApiServiceFactory.getSeatAPI());
        this.screenRepository = new ScreenRepository(ApiServiceFactory.getScreenAPI());
    }

    private void fetchScreen(String screenId){
        this.screen = screenRepository.getScreenById(screenId);
    }

    private void fetchSeatClasses(){
        this.seatClasses = seatRepository.getAllSeats();
    }

    private void fetchSoldSeats(Integer showtimeId){
        this.soldSeats = screenRepository.getSoldSeats(showtimeId);
    }

    public LiveData<ApiResponse<ScreenDTO>> getScreen(String screenId){
        fetchScreen(screenId);
        return screen;
    }

    public LiveData<ApiResponse<List<SeatTypeDTO>>> getSeatClasses(){
        fetchSeatClasses();
        return seatClasses;
    }

    public LiveData<ApiResponse<List<ChairDTO>>> getSoleSeats(Integer showtimeId){
        fetchSoldSeats(showtimeId);
        return soldSeats;
    }
}
