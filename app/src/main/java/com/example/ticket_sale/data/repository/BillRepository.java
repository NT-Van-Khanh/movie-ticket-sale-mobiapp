package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.model.Bill;
import com.example.ticket_sale.data.network.api.BillAPI;

import java.util.Map;

public class BillRepository {
    private final BillAPI billAPI;

    public BillRepository(BillAPI billAPI) {
        this.billAPI = billAPI;
    }

    public LiveData<ApiResponse<Bill>> getBillById(String id){
        MutableLiveData<ApiResponse<Bill>> responseData = new MutableLiveData<>();
        billAPI.getBillById(id).enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getBillById"));
        return responseData;
    }

    public LiveData<ApiResponse<Bill>> addBill(Bill bill){
        MutableLiveData<ApiResponse<Bill>> responseData = new MutableLiveData<>();
        billAPI.addBill(bill).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName() + "_addBill"));
        return responseData;
    }

    public LiveData<ApiResponse<Bill>> payment(Map<String, Object> additionalProp){
        MutableLiveData<ApiResponse<Bill>> responseData = new MutableLiveData<>();
        billAPI.payment(additionalProp).enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_payment"));
        return responseData;
    }

}
