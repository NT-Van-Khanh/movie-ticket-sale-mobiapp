package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.UserDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.UserRepository;

public class UpdateUserViewModel extends ViewModel {
    private final  UserRepository userRepository;
    private LiveData<ApiResponse<UserDTO>> updateUser;

    public UpdateUserViewModel() {
        this.userRepository = new UserRepository(ApiServiceFactory.getUserAPI());
    }

    private void fetchUpdateUser(String name, String phoneNumber, String email ){
        this.updateUser = userRepository.updateUser(name, phoneNumber, email);
    }

    public LiveData<ApiResponse<UserDTO>> updateUser(String name, String phoneNumber, String email ) {
        fetchUpdateUser(name, phoneNumber, email);
        return updateUser;
    }

}
