package com.example.ticket_sale.data;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL = "http://10.0.2.2:8888/";//10.252.14.131
    private static Retrofit retrofit;
    private static HttpLoggingInterceptor logging;
    private static OkHttpClient client;
    private static AuthInterceptor authInterceptor;

    public RetrofitInstance() {
    }

    public static Retrofit getInstance(){
        if(retrofit == null) {
            logging = new HttpLoggingInterceptor(message -> Log.d("RetrofitNetworkLog", message));
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS); // Có thể chọn BASIC, HEADERS, BODY

            if (authInterceptor == null) {
                authInterceptor = new AuthInterceptor(TokenManager.getToken());
            } else {
                authInterceptor.setToken(TokenManager.getToken());
            }

            client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(authInterceptor)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
    public static void updateToken(String newToken) {
        if (authInterceptor != null) {
            authInterceptor.setToken(newToken); // Cập nhật token trong AuthInterceptor
        }
    }
}

//    public static ApiInterface getApiInterface() {
//        return getInstance().create(ApiInterface.class);
//    }

//
//    public static  synchronized Retrofit getInstance(){// nhiều luồng cùng gọi getInstance(), tránh deadlock
//        if(retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit;
//




//    public static Retrofit getInstance() {
//        if (retrofit != null) return retrofit;
//
//        // Logging interceptor
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        // Header interceptor
//        Interceptor headerInterceptor = chain -> {
//            Request original = chain.request();
//            Request request = original.newBuilder()
//                    .header("Authorization", "Bearer your_token")
//                    .method(original.method(), original.body())
//                    .build();
//            return chain.proceed(request);
//        };
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .addInterceptor(headerInterceptor)
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)  // dùng client tùy chỉnh
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        return retrofit;
//    }
