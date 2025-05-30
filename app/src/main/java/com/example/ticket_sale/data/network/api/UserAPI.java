package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.dto.UserDTO;
import com.example.ticket_sale.data.network.ApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPI {
    String BASE_PATH = "user-service/api/customer";

//    @GET(BASE_PATH+"/{id}")
//    Call<ApiResponse<UserDTO>> getUserById(@Path("id") String id,
//                                                @Header("Authorization") String token);//Bearer
    @GET(BASE_PATH+"/{id}")
    Call<ApiResponse<UserDTO>> getUserById(@Path("id") String id);

    @POST(BASE_PATH + "/add")
    Call<ApiResponse<UserDTO>> addUser(@Body Map<String, Object> user);

    @PUT(BASE_PATH + "/update/{id}")
    Call<ApiResponse<UserDTO>> updateUser(@Path("id") String id,
                                          @Body Map<String, Object> user);

    @PUT(BASE_PATH+ "/update/password/{id}")
    Call<ApiResponse<Void>> updatePassword(@Path("id") String id,
                                  @Body Map<String, String>  newPassword);

    @POST(BASE_PATH +"/forget/customer")
    Call<ApiResponse<Void>> sendOTPToEmail(@Query("email") String email);

    @POST(BASE_PATH + "/change/{opt}/{email}")
    Call<ApiResponse<UserDTO>> resetPasswordByEmail(@Path("opt") String otp,
                                                    @Path("email") String email,
                                                    @Body String newPassword);
}