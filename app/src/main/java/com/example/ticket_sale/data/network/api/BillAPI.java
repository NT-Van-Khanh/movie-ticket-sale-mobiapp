package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.model.Bill;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BillAPI {

    @GET("/payment-service/api/bill/{id}")
    Call<ApiResponse<Bill>> getBillById(@Path("id") String id);

    @POST("payment-service/api/bill/add")
    Call<ApiResponse<Bill>> addBill(@Body Bill bill);

    @POST("payment-service/api/bill/payment")
    Call<ApiResponse<Bill>> payment(@Body Map<String, Object> additionalProp);
//    {
//        "additionalProp1": {},
//        "additionalProp2": {},
//        "additionalProp3": {}
//    }
}
