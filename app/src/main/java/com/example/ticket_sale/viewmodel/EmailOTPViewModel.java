package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.UserDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.UserRepository;

public class EmailOTPViewModel extends ViewModel {
    private final UserRepository userRepository;
    private LiveData<ApiResponse<Void>> sendOtpToEmailResponse;
    private LiveData<ApiResponse<UserDTO>> authEmailResponse;

    public EmailOTPViewModel() {
        this.userRepository = new UserRepository(ApiServiceFactory.getUserAPI());
    }

    public LiveData<ApiResponse<Void>>  sendOtpToEmail(String email){
        this.sendOtpToEmailResponse = userRepository.sendOTPtoEmail(email);
        return sendOtpToEmailResponse;
    }

    public LiveData<ApiResponse<UserDTO>> authEmailOTP(String email, String otp, String newPassword){
        this.authEmailResponse = userRepository.resetPassword(email, otp, newPassword);
        return authEmailResponse;
    }

//    public void auth(String email, String otp){
//        this.authEmailResponse =userRepository.
//    }
}
