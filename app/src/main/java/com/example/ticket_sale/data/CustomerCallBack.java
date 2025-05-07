package com.example.ticket_sale.data;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerCallBack<T> implements Callback<T> {
    private final MutableLiveData<T> liveData;
    private final String logTag;

    public CustomerCallBack(MutableLiveData<T> liveData, String logTag) {
        this.liveData = liveData;
        this.logTag = logTag;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            T body = response.body();


            liveData.setValue(body);
            Log.d(logTag, "Success: " + response.body());
        } else {
            Log.e(logTag, "Code: " + response.code());
            try (ResponseBody errorBody = response.errorBody()) {
                if (errorBody != null) {
                    Log.e(logTag, "Error body: " + errorBody.string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            liveData.setValue(null);
        }
//        liveData.setValue(response.isSuccessful() ? response.body() : null);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e(logTag, "Error: " +t.getMessage(), t);
        liveData.setValue(null);
    }
}
//            if (body instanceof ApiResponse) {
//                ApiResponse<?> apiResponse = (ApiResponse<?>) body;
//                Log.e(logTag, "Success message: " + apiResponse.getMessage());
//                Object data = apiResponse.getData();
//                liveData.setValue((T) data); // nếu muốn chỉ trả về data
//            } else {
//                liveData.setValue(body);
//            }