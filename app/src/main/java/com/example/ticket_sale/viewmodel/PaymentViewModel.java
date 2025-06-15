package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.BillResponseDTO;
import com.example.ticket_sale.data.dto.PaymentDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.BillRepository;
import com.example.ticket_sale.mapper.OrderMapper;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.User;
import com.example.ticket_sale.util.JwtUtil;
import com.example.ticket_sale.util.TokenManager;

import org.json.JSONObject;

public class PaymentViewModel extends ViewModel {
    private final BillRepository billRepository;
    private LiveData<ApiResponse<BillResponseDTO>> addBill;
    private LiveData<ApiResponse<BillResponseDTO>> paymentResult;


    public PaymentViewModel() {
        this.billRepository = new BillRepository(ApiServiceFactory.getBillAPI());
    }

    private void fetchAddBill(Order order){
        this.addBill = billRepository.addBill(OrderMapper.toBillRequestDTO(order));
    }

    public LiveData<ApiResponse<BillResponseDTO>> getAddBill(Order order) {
        JSONObject payload = JwtUtil.decodeJWT(TokenManager.getToken());
        if (payload != null) {
            User user = new User();
            String name = payload.optString("name");
            String email = payload.optString("email");
            String phone = payload.optString("phone");
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            order.setUser(user);
        }
        fetchAddBill(order);
        return addBill;
    }

    private void fetchPaymentBill(String billId, Long totalPrice, String stripePaymentMethodId){
        PaymentDTO payment = new PaymentDTO( billId, totalPrice, "VND", stripePaymentMethodId);
        this.paymentResult = billRepository.payment(payment);
    }

    public LiveData<ApiResponse<BillResponseDTO>> paymentBill(String billId, Long totalPrice, String stripePaymentMethodId){
        fetchPaymentBill(billId, totalPrice, stripePaymentMethodId);
        return this.paymentResult;
    }
}
