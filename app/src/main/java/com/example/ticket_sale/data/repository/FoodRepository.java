package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.model.FoodType;
import com.example.ticket_sale.data.network.api.FoodAPI;
import com.example.ticket_sale.data.model.Food;

import java.util.List;

public class FoodRepository {
    private final FoodAPI foodAPI;

    public FoodRepository(FoodAPI foodAPI) {
        this.foodAPI = foodAPI;
    }

    public LiveData<ApiResponse<List<Food>>> getAllFoods(){
        MutableLiveData<ApiResponse<List<Food>>> responseData = new MutableLiveData<>();
        foodAPI.getAllFoods().enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getAllFoods"));
        return responseData;
    }

    public LiveData<ApiResponse<Food>> getFoodById(String foodId){
        MutableLiveData<ApiResponse<Food>> responseData = new MutableLiveData<>();
        foodAPI.getFoodById(foodId).enqueue(new CustomerCallBack<>(responseData,getClass().getSimpleName() + "_getFoodById"));
        return  responseData;
    }

    public LiveData<ApiResponse<FoodType>> getFoodTypeById(String foodTypeId){

        MutableLiveData<ApiResponse<FoodType>> responseData = new MutableLiveData<>();
        foodAPI.getFoodTypeById(foodTypeId).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getFoodTypeById"));
        return responseData;

    }

    public  LiveData<ApiResponse<List<FoodType>>> getAllFoodTypes(){
        MutableLiveData<ApiResponse<List<FoodType>>>  responseData = new MutableLiveData<>();
        foodAPI.getAllFoodTypes().enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_getAllFoods"));
        return responseData;
    }
}
