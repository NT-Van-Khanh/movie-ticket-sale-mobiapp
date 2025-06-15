package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.ChairDTO;
import com.example.ticket_sale.data.dto.ChairRequestDTO;
import com.example.ticket_sale.data.dto.SeatTypeDTO;
import com.example.ticket_sale.data.dto.TicketDTO;
import com.example.ticket_sale.data.dto.TicketRequestDTO;
import com.example.ticket_sale.model.Seat;
import com.example.ticket_sale.model.SeatPosition;
import com.example.ticket_sale.model.SeatType;
import com.example.ticket_sale.model.Ticket;

import java.util.Locale;

public class SeatMapper {
    public static Seat toSeat(SeatTypeDTO seatDTO){
        if(seatDTO == null || !seatDTO.isValid()) return null;
        Seat seat = new Seat();
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

    public static ChairRequestDTO toChairRequest(SeatPosition seatPosition, Ticket ticket){
        if(seatPosition == null || ticket == null) return null;
        ChairRequestDTO chairDTO = new ChairRequestDTO();

        chairDTO.setPrice(60000D);
        chairDTO.setTicket(new TicketRequestDTO(ticket.getId()));
        chairDTO.setChairCode(String.format(Locale.getDefault(),"[%d,%d]", seatPosition.getRow(),
                            seatPosition.getColumn()));
        return chairDTO;
    }
}
