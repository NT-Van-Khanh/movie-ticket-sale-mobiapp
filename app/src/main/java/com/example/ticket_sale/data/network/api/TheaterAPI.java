package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.dto.Theater;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheaterAPI {
    String BASE_PATH = "room-service/api/branch";
    //http://localhost:8888/room-service/api/branch/739baa73-1d11-47c3-a049-c37da5f3da2b
    @GET(BASE_PATH + "/{id}")
    Call<ApiResponse<Theater>> getTheaterById(@Path("id") String id);

    @GET(BASE_PATH + "/all")
    Call<ApiResponse<List<Theater>>> getAllTheaters();

    //http://localhost:8888/room-service/api/branch/get/branch?page=0&limit=10&asc=asc&status=none&orderBy=nameBranch
    @GET(BASE_PATH + "/get/branch")
    Call<ApiResponse<PageResponse<Theater>>> getTheatersFilter(@Query("page") String page,
                                                               @Query("limit") String limit,
                                                               @Query("asc") String asc,
                                                               @Query("status") String status,
                                                               @Query("q") String q,
                                                               @Query("orderBy") String orderBy);
}
