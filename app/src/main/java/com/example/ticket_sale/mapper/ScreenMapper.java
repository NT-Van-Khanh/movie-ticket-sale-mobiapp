package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.ScreenDTO;
import com.example.ticket_sale.model.Screen;

public class ScreenMapper {

    public static  Screen toScreen(ScreenDTO screenDTO){
        Screen screen = new Screen();

        return screen;
    }
}
