package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.dto.TheaterDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.network.api.TheaterAPI;

import java.util.List;

public class TheaterRepository {
    private final TheaterAPI theaterAPI;

    public TheaterRepository(TheaterAPI theaterAPI) {
        this.theaterAPI = theaterAPI;
    }

    public LiveData<ApiResponse<TheaterDTO>> getTheaterById(String id) {
        MutableLiveData<ApiResponse<TheaterDTO>> responseData = new MutableLiveData<>();
        theaterAPI.getTheaterById(id)
                .enqueue(new CustomerCallBack<>(responseData, "getTheaterById"));

        return responseData;
    }

    public LiveData<ApiResponse<List<TheaterDTO>>> getAllTheaters() {
        MutableLiveData<ApiResponse<List<TheaterDTO>>> responseData = new MutableLiveData<>();
        theaterAPI.getAllTheaters()
                .enqueue(new CustomerCallBack<>(responseData, "getAllTheaters"));

        return responseData;
    }

    public LiveData<ApiResponse<PageResponse<TheaterDTO>>> getTheatersFilter(String page, String limit,
                                                                             String asc, String status,
                                                                             String q, String orderBy ) {
        MutableLiveData<ApiResponse<PageResponse<TheaterDTO>>> responseData = new MutableLiveData<>();
        theaterAPI.getTheatersFilter(page, limit, asc, status, q, orderBy)
                .enqueue(new CustomerCallBack<>(responseData, "getTheatersFilter"));

        return responseData;
    }
}
