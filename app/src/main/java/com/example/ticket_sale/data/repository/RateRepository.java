package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.dto.TotalRateDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.dto.Rate;
import com.example.ticket_sale.data.network.api.RateAPI;

import java.util.HashMap;
import java.util.Map;

public class RateRepository {
    private final RateAPI rateAPI;

    public RateRepository(RateAPI rateAPI) {
        this.rateAPI = rateAPI;
    }

    public LiveData<ApiResponse<PageResponse<TotalRateDTO>>> getRateByMovieId(String movieId, int page, int limit){
        MutableLiveData<ApiResponse<PageResponse<TotalRateDTO>>> responseData = new MutableLiveData<>();

        rateAPI.getRateByMovieId(movieId, page, limit, "asc", "timeStamp","ACTIVE")
                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName()+"_getRateByMovieId"));
        return responseData;
    }

    public LiveData<ApiResponse<Void>> checkRated(String movieId, String userId) {
        MutableLiveData<ApiResponse<Void>> responseData = new MutableLiveData<>();
        rateAPI.checkRated(movieId, userId)
                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_checkRated"));
        return responseData;
    }

    public LiveData<ApiResponse<PageResponse<Rate>>> getCustomerRatesWithFilter(int page, int limit, String q,
                                                                 String asc, String orderBy, String status) {
        MutableLiveData<ApiResponse<PageResponse<Rate>>> responseData = new MutableLiveData<>();
        rateAPI.getCustomerRatesWithFilter(page, limit, q, asc, orderBy, status)
                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getCustomerRates"));
        return responseData;
    }

    public LiveData<ApiResponse<Rate>> addRate(String userId,
                                               String movieId,
                                               Integer star,
                                               String content) {
        Map<String, Object> rate = new HashMap<>();
        rate.put("star",star);
        rate.put("content",content);
        rate.put("active","ACTIVE");
        rate.put("customerId",userId);// sẽ bỏ trong t.lai
        rate.put("filmId",movieId);// sẽ bỏ trong t.lai

        MutableLiveData<ApiResponse<Rate>> responseData = new MutableLiveData<>();
        rateAPI.addRate(movieId, userId, rate)
                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_addRate"));
        return responseData;
    }
}
