package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.dto.UserDTO;
import com.example.ticket_sale.data.network.ApiResponse;

import java.util.Map;

import okhttp3.RequestBody;
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

//    Curl
//    curl -X 'POST' \
//            'http://localhost:8888/user-service/api/customer/add' \
//            -H 'accept: */*' \
//            -H 'Content-Type: application/json' \
//            -d '{
//            "name": "Nguyen Khanh",
//            "phoneNumber": "0823321321",
//            "email": "ntdh137@gmail.com",
//            "userName": "vankhanh127",
//            "password": "123123123",
//            "roleId": 100,
//            "status": "ACTIVE",
//            "provider": "LOCAL"
//  Request URL
//  http://localhost:8888/user-service/api/customer/add
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

//    Curl
//
//    curl -X 'POST' \
//            'http://localhost:8888/user-service/api/customer/change/customer/703491/ntvk137%40gmail.com' \
//            -H 'accept: */*' \
//            -H 'Content-Type: application/json' \
//            -d 'string123'
//    Request URL
//    http://localhost:8888/user-service/api/customer/change/customer/703491/nt***37%40gmail.com
    @POST(BASE_PATH + "/change/customer/{opt}/{email}")
    Call<ApiResponse<UserDTO>> resetPasswordByEmail(@Path("opt") String otp,
                                                    @Path("email") String email,
                                                    @Body RequestBody newPassword);
}