package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.dto.SeatTypeDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.network.api.SeatAPI;

import java.util.List;

public class SeatRepository {
    private final SeatAPI seatAPI;

    public SeatRepository(SeatAPI seatAPI) {
        this.seatAPI = seatAPI;
    }

    public LiveData<ApiResponse<SeatTypeDTO>> getSeatById(String id) {
        MutableLiveData<ApiResponse<SeatTypeDTO>> responseData = new MutableLiveData<>();
        seatAPI.getSeatById(id).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getSeatById"));
        return responseData;
    }

    public LiveData<ApiResponse<List<SeatTypeDTO>>> getAllSeats() {
        MutableLiveData<ApiResponse<List<SeatTypeDTO>>> responseData = new MutableLiveData<>();
        seatAPI.getAllSeats().enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getAllSeats"));
        return responseData;
    }

    public LiveData<ApiResponse<List<SeatTypeDTO>>> getSeatsFilter(int page, int limit,
                                                                   String asc, String q,
                                                                   String status, String orderBy) {
        MutableLiveData<ApiResponse<List<SeatTypeDTO>>> responseData = new MutableLiveData<>();
        seatAPI.getSeatsFilter(page, limit, asc, q, status, orderBy)
                .enqueue(new CustomerCallBack<>(responseData,
                        getClass().getSimpleName() + "_getSeatFilter"));
        return responseData;
    }
}
