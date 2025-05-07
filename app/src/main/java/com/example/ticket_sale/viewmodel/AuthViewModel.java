package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.AuthRepository;

import java.util.Map;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;

    public AuthViewModel() {
        this.authRepository = new AuthRepository(ApiServiceFactory.getAuthenticationAPI());
    }
    public LiveData<Map<String, String>> auth(String username, String password) {
        return authRepository.auth(username, password);
    }

    public  LiveData<ApiResponse<String>> logout(){
        return authRepository.logout();
    }
}
