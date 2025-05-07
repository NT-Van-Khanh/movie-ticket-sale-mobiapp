package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.dto.User;
import com.example.ticket_sale.data.network.api.UserAPI;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final UserAPI userApi;

    public UserRepository(UserAPI userApi) {
        this.userApi = userApi;
    }

    public LiveData<ApiResponse<User>> getUserById(String id){
        MutableLiveData<ApiResponse<User>> userData = new MutableLiveData<>();
        userApi.getUserById(id).enqueue(new CustomerCallBack<>(userData,
                getClass().getSimpleName()+"_getUserById"));
        return userData;
    }

    public LiveData<ApiResponse<User>> addUser(String name, String phoneNumber,
                                                       String email, String userName, String password){
        Map<String,Object> data = new HashMap<>();
        data.put("name",name);
        data.put("phoneNumber", phoneNumber);
        data.put("email", email);
        data.put("userName", userName);
        data.put("password", password);
        data.put("roleId",1073741824L);
        data.put("status","DELETE");
        MutableLiveData<ApiResponse<User>> responseData = new MutableLiveData<>();
        userApi.addUser(data).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName()+"_addUser"));
        return responseData;
    }

    public LiveData<ApiResponse<User>> updateUser(String id, String name,
                                                  String phoneNumber, String email){
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("phoneNumber",phoneNumber);
        data.put("email", email);
        MutableLiveData<ApiResponse<User>> responseData = new MutableLiveData<>();
        userApi.updateUser(id, data).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName()+"_updateUser"));
        return responseData;
    }

    public LiveData<ApiResponse<User>> resetPassword(String email, String otp, String newPassword){
        MutableLiveData<ApiResponse<User>> responseData = new MutableLiveData<>();
        userApi.resetPasswordByEmail(otp,email, newPassword).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName()+"_authEmail"));
        return responseData;
    }

    public LiveData<ApiResponse<Void>> sendOTPtoEmail(String email){
        MutableLiveData<ApiResponse<Void>> responseData = new MutableLiveData<>();
        userApi.sendOTPToEmail(email).enqueue(new CustomerCallBack<>(responseData,
                getClass().getSimpleName()+"_resetPassword"));
        return responseData;
    }

    public LiveData<ApiResponse<Void>> updatePassword(String id, String password){
        Map<String, String> data = new HashMap<>();
        data.put("passwordNew",password);
        MutableLiveData<ApiResponse<Void>> userData = new MutableLiveData<>();
        userApi.updatePassword(id, data).enqueue(new CustomerCallBack<>(userData,
                getClass().getSimpleName()+"_updatePassword"));
        return userData;
    }
}
//    public LiveData<User> getUserById(String id){
//        MutableLiveData<User> userData = new MutableLiveData<>();
//
//        userApi.getUserById(id).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if(response.isSuccessful()) {
//                    userData.setValue(response.body());
//                }else {
//                    userData.setValue(null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.e("UserRepository", "Error fetching user: ", t);
//                userData.setValue(null);
//            }
//        });
//
//        return userData;
//    }

//    public LiveData<User> addUser(User user){
//        MutableLiveData<User> userData = new MutableLiveData<>();
//        userApi.addUser(user).enqueue(new CustomerCallBack<>(userData,"UserRepository_addUser"));
//        return userData;
//    }

//    private <T> Callback<T> createCallback(MutableLiveData<T> liveData) {
//        return new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                liveData.setValue(response.isSuccessful() ? response.body() : null);
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                Log.e("API_ERROR", "Error: ", t);
//                liveData.setValue(null);
//            }
//        };
//    }

