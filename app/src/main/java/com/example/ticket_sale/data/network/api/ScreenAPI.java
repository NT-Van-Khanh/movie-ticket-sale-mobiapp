package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.model.Screen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScreenAPI {

    @GET("/room-service/api/room/{id}")
    Call<ApiResponse<Screen>> getScreenById(@Path("id") String id);

    @GET("/room-service/api/room/all")
    Call<ApiResponse<List<Screen>>> getAllScreens();

    //http://localhost:8888/room-service/api/room/get/room?page=0&limit=10&asc=asc&status=none&orderBy=name
    @GET("/room-service/api/room/get/room")
    Call<ApiResponse<List<Screen>>> getScreensFilter(@Query("page") int page,
                                                     @Query("limit") int limit,
                                                     @Query("asc") String asc,
                                                     @Query("q") String q,
                                                     @Query("status") String status,
                                                     @Query("orderBy") String orderBy);

    //http://localhost:8888/room-service/api/room/get/branch/739baa73-1d11-47c3-a049-c37da5f3da2b
    @GET("/room-service/api/room/get/branch/{branchId}")
    Call<ApiResponse<List<Screen>>> getScreensByBranchId(@Path("branchId") String branchId);


}
