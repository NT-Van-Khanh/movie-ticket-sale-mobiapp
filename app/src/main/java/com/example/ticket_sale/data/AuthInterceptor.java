package com.example.ticket_sale.data;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor  implements Interceptor {
    private String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (token != null && !token.isEmpty()) {
            Request newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token)  // Thêm token vào header
                    .build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(originalRequest);
    }

    public void setToken(String token) {
        this.token = token;
    }
}
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request originalRequest = chain.request();
//            if (token != null && !token.isEmpty() && !originalRequest.url().toString().contains("/auth")) {
//                Request newRequest = originalRequest.newBuilder()
//                        .header("Authorization", "Bearer " + token)
//                        .build();
//                return chain.proceed(newRequest);
//            }
//            return chain.proceed(originalRequest);
//        }
