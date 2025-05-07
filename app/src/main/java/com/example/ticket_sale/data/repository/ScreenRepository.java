package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.dto.ScreenDTO;
import com.example.ticket_sale.data.network.api.ScreenAPI;

import java.util.List;

public class ScreenRepository {
    private final ScreenAPI screenAPI;

    public ScreenRepository(ScreenAPI screenAPI) {
        this.screenAPI = screenAPI;
    }

    public LiveData<ApiResponse<ScreenDTO>> getScreenById(String id) {
        MutableLiveData<ApiResponse<ScreenDTO>> responseData = new MutableLiveData<>();
        screenAPI.getScreenById(id).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getScreenById"));
        return responseData;
    }

    public LiveData<ApiResponse<List<ScreenDTO>>> getAllScreens() {
        MutableLiveData<ApiResponse<List<ScreenDTO>>> responseData = new MutableLiveData<>();
        screenAPI.getAllScreens().enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getAllScreens"));

        return responseData;
    }

    public LiveData<ApiResponse<List<ScreenDTO>>> getScreensByBranchId(String branchId) {
        MutableLiveData<ApiResponse<List<ScreenDTO>>> responseData = new MutableLiveData<>();
        screenAPI.getScreensByBranchId(branchId).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getScreensByBranchId"));
        return responseData;
    }

    public LiveData<ApiResponse<List<ScreenDTO>>> getScreensFilter(int page, int limit,
                                                                   String asc, String q,
                                                                   String status, String orderBy) {
        MutableLiveData<ApiResponse<List<ScreenDTO>>> responseData = new MutableLiveData<>();
        screenAPI.getScreensFilter(page, limit, asc, q, status, orderBy).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getScreensFilter"));
        return responseData;
    }
}
