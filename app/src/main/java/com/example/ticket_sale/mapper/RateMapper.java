package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.RateDTO;
import com.example.ticket_sale.model.Rate;

public class RateMapper {
    public static Rate toRate(RateDTO dto){
        Rate rate = new Rate();

        rate.setId( dto.getId());
        rate.setStar(dto.getStar());
        rate.setContent(dto.getContent());
        rate.setCustomerName(dto.getCustomer().getName());
        rate.setTimeStamp(dto.getTimeStamp());
        return rate;
    }
}
