package com.example.ticket_sale.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.AuthRepository;
import com.example.ticket_sale.data.repository.UserRepository;
import com.example.ticket_sale.util.JwtUtil;
import com.example.ticket_sale.util.TokenManager;

import org.json.JSONObject;

import java.util.Map;

public class ChangePasswordViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private LiveData<ApiResponse<Void>> changePasswordResponse;
    private LiveData<Map<String, String>> authResponse;

    public ChangePasswordViewModel() {
        this.authRepository = new AuthRepository(ApiServiceFactory.getAuthenticationAPI());
        this.userRepository = new UserRepository(ApiServiceFactory.getUserAPI());
    }

    private void fetchChangePassword( String password){
        JSONObject payload = JwtUtil.decodeJWT(TokenManager.getToken());
        String id = payload.optString("id");
        this.changePasswordResponse = userRepository.updatePassword(id, password);
    }

    public  LiveData<ApiResponse<Void>> getChangePasswordResponse(String password){
        fetchChangePassword(password);
        return changePasswordResponse;
    }

    private void fetchAuth( String password){
        JSONObject payload = JwtUtil.decodeJWT(TokenManager.getToken());
        JSONObject account = payload.optJSONObject("account");
        if (account != null) {
            String username = account.optString("username");
            this.authResponse = authRepository.auth(username, password);
        } else {
            Log.e("fetchAuth", "AccountDTO information not found in token");
        }
    }

    public LiveData<Map<String, String>> getAuthResponse(String password) {
        fetchAuth(password);
        return authResponse;
    }
}
