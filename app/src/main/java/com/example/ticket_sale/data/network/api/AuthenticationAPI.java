package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.network.ApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthenticationAPI {
    @GET("/api/logout")
    Call<ApiResponse<String>> logout();

    @POST("/api/authenticate")
    Call<Map<String,String>> auth(@Body Map<String,String> auth);
//
//        "accessToken":}
    }
