package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.model.PaymentMethod;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PaymentAPI {
    String BASE_PATH = "user-service/api/account/bank";

//    @GET(BASE_PATH + "/customer/{customerId}")
//    Call<PaymentMethod> getPaymentByUserId(@Path("customerId") String userId);
//
//    @POST(BASE_PATH + "/add")
//    Call<PaymentMethod> addPaymentMethod(@Body PaymentMethod payment);
//
//    @PUT(BASE_PATH + "/update/{id}")
//    Call<PaymentMethod> updatePayment(@Path("id") String paymentId,
//                                      @Body PaymentMethod payment);
//
//    @PATCH(BASE_PATH + "/active/{id}")
//    Call<PaymentMethod> updatePaymentActive(@Path("id") String paymentId);
}
