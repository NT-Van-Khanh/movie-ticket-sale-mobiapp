package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.ChairDTO;
import com.example.ticket_sale.data.dto.SeatTypeDTO;
import com.example.ticket_sale.model.Seat;
import com.example.ticket_sale.model.SeatPosition;
import com.example.ticket_sale.model.SeatType;

public class SeatMapper {
    public static Seat toSeat(SeatTypeDTO seatDTO){
        Seat seat = new Seat();
        if(!seatDTO.isValid()) return null;
        seat.setId(seatDTO.getId());
        seat.setTitle(seatDTO.getName());
        seat.setDescription(seatDTO.getDescription());
        seat.setPrice(60000L);
        return seat;
    }

    public static SeatPosition toSeatPosition(ChairDTO chairDTO){
        if(chairDTO == null || chairDTO.getChairCode() == null) return null;
        String chairCode = chairDTO.getChairCode();
        String[] parts = chairCode.replaceAll("[\\[\\]]", "").split(",");
        int row = Integer.parseInt(parts[0].trim());
        int col = Integer.parseInt(parts[1].trim());
        return new SeatPosition(row, col);
    }
}
