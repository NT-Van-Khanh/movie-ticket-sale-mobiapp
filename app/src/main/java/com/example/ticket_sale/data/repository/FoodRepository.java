package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.dto.FoodTypeDTO;
import com.example.ticket_sale.data.network.api.FoodAPI;
import com.example.ticket_sale.data.dto.FoodDTO;

import java.util.List;

public class FoodRepository {
    private final FoodAPI foodAPI;

    public FoodRepository(FoodAPI foodAPI) {
        this.foodAPI = foodAPI;
    }

    public LiveData<ApiResponse<List<FoodDTO>>> getAllFoods(){
        MutableLiveData<ApiResponse<List<FoodDTO>>> responseData = new MutableLiveData<>();
        foodAPI.getAllFoods().enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getAllFoods"));
        return responseData;
    }

    public LiveData<ApiResponse<FoodDTO>> getFoodById(String foodId){
        MutableLiveData<ApiResponse<FoodDTO>> responseData = new MutableLiveData<>();
        foodAPI.getFoodById(foodId).enqueue(new CustomerCallBack<>(responseData,getClass().getSimpleName() + "_getFoodById"));
        return  responseData;
    }

    public LiveData<ApiResponse<FoodTypeDTO>> getFoodTypeById(String foodTypeId){

        MutableLiveData<ApiResponse<FoodTypeDTO>> responseData = new MutableLiveData<>();
        foodAPI.getFoodTypeById(foodTypeId).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getFoodTypeById"));
        return responseData;

    }

    public  LiveData<ApiResponse<List<FoodTypeDTO>>> getAllFoodTypes(){
        MutableLiveData<ApiResponse<List<FoodTypeDTO>>>  responseData = new MutableLiveData<>();
        foodAPI.getAllFoodTypes().enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getAllFoods"));
        return responseData;
    }
}
