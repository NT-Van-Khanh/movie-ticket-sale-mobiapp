package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.ScreenDTO;
import com.example.ticket_sale.data.dto.SeatTypeDTO;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.SeatPosition;

import java.util.List;

public class ScreenMapper {

    public static  Screen toScreen(ScreenDTO screenDTO){
        if(!screenDTO.isValid()) return null;
        Screen screen = new Screen();
        screen.setId(screen.getId());
        screen.setName(screen.getName());
        screen.setTheater(TheaterMapper.toTheater(screenDTO.getTheater()));
        screen.setSeatPositions(screenDTO.getPositionChair());
        return screen;
    }

//    private SeatPosition[][] toSeatPosition(String[][] seatTypeId, List<SeatTypeDTO> seatTypeDTOS){
//
//    }
}
