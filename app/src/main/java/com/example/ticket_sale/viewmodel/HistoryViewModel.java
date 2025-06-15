package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.BillResponseDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.BillRepository;
import com.example.ticket_sale.util.JwtUtil;
import com.example.ticket_sale.util.TokenManager;

import org.json.JSONObject;

import java.util.List;

public class HistoryViewModel extends ViewModel {
    private final BillRepository billRepository;
    private LiveData<ApiResponse<List<BillResponseDTO>>> bills;

    public HistoryViewModel() {
        this.billRepository = new BillRepository(ApiServiceFactory.getBillAPI());
    }
    
    private void fetchGetBillsByCustomerId(String customerId){
        this.bills = billRepository.getBillByCustomerId(customerId);
    }

    public LiveData<ApiResponse<List<BillResponseDTO>>> getBillsByCustomer(){
        String token = TokenManager.getToken();
        JSONObject payload = JwtUtil.decodeJWT(token);
        if(payload != null) {
            String id = payload.optString("id");
            fetchGetBillsByCustomerId(id);
        }else{
            fetchGetBillsByCustomerId("");
        }
        return bills;
    }
}
