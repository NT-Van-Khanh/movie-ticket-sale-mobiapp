package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.dto.MovieShowtimeDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieShowtimeAPI {
    String BASE_PATH = "filmshowtime-service/api/filmshowtime";

    @GET(BASE_PATH + "/{roomId}/all")
    Call<ApiResponse<List<MovieShowtimeDTO>>> getShowtimesByRoomId(@Path("roomId") String roomId,
                                                                   @Query("date") String date);
    //http://localhost:8888/filmshowtime-service/api/filmshowtime/1/all?date=2025-03-17

    @GET(BASE_PATH + "/get/{branchId}/{time}/{filmId}/{subId}")
    Call<ApiResponse<List<MovieShowtimeDTO>>> getShowtimesByTheaterAndMovie(
            @Path("branchId") String branchId,
            @Path("time") String date,
            @Path("filmId") String filmId,
            @Path("subId") String subId);
    //http://localhost:8888/filmshowtime-service/api/filmshowtime/get/739baa73-1d11-47c3-a049-c37da5f3da2b/2025-03-26/1/2
    //http://localhost:8888/filmshowtime-service/api/filmshowtime/get/739baa73-1d11-47c3-a049-c37da5f3da2b/2025-03-21/9d7226db-6e50-4370-a50b-f0f2b5f28024/1
}
