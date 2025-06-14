package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.dto.ChairDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.dto.ScreenDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScreenAPI {

    @GET("/room-service/api/room/{id}")
    Call<ApiResponse<ScreenDTO>> getScreenById(@Path("id") String id);

    @GET("/room-service/api/room/all")
    Call<ApiResponse<List<ScreenDTO>>> getAllScreens();

    //http://localhost:8888/room-service/api/room/get/room?page=0&limit=10&asc=asc&status=none&orderBy=name
    @GET("/room-service/api/room/get/room")
    Call<ApiResponse<List<ScreenDTO>>> getScreensFilter(@Query("page") int page,
                                                        @Query("limit") int limit,
                                                        @Query("asc") String asc,
                                                        @Query("q") String q,
                                                        @Query("status") String status,
                                                        @Query("orderBy") String orderBy);

    //http://localhost:8888/room-service/api/room/get/branch/739baa73-1d11-47c3-a049-c37da5f3da2b
    @GET("/room-service/api/room/get/branch/{branchId}")
    Call<ApiResponse<List<ScreenDTO>>> getScreensByBranchId(@Path("branchId") String branchId);


    @GET("/payment-service/api/billchair/{filmShowId}")
    Call<ApiResponse<List<ChairDTO>>> getSoldSeats(
            @Path("filmShowId") Integer showtimeId);
}
//http://localhost:8888/payment-service/api/billchair/12
//{
//    "statusCode": 200,
//        "message": "Get bill chair successfully",
//        "data": [
//    {
//        "id": null,
//            "chairCode": "[0,1]",
//            "price": null,
//            "ticket": null,
//            "active": null
//    },
//    {
//        "id": null,
//            "chairCode": "[0,2]",
//            "price": null,
//            "ticket": null,
//            "active": null
//    },
//    {
//        "id": null,
//            "chairCode": "[2,1]",
//            "price": null,
//            "ticket": null,
//            "active": null
//    },
//    {
//        "id": null,
//            "chairCode": "[1,2]",
//            "price": null,
//            "ticket": null,
//            "active": null
//    },
//    {
//        "id": null,
//            "chairCode": "[2,2]",
//            "price": null,
//            "ticket": null,
//            "active": null
//    },
//    {
//        "id": null,
//            "chairCode": "[4,0]",
//            "price": null,
//            "ticket": null,
//            "active": null
//    }
//  ]
//}

