package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.model.Slider;
import com.example.ticket_sale.data.network.api.SliderAPI;

import java.util.List;

public class SliderRepository {
    private final SliderAPI sliderAPI;

    public SliderRepository(SliderAPI sliderAPI) {
        this.sliderAPI = sliderAPI;
    }

    public LiveData<ApiResponse<List<Slider>>> getAllSliders(){
        MutableLiveData<ApiResponse<List<Slider>>> responseData = new MutableLiveData<>();
        sliderAPI.getAllSliders()
                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getAllSliders"));

        return responseData;
    }
}
