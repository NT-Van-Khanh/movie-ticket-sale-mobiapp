package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.dto.SeatTypeDTO;
import com.example.ticket_sale.data.network.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SeatAPI {
    String BASE_PATH = "room-service/api/chair";
    @GET(BASE_PATH + "/{id}")
    Call<ApiResponse<SeatTypeDTO>> getSeatById(@Path("id") String id);

    @GET(BASE_PATH + "/all")
    Call<ApiResponse<List<SeatTypeDTO>>> getAllSeats();

    //http://localhost:8888/room-service/api/chair/get/chair?page=0&limit=10&asc=asc&status=none&orderBy=name
    @GET(BASE_PATH + "/get/chair")
    Call<ApiResponse<List<SeatTypeDTO>>> getSeatsFilter(@Query("page") int page,
                                                        @Query("limit") int limit,
                                                        @Query("asc") String asc,
                                                        @Query("q") String q,
                                                        @Query("status") String status,
                                                        @Query("orderBy") String orderBy );
}
