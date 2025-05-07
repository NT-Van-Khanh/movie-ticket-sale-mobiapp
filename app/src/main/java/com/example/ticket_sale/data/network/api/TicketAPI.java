package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.dto.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TicketAPI {
    String BASE_PATH = "payment-service/api/ticket";
    @GET(BASE_PATH + "/{id}")
    Call<ApiResponse<Ticket>> getTicketById(@Path("id") String id);

    @GET(BASE_PATH + "/get/active")
    Call<ApiResponse<List<Ticket>>> getAllActiveTickets();

    //http://localhost:8888/payment-service/api/ticket/all?limit=10&page=0&active=none&orderBy=price&asc=asc
    @GET(BASE_PATH + "/all")
    Call<ApiResponse<PageResponse<Ticket>>> getTicketsFilter(
            @Query("page") String page,
            @Query("limit") String limit,
            @Query("asc") String asc,
            @Query("active") String active,
            @Query("orderBy") String orderBy,
            @Query("q") String q);

}
