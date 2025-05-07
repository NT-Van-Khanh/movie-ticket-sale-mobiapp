package com.example.ticket_sale.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.util.TokenManager;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.network.api.AuthenticationAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final AuthenticationAPI authenticationAPI;

    public AuthRepository(AuthenticationAPI authenticationAPI) {
        this.authenticationAPI = authenticationAPI;
    }

    public LiveData<ApiResponse<String>> logout(){
        MutableLiveData<ApiResponse<String>> responseData = new MutableLiveData<>();
//        authenticationAPI.logout().enqueue(new CustomerCallBack<>(responseData,getClass().getSimpleName()+"_logout"));
        authenticationAPI.logout().enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if(response != null && response.isSuccessful() && response.body() != null){
                    TokenManager.clearToken();
                    responseData.setValue(response.body());
                    Log.d(getClass().getSimpleName() + "_auth", "Success: " + response.body());
                }else {
                    Log.e(getClass().getSimpleName() + "_auth", "Code: " + response.code());
                    try {
                        Log.e(getClass().getSimpleName() + "_auth", "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    responseData.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {

            }
        });
        return responseData;
    }

    public LiveData<Map<String,String>> auth(String username, String password){
        Map<String, String> data = new HashMap<>();
        data.put("username",username);
        data.put("password",password);
        MutableLiveData<Map<String,String>> responseData = new MutableLiveData<>();
//        authenticationAPI.auth(data).enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_auth"));
        authenticationAPI.auth(data).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if(response != null && response.isSuccessful() && response.body() != null){
                    String token = response.body().get("accessToken");
                    if(token != null){
                        TokenManager.saveToken(token);
                        responseData.setValue(response.body());
                        Log.d(getClass().getSimpleName() + "_auth", "Success: " + response.body());
                    } else {
                        Log.w(getClass().getSimpleName() + "_auth", "Token is null");
                    }
                }else {
                    Log.e(getClass().getSimpleName() + "_auth", "Code: " + response.code());
                    try {
                        Log.e(getClass().getSimpleName() + "_auth", "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    responseData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.e(getClass().getSimpleName() + "_auth","Error: " +t.getMessage(), t);
                responseData.setValue(null);
            }
        });

        return responseData;
    }
}
