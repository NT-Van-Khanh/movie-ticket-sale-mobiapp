package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.dto.Rate;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RateAPI {
    String BASE_PATH = "rate-service/api/rate";

    //http://localhost:8888/rate-service/api/rate/film/1?page=0&limit=2&asc=asc&orderBy=timeStamp&status=ACTIVE
    @GET(BASE_PATH + "/film/{filmId}")
    Call<ApiResponse<PageResponse<Rate>>> getRateByMovieId(@Path("filmId") String movieId,
                                                         @Query("page") int page,
                                                         @Query("limit") int limit,
                                                         @Query("asc") String asc,
                                                         @Query("orderBy") String orderBy,
                                                         @Query("status") String status);

    // http://localhost:8888/rate-service/api/rate/check/1/afb2f2c2-e070-42f6-9796-03fcbd085ab2
    @GET(BASE_PATH + "/check/{filmId}/{customerId}")//không có dữ liệu trả về, chỉ có 200, 404, 201....
    Call<ApiResponse<Void>> checkRated(@Path("filmId") String movieId,
                                                     @Path("customerId") String UserId);

    // http://localhost:8888/rate-service/api/rate/get/rate?page=0&limit=10&asc=asc&orderBy=timeStamp&status=none
    @GET(BASE_PATH + "/get/rate")
    Call<ApiResponse<PageResponse<Rate>>> getRatesWithFilter(@Query("page") int page,
                       @Query("limit") int limit,
                       @Query("q") String q,
                       @Query("asc") String asc,
                       @Query("orderBy") String orderBy,
                       @Query("status") String status);

    //http://localhost:8888/rate-service/api/rate/add/cd664219-042d-4ffc-b851-5bbba4005d8b/1
    //Request body
    //{
    //  "star": 5,
    //  "content": "string",
    //  "customerId": "cd664219-042d-4ffc-b851-5bbba4005d8b",
    //  "filmId": "1",
    //  "active": "ACTIVE"
    //}
    @POST(BASE_PATH + "/add/{userId}/{filmId}")
    Call<ApiResponse<Rate>> addRate(@Path("filmId") String movieId,
                       @Path("userId") String userId,
                       @Body Map<String,Object> rate);

    @PUT(BASE_PATH + "/delete/{id}")
    Call<ApiResponse<Void>> deleteRate(@Path("id") String id);

//    @PUT(BASE_PATH + "/active/{id}")
//    Call<Rate> activeRate(@Path("id") String id);
}
