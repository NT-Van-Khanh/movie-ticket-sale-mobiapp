package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.dto.BillRequestDTO;
import com.example.ticket_sale.data.dto.BillResponseDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.network.api.BillAPI;

import java.util.Map;

public class BillRepository {
    private final BillAPI billAPI;

    public BillRepository(BillAPI billAPI) {
        this.billAPI = billAPI;
    }

    public LiveData<ApiResponse<BillResponseDTO>> getBillById(String id){
        MutableLiveData<ApiResponse<BillResponseDTO>> responseData = new MutableLiveData<>();
        billAPI.getBillById(id).enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getBillById"));
        return responseData;
    }

    public LiveData<ApiResponse<BillResponseDTO>> addBill(BillRequestDTO bill){
        MutableLiveData<ApiResponse<BillResponseDTO>> responseData = new MutableLiveData<>();
        billAPI.addBill(bill).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_addBill"));
        return responseData;
    }

    public LiveData<ApiResponse<BillResponseDTO>> payment(Map<String, Object> additionalProp){
        MutableLiveData<ApiResponse<BillResponseDTO>> responseData = new MutableLiveData<>();
        billAPI.payment(additionalProp).enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_payment"));
        return responseData;
    }

}
