package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.UserDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.AuthRepository;
import com.example.ticket_sale.data.repository.UserRepository;

public class ProfileViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private LiveData<ApiResponse<String>> logoutStatus;
    private LiveData<ApiResponse<UserDTO>> user;

    public ProfileViewModel() {
        this.authRepository = new AuthRepository(ApiServiceFactory.getAuthenticationAPI());
        this.userRepository = new UserRepository(ApiServiceFactory.getUserAPI());
    }

    private void fetchLogout(){
        logoutStatus = authRepository.logout();
    }

    private void fetchUser(String id){
        user = userRepository.getUserById(id);
    }

    public LiveData<ApiResponse<String>>  logout(){
        if(logoutStatus == null) fetchLogout();
        return logoutStatus;
    }

    public LiveData<ApiResponse<UserDTO>> getUser(String id){
        if(user == null) fetchUser(id);
        return user;
    }
}
