package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.dto.TotalRateDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.dto.RateDTO;
import com.example.ticket_sale.data.network.api.RateAPI;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
//        rateAPI.checkRated(movieId, userId)
//                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_checkRated"));
        rateAPI.checkRated(movieId, userId).enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                responseData.setValue(new ApiResponse<>(response.code(), response.message())); // 200, 404, 500,...
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                responseData.setValue(new ApiResponse<>(-1,t.getMessage())); // Lỗi kết nối
            }
        });
        return responseData;
    }

    public LiveData<ApiResponse<PageResponse<RateDTO>>> getCustomerRatesWithFilter(int page, int limit, String q,
                                                                                   String asc, String orderBy, String status) {
        MutableLiveData<ApiResponse<PageResponse<RateDTO>>> responseData = new MutableLiveData<>();
        rateAPI.getCustomerRatesWithFilter(page, limit, q, asc, orderBy, status)
                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getCustomerRates"));
        return responseData;
    }

    public LiveData<ApiResponse<RateDTO>> addRate(String userId,
                                                  String movieId,
                                                  Integer star,
                                                  String content) {
        Map<String, Object> rate = new HashMap<>();
        rate.put("star",star);
        rate.put("content",content);
        rate.put("active","ACTIVE");
        rate.put("customerId",userId);// sẽ bỏ trong t.lai
        rate.put("filmId",movieId);// sẽ bỏ trong t.lai

        MutableLiveData<ApiResponse<RateDTO>> responseData = new MutableLiveData<>();
        rateAPI.addRate(movieId, userId, rate)
                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_addRate"));
        return responseData;
    }
}
