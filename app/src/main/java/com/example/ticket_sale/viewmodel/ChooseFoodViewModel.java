package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.FoodTypeDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.FoodRepository;

import java.util.List;

public class ChooseFoodViewModel extends ViewModel {
    private final FoodRepository foodRepository;
    private LiveData<ApiResponse<List<FoodTypeDTO>>> foodTypes;

    public ChooseFoodViewModel() {
        this.foodRepository = new FoodRepository(ApiServiceFactory.getFoodAPI());
    }

    private void fetchFoods(){
        foodTypes = foodRepository.getAllFoodTypes();
    }

    public LiveData<ApiResponse<List<FoodTypeDTO>>> getFoods(){
        fetchFoods();
        return foodTypes;
    }
}
