package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.UserDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.UserRepository;
import com.example.ticket_sale.model.User;

public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository;
    private LiveData<ApiResponse<UserDTO>> addUserResponse;

    public RegisterViewModel() {
        this.userRepository = new UserRepository(ApiServiceFactory.getUserAPI());
    }

    private LiveData<ApiResponse<UserDTO>>  addUser(User user, String password ){
        this.addUserResponse = userRepository.addUser(user.getName(), user.getPhone(),
                                user.getEmail(), user.getUsername(), password);
        return addUserResponse;
    }
}
