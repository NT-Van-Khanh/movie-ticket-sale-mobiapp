package com.example.ticket_sale.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.CustomerCallBack;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.dto.TicketDTO;
import com.example.ticket_sale.data.network.api.TicketAPI;

import java.util.List;

public class TicketRepository {
    private final TicketAPI ticketAPI;

    public TicketRepository(TicketAPI ticketAPI) {
        this.ticketAPI = ticketAPI;
    }

    public LiveData<ApiResponse<TicketDTO>> getTicketById(String id){
        MutableLiveData<ApiResponse<TicketDTO>> responseData = new MutableLiveData<>();
        ticketAPI.getTicketById(id)
                .enqueue(new CustomerCallBack<>(responseData,getClass().getSimpleName() + "-getTicketById"));
        return responseData;
    }

    public LiveData<ApiResponse<List<TicketDTO>>> getAllActiveTickets(){
        MutableLiveData<ApiResponse<List<TicketDTO>>> responseData = new MutableLiveData<>();
        ticketAPI.getAllActiveTickets()
                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getAllActiveTickets"));
        return responseData;
    }

    public LiveData<ApiResponse<PageResponse<TicketDTO>>> getTicketsFilter(String page, String limit,
                                                                           String asc, String active,
                                                                           String orderBy, String q){
        MutableLiveData<ApiResponse<PageResponse<TicketDTO>>> responseData = new MutableLiveData<>();
        ticketAPI.getTicketsFilter(page, limit, asc, active, orderBy, q)
                .enqueue(new CustomerCallBack<>(responseData, getClass().getSimpleName() + "_getTicketsFilter"));
        return responseData;
    }
//    ticketRepository.getTicketsFilter("0", "10", "asc", "none", "price", "");
}
